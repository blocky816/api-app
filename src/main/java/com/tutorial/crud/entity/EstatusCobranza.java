/*En este paquete tenemos nuestro clase EstatusCobranza.java y utilizaremos las @anotaciones 
 * JPA para relacionarla con nuestra tabla EstatusCobranza, quedaría de la siguiente forma:
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
@Table(name="estatuscobranza") //se utiliza para poner el nombre real de la tabla en la base de datos
public class EstatusCobranza 
{
	/*@Id
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name="idMensaje", unique = true)*/
	@Id //Define la llave primaria 
	@Column(name="id_estatuscobranza")  //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private int IdEstatusCobranza;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="activo")
	private boolean Activo;
	
	@Column(name="fechacreacion")
	private Date FechaCreacion;
	
	@Column(name="fechamodificacion")
	private Date FechaModificacion;
	
	//contructor
	public EstatusCobranza() {}
	
	public EstatusCobranza( String Descripcion) 
	{
		this.nombre = Descripcion;
		Activo = true;
		FechaCreacion = new Date();
	}

	//Se generó los getters y setters
	public int getIdEstatusCobranza() 
	{
		return IdEstatusCobranza;
	}

	public void setIdEstatusCobranza(int idMensaje) 
	{
		IdEstatusCobranza = idMensaje;
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
