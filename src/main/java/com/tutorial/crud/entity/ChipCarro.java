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
@Table(name = "clases")
@Immutable
public class ChipCarro {

	@Id //Define la llave primaria.
    @Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private int id;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private String club;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private String idChip;

	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private long idParking;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private int idCliente;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private int idEmpleado;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private String marca;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private String modelo;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private String noSerie;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private String placa;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private String vigencia;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private String color;

	public int getId() {
		return id;
	}

	public String getClub() {
		return club;
	}

	public String getIdChip() {
		return idChip;
	}

	public long getIdParking() {
		return idParking;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public String getMarca() {
		return marca;
	}

	public String getModelo() {
		return modelo;
	}

	public String getNoSerie() {
		return noSerie;
	}

	public String getPlaca() {
		return placa;
	}

	public String getVigencia() {
		return vigencia;
	}

	public String getColor() {
		return color;
	}

	public int getIdEmpleado() {
		return idEmpleado;
	}

	@Override
	public String toString() {
		return "ChipCarro [id=" + id + ", club=" + club + ", idChip=" + idChip + ", idParking=" + idParking
				+ ", idCliente=" + idCliente + ", marca=" + marca + ", modelo=" + modelo + ", noSerie=" + noSerie
				+ ", placa=" + placa + ", vigencia=" + vigencia + ", color=" + color + "]";
	}
	
	

	
}
