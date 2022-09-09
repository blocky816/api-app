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
public class RHVacacionesEmpleado {

	@Id //Define la llave primaria.
    @Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private int idEmpleado;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private String empleado;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private String ingreso;

	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private String antiguedad;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private String derecho;
	
	@Column(insertable = false, updatable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private String dias;

	
	
}
