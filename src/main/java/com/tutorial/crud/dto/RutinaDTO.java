package com.tutorial.crud.dto;

public class RutinaDTO {
	private String id_ejercicio;
	private int repeticiones;
	private int series;
	private int orden;
	
	
	public String getId_ejercicio() {
		return id_ejercicio;
	}
	public void setId_ejercicio(String id_ejercicio) {
		this.id_ejercicio = id_ejercicio;
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
	@Override
	public String toString() {
		return "RutinaDTO [idEjercicio=" + id_ejercicio + ", repeticiones=" + repeticiones + ", series=" + series
				+ ", orden=" + orden + "]";
	}
	
	

}
