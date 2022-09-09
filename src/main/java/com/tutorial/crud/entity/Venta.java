/*En este paquete tenemos nuestro clase Venta.java y utilizaremos las @anotaciones 
 * JPA para relacionarla con nuestra tabla Venta, quedaría de la siguiente forma:
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
@Table(name="venta") //se utiliza para poner el nombre real de la tabla en la base de datos
public class Venta 
{
	/*@Id
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name="idVenta", unique = true)*/
	@Id //Define la llave primaria.
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idventa") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private int IdVenta;
	
	@Column(name="idcliente")
	private int IdCliente;
	
	@Column(name="concepto")
	private String Concepto;
	
	@Column(name="importeov")
	private float ImporteOV;
	
	@Column(name="cancelado")
	private boolean Cancelado;
	
	@Column(name="idordendeventadetalle")
	private int IdOrdenDeVentaDetalle;
	
	@Column(name="fechainicio")
	private Date FechaInicio;
	
	@Column(name="fechafin")
	private Date FechaFin;
	
	@Column(name="mediodepago")
	private String MedioDePago;
	
	@Column(name="importepago")
	private float ImportePago;
	
	@Column(name="Fechaaplicacion")
	private Date FechaAplicacion;
	
	@Column(name="Foliorecibo")
	private String FolioRecibo;
	
	@Column(name="estapagado")
	private boolean EstaPagado;
	
	@Column(name="cuotatipo")
	private String CuotaTipo;
	
	@Column(name="familia")
	private String Familia;
	
	@Column(name="grupo")
	private String Grupo;
	
	@Column(name="categoria")
	private String Categoria;
	
	@Column(name="subcategoria")
	private String Subcategoria;
	
	@Column(name="programaciontipo")
	private String ProgramacionTipo;
	
	@Column(name="clientetipo")
	private String ClienteTipo;
	
	@Column(name="membresiatipo")
	private String MembresiaTipo;
	
	@Column(name="activo")
	private boolean Activo;
	
	@Column(name="fechacreacion")
	private Date FechaCreacion;
	
	@Column(name="fechamodificacion")
	private Date FechaModificacion;
	
	//Constructores
	public Venta() {}
	
	public Venta(int IdCliente, String Concepto, float ImporteOV, boolean Cancelado, int IdOrdenDeVentaDetalle,
			Date FechaInicio, Date FechaFin, String MedioDePago, float ImportePago, Date FechaAplicacion, 
			String FolioRecibo, boolean EstaPagado, String CuotaTipo, String Familia, String Grupo, 
			String Categoria, String Subcategoria, String ProgramacionTipo, String ClienteTipo, 
			String MembresiaTipo)
	{
		this.IdCliente = IdCliente;
		this.Concepto = Concepto;
		this.ImporteOV = ImporteOV;
		this.Cancelado = Cancelado;
		this.IdOrdenDeVentaDetalle = IdOrdenDeVentaDetalle;
		this.FechaInicio = FechaInicio;
		this.FechaFin = FechaFin;
		this.MedioDePago = MedioDePago;
		this.ImportePago = ImportePago;
		this.FechaAplicacion = FechaAplicacion;
		this.FolioRecibo = FolioRecibo;
		this.EstaPagado = EstaPagado;
		this.CuotaTipo = CuotaTipo;
		this.Familia = Familia;
		this.Grupo = Grupo;
		this.Categoria = Categoria;
		this.Subcategoria = Subcategoria;
		this.ProgramacionTipo = ProgramacionTipo;
		this.ClienteTipo = ClienteTipo;
		this.MembresiaTipo = MembresiaTipo;
		Activo = true;
		FechaCreacion = new Date(); 
	}

	//Getters y Setters
	public int getIdCliente() 
	{
		return IdCliente;
	}

	public void setIdCliente(int idCliente) 
	{
		IdCliente = idCliente;
	}

	public String getConcepto() 
	{
		return Concepto;
	}

	public void setConcepto(String concepto) 
	{
		Concepto = concepto;
	}

	public float getImporteOV() 
	{
		return ImporteOV;
	}

	public void setImporteOV(float importeOV)
	{
		ImporteOV = importeOV;
	}

	public boolean isCancelado() 
	{
		return Cancelado;
	}

	public void setCancelado(boolean cancelado)
	{
		Cancelado = cancelado;
	}

	public int getIdOrdenDeVentaDetalle() 
	{
		return IdOrdenDeVentaDetalle;
	}

	public void setIdOrdenDeVentaDetalle(int idOrdenDeVentaDetalle) 
	{
		IdOrdenDeVentaDetalle = idOrdenDeVentaDetalle;
	}

	public int getIdVenta()
	{
		return IdVenta;
	}

	public void setIdVenta(int idVenta) 
	{
		IdVenta = idVenta;
	}

	public Date getFechaInicio() 
	{
		return FechaInicio;
	}

	public void setFechaInicio(Date fechaInicio)
	{
		FechaInicio = fechaInicio;
	}

	public Date getFechaFin() 
	{
		return FechaFin;
	}

	public void setFechaFin(Date fechaFin) 
	{
		FechaFin = fechaFin;
	}

	public String getMedioDePago() 
	{
		return MedioDePago;
	}

	public void setMedioDePago(String medioDePago) 
	{
		MedioDePago = medioDePago;
	}

	public float getImportePago() 
	{
		return ImportePago;
	}

	public void setImportePago(float importePago) 
	{
		ImportePago = importePago;
	}

	public Date getFechaAplicacion()
	{
		return FechaAplicacion;
	}

	public void setFechaAplicacion(Date fechaAplicacion) 
	{
		FechaAplicacion = fechaAplicacion;
	}

	public String getFolioRecibo() 
	{
		return FolioRecibo;
	}

	public void setFolioRecibo(String folioRecibo)
	{
		FolioRecibo = folioRecibo;
	}

	public boolean isEstaPagado()
	{
		return EstaPagado;
	}

	public void setEstaPagado(boolean estaPagado) 
	{
		EstaPagado = estaPagado;
	}

	public String getCuotaTipo() 
	{
		return CuotaTipo;
	}

	public void setCuotaTipo(String cuotaTipo)
	{
		CuotaTipo = cuotaTipo;
	}

	public String getFamilia()
	{
		return Familia;
	}

	public void setFamilia(String familia)
	{
		Familia = familia;
	}

	public String getGrupo()
	{
		return Grupo;
	}

	public void setGrupo(String grupo) 
	{
		Grupo = grupo;
	}

	public String getCategoria() 
	{
		return Categoria;
	}

	public void setCategoria(String categoria) 
	{
		Categoria = categoria;
	}

	public String getSubcategoria()
	{
		return Subcategoria;
	}

	public void setSubcategoria(String subcategoria) 
	{
		Subcategoria = subcategoria;
	}

	public String getProgramacionTipo() 
	{
		return ProgramacionTipo;
	}

	public void setProgramacionTipo(String programacionTipo) 
	{
		ProgramacionTipo = programacionTipo;
	}

	public String getClienteTipo()
	{
		return ClienteTipo;
	}

	public void setClienteTipo(String clienteTipo) 
	{
		ClienteTipo = clienteTipo;
	}

	public String getMembresiaTipo()
	{
		return MembresiaTipo;
	}

	public void setMembresiaTipo(String membresiaTipo)
	{
		MembresiaTipo = membresiaTipo;
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
