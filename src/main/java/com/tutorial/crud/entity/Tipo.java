package com.tutorial.crud.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="tipo")
public class Tipo {
	@Id //Define la llave primaria.
    @GeneratedValue(generator = "UUID") //Se estableció un tipo de variable UUID 
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id_apartados", updatable = false, nullable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private UUID id;
	
	@Column(name = "nombre")
	private String nombre;
	
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public void setCreated(Date created) {
		this.created = created;
	}


	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}


	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public UUID getId() {
		return id;
	}

	public boolean isActivo() { return activo; }

	@Override
	public String toString() {
		return "Tipo [id=" + id + ", nombre=" + nombre + ", createdBy=" + createdBy + ", created=" + created
				+ ", updatedBy=" + updatedBy + ", updated=" + updated + ", activo=" + activo + "]";
	}
	
	
}
