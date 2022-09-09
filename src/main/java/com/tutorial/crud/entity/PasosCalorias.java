package com.tutorial.crud.entity;
 
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "pasos_calorias")
@Immutable
public class PasosCalorias {

	@Id
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private int id_cliente;
	
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private float calorias;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private int pasos;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private String nombre;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private byte[] imagen;

	public int getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}

	public float getCalorias() {
		return calorias;
	}

	public void setCalorias(float calorias) {
		this.calorias = calorias;
	}

	public int getPasos() {
		return pasos;
	}

	public void setPasos(int pasos) {
		this.pasos = pasos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}
	
	
	
}
