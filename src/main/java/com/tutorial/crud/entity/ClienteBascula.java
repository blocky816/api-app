package com.tutorial.crud.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity 
@Table(name="cliente_bascula")
public class ClienteBascula {
	
		@Id 
		@GeneratedValue(strategy=GenerationType.IDENTITY)	
		@Column(name="id") 
		public int id;
		
		@JsonProperty("Sexo") 
		@Column(name="sexo")
	    public String sexo;
		
		@JsonProperty("Altura") 
		@Column(name="altura")
	    public int altura;
		
		@JsonProperty("Atleta") 
		@Column(name="atleta")
	    public boolean atleta;
		
		@JsonProperty("NivelActividad") 
		@Column(name="nivel_actividad")
	    public String nivelActividad;		
		
	    @JsonProperty("Peso") 
		@Column(name="peso")
	    public float peso;
	    
	    @JsonProperty("MasaOsea") 
		@Column(name="masa_osea")
	    public float masaOsea;

	    @JsonProperty("IMC") 
		@Column(name="imc")
	    public float iMC;
	    
	    @JsonProperty("EdadMetabolica") 
		@Column(name="edad_metabolica")
	    public int edadMetabolica;
	    
	    @JsonProperty("MasaGrasa") 
		@Column(name="masa_grasa")
	    public float masaGrasa;
	    
	    @JsonProperty("Agua") 
		@Column(name="agua")
	    public float agua;
	    
	    @JsonProperty("CaloriasDiarias") 
		@Column(name="calorias_diarias")
	    public float caloriasDiarias;
	    
	    @JsonProperty("MasaMagra") 
		@Column(name="masa_magra")
	    public float masaMagra;
	    
	    @JsonProperty("Adiposidad") 
		@Column(name="adiposidad")
	    public float adiposidad;
	    
	    @JsonProperty("ValoracionFisica") 
		@Column(name="valoracion_fisica")
	    public int valoracionFisica;
	    
	    @JsonProperty("TMB") 
		@Column(name="tmb")
	    public float tMB;
	    
	    @JsonProperty("IDUsuario") 
		@Column(name="id_usuario")
	    public int idUsuario;
	    
	    @JsonProperty("IDTerminal") 
		@Column(name="id_terminal")
	    public int idTerminal;
	    
	    @JsonProperty("FechaCaptura")
		@Column(name="fecha_captura")
	    public Date fechaCaptura;
	}
