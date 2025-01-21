package com.tutorial.crud.controller;

import com.tutorial.crud.dto.CitaAsesorRequest;
import com.tutorial.crud.entity.CitaAsesorDeportivo;
import com.tutorial.crud.exception.ErrorResponse;
import com.tutorial.crud.service.CitaAsesorDeportivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/asesor")
@CrossOrigin(origins = "*")
public class CitaAsesorController {
    @Autowired
    private CitaAsesorDeportivoService citaAsesorDeportivoService;

    @PostMapping("/programar_cita")
    public ResponseEntity<?> programarCita(@Valid @RequestBody CitaAsesorRequest citaRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return handleValidationErrors(bindingResult);
        }

        // Llamamos al servicio para programar la cita
        CitaAsesorDeportivo cita = citaAsesorDeportivoService.programarCita(
                citaRequest.getIdCliente(), citaRequest.getFecha(), citaRequest.getHora(), citaRequest.getNombreAsesor());

        // Responder con la cita creada
        return ResponseEntity.ok(cita);
    }

    private ResponseEntity<?> handleValidationErrors(BindingResult bindingResult) {
        StringBuilder errorMessages = new StringBuilder("Errores en la solicitud:");
        for (FieldError error : bindingResult.getFieldErrors()) {
            errorMessages.append(" Campo '")
                    .append(error.getField())
                    .append("': ")
                    .append(error.getDefaultMessage())
                    .append("; ");
        }
        System.out.println("Errores de validación: " + errorMessages.toString());  // Log para revisar si se están capturando los errores

        return new ResponseEntity<>(new ErrorResponse(
                LocalDateTime.now(), HttpStatus.BAD_REQUEST.toString(),
                errorMessages.toString(), "Datos inválidos en la solicitud"),
                HttpStatus.BAD_REQUEST);
    }
}
