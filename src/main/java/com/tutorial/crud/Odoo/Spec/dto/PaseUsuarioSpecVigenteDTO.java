package com.tutorial.crud.Odoo.Spec.dto;

import java.time.LocalDateTime;

public class PaseUsuarioSpecVigenteDTO {
    private String concepto;
    private LocalDateTime fechaVigencia;
    private int cantidad;
    private int disponibles;
    private int consumido;
    private boolean activo;

    public PaseUsuarioSpecVigenteDTO(String concepto, LocalDateTime fechaVigencia, int cantidad, int disponibles, boolean activo) {
        this.concepto = concepto;
        this.fechaVigencia = fechaVigencia;
        this.cantidad = cantidad;
        this.disponibles = disponibles;
        this.consumido = cantidad - disponibles;
        this.activo = activo;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public LocalDateTime getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(LocalDateTime fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getDisponibles() {
        return disponibles;
    }

    public void setDisponibles(int disponibles) {
        this.disponibles = disponibles;
    }

    public int getConsumido() {
        return consumido;
    }

    public void setConsumido(int consumido) {
        this.consumido = consumido;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "PaseUsuarioSpecVigenteDTO{" +
                "concepto='" + concepto + '\'' +
                ", fechaVigencia=" + fechaVigencia +
                ", cantidad=" + cantidad +
                ", disponibles=" + disponibles +
                ", consumido=" + consumido +
                ", activo=" + activo +
                '}';
    }
}
