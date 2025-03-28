package com.tutorial.crud.Odoo.Spec.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class SpecFacturaLinea {
    @JsonProperty("id_line")
    private int idLine;

    @JsonProperty("product_id")
    private ArrayList<Object> productId;

    private double quantity;

    @JsonProperty("cantidad_cosumida_spec")
    private int cantidadConsumidaSpec;

    @JsonProperty("vigencia_qr")
    private String vigenciaQr;

    @JsonProperty("es_pase_qr")
    private Boolean esPaseQr;

    public int getIdLine() {
        return idLine;
    }

    public void setIdLine(int idLine) {
        this.idLine = idLine;
    }

    public ArrayList<Object> getProductId() {
        return productId;
    }

    public void setProductId(ArrayList<Object> productId) {
        this.productId = productId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public int getCantidadConsumidaSpec() {
        return cantidadConsumidaSpec;
    }

    public void setCantidadConsumidaSpec(int cantidadConsumidaSpec) {
        this.cantidadConsumidaSpec = cantidadConsumidaSpec;
    }

    public String getVigenciaQr() {
        return vigenciaQr;
    }

    public void setVigenciaQr(String vigenciaQr) {
        this.vigenciaQr = vigenciaQr;
    }

    public Boolean getEsPaseQr() {
        return esPaseQr;
    }

    public void setEsPaseQr(Boolean esPaseQr) {
        this.esPaseQr = esPaseQr;
    }

    @Override
    public String toString() {
        return "SpecFacturaLinea{" +
                "idLine=" + idLine +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", cantidadConsumidaSpec=" + cantidadConsumidaSpec +
                '}';
    }
}
