package com.tutorial.crud.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="terminal_redencion")
public class TerminalRedencion {
	@Id //Define la llave primaria.
    @Column(name = "id") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private int id;
	
	@Column(name = "nombre")
	private String nombre;
	
	@ManyToOne(optional = false)
    @JoinColumn(name="id_sala")
    private CASala sala;
	
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
	
	@Column(name = "activo")
	private boolean activo=true;	
	
	public TerminalRedencion(int id) {
		this.id = id;
		this.activo=true;
		this.created=new Date();
		this.updated=new Date();
		this.createdBy="admin";
		this.updatedBy="admin";
	}
		
	public CASala obtenerSala() {
		return sala;
	}

	public void setSala(CASala sala) {
		this.sala = sala;
	}

	public TerminalRedencion() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

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

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	@Override
	public String toString() {
		return "TerminalRedencion [id=" + id + ", nombre=" + nombre + ", createdBy=" + createdBy + ", created="
				+ created + ", updatedBy=" + updatedBy + ", updated=" + updated + ", activo=" + activo + "]";
	}
	
	
}
