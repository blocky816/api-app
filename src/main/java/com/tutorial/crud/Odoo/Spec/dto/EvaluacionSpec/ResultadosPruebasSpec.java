package com.tutorial.crud.Odoo.Spec.dto.EvaluacionSpec;

public class ResultadosPruebasSpec {
    private String nombre;
    private Double valor;
    private Double limiteInferior;
    private Double limiteSuperior;
    private String referencia;
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Double getValor() {
        return valor;
    }
    public void setValor(Double valor) {
        this.valor = valor;
    }
    public Double getLimiteInferior() {
        return limiteInferior;
    }
    public void setLimiteInferior(Double limiteInferior) {
        this.limiteInferior = limiteInferior;
    }
    public Double getLimiteSuperior() {
        return limiteSuperior;
    }
    public void setLimiteSuperior(Double limiteSuperior) {
        this.limiteSuperior = limiteSuperior;
    }
    public String getReferencia() {
        return referencia;
    }
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
}