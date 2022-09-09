package com.tutorial.crud.dto;

public class EjercicioDTO{
    private int repeticiones;
    private int series;
    private int orden;
    private String nombre;
    private String id_ejercicio;
    private String rutaImagen;
	
	public int getRepeticiones() {
		return repeticiones;
	}
	public void setRepeticiones(int repeticiones) {
		this.repeticiones = repeticiones;
	}
	public int getSeries() {
		return series;
	}
	public void setSeries(int series) {
		this.series = series;
	}
	public int getOrden() {
		return orden;
	}
	public void setOrden(int orden) {
		this.orden = orden;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRutaImagen() {
		return rutaImagen;
	}
	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}
	public String getId_ejercicio() {
		return id_ejercicio;
	}
	public void setId_ejercicio(String id_ejercicio) {
		this.id_ejercicio = id_ejercicio;
	}
	@Override
	public String toString() {
		return "EjercicioDTO [repeticiones=" + repeticiones + ", series=" + series + ", orden=" + orden + ", nombre="
				+ nombre + ", id_ejercicio=" + id_ejercicio + ", rutaImagen=" + rutaImagen + "]";
	}
	
	
	
    
    
}