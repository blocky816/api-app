package com.tutorial.crud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class body3 {
	@JsonProperty("IDCliente")
	private int IDCliente;

	private float monto;
	
	private String idOrden;

	public int getIDCliente() {
		return IDCliente;
	}

	public void setIDCliente(int iDCliente) {
		IDCliente = iDCliente;
	}

	public float getMonto() {
		return monto;
	}

	public void setMonto(float monto) {
		this.monto = monto;
	}

	public String getIdOrden() {
		return idOrden;
	}

	public void setIdOrden(String idOrden) {
		this.idOrden = idOrden;
	} 

	
}
