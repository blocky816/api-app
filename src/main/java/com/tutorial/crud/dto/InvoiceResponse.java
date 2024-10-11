package com.tutorial.crud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class InvoiceResponse {
    @JsonProperty("id_venta_detalle")
    private int idVentaDetalle;
    @JsonProperty("id_cliente_odoo")
    private int idClienteOdoo;
    @JsonProperty("id_cliente")
    private String idCliente;
    @JsonProperty("productos")
    private List<ProductResponse> productos;

    public int getIdVentaDetalle() {
        return idVentaDetalle;
    }

    public void setIdVentaDetalle(int idVentaDetalle) {
        this.idVentaDetalle = idVentaDetalle;
    }

    public int getIdClienteOdoo() {
        return idClienteOdoo;
    }

    public void setIdClienteOdoo(int idClienteOdoo) {
        this.idClienteOdoo = idClienteOdoo;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public List<ProductResponse> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductResponse> productos) {
        this.productos = productos;
    }

    @Override
    public String toString() {
        return "InvoiceResponse{" +
                "idVentaDetalle=" + idVentaDetalle +
                ", idClienteOdoo=" + idClienteOdoo +
                ", idCliente='" + idCliente + '\'' +
                ", productos=" + productos +
                '}';
    }
}
