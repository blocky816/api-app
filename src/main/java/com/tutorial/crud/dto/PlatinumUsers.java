package com.tutorial.crud.dto;

import java.lang.reflect.Array;
import java.util.List;

public class PlatinumUsers {
    private int id;
    private String nombre;
    private String tipo_membresia;
    private List<String> cupones_aplicados;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo_membresia() {
        return tipo_membresia;
    }

    public void setTipo_membresia(String tipo_membresia) {
        this.tipo_membresia = tipo_membresia;
    }

    public List<String> getCupones_aplicados() {
        return cupones_aplicados;
    }

    public void setCupones_aplicados(List<String> cupones_aplicados) {
        this.cupones_aplicados = cupones_aplicados;
    }

    @Override
    public String toString() {
        return "PlatinumUsers{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", tipo_membresia='" + tipo_membresia + '\'' +
                ", cupones_aplicados=" + cupones_aplicados +
                '}';
    }
}
