/*En este paquete tenemos nuestro clase Recibo.java y utilizaremos las @anotaciones 
 * JPA para relacionarla con nuestra tabla Recibo, quedaría de la siguiente forma:
 *	@autor: Daniel García Velasco 
 * 			Abimael Rueda Galindo
 *	@version: 1
 *12/07/2021
*/
package com.tutorial.crud.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity //Sirve únicamente para indicarle a JPA que esa clase es una Entity.
@Table(name="recibo") //se utiliza para poner el nombre real de la tabla en la base de datos
public class Recibo 
{
	/*@Id
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name="folio", unique = true)*/
	@Id //Define la llave primaria
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="folio") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private String Folio;
	
	@Column(name="idcliente")
	private int IdCliente;
	
	@Column(name="importe")
	private float Importe;
	
	@Column(name="fecha")
	private Date Fecha;
	
	@Column(name="activo")
	private boolean Activo;
	
	@Column(name="fechacreacion")
	private Date FechaCreacion;
	
	@Column(name="fechamodificacion")
	private Date FechaModificacion;
	
	//Constructor
	public Recibo() {}
	
	public Recibo(int IdCliente, float Importe, Date Fecha) 
	{
		this.IdCliente = IdCliente;
		this.Importe = Importe;
		this.Fecha = Fecha;
		Activo = true;
		FechaCreacion = new Date();
	}
	
	//Getters y setters
	public String getFolio() 
	{
		return Folio;
	}

	public void setFolio(String folio) 
	{
		Folio = folio;
	}

	public int getIdCliente() 
	{
		return IdCliente;
	}

	public void setIdCliente(int idCliente)
	{
		IdCliente = idCliente;
	}

	public float getImporte()
	{
		return Importe;
	}

	public void setImporte(float importe) 
	{
		Importe = importe;
	}

	public Date getFecha() 
	{
		return Fecha;
	}

	public void setFecha(Date fecha) 
	{
		Fecha = fecha;
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
