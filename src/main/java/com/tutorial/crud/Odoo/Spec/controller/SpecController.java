package com.tutorial.crud.Odoo.Spec.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorial.crud.Odoo.Spec.dto.EvaluacionSpec.ReporteEvaluacion;
import com.tutorial.crud.Odoo.Spec.dto.EvaluacionSpec.SpecEvaluacionDto;
import com.tutorial.crud.Odoo.Spec.dto.RespuestaDTO;
import com.tutorial.crud.Odoo.Spec.entity.RespuestaFormulario;
import com.tutorial.crud.Odoo.Spec.service.SpecEvaluacionService;
import com.tutorial.crud.Odoo.Spec.service.SpecFormsService;

@RestController
@RequestMapping("/spec")
@CrossOrigin(origins = "*")
public class SpecController {

    @Autowired
    private SpecFormsService specFormsService;

    @Autowired
    private SpecEvaluacionService specEvaluacionService;

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

    //Endpoint para obtener datos de evaluacion
    @GetMapping("/evaluacion/{idCliente}")
    public ResponseEntity<?> obtenerEvaluacion(@PathVariable Integer idCliente) {

        try {
            var respuestas = specEvaluacionService.obtenerEvaluacion(idCliente);
            if (respuestas.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("msg", "No se encontraron evaluaciones"));
            }
            // get ultima evaluacion that state == done and id is the max
            Optional<SpecEvaluacionDto> ultimaEvaluacion = respuestas.stream().filter(e -> e.getState().equals("validado")).max((e1, e2) -> e1.getId_evaluacion() - e2.getId_evaluacion());
            if(ultimaEvaluacion.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("msg", "No se encontraron evaluaciones completadas"));
            }
            ReporteEvaluacion reporte = specEvaluacionService.obtenerReporte(ultimaEvaluacion.get());
            return ResponseEntity.ok(reporte);
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("msg", "Error al obtener evaluaciones"));
        }
    }
}
