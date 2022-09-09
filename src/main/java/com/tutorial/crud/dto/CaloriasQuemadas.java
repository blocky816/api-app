package com.tutorial.crud.dto;

import java.util.List;


import com.tutorial.crud.entity.PasosCalorias;

public class CaloriasQuemadas {
	private List<PasosCalorias> lista;
	
	private float calorias;

	public List<PasosCalorias> getLista() {
		return lista;
	}

	public void setLista(List<PasosCalorias> lista) {
		this.lista = lista;
	}

	public float getCalorias() {
		return calorias;
	}

	public void setCalorias(float calorias) {
		this.calorias = calorias;
	}

	
	
}
