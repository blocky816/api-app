/*En este paquete tenemos nuestro clase Recibo.java y utilizaremos las @anotaciones 
 * JPA para relacionarla con nuestra tabla Recibo, quedaría de la siguiente forma:
 *	@autor: Daniel García Velasco 
 * 			Abimael Rueda Galindo
 *	@version: 1
 *12/07/2021
*/
package com.tutorial.crud.dto;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

public class Recibo 
{
	private Date fechaCaptura;
	
	private String folio;
	
	private int idCliente;
	
	private String nombreCliente;
	
	private long membresia;
	
	private String codigoPostal;
	
	private String rfc;
	
	private int idVenta;
	
	private String concepto;
	
	private float costo;
	
	private String clave;
	
	private float precioUnitario;
	
	private float precioUnitarioIVA;
	
	private float total;
	
	private String correo;
	
	private String idProducto;
	
	private boolean facturado;
	
	private String codigo;
	
	private String unidad;
	
	

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(String idProducto) {
		this.idProducto = idProducto;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public boolean isFacturado() {
		return facturado;
	}

	public void setFacturado(boolean facturado) {
		this.facturado = facturado;
	}

	public Date getFechaCaptura() {
		return fechaCaptura;
	}

	public void setFechaCaptura(Date fechaCaptura) {
		this.fechaCaptura = fechaCaptura;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public long getMembresia() {
		return membresia;
	}

	public void setMembresia(long membresia) {
		this.membresia = membresia;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public int getIdVenta() {
		return idVenta;
	}

	public void setIdVenta(int idVenta) {
		this.idVenta = idVenta;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public float getCosto() {
		return costo;
	}

	public void setCosto(float costo) {
		this.costo = costo;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public float getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(float precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public float getPrecioUnitarioIVA() {
		return precioUnitarioIVA;
	}

	public void setPrecioUnitarioIVA(float precioUnitarioIVA) {
		this.precioUnitarioIVA = precioUnitarioIVA;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "Recibo [fechaCaptura=" + fechaCaptura + ", folio=" + folio + ", idCliente=" + idCliente
				+ ", nombreCliente=" + nombreCliente + ", membresia=" + membresia + ", codigoPostal=" + codigoPostal
				+ ", rfc=" + rfc + ", idVenta=" + idVenta + ", concepto=" + concepto + ", costo=" + costo + ", clave="
				+ clave + ", precioUnitario=" + precioUnitario + ", precioUnitarioIVA=" + precioUnitarioIVA + ", total="
				+ total + "]";
	}
	
	
	
	
	
}
