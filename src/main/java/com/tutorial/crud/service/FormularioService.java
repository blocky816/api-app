package com.tutorial.crud.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tutorial.crud.aopDao.Pregunta;
import com.tutorial.crud.dto.BodyFormulario;
import com.tutorial.crud.dto.FormularioDTO;
import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.Formulario;
import com.tutorial.crud.entity.FormularioCliente;
import com.tutorial.crud.entity.FormularioRespuesta;
import com.tutorial.crud.repository.FormularioRepository;
import com.tutorial.crud.repository.FormularioRespuestaRepository;
import org.hibernate.mapping.Formula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public Integer getLastFormularioId() {
        return formularioRepository.getLastFormularioId();
    }

    public Boolean existByFolioAndActivo(int folio, boolean activo) {
        return formularioRepository.existsByFolioAndActivo(folio, activo);
    }

    public LocalDateTime getFechaCreated(int folio) {
        Formulario formulario = formularioRepository.findTopByFolio(folio);
        System.out.println("FECHA de creacion del formulario: " + formulario.getCreated());
        return formulario.getCreated();
    }

    public void save(BodyFormulario body) {
        FormularioDTO formulario = body.getBody().get(0);
        List<Pregunta> preguntas = formulario.getFormulario();

        /*System.out.println("FORMULARIO REQUEST: " + formulario);
        System.out.println("Formulario preguntas: " + formulario.getFormulario());
        System.out.println("Formulario nombre: " + formulario.getNombre());
        System.out.println("Formulario tipo: " + formulario.getTipo());
        System.out.println("Formulario nivel: " + formulario.getNivel());
        System.out.println("LAST FOLIO:  " + getLastFormularioId());*/
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
        System.out.println("IDCLIENTE: " + idCliente);
        System.out.println("FOLIO: " + folio);
        if (idCliente < 1 || folio < 1) {
            System.out.println("CAMPOS OBLIGATORIOS");
            throw new NullPointerException("Todos los campos son obligatorios");
        }
        Cliente cliente = clienteService.findById(idCliente);
        if (cliente == null) {
            System.out.println("Cliente no existe");
            throw new NullPointerException("CLIENTE no existe");
        }
        System.out.println("Formulario existe ? : " + existByFolioAndActivo(folio, true));
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
                System.out.println("ES seguro eliminar el formulario");
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

        System.out.println("FORMULARIO RESPUESTAS: " + formularioRespuestas);
        if (respuestas.isEmpty()) throw new NullPointerException("Formulario vacio");

        for (Pregunta respuesta: respuestas){
            FormularioRespuesta newFormulario = new FormularioRespuesta();
            newFormulario.setFolio(formulario.get(0).getFolio());
            newFormulario.setCliente(cliente);
            newFormulario.setPregunta(respuesta.getPregunta());
            newFormulario.setRespuesta(respuesta.getRespuesta());
            formularioRespuestaRepository.save(newFormulario);
        }

        List<FormularioCliente> formulariosCliente = formularioClienteService.findByFolioAndActivo(formulario.get(0).getFolio(), true);
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

    /*public List<Formulario> findAllByFolio(int folio, Boolean activo) {
        List<Formulario> formulario = formularioRepository.findAllByFolioAndActivo(folio, activo);
        if (ejercicio.isPresent()) {
            return ejercicio.get();
        } else {
            throw new NullPointerException("El formulario no existe");
        }
    }*/
}
