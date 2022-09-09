package com.tutorial.crud.dto;

import java.util.List;
import java.util.UUID;

public class ApartadosDTO {
	private UUID id;
	private int conteo;
	
	private String horario;

	public int getConteo() {
		return conteo;
	}

	public void setConteo(int conteo) {
		this.conteo = conteo;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	
	
	
}
