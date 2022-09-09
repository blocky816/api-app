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
@Table(name="agenda_reservas_usuario") //Se utiliza para poner el nombre real de la tabla en la base de datos
public class AgendaReservasUsuario {
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
    @JoinColumn(name = "id_reservas")
	private AgendaReservas reservas;
	
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

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public UUID getId() {
		return id;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void setReservas(AgendaReservas reservas) {
		this.reservas = reservas;
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




	
}
