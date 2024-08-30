package com.tutorial.crud.controller;

import com.tutorial.crud.dto.DirectDebitRequestDTO;
import com.tutorial.crud.service.DirectDebitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping({"/directDebit"})
@CrossOrigin(origins = "*")
public class DirectDebitController {

    private static final Logger logger = Logger.getLogger(DirectDebitController.class.getName());
    @Autowired
    DirectDebitService directDebitService;

    @PostMapping("/create")
    public ResponseEntity<?> createDirectDebit(@RequestBody DirectDebitRequestDTO request) {
        try {
            logger.info("Request from Webhook domiciliar cliente: " + request);
            directDebitService.createDirectDebit(
                    request.getCustomerID(),
                    request.getCardNumber(),
                    request.getCardType()
            );
            logger.info("Domiciliacion guardada correctamente para cliente: " + request.getCustomerID());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            logger.info("Error tratando de guardar datos de domiciliacion: " + request + " Error: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/cancel")
    public ResponseEntity<?> cancelarDomiciliacion(@RequestBody DirectDebitRequestDTO request) {
        try {
            logger.info("Solicitud de cancelacion de domiciliacion: " + request);
            directDebitService.cancelDirectDebit(
                    request.getCustomerID(),
                    request.getPerformedBy(),
                    request.getReason()
            );
            logger.info("Cancelacion exitosa del cliente: " + request.getCustomerID());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.info("Error tratando de cancelar domiciliacion: " + request + " Error: " + e.getMessage());
            return new ResponseEntity<>("{\"respuesta\":\"domiciliacion no encontrada\"}", HttpStatus.NOT_FOUND);
        }
    }
}
