package com.tutorial.crud.Odoo.Spec.dto.EvaluacionSpec;

public class InformacionDeportistaSpec {
        private String nombre;
        private Integer edad;
        private Double talla;
        private Double peso;
        private String deporte;
        private Double masaAdiposa;
        private Double masaMuscular;
        private String fechaEvaluacion;
        public String getNombre() {
            return nombre;
        }
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        public Integer getEdad() {
            return edad;
        }
        public void setEdad(Integer edad) {
            this.edad = edad;
        }
        public Double getTalla() {
            return talla;
        }
        public void setTalla(Double talla) {
            this.talla = talla;
        }
        public Double getPeso() {
            return peso;
        }
        public void setPeso(Double peso) {
            this.peso = peso;
        }
        public String getDeporte() {
            return deporte;
        }
        public void setDeporte(String deporte) {
            this.deporte = deporte;
        }
        public Double getMasaAdiposa() {
            return masaAdiposa;
        }
        public void setMasaAdiposa(Double masaAdiposa) {
            this.masaAdiposa = masaAdiposa;
        }
        public Double getMasaMuscular() {
            return masaMuscular;
        }
        public void setMasaMuscular(Double masaMuscular) {
            this.masaMuscular = masaMuscular;
        }
        public String getFechaEvaluacion() { return fechaEvaluacion; }
        public void setFechaEvaluacion(String fechaEvaluacion) { this.fechaEvaluacion = fechaEvaluacion; }
}
