package com.tutorial.crud.Odoo.Spec.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SpecFacturaResponse {
    @JsonProperty("id_factura")
    private int idFactura;

    private String name;

    @JsonProperty("partner_id")
    private List<String> partnerId;

    private String state;

    @JsonProperty("payment_state")
    private String paymentState;

    @JsonProperty("invoice_line")
    private List<SpecFacturaLinea> invoiceLine;

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(List<String> partnerId) {
        this.partnerId = partnerId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(String paymentState) {
        this.paymentState = paymentState;
    }

    public List<SpecFacturaLinea> getInvoiceLine() {
        return invoiceLine;
    }

    public void setInvoiceLine(List<SpecFacturaLinea> invoiceLine) {
        this.invoiceLine = invoiceLine;
    }

    @Override
    public String toString() {
        return "SpecFacturaResponse{" +
                "idFactura=" + idFactura +
                ", name='" + name + '\'' +
                ", partnerId=" + partnerId +
                ", state='" + state + '\'' +
                ", paymentState='" + paymentState + '\'' +
                ", invoiceLine=" + invoiceLine +
                '}';
    }
}
