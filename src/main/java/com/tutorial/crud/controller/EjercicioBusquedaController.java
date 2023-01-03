package com.tutorial.crud.controller;

import com.tutorial.crud.entity.EjercicioBusqueda;
import com.tutorial.crud.service.EjercicioBusquedaService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ejercicios_busqueda")
@CrossOrigin(origins = "*")
public class EjercicioBusquedaController {

    @Autowired
    EjercicioBusquedaService ejercicioBusquedaService;

    @PostMapping("")
    @ResponseBody
    public ResponseEntity<?> nuevoEjercicioBusqueda(@RequestBody EjercicioBusqueda request){
        try {
            ejercicioBusquedaService.save(request);
            return new ResponseEntity<>(new JSONObject("{'respuesta': 'Creado correctamente'}").toMap(), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Exception message: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(new JSONObject("{'respuesta': 'Error interno'}").toMap(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> actualizarEjercicioBusqueda(@PathVariable int id, @RequestBody EjercicioBusqueda request) {
        try {
            ejercicioBusquedaService.updateEjercicioBusqueda(id, request);
            return new ResponseEntity<>(new JSONObject("{'respuesta': 'Actualizado correctamente'}").toMap(), HttpStatus.OK);
        } catch (NullPointerException e){
            System.out.println("Exception message: " + e.getMessage());
            return new ResponseEntity<>(new JSONObject("{'respuesta': 'El ejercicio no existe'}").toMap(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            System.out.println("Exception message: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(new JSONObject("{'respuesta': 'Error interno'}").toMap(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> actualizarEjercicioBusqueda(@PathVariable int id) {
        try {
            EjercicioBusqueda ejercicio = ejercicioBusquedaService.findById(id);
            return new ResponseEntity<>(ejercicio, HttpStatus.OK);
        } catch (NullPointerException e){
            System.out.println("Exception message: " + e.getMessage());
            return new ResponseEntity<>(new JSONObject("{'respuesta': 'El ejercicio no existe'}").toMap(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println("Exception message: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(new JSONObject("{'respuesta': 'Error interno'}").toMap(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<?> obtenerEjerciciosActivos() {
        try {
            List<EjercicioBusqueda> ejercicios = ejercicioBusquedaService.findAll();
            return new ResponseEntity<>(ejercicios, HttpStatus.OK);
        } catch (NullPointerException e){
            System.out.println("Exception message: " + e.getMessage());
            return new ResponseEntity<>(new JSONObject("{'respuesta': 'El ejercicio no existe'}").toMap(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println("Exception message: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(new JSONObject("{'respuesta': 'Error interno'}").toMap(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}") //Mapeo del método DELETE
    public @ResponseBody ResponseEntity<?> eliminarEjercicio(@PathVariable int id) {
        try {
            ejercicioBusquedaService.deleteEjercicioBusqueda(id);
            return new ResponseEntity<>(new JSONObject("{'respuesta': 'El ejercicio se elimino correctamente'}").toMap(), HttpStatus.OK);
        } catch (NullPointerException e){
            System.out.println("Exception message: " + e.getMessage());
            return new ResponseEntity<>(new JSONObject("{'respuesta': 'El ejercicio no existe'}").toMap(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println("Exception message: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(new JSONObject("{'respuesta': 'Error interno'}").toMap(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
