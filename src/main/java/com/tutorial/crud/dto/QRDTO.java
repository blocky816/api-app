package com.tutorial.crud.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

public class QRDTO {
	private int cantidadQrNormal;
	
	private int cantidadQrPerdida;
	
	private float totalQrNormal;
	
	private float totalQrPerdida;

	public int getCantidadQrNormal() {
		return cantidadQrNormal;
	}

	public void setCantidadQrNormal(int cantidadQrNormal) {
		this.cantidadQrNormal = cantidadQrNormal;
	}

	public int getCantidadQrPerdida() {
		return cantidadQrPerdida;
	}

	public void setCantidadQrPerdida(int cantidadQrPerdida) {
		this.cantidadQrPerdida = cantidadQrPerdida;
	}

	public float getTotalQrNormal() {
		return totalQrNormal;
	}

	public void setTotalQrNormal(float totalQrNormal) {
		this.totalQrNormal = totalQrNormal;
	}

	public float getTotalQrPerdida() {
		return totalQrPerdida;
	}

	public void setTotalQrPerdida(float totalQrPerdida) {
		this.totalQrPerdida = totalQrPerdida;
	}
	
	
	
	
	
}
