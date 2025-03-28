package com.tutorial.crud.Odoo.Spec.dto;

import java.util.List;

public class RespuestasRequestDTO {
    private ClienteDTO cliente;
    private List<RespuestaDTO> formulario;

    // Constructor
    public RespuestasRequestDTO(ClienteDTO cliente, List<RespuestaDTO> formulario) {
        this.cliente = cliente;
        this.formulario = formulario;
    }

    public RespuestasRequestDTO() {
    }

    // Getters y Setters
    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public List<RespuestaDTO> getFormulario() {
        return formulario;
    }

    public void setFormulario(List<RespuestaDTO> formulario) {
        this.formulario = formulario;
    }
}
