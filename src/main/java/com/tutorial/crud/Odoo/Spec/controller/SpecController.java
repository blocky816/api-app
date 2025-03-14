package com.tutorial.crud.Odoo.Spec.controller;

import com.tutorial.crud.Odoo.Spec.dto.RespuestaDTO;
import com.tutorial.crud.Odoo.Spec.entity.RespuestaFormulario;
import com.tutorial.crud.Odoo.Spec.service.SpecFormsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spec")
@CrossOrigin(origins = "*")
public class SpecController {

    @Autowired
    private SpecFormsService specFormsService;

    @PostMapping("/formulario/responder/{idCliente}")
    public ResponseEntity<List<RespuestaFormulario>> responderFormularioMultiple(@PathVariable int idCliente, @RequestBody List<RespuestaDTO> respuestas) {
        List<RespuestaFormulario> respuestasGuardadas = specFormsService.responderFormularioMultiple(idCliente, respuestas);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuestasGuardadas);
    }

    @GetMapping("/formulario/respuestas/{idCliente}")
    public ResponseEntity<List<RespuestaDTO>> obtenerRespuestasFormulario(
            @PathVariable Integer idCliente) {

        List<RespuestaDTO> respuestas = specFormsService.obtenerRespuestasFormulario(idCliente);
        return ResponseEntity.ok(respuestas);
    }
}
