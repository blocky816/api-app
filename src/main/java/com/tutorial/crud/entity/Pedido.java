/*En este paquete tenemos nuestro clase Pedido.java y utilizaremos las @anotaciones 
 * JPA para relacionarla con nuestra tabla Pedido, quedaría de la siguiente forma:
 *	@autor: Daniel García Velasco 
 * 			Abimael Rueda Galindo
 *	@version: 1
 *12/07/2021
*/

package com.tutorial.crud.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity  //Sirve únicamente para indicarle a JPA que esa clase es una Entity.
@Table(name="pedido") //se utiliza para poner el nombre real de la tabla en la base de datos
public class Pedido
{
	/*@Id
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name="noPedido", unique = true)*/
	@Id //Define la llave primaria.
	@Column(name="nopedido") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private int NoPedido;
	
	@OneToOne(cascade=CascadeType.ALL) //Relacion uno a uno 
	@JoinColumn(name="idpago")
	private Pago IdPago;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="NoPedido") //Relacion de uno a muchos
    private List<PedidoDetalle> pedidoDetalle;
	
	@Column(name="idcliente")
	private int IdCliente;
	
	@Column(name="urlliga")
	private String URLLiga;
	
	@Column(name="status")
	private String Status;
	
	@Column(name="creado")
	private Date Creado;
	
	@Column(name="pagofecha")
	private Date PagoFecha;
	
	@Column(name="canceladofecha")
	private Date CanceladoFecha;
	
	@Column(name="pagado")
	private boolean Pagado;
	
	@Column(name="cancelado")
	private boolean Cancelado;
	
	@Column(name="correocliente")
	private String CorreoCliente;
	
	@Column(name="activo")
	private boolean Activo;
	
	@Column(name="fechacreacion")
	private Date FechaCreacion;
	
	@Column(name="fechamodificacion")
	private Date FechaModificacion;
	
	//Constructor
	public Pedido() {}
	
	//Se generaron todos los Getters y Setters.
	public int getNoPedido() 
	{
		return NoPedido;
	}

	public void setNoPedido(int noPedido) 
	{
		NoPedido = noPedido;
	}

	public Pago getIdPago() 
	{
		return IdPago;
	}

	public void setIdPago(Pago idPago) 
	{
		IdPago = idPago;
	}

	
	public List<PedidoDetalle> getPedidoDetalle() {
		return pedidoDetalle;
	}



	public void setPedidoDetalle(List<PedidoDetalle> pedidoDetalle) {
		this.pedidoDetalle = pedidoDetalle;
	}



	public int getIdCliente()
	{
		return IdCliente;
	}

	public void setIdCliente(int idCliente) 
	{
		IdCliente = idCliente;
	}

	public String getURLLiga() 
	{
		return URLLiga;
	}

	public void setURLLiga(String uRLLiga)
	{
		URLLiga = uRLLiga;
	}

	public String getStatus() 
	{
		return Status;
	}

	public void setStatus(String status) 
	{
		Status = status;
	}

	public Date getCreado() 
	{
		return Creado;
	}

	public void setCreado(Date creado)
	{
		Creado = creado;
	}

	public Date getPagoFecha() 
	{
		return PagoFecha;
	}

	public void setPagoFecha(Date pagoFecha)
	{
		PagoFecha = pagoFecha;
	}

	public Date getCanceladoFecha() 
	{
		return CanceladoFecha;
	}

	public void setCanceladoFecha(Date canceladoFecha) 
	{
		CanceladoFecha = canceladoFecha;
	}

	public boolean isPagado()
	{
		return Pagado;
	}

	public void setPagado(boolean pagado)
	{
		Pagado = pagado;
	}

	public boolean isCancelado() 
	{
		return Cancelado;
	}

	public void setCancelado(boolean cancelado)
	{
		Cancelado = cancelado;
	}

	public String getCorreoCliente()
	{
		return CorreoCliente;
	}

	public void setCorreoCliente(String correoCliente)
	{
		CorreoCliente = correoCliente;
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
		return "Pedido [NoPedido=" + NoPedido + ", IdPago=" + IdPago + ", pedidoDetalle=" + pedidoDetalle
				+ ", IdCliente=" + IdCliente + ", URLLiga=" + URLLiga + ", Status=" + Status + ", Creado=" + Creado
				+ ", PagoFecha=" + PagoFecha + ", CanceladoFecha=" + CanceladoFecha + ", Pagado=" + Pagado
				+ ", Cancelado=" + Cancelado + ", CorreoCliente=" + CorreoCliente + ", Activo=" + Activo
				+ ", FechaCreacion=" + FechaCreacion + ", FechaModificacion=" + FechaModificacion + "]";
	}

	
	
	
	
}
