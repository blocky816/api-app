/**En este paquete tenemos nuestro clase TipoCliente.java y utilizaremos las @anotaciones 
 * JPA para relacionarla con nuestra tabla Tipocliente, quedaría de la siguiente forma:
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
@Table(name="tipocliente") //se utiliza para poner el nombre real de la tabla en la base de datos
public class TipoCliente 
{
	/*@Id
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name="idMensaje", unique = true)*/
	@Id //Define la lave primaria 
	@Column(name="idtipocliente")  //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private int IdTipoCliente;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="activo")
	private boolean Activo;
	
	@Column(name="fechacreacion")
	private Date FechaCreacion;
	
	@Column(name="fechamodificacion")
	private Date FechaModificacion;
	
	//Constructor
	public TipoCliente() {}
	
	public TipoCliente( String Descripcion) 
	{
		this.nombre = Descripcion;
		Activo = true;
		FechaCreacion = new Date();
	}

	//Getters y setters
	public int getIdTipoCliente() 
	{
		return IdTipoCliente;
	}

	public void setIdTipoCliente(int idMensaje) 
	{
		IdTipoCliente = idMensaje;
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
