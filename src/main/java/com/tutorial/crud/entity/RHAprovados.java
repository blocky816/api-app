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
@Table(name="rh_aprovados") //Se utiliza para poner el nombre real de la tabla en la base de datos
public class RHAprovados {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@OneToOne
    @JoinColumn(name = "id_solicitud", referencedColumnName = "id")
    private RHSolicitud solicitud;
	
	@Column(name = "aprovado" )
	private String aprovado="NO";
	
	@Column(name = "fecha_aprovacion")
	private Date fechaAprovacion;

	public RHSolicitud getSolicitud() {
		return solicitud;
	}
	
	public int getId() {
		return id;
	}

	public void setSolicitud(RHSolicitud solicitud) {
		this.solicitud = solicitud;
	}

	public String getAprovado() {
		return aprovado;
	}

	public void setAprovado(String aprovado) {
		this.aprovado = aprovado;
	}

	public Date getFechaAprovacion() {
		return fechaAprovacion;
	}

	public void setFechaAprovacion(Date fechaAprovacion) {
		this.fechaAprovacion = fechaAprovacion;
	}

    
	
}
