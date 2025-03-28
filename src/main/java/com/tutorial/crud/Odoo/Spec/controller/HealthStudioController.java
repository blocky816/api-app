package com.tutorial.crud.Odoo.Spec.controller;


import com.tutorial.crud.Odoo.Spec.dto.PaginacionDTO;
import com.tutorial.crud.Odoo.Spec.dto.PaseHealthStudioDTO;
import com.tutorial.crud.Odoo.Spec.dto.PaseReporteDTO;
import com.tutorial.crud.Odoo.Spec.service.PaseHealthStudioConsumidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/health-studio")
@CrossOrigin(origins = "*")
public class HealthStudioController {
    @Autowired
    private PaseHealthStudioConsumidoService service;

    @GetMapping("/paseQR/{idCliente}")
    public ResponseEntity<?> obtenerPasesQR(@PathVariable Integer idCliente) {
        PaseHealthStudioDTO pases = service.obtenerPasesQR(idCliente);

        if (pases != null)
            return ResponseEntity.ok(pases);
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("msg", "No hay pases disponibles"));
    }

    @GetMapping("/consumos")
    public ResponseEntity<PaginacionDTO> obtenerConsumos(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        PaginacionDTO resultado = service.getConsumosPaged(startDate, endDate, page, size);
        return ResponseEntity.ok(resultado);
    }
}
