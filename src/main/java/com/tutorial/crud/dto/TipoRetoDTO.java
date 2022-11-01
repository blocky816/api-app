package com.tutorial.crud.dto;

import java.util.UUID;

public class TipoRetoDTO {
    private UUID id;
    private String nombre;
    //private Boolean activo;
    private String dificultad;
    private Boolean time;
    private int max;

    //OCTUBRE

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

	/*public Boolean getActivo() { return activo; }

	public void setActivo(Boolean activo) { this.activo = activo; }*/

    public String getDificultad() { return dificultad; }

    public void setDificultad(String dificultad) { this.dificultad = dificultad; }


    public UUID getId() { return id; }

    public void setId(UUID id) { this.id = id; }

    public Boolean getTime() { return time; }

    public void setTime(Boolean time) { this.time = time; }

    public int getMax() { return max; }

    public void setMax(int max) { this.max = max; }
}
