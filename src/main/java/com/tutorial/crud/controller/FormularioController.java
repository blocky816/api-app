package com.tutorial.crud.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tutorial.crud.dto.BodyFormulario;
import com.tutorial.crud.dto.FormularioDTO;
import com.tutorial.crud.entity.Formulario;
import com.tutorial.crud.entity.FormularioCliente;
import com.tutorial.crud.service.FormularioService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/formularios")
@CrossOrigin(origins = "*")
public class FormularioController {

    @Autowired
    FormularioService formularioService;

    @PostMapping("")
    @ResponseBody
    public ResponseEntity<?> nuevoFormulario(@RequestBody BodyFormulario body){
        try {
            formularioService.save(body);
            return new ResponseEntity<>(new JSONObject("{'respuesta': 'Creado correctamente'}").toMap(), HttpStatus.OK);
        } catch (NullPointerException e){
            System.out.println("Exception message: " + e.getMessage());
            return new ResponseEntity<>(new JSONObject("{'respuesta':" + e.getMessage() + "}").toMap(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println("Exception message: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(new JSONObject("{'respuesta': 'Error interno'}").toMap(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{folio}")
    @ResponseBody
    public ResponseEntity<?> getFormulario(@PathVariable int folio, @RequestBody BodyFormulario body){
        try {
            formularioService.updateFormulario(body, folio);
            return new ResponseEntity<>(new JSONObject("{'respuesta': 'Actualizado correctamente'}").toMap(), HttpStatus.OK);
        } catch (NullPointerException e){
            System.out.println("Exception message: " + e.getMessage());
            return new ResponseEntity<>(new JSONObject("{'respuesta':" + e.getMessage() + "}").toMap(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println("Exception message: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(new JSONObject("{'respuesta': 'Error interno'}").toMap(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{folio}")
    @ResponseBody
    public ResponseEntity<?> getFormulario(@PathVariable int folio){
        try {
            FormularioDTO  formularioDTO = formularioService.findByFolio(folio);
            return new ResponseEntity<>(formularioDTO, HttpStatus.OK);
        } catch (NullPointerException e){
            System.out.println("Exception message: " + e.getMessage());
            return new ResponseEntity<>(new JSONObject("{'respuesta':" + e.getMessage() + "}").toMap(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println("Exception message: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(new JSONObject("{'respuesta': 'Error interno'}").toMap(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{folio}")
    @ResponseBody
    public ResponseEntity<?> deleteFormulario(@PathVariable int folio){
        try {
            formularioService.deleteByFolio(folio);
            return new ResponseEntity<>(new JSONObject("{'respuesta': 'Eliminado correctamente'}").toMap(), HttpStatus.OK);
        } catch (NullPointerException e){
            System.out.println("Exception message: " + e.getMessage());
            return new ResponseEntity<>(new JSONObject("{'respuesta':" + e.getMessage() + "}").toMap(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println("Exception message: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(new JSONObject("{'respuesta':" + e.getMessage() + "}").toMap(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*---------------------------------------- CLIENTE -------------------------------------------------*/
    @PostMapping("/asignarCliente")
    @ResponseBody
    public ResponseEntity<?> asignarFormulario(@RequestBody ObjectNode request){
        try {
            formularioService.asignarCliente(request);
            return new ResponseEntity<>(new JSONObject("{'respuesta': 'Asignado correctamente'}").toMap(), HttpStatus.OK);
        } catch (NullPointerException e){
            System.out.println("Exception message: " + e.getMessage());
            return new ResponseEntity<>(new JSONObject("{'respuesta':" + e.getMessage() + "}").toMap(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println("Exception message: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(new JSONObject("{'respuesta':" + e.getMessage() + "}").toMap(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getByCliente/{id}")
    @ResponseBody
    public ResponseEntity<?> getFormularioByCliente(@PathVariable int id){
        try {
            FormularioDTO  formularioDTO = formularioService.findByCliente(id);
            return new ResponseEntity<>(formularioDTO, HttpStatus.OK);
        } catch (NullPointerException e){
            if (e.getMessage() == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            System.out.println("Exception message: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println("Exception message: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/responder/{idCliente}")
    @ResponseBody
    public ResponseEntity<?> responderFormulario(@PathVariable Integer idCliente, @RequestBody FormularioDTO body){
        try {
            formularioService.responderForm(idCliente, body);
            return new ResponseEntity<>(new JSONObject("{'respuesta': 'Form completado correctamente'}").toMap(), HttpStatus.OK);
        } catch (NullPointerException e){
            System.out.println("Exception message: " + e.getMessage());
            return new ResponseEntity<>(new JSONObject("{'respuesta':" + e.getMessage() + "}").toMap(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println("Exception message: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(new JSONObject("{'respuesta': 'Error interno'}").toMap(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
