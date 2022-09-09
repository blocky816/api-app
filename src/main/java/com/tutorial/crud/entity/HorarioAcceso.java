/*En este paquete tenemos nuestro clase HorarioAcceso.java y utilizaremos las @anotaciones 
 * JPA para relacionarla con nuestra tabla Categoria, quedaría de la siguiente forma:
 *	@autor: Daniel García Velasco 
 * 			Abimael Rueda Galindo
 *	@version: 1
 *12/07/2021
*/
package com.tutorial.crud.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;



@Entity //Sirve únicamente para indicarle a JPA que esa clase es una Entity.
@Table(name="horarioacceso")  //se utiliza para poner el nombre real de la tabla en la base de datos
public class HorarioAcceso {

	@Id //Define la llave primaria.
	@Column(name="idhorarioacceso") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private int idHorarioAcceso;
	
	@OneToOne(cascade=CascadeType.ALL) //Relación uno a uno
	@JoinColumn(name="idhorario")
	private Horario horario;
	
	@Column(name="activo")
	private boolean activo;
	
	@Column(name="fechacreacion")
	private Date fechaCreacion;
	
	@Column(name="fechamodificacion")
	private Date fechaModificacion;
	
	@Column(name="nombre")
	private String nombre;
	

	//Se generaron todos los Getters y Setters.
	public int getIdHorarioAcceso() {
		return idHorarioAcceso;
	}
	

	public void setIdHorarioAcceso(int idHorarioAcceso) {
		this.idHorarioAcceso = idHorarioAcceso;
	}

	public Horario getHorario() {
		return horario;
	}

	public void setHorario(Horario horario) {
		this.horario = horario;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "HorarioAcceso [idHorarioAcceso=" + idHorarioAcceso + ", horario=" + horario + ", activo=" + activo
				+ ", fechaCreacion=" + fechaCreacion + ", fechaModificacion=" + fechaModificacion + ", nombre=" + nombre
				+ "]";
	}
		
}
