package com.tutorial.crud.entity;
 
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "clases")
@Immutable
public class CAClase {

	@Id //Define la llave primaria.
    @GeneratedValue(generator = "UUID") //Se estableció un tipo de variable UUID 
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private UUID id;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private String nombre;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private String tecnico;

	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private String tipoActividad;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private String color;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private String lugar;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private int duracion;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private String nivel;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private String hora;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private String cupo_actual;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private String cupo_maximo;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private String rango;

	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private String dia;
	
	private boolean disponible;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private int paga;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private UUID idApartados;

	public int getPaga() {
		return paga;
	}

	public String getDia() {
		return dia;
	}

	public UUID getId() { 
		return id;
	}



	public String getNombre() {
		return nombre;
	}



	public String getTecnico() {
		return tecnico;
	}



	public String getTipoActividad() {
		return tipoActividad;
	}



	public String getColor() {
		return color;
	}



	public String getLugar() {
		return lugar;
	}



	public int getDuracion() {
		return duracion;
	}



	public String getNivel() {
		return nivel;
	}



	public String getHora() {
		return hora;
	}



	public String getCupo_actual() {
		return cupo_actual;
	}



	public String getCupo_maximo() {
		return cupo_maximo;
	}



	public String getRango() {
		return rango;
	}



	public boolean getDisponible() {
		return disponible;
	}



	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	

	@Override
	public String toString() {
		return "CAClase [id=" + id + ", nombre=" + nombre + ", tecnico=" + tecnico + ", tipoActividad=" + tipoActividad
				+ ", color=" + color + ", lugar=" + lugar + ", duracion=" + duracion + ", nivel=" + nivel + ", hora="
				+ hora + ", cupo_actual=" + cupo_actual + ", cupo_maximo=" + cupo_maximo + ", rango=" + rango + ", dia="
				+ dia + ", disponible=" + disponible + ", paga=" + paga + ", idApartados=" + idApartados + "]";
	}

	public UUID getIdApartados() {
		return idApartados;
	}

	
}
