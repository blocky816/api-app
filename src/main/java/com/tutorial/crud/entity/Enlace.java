/*En este paquete tenemos nuestro clase Categoria.java y utilizaremos las @anotaciones 
 * JPA para relacionarla con nuestra tabla Categoria, quedaría de la siguiente forma:
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
import javax.persistence.Id;
import javax.persistence.Table;
 
@Entity //Sirve únicamente para indicarle a JPA que esa clase es una Entity.
@Table(name="enlace") //se utiliza para poner el nombre real de la tabla en la base de datos
public class Enlace 
{
	@Id //Define la llave primaria.
	@Column(name="id_alpha") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	int idAlpha; //Variables
	
	@Column(name="id_openpay") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	String idOpenpay;

	public int getIdAlpha() {
		return idAlpha;
	}

	public void setIdAlpha(int idAlpha) {
		this.idAlpha = idAlpha;
	}

	public String getIdOpenpay() {
		return idOpenpay;
	}

	public void setIdOpenpay(String idOpenpay) {
		this.idOpenpay = idOpenpay;
	}

}
