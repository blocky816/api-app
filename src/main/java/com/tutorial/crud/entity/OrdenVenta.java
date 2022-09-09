/*En este paquete tenemos nuestro clase OrdenVenta.java y utilizaremos las @anotaciones 
 * JPA para relacionarla con nuestra tabla OrdenVenta, quedaría de la siguiente forma:
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
@Table(name="ordenventa") //se utiliza para poner el nombre real de la tabla en la base de datos
public class OrdenVenta 
{
	/*@Id
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name="idOV", unique = true)*/
	@Id //Define la llave primaria.
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idov") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private int IdOV;
	
	@Column(name="idcliente")
	private int IdCliente;
	
	@Column(name="idclub")
	private int IdClub;
	
	//@ManyToOne(cascade=CascadeType.ALL)
	//@JoinColumn(name="idVenta")
	@Column(name="idventa")
	private int IdVenta;
	
	@Column(name="cantidad")
	private int Cantidad;
	
	@Column(name="idproductoservicio")
	private int IdProductoServicio;
	
	@Column(name="observaciones")
	private String Observaciones;
	
	@Column(name="descuentoporciento")
	private float DescuentoPorCiento;
	
	@Column(name="fechainicio")
	private Date FechaInicio;
	
	@Column(name="fechafinal")
	private Date FechaFinal;
	
	@Column(name="cobroproporcional")
	private float CobroProporcional;
	
	@Column(name="activo")
	private boolean Activo;
	
	@Column(name="fechacreacion")
	private Date FechaCreacion;
	
	@Column(name="fechamodificacion")
	private Date FechaModificacion;
	
	//El constructor se genera
	public OrdenVenta() {}
	
	public OrdenVenta(int IdCliente, int IdClub, int IdVenta, int Cantidad, int IdProductoServicio,
			String Observaciones, float DescuentoPorCiento, Date FechaInicio, Date FechaFinal, float CobroProporcional) 
	{
		this.IdCliente = IdCliente;
		this.IdClub = IdClub;
		this.IdOV = IdVenta;
		this.Cantidad = Cantidad;
		this.IdProductoServicio = IdProductoServicio;
		this.Observaciones = Observaciones;
		this.DescuentoPorCiento = DescuentoPorCiento;
		this.FechaInicio = FechaInicio;
		this.FechaFinal = FechaFinal;
		this.CobroProporcional = CobroProporcional;
		Activo = true;
		FechaCreacion = new Date();
	}
	//Se genera los getters y setters
	public int getIdOV()
	{
		return IdOV;
	}

	public void setIdOV(int idOV) 
	{
		IdOV = idOV;
	}

	public int getIdCliente()
	{
		return IdCliente;
	}

	public void setIdCliente(int idCliente)
	{
		IdCliente = idCliente;
	}

	public int getIdClub() 
	{
		return IdClub;
	}

	public void setIdClub(int idClub) 
	{
		IdClub = idClub;
	}

	public int getIdVenta() 
	{
		return IdVenta;
	}

	public void setIdVenta(int idVenta)
	{
		IdVenta = idVenta;
	}

	public int getCantidad()
	{
		return Cantidad;
	}

	public void setCantidad(int cantidad) 
	{
		Cantidad = cantidad;
	}

	public int getIdProductoServicio()
	{
		return IdProductoServicio;
	}

	public void setIdProductoServicio(int idProductoServicio)
	{
		IdProductoServicio = idProductoServicio;
	}

	public String getObservaciones() 
	{
		return Observaciones;
	}

	public void setObservaciones(String observaciones) 
	{
		Observaciones = observaciones;
	}

	public float getDescuentoPorCiento() 
	{
		return DescuentoPorCiento;
	}

	public void setDescuentoPorCiento(float descuentoPorCiento)
	{
		DescuentoPorCiento = descuentoPorCiento;
	}

	public Date getFechaInicio()
	{
		return FechaInicio;
	}

	public void setFechaInicio(Date fechaInicio)
	{
		FechaInicio = fechaInicio;
	}

	public Date getFechaFinal()
	{
		return FechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) 
	{
		FechaFinal = fechaFinal;
	}

	public float getCobroProporcional() 
	{
		return CobroProporcional;
	}

	public void setCobroProporcional(float cobroProporcional)
	{
		CobroProporcional = cobroProporcional;
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
