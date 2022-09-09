/*En este paquete tenemos nuestro clase EstatusMembresia.java y utilizaremos las @anotaciones 
 * JPA para relacionarla con nuestra tabla Categoria, quedaría de la siguiente forma:
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
@Table(name="estatusmembresia") //se utiliza para poner el nombre real de la tabla en la base de datos
public class EstatusMembresia 
{
	/*@Id
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name="idMensaje", unique = true)*/
	@Id //	Define la llave primaria 
	@Column(name="id_estatusmembresia") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private int IdEstatusMembresia;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="activo")
	private boolean Activo;
	
	@Column(name="fechacreacion")
	private Date FechaCreacion;
	
	@Column(name="fechamodificacion")
	private Date FechaModificacion;
	
	//Constructor
	public EstatusMembresia() {}
	
	public EstatusMembresia( String Descripcion) 
	{
		this.nombre = Descripcion;
		Activo = true;
		FechaCreacion = new Date();
	}

	//Se generaron todos los Getters y Setters.
	public int getIdEstatusMembresia() 
	{
		return IdEstatusMembresia;
	}

	public void setIdEstatusMembresia(int idMensaje) 
	{
		IdEstatusMembresia = idMensaje;
	}

	public String getNombre() 
	{
		return nombre;
	}

	public void setDescripcion(String descripcion) 
	{
		nombre = descripcion;
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
