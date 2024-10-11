package com.tutorial.crud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccesoTorniqueteDTO {
    @JsonProperty("id_cliente_odoo")
    private int idClienteOdoo;
    @JsonProperty("branch_id")
    private int branchId;
    @JsonProperty("titular")
    private int titular;
    @JsonProperty("id_cliente")
    private String idCliente;
    @JsonProperty("nombre_acceso")
    private String nombreAcceso;

    public int getIdClienteOdoo() {
        return idClienteOdoo;
    }

    public void setIdClienteOdoo(int idClienteOdoo) {
        this.idClienteOdoo = idClienteOdoo;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public int getTitular() {
        return titular;
    }

    public void setTitular(int titular) {
        this.titular = titular;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreAcceso() {
        return nombreAcceso;
    }

    public void setNombreAcceso(String nombreAcceso) {
        this.nombreAcceso = nombreAcceso;
    }

    @Override
    public String toString() {
        return "Acceso{" +
                "idClienteOdoo=" + idClienteOdoo +
                ", branchId=" + branchId +
                ", titular=" + titular +
                ", idCliente='" + idCliente + '\'' +
                ", nombreAcceso='" + nombreAcceso + '\'' +
                '}';
    }
}
