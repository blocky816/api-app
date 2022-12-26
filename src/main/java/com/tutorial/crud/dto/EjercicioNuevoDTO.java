package com.tutorial.crud.dto;

public class EjercicioNuevoDTO {

    private int id;
    private String nombre;
    private int dia;
    private int club;
    private String nivel;
    private String tipo;
    private String imagen;

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

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getClub() {
        return club;
    }

    public void setClub(int club) {
        this.club = club;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "EjercicioNuevoDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", dia=" + dia +
                ", club=" + club +
                ", nivel='" + nivel + '\'' +
                ", tipo='" + tipo + '\'' +
                ", imagen='" + imagen + '\'' +
                '}';
    }
}
