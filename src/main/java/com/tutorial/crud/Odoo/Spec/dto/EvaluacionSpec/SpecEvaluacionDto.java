package com.tutorial.crud.Odoo.Spec.dto.EvaluacionSpec;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SpecEvaluacionDto {
    public Integer id_evaluacion;
    public String folio;
    public List<Object> partner_id;
    public String id_cliente;
    public String state;
    public String fecha_de_evaluacion_contestado;
    public List<Object> deporte_id;
    public List<LineEvaluation> line_evaluation;

    public Integer getId_evaluacion() {
        return id_evaluacion;
    }

    public void setId_evaluacion(Integer id_evaluacion) {
        this.id_evaluacion = id_evaluacion;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public List<Object> getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(List<Object> partner_id) {
        this.partner_id = partner_id;
    }

    public String getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<LineEvaluation> getLine_evaluation() {
        return line_evaluation;
    }

    public void setLine_evaluation(List<LineEvaluation> line_evaluation) {
        this.line_evaluation = line_evaluation;
    }

    public String getFecha_de_evaluacion_contestado() {
        return fecha_de_evaluacion_contestado;
    }

    public void setFecha_de_evaluacion_contestado(String fecha_de_evaluacion_contestado) {
        this.fecha_de_evaluacion_contestado = fecha_de_evaluacion_contestado;
    }

    public List<Object> getDeporte_id() {
        return deporte_id;
    }

    public void setDeporte_id(List<Object> deporte_id) {
        this.deporte_id = deporte_id;
    }

    public static List<SpecEvaluacionDto> getList(String body) {
        if (body == null || body.isBlank()) {
            return List.of();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return objectMapper.readValue(body, new TypeReference<List<SpecEvaluacionDto>>() {});
        } catch (JsonProcessingException e) {
            return List.of();
        }
    }

    
}