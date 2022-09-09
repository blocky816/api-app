package com.tutorial.crud.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="pase_consumido") //Se utiliza para poner el nombre real de la tabla en la base de datos
public class PaseConsumido {
	
	@Id //Define la llave primaria.
    @GeneratedValue(generator = "UUID") //Se estableció un tipo de variable UUID 
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private UUID id;
	
	@ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "apartado_usuario")
	private CAApartadosUsuario apartadosUsuario;
	
	@ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "pase_usuario")
	private PaseUsuario paseUsuario;
	
	@Column(name = "fecha_redencion")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaRedencion;
	
	@OneToOne(cascade=CascadeType.ALL)
    private TerminalRedencion terminalRedencion;

	
	@Column(name = "created_by")
	private String createdBy; 
	
	@Column(name = "created") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	@Temporal(TemporalType.TIMESTAMP)
	private Date created = new Date();	
	
	@Column(name = "updated_by")
	private String updatedBy;
	
	@Column(name = "updated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated=new Date();
	

	
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	@Column(name = "activo")
	private boolean activo=true;
	
	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public CAApartadosUsuario getApartadosUsuario() {
		return apartadosUsuario;
	}

	public void setApartadosUsuario(CAApartadosUsuario apartadosUsuario) {
		this.apartadosUsuario = apartadosUsuario;
	}

	public PaseUsuario getPaseUsuario() {
		return paseUsuario;
	}

	public void setPaseUsuario(PaseUsuario paseUsuario) {
		this.paseUsuario = paseUsuario;
	}

	public Date getFechaRedencion() {
		return fechaRedencion;
	}

	public void setFechaRedencion(Date fechaRedencion) {
		this.fechaRedencion = fechaRedencion;
	}

	public TerminalRedencion getTerminalRedencion() {
		return terminalRedencion;
	}

	public void setTerminalRedencion(TerminalRedencion terminalRedencion) {
		this.terminalRedencion = terminalRedencion;
	}

	@Override
	public String toString() {
		return "PaseConsumido [id=" + id + ", apartadosUsuario=" + apartadosUsuario + ", paseUsuario=" + paseUsuario
				+ ", fechaRedencion=" + fechaRedencion + ", terminalRedencion=" + terminalRedencion +"]";
		
	}
	
	
	
}
