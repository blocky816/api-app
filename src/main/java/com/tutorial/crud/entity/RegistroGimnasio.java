package com.tutorial.crud.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name="registro_gimnasio") 
public class RegistroGimnasio {
	@Id //Define la llave primaria.
    @GeneratedValue(generator = "UUID") //Se estableció un tipo de variable UUID 
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private UUID id;
	
	@ManyToOne(optional = false)
    @JoinColumn(name="id_cliente")
	private Cliente idCliente;

	@ManyToOne(optional = true)
    @JoinColumn(name="id_terminal")
	private TerminalRedencion idTerminal;
	
	@ManyToOne(optional = true)
    @JoinColumn(name="id_apartados")
	private CAApartados idApartados;
	
	@Column(name = "registro_acceso") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	@Temporal(TemporalType.TIMESTAMP)
	private Date registroAcceso = new Date();

	public Cliente getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Cliente idCliente) {
		this.idCliente = idCliente;
	}

	public TerminalRedencion getIdTerminal() {
		return idTerminal;
	}

	public void setIdTerminal(TerminalRedencion idTerminal) {
		this.idTerminal = idTerminal;
	}

	public CAApartados getIdApartados() {
		return idApartados;
	}

	public void setIdApartados(CAApartados idApartados) {
		this.idApartados = idApartados;
	}

	public Date getRegistroAcceso() {
		return registroAcceso;
	}

	public void setRegistroAcceso(Date registroAcceso) {
		this.registroAcceso = registroAcceso;
	}
	
	
	
}
