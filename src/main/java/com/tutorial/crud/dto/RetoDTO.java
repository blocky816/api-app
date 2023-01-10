package com.tutorial.crud.dto;


import java.util.UUID;

public class RetoDTO {
	private UUID id;
	private String nombre;
	private String fechaInicio;
	private String fechaFin;
	private String tipoReto;
	private String descripcion;


	public UUID getId() { return id; }

	public void setId(UUID id) { this.id = id; }

	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getTipo() {
		return tipoReto;
	}
	public void setTipo(String tipoReto) {
		this.tipoReto = tipoReto;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
