/**En este paquete tenemos nuestro clase TipoMembresia.java y utilizaremos las @anotaciones 
 * JPA para relacionarla con nuestra tabla TipoMembresia, quedaría de la siguiente forma:
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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity //Sirve únicamente para indicarle a JPA que esa clase es una Entity.
@Table(name="tipomembresia") //se utiliza para poner el nombre real de la tabla en la base de datos
public class TipoMembresia 
{
	/*@Id
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name="terminalId", unique = true)*/
	@Id //Define la llave primaria 
	@Column(name="id_tipomembresia") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private int IdTipoMembresia;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="activo")
	private boolean Activo;
	
	@Column(name="fechacreacion")
	private Date FechaCreacion;
	
	@Column(name="fechamodificacion")
	private Date FechaModificacion;
	
	@OneToOne(cascade=CascadeType.ALL) //Relacion de uno a uno
	@JoinColumn(name="horarioacceso")
	private HorarioAcceso horarioacceso;
	
	//Constructor
	public TipoMembresia() {}
	
	//Se generaron todos los Getters y Setters.
	public TipoMembresia( String Observaciones)
	{
		Activo = true;
		FechaCreacion = new Date();
	}

	public int getIdTipoMembresia() 
	{
		return IdTipoMembresia;
	}

	public void setTerminalId(int termianlId) 
	{
		IdTipoMembresia = termianlId;
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setIdTipoMembresia(int idTipoMembresia) {
		IdTipoMembresia = idTipoMembresia;
	}

	@Override
	public String toString() {
		return "TipoMembresia [IdTipoMembresia=" + IdTipoMembresia + ", nombre=" + nombre + ", Activo=" + Activo
				+ ", FechaCreacion=" + FechaCreacion + ", FechaModificacion=" + FechaModificacion + ", horarioacceso="
				+ horarioacceso + "]";
	}

	
	
}
