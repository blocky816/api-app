/*En este paquete tenemos nuestro clase Pago.java y utilizaremos las @anotaciones 
 * JPA para relacionarla con nuestra tabla Pago, quedaría de la siguiente forma:
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
@Table(name="pago") //se utiliza para poner el nombre real de la tabla en la base de datos
public class Pago 
{
	/*@Id
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name="IdCliente", unique = true)*/
	@Id //Define la llave primaria.
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idpago") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private int IdPago;	//Variable
	
	//@OneToOne(cascade=CascadeType.REFRESH)
	//@JoinColumn(name="noPedido")
	@Column(name="nopedido")
	private int NoPedido;
	
	@Column(name="monto")
	private float Monto;
	
	@Column(name="notarjeta")
	private int NoTarjeta;
	
	@Column(name="foliointerbancario")
	private int FolioInterbancario;
	
	@Column(name="noautorizacion")
	private int NoAutorizacion;
	
	@Column(name="fechapago")
	private Date FechaPago;
	
	@Column(name="horapago")
	private Date HoraPago;
	
	@Column(name="titularcuenta")
	private String TitularCuenta;
	
	@Column(name="activo")
	private boolean Activo;
	
	@Column(name="fechacreacion")
	private Date FechaCreacion;
	
	@Column(name="fechamodificacion")
	private Date FechaModificacion;
	
	//Se genera los constructores 
	public Pago() {}
	
	public Pago(int NoPedido, float Monto, int NoTarjeta, int FolioInterbancario, int NoAutorizacion,
			Date FechaPago, Date HoraPago, String TitularCuenta) 
	{
		this.NoPedido = NoPedido;
		this.Monto = Monto;
		this.NoTarjeta = NoTarjeta;
		this.FolioInterbancario = FolioInterbancario;
		this.NoAutorizacion = NoAutorizacion;
		this.FechaPago = FechaPago;
		this.HoraPago = HoraPago;
		this.TitularCuenta = TitularCuenta;
		Activo = true;
		FechaCreacion = new Date();
	}

	//Se genera los getters y setters
	public int getIdPago() 
	{
		return IdPago;
	}

	public void setIdPago(int idPago) 
	{
		IdPago = idPago;
	}

	public int getNoPedido()
	{
		return NoPedido;
	}

	public void setNoPedido(int noPedido) 
	{
		NoPedido = noPedido;
	}

	public float getMonto() 
	{
		return Monto;
	}

	public void setMonto(float monto)
	{
		Monto = monto;
	}

	public int getNoTarjeta() 
	{
		return NoTarjeta;
	}

	public void setNoTarjeta(int noTarjeta) 
	{
		NoTarjeta = noTarjeta;
	}

	public int getFolioInterbancario()
	{
		return FolioInterbancario;
	}

	public void setFolioInterbancario(int folioInterbancario)
	{
		FolioInterbancario = folioInterbancario;
	}

	public int getNoAutorizacion() 
	{
		return NoAutorizacion;
	}

	public void setNoAutorizacion(int noAutorizacion) 
	{
		NoAutorizacion = noAutorizacion;
	}

	public Date getFechaPago()
	{
		return FechaPago;
	}

	public void setFechaPago(Date fechaPago)
	{
		FechaPago = fechaPago;
	}

	public Date getHoraPago() 
	{
		return HoraPago;
	}

	public void setHoraPago(Date horaPago) 
	{
		HoraPago = horaPago;
	}

	public String getTitularCuenta() 
	{
		return TitularCuenta;
	}

	public void setTitularCuenta(String tirularCuenta) 
	{
		TitularCuenta = tirularCuenta;
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
