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

public class VistaRutinaCliente {
		 private String nombreRutina;
		 private String nombreObjetivo;
		 private int semanas;
		 List < RutinaEjercicio > ejercicios = new ArrayList < RutinaEjercicio > ();
		 private java.sql.Date diaInicio;
		 private String diaFinal;


		 // Getter Methods 

		 public String getNombreRutina() {
		  return nombreRutina;
		 }

		 public String getNombreObjetivo() {
		  return nombreObjetivo;
		 }

		 public int getSemanas() {
		  return semanas;
		 }

		 public java.sql.Date getDiaInicio() {
		  return diaInicio;
		 }

		 public String getDiaFinal() {
		  return diaFinal;
		 }

		public List<RutinaEjercicio> getEjercicios() {
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

		 public void setDiaInicio(java.sql.Date diaInicio) {
		  this.diaInicio = diaInicio;
		 }

		 public void setDiaFinal(String diaFinal) {
		  this.diaFinal = diaFinal;
		 }


		public void setEjercicios(List<RutinaEjercicio> list) {
			this.ejercicios = list;
		}
		 
}