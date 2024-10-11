package com.tutorial.crud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class InvoiceRequest {
    @JsonProperty("cliente_id_odoo")
    private int clienteIdOdoo;
    @JsonProperty("branch_id")
    private int branchId;
    @JsonProperty("array_invoice_line")
    private List<InvoiceLine> arrayInvoiceLine;

    // Getters y Setters


    public int getClienteIdOdoo() {
        return clienteIdOdoo;
    }

    public void setClienteIdOdoo(int clienteIdOdoo) {
        this.clienteIdOdoo = clienteIdOdoo;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public List<InvoiceLine> getArrayInvoiceLine() {
        return arrayInvoiceLine;
    }

    public void setArrayInvoiceLine(List<InvoiceLine> arrayInvoiceLine) {
        this.arrayInvoiceLine = arrayInvoiceLine;
    }

    @Override
    public String toString() {
        return "InvoiceRequest{" +
                "clienteIdOdoo=" + clienteIdOdoo +
                ", branchId=" + branchId +
                ", arrayInvoiceLine=" + arrayInvoiceLine +
                '}';
    }

    public static class InvoiceLine {
        @JsonProperty("product_id")
        private int productId;
        @JsonProperty("cantidad")
        private int cantidad;
        @JsonProperty("precio_unitario")
        private double precioUnitario;

        // Getters y Setters

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public int getCantidad() {
            return cantidad;
        }

        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }

        public double getPrecioUnitario() {
            return precioUnitario;
        }

        public void setPrecioUnitario(double precioUnitario) {
            this.precioUnitario = precioUnitario;
        }

        @Override
        public String toString() {
            return "InvoiceLine{" +
                    "productId=" + productId +
                    ", cantidad=" + cantidad +
                    ", precioUnitario=" + precioUnitario +
                    '}';
        }
    }
}
