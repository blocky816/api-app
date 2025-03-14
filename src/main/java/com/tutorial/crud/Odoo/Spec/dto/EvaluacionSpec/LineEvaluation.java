package com.tutorial.crud.Odoo.Spec.dto.EvaluacionSpec;

import java.util.ArrayList;
import java.util.List;

public class LineEvaluation {
    public int id_line_evaluation;
    public List<IdParametro> id_parametro;
    public List<IdMaquina> id_maquina;
    public double limite_inferior;
    public double limite_superior;
    public double valor;
    public boolean indice;
    public int getId_line_evaluation() {
        return id_line_evaluation;
    }
    public void setId_line_evaluation(int id_line_evaluation) {
        this.id_line_evaluation = id_line_evaluation;
    }
    public List<IdParametro> getId_parametro() {
        return id_parametro;
    }
    public void setId_parametro(List<IdParametro> id_parametro) {
        this.id_parametro = id_parametro;
    }
    public List<IdMaquina> getId_maquina() {
        return id_maquina;
    }
    public void setId_maquina(List<IdMaquina> id_maquina) {
        this.id_maquina = id_maquina;
    }
    public double getLimite_inferior() {
        return limite_inferior;
    }
    public void setLimite_inferior(double limite_inferior) {
        this.limite_inferior = limite_inferior;
    }
    public double getLimite_superior() {
        return limite_superior;
    }
    public void setLimite_superior(double limite_superior) {
        this.limite_superior = limite_superior;
    }
    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }
    public boolean isIndice() {
        return indice;
    }
    public void setIndice(boolean indice) {
        this.indice = indice;
    }
}
