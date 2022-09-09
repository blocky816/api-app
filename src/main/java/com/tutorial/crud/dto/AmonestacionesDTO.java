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

public class AmonestacionesDTO {
	private String incidencia;
	
	private String horaEntrada;
	

	private String horaSalida;
	
	private String texto;
	
	private int idCliente;
	
	private long membresia;
	
	private long idChip;
	
	

	public long getIdChip() {
		return idChip;
	}

	public void setIdChip(long idChip) {
		this.idChip = idChip;
	}

	public String getIncidencia() {
		return incidencia;
	}

	public void setIncidencia(String incidencia) {
		this.incidencia = incidencia;
	}


	public String getHoraEntrada() {
		return horaEntrada;
	}

	public void setHoraEntrada(String horaEntrada) {
		this.horaEntrada = horaEntrada;
	}

	public String getHoraSalida() {
		return horaSalida;
	}

	public void setHoraSalida(String horaSalida) {
		this.horaSalida = horaSalida;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public long getMembresia() {
		return membresia;
	}

	public void setMembresia(long membresia) {
		this.membresia = membresia;
	}
	
	
}
