package com.tutorial.crud.dto;

import java.util.ArrayList;

public class RutinaEjercicioDTO {
	private String nombreRutina;
	private String nombreObjetivo;
	private int semanas;
	private ArrayList<EjercicioDTO> ejercicios;
	private int idCliente;
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
	public ArrayList<EjercicioDTO> getEjercicios() {
		return ejercicios;
	}
	public void setEjercicios(ArrayList<EjercicioDTO> ejercicios) {
		this.ejercicios = ejercicios;
	}
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	

	
}
