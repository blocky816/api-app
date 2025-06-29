/*En este paquete tenemos nuestro clase Categoria.java y utilizaremos las @anotaciones 
 * JPA para relacionarla con nuestra tabla Categoria, quedaría de la siguiente forma:
 *	@autor: Daniel García Velasco 
 * 			Abimael Rueda Galindo
 *	@version: 1
 *12/07/2021
*/

package com.tutorial.crud.entity;

//Librería 
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import javax.persistence.*;

@Entity //Sirve únicamente para indicarle a JPA que esa clase es una Entity.
@Table(name="categoria") //se utiliza para poner el nombre real de la tabla en la base de datos
public class Categoria 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	//@JsonIgnore
	@Column(name="id") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	int id; //Variables
	
	@Column(name="nombre") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	String nombre;
	
	@Column(name="activo") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private boolean Activo;
	
	@Column(name="fechacreacion") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private Date FechaCreacion;
	
	@Column(name="fechamodificacion") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private Date FechaModificacion;

	//Creación de los constructores
	public Categoria() {}
	
	public Categoria(String Nombre, Boolean Activo, Date FechaCreacion) 
	{
		this.nombre = Nombre;
		Activo = true;
		FechaCreacion = new Date();
	}

	//Se generaron todos los Getters y Setters.
	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
	{
		id = id;
	}

	public String getNombre() 
	{
		return nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
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

	@Override
	public String toString() {
		return "Categoria{" +
				"id=" + id +
				", nombre='" + nombre + '\'' +
				", Activo=" + Activo +
				", FechaCreacion=" + FechaCreacion +
				", FechaModificacion=" + FechaModificacion +
				'}';
	}
}
