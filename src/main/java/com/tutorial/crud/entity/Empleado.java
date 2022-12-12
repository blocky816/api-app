package com.tutorial.crud.entity;

//Librería 
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity //Sirve únicamente para indicarle a JPA que esa clase es una Entity.
@Table(name="empleados")
public class Empleado {
    
    @Id   
	@Column(name = "id_empleado")
	private int idEmpleado;

    @Column(name = "correo")
    private String correo;
    
    public int getIdEmpleado() { return idEmpleado; }

    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }

    public String getCorreo() { return correo; }

    public void setCorreo(String correo) { this.correo = correo; }

}
