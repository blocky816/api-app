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

public class CarroDTO {
	
	private String nombreCompleto;
	
	private String club;
	
	private long idChip;
	
	private int idEmpleado;
	
	private long membresia;
	
	private int idCliente;
	
	private String tipoCliente;
	
	private int idVentaDetalle;
	
	private String placa;
	
	private String color;

	private String marca;
	
	private String noSerie;

	private int anio;
	
	private Date vigencia;
	
	private String modelo;
	
	private int id;
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getClub() {
		return club;
	}

	public void setClub(String club) {
		this.club = club;
	}

	public long getMembresia() {
		return membresia;
	}

	public void setMembresia(long membresia) {
		this.membresia = membresia;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public String getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public int getIdVentaDetalle() {
		return idVentaDetalle;
	}

	public void setIdVentaDetalle(int idVentaDetalle) {
		this.idVentaDetalle = idVentaDetalle;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getNoSerie() {
		return noSerie;
	}

	public void setNoSerie(String noSerie) {
		this.noSerie = noSerie;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public Date getVigencia() {
		return vigencia;
	}

	public void setVigencia(Date vigencia) {
		this.vigencia = vigencia;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	
	
	public long getIdChip() {
		return idChip;
	}

	public void setIdChip(long idChip) {
		this.idChip = idChip;
	}

	public int getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	@Override
	public String toString() {
		return "CarroDTO [nombreCompleto=" + nombreCompleto + ", club=" + club + ", idChip=" + idChip + ", idEmpleado="
				+ idEmpleado + ", membresia=" + membresia + ", idCliente=" + idCliente + ", tipoCliente=" + tipoCliente
				+ ", idVentaDetalle=" + idVentaDetalle + ", placa=" + placa + ", color=" + color + ", marca=" + marca
				+ ", noSerie=" + noSerie + ", anio=" + anio + ", vigencia=" + vigencia + ", modelo=" + modelo + "]";
	}

	
	
	
	
}
