/*En este paquete tenemos nuestro clase CursoHorario.java y utilizaremos las @anotaciones 
 * JPA para relacionarla con nuestra tabla CursoHorario, quedaría de la siguiente forma:
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

@Entity  //Sirve únicamente para indicarle a JPA que esa clase es una Entity.
@Table(name="cursohorario") //se utiliza para poner el nombre real de la tabla en la base de datos
public class CursoHorario 
{
	/*@Id
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name="IdCurso", unique = true)*/
	@Id //LLave primaria
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idcurso") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private int IdCurso;
	
	@Column(name="nombrecurso")
	private String NombreCurso;
	
	@Column(name="luneshorarioentrada")
	private Date LunesHoraEntrada;
	
	@Column(name="luneshorariosalida")
	private Date LunesHoraSalida;
	
	@Column(name="marteshorarioentrada")
	private Date MartesHoraEntrada;
	
	@Column(name="marteshorariosalida")
	private Date MartesHoraSalida;
	
	@Column(name="miercoleshorarioentrada")
	private Date MiercolesHoraEntrada;
	
	@Column(name="miercoleshorariosalida")
	private Date MiercolesHoraSalida;
	
	@Column(name="jueveshorarioentrada")
	private Date JuevesHoraEntrada;
	
	@Column(name="jueveshorariosalida")
	private Date JuevesHoraSalida;
	
	@Column(name="vierneshorarioentrada")
	private Date ViernesHoraEntrada;
	
	@Column(name="vierneshorariosalida")
	private Date ViernesHoraSalida;
	
	@Column(name="sabadohorarioentrada")
	private Date SabadoHoraEntrada;
	
	@Column(name="sabadohorariosalida")
	private Date SabadoHoraSalida;
	
	@Column(name="domingohorarioentrada")
	private Date DomingoHoraEntrada;
	
	@Column(name="domingohorariosalida")
	private Date DomingoHoraSalida;
	
	@Column(name="activo")
	private boolean Activo;
	
	@Column(name="fechacreacion")
	private Date FechaCreacion;
	
	@Column(name="fechamodificacion")
	private Date FechaModificacion;
	
	//Creción del constructor
	public CursoHorario() {}
	
	public CursoHorario(String NombreCurso, Date LunesHoraEntrada, Date LunesHoraSalida, Date MartesHoraEntrada, 
			Date MartesHoraSalida, Date MiercolesHoraEntrada, Date MiercolesHoraSalida, Date JuevesHoraEntrada, 
			Date JuevesHoraSalida, Date ViernesHoraEntrada, Date ViernesHoraSalida, Date SabadoHoraEntrada, 
			Date SabadoHoraSalida, Date DomingoHoraEntrada, Date DomingoHoraSalida) 
	{
		this.NombreCurso = NombreCurso;
		this.LunesHoraEntrada = LunesHoraEntrada;
		this.LunesHoraSalida = LunesHoraSalida;
		this.MartesHoraEntrada = MartesHoraEntrada;
		this.MartesHoraSalida = MartesHoraSalida;
		this.MiercolesHoraEntrada = MiercolesHoraEntrada;
		this.MiercolesHoraSalida = MiercolesHoraSalida;
		this.JuevesHoraEntrada = JuevesHoraEntrada;
		this.JuevesHoraSalida = JuevesHoraSalida;
		this.ViernesHoraEntrada = ViernesHoraEntrada;
		this.ViernesHoraSalida = ViernesHoraSalida;
		this.SabadoHoraEntrada = SabadoHoraEntrada;
		this.SabadoHoraSalida = SabadoHoraSalida;
		this.DomingoHoraEntrada = DomingoHoraEntrada;
		this.DomingoHoraSalida = DomingoHoraSalida;
		Activo = true;
		FechaCreacion = new Date();
	}

	//Generar Getters y Setters
	public int getIdCurso() 
	{
		return IdCurso;
	}

	public void setIdCurso(int idCurso) 
	{
		IdCurso = idCurso;
	}

	public String getNombreCurso() 
	{
		return NombreCurso;
	}

	public void setNombreCurso(String nombreCurso)
	{
		NombreCurso = nombreCurso;
	}

	public Date getLunesHoraEntrada() 
	{
		return LunesHoraEntrada;
	}

	public void setLunesHoraEntrada(Date lunesHoraEntrada) 
	{
		LunesHoraEntrada = lunesHoraEntrada;
	}

	public Date getLunesHoraSalida() 
	{
		return LunesHoraSalida;
	}

	public void setLunesHoraSalida(Date lunesHoraSalida) 
	{
		LunesHoraSalida = lunesHoraSalida;
	}

	public Date getMartesHoraEntrada() 
	{
		return MartesHoraEntrada;
	}

	public void setMartesHoraEntrada(Date martesHoraEntrada) 
	{
		MartesHoraEntrada = martesHoraEntrada;
	}

	public Date getMartesHoraSalida() 
	{
		return MartesHoraSalida;
	}

	public void setMartesHoraSalida(Date martesHoraSalida) 
	{
		MartesHoraSalida = martesHoraSalida;
	}

	public Date getMiercolesHoraEntrada() 
	{
		return MiercolesHoraEntrada;
	}

	public void setMiercolesHoraEntrada(Date miercolesHoraEntrada) 
	{
		MiercolesHoraEntrada = miercolesHoraEntrada;
	}

	public Date getMiercolesHoraSalida() 
	{
		return MiercolesHoraSalida;
	}

	public void setMiercolesHoraSalida(Date miercolesHoraSalida) 
	{
		MiercolesHoraSalida = miercolesHoraSalida;
	}

	public Date getJuevesHoraEntrada()
	{
		return JuevesHoraEntrada;
	}

	public void setJuevesHoraEntrada(Date juevesHoraEntrada) 
	{
		JuevesHoraEntrada = juevesHoraEntrada;
	}

	public Date getJuevesHoraSalida()
	{
		return JuevesHoraSalida;
	}

	public void setJuevesHoraSalida(Date juevesHoraSalida)
	{
		JuevesHoraSalida = juevesHoraSalida;
	}

	public Date getViernesHoraEntrada() 
	{
		return ViernesHoraEntrada;
	}

	public void setViernesHoraEntrada(Date viernesHoraEntrada) 
	{
		ViernesHoraEntrada = viernesHoraEntrada;
	}

	public Date getViernesHoraSalida() 
	{
		return ViernesHoraSalida;
	}

	public void setViernesHoraSalida(Date viernesHoraSalida)
	{
		ViernesHoraSalida = viernesHoraSalida;
	}

	public Date getSabadoHoraEntrada() 
	{
		return SabadoHoraEntrada;
	}

	public void setSabadoHoraEntrada(Date sabadoHoraEntrada)
	{
		SabadoHoraEntrada = sabadoHoraEntrada;
	}

	public Date getSabadoHoraSalida() 
	{
		return SabadoHoraSalida;
	}

	public void setSabadoHoraSalida(Date sabadoHoraSalida)
	{
		SabadoHoraSalida = sabadoHoraSalida;
	}

	public Date getDomingoHoraEntrada() 
	{
		return DomingoHoraEntrada;
	}

	public void setDomingoHoraEntrada(Date domingoHoraEntrada) 
	{
		DomingoHoraEntrada = domingoHoraEntrada;
	}

	public Date getDomingoHoraSalida() 
	{
		return DomingoHoraSalida;
	}

	public void setDomingoHoraSalida(Date domingoHoraSalida)
	{
		DomingoHoraSalida = domingoHoraSalida;
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
