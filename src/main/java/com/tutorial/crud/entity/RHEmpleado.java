/*En este paquete tenemos nuestro clase Foto.java y utilizaremos las @anotaciones 
 * JPA para relacionarla con nuestra tabla Cliente, quedaría de la siguiente forma:
 *	@autor: Daniel García Velasco 
 * 			Abimael Rueda Galindo
 *	@version: 1
 *12/07/2021
*/

package com.tutorial.crud.entity;

//Librería 
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
 
@Entity //Sirve únicamente para indicarle a JPA que esa clase es una Entity.
@Table(name="rh_empleado") //se utiliza para poner el nombre real de la tabla en la base de datos
public class RHEmpleado 
{
	@Id //Define la llave primaria.
	@Column(name="id") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private int id; //Variables
	
	@Column(name="empleado") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private String empleado;
	
	@Column(name="iniciales") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private String iniciales;
	
	@Column(name="activo") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private String activo;
	
	@Column(name="club") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private String club;
	
	@Column(name="departamento") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private String departamento;
	
	@Column(name="puesto") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private String puesto;
	
	@Column(name="clave_externa") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private String claveExterna;
	
	@Column(name="id_empleado") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private int idEmpleado;
	
	@Column(name="rfc") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private String rfc;
	
	@Column(name="curp") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private String curp;
	
	@Column(name="imss") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private String imss;
	
	@Column(name="empleado_tipo") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private String empleadoTipo;
	
	@Column(name="fecha_alta") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private Date fechaAlta;
	
	@OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL)
    List<RHSolicitud> solicitudes;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmpleado() {
		return empleado;
	}

	public void setEmpleado(String empleado) {
		this.empleado = empleado;
	}

	public String getIniciales() {
		return iniciales;
	}

	public void setIniciales(String iniciales) {
		this.iniciales = iniciales;
	}

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	public String getClub() {
		return club;
	}

	public void setClub(String club) {
		this.club = club;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public String getClaveExterna() {
		return claveExterna;
	}

	public void setClaveExterna(String claveExterna) {
		this.claveExterna = claveExterna;
	}

	public int getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getImss() {
		return imss;
	}

	public void setImss(String imss) {
		this.imss = imss;
	}

	public String getEmpleadoTipo() {
		return empleadoTipo;
	}

	public void setEmpleadoTipo(String empleadoTipo) {
		this.empleadoTipo = empleadoTipo;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public List<RHSolicitud> obtenerSolicitudes() {
		return solicitudes;
	}

	public void setSolicitudes(List<RHSolicitud> solicitudes) {
		this.solicitudes = solicitudes;
	}


	@Override
	public String toString() {
		return "RHEmpleado [id=" + id + ", empleado=" + empleado + ", iniciales=" + iniciales + ", activo=" + activo
				+ ", club=" + club + ", departamento=" + departamento + ", puesto=" + puesto + ", claveExterna="
				+ claveExterna + ", idEmpleado=" + idEmpleado + ", rfc=" + rfc + ", curp=" + curp + ", imss=" + imss
				+ ", empleadoTipo=" + empleadoTipo + ", fechaAlta=" + fechaAlta + "]";
	}


	
}
