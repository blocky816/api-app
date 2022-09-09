package com.tutorial.crud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PagoApi{
    @JsonProperty("NoPedido") 
    public int noPedido;
    @JsonProperty("Monto") 
    public float monto;
    @JsonProperty("Notarjeta") 
    public String notarjeta;
    @JsonProperty("FolioInterbancario") 
    public String folioInterbancario;
    @JsonProperty("NoAutorizacion") 
    public String noAutorizacion;
    @JsonProperty("FechaPago") 
    public String fechaPago;
    @JsonProperty("HoraPago") 
    public String horaPago;
    @JsonProperty("TitularCuenta") 
    public String titularCuenta;
    @JsonProperty("FormaPago") 
    public int formaPago;
    @JsonProperty("ReciboName") 
    public String reciboName;
    @JsonProperty("IDBanco") 
    public int idBanco;
    @JsonProperty("IDCuentaDeBanco") 
    public int idCuentaDeBanco;
    @JsonProperty("FechaImpresion") 
    public String fechaImpresion;
}
