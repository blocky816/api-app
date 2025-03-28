package com.tutorial.crud.Odoo.Spec.dto;

import java.time.LocalDateTime;

public class PaseReporteDTO {
    private int idCliente;
    private String nombreCompleto;
    private String concepto;
    private LocalDateTime fechaConsumo;
    private String consumidoPor;

    public PaseReporteDTO(int idCliente, String nombreCompleto, String concepto, LocalDateTime fechaConsumo, String consumidoPor) {
        this.idCliente = idCliente;
        this.nombreCompleto = nombreCompleto;
        this.concepto = concepto;
        this.fechaConsumo = (fechaConsumo != null) ? fechaConsumo.withNano(0) : null;
        this.consumidoPor = consumidoPor;
    }

    // Getters y setters

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public LocalDateTime getFechaConsumo() {
        return fechaConsumo;
    }

    public void setFechaConsumo(LocalDateTime fechaConsumo) {
        this.fechaConsumo = fechaConsumo;
    }

    public String getConsumidoPor() {
        return consumidoPor;
    }

    public void setConsumidoPor(String consumidoPor) {
        this.consumidoPor = consumidoPor;
    }
}
