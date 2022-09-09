/*En este paquete tenemos nuestro clase HorarioOtroClub.java y utilizaremos las @anotaciones 
 * JPA para relacionarla con nuestra tabla HorarioOtroClub, quedaría de la siguiente forma:
 *	@autor: Daniel García Velasco 
 * 			Abimael Rueda Galindo
 *	@version: 1
 *12/07/2021
*/
package com.tutorial.crud.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity //Sirve únicamente para indicarle a JPA que esa clase es una Entity.
@Table(name="horariootroclub") //se utiliza para poner el nombre real de la tabla en la base de datos
@IdClass(HorarioOtroClubId.class)
public class HorarioOtroClub implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*@Id
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name="terminalId", unique = true)*/
	@Id //Define la llave primaria.
	@Column(name="terminalid") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private int TerminalId;
	@Id
	@Column(name="desde")
	private Date Desde;
	@Id
	@Column(name="hasta")
	private Date Hasta;
	
	@Column(name="observaciones")
	private String Observaciones;
	
	@Column(name="activo")
	private boolean Activo;
	
	@Column(name="fechacreacion")
	private Date FechaCreacion;
	
	@Column(name="fechamodificacion")
	private Date FechaModificacion;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="id_horarioacceso")
	private HorarioAcceso horarioacceso;

	//Los constructores
	public HorarioOtroClub() {}

	public HorarioOtroClub(Date Desde, Date Hasta, String Observaciones)
	{
		
		this.Desde = Desde;
		this.Hasta = Hasta;
		this.Observaciones = Observaciones;
		Activo = true;
		FechaCreacion = new Date();
	}

	//Se generaron todos los Getters y Setters.
	public int getTerminalId() 
	{
		return TerminalId;
	}

	public void setTerminalId(int termianlId) 
	{
		TerminalId = termianlId;
	}

	public Date getDesde() 
	{
		return Desde;
	}

	public void setDesde(Date desde) 
	{
		Desde = desde;
	}

	public Date getHasta() 
	{
		return Hasta;
	}

	public void setHasta(Date hasta)
	{
		Hasta = hasta;
	}

	public String getObservaciones() 
	{
		return Observaciones;
	}

	public void setObservaciones(String observaciones) 
	{
		Observaciones = observaciones;
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

	public HorarioAcceso getHorarioacceso() {
		return horarioacceso;
	}

	public void setHorarioacceso(HorarioAcceso horarioacceso) {
		this.horarioacceso = horarioacceso;
	}

	@Override
	public String toString() {
		return "HorarioOtroClub [TerminalId=" + TerminalId + ", Desde=" + Desde + ", Hasta=" + Hasta
				+ ", Observaciones=" + Observaciones + ", Activo=" + Activo + ", FechaCreacion=" + FechaCreacion
				+ ", FechaModificacion=" + FechaModificacion + ", horarioacceso=" + horarioacceso +  "]\n";
	}

	
	
}
