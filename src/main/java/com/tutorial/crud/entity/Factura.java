package com.tutorial.crud.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="factura") //Se utiliza para poner el nombre real de la tabla en la base de datos
public class Factura {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "recibo")
	private String recibo;
	
	@Column(name = "uuid")
	private String uuid;
	
	@Column(name = "fecha_creacion")
	private Date fechaCreacion;
	
	@Column(name = "fecha_modificacion")
	private Date fechaModificacion;

	@Column(name = "facturado")
	private boolean facturado;
	
	@Column(name = "id_cliente")
	private int idCliente;
	

	@Column(name = "total")
	private double total;
	

	@Column(name = "uso_cfdi")
	private String usoCFDI;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "rfc")
	private String rfc;
	
	@Column(name = "regimen_fiscal")
	private String regimenFiscal;
	
	@Column(name = "codigo_postal")
	private String codigoPostal;
	
	
	@Column(name = "email")
	private String email;
	
	

	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getUsoCFDI() {
		return usoCFDI;
	}


	public void setUsoCFDI(String usoCFDI) {
		this.usoCFDI = usoCFDI;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getRfc() {
		return rfc;
	}


	public void setRfc(String rfc) {
		this.rfc = rfc;
	}


	public String getRegimenFiscal() {
		return regimenFiscal;
	}


	public void setRegimenFiscal(String regimenFiscal) {
		this.regimenFiscal = regimenFiscal;
	}


	public String getCodigoPostal() {
		return codigoPostal;
	}


	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}


	public double getTotal() {
		return total;
	}


	public void setTotal(double total) {
		this.total = total;
	}


	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}


	public void setFacturado(boolean facturado) {
		this.facturado = facturado;
	}

	public String getRecibo() {
		return recibo;
	}

	public void setRecibo(String recibo) {
		this.recibo = recibo;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Date getFechaFacturacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	
	
			
	
}
