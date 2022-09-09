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
@Table(name="registro_facturacion_global") //Se utiliza para poner el nombre real de la tabla en la base de datos
public class RegistroFacturaGlobal {
	@Id //Define la llave primaria.
    @GeneratedValue(generator = "UUID") //Se estableció un tipo de variable UUID 
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private UUID id;
	

	@Column(name = "solicito")
	private String solicito;

	@Column(name = "fecha_solicitud")
	private Date fecha_solicitud;

	public String getSolicito() {
		return solicito;
	}

	public void setSolicito(String solicito) {
		this.solicito = solicito;
	}

	public Date getFecha_solicitud() {
		return fecha_solicitud;
	}

	public void setFecha_solicitud(Date fecha_solicitud) {
		this.fecha_solicitud = fecha_solicitud;
	}
	
	
	
}
