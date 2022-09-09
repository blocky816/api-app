package com.tutorial.crud.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.sql.Date;

public class RutinaSinEjercicios {
	
	private String nombreRutina;
	
	private String nombreObjetivo;
	
	private int semanas;
	
	private Date diaInicio;
	private String diaFinal;

	private int idCliente;
	

	public int getIdCliente() {
		return idCliente;
	}
	

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
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

	
	public java.sql.Date getDiaInicio(){
		return diaInicio;
		
	}
	public String getDiaFinal(){
		return diaFinal;
		
	}


	public void setDiaInicio(Date diaInicio) {
		this.diaInicio = diaInicio;
	}


	public void setDiaFinal(String diaFinal) {
		this.diaFinal = diaFinal;
	}
	
}
