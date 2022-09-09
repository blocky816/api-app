package com.tutorial.crud.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="carro") //Se utiliza para poner el nombre real de la tabla en la base de datos
public class Carro {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "placa")
	private String placa;
	
	@Column(name = "color")
	private String color;

	@Column(name = "marca")
	private String marca;
	
	@Column(name = "no_serie")
	private String noSerie;

	@Column(name = "anio")
	private int anio;
	
	@Column(name = "vigencia")
	private Date vigencia;
	
	@Column(name = "modelo")
	private String modelo;

	@Column(name = "activo")
	private boolean activo=true;
	
    @ManyToOne()
    @JoinColumn(name = "id_parking")
    private ParkingUsuario parkingUsuario;
	
    
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Date getVigencia() {
		return vigencia;
	}

	public void setVigencia(Date vigencia) {
		this.vigencia = vigencia;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public void setParkingUsuario(ParkingUsuario parkingUsuario) {
		this.parkingUsuario = parkingUsuario;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	@Override
	public String toString() {
		return "Carro [idVentaDetalle=" + id + ", placa=" + placa + ", color=" + color + ", marca=" + marca
				+ ", noSerie=" + noSerie + ", anio=" + anio + ", vigencia=" + vigencia + ", modelo=" + modelo + "]";
	}	
}
