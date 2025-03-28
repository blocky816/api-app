package com.tutorial.crud.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tutorial.crud.Odoo.Spec.dto.*;
import com.tutorial.crud.Odoo.Spec.entity.RespuestaFormulario;
import com.tutorial.crud.Odoo.Spec.repository.PreguntaRepository;
import com.tutorial.crud.Odoo.Spec.repository.RespuestaFormularioRepository;
import com.tutorial.crud.aopDao.Pregunta;
import com.tutorial.crud.chatGPT.ChatGPT;
import com.tutorial.crud.dto.BodyFormulario;
import com.tutorial.crud.dto.FormularioDTO;
import com.tutorial.crud.entity.*;
import com.tutorial.crud.exception.ClienteNoEncontradoException;
import com.tutorial.crud.repository.*;
import jdk.jfr.Experimental;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Transactional
public class FormularioService {

    @Autowired
    FormularioRepository formularioRepository;

    @Autowired
    ClienteService clienteService;

    @Autowired
    FormularioClienteService formularioClienteService;

    @Autowired
    FormularioRespuestaRepository formularioRespuestaRepository;

    @Autowired
    ClienteBasculaService clienteBasculaService;

    @Autowired
    AnswerChatGPTService answerChatGPTService;

    @Autowired
    AnswerChatGPTRepository answerChatGPTRepository;

    @Autowired
    ClubService clubService;

    @Autowired
    private PreguntaRepository preguntaRepository;

    @Autowired
    private RespuestaFormularioRepository respuestaFormularioRepository;

    @Autowired
    ChatGPT chatGPT;

    public Integer getLastFormularioId() {
        return formularioRepository.getLastFormularioId();
    }

    public Boolean existByFolioAndActivo(int folio, boolean activo) {
        return formularioRepository.existsByFolioAndActivo(folio, activo);
    }

    public LocalDateTime getFechaCreated(int folio) {
        Formulario formulario = formularioRepository.findTopByFolio(folio);
        return formulario.getCreated();
    }

    public void save(BodyFormulario body) {
        FormularioDTO formulario = body.getBody().get(0);
        List<Pregunta> preguntas = formulario.getFormulario();

        int lastFolio = getLastFormularioId() == null ? 1 : getLastFormularioId() + 1;
        for (Pregunta pregunta: preguntas){
            Formulario newFormulario = new Formulario();
            newFormulario.setPregunta(pregunta.getPregunta());
            newFormulario.setRespuesta(pregunta.getRespuesta());
            newFormulario.setFolio(lastFolio);
            newFormulario.setNombre(formulario.getNombre());
            newFormulario.setTipo(formulario.getTipo());
            newFormulario.setNivel(formulario.getNivel());
            formularioRepository.save(newFormulario);
        }
    }

    public void asignarCliente(ObjectNode request) throws Exception {
        int idCliente = request.get("idCliente").asInt();
        int folio = request.get("folio").asInt();
        if (idCliente < 1 || folio < 1) {
            throw new NullPointerException("Todos los campos son obligatorios");
        }
        Cliente cliente = clienteService.findById(idCliente);
        if (cliente == null) {
            throw new NullPointerException("CLIENTE no existe");
        }
        if(existByFolioAndActivo(folio, true)) {
            if (cliente.getFormulario() == null){
                FormularioCliente formularioCliente = new FormularioCliente();

                formularioCliente.setFolio(folio);
                formularioCliente.setCliente(cliente);
                cliente.setFormulario(folio);
                formularioCliente.setFormularioDate(getFechaCreated(folio));
                formularioClienteService.save(formularioCliente);
            } else {
                throw new Exception("El cliente tiene un formulario asignado");
            }
        } else {
            throw new NullPointerException("Formulario no existe");
        }
    }

    public FormularioDTO findByFolio(int folio) {
        List<Formulario> formularioList = formularioRepository.findAllByFolioAndActivo(folio, true);
        if (folio < 0 || formularioList.isEmpty()){
            throw new NullPointerException("Formulario no existe");
        } else {
            FormularioDTO formularioDTO = new FormularioDTO();
            ArrayList<Pregunta> preguntas = new ArrayList<>();

            for (Formulario formulario: formularioList){
                Pregunta pregunta = new Pregunta(formulario.getPregunta(), formulario.getRespuesta());
                preguntas.add(pregunta);
            }
            formularioDTO.setId(formularioList.get(0).getFolio());
            formularioDTO.setNombre(formularioList.get(0).getNombre());
            formularioDTO.setTipo(formularioList.get(0).getTipo());
            formularioDTO.setNivel(formularioList.get(0).getNivel());
            formularioDTO.setFormulario(preguntas);
            return formularioDTO;
        }
    }

    public void updateFormulario(BodyFormulario body, int folio) {
        FormularioDTO formulario = body.getBody().get(0);
        List<Pregunta> preguntas = formulario.getFormulario();
        List<Formulario> formularioList = formularioRepository.findAllByFolioAndActivo(folio, true);

        if (!formularioList.isEmpty()) {
            for(Formulario form: formularioList) {
                form.setActivo(false);
                formularioRepository.save(form);
            }

            int folioForm = formularioList.get(0).getFolio();
            //int lastFolio = getLastFormularioId() == null ? 1 : getLastFormularioId() + 1;
            for (Pregunta pregunta: preguntas){
                Formulario newFormulario = new Formulario();
                newFormulario.setPregunta(pregunta.getPregunta());
                newFormulario.setRespuesta(pregunta.getRespuesta());
                newFormulario.setFolio(folioForm);
                newFormulario.setNombre(formulario.getNombre());
                newFormulario.setTipo(formulario.getTipo());
                newFormulario.setNivel(formulario.getNivel());
                formularioRepository.save(newFormulario);
            }
        } else {
            throw new NullPointerException("El formulario no existe");
        }
    }

    public void deleteByFolio(int folio) throws Exception {
        List<Formulario> formularioList = formularioRepository.findAllByFolioAndActivo(folio, true);
        if (folio <= 0 || formularioList.isEmpty()){
            throw new NullPointerException("Formulario no existe");
        } else {
            if (formularioClienteService.findByFolioAndActivo(folio, true).isEmpty()){
                for(Formulario form: formularioList) {
                    form.setActivo(false);
                    formularioRepository.save(form);
                }
            } else {
                throw new Exception("No se puede eliminar un formulario asignado");
            }

        }
    }

    public FormularioDTO findByCliente(int id) {
        Cliente cliente = clienteService.findById(id);

        if (cliente == null) {
            throw new NullPointerException("El cliente no existe");
        }
        int folio = cliente.getFormulario();
        List<Formulario> formularioList = formularioRepository.findAllByFolioAndActivo(folio, true);
        if (folio < 0 || formularioList.isEmpty()){
            throw new NullPointerException("El cliente no tiene asignado ningun formulario");
        } else {
            FormularioDTO formularioDTO = new FormularioDTO();
            ArrayList<Pregunta> preguntas = new ArrayList<>();

            for (Formulario formulario: formularioList){
                Pregunta pregunta = new Pregunta(formulario.getPregunta(), formulario.getRespuesta());
                preguntas.add(pregunta);
            }
            formularioDTO.setId(formularioList.get(0).getFolio());
            formularioDTO.setNombre(formularioList.get(0).getNombre());
            formularioDTO.setTipo(formularioList.get(0).getTipo());
            formularioDTO.setNivel(formularioList.get(0).getNivel());
            formularioDTO.setFormulario(preguntas);
            return formularioDTO;
        }
    }

    public void responderForm(Integer idCliente, FormularioDTO body) {
        if (idCliente == null) throw new NullPointerException("Cliente es obligatorio");
        Cliente cliente = clienteService.findById(idCliente);
        if (cliente == null) throw new NullPointerException("El cliente no existe");
        Integer idFormulario = cliente.getFormulario();
        if (idFormulario == null) throw new NullPointerException("El cliente no tiene un formulario asignado");
        List<Formulario> formulario = formularioRepository.findAllByFolioAndActivo(idFormulario, true);
        if (formulario.isEmpty()) throw new NullPointerException("El formulario no existe");

        //FormularioDTO formularioRespuestas = body.getBody().get(0);
        FormularioDTO formularioRespuestas = body;
        List<Pregunta> respuestas = formularioRespuestas.getFormulario();

        if (respuestas.isEmpty()) throw new NullPointerException("Formulario vacio");

        for (Pregunta respuesta: respuestas){
            FormularioRespuesta newFormulario = new FormularioRespuesta();
            newFormulario.setFolio(formulario.get(0).getFolio());
            newFormulario.setCliente(cliente);
            newFormulario.setPregunta(respuesta.getPregunta());
            newFormulario.setRespuesta(respuesta.getRespuesta());
            formularioRespuestaRepository.save(newFormulario);
        }

        List<FormularioCliente> formulariosCliente = formularioClienteService.findByFolioAndActivoAndCliente(formulario.get(0).getFolio(), true, cliente);
        for (FormularioCliente formularioCliente: formulariosCliente){
            if (formularioCliente.getFolio() == formulario.get(0).getFolio() &&
                    formularioCliente.getCliente() == cliente){
                formularioCliente.setActivo(false);
                formularioClienteService.save(formularioCliente);
                cliente.setFormulario(null);
                clienteService.save(cliente);
            }
        }
    }

    public String getFormAnswersForPrompt(int customerID, int formFolio) {
        Cliente customer = clienteService.findById(customerID);
        String prompt = getCustomerWeighing(customerID);
        //System.out.println("Prompt => " + prompt);
        ///List<FormularioRespuesta> formularioRespuestaList = formularioRespuestaRepository.findByClienteAndFolio(customer, formFolio);
        List<FormularioRespuesta> formularioRespuestaList = formularioRespuestaRepository.findLastAnswersFormByCustomer(customer.getIdCliente(), formFolio);
        if (formularioRespuestaList.isEmpty() || formularioRespuestaList == null) throw new RuntimeException("Formulario vacio");
        for (FormularioRespuesta iterator : formularioRespuestaList) {
            if (iterator.getRespuesta() == null || iterator.getRespuesta().trim().equals("")) continue;
            //prompt = prompt + "\n" + iterator.getPregunta() + " " + iterator.getRespuesta();
            prompt = prompt + iterator.getPregunta() + " " + iterator.getRespuesta() + " ";
        }

        //ChatGPT chatGPT = new ChatGPT(answerChatGPTService, answerChatGPTRepository, this);
        int tries = 0;
        String answerChatGPT = "";
        do {
            //System.out.println("Intento numero: " + tries);
            answerChatGPT = chatGPT.sendPrompt(prompt, customer);
            if (!answerChatGPT.isEmpty() || !"".equals(answerChatGPT)) {
                //System.out.println("No estuve vacio");
                break;
            }
            else  {
                //System.out.println("ChatGPT no fallo pero devolvio vacio, intento: " + tries);
                tries++;
            }
        } while(tries < 3);
        //System.out.println("CHATGPT => " + answerChatGPT);
        return answerChatGPT;
    }

    public String getCustomerWeighing(int customerID) {
        ClienteBascula clienteBascula = clienteBasculaService.getUltimoPesaje(customerID);
        if (clienteBascula == null) throw new RuntimeException("Cliente no tiene pesajes");
        Cliente cliente = clienteService.findById(customerID);
        String gender = cliente.getSexo().toLowerCase();
        Date today = new Date();
        int age = calcularAniosDate(cliente.getFechaNacimiento());
        String athlete = clienteBascula.atleta ? "soy atleta." : "no soy atleta.";
        String initial = "Dame un json para lo siguiente: ";
        String customerWeighing = initial + "Soy " + gender + " de " + age + " años de edad, mido " + (float) clienteBascula.altura / 100 + " m, peso "
            + clienteBascula.peso + " kg, mi aporte calorico diario es de " + clienteBascula.caloriasDiarias +" y " + athlete + " Recientemente conteste el siguiente cuestionario sobre salud: ";
        return customerWeighing;
    }

    public static int calcularAniosDate(Date fecha) {
        //Fecha actual
        Date actual = new Date();
        //Cojo los datos necesarios
        int diaActual = actual.getDate();
        int mesActual = actual.getMonth() + 1;
        int anioActual = actual.getYear() + 1900;
        //Diferencia de años
        int diferencia = anioActual - (fecha.getYear() + 1900);
        // Si la diferencia es diferencia a 0
        if (diferencia != 0) {
            //Si el mes actual es menor que el que me pasan le resto 1
            //Si el mes es igual y el dia que me pasan es mayor al actual le resto 1
            //Si el mes es igual y el dia que me pasan es menor al actual no le resto 1
            if (mesActual <= (fecha.getMonth() + 1)) {
                if (mesActual == (fecha.getMonth() + 1)) {
                    if (fecha.getDate() > diaActual) {
                        diferencia--;
                    }
                } else {
                    diferencia--;
                }
            }
        }
        return diferencia;
    }

    /* public void assignDietForm(int customerID) {
        Cliente customer = clienteService.findById(customerID);
        List<FormularioRespuesta> formularioRespuestas = formularioRespuestaRepository.findLastAnswersFormByCustomer(customerID, 4);
        if (customer != null && customer.getFormulario() == null && formularioRespuestas.isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.createObjectNode();
            ((ObjectNode) node).put("idCliente", customerID);
            ((ObjectNode) node).put("folio", 4);
            try {
                this.asignarCliente((ObjectNode) node);
            } catch (Exception e) {
                System.out.println("Error al asignar form: " + e.getMessage());
            }
        }
    }

    public void assignDietForms() {
        List<Cliente> customers = clienteService.findAll();
        System.out.println("clientes All: " + customers.size());
        for (Cliente customer: customers) {
            try {
                assignDietForm(customer.getIdCliente());
            } catch (Exception e) { }
        }
    }

    /*public void assignDietFormsByClub(int clubID) {
        Club club = clubService.findById(clubID);
        List<Cliente> customers = clienteService.findAllByClub(club);
        System.out.println("clientes by club: " + customers.size());
        for (Cliente customer: customers) {
            try {
                assignDietForm(customer.getIdCliente());
            } catch (Exception e) { }
        }
    }*/

    public Formulario crearFormulario(Formulario formulario) {
        Formulario savedFormulario = formularioRepository.save(formulario);

        // Guardamos las preguntas asociadas (si existen)
        if (formulario.getPreguntas() != null) {
            for (com.tutorial.crud.Odoo.Spec.entity.Pregunta pregunta : formulario.getPreguntas()) {
                pregunta.setFormulario(savedFormulario);  // Relacionamos la pregunta con el formulario
                preguntaRepository.save(pregunta);
            }
        }

        return savedFormulario;
    }


    @Transactional
    public List<RespuestaFormulario> responderFormularioMultiple(RespuestasRequestDTO formulario) throws ClienteNoEncontradoException {
        // Obtener el cliente y el formulario desde la solicitud
        ClienteDTO clienteDTO = formulario.getCliente();
        List<RespuestaDTO> respuestas = formulario.getFormulario();

        Cliente cliente = clienteService.findById(clienteDTO.getIdCliente());

        if (cliente == null)
            throw new ClienteNoEncontradoException("Cliente no encontrado con id: " + clienteDTO.getIdCliente());

        List<String> preguntasTexto = respuestas.stream()
                .map(RespuestaDTO::getPregunta)
                .collect(Collectors.toList());

        List<com.tutorial.crud.Odoo.Spec.entity.Pregunta> preguntasExistentes = preguntaRepository.findByDescripcionIn(preguntasTexto);

        Map<String, com.tutorial.crud.Odoo.Spec.entity.Pregunta> preguntaMap = preguntasExistentes.stream()
                .collect(Collectors.toMap(com.tutorial.crud.Odoo.Spec.entity.Pregunta::getDescripcion, p -> p));

        LocalDateTime fechaRespuesta = LocalDateTime.now().withNano(0);

        respuestaFormularioRepository.desactivarRespuestasActivas(cliente.getIdCliente());

        List<RespuestaFormulario> respuestasGuardadas = respuestas.stream()
                .map(respuestaDTO -> {
                    com.tutorial.crud.Odoo.Spec.entity.Pregunta pregunta = preguntaMap.get(respuestaDTO.getPregunta());

                    if (pregunta == null) {
                        pregunta = new com.tutorial.crud.Odoo.Spec.entity.Pregunta();
                        pregunta.setDescripcion(respuestaDTO.getPregunta());
                        pregunta.setActivo(true);
                        pregunta.setTipo("texto");
                        preguntaRepository.save(pregunta);
                        preguntaMap.put(respuestaDTO.getPregunta(), pregunta);
                    }

                    RespuestaFormulario respuesta = new RespuestaFormulario();
                    respuesta.setPregunta(pregunta);
                    respuesta.setCliente(cliente);
                    respuesta.setRespuesta(respuestaDTO.getRespuesta());
                    respuesta.setFechaRespuesta(fechaRespuesta);
                    respuesta.setActivo(true);
                    respuesta.setPeso(clienteDTO.getPeso());
                    respuesta.setEstatura(clienteDTO.getEstatura());

                    return respuesta;
                })
                .collect(Collectors.toList());

        return respuestaFormularioRepository.saveAll(respuestasGuardadas);
    }


    public List<RespuestaDTO> obtenerRespuestasPorCliente(int idCliente) {
        List<RespuestaFormulario> respuestas = respuestaFormularioRepository.findByClienteIdClienteAndActivoTrue(idCliente);
        return respuestas.stream()
                .map(respuesta -> new RespuestaDTO(respuesta.getId(), respuesta.getPregunta().getId(), respuesta.getPregunta().getDescripcion(),
                        respuesta.getRespuesta(), respuesta.getCliente().getIdCliente(), respuesta.getPeso(), respuesta.getEstatura()))
                .collect(Collectors.toList());
    }

}
