package com.tutorial.crud.dto;

import java.util.ArrayList;
public class MensajeError {
	
	private String msg;

	private ArrayList<ItemFallido> productosFallidos;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ArrayList<ItemFallido> getProductosFallidos() {
		return productosFallidos;
	}

	public void setProductosFallidos(ArrayList<ItemFallido> productosFallidos) {
		this.productosFallidos = productosFallidos;
	}

	
	
}
