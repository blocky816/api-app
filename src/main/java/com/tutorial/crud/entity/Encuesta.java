package com.tutorial.crud.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "encuestas")
public class Encuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idEncuesta;
    @Column(name = "id_cliente")
    private int idCliente;
    @Column(name = "id_club")
    private int idClub;
    @Column(name = "id_survey", columnDefinition = "integer default 0")
    private int idSurvey;
    @Column(name = "encuestas", columnDefinition = "TEXT")
    String encuestas;
    @Column(name = "fecha_encuestas", columnDefinition = "TIMESTAMP(0) default current_timestamp", insertable = false)
    private LocalDateTime fechaEncuestas;
    @Column(name = "preguntas", columnDefinition = "TEXT")
    String preguntas;
    @Column(name = "fecha_preguntas", columnDefinition = "TIMESTAMP(0)")
    private LocalDateTime fechaPreguntas;
    @Column(name = "respuestas", columnDefinition = "TEXT")
    String respuestas;
    @Column(name = "fecha_respuestas", columnDefinition = "TIMESTAMP(0)")
    private LocalDateTime fechaRespuestas;
    @Column(name = "guardado_odoo", columnDefinition = "boolean default false", insertable = false)
    private boolean guardadoOdoo;

    public Long getIdEncuesta() {
        return idEncuesta;
    }

    public void setIdEncuesta(Long idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdClub() {
        return idClub;
    }

    public void setIdClub(int idClub) {
        this.idClub = idClub;
    }

    public int getIdSurvey() {
        return idSurvey;
    }

    public void setIdSurvey(int idSurvey) {
        this.idSurvey = idSurvey;
    }

    public String getEncuestas() {
        return encuestas;
    }

    public void setEncuestas(String encuestas) {
        this.encuestas = encuestas;
    }

    public LocalDateTime getFechaEncuestas() {
        return fechaEncuestas;
    }

    public void setFechaEncuestas(LocalDateTime fechaEncuestas) {
        this.fechaEncuestas = fechaEncuestas;
    }

    public String getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(String preguntas) {
        this.preguntas = preguntas;
    }

    public LocalDateTime getFechaPreguntas() {
        return fechaPreguntas;
    }

    public void setFechaPreguntas(LocalDateTime fechaPreguntas) {
        this.fechaPreguntas = fechaPreguntas;
    }

    public String getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(String respuestas) {
        this.respuestas = respuestas;
    }

    public LocalDateTime getFechaRespuestas() {
        return fechaRespuestas;
    }

    public void setFechaRespuestas(LocalDateTime fechaRespuestas) {
        this.fechaRespuestas = fechaRespuestas;
    }

    public boolean isGuardadoOdoo() {
        return guardadoOdoo;
    }

    public void setGuardadoOdoo(boolean guardadoOdoo) {
        this.guardadoOdoo = guardadoOdoo;
    }

    @Override
    public String toString() {
        return "Encuesta{" +
                "idEncuesta=" + idEncuesta +
                ", idCliente=" + idCliente +
                ", idClub=" + idClub +
                ", idSurvey=" + idSurvey +
                ", encuestas='" + encuestas + '\'' +
                ", fechaEncuestas=" + fechaEncuestas +
                ", preguntas='" + preguntas + '\'' +
                ", fechaPreguntas=" + fechaPreguntas +
                ", respuestas='" + respuestas + '\'' +
                ", fechaRespuestas=" + fechaRespuestas +
                ", guardadoOdoo=" + guardadoOdoo +
                '}';
    }
}
