package com.tutorial.crud.Odoo.Spec.dto;

public class ClientePase {
    private String nombre;
    private byte[] foto;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}
