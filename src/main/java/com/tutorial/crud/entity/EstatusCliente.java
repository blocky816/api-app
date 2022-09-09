/*En este paquete tenemos nuestro clase EstatusCliente.java y utilizaremos las @anotaciones 
 * JPA para relacionarla con nuestra tabla EstatusCliente, quedaría de la siguiente forma:
 *	@autor: Daniel García Velasco 
 * 			Abimael Rueda Galindo
 *	@version: 1
 *12/07/2021
*/

package com.tutorial.crud.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity //Sirve únicamente para indicarle a JPA que esa clase es una Entity.
@Table(name="estatuscliente") //se utiliza para poner el nombre real de la tabla en la base de datos
public class EstatusCliente 
{
	@Id  //Define la llave primaria.
	@Column(name="id_statucliente") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	int IdStatusCliente;
	
	@Column(name="nombre")
	String Nombre;
	
	@Column(name="activo")
	private boolean Activo;
	
	@Column(name="fechacreacion")
	private Date FechaCreacion;
	
	@Column(name="fechamodificacion")
	private Date FechaModificacion;

	//Se genero el constructor
	public EstatusCliente() {}
	
	public EstatusCliente(String Nombre, Boolean Activo, Date FechaCreacion) 
	{
		this.Nombre = Nombre;
		Activo = true;
		FechaCreacion = new Date();
	}
	//Se generaron todos los Getters y Setters.

	public int getIdStatusCliente() 
	{
		return IdStatusCliente;
	}

	public void setIdStatusCliente(int idStatusCliente) 
	{
		IdStatusCliente = idStatusCliente;
	}

	public String getNombre() 
	{
		return Nombre;
	}

	public void setNombre(String nombre) 
	{
		Nombre = nombre;
	}

	public boolean isActivo() 
	{
		return Activo;
	}

	public void setActivo(boolean activo) 
	{
		Activo = activo;
	}

	public Date getFechaCreacion() 
	{
		return FechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) 
	{
		FechaCreacion = fechaCreacion;
	}

	public Date getFechaModificacion() 
	{
		return FechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) 
	{
		FechaModificacion = fechaModificacion;
	}

}
