package com.tutorial.crud.dto;

import java.util.List;

public class RutinaNuevoDTO {

    private int id;
    private String nombreRutina;
    private String nombreObjetivo;
    private Integer semanas;
    private String tipoPlantilla;

    private List<EjercicioNuevoDTO> ejercicios;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNombreRutina() { return nombreRutina; }

    public void setNombreRutina(String nombreRutina) { this.nombreRutina = nombreRutina; }

    public String getNombreObjetivo() { return nombreObjetivo; }

    public void setNombreObjetivo(String nombreObjetivo) { this.nombreObjetivo = nombreObjetivo; }

    public Integer getSemanas() { return semanas; }

    public void setSemanas(Integer semanas) { this.semanas = semanas; }

    public String getTipoPlantilla() { return tipoPlantilla; }

    public void setTipoPlantilla(String tipoPlantilla) { this.tipoPlantilla = tipoPlantilla; }

    public List<EjercicioNuevoDTO> getEjercicios() { return ejercicios; }

    public void setEjercicios(List<EjercicioNuevoDTO> ejercicios) { this.ejercicios = ejercicios; }

    @Override
    public String toString() {
        return "RutinaNuevoDTO{" +
                "nombreRutina='" + nombreRutina + '\'' +
                ", nombreObjetivo='" + nombreObjetivo + '\'' +
                ", semanas=" + semanas +
                ", tipoPlantilla='" + tipoPlantilla + '\'' +
                ", ejercicios=" + ejercicios +
                '}';
    }
}
