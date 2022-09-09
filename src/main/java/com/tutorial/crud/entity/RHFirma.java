package com.tutorial.crud.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="rh_firma") //Se utiliza para poner el nombre real de la tabla en la base de datos
public class RHFirma {
	
	@Id	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@OneToOne
    @JoinColumn(name = "id_solicitud", referencedColumnName = "id")
    private RHSolicitud solicitud;
    
	@Column(name = "entrega_documento")
	private String entregaDocumento="NO";
	
	@Column(name = "validacion")
	private String validacion="NO";
	
	@Column(name = "fecha_validacion")
	private Date fechaValidacion;

	public RHSolicitud getSolicitud() {
		return solicitud;
	}

	public int getId() {
		return id;
	}

	public void setSolicitud(RHSolicitud solicitud) {
		this.solicitud = solicitud;
	}

	public String getEntregaDocumento() {
		return entregaDocumento;
	}

	public void setEntregaDocumento(String entregaDocumento) {
		this.entregaDocumento = entregaDocumento;
	}

	public String getValidacion() {
		return validacion;
	}

	public void setValidacion(String validacion) {
		this.validacion = validacion;
	}

	public Date getFechaValidacion() {
		return fechaValidacion;
	}

	public void setFechaValidacion(Date fechaValidacion) {
		this.fechaValidacion = fechaValidacion;
	}
	
	
	
}
