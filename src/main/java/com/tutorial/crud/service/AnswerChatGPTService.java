package com.tutorial.crud.service;

import com.tutorial.crud.chatGPT.ChatGPT;
import com.tutorial.crud.entity.AnswerChatGPT;
import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.ClienteBascula;
import com.tutorial.crud.entity.FormularioRespuesta;
import com.tutorial.crud.exception.ClienteNoEncontradoException;
import com.tutorial.crud.exception.ResourceNotFoundException;
import com.tutorial.crud.repository.AnswerChatGPTRepository;
import com.tutorial.crud.repository.FormularioRespuestaRepository;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final int MAX_RETRIES = 3; // Límite de reintentos
    private static final int RETRY_DELAY_MS = 1000; // Retardo entre intentos

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

    private LocalDateTime[] getStartAndEndDateOfCurrentMonth() {
        LocalDate today = LocalDate.now();
        LocalDateTime startDate = LocalDateTime.of(today.minusDays(today.getDayOfMonth() - 1), LocalTime.MIN);
        LocalDateTime endDate = LocalDateTime.of(today.plusDays(today.lengthOfMonth() - today.getDayOfMonth()), LocalTime.MAX.withSecond(0).withNano(0));
        return new LocalDateTime[] { startDate, endDate };
    }

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

    public boolean verificarGuiaAlimenticiaValida(Integer clienteId) {
        LocalDateTime[] dateRange = getStartAndEndDateOfCurrentMonth();
        LocalDateTime startDate = dateRange[0];
        LocalDateTime endDate = dateRange[1];

        Optional<AnswerChatGPT> guiaDelMes = Optional.ofNullable(
                answerChatGPTRepository.findFirstByCustomerIdClienteAndIsValidQuestionTrueAndActivoTrueAndCreatedAtBetweenOrderByCreatedAtDesc(
                        clienteId, startDate, endDate
                )
        );

        return guiaDelMes.isPresent();
    }

    public String verificarGuiaAlimenticiaMensual(Integer idCliente) throws ClienteNoEncontradoException {

        Cliente cliente = clienteService.findById(idCliente);
        if (cliente == null)
            throw new ClienteNoEncontradoException("Cliente no encontrado con id: " + idCliente);

        boolean existeGuiaMensual = verificarGuiaAlimenticiaValida(idCliente);

        return existeGuiaMensual ? "La guía alimenticia ya está generada para el cliente con id: " + idCliente : generarGuiaAlimenticia(idCliente);
    }

    @Transactional
    public String generarGuiaAlimenticia(Integer idCliente) {
        String prompt = chatGPT.getPrompt(idCliente);

        // Intentos de generación con un método dedicado
        return generarGuiaConReintentos(idCliente, prompt);
    }

    private String generarGuiaConReintentos(Integer idCliente, String prompt) {
        int retries = 0;
        boolean validDietFound = false;
        AnswerChatGPT answerChatGPT = new AnswerChatGPT();
        while (retries < MAX_RETRIES && !validDietFound) {
            try {
                JSONObject jsonObject = new JSONObject(prompt);
                String pregunta = jsonObject.getJSONArray("messages").getJSONObject(1).getString("content");
                String respuesta = chatGPT.generarGuiaSPEC(prompt);
                answerChatGPT = procesarRespuesta(pregunta, respuesta, idCliente);
                if (answerChatGPT.isValidQuestion()) {
                    validDietFound = true;
                }
                retries++;
                Thread.sleep(RETRY_DELAY_MS); // Retardo entre reintentos
            } catch (Exception e) {
                retries++;
                logger.error("Error al generar dieta, reintentando... " + e.getMessage());
                try {
                    Thread.sleep(RETRY_DELAY_MS); // Retardo entre intentos
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        if (!validDietFound) {
            throw new RuntimeException("No se pudo generar una dieta válida después de 3 intentos.");
        }
        return answerChatGPT.getAnswer();
    }

    private AnswerChatGPT procesarRespuesta(String prompt, String respuesta, Integer idCliente) {
        AnswerChatGPT answerChatGPT = new AnswerChatGPT();
        answerChatGPT.setCustomer(clienteService.findById(idCliente));
        answerChatGPT.setQuestion(prompt);
        String respuestaFinal = chatGPT.isValidDiet(respuesta);
        String cleanedJsonString = respuestaFinal.replaceAll("\\s+", " ").trim();
        boolean valido = !respuestaFinal.isEmpty();
        answerChatGPT.setValidQuestion(valido);
        answerChatGPT.setActivo(valido);
        answerChatGPT.setAnswer(valido ? cleanedJsonString : respuesta);
        answerChatGPT.setTipo("SPEC");
        answerChatGPTRepository.save(answerChatGPT);
        return answerChatGPT;
    }
}
