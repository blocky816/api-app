package com.tutorial.crud.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tutorial.crud.chatGPT.ChatGPT;
import com.tutorial.crud.entity.AnswerChatGPT;
import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.ClienteBascula;
import com.tutorial.crud.entity.FormularioRespuesta;
import com.tutorial.crud.exception.ChatGPTException;
import com.tutorial.crud.exception.ClienteNoEncontradoException;
import com.tutorial.crud.exception.ResourceNotFoundException;
import com.tutorial.crud.repository.AnswerChatGPTRepository;
import com.tutorial.crud.repository.FormularioRespuestaRepository;
import jdk.swing.interop.SwingInterOpUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.tutorial.crud.service.FormularioService.calcularAniosDate;

@Service
@Transactional
public class AnswerChatGPTService {

    private static final Logger logger = LoggerFactory.getLogger(AnswerChatGPTService.class);

    @Autowired
    AnswerChatGPTRepository answerChatGPTRepository;
    @Autowired
    ClienteBasculaService clienteBasculaService;
    @Autowired
    FormularioRespuestaRepository formularioRespuestaRepository;
    @Autowired
    ClienteService clienteService;
    @Autowired
    ChatGPT chatGPT;
    @Autowired
    private ObjectMapper objectMapper;

    @Value("${chatgpt.retries}")
    private int MAX_RETRIES;

    @Value("${chatgpt.retry-delay-ms}")
    private int RETRY_DELAY_MS;

    @Value("${odoo.api.url}")
    private String ODOO_API_URL;

    public AnswerChatGPT save(AnswerChatGPT answerChatGPT) {
        return answerChatGPTRepository.save(answerChatGPT);
    }

    public List<AnswerChatGPT> getDietInCurrentMonth(int customerID, LocalDateTime startDate, LocalDateTime endDate){
        Cliente customer = clienteService.findById(customerID);
        ClienteBascula clienteBascula = clienteBasculaService.getUltimoPesaje(customer.getIdCliente());
        if (clienteBascula == null) throw new RuntimeException("Cliente no tiene pesajes");
        List<FormularioRespuesta> formularioRespuestaList = formularioRespuestaRepository.findLastAnswersFormByCustomer(customer.getIdCliente(), 4);
        if (formularioRespuestaList.isEmpty() || formularioRespuestaList == null) throw new RuntimeException("Formulario vacio");
        List<AnswerChatGPT> answerChatGPTList = answerChatGPTRepository.findByCustomerAndCreatedAtBetweenOrderByCreatedAtDesc(customer, startDate, endDate);
        if(answerChatGPTList.isEmpty()) throw new RuntimeException("No hay dietas en el mes");
        return  answerChatGPTList;
    }

    public String getGuiaAlimenticiaMensual(Integer idCliente) throws ClienteNoEncontradoException, ResourceNotFoundException {
        logger.debug("Iniciando búsqueda del plan alimenticio para el cliente con id: {}", idCliente);

        Cliente cliente = clienteService.findById(idCliente);
        if (cliente == null) {
            logger.error("Cliente con id {} no encontrado.", cliente.getIdCliente());
            throw new ClienteNoEncontradoException("Cliente no encontrado con id: " + idCliente);
        }

        LocalDateTime[] dateRange = getStartAndEndDateOfCurrentMonth();
        LocalDateTime startDate = dateRange[0];
        LocalDateTime endDate = dateRange[1];
        Optional<AnswerChatGPT> planAlimenticioActual = Optional.ofNullable(
                answerChatGPTRepository.findFirstByCustomerIdClienteAndIsValidQuestionTrueAndActivoTrueAndCreatedAtBetweenOrderByCreatedAtDesc(
                        cliente.getIdCliente(), startDate, endDate
                )
        );

        if (!planAlimenticioActual.isPresent()) {
            logger.warn("No se encontró un plan alimenticio válido para el cliente con id: {}", cliente.getIdCliente());
            throw new ResourceNotFoundException("No se encontró un plan alimenticio para el cliente con id: " + cliente.getIdCliente());
        }

        return planAlimenticioActual.get().getAnswer();
    }

    public boolean verificarGuiaGeneradaEsteMes(Integer clienteId) {

        Cliente cliente = obtenerCliente(clienteId);
        LocalDateTime[] dateRange = getStartAndEndDateOfCurrentMonth();
        LocalDateTime startDate = dateRange[0];
        LocalDateTime endDate = dateRange[1];

        Optional<AnswerChatGPT> guiaDelMes = Optional.ofNullable(
                answerChatGPTRepository.findFirstByCustomerIdClienteAndIsValidQuestionTrueAndActivoTrueAndCreatedAtBetweenOrderByCreatedAtDesc(
                        cliente.getIdCliente(), startDate, endDate
                )
        );

        return guiaDelMes.isPresent();
    }

    private Cliente obtenerCliente(Integer idCliente) {
        Cliente cliente = clienteService.findById(idCliente);
        if (cliente == null) {
            throw new ClienteNoEncontradoException("Cliente no encontrado con id: " + idCliente);
        }
        return cliente;
    }

    private LocalDateTime[] getStartAndEndDateOfCurrentMonth() {
        LocalDate today = LocalDate.now();
        LocalDateTime startDate = LocalDateTime.of(today.minusDays(today.getDayOfMonth() - 1), LocalTime.MIN);
        LocalDateTime endDate = LocalDateTime.of(today.plusDays(today.lengthOfMonth() - today.getDayOfMonth()), LocalTime.MAX.withSecond(0).withNano(0));
        return new LocalDateTime[] { startDate, endDate };
    }

    @Transactional(noRollbackFor = ChatGPTException.class)
    public String generarGuiaAlimenticia(Integer idCliente) {
        try {
            String prompt = chatGPT.getPrompt(idCliente);
            return intentarGenerarGuiaConReintentos(idCliente, prompt);
        } catch (Exception e) {
            throw new ChatGPTException("Error al generar la guía alimenticia: " + e.getMessage());
        }
    }


    private String intentarGenerarGuiaConReintentos(Integer idCliente, String prompt) {
        for (int intentos = 0; intentos < MAX_RETRIES; intentos++) {
            try {
                JSONObject jsonObject = new JSONObject(prompt);
                String respuesta = chatGPT.generarGuiaSPEC(prompt);
                String pregunta = jsonObject.getJSONArray("messages").getJSONObject(1).getString("content");
                return procesarRespuesta(idCliente, pregunta, respuesta);
            } catch (Exception e) {
                if (intentos == MAX_RETRIES - 1) {
                    throw new ChatGPTException("No se pudo generar una dieta válida después de 3 intentos.");
                }
                logger.error("Error en intento " + (intentos + 1) + " de generar guía, reintentando..." + e.getMessage());
                esperarAntesDeReintentar();
            }
        }
        throw new ChatGPTException("Reintentos agotados, no se pudo generar la guía.");
    }

    private void esperarAntesDeReintentar() {
        try {
            Thread.sleep(RETRY_DELAY_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private String procesarRespuesta(Integer idCliente, String pregunta, String respuesta) {
        AnswerChatGPT answer = new AnswerChatGPT();

        String respuestaValida = chatGPT.isValidDiet(respuesta);
        boolean esRespuestaValida = esRespuestaValida(respuestaValida);
        //String prueba = "{\"día1\":{\"colación2\":\"150 gramos de yogur natural sin azúcar, 50 gramos de arándanos\",\"colación1\":\"100 gramos de almendras, 250 mililitros de jugo de naranja\",\"comida\":\"200 gramos de pechuga de pollo a la plancha, 150 gramos de quinoa cocida, 100 gramos de espinacas salteadas, 300 mililitros de agua\",\"desayuno\":\"150 gramos de avena cocida, 200 gramos de plátano, 30 gramos de nueces, 300 mililitros de leche descremada\",\"cena\":\"150 gramos de salmón al horno, 100 gramos de brócoli al vapor, 50 gramos de puré de papa, 300 mililitros de agua\"},\"día2\":{\"colación2\":\"1 manzana, 30 gramos de nueces\",\"colación1\":\"100 gramos de fresas, 30 gramos de almendras\",\"comida\":\"200 gramos de carne de res magra, 150 gramos de arroz integral, 100 gramos de calabacitas al vapor, 300 mililitros de agua\",\"desayuno\":\"2 huevos revueltos con 50 gramos de jamón de pavo, 100 gramos de pan integral, 300 mililitros de agua\",\"cena\":\"150 gramos de pechuga de pollo, 100 gramos de espárragos, 100 gramos de puré de batata, 300 mililitros de agua\"},\"día3\":{\"colación2\":\"1 pera, 30 gramos de almendras\",\"colación1\":\"100 gramos de zanahorias baby, 50 gramos de hummus\",\"comida\":\"200 gramos de pescado blanco al horno, 150 gramos de cuscús, 100 gramos de judías verdes, 300 mililitros de agua\",\"desayuno\":\"150 gramos de yogur griego, 50 gramos de granola, 100 gramos de frutos rojos, 300 mililitros de agua\",\"cena\":\"150 gramos de tofu salteado, 100 gramos de champiñones, 100 gramos de espinacas, 300 mililitros de agua\"},\"día4\":{\"colación2\":\"1 naranja, 30 gramos de almendras\",\"colación1\":\"1 plátano, 30 gramos de nueces\",\"comida\":\"200 gramos de pollo al curry, 150 gramos de arroz basmati, 100 gramos de guisantes, 300 mililitros de agua\",\"desayuno\":\"2 tortillas de maíz, 150 gramos de frijoles negros, 50 gramos de queso fresco, 300 mililitros de agua\",\"cena\":\"150 gramos de atún a la plancha, 100 gramos de col rizada, 50 gramos de puré de zanahoria, 300 mililitros de agua\"},\"día5\":{\"colación2\":\"1 manzana, 30 gramos de nueces\",\"colación1\":\"100 gramos de pepino, 50 gramos de hummus\",\"comida\":\"200 gramos de pavo al horno, 150 gramos de pasta integral, 100 gramos de brócoli, 300 mililitros de agua\",\"desayuno\":\"150 gramos de avena cocida, 50 gramos de pasas, 30 gramos de almendras, 300 mililitros de leche de almendra\",\"cena\":\"150 gramos de pollo al grill, 100 gramos de espárragos, 50 gramos de quinoa, 300 mililitros de agua\"},\"día6\":{\"colación2\":\"100 gramos de yogur natural, 50 gramos de arándanos\",\"colación1\":\"1 pera, 30 gramos de almendras\",\"comida\":\"200 gramos de lomo de cerdo asado, 150 gramos de arroz salvaje, 100 gramos de zanahorias al vapor, 300 mililitros de agua\",\"desayuno\":\"2 huevos cocidos, 100 gramos de pan integral, 50 gramos de aguacate, 300 mililitros de agua\",\"cena\":\"150 gramos de salmón a la plancha, 100 gramos de espinacas, 50 gramos de puré de batata, 300 mililitros de agua\"},\"día19\":{\"colación2\":\"1 pera, 30 gramos de almendras\",\"colación1\":\"100 gramos de pepino, 50 gramos de hummus\",\"comida\":\"200 gramos de pollo al limón, 150 gramos de arroz basmati, 100 gramos de guisantes, 300 mililitros de agua\",\"desayuno\":\"150 gramos de yogur griego, 50 gramos de granola, 100 gramos de frutos rojos, 300 mililitros de agua\",\"cena\":\"150 gramos de atún al horno, 100 gramos de col rizada, 50 gramos de puré de batata, 300 mililitros de agua\"},\"día7\":{\"colación2\":\"1 naranja, 30 gramos de nueces\",\"colación1\":\"100 gramos de zanahorias baby, 50 gramos de hummus\",\"comida\":\"200 gramos de pollo al ajillo, 150 gramos de arroz integral, 100 gramos de brócoli, 300 mililitros de agua\",\"desayuno\":\"150 gramos de avena cocida, 100 gramos de fresas, 30 gramos de semillas de chía, 300 mililitros de leche descremada\",\"cena\":\"150 gramos de pescado al vapor, 100 gramos de judías verdes, 50 gramos de puré de coliflor, 300 mililitros de agua\"},\"día28\":{\"colación2\":\"1 manzana, 30 gramos de nueces\",\"colación1\":\"1 plátano, 30 gramos de almendras\",\"comida\":\"200 gramos de carne de res a la plancha, 150 gramos de quinoa, 100 gramos de espárragos, 300 mililitros de agua\",\"desayuno\":\"2 tortillas de maíz, 150 gramos de frijoles refritos, 50 gramos de queso panela, 300 mililitros de agua\",\"cena\":\"150 gramos de tofu al grill, 100 gramos de champiñones, 50 gramos de puré de zanahoria, 300 mililitros de agua\"},\"día29\":{\"colación2\":\"1 pera, 30 gramos de almendras\",\"colación1\":\"100 gramos de pepino, 50 gramos de hummus\",\"comida\":\"200 gramos de pollo al limón, 150 gramos de arroz basmati, 100 gramos de guisantes, 300 mililitros de agua\",\"desayuno\":\"150 gramos de yogur griego, 50 gramos de granola, 100 gramos de frutos rojos, 300 mililitros de agua\",\"cena\":\"150 gramos de atún al horno, 100 gramos de col rizada, 50 gramos de puré de batata, 300 mililitros de agua\"},\"día26\":{\"colación2\":\"100 gramos de yogur natural, 50 gramos de arándanos\",\"colación1\":\"1 pera, 30 gramos de almendras\",\"comida\":\"200 gramos de lomo de cerdo asado, 150 gramos de arroz salvaje, 100 gramos de zanahorias al vapor, 300 mililitros de agua\",\"desayuno\":\"2 huevos cocidos, 100 gramos de pan integral, 50 gramos de aguacate, 300 mililitros de agua\",\"cena\":\"150 gramos de salmón a la plancha, 100 gramos de espinacas, 50 gramos de puré de batata, 300 mililitros de agua\"},\"día27\":{\"colación2\":\"1 naranja, 30 gramos de nueces\",\"colación1\":\"100 gramos de zanahorias baby, 50 gramos de hummus\",\"comida\":\"200 gramos de pollo al ajillo, 150 gramos de arroz integral, 100 gramos de brócoli, 300 mililitros de agua\",\"desayuno\":\"150 gramos de avena cocida, 100 gramos de fresas, 30 gramos de semillas de chía, 300 mililitros de leche descremada\",\"cena\":\"150 gramos de pescado al vapor, 100 gramos de judías verdes, 50 gramos de puré de coliflor, 300 mililitros de agua\"},\"día24\":{\"colación2\":\"1 naranja, 30 gramos de almendras\",\"colación1\":\"1 plátano, 30 gramos de nueces\",\"comida\":\"200 gramos de pollo al curry, 150 gramos de arroz basmati, 100 gramos de guisantes, 300 mililitros de agua\",\"desayuno\":\"2 tortillas de maíz, 150 gramos de frijoles negros, 50 gramos de queso fresco, 300 mililitros de agua\",\"cena\":\"150 gramos de atún a la plancha, 100 gramos de col rizada, 50 gramos de puré de zanahoria, 300 mililitros de agua\"},\"día25\":{\"colación2\":\"1 manzana, 30 gramos de nueces\",\"colación1\":\"100 gramos de pepino, 50 gramos de hummus\",\"comida\":\"200 gramos de pavo al horno, 150 gramos de pasta integral, 100 gramos de brócoli, 300 mililitros de agua\",\"desayuno\":\"150 gramos de avena cocida, 50 gramos de pasas, 30 gramos de almendras, 300 mililitros de leche de almendra\",\"cena\":\"150 gramos de pollo al grill, 100 gramos de espárragos, 50 gramos de quinoa, 300 mililitros de agua\"},\"día22\":{\"colación2\":\"1 manzana, 30 gramos de nueces\",\"colación1\":\"100 gramos de fresas, 30 gramos de almendras\",\"comida\":\"200 gramos de carne de res magra, 150 gramos de arroz integral, 100 gramos de calabacitas al vapor, 300 mililitros de agua\",\"desayuno\":\"2 huevos revueltos con 50 gramos de jamón de pavo, 100 gramos de pan integral, 300 mililitros de agua\",\"cena\":\"150 gramos de pechuga de pollo, 100 gramos de espárragos, 100 gramos de puré de batata, 300 mililitros de agua\"},\"día23\":{\"colación2\":\"1 pera, 30 gramos de almendras\",\"colación1\":\"100 gramos de zanahorias baby, 50 gramos de hummus\",\"comida\":\"200 gramos de pescado blanco al horno, 150 gramos de cuscús, 100 gramos de judías verdes, 300 mililitros de agua\",\"desayuno\":\"150 gramos de yogur griego, 50 gramos de granola, 100 gramos de frutos rojos, 300 mililitros de agua\",\"cena\":\"150 gramos de tofu salteado, 100 gramos de champiñones, 100 gramos de espinacas, 300 mililitros de agua\"},\"día20\":{\"colación2\":\"1 naranja, 30 gramos de nueces\",\"colación1\":\"100 gramos de fresas, 30 gramos de almendras\",\"comida\":\"200 gramos de pavo al grill, 150 gramos de pasta integral, 100 gramos de calabacitas, 300 mililitros de agua\",\"desayuno\":\"2 huevos revueltos con 50 gramos de jamón de pavo, 100 gramos de pan integral, 300 mililitros de agua\",\"cena\":\"150 gramos de pollo al horno, 100 gramos de espinacas, 50 gramos de quinoa, 300 mililitros de agua\"},\"día21\":{\"colación2\":\"150 gramos de yogur natural sin azúcar, 50 gramos de arándanos\",\"colación1\":\"100 gramos de almendras, 250 mililitros de jugo de naranja\",\"comida\":\"200 gramos de pechuga de pollo a la plancha, 150 gramos de quinoa cocida, 100 gramos de espinacas salteadas, 300 mililitros de agua\",\"desayuno\":\"150 gramos de avena cocida, 200 gramos de plátano, 30 gramos de nueces, 300 mililitros de leche descremada\",\"cena\":\"150 gramos de salmón al horno, 100 gramos de brócoli al vapor, 50 gramos de puré de papa, 300 mililitros de agua\"},\"día8\":{\"colación2\":\"1 manzana, 30 gramos de nueces\",\"colación1\":\"1 plátano, 30 gramos de almendras\",\"comida\":\"200 gramos de carne de res a la plancha, 150 gramos de quinoa, 100 gramos de espárragos, 300 mililitros de agua\",\"desayuno\":\"2 tortillas de maíz, 150 gramos de frijoles refritos, 50 gramos de queso panela, 300 mililitros de agua\",\"cena\":\"150 gramos de tofu al grill, 100 gramos de champiñones, 50 gramos de puré de zanahoria, 300 mililitros de agua\"},\"día17\":{\"colación2\":\"1 naranja, 30 gramos de nueces\",\"colación1\":\"100 gramos de zanahorias baby, 50 gramos de hummus\",\"comida\":\"200 gramos de pollo al ajillo, 150 gramos de arroz integral, 100 gramos de brócoli, 300 mililitros de agua\",\"desayuno\":\"150 gramos de avena cocida, 100 gramos de fresas, 30 gramos de semillas de chía, 300 mililitros de leche descremada\",\"cena\":\"150 gramos de pescado al vapor, 100 gramos de judías verdes, 50 gramos de puré de coliflor, 300 mililitros de agua\"},\"día9\":{\"colación2\":\"1 pera, 30 gramos de almendras\",\"colación1\":\"100 gramos de pepino, 50 gramos de hummus\",\"comida\":\"200 gramos de pollo al limón, 150 gramos de arroz basmati, 100 gramos de guisantes, 300 mililitros de agua\",\"desayuno\":\"150 gramos de yogur griego, 50 gramos de granola, 100 gramos de frutos rojos, 300 mililitros de agua\",\"cena\":\"150 gramos de atún al horno, 100 gramos de col rizada, 50 gramos de puré de batata, 300 mililitros de agua\"},\"día18\":{\"colación2\":\"1 manzana, 30 gramos de nueces\",\"colación1\":\"1 plátano, 30 gramos de almendras\",\"comida\":\"200 gramos de carne de res a la plancha, 150 gramos de quinoa, 100 gramos de espárragos, 300 mililitros de agua\",\"desayuno\":\"2 tortillas de maíz, 150 gramos de frijoles refritos, 50 gramos de queso panela, 300 mililitros de agua\",\"cena\":\"150 gramos de tofu al grill, 100 gramos de champiñones, 50 gramos de puré de zanahoria, 300 mililitros de agua\"},\"día15\":{\"colación2\":\"1 manzana, 30 gramos de nueces\",\"colación1\":\"100 gramos de pepino, 50 gramos de hummus\",\"comida\":\"200 gramos de pavo al horno, 150 gramos de pasta integral, 100 gramos de brócoli, 300 mililitros de agua\",\"desayuno\":\"150 gramos de avena cocida, 50 gramos de pasas, 30 gramos de almendras, 300 mililitros de leche de almendra\",\"cena\":\"150 gramos de pollo al grill, 100 gramos de espárragos, 50 gramos de quinoa, 300 mililitros de agua\"},\"día16\":{\"colación2\":\"100 gramos de yogur natural, 50 gramos de arándanos\",\"colación1\":\"1 pera, 30 gramos de almendras\",\"comida\":\"200 gramos de lomo de cerdo asado, 150 gramos de arroz salvaje, 100 gramos de zanahorias al vapor, 300 mililitros de agua\",\"desayuno\":\"2 huevos cocidos, 100 gramos de pan integral, 50 gramos de aguacate, 300 mililitros de agua\",\"cena\":\"150 gramos de salmón a la plancha, 100 gramos de espinacas, 50 gramos de puré de batata, 300 mililitros de agua\"},\"día13\":{\"colación2\":\"1 pera, 30 gramos de almendras\",\"colación1\":\"100 gramos de zanahorias baby, 50 gramos de hummus\",\"comida\":\"200 gramos de pescado blanco al horno, 150 gramos de cuscús, 100 gramos de judías verdes, 300 mililitros de agua\",\"desayuno\":\"150 gramos de yogur griego, 50 gramos de granola, 100 gramos de frutos rojos, 300 mililitros de agua\",\"cena\":\"150 gramos de tofu salteado, 100 gramos de champiñones, 100 gramos de espinacas, 300 mililitros de agua\"},\"día14\":{\"colación2\":\"1 naranja, 30 gramos de almendras\",\"colación1\":\"1 plátano, 30 gramos de nueces\",\"comida\":\"200 gramos de pollo al curry, 150 gramos de arroz basmati, 100 gramos de guisantes, 300 mililitros de agua\",\"desayuno\":\"2 tortillas de maíz, 150 gramos de frijoles negros, 50 gramos de queso fresco, 300 mililitros de agua\",\"cena\":\"150 gramos de atún a la plancha, 100 gramos de col rizada, 50 gramos de puré de zanahoria, 300 mililitros de agua\"},\"día11\":{\"colación2\":\"150 gramos de yogur natural sin azúcar, 50 gramos de arándanos\",\"colación1\":\"100 gramos de almendras, 250 mililitros de jugo de naranja\",\"comida\":\"200 gramos de pechuga de pollo a la plancha, 150 gramos de quinoa cocida, 100 gramos de espinacas salteadas, 300 mililitros de agua\",\"desayuno\":\"150 gramos de avena cocida, 200 gramos de plátano, 30 gramos de nueces, 300 mililitros de leche descremada\",\"cena\":\"150 gramos de salmón al horno, 100 gramos de brócoli al vapor, 50 gramos de puré de papa, 300 mililitros de agua\"},\"día12\":{\"colación2\":\"1 manzana, 30 gramos de nueces\",\"colación1\":\"100 gramos de fresas, 30 gramos de almendras\",\"comida\":\"200 gramos de carne de res magra, 150 gramos de arroz integral, 100 gramos de calabacitas al vapor, 300 mililitros de agua\",\"desayuno\":\"2 huevos revueltos con 50 gramos de jamón de pavo, 100 gramos de pan integral, 300 mililitros de agua\",\"cena\":\"150 gramos de pechuga de pollo, 100 gramos de espárragos, 100 gramos de puré de batata, 300 mililitros de agua\"},\"día10\":{\"colación2\":\"1 naranja, 30 gramos de nueces\",\"colación1\":\"100 gramos de fresas, 30 gramos de almendras\",\"comida\":\"200 gramos de pavo al grill, 150 gramos de pasta integral, 100 gramos de calabacitas, 300 mililitros de agua\",\"desayuno\":\"2 huevos revueltos con 50 gramos de jamón de pavo, 100 gramos de pan integral, 300 mililitros de agua\",\"cena\":\"150 gramos de pollo al horno, 100 gramos de espinacas, 50 gramos de quinoa, 300 mililitros de agua\"},\"día30\":{\"colación2\":\"1 naranja, 30 gramos de nueces\",\"colación1\":\"100 gramos de fresas, 30 gramos de almendras\",\"comida\":\"200 gramos de pavo al grill, 150 gramos de pasta integral, 100 gramos de calabacitas, 300 mililitros de agua\",\"desayuno\":\"2 huevos revueltos con 50 gramos de jamón de pavo, 100 gramos de pan integral, 300 mililitros de agua\",\"cena\":\"150 gramos de pollo al horno, 100 gramos de espinacas, 50 gramos de quinoa, 300 mililitros de agua\"}}";
        //System.out.println("Respuesta valida desordenada: " + prueba);
        //String respuestaValidaOrdenada = obtenerPlanAlimenticioOrdenado(prueba);
        String cleanedJsonString = compactarJson(respuestaValida);

        String errorRespuesta = "";
        if(!esRespuestaValida) {
            errorRespuesta = obtenerErrorEnRespuesta(respuesta);
        }

        answer.setCustomer(clienteService.findById(idCliente));
        answer.setQuestion(pregunta);
        answer.setAnswer(esRespuestaValida ? cleanedJsonString : errorRespuesta);
        answer.setValidQuestion(esRespuestaValida);
        answer.setActivo(esRespuestaValida);
        answer.setTipo("SPEC");

        logger.info("Respuesta procesada y limpia: " + truncarMensaje(esRespuestaValida ? cleanedJsonString : errorRespuesta, 50));

        answerChatGPTRepository.save(answer);

        if (respuestaValida.isEmpty()) {
            throw new ChatGPTException("No se pudo validar el plan alimenticio para: " + idCliente);
        }
        return respuesta;
    }

    private boolean esRespuestaValida(String respuesta) {
        return respuesta != null && !respuesta.trim().isEmpty();
    }

    private String obtenerErrorEnRespuesta(String respuesta){

        String content = "";
        try {
            JSONObject jsonObject = new JSONObject(respuesta);
            if (jsonObject.has("choices")
                    && jsonObject.getJSONArray("choices").length() > 0
                    && jsonObject.getJSONArray("choices").getJSONObject(0).has("message")
                    && jsonObject.getJSONArray("choices").getJSONObject(0).getJSONObject("message").has("content")) {
                content =  jsonObject.getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content");
            }
        } catch (Exception e) {
            logger.warn("Error al procesar el JSON de respuesta: " + e.getMessage());
            content = respuesta;
        }
        return compactarJson(content);
    }



    /*private String compactarJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            // Crear un LinkedHashMap para mantener el orden de las claves
            LinkedHashMap<String, Object> mapaOrdenado = new LinkedHashMap<>();
            return jsonObject.toString();
        } catch (Exception e) {
            logger.warn("Error al compactar el JSON, devolviendo sin formato: " + e.getMessage());
            return json.replaceAll("\\s+", " ").trim();
        }
    }*/

    private String compactarJson(String dietaDesordenada) {
        try {
            //ObjectMapper objectMapper = new ObjectMapper();

            LinkedHashMap<String, Object> jsonMap = objectMapper.readValue(
                    dietaDesordenada, new TypeReference<LinkedHashMap<String, Object>>() {});

            LinkedHashMap<String, Object> mapaOrdenado = jsonMap.keySet()
                    .stream()
                    .sorted(Comparator.comparingInt(clave -> Integer.parseInt(ChatGPT.quitarAcentos(clave).replaceAll("\\D", ""))))
                    .collect(Collectors.toMap(
                            clave -> clave,
                            jsonMap::get,
                            (a, b) -> b,
                            LinkedHashMap::new
                    ));

            return objectMapper.writeValueAsString(mapaOrdenado);
        } catch (Exception e) {
            logger.warn("Error al procesar el JSON: " + e.getMessage());
            e.printStackTrace();
            return dietaDesordenada.replaceAll("\\s+", " ").trim();
        }
    }

    private String truncarMensaje(String mensaje, int maxLength) {
        if (mensaje.length() > maxLength) {
            return mensaje.substring(0, maxLength) + "... [Truncado]";
        }
        return mensaje;
    }

    /****************************************************[ GUIA ASINCRONA ]******************************************************************************************/
    @Async
    public CompletableFuture<String> generarGuiaAlimenticiaAsync(Integer idCliente) {
        boolean guiaGenerada = verificarGuiaGeneradaEsteMes(idCliente);

        if (guiaGenerada) {
            logger.info("La guía ya fue generada este mes para el cliente: " + idCliente);
            return CompletableFuture.completedFuture("La guía ya fue generada este mes para el cliente: " + idCliente);
        }

        String estadoPago = verificarEstadoPago(idCliente);

        if (!"pagado".equalsIgnoreCase(estadoPago)) {
            logger.info("El cliente no tiene el estado de pago como 'pagado'. Guía alimenticia no generada.");
            return CompletableFuture.completedFuture("El cliente no tiene el estado de pago como 'pagado'. Guía alimenticia no generada.");
        }

        logger.info("Generando guía alimenticia para el cliente: " + idCliente);
        String guiaGeneradaMensaje = generarGuiaAlimenticia(idCliente);
        logger.info("Guía alimenticia generada exitosamente para el cliente: " + idCliente);
        return CompletableFuture.completedFuture(guiaGeneradaMensaje);
    }

    private String verificarEstadoPago(Integer idCliente) {
        try {
            logger.info("Verificando estado de pago para el cliente: {}", idCliente);

            String responseBody = realizarSolicitudEstadoPago(idCliente);
            String cleanedResponseBody = responseBody != null ? responseBody.trim().replaceAll("^\"|\"$", "") : null;

            if (cleanedResponseBody == null || "no hay datos que mostrar".equalsIgnoreCase(cleanedResponseBody)) {
                logger.info("Respuesta del endpoint: No hay datos que mostrar para el cliente: {}", idCliente);
                return "No hay datos que mostrar";
            }

            String estadoPago = extraerEstadoPago(responseBody);
            if (estadoPago != null) {
                return estadoPago;
            }

            logger.warn("No se pudo obtener el estado de pago correctamente para el cliente: {}", idCliente);
            return "No pagado";
        } catch (Exception e) {
            logger.error("Error al consultar el estado de pago para el cliente: {}", idCliente, e);
            return "No pagado";
        }
    }

    private String realizarSolicitudEstadoPago(Integer idCliente) throws RestClientException {
        RestTemplate restTemplate = new RestTemplate();
        String url = ODOO_API_URL + "/ServiciosClubAlpha/api/Spec/Estado/Pago";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("id_cliente", idCliente);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }

        logger.warn("Estado de respuesta inesperado: {} para cliente {}", response.getStatusCodeValue(), idCliente);
        return null;
    }

    private String extraerEstadoPago(String responseBody) {
        try {
            JSONArray jsonArray = new JSONArray(responseBody);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.has("estado_pago")) {
                    return jsonObject.getString("estado_pago");
                }
            }
        } catch (JSONException e) {
            logger.error("Error al analizar la respuesta JSON: {}", e.getMessage());
        }
        return null;
    }
}
