package com.tutorial.crud.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="referencia") 
public class Referencia {
	@Id //Define la llave primaria.
    @GeneratedValue(generator = "UUID") //Se estableció un tipo de variable UUID 
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private UUID id;

	@Column(name = "id_cliente")
	private int idCliente;

	@Column(name = "fecha")
	private String fecha;

	@Column(name = "no_referencia")
	private String noReferencia;

	@Column(name = "importe")
	private String importe;

	@Column(name = "no_autorizacion")
	private String noAutorizacion;

	@Column(name = "no_membresia")
	private String  noMembresia;

	@Column(name = "nombre_titular")
	private String nombreTitular;
	
	@Column(name = "nombre_programa")
	private String nombrePrograma;
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getNoReferencia() {
		return noReferencia;
	}
	public void setNoReferencia(String noReferencia) {
		this.noReferencia = noReferencia;
	}
	public String getImporte() {
		return importe;
	}
	public void setImporte(String importe) {
		this.importe = importe;
	}
	public String getNoAutorizacion() {
		return noAutorizacion;
	}
	public void setNoAutorizacion(String noAutorizacion) {
		this.noAutorizacion = noAutorizacion;
	}
	public String getNombreTitular() {
		return nombreTitular;
	}
	public void setNombreTitular(String nombreTitular) {
		this.nombreTitular = nombreTitular;
	}
	public String getNoMembresia() {
		return noMembresia;
	}
	public void setNoMembresia(String noMembresia) {
		this.noMembresia = noMembresia;
	}
	
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	
	public String getNombrePrograma() {
		return nombrePrograma;
	}
	public void setNombrePrograma(String nombrePrograma) {
		this.nombrePrograma = nombrePrograma;
	}
	@Override
	public String toString() {
		return "Referencia [id=" + id + ", idCliente=" + idCliente + ", fecha=" + fecha + ", noReferencia="
				+ noReferencia + ", importe=" + importe + ", noAutorizacion=" + noAutorizacion + ", noMembresia="
				+ noMembresia + ", nombreTitular=" + nombreTitular + ", nombrePrograma=" + nombrePrograma + "]";
	}
	
	
}
