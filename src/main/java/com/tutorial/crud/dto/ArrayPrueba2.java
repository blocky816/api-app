package com.tutorial.crud.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class ArrayPrueba2 {
	
	private int idCliente;
	private int idPlantilla;
	private String nombreRutina;
	private String nombreObjetivo;
	private int semanas;
	private String tipoPlantilla;
	
	private ArrayList<RutinaDTO> rutina;
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
	
	public ArrayList<RutinaDTO> getRutina() {
		return rutina;
	}
	public void setRutina(ArrayList<RutinaDTO> rutina) {
		this.rutina = rutina;
	}
	
	
	public String getTipoPlantilla() {
		return tipoPlantilla;
	}
	public void setTipoPlantilla(String tipoPlantilla) {
		this.tipoPlantilla = tipoPlantilla;
	}
	
	
	public int obtenerIdPlantilla() {
		return idPlantilla;
	}
	public void setIdPlantilla(int idPlantilla) {
		this.idPlantilla = idPlantilla;
	}
	@Override
	public String toString() {
		return "ArrayPrueba2 [idCliente=" + idCliente + ", idPlantilla=" + idPlantilla + ", nombreRutina="
				+ nombreRutina + ", nombreObjetivo=" + nombreObjetivo + ", semanas=" + semanas + ", tipoPlantilla="
				+ tipoPlantilla + ", rutina=" + rutina + "]";
	}
	
	

	

}
