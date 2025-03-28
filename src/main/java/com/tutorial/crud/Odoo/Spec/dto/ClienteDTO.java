package com.tutorial.crud.Odoo.Spec.dto;

public class ClienteDTO {
    private Integer idCliente;
    private String peso;
    private String estatura;

    // Constructor
    public ClienteDTO(Integer idCliente, String peso, String estatura) {
        this.idCliente = idCliente;
        this.peso = peso;
        this.estatura = estatura;
    }

    public ClienteDTO() {
    }

    // Getters y Setters
    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getEstatura() {
        return estatura;
    }

    public void setEstatura(String estatura) {
        this.estatura = estatura;
    }
}
