package com.tutorial.crud.dto;

public class MonthlyClassDTO {
    private String nombre;
    private Long id;
    private int uso_maximo;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getUso_maximo() {
        return uso_maximo;
    }

    public void setUso_maximo(int uso_maximo) {
        this.uso_maximo = uso_maximo;
    }
}
