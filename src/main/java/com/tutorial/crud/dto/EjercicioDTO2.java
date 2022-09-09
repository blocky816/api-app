package com.tutorial.crud.dto;

public class EjercicioDTO2{
    private int dia;
    private int repeticiones;
    private int series;
    private int orden;
    private String nombre;
    private int idEjercicio;
    private String rutaImagen;
	public int getDia() {
		return dia;
	}
	public void setDia(int dia) {
		this.dia = dia;
	}
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
	public int getIdEjercicio() {
		return idEjercicio;
	}
	public void setIdEjercicio(int idEjercicio) {
		this.idEjercicio = idEjercicio;
	}
	public String getRutaImagen() {
		return rutaImagen;
	}
	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}
	@Override
	public String toString() {
		return "EjercicioDTO [dia=" + dia + ", repeticiones=" + repeticiones + ", series=" + series + ", orden=" + orden
				+ ", nombre=" + nombre + ", idEjercicio=" + idEjercicio + ", rutaImagen=" + rutaImagen + "]";
	}
	
	
    
    
}