package com.tutorial.crud.Odoo.Spec.dto.EvaluacionSpec;

import java.util.List;

public class ReporteEvaluacion {
    InformacionDeportistaSpec info;
    List<ResultadosPruebasSpec> pruebas;
    public InformacionDeportistaSpec getInfo() {
        return info;
    }
    public void setInfo(InformacionDeportistaSpec info) {
        this.info = info;
    }
    public List<ResultadosPruebasSpec> getPruebas() {
        return pruebas;
    }
    public void setPruebas(List<ResultadosPruebasSpec> pruebas) {
        this.pruebas = pruebas;
    }
}
