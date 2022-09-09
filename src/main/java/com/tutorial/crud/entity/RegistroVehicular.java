package com.tutorial.crud.entity;
 
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "registro_vehicular")
@Immutable
public class RegistroVehicular {

	@Id
	 @Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private int id;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private int id_cliente;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private long membresia;

	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private String nombre_completo;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private String placa;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private String color;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private String marca;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private String modelo;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private String club;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private String noSerie;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private int anio;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private String vigencia;

	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private boolean parking;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private String chip;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}

	public long getMembresia() {
		return membresia;
	}

	public void setMembresia(long membresia) {
		this.membresia = membresia;
	}

	public String getNombre_completo() {
		return nombre_completo;
	}

	public void setNombre_completo(String nombre_completo) {
		this.nombre_completo = nombre_completo;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getClub() {
		return club;
	}

	public void setClub(String club) {
		this.club = club;
	}

	public String getNoSerie() {
		return noSerie;
	}

	public void setNoSerie(String noSerie) {
		this.noSerie = noSerie;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public String getVigencia() {
		return vigencia;
	}

	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}

	public boolean isParking() {
		return parking;
	}

	public void setParking(boolean parking) {
		this.parking = parking;
	}



	public String getChip() {
		return chip;
	}

	public void setChip(String chip) {
		this.chip = chip;
	}

	@Override
	public String toString() {
		return "RegistroVehicular [id=" + id + ", id_cliente=" + id_cliente + ", membresia=" + membresia
				+ ", nombre_completo=" + nombre_completo + ", placa=" + placa + ", color=" + color + ", marca=" + marca
				+ ", modelo=" + modelo + ", club=" + club + ", noSerie=" + noSerie + ", anio=" + anio + ", vigencia="
				+ vigencia + ", parking=" + parking +  "]";
	}


	

	
}
