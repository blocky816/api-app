/*En este paquete tenemos nuestro clase Club.java y utilizaremos las @anotaciones 
 * JPA para relacionarla con nuestra tabla Club, quedaría de la siguiente forma:
 *	@autor: Daniel García Velasco 
 * 			Abimael Rueda Galindo
 *	@version: 1
 *12/07/2021
*/
package com.tutorial.crud.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity //Sirve únicamente para indicarle a JPA que esa clase es una Entity.
@Table(name="club") //se utiliza para poner el nombre real de la tabla en la base de datos
public class Club 
{
	/*@Id
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name="idCliente", unique = true)*/
	@Id //Define la llave primaria.
	@Column(name="idclub") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private int IdClub; //Variable
	
	@Column(name="nombre")
	private String Nombre;
	
	@Column(name="activo")
	private boolean Activo;
	
	@Column(name="fechacreacion")
	private Date FechaCreacion;
	
	@Column(name="fechamodificacion")
	private Date FechaModificacion;
	
	@OneToMany(mappedBy="club")
    private List<Caseta> casetas;
	
	
	public List<Caseta> obtenerCasetas() {
		return casetas;
	}

	public void setCasetas(List<Caseta> casetas) {
		this.casetas = casetas;
	}

	//Creación de los constructores
	public Club() {}

	public Club(String Nombre)
	{
		this.Nombre = Nombre;
		Activo = true;
		FechaCreacion = new Date();
	}
	
	//Se generaron todos los Getters y Setters.
	public int getIdClub()
	{
		return IdClub;
	}

	public void setIdClub(int idClub) 
	{
		IdClub = idClub;
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

	@Override
	public String toString() {
		return "Club [IdClub=" + IdClub + ", Nombre=" + Nombre + ", Activo=" + Activo + ", FechaCreacion="
				+ FechaCreacion + ", FechaModificacion=" + FechaModificacion + "]";
	}
	
}
