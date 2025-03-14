package com.tutorial.crud.Odoo.Spec.dto;

public class RespuestaDTO {
    private Long idRespuesta;
    private Long idPregunta;
    private String pregunta;
    private String respuesta;
    private Integer idCliente;

    public RespuestaDTO(Long idRespuesta, Long idPregunta, String pregunta, String respuesta, Integer idCliente) {
        this.idRespuesta = idRespuesta;
        this.idPregunta = idPregunta;
        this.pregunta = pregunta;
        this.respuesta = respuesta;
        this.idCliente = idCliente;
    }

    public RespuestaDTO() {
    }

    public RespuestaDTO(String pregunta, String respuesta) {
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
}
