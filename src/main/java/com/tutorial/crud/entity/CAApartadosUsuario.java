package com.tutorial.crud.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="ca_apartados_usuario") //Se utiliza para poner el nombre real de la tabla en la base de datos
public class CAApartadosUsuario {
	@Id //Define la llave primaria.
    @GeneratedValue(generator = "UUID") //Se estableció un tipo de variable UUID 
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private UUID id;	
	
	@ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "idcliente")
	private Cliente cliente;
	
	@ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "id_apartados")
	private CAApartados apartados;
	
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

	public UUID obtenerId() {
		return id;
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

	public int getCliente() {
		return cliente.getIdCliente();
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public UUID getApartados() {
		return apartados.getId();
	}

	public void setApartados(CAApartados apartados) {
		this.apartados = apartados;
	}

	@Override
	public String toString() {
		return "CAApartadosUsuario [ cliente=" + cliente.getIdCliente() + ", apartados=" + apartados.getId() + ", createdBy="
				+ createdBy + ", created=" + created + ", updatedBy=" + updatedBy + ", updated=" + updated + ", activo="
				+ activo + "]";
	}

	public UUID getId() {
		return id;
	}


	
}
