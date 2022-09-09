/*En este paquete tenemos nuestro clase Horario.java y utilizaremos las @anotaciones 
 * JPA para relacionarla con nuestra tabla Horario, quedaría de la siguiente forma:
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
@Table(name="horario") //se utiliza para poner el nombre real de la tabla en la base de datos
public class Horario {

	@Id //define una llave primaria
	@Column(name="idhorario") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private int idHorario;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="idlunes")
	private Lunes idLunes;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="idmartes")
	private Martes idMartes;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="idmiercoles")
	private Miercoles idMiercoles;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="idjueves")
	private Jueves idJueves;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="idviernes")
	private Viernes idViernes;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="idsabado")
	private Sabado idSabado;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="iddomingo")
	private Domingo idDomingo;
	
	@Override
	public String toString() {
		return "Horario [idHorario=" + idHorario + ", idLunes=" + idLunes + ", idMartes=" + idMartes + ", idMiercoles="
				+ idMiercoles + ", idJueves=" + idJueves + ", idViernes=" + idViernes + ", idSabado=" + idSabado
				+ ", idDomingo=" + idDomingo + ", activo=" + activo + ", fechaCreacion=" + fechaCreacion
				+ ", fechaModificacion=" + fechaModificacion + "]";
	}

	@Column(name="activo")
	private boolean activo;
	
	@Column(name="fechacreacion")
	private Date fechaCreacion;
	
	@Column(name="fechamodificacion")
	private Date fechaModificacion;

	public Lunes getIdLunes() {
		return idLunes;
	}

	public void setIdLunes(Lunes idLunes) {
		this.idLunes = idLunes;
	}

	public Martes getIdMartes() {
		return idMartes;
	}

	public void setIdMartes(Martes idMartes) {
		this.idMartes = idMartes;
	}

	public Miercoles getIdMiercoles() {
		return idMiercoles;
	}

	public void setIdMiercoles(Miercoles idMiercoles) {
		this.idMiercoles = idMiercoles;
	}

	public Jueves getIdJueves() {
		return idJueves;
	}

	public void setIdJueves(Jueves idJueves) {
		this.idJueves = idJueves;
	}

	public Viernes getIdViernes() {
		return idViernes;
	}

	public void setIdViernes(Viernes idViernes) {
		this.idViernes = idViernes;
	}

	public Sabado getIdSabado() {
		return idSabado;
	}

	public void setIdSabado(Sabado idSabado) {
		this.idSabado = idSabado;
	}

	public Domingo getIdDomingo() {
		return idDomingo;
	}

	public void setIdDomingo(Domingo idDomingo) {
		this.idDomingo = idDomingo;
	}

	public int getIdHorario() {
		return idHorario;
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

	public void setIdHorario(int idHorario) {
		this.idHorario = idHorario;
	}
	
	
}
