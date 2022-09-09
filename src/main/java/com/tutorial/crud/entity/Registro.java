package com.tutorial.crud.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="registro") //Se utiliza para poner el nombre real de la tabla en la base de datos
public class Registro {
	@Id //Define la llave primaria.
    @GeneratedValue(generator = "UUID") //Se estableció un tipo de variable UUID 
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private UUID id;

	@Column(name = "id_cliente")
	private int idCliente;
	
	@Column(name = "id_terminal")
	private int idTerminal;
	
	@Column(name = "registro_acceso") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	@Temporal(TemporalType.TIMESTAMP)
	private Date registroAcceso = new Date();

	
	
	public Registro(int idCliente,int idTerminal) {
		this.idCliente = idCliente;
		registroAcceso=new Date();
		this.idTerminal=idTerminal;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public Date getRegistroAcceso() {
		return registroAcceso;
	}

	public void setRegistroAcceso(Date registroAcceso) {
		this.registroAcceso = registroAcceso;
	}

	public int getIdTerminal() {
		return idTerminal;
	}

	public void setIdTerminal(int idTerminal) {
		this.idTerminal = idTerminal;
	}	
	
	
	
}
