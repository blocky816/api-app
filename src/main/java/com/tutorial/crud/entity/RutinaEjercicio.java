package com.tutorial.crud.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.tutorial.crud.dto.MovimientoDTO;

@Entity 
@Table(name="rutina_ejercicio")
public class RutinaEjercicio implements Comparable<RutinaEjercicio>{

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	@Column(name="id") 
	private int id;
	
	@ManyToOne
	@JoinColumn(name="rutina_id")
	private Rutina rutina;	
	
	@ManyToOne
	@JoinColumn(name="ejercicio_id")
	private Ejercicio ejercicio;
	
	@Column(name="dia")
	private int dia;
	
	
	@Column(name="repeticiones")
	private int repeticiones;
	
	@Column(name="series")
	private int series;
	
	@Column(name="orden")
	private int orden;
	
	

	public void setRutina(Rutina rutina) {
		this.rutina = rutina;
	}

	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public int getIdEjercicio() {
		return ejercicio.getId();
	}
	
	public String getRutaImagen() {
		return ejercicio.getRuta();
	}
	
	public String getNombre() {
		return ejercicio.getNombre();
	}
	
	public String getGrupoMuscular() {
		return ejercicio.getGrupoMuscular();
	}
	public String getMaquina() {
		return ejercicio.getMaquina();
	}
	
	

	public void setEjercicio(Ejercicio ejercicio) {
		this.ejercicio = ejercicio;
	}

	public int getRepeticiones() {
		return repeticiones;
	}

	public void setRepeticiones(int repeticiones) {
		this.repeticiones = repeticiones;
	}

	public int getSeries() {
		return series;
	}

	public void setSeries(int series) {
		this.series = series;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	@Override
	public String toString() {
		return "RutinaEjercicio [id=" + id +  ", ejercicio=" + ejercicio + ", dia=" + dia
				+ ", repeticiones=" + repeticiones + ", series=" + series + ", orden=" + orden + "]";
	}

	@Override
	public int compareTo(RutinaEjercicio o) {
		int a=this.dia;
		int b=o.getDia();
		return Integer.compare(a, b);
	}
	
	
	
	
}
