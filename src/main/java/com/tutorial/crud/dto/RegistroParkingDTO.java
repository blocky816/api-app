package com.tutorial.crud.dto;


import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.tutorial.crud.entity.Antena;
import com.tutorial.crud.entity.Caseta;
import com.tutorial.crud.entity.RegistroTag;
import com.tutorial.crud.entity.TipoAcceso;

public class RegistroParkingDTO {
	private int id;

	private Antena idAntena;

	private Caseta idCaseta;

	private RegistroTag idChip;

	private Date horaEvento;
	
	private Timestamp horaEntrada;
	
	private String mensaje;
	
	private String raw;	
	
	private boolean acceso;
	
    private TipoAcceso tipoAcceso;
    
    private boolean sentido;

	public boolean isSentido() {
		return sentido;
	}	
	
	public void setSentido(boolean sentido) {
		this.sentido = sentido;
	}

	public boolean isAcceso() {
		return acceso;
	}

	public void setAcceso(boolean acceso) {
		this.acceso = acceso;
	}

	public TipoAcceso getTipoAcceso() {
		return tipoAcceso;
	}

	public void setTipoAcceso(TipoAcceso tipoAcceso) {
		this.tipoAcceso = tipoAcceso;
	}

	public Timestamp getHoraEntrada() {
		return horaEntrada;
	}

	public void setHoraEntrada(long horaEntrada) {
		this.horaEntrada =new Timestamp(horaEntrada);
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getRaw() {
		return raw;
	}

	public void setRaw(String raw) {
		this.raw = raw;
	}

	public Antena getIdAntena() {
		return idAntena;
	}

	public void setIdAntena(Antena idAntena) {
		this.idAntena = idAntena;
	}

	public Caseta getIdCaseta() {
		return idCaseta;
	}

	public void setIdCaseta(Caseta idCaseta) {
		this.idCaseta = idCaseta;
	}

	public RegistroTag getIdChip() {
		return idChip;
	}

	public void setIdChip(RegistroTag idChip) {
		this.idChip = idChip;
	}

	public Date getHoraEvento() {
		return horaEvento;
	}

	public void setHoraEvento(Date horaEvento) {
		this.horaEvento = horaEvento;
	}

	public int getId() {
		return id;
	}



	@Override
	public String toString() {
		return "RegistroParkingDTO [id=" + id + ", idAntena=" + idAntena + ", idCaseta=" + idCaseta + ", idChip="
				+ idChip + ", horaEvento=" + horaEvento + ", horaEntrada=" + horaEntrada + ", mensaje=" + mensaje
				+ ", raw=" + raw + ", acceso=" + acceso + ", tipoAcceso=" + tipoAcceso + ", sentido=" + sentido + "]";
	}



	
	
}
