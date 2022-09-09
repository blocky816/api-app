package com.tutorial.crud.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.tutorial.crud.entity.Ejercicio;
import com.tutorial.crud.entity.RutinaEjercicio;

public class VistaPlantilla2 {
		 private int idPlantilla;
		 private String nombreRutina;
		 private String nombreObjetivo;
		 private String tipoPlantilla;
		private String comentarios;
		 private int semanas;
		 List < EjercicioDTO > ejercicios = new ArrayList < EjercicioDTO > ();


		 // Getter Methods 
		 

		 public String getTipoPlantilla() {
			return tipoPlantilla;
		}
		 
		 public String getComentarios() {
			return comentarios;
		}

		public void setComentarios(String comentarios) {
			this.comentarios = comentarios;
		}

		public String getNombreRutina() {
		  return nombreRutina;
		 }



		public int getIdPlantilla() {
			return idPlantilla;
		}

		public String getNombreObjetivo() {
		  return nombreObjetivo;
		 }

		 public int getSemanas() {
		  return semanas;
		 }

		public List<EjercicioDTO> getEjercicios() {
			return ejercicios;
		}
		 // Setter Methods 

		 public void setNombreRutina(String nombreRutina) {
		  this.nombreRutina = nombreRutina;
		 }

		 public void setNombreObjetivo(String nombreObjetivo) {
		  this.nombreObjetivo = nombreObjetivo;
		 }

		 public void setSemanas(int semanas) {
		  this.semanas = semanas;
		 }

		public void setEjercicios(List<EjercicioDTO> list) {
			this.ejercicios = list;
		}

		public void setIdPlantilla(int idPlantilla) {
			this.idPlantilla = idPlantilla;
		}

		public void setTipoPlantilla(String tipoPlantilla) {
			this.tipoPlantilla = tipoPlantilla;
		}

		 
}