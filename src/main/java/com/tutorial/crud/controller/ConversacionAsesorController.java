package com.tutorial.crud.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tutorial.crud.entity.BasculaCliente;
import com.tutorial.crud.entity.Cliente;

import com.tutorial.crud.entity.ConversacionAsesor;
import com.tutorial.crud.service.BasculaClienteService;
import com.tutorial.crud.service.ClienteService;
import com.tutorial.crud.service.ConversacionAsesorService;
import org.json.JSONObject;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.Integers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.desktop.SystemEventListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/conversaciones")
@CrossOrigin(origins = "*")
public class
ConversacionAsesorController {

    @Autowired
    ConversacionAsesorService conversacionAsesorService;

    @Autowired
    BasculaClienteService basculaClienteService;

    @Autowired
    ClienteService clienteService;

    /*--------------------------------------------------- Bascula Cliente Service -----------------------------------*/
    @PostMapping("/bascula")
    @ResponseBody
    public ResponseEntity<?> crearRegistroBascula(@RequestBody BasculaCliente basculaCliente){

        JSONObject response = new JSONObject();
        if (basculaCliente.getIdCliente() == 0){
            response.put("respuesta", "Campo CLIENTE es obligatorio");
            return new ResponseEntity<>(response.toMap(), HttpStatus.CONFLICT);
        }
        Cliente cliente = clienteService.findById(basculaCliente.getIdCliente());
        if (cliente == null){
            response.put("respuesta", "El cliente no existe");
            return new ResponseEntity<>(response.toMap(), HttpStatus.NOT_FOUND);
        }else {
            if(basculaClienteService.existsByIdCliente(basculaCliente.getIdCliente()) && basculaCliente.getActivo() == null){
                BasculaCliente basculaClienteFound = basculaClienteService.findByIdCliente(basculaCliente.getIdCliente());
                if (basculaClienteFound.getIntentos() >= 1 && cliente.getFormulario() == null) {
                    System.out.println("CLIEnte encontrado en bascula: " + basculaCliente.getIdCliente());
                    System.out.println("Intentos de pesaje: " + basculaClienteFound.getIntentos());
                    basculaClienteFound.setIntentos(basculaClienteFound.getIntentos() + 1);
                    basculaClienteService.save(basculaClienteFound);
                }

                response.put("respuesta", "CLIENTE ya registrado");
                return new ResponseEntity<>(response.toMap(), HttpStatus.CONFLICT);
            } else if (basculaCliente.getActivo() != null) {
                BasculaCliente basculaClienteFound = basculaClienteService.findByIdCliente(basculaCliente.getIdCliente());
                basculaClienteFound.setActivo(basculaCliente.getActivo());
                basculaClienteService.save(basculaClienteFound);
                return new ResponseEntity<>(basculaClienteFound, HttpStatus.OK);
            }else {
                BasculaCliente basculaClienteNew = new BasculaCliente();
                basculaClienteNew = basculaCliente;
                basculaClienteNew.setActivo(true);
                basculaClienteService.save(basculaClienteNew);
                return new ResponseEntity<>(basculaClienteNew, HttpStatus.OK);
            }
        }
    }

    @GetMapping("/getBasculaByCliente")
    @ResponseBody
    public ResponseEntity<?> obtenerRegistroBascula(@RequestParam int idCliente) {

        JSONObject response = new JSONObject();
        if (idCliente == 0) {
            response.put("respuesta", "Campo CLIENTE es obligatorio");
            return new ResponseEntity<>(response.toMap(), HttpStatus.CONFLICT);
        }
        Cliente cliente = clienteService.findById(idCliente);
        if (cliente == null) {
            response.put("respuesta", "El cliente no existe");
            return new ResponseEntity<>(response.toMap(), HttpStatus.NOT_FOUND);
        }
        else {
            if(basculaClienteService.existsByIdCliente(idCliente)) {
                BasculaCliente basculaCliente = basculaClienteService.findByIdCliente(idCliente);
                return new ResponseEntity<>(basculaCliente, HttpStatus.OK);
            } else {
                response.put("respuesta", "CLIENTE no pesado");
                return new ResponseEntity<>(response.toMap(), HttpStatus.NOT_FOUND);
            }
        }
    }


    /*------------------------------------------- ConversacionesAsesor Service -----------------------------------*/
    @GetMapping("/obtenerByIdCliente")
    @ResponseBody
    public ResponseEntity<?> obtenerRegistroConversaciones(@RequestParam int idCliente) {

        JSONObject response = new JSONObject();
        if (idCliente == 0) {
            response.put("respuesta", "Campo CLIENTE no válido");
            return new ResponseEntity<>(response.toMap(), HttpStatus.CONFLICT);
        }
        Cliente cliente = clienteService.findById(idCliente);
        if (cliente == null) {
            response.put("respuesta", "El cliente no existe");
            return new ResponseEntity<>(response.toMap(), HttpStatus.NOT_FOUND);
        }
        else {
            List<ConversacionAsesor> conversacionAsesorList = conversacionAsesorService.findAllByFolioAndActivoOrderByFechaAsc(cliente.getIdCliente(), true);
            for(ConversacionAsesor conversacionAsesor: conversacionAsesorList) {
                conversacionAsesor.setVisto(true);
                conversacionAsesorService.save(conversacionAsesor);
            }
            conversacionAsesorList = conversacionAsesorService.findAllByFolioAndActivoOrderByFechaAsc(cliente.getIdCliente(), true);
            return new ResponseEntity<>(conversacionAsesorList, HttpStatus.OK);
        }
    }

    @PostMapping("/cliente")
    @ResponseBody
    public ResponseEntity<?> nuevaMensajeCliente(@RequestBody ConversacionAsesor request){

        JSONObject response = new JSONObject();

        if (!"B".equals(request.getTipo())) {
            response.put("respuesta", "Campo TIPO no válido o inexistente para mensaje de cliente");
            return new ResponseEntity<>(response.toMap(), HttpStatus.CONFLICT);
        }
        if ("".equals(request.getTipo()) || request.getIdCliente() == 0){
            response.put("respuesta", "Campos CLIENTE y TIPO obligatorios");
            return new ResponseEntity<>(response.toMap(), HttpStatus.CONFLICT);
        }
        if ("".equals(request.getMensaje())){
            response.put("respuesta", "Campos MENSAJE no puede estar vacío");
            return new ResponseEntity<>(response.toMap(), HttpStatus.CONFLICT);
        }

        Cliente cliente = clienteService.findById(request.getIdCliente());

        if (cliente == null){
            response.put("respuesta", "El cliente no existe");
            return new ResponseEntity<>(response.toMap(), HttpStatus.NOT_FOUND);
        }
        ConversacionAsesor conversacionAsesor = new ConversacionAsesor();

        conversacionAsesor.setIdCliente(cliente.getIdCliente());
        conversacionAsesor.setCliente((cliente.getNombre().indexOf(" ") == -1 ? cliente.getNombre() :
                cliente.getNombre().substring(0, cliente.getNombre().indexOf(" "))) + ' ' + cliente.getApellidoPaterno());

        // validar mensaje no sea vacío

        conversacionAsesor.setMensaje(request.getMensaje());
        conversacionAsesor.setTipo(request.getTipo());
        //fecha tiene que ser automática
        conversacionAsesor.setFecha(LocalDateTime.now().withNano(0));
        conversacionAsesor.setActivo(true);
        conversacionAsesor.setBascula(false);
        conversacionAsesor.setFolio(cliente.getIdCliente());

        conversacionAsesorService.save(conversacionAsesor);

        response.put("respuesta", "MENSAJE enviado correctamente");
        return new ResponseEntity<>(response.toMap(), HttpStatus.OK);
    }

    @PostMapping("/asesor")
    @ResponseBody
    public ResponseEntity<?> nuevaMensajeAsesor(@RequestBody ConversacionAsesor request){

        JSONObject response = new JSONObject();

        if (!"A".equals(request.getTipo())) {
            response.put("respuesta", "Campo TIPO no válido o inexistente para mensaje de asesor");
            return new ResponseEntity<>(response.toMap(), HttpStatus.CONFLICT);
        }
        if ("".equals(request.getTipo()) || request.getAsesor().length() == 0){
            response.put("respuesta", "Todos los campos son obligatorios");
            return new ResponseEntity<>(response.toMap(), HttpStatus.CONFLICT);
        }
        if (request.getIdCliente() != 0){
            response.put("respuesta", "Valor no válido para IDCLIENTE de asesor");
            return new ResponseEntity<>(response.toMap(), HttpStatus.CONFLICT);
        }
        if (request.getMensaje().length() == 0) {
            response.put("respuesta", "Campo MENSAJE no puede estar vacío");
            return new ResponseEntity<>(response.toMap(), HttpStatus.CONFLICT);
        }
        if (request.getFolio() < 1) {
            response.put("respuesta", "Campo FOLIO no válido");
            return new ResponseEntity<>(response.toMap(), HttpStatus.CONFLICT);
        }
        Cliente cliente = clienteService.findById(request.getFolio());
        if (cliente == null){
            response.put("respuesta", "El cliente no existe");
            return new ResponseEntity<>(response.toMap(), HttpStatus.NOT_FOUND);
        }

        ConversacionAsesor conversacionAsesor = new ConversacionAsesor();
        conversacionAsesor.setIdCliente(request.getIdCliente());
       // conversacionAsesor.setCliente((request.getCliente().indexOf(" ") == -1 ? request.getCliente() :
         //       request.getCliente().substring(0, request.getCliente().indexOf(" "))) + ' ' + cliente.getApellidoPaterno());
        conversacionAsesor.setAsesor(request.getAsesor().toUpperCase());
        conversacionAsesor.setMensaje(request.getMensaje());
        conversacionAsesor.setTipo(request.getTipo());
        //fecha tiene que ser automática
        conversacionAsesor.setFecha(LocalDateTime.now().withNano(0));
        conversacionAsesor.setActivo(true);
        conversacionAsesor.setBascula(false);
        conversacionAsesor.setFolio(cliente.getIdCliente());

        conversacionAsesorService.save(conversacionAsesor);

        response.put("respuesta", "MENSAJE enviado correctamente");
        return new ResponseEntity<>(response.toMap(), HttpStatus.OK);
    }
}


