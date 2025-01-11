package com.tutorial.crud.controller;

import com.tutorial.crud.dto.AsistenciaDTO;
import com.tutorial.crud.dto.DeportistaDTO;
import com.tutorial.crud.dto.EvaluacionRequest;
import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.exception.ResourceNotFoundException;
import com.tutorial.crud.service.DeportistaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deportista")
public class DeportistaController {

    private final DeportistaService deportistaService;

    //@Autowired
    public DeportistaController(DeportistaService deportistaService) {
        this.deportistaService = deportistaService;
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<List<DeportistaDTO>> obtenerDeportistas(@PathVariable int idCliente) throws ResourceNotFoundException {
        return ResponseEntity.ok(deportistaService.obtenerDeportistas(idCliente));
    }

    @PostMapping("/evaluaciones")
    public ResponseEntity<?> obtenerEvaluacionesDeportista(@RequestBody EvaluacionRequest request) {
        try {
            ResponseEntity<String> response = deportistaService.getEvaluacionesDeportista(request);
            return response;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron evaluaciones disponibles para: " + request.getDeportista_id());
        }
    }

    @PostMapping("/asistencias")
    public ResponseEntity<?> obtenerAsistenciasDeportista(@RequestBody EvaluacionRequest request) {
        try {
            ResponseEntity<List<AsistenciaDTO>> response = deportistaService.getAsistenciasDeportista(request);
            return response;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron asistencias disponibles para: " + request.getDeportista_id());
        }
    }

    @PatchMapping("/{idCliente}")
    public ResponseEntity<?> darConsentimiento(@PathVariable("idCliente") int idCliente) {
        Cliente cliente = deportistaService.darConsentimiento(idCliente);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
