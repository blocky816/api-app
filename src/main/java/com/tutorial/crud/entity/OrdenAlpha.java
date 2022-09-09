package com.tutorial.crud.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orden_alpha")
public class OrdenAlpha {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "no_pedido", nullable = false, length = 20)
	private int noPedido;
	
	@Column(name = "monto", nullable = false, length = 10)
	private double monto;
	
	@Column(name = "no_autorizacion", nullable = false, length = 50)
	private String noAutorizacion;
	
	@Column(name = "titular_cuenta", nullable = false, length = 50)
	private String titularCuenta;
	
	@Column(name = "json", nullable = false, length = 50000)
	private String json;
	
	@Column(name = "notarjeta", nullable = false, length = 50)
	private String notarjeta;
	
	@Column(name = "fecha_creacion", nullable = false)
	private Date fechaCreacion;
	
	@Column(name = "id_cliente", nullable = false)
	private int idCliente;

	public OrdenAlpha() {
	}
	
	public OrdenAlpha(int noPedido, double monto, String noAutorizacion, String titularCuenta, String json,
			Date fechaCreacion) {
		this.noPedido = noPedido;
		this.monto = monto;
		this.noAutorizacion = noAutorizacion;
		this.titularCuenta = titularCuenta;
		this.json = json;
		this.fechaCreacion = fechaCreacion;
	}
	
	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public int getNoPedido() {
		return noPedido;
	}

	public void setNoPedido(int noPedido) {
		this.noPedido = noPedido;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public String getNoAutorizacion() {
		return noAutorizacion;
	}

	public void setNoAutorizacion(String noAutorizacion) {
		this.noAutorizacion = noAutorizacion;
	}

	public String getTitularCuenta() {
		return titularCuenta;
	}

	public void setTitularCuenta(String titularCuenta) {
		this.titularCuenta = titularCuenta;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getNotarjeta() {
		return notarjeta;
	}

	public void setNotarjeta(String notarjeta) {
		this.notarjeta = notarjeta;
	}
	
	

}
