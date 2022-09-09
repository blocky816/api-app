package com.tutorial.crud.dto;

import java.util.List;


import com.tutorial.crud.entity.PasosCalorias;

public class PasosDados {
	private List<PasosCalorias> lista;
	
	private int pasos;

	public List<PasosCalorias> getLista() {
		return lista;
	}

	public void setLista(List<PasosCalorias> lista) {
		this.lista = lista;
	}

	public int getPasos() {
		return pasos;
	}

	public void setPasos(int pasos) {
		this.pasos = pasos;
	}
	
	
}
