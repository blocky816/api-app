/*En este paquete tenemos nuestro clase Foto.java y utilizaremos las @anotaciones 
 * JPA para relacionarla con nuestra tabla Cliente, quedaría de la siguiente forma:
 *	@autor: Daniel García Velasco 
 * 			Abimael Rueda Galindo
 *	@version: 1
 *12/07/2021
*/

package com.tutorial.crud.entity;

//Librería 
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
 
@Entity //Sirve únicamente para indicarle a JPA que esa clase es una Entity.
@Table(name="fotos") //se utiliza para poner el nombre real de la tabla en la base de datos
public class Foto 
{
	@Id //Define la llave primaria.
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_foto") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private int Id_foto; //Variables
	
	@OneToOne (mappedBy="URLFoto")
    private Cliente cliente;	
	
	@Column(name="imagen") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private byte[] imagen;
	
	@Column(name="activo") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private boolean Activo=true;
	
	@Column(name="fechacreacion") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private Date FechaCreacion=new Date();
	
	@Column(name="fechamodificacion") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private Date FechaModificacion=new Date();

	//Creación de los constructores
	public Foto() {}
	public Foto(byte[] bytes) {
		this.imagen=bytes;
	}
	
	//Getters y Setters
	public int getId_foto() {
		return Id_foto;
	}

	public void setId_foto(int id_foto) {
		Id_foto = id_foto;
	}


	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
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
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	
}
