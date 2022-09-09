/*En este paquete tenemos nuestro clase Movimientos.java y utilizaremos las @anotaciones 
 * JPA para relacionarla con nuestra tabla Movimientos
 *	@autor: Daniel García Velasco 
 * 			Abimael Rueda Galindo
 *	@version: 1
 *12/07/2021
*/

package com.tutorial.crud.entity;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONException;
import org.json.JSONObject;

@Entity  //Sirve únicamente para indicarle a JPA que esa clase es una Entity.
@Table(name="movimientos") //se utiliza para poner el nombre real de la tabla en la base de datos
public class Movimientos
{
	/*@Id
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name="noPedido", unique = true)*/
	@Id //Define la llave primaria.
	@Column(name="id_cliente_movimiento") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private int IDClienteMovimiento;
	
	@Column(name="id_cliente")
	private int IDCliente;
	
	@Column(name="cliente")
	private String Cliente;
	
	@Column(name="concepto")
	private String Concepto;
	
	@Column(name="fecha_de_aplicacion")
	private Date FechaDeAplicacion;
	
	@Column(name="id_orden_de_venta")
	private int IDOrdenDeVenta;
	
	@Column(name="id_orden_de_venta_detalle")
	private int IDOrdenDeVentaDetalle;
	
	@Column(name="debito")
	private float Debito;
	
	@Column(name="saldo")
	private float Saldo;
	
	@Column(name="activo")
	private boolean Activo;
	
	@Column(name="fechacreacion")
	private Date FechaCreacion;
	
	@Column(name="fechamodificacion")
	private Date FechaModificacion;

	/**
	 * getters y setters de la clase
	 * 
	 */
	
	
	
	
	public int getIDClienteMovimiento() {
		return IDClienteMovimiento;
	}

	public Movimientos() {
		
	}
	public Movimientos(JSONObject object) {
		IDClienteMovimiento = object.getInt("idclienteMovimiento");
		IDCliente = object.getInt("idcliente");
		Cliente = object.getString("cliente");
		Concepto = object.getString("concepto");
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
		    Date parsedDate;
			try {
				parsedDate = dateFormat.parse(object.get("fechaDeAplicacion").toString());
				FechaDeAplicacion = parsedDate ;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		IDOrdenDeVenta = object.getInt("idordenDeVenta");
		IDOrdenDeVentaDetalle = object.getInt("idordenDeVentaDetalle");
		Debito = object.getFloat("debito");
		Saldo = object.getFloat("saldo");
	}

	public void setIDClienteMovimiento(int iDClienteMovimiento) {
		IDClienteMovimiento = iDClienteMovimiento;
	}

	public int getIDCliente() {
		return IDCliente;
	}

	public void setIDCliente(int iDCliente) {
		IDCliente = iDCliente;
	}

	public String getCliente() {
		return Cliente;
	}

	public void setCliente(String cliente) {
		Cliente = cliente;
	}

	public String getConcepto() {
		return Concepto;
	}

	public void setConcepto(String concepto) {
		Concepto = concepto;
	}

	public Date getFechaDeAplicacion() {
		return FechaDeAplicacion;
	}

	public void setFechaDeAplicacion(Date fechaDeAplicacion) {
		FechaDeAplicacion = fechaDeAplicacion;
	}

	public int getIDOrdenDeVenta() {
		return IDOrdenDeVenta;
	}

	public void setIDOrdenDeVenta(int iDOrdenDeVenta) {
		IDOrdenDeVenta = iDOrdenDeVenta;
	}

	public int getIDOrdenDeVentaDetalle() {
		return IDOrdenDeVentaDetalle;
	}

	public void setIDOrdenDeVentaDetalle(int iDOrdenDeVentaDetalle) {
		IDOrdenDeVentaDetalle = iDOrdenDeVentaDetalle;
	}

	public float getDebito() {
		return Debito;
	}

	public void setDebito(float debito) {
		Debito = debito;
	}

	public float getSaldo() {
		return Saldo;
	}

	public void setSaldo(float saldo) {
		Saldo = saldo;
	}

	public boolean isActivo() {
		return Activo;
	}

	public void setActivo(boolean activo) {
		Activo = activo;
	}

	public Date getFechaCreacion() {
		return FechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		FechaCreacion = fechaCreacion;
	}

	public Date getFechaModificacion() {
		return FechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		FechaModificacion = fechaModificacion;
	}

	@Override
	public String toString() {
		return "Movimientos [IDClienteMovimiento=" + IDClienteMovimiento + ", IDCliente=" + IDCliente + ", Cliente="
				+ Cliente + ", Concepto=" + Concepto + ", FechaDeAplicacion=" + FechaDeAplicacion + ", IDOrdenDeVenta="
				+ IDOrdenDeVenta + ", IDOrdenDeVentaDetalle=" + IDOrdenDeVentaDetalle + ", Debito=" + Debito
				+ ", Saldo=" + Saldo + ", Activo=" + Activo + ", FechaCreacion=" + FechaCreacion
				+ ", FechaModificacion=" + FechaModificacion + "]";
	}
	
	
	
	
}
