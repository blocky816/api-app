/*En este paquete tenemos nuestro clase Pase.java y utilizaremos las @anotaciones 
 * JPA para relacionarla con nuestra tabla Pase, quedaría de la siguiente forma:
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
@Table(name="pase") //se utiliza para poner el nombre real de la tabla en la base de datos
public class Pase 
{
	/*@Id
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name="IdPase", unique = true)*/
	@Id //Define la llave primaria.
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idpase") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private int IdPase;
	
	@Column(name="idprodserv")
	private int IdProdServ;
	
	@Column(name="fecharcaptura")
	private int FechaCaptura;
	
	@Column(name="concepto")
	private int Concepto;
	
	@Column(name="ventadetalle")
	private int VentaDetalle;
	
	@Column(name="activo")
	private boolean Activo;
	
	@Column(name="fechacreacion")
	private Date FechaCreacion;
	
	@Column(name="fechamodificacion")
	private Date FechaModificacion;
	
	//COnstructor
	public Pase() {}
	
	public Pase(int IdProdServ, int FechaCaptura, int Concepto, int VentaDetalle) 
	{
		this.IdProdServ = IdProdServ;
		this.FechaCaptura = FechaCaptura;
		this.Concepto = Concepto;
		this.VentaDetalle = VentaDetalle;
		Activo = true;
		FechaCreacion = new Date();
	}

	//Getters y setters
	public int getIdPase() 
	{
		return IdPase;
	}

	public void setIdPase(int idPase)
	{
		IdPase = idPase;
	}

	public int getIdProdServ()
	{
		return IdProdServ;
	}

	public void setIdProdServ(int idProdServ) 
	{
		IdProdServ = idProdServ;
	}

	public int getFechaCaptura()
	{
		return FechaCaptura;
	}

	public void setFechaCaptura(int fechaCaptura)
	{
		FechaCaptura = fechaCaptura;
	}

	public int getConcepto() 
	{
		return Concepto;
	}

	public void setConcepto(int concepto) 
	{
		Concepto = concepto;
	}

	public int getVentaDetalle()
	{
		return VentaDetalle;
	}

	public void setVentaDetalle(int ventaDetalle) 
	{
		VentaDetalle = ventaDetalle;
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
