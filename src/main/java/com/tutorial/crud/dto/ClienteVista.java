package com.tutorial.crud.dto;

public class ClienteVista {

	private int idCLiente;
	private long membresia;
	private String nombre;
	private String clienteTipo;
	private String club;
	public int getIdCLiente() {
		return idCLiente;
	}
	public void setIdCLiente(int idCLiente) {
		this.idCLiente = idCLiente;
	}
	public long getMembresia() {
		return membresia;
	}
	public void setMembresia(long membresia) {
		this.membresia = membresia;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getClienteTipo() {
		return clienteTipo;
	}
	public void setClienteTipo(String clienteTipo) {
		this.clienteTipo = clienteTipo;
	}
	public String getClub() {
		return club;
	}
	public void setClub(String club) {
		this.club = club;
	}
	@Override
	public String toString() {
		return "ClienteVista [idCLiente=" + idCLiente + ", membresia=" + membresia + ", nombre=" + nombre
				+ ", clienteTipo=" + clienteTipo + ", club=" + club + "]";
	}
	
	
}
