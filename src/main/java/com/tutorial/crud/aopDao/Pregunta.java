package com.tutorial.crud.aopDao;

public class Pregunta {

    private String pregunta;
    private String respuesta;

    public Pregunta(String pregunta, String respuesta) {
        super();
        this.pregunta  = pregunta;
        this.respuesta = respuesta;
    }

    public String getPregunta() { return pregunta; }

    public void setPregunta(String pregunta) { this.pregunta = pregunta; }

    public String getRespuesta() { return respuesta; }

    public void setRespuesta(String respuesta) { this.respuesta = respuesta; }
}
