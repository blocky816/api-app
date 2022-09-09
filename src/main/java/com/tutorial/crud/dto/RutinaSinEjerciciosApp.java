package com.tutorial.crud.dto;

public class RutinaSinEjerciciosApp {
	
	private String nombreRutina;
	
	private String nombreObjetivo;
	
	private int semanas;
	
	private int idPlantilla;
	

	public int getIdPlantilla() {
		return idPlantilla;
	}
	

	public void setIdPlantilla(int idPlantilla) {
		this.idPlantilla = idPlantilla;
	}

	public String getNombreRutina() {
		return nombreRutina;
	}

	public void setNombreRutina(String nombreRutina) {
		this.nombreRutina = nombreRutina;
	}

	public String getNombreObjetivo() {
		return nombreObjetivo;
	}

	public void setNombreObjetivo(String nombreObjetivo) {
		this.nombreObjetivo = nombreObjetivo;
	}

	public int getSemanas() {
		return semanas;
	}

	public void setSemanas(int semanas) {
		this.semanas = semanas;
	}


	
}
