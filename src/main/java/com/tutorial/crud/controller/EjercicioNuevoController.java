package com.tutorial.crud.controller;

import com.tutorial.crud.entity.EjercicioNuevo;
import com.tutorial.crud.service.EjercicioNuevoService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/ejercicios")
@CrossOrigin(origins = "*")
public class EjercicioNuevoController {

    @Autowired
    EjercicioNuevoService ejercicioNuevoService;

    @PostMapping("/nuevo")
    @ResponseBody
    public ResponseEntity<?> nuevoEjercicio(@RequestBody EjercicioNuevo request){

        JSONObject response = new JSONObject();

        if (request.getDia() == 0 || request.getClub() == 0 || request.getNivel().length() == 0 ||
        request.getTipo().length() == 0 || request.getGeneral() == null || request.getImagen().length() == 0 || request.getNombre().length() == 0){
            response.put("respuesta", "Todos los campos son obligatorios");
            return new ResponseEntity<>(response.toMap(), HttpStatus.CONFLICT);
        }
        if (request.getDia() < 1 && request.getDia() > 7){
            response.put("respuesta", "DIA no válido");
            return new ResponseEntity<>(response.toMap(), HttpStatus.CONFLICT);
        }
        if (request.getClub() < 1 || request.getClub() > 5) {
            response.put("respuesta", "CLUB no válido");
            return new ResponseEntity<>(response.toMap(), HttpStatus.CONFLICT);
        }
        EjercicioNuevo ejercicioNuevo = request;
        ejercicioNuevoService.save(ejercicioNuevo);

        response.put("respuesta", "Ejercicio guardado correctamente");
        return new ResponseEntity<>(response.toMap(), HttpStatus.OK);
        //return new ResponseEntity<>(ejercicioNuevo, HttpStatus.OK);
    }

    @PutMapping("/actualizarById")
    @ResponseBody
    public ResponseEntity<?> actualizarEjercicio(@RequestBody EjercicioNuevo request){

        JSONObject response = new JSONObject();
        if (request.getId() < 1){
            response.put("respuesta", "ID de ejercicio obligatorio");
            return new ResponseEntity<>(response.toMap(), HttpStatus.CONFLICT);
        }

        if (request.getDia() < 1 && request.getDia() > 7){
            response.put("respuesta", "DIA no válido");
            return new ResponseEntity<>(response.toMap(), HttpStatus.CONFLICT);
        }
        if (request.getClub() < 1 || request.getClub() > 5) {
            response.put("respuesta", "CLUB no válido");
            return new ResponseEntity<>(response.toMap(), HttpStatus.CONFLICT);
        }

        Optional<EjercicioNuevo> ejercicio = ejercicioNuevoService.findById(request.getId());

        if (ejercicio.isPresent() && ejercicio.get().getActivo() == true){
            EjercicioNuevo ejercicioActualizar = ejercicio.get();
            ejercicioActualizar.setClub(request.getClub());
            ejercicioActualizar.setDia(request.getDia());
            ejercicioActualizar.setNivel(request.getNivel());
            ejercicioActualizar.setTipo(request.getTipo());
            ejercicioActualizar.setGeneral(request.getGeneral());
            ejercicioActualizar.setImagen(request.getImagen());
            ejercicioActualizar.setNombre(request.getNombre());
            ejercicioNuevoService.save(ejercicioActualizar);

            response.put("respuesta", "Ejercicio actualizado correctamente");
            return new ResponseEntity<>(response.toMap(), HttpStatus.OK);
        }else {
            response.put("respuesta", "Ejercicio no existe");
            return new ResponseEntity<>(response.toMap(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/deleteById")
    @ResponseBody
    public ResponseEntity<?> desactivarEjercicio(@RequestParam int request){

        JSONObject response = new JSONObject();

        Optional<EjercicioNuevo> ejercicio = ejercicioNuevoService.findById(request);
        if (ejercicio.isPresent()){
            if (ejercicio.get().getActivo() == true){
                ejercicio.get().setActivo(false);
                ejercicioNuevoService.save(ejercicio.get());

                response.put("respuesta", "Ejercicio eliminado correctamente");
                return new ResponseEntity<>(response.toMap(), HttpStatus.OK);
            }else {
                response.put("respuesta", "Ejercicio no se puede eliminar");
                return new ResponseEntity<>(response.toMap(), HttpStatus.OK);
            }
        }else {
            response.put("respuesta", "Ejercicio no existe");
            return new ResponseEntity<>(response.toMap(), HttpStatus.NOT_FOUND);
        }
    }

}
