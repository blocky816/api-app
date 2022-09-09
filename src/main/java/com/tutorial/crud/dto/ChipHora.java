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

import com.tutorial.crud.entity.RegistroTag;

public class ChipHora {
	private RegistroTag idChip;
	
	private int horas;
	
	private Date horaEntrada;
	
	private Date horaSalida;

	public RegistroTag getIdChip() {
		return idChip;
	}

	public void setIdChip(RegistroTag idChip) {
		this.idChip = idChip;
	}

	public int getHoras() {
		return horas;
	}

	public void setHoras(int horas) {
		this.horas = horas;
	}
	
	

	public Date getHoraEntrada() {
		return horaEntrada;
	}

	public void setHoraEntrada(Date horaEntrada) {
		this.horaEntrada = horaEntrada;
	}

	public Date getHoraSalida() {
		return horaSalida;
	}

	public void setHoraSalida(Date horaSalida) {
		this.horaSalida = horaSalida;
	}

	@Override
	public String toString() {
		return "ChipHora [idChip=" + idChip + ", horas=" + horas + "]";
	}
	
	
	
	
}
