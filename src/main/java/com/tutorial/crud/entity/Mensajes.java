/*En este paquete tenemos nuestro clase Mensajes.java y utilizaremos las @anotaciones 
 * JPA para relacionarla con nuestra tabla Mensajes, quedaría de la siguiente forma:
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
@Table(name="mensajes") //se utiliza para poner el nombre real de la tabla en la base de datos
public class Mensajes 
{
	/*@Id
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name="idMensaje", unique = true)*/
	@Id //Define la llave primaria.
	@Column(name="id_mensaje") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private int IdMensaje;
	
	@Column(name="descripcion")
	private String Descripcion;
	
	@Column(name="activo")
	private boolean Activo;
	
	@Column(name="fechacreacion")
	private Date FechaCreacion;
	
	@Column(name="fechamodificacion")
	private Date FechaModificacion;
	
	@Column(name="id_cliente")
    private int cliente;
 
	//Se generaron todos los Getters y Setters.
	public int getCliente() {
		return cliente;
	}

	public void setCliente(int cliente) {
		this.cliente = cliente;
	}

	public Mensajes() {}
	
	public Mensajes( String Descripcion) 
	{
		this.Descripcion = Descripcion;
		Activo = true;
		FechaCreacion = new Date();
	}

	public int getIdMensaje() 
	{
		return IdMensaje;
	}

	public void setIdMensaje(int idMensaje) 
	{
		IdMensaje = idMensaje;
	}

	public String getDescripcion() 
	{
		return Descripcion;
	}

	public void setDescripcion(String descripcion) 
	{
		Descripcion = descripcion;
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
		return "Mensajes [IdMensaje=" + IdMensaje + ", Descripcion=" + Descripcion + ", Activo=" + Activo
				+ ", FechaCreacion=" + FechaCreacion + ", FechaModificacion=" + FechaModificacion + ",idcliente"+cliente+"]";
	}
	
}
