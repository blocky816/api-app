package com.tutorial.crud.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tutorial.crud.entity.EjercicioNuevo;

import java.time.LocalDateTime;
import java.util.List;

public class RutinaByCliente {


    private String nombreRutina;
    private String nombreObjetivo;
    private Integer semanas;
    private LocalDateTime inicio;
    private LocalDateTime fin;

    private List<EjercicioNuevoDTO> ejercicios;


    public String getNombreRutina() {
        return nombreRutina;
    }

    public void setNombreRutina(String nombreRutina) {
        this.nombreRutina = nombreRutina;
    }

    public String getNombreObjetivo() {
        return nombreObjetivo;
    }

    public void setNombreObjetivo(String nombreObjetivo) {
        this.nombreObjetivo = nombreObjetivo;
    }

    public Integer getSemanas() {
        return semanas;
    }

    public void setSemanas(Integer semanas) {
        this.semanas = semanas;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public LocalDateTime getFin() {
        return fin;
    }

    public void setFin(LocalDateTime fin) {
        this.fin = fin;
    }

    public List<EjercicioNuevoDTO> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(List<EjercicioNuevoDTO> ejercicios) {
        this.ejercicios = ejercicios;
    }

    @Override
    public String toString() {
        return "RutinaByCliente{" +
                "nombreRutina='" + nombreRutina + '\'' +
                ", nombreObjetivo='" + nombreObjetivo + '\'' +
                ", semanas=" + semanas +
                ", inicio=" + inicio +
                ", fin=" + fin +
                ", ejercicios=" + ejercicios +
                '}';
    }
}
