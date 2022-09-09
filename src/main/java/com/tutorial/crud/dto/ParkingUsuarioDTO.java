package com.tutorial.crud.dto;

import java.util.Date;


import com.tutorial.crud.entity.Cliente;

public class ParkingUsuarioDTO {
	private int idVentaDetalle;
	
	private ClienteVista cliente;
	
	private String concepto;
	
	private int idProd;

	private int cantidad;
	
	private Date fechaCaptura;

	private boolean pk;
	
	private Date vigencia;
	
	private String observaciones;

	
	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public int getIdVentaDetalle() {
		return idVentaDetalle;
	}

	public void setIdVentaDetalle(int idVentaDetalle) {
		this.idVentaDetalle = idVentaDetalle;
	}

	public ClienteVista getCliente() {
		return cliente;
	}

	public void setCliente(ClienteVista cliente) {
		this.cliente = cliente;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public int getIdProd() {
		return idProd;
	}

	public void setIdProd(int idProd) {
		this.idProd = idProd;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Date getFechaCaptura() {
		return fechaCaptura;
	}

	public void setFechaCaptura(Date fechaCaptura) {
		this.fechaCaptura = fechaCaptura;
	}

	public boolean isPk() {
		return pk;
	}

	public void setPk(boolean pk) {
		this.pk = pk;
	}

	
	public Date getVigencia() {
		return vigencia;
	}

	public void setVigencia(Date vigencia) {
		this.vigencia = vigencia;
	}

	@Override
	public String toString() {
		return "ParkingUsuarioDTO [idVentaDetalle=" + idVentaDetalle + ", cliente=" + cliente + ", concepto=" + concepto
				+ ", idProd=" + idProd + ", cantidad=" + cantidad + ", fechaCaptura=" + fechaCaptura + ", pk=" + pk
				+ ", vigencia=" + vigencia + "]";
	}


	
	
}
