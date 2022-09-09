package com.tutorial.crud.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class body2 {
	
	private int IDCliente;

	private int IDClub;

	private int Cantidad;
	
	private int IDProductoServicio;
	
	private String Observaciones;
	
	private Date FechaInicio;
	
	private Date FechaFin;

	private int descuentoPorciento;
	
	private int CobroProporcional;
	
	private String dia;
	
	

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public int getIDCliente() {
		return IDCliente;
	}

	public void setIDCliente(int iDCliente) {
		IDCliente = iDCliente;
	}

	public int getIDClub() {
		return IDClub;
	}

	public void setIDClub(int iDClub) {
		IDClub = iDClub;
	}

	public int getCantidad() {
		return Cantidad;
	}

	public void setCantidad(int cantidad) {
		Cantidad = cantidad;
	}

	public int getIDProductoServicio() {
		return IDProductoServicio;
	}

	public void setIDProductoServicio(int iDProductoServicio) {
		IDProductoServicio = iDProductoServicio;
	}

	public String getObservaciones() {
		return Observaciones;
	}

	public void setObservaciones(String observaciones) {
		Observaciones = observaciones;
	}

	public Date getFechaInicio() {
		return FechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
		FechaInicio = format.parse(fechaInicio);
		} catch (ParseException e) {
		e.printStackTrace();
		}
	}

	public Date getFechaFin() {
		return FechaFin;
	}

	public void setFechaFin(String fechaFin) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
		FechaFin = format.parse(fechaFin);
		} catch (ParseException e) {
		e.printStackTrace();
		}
	}
	public Date ParseFecha(Date parsedDate)
    {
	        SimpleDateFormat print = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        print.format(parsedDate);
	        return parsedDate;
    }
	public int getCobroProporcional() {
		return CobroProporcional;
	}

	public void setCobroProporcional(int cobroProporcional) {
		CobroProporcional = cobroProporcional;
	}

	public int getDescuentoPorciento() {
		return descuentoPorciento;
	}

	public void setDescuentoPorciento(int descuentoPorciento) {
		this.descuentoPorciento = descuentoPorciento;
	}

	@Override
	public String toString() {
		return "body2 [IDCliente=" + IDCliente + ", IDClub=" + IDClub + ", Cantidad=" + Cantidad
				+ ", IDProductoServicio=" + IDProductoServicio + ", Observaciones=" + Observaciones + ", FechaInicio="
				+ FechaInicio + ", FechaFin=" + FechaFin + ", descuentoPorciento=" + descuentoPorciento
				+ ", CobroProporcional=" + CobroProporcional + "]";
	}


	
}
