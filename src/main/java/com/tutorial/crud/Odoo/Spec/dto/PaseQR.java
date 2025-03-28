package com.tutorial.crud.Odoo.Spec.dto;


import java.time.LocalDateTime;

public class PaseQR {

    private String concepto;
    private Integer disponibles;
    private Integer idVentaDetalle;
    private ClientePase cliente;
    private LocalDateTime fechaVigencia;

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Integer getDisponibles() {
        return disponibles;
    }

    public void setDisponibles(Integer disponibles) {
        this.disponibles = disponibles;
    }

    public Integer getIdVentaDetalle() {
        return idVentaDetalle;
    }

    public void setIdVentaDetalle(Integer idVentaDetalle) {
        this.idVentaDetalle = idVentaDetalle;
    }

    public ClientePase getCliente() {
        return cliente;
    }

    public void setCliente(ClientePase cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(LocalDateTime fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }

    @Override
    public String toString() {
        return "PaseQR{" +
                "concepto='" + concepto + '\'' +
                ", disponibles=" + disponibles +
                ", idVentaDetalle=" + idVentaDetalle +
                ", cliente=" + cliente +
                ", fechaVigencia=" + fechaVigencia +
                '}';
    }
}
