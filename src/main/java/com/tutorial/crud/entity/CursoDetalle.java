/*En este paquete tenemos nuestro clase CursoDetalle.java y utilizaremos las @anotaciones 
 * JPA para relacionarla con nuestra tabla CursoDetalle, quedaría de la siguiente forma:
 *	@autor: Daniel García Velasco 
 * 			Abimael Rueda Galindo
 *	@version: 1
 *12/07/2021
*/

package com.tutorial.crud.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity //Sirve únicamente para indicarle a JPA que esa clase es una Entity.
@Table(name="cursodetalle") //se utiliza para poner el nombre real de la tabla en la base de datos
public class CursoDetalle 
{
	/*@Id
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name="idCursoDetallle", unique = true)*/
	@Id //Define la llave primaria.
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idcursodetalle") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private int IdCursoDetalle; 
	
	@Column(name="periodo")
	private String Periodo;
	
	@Column(name="cursotipo")
	private String CursoTipo;
	
	@Column(name="area")
	private String Area;
	
	@Column(name="instalacion")
	private String Instalacion;
	
	@Column(name="usadopor")
	private String UsadoPor;
	
	@Column(name="profesor")
	private String Profesor;
	
	@Column(name="horarioinicio")
	private Date HorarioInicio;
	
	@Column(name="edadminima")
	private int EdadMinima;
	
	@Column(name="edadmaxima")
	private int EdadMaxima;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="idcursohorario")
	private CursoHorario IdCursoHorario;
	
	@Column(name="idcursotipo")
	private int IdCursoTipo;
	
	@Column(name="activo")
	private boolean Activo;
	
	@Column(name="fechacreacion")
	private Date FechaCreacion;
	
	@Column(name="fechamodificacion")
	private Date FechaModificacion;
	
	//Creación de los constructores
	public CursoDetalle() {}
	
	public CursoDetalle(String Periodo, String CursoTipo, String Area, String Instalacion, String UsadoPor, 
			String Profesor, Date HorarioInicio, int EdadMinima, int EdadMaxima, CursoHorario IdCursoHorario,
			int IdCursoTipo) 
	{
		this.Periodo = Periodo;
		this.CursoTipo = CursoTipo;
		this.Area = Area;
		this.Instalacion = Instalacion;
		this.UsadoPor = UsadoPor;
		this.Profesor = Profesor;
		this.HorarioInicio = HorarioInicio;
		this.EdadMinima = EdadMinima;
		this.EdadMaxima = EdadMaxima;
		this.IdCursoHorario = IdCursoHorario;
		this.IdCursoTipo = IdCursoTipo;
		Activo = true;
		FechaCreacion = new Date();
	}
	//Se generaron todos los Getters y Setters.

	public int getIdCursoDetalle() 
	{
		return IdCursoDetalle;
	}

	public void setIdCursoDetalle(int idCursoDetalle) 
	{
		IdCursoDetalle = idCursoDetalle;
	}

	public String getPeriodo() 
	{
		return Periodo;
	}

	public void setPeriodo(String periodo) 
	{
		Periodo = periodo;
	}

	public String getCursoTipo() 
	{
		return CursoTipo;
	}

	public void setCursoTipo(String cursoTipo) 
	{
		CursoTipo = cursoTipo;
	}

	public String getArea() 
	{
		return Area;
	}

	public void setArea(String area) 
	{
		Area = area;
	}

	public String getInstalacion() 
	{
		return Instalacion;
	}

	public void setInstalacion(String instalacion) 
	{
		Instalacion = instalacion;
	}

	public String getUsadoPor() 
	{
		return UsadoPor;
	}

	public void setUsadoPor(String usadoPor) 
	{
		UsadoPor = usadoPor;
	}

	public String getProfesor() 
	{
		return Profesor;
	}

	public void setProfesor(String profesor) 
	{
		Profesor = profesor;
	}

	public Date getHorarioInicio() 
	{
		return HorarioInicio;
	}

	public void setHorarioInicio(Date horarioInicio) 
	{
		HorarioInicio = horarioInicio;
	}

	public int getEdadMinima() 
	{
		return EdadMinima;
	}

	public void setEdadMinima(int edadMinima) 
	{
		EdadMinima = edadMinima;
	}

	public int getEdadMaxima() 
	{
		return EdadMaxima;
	}

	public void setEdadMaxima(int edadMaxima) 
	{
		EdadMaxima = edadMaxima;
	}

	public CursoHorario getIdCursoHorario() {
		return IdCursoHorario;
	}

	public void setIdCursoHorario(CursoHorario idCursoHorario) {
		this.IdCursoHorario = idCursoHorario;
	}

	public int getIdCursoTipo() 
	{
		return IdCursoTipo;
	}

	public void setIdCursoTipo(int idCursoTipo) 
	{
		IdCursoTipo = idCursoTipo;
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
