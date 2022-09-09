/*En este paquete tenemos nuestro clase PedidoDetalle.java y utilizaremos las @anotaciones 
 * JPA para relacionarla con nuestra tabla PedidoDetalle, quedaría de la siguiente forma:
 *	@autor: Daniel García Velasco 
 * 			Abimael Rueda Galindo
 *	@version: 1
 *12/07/2021
*/
package com.tutorial.crud.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity //Sirve únicamente para indicarle a JPA que esa clase es una Entity.
@Table(name="pedidodetalle") //se utiliza para poner el nombre real de la tabla en la base de datos
@IdClass(PedidoDetalleId.class) //define llaves primarias compuestas, y consiste en crear una clase adicional únicamente con los campos que corresponden a la llave primaria.
public class PedidoDetalle implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*@Id
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name="idPedidoDetalle", unique = true)*/
	@Id //Define la llave primaria.
	@Column(name="nopedido") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private int NoPedido;

	@Id
	@Column(name="idordenventadetalle")
	private int IdOrdenVentaDetalle;
	
	@Column(name="concepto")
	private String Concepto;
	
	@Column(name="cantidad")
	private int Cantidad;
	
	@Column(name="importe")
	private float Importe;
	
	@Column(name="fechainicio")
	private Date FechaInicio;
	
	@Column(name="fechafin")
	private Date FechaFin;
	
	@Column(name="idordendeventa")
	private int IdOrdenDeVenta;
	
	@Column(name="idprodserv")
	private int IdProdServ;
	
	@Column(name="preciounitario")
	private float PrecioUnitario;
	
	@Column(name="idcasillero")
	private int IdCasillero;
	
	@Column(name="descuento")
	private float Descuento;
	
	@Column(name="iva")
	private float IVA;

	@Column(name="subimporte")
	private float SubImporte;
	
	@Column(name="activo")
	private boolean Activo=true;
	
	@Column(name="fechacreacion")
	private Date FechaCreacion=new Date();
	
	@Column(name="fechamodificacion")
	private Date FechaModificacion=new Date();

	@Column(name="idcliente")
	private int IdCliente;
	
	//Constructores
	public PedidoDetalle() {}
	
	//Se generaron todos los Getters y Setters.
	public int getNoPedido() 
	{
		return NoPedido;
	}

	public void setNoPedido(int idPedidoDetalle)
	{
		NoPedido = idPedidoDetalle;
	}

	public int getIdOrdenVentaDetalle()
	{
		return IdOrdenVentaDetalle;
	}

	public void setIdOrdenVentaDetalle(int idOrdenVentaDetalle)
	{
		IdOrdenVentaDetalle = idOrdenVentaDetalle;
	}

	public String getConcepto() 
	{
		return Concepto;
	}

	public void setConcepto(String concepto)
	{
		Concepto = concepto;
	}

	public int getCantidad()
	{
		return Cantidad;
	}

	public void setCantidad(int cantidad) 
	{
		Cantidad = cantidad;
	}

	public float getImporte() 
	{
		return Importe;
	}

	public void setImporte(float importe) 
	{
		this.Importe = importe;
	}

	public Date getFechaInicio()
	{
		return FechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) 
	{
		FechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return FechaFin;
	}

	public void setFechaFin(Date fechaFin)
	{
		FechaFin = fechaFin;
	}

	public int getIdOrdenDeVenta()
	{
		return IdOrdenDeVenta;
	}

	public void setIdOrdenDeVenta(int idOrdenDeVenta) 
	{
		IdOrdenDeVenta = idOrdenDeVenta;
	}

	public int getIdProdServ() 
	{
		return IdProdServ;
	}

	public void setIdProdServ(int idProdServ)
	{
		IdProdServ = idProdServ;
	}

	public float getPrecioUnitario()
	{
		return PrecioUnitario;
	}

	public void setPrecioUnitario(float precioUnitario) 
	{
		PrecioUnitario = precioUnitario;
	}

	public int getIdCasillero()
	{
		return IdCasillero;
	}

	public void setIdCasillero(int idCasillero)
	{
		IdCasillero = idCasillero;
	}

	public float getDescuento()
	{
		return Descuento;
	}

	public void setDescuento(float descuento) 
	{
		Descuento = descuento;
	}

	public float getIVA() 
	{
		return IVA;
	}

	public void setIVA(float iVA) 
	{
		IVA = iVA;
	}

	public float getSubImporte() 
	{
		return SubImporte;
	}

	public void setSubImporte(float subImporte) 
	{
		SubImporte = subImporte;
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

	public int getIdCliente() {
		return IdCliente;
	}

	public void setIdCliente(int idCliente) {
		IdCliente = idCliente;
	}



	@Override
	public String toString() {
		return "PedidoDetalle [NoPedido=" + NoPedido + ", IdOrdenVentaDetalle=" + IdOrdenVentaDetalle + ", Concepto="
				+ Concepto + ", Cantidad=" + Cantidad + ", Importe=" + Importe + ", FechaInicio=" + FechaInicio
				+ ", FechaFin=" + FechaFin + ", IdOrdenDeVenta=" + IdOrdenDeVenta + ", IdProdServ=" + IdProdServ
				+ ", PrecioUnitario=" + PrecioUnitario + ", IdCasillero=" + IdCasillero + ", Descuento=" + Descuento
				+ ", IVA=" + IVA + ", SubImporte=" + SubImporte + ", Activo=" + Activo + ", FechaCreacion="
				+ FechaCreacion + ", FechaModificacion=" + FechaModificacion + ", IdCliente=" + IdCliente + "]";
	}

	
}
