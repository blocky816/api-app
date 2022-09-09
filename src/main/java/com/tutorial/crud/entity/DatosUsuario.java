package com.tutorial.crud.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="datos_usuario") //Se utiliza para poner el nombre real de la tabla en la base de datos
public class DatosUsuario {
	
	@Id //Define la llave primaria.
    @GeneratedValue(generator = "UUID") //Se estableció un tipo de variable UUID 
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private UUID id;

	@Column(name = "tipo")
	private String tipo;

	@Column(name = "clave")
	private String clave;	

	@Column(name = "id_cliente")
	private int idCliente;

	@Column(name="token") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private String token;
	
	@Column(name="id_tarjeta") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private String idTarjeta;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getIdTarjeta() {
		return idTarjeta;
	}

	public void setIdTarjeta(String idTarjeta) {
		this.idTarjeta = idTarjeta;
	}

	public UUID getId() {
		return id;
	}

	

	
}
