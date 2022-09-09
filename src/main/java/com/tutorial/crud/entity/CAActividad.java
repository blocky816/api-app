package com.tutorial.crud.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="ca_actividad") //Se utiliza para poner el nombre real de la tabla en la base de datos
public class CAActividad {
	@Id //Define la llave primaria.
    @GeneratedValue(generator = "UUID") //Se estableció un tipo de variable UUID 
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private UUID id;

	@Column(name = "sobreescribir")
	private boolean sobreescribir;

	@Column(name = "nombre")
	private String nombre;	

	@Column(name = "segmentacion")
	private boolean segmentacion;

	@Column(name = "dificultad")
	private String dificultad;

	@Column(name = "cupo")
	private int cupo;
	
	@Column(name = "max")
	private int max;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="tipo_actividad")
	private CATipoActividad tipoActividad;
	
	@Column(name = "paga")
	private int paga;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "created") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	@Temporal(TemporalType.TIMESTAMP)
	private Date created = new Date();	
	
	@Column(name = "updated_by")
	private String updatedBy;
	
	@Column(name = "updated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated=new Date();
	
	@Column(name = "activo")
	private boolean activo=true;



	
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	

	public void setCreated(Date created) {
		this.created = created;
	}

	

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public boolean esActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	
	public UUID getId() {
		return id;
	}

	public boolean isSobreescribir() {
		return sobreescribir;
	}

	public void setSobreescribir(boolean sobreescribir) {
		this.sobreescribir = sobreescribir;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isSegmentacion() {
		return segmentacion;
	}

	public void setSegmentacion(boolean segmentacion) {
		this.segmentacion = segmentacion;
	}

	public String getDificultad() {
		return dificultad;
	}

	public void setDificultad(String dificultad) {
		this.dificultad = dificultad;
	}

	public CATipoActividad getTipoActividad() {
		return tipoActividad;
	}

	public void setTipoActividad(CATipoActividad tipoActividad) {
		this.tipoActividad = tipoActividad;
	}

	public int getPaga() {
		return paga;
	}

	public void setPaga(int paga) {
		this.paga = paga;
	}
	
	

	public int getCupo() {
		return cupo;
	}

	public void setCupo(int cupo) {
		this.cupo = cupo;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	@Override
	public String toString() {
		return "CAActividad [id=" + id + ", sobreescribir=" + sobreescribir + ", nombre=" + nombre + ", segmentacion="
				+ segmentacion + ", dificultad=" + dificultad + ", cupo=" + cupo + ", max=" + max + ", tipoActividad="
				+ tipoActividad + ", paga=" + paga + ", createdBy=" + createdBy + ", created=" + created
				+ ", updatedBy=" + updatedBy + ", updated=" + updated + ", activo=" + activo + "]";
	}


	
}
