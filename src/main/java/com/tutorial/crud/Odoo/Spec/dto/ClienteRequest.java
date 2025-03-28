package com.tutorial.crud.Odoo.Spec.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClienteRequest {
    @JsonProperty("id_cliente")
    private String idCliente;

    // Constructor, getters y setters
    public ClienteRequest(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }
}
