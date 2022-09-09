package com.tutorial.crud.dto;

import java.util.Arrays;

public class ClienteRutina {

	private int idCliente;
	private String nombre;
	private String estatusCobranza;
	private byte[] imagen;
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEstatusCobranza() {
		return estatusCobranza;
	}
	public void setEstatusCobranza(String estatusCobranza) {
		this.estatusCobranza = estatusCobranza;
	}
	public byte[] getImagen() {
		return imagen;
	}
	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}
	@Override
	public String toString() {
		return "ClienteRutina [idCliente=" + idCliente + ", nombre=" + nombre + ", estatusCobranza=" + estatusCobranza
				+ ", imagen=" + Arrays.toString(imagen) + "]";
	}
	
	
	
}
