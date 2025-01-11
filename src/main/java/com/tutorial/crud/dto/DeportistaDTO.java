package com.tutorial.crud.dto;

public class DeportistaDTO {
    private int id_deportista;
    private int deporte_id;
    private String nombre_deportista;
    private String id_cliente_deportista;
    private String nombre_deporte;
    private String grupo_deportista;
    private Boolean profesionalizacion;
    private byte[] imagen;
    private int edad;

    // Getters y Setters
    public int getId_deportista() {
        return id_deportista;
    }

    public void setId_deportista(int id_deportista) {
        this.id_deportista = id_deportista;
    }

    public String getNombre_deportista() {
        return nombre_deportista;
    }

    public void setNombre_deportista(String nombre_deportista) {
        this.nombre_deportista = nombre_deportista;
    }

    public String getId_cliente_deportista() {
        return id_cliente_deportista;
    }

    public void setId_cliente_deportista(String id_cliente_deportista) {
        this.id_cliente_deportista = id_cliente_deportista;
    }

    public String getNombre_deporte() {
        return nombre_deporte;
    }

    public void setNombre_deporte(String nombre_deporte) {
        this.nombre_deporte = nombre_deporte;
    }

    public String getGrupo_deportista() {
        return grupo_deportista;
    }

    public void setGrupo_deportista(String grupo_deportista) {
        this.grupo_deportista = grupo_deportista;
    }

    public Boolean getProfesionalizacion() {
        return profesionalizacion;
    }

    public void setProfesionalizacion(Boolean profesionalizacion) {
        this.profesionalizacion = profesionalizacion;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public int getDeporte_id() { return deporte_id; }

    public void setDeporte_id(int deporte_id) { this.deporte_id = deporte_id; }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
