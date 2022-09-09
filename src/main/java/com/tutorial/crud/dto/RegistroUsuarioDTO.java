package com.tutorial.crud.dto;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
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

public class RegistroUsuarioDTO {
	private Date horaEvento;
	private String sentido;
	private BigInteger idChip;
	private String tipoAcceso;
	private int antena;
	private int caseta;
	
	public String getHoraEvento() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		String startDate = formatter.format(horaEvento);
		return startDate;
	}
	public void setHoraEvento(Date horaEvento) {
		this.horaEvento = horaEvento;
	}
	public String getSentido() {
		return sentido;
	}
	public void setSentido(String sentido) {
		this.sentido = sentido;
	}
	public BigInteger getIdChip() {
		return idChip;
	}
	public void setIdChip(BigInteger idChip) {
		this.idChip = idChip;
	}
	public String getTipoAcceso() {
		return tipoAcceso;
	}
	public void setTipoAcceso(String tipoAcceso) {
		this.tipoAcceso = tipoAcceso;
	}
	public int getAntena() {
		return antena;
	}
	public void setAntena(int antena) {
		this.antena = antena;
	}
	public int getCaseta() {
		return caseta;
	}
	public void setCaseta(int caseta) {
		this.caseta = caseta;
	}
	
	
	
}
