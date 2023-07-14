package com.tutorial.crud.dto;

import com.tutorial.crud.entity.EjercicioNuevo;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ArrayPrueba2Nuevo {

    private int id;
    private int idCliente;
    private int idPlantilla;
    private String nombreRutina;
    private String nombreObjetivo;
    private int semanas;
    private String tipoPlantilla;

    private String comentarios;

    private String segmento;

    private ArrayList<EjercicioNuevo> ejercicios;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getIdCliente() { return idCliente; }

    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public int getIdPlantilla() { return idPlantilla; }

    public void setIdPlantilla(int idPlantilla) { this.idPlantilla = idPlantilla; }

    public String getNombreRutina() { return nombreRutina; }

    public void setNombreRutina(String nombreRutina) { this.nombreRutina = nombreRutina; }

    public String getNombreObjetivo() { return nombreObjetivo; }

    public void setNombreObjetivo(String nombreObjetivo) { this.nombreObjetivo = nombreObjetivo; }

    public int getSemanas() { return semanas; }

    public void setSemanas(int semanas) { this.semanas = semanas; }

    public String getTipoPlantilla() { return tipoPlantilla; }

    public void setTipoPlantilla(String tipoPlantilla) { this.tipoPlantilla = tipoPlantilla; }

    public ArrayList<EjercicioNuevo> getEjercicios() { return ejercicios; }

    public void setEjercicios(ArrayList<EjercicioNuevo> ejercicios) { this.ejercicios = ejercicios; }

    public String getComentarios() { return comentarios; }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getSegmento() { return segmento; }

    public void setSegmento(String segmento) { this.segmento = segmento; }

    @Override
    public String toString() {
        return "ArrayPrueba2Nuevo{" +
                "id=" + id +
                ", idCliente=" + idCliente +
                ", idPlantilla=" + idPlantilla +
                ", nombreRutina='" + nombreRutina + '\'' +
                ", nombreObjetivo='" + nombreObjetivo + '\'' +
                ", semanas=" + semanas +
                ", tipoPlantilla='" + tipoPlantilla + '\'' +
                ", comentarios='" + comentarios + '\'' +
                ", segmento='" + segmento + '\'' +
                ", ejercicios=" + ejercicios +
                '}';
    }
}
