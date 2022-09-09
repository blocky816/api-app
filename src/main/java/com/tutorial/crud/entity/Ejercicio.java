package com.tutorial.crud.entity;


import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="ejercicio") //Se utiliza para poner el nombre real de la tabla en la base de datos
public class Ejercicio implements Cloneable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "ruta")
	private String ruta;
	
	@Column(name="ruta_video")
	private String rutaVideo;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "grupo_muscular")
	private String grupoMuscular;

	@Column(name = "maquina")
	private String maquina;
	
	@Column(name = "activo")
	private boolean activo;

	@Column(name = "created")
	private Date created;
	
	@Column(name = "updated")
	private Date updated;
	
	@Column(name = "segmento")
	private String segmento;
	
	@OneToMany(mappedBy="ejercicio")
	List<RutinaEjercicio> ejercicios;
	

	public void setEjercicios(List<RutinaEjercicio> ejercicios) {
		this.ejercicios = ejercicios;
	}

	public String getSegmento() {
		return segmento;
	}

	public void setSegmento(String segmento) {
		this.segmento = segmento;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getGrupoMuscular() {
		return grupoMuscular;
	}

	public void setGrupoMuscular(String grupoMuscular) {
		this.grupoMuscular = grupoMuscular;
	}

	public String getMaquina() {
		return maquina;
	}

	public void setMaquina(String maquina) {
		this.maquina = maquina;
	}

	public int getId() {
		return id;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	

	public String getRutaVideo() {
		return rutaVideo;
	}

	public void setRutaVideo(String rutaVideo) {
		this.rutaVideo = rutaVideo;
	}

	
	public void setId(int id) {
		this.id = id;
	}

	
	public boolean esActivo() {
		return activo;
	}

	
	public Date obtenerCreated() {
		return created;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public String toString() {
		return "Ejercicio [id=" + id + ", ruta=" + ruta + ", rutaVideo=" + rutaVideo + ", nombre=" + nombre
				+ ", grupoMuscular=" + grupoMuscular + ", maquina=" + maquina + ", activo=" + activo + ", created="
				+ created + ", updated=" + updated + "]";
	}

	
	
}
