package com.tutorial.crud.dto;

import java.util.UUID;

public class HorarioVista {
	private boolean lunes;
	
	private boolean martes;
	
	private boolean miercoles;
	
	private boolean jueves;
	
	private boolean viernes;
	
	private boolean sabado;
	
	private boolean domingo;
	
	private int duracion;	

	private String hora;	

	private String periodoInicio;	

	private String periodoFinal;	

	private UUID actividad;	

	private UUID sala;	

	private UUID tecnico;
	
	private String club;
	
	private boolean disponible;	

	private String rango;
	
	private int cupoMaximo;


	public int getCupoMaximo() {
		return cupoMaximo;
	}

	public void setCupoMaximo(int cupoMaximo) {
		this.cupoMaximo = cupoMaximo;
	}

	public String getClub() {
		return club;
	}

	public void setClub(String club) {
		this.club = club;
	}

	public boolean isLunes() {
		return lunes;
	}

	public void setLunes(boolean lunes) {
		this.lunes = lunes;
	}

	public boolean isMartes() {
		return martes;
	}

	public void setMartes(boolean martes) {
		this.martes = martes;
	}

	public boolean isMiercoles() {
		return miercoles;
	}

	public void setMiercoles(boolean miercoles) {
		this.miercoles = miercoles;
	}

	public boolean isJueves() {
		return jueves;
	}

	public void setJueves(boolean jueves) {
		this.jueves = jueves;
	}

	public boolean isViernes() {
		return viernes;
	}

	public void setViernes(boolean viernes) {
		this.viernes = viernes;
	}

	public boolean isSabado() {
		return sabado;
	}

	public void setSabado(boolean sabado) {
		this.sabado = sabado;
	}

	public boolean isDomingo() {
		return domingo;
	}

	public void setDomingo(boolean domingo) {
		this.domingo = domingo;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getPeriodoInicio() {
		return periodoInicio;
	}

	public void setPeriodoInicio(String periodoInicio) {
		this.periodoInicio = periodoInicio;
	}

	public String getPeriodoFinal() {
		return periodoFinal;
	}

	public void setPeriodoFinal(String periodoFinal) {
		this.periodoFinal = periodoFinal;
	}


	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	public String getRango() {
		return rango;
	}

	public void setRango(String rango) {
		this.rango = rango;
	}
	
	

	public UUID getActividad() {
		return actividad;
	}

	public void setActividad(UUID actividad) {
		this.actividad = actividad;
	}

	public UUID getSala() {
		return sala;
	}

	public void setSala(UUID sala) {
		this.sala = sala;
	}

	public UUID getTecnico() {
		return tecnico;
	}

	public void setTecnico(UUID tecnico) {
		this.tecnico = tecnico;
	}

	@Override
	public String toString() {
		return "HorarioDTO [lunes=" + lunes + ", martes=" + martes + ", miercoles=" + miercoles + ", jueves=" + jueves
				+ ", viernes=" + viernes + ", sabado=" + sabado + ", domingo=" + domingo + ", duracion=" + duracion
				+ ", hora=" + hora + ", periodoInicio=" + periodoInicio + ", periodoFinal=" + periodoFinal
				+ ", actividad=" + actividad + ", sala=" + sala + ", tecnico=" + tecnico + ", club=" + club
				+ ", disponible=" + disponible + ", rango=" + rango + "]";
	}	
	
	
}
