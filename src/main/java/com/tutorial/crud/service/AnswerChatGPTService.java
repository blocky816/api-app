package com.tutorial.crud.service;

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
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

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

    @Value("${chatgpt.retries}")
    private int MAX_RETRIES;

    @Value("${chatgpt.retry-delay-ms}")
    private int RETRY_DELAY_MS;

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

    @Transactional
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
                logger.error("Error en intento " + (intentos + 1) + " de generar guía, reintentando...", e);
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
        String cleanedJsonString = respuestaValida.replaceAll("\\s+", " ").trim();

        answer.setCustomer(clienteService.findById(idCliente));
        answer.setQuestion(pregunta);
        answer.setAnswer(esRespuestaValida ? cleanedJsonString : respuesta);
        answer.setValidQuestion(esRespuestaValida);
        answer.setActivo(esRespuestaValida);
        answer.setTipo("SPEC");
        answerChatGPTRepository.save(answer);
        return respuesta;
    }

    private boolean esRespuestaValida(String respuesta) {
        return respuesta != null && !respuesta.trim().isEmpty();
    }
}
