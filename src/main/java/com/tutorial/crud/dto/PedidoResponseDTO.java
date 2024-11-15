package com.tutorial.crud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PedidoResponseDTO {
    @JsonProperty("NoPedido")
    private int noPedido;
    @JsonProperty("Detalle")
    private List<Detalle> detalle;

    public int getNoPedido() {
        return noPedido;
    }

    public void setNoPedido(int noPedido) {
        this.noPedido = noPedido;
    }

    public List<Detalle> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<Detalle> detalle) {
        this.detalle = detalle;
    }

    public static class Detalle {
        @JsonProperty("Importe")
        private double importe;

        public double getImporte() {
            return importe;
        }

        public void setImporte(double importe) {
            this.importe = importe;
        }
    }
}
