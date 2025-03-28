package com.tutorial.crud.Odoo.Spec.dto;

import java.util.Map;

public class Dieta {
    private Map<String, Dia> dias;

    // Getters and Setters
    public Map<String, Dia> getDias() {
        return dias;
    }

    public void setDias(Map<String, Dia> dias) {
        this.dias = dias;
    }

    public static class Dia {
        private String desayuno;
        private String colacion1;
        private String comida;
        private String colacion2;
        private String cena;

        public String getDesayuno() {
            return desayuno;
        }

        public void setDesayuno(String desayuno) {
            this.desayuno = desayuno;
        }

        public String getColacion1() {
            return colacion1;
        }

        public void setColacion1(String colacion1) {
            this.colacion1 = colacion1;
        }

        public String getComida() {
            return comida;
        }

        public void setComida(String comida) {
            this.comida = comida;
        }

        public String getColacion2() {
            return colacion2;
        }

        public void setColacion2(String colacion2) {
            this.colacion2 = colacion2;
        }

        public String getCena() {
            return cena;
        }

        public void setCena(String cena) {
            this.cena = cena;
        }
    }
}
