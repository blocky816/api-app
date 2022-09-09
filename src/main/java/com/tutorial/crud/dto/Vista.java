package com.tutorial.crud.dto;

import com.tutorial.crud.entity.Carro;

public class Vista {
	
	private long idVentaDetalle;
	private String nombreCompleto;
	
	private String club;
	
	private long membresia;
	
	private int idCliente;

	private String tipoCliente;

	private Carro carro;

	
	
	public long getIdVentaDetalle() {
		return idVentaDetalle;
	}

	public void setIdVentaDetalle(long idVentaDetalle) {
		this.idVentaDetalle = idVentaDetalle;
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

	public Carro getCarro() {
		return carro;
	}

	public void setCarro(Carro carro) {
		this.carro = carro;
	}

	
}
