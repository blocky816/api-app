package com.tutorial.crud.entity;


import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="banda") //Se utiliza para poner el nombre real de la tabla en la base de datos
public class Banda {
	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "modelo")
	private String modelo;
	
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	@Override
	public String toString() {
		return "Banda [id=" + id + ", modelo=" + modelo + ", fechaRegistro=" + fechaRegistro + "]";
	}
	
	
}
