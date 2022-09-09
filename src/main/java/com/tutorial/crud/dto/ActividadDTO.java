package com.tutorial.crud.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

public class ActividadDTO {
	private String nombre;
	
	private String dificultad;
	
	private int paga;
	
	private boolean segmentacion;

	private boolean sobreescribir;

	private UUID tipoActividad;
	
	private int cupo;
	
	private int max;
	
	
	public int getCupo() {
		return cupo;
	}

	public void setCupo(int cupo) {
		this.cupo = cupo;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDificultad() {
		return dificultad;
	}

	public void setDificultad(String dificultad) {
		this.dificultad = dificultad;
	}

	public int getPaga() {
		return paga;
	}

	public void setPaga(int i) {
		this.paga = i;
	}

	public boolean isSegmentacion() {
		return segmentacion;
	}

	public void setSegmentacion(boolean segmentacion) {
		this.segmentacion = segmentacion;
	}

	public boolean isSobreescribir() {
		return sobreescribir;
	}

	public void setSobreescribir(boolean sobreescribir) {
		this.sobreescribir = sobreescribir;
	}

	public UUID getTipoActividad() {
		return tipoActividad;
	}

	public void setTipoActividad(UUID tipoActividad) {
		this.tipoActividad = tipoActividad;
	}

	
}
