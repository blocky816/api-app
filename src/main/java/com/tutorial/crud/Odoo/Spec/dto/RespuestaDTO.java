package com.tutorial.crud.Odoo.Spec.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RespuestaDTO {
    private Long idRespuesta;
    private Long idPregunta;
    private String pregunta;
    private String respuesta;
    private Integer idCliente;
    private String peso;
    private String estatura;

    public RespuestaDTO(Long idRespuesta, Long idPregunta, String pregunta, String respuesta, Integer idCliente, String peso, String estatura) {
        this.idRespuesta = idRespuesta;
        this.idPregunta = idPregunta;
        this.pregunta = pregunta;
        this.respuesta = respuesta;
        this.idCliente = idCliente;
        this.peso = peso;
        this.estatura = estatura;
    }

    public RespuestaDTO() {
    }

    @JsonCreator
    public RespuestaDTO(@JsonProperty("pregunta") String pregunta, @JsonProperty("respuesta") String respuesta) {
        this.pregunta = pregunta;
        this.respuesta = respuesta;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public Long getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(Long idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public Long getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(Long idPregunta) {
        this.idPregunta = idPregunta;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getEstatura() {
        return estatura;
    }

    public void setEstatura(String estatura) {
        this.estatura = estatura;
    }
}
