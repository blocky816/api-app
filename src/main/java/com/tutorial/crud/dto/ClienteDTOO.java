package com.tutorial.crud.dto;


public class ClienteDTOO {

	private int IdCliente;
	private String Nombre;
	private String EstatusAccesos;
	public int getIdCliente() {
		return IdCliente;
	}
	public void setIdCliente(int idCliente) {
		IdCliente = idCliente;
	}
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	public String getEstatusAccesos() {
		return EstatusAccesos;
	}
	public void setEstatusAccesos(String estatusAccesos) {
		EstatusAccesos = estatusAccesos;
	}
	

}
