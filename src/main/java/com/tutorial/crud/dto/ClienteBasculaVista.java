package com.tutorial.crud.dto;

import java.io.Serializable;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClienteBasculaVista  implements Serializable{
	
		public int id;
		
		@JsonProperty("Sexo") 
	    public String sexo;
		
		@JsonProperty("Altura") 
	    public int altura;
		
		@JsonProperty("Atleta") 
	    public boolean atleta;
		
		@JsonProperty("NivelActividad") 
	    public String nivelActividad;		
		
	    @JsonProperty("Peso") 
	    public float peso;
	    
	    @JsonProperty("MasaOsea") 
	    public float masaOsea;

	    @JsonProperty("IMC") 
	    public float iMC;
	    
	    @JsonProperty("EdadMetabolica") 
	    public int edadMetabolica;
	    
	    @JsonProperty("MasaGrasa") 
	    public float masaGrasa;
	    
	    @JsonProperty("Agua") 
	    public float agua;
	    
	    @JsonProperty("CaloriasDiarias") 
	    public float caloriasDiarias;
	    
	    @JsonProperty("MasaMagra") 
	    public float masaMagra;
	    
	    @JsonProperty("Adiposidad") 
	    public float adiposidad;
	    
	    @JsonProperty("ValoracionFisica") 
	    public int valoracionFisica;
	    
	    @JsonProperty("TMB") 
	    public float tMB;
	    
	    @JsonProperty("IDUsuario") 
	    public int idUsuario;
	    
	    @JsonProperty("IDTerminal") 
	    public int idTerminal;
	    
	    @JsonProperty("FechaCaptura")
	    public String fechaCaptura;
	    

	    @JsonProperty("EdadUsuario")
	    public int edadUsuario;

	    @JsonProperty("Nombre")
	    public String nombre;
	    
	    @JsonProperty("Foto")
	    public byte[] foto;

		@Override
		public String toString() {
			return "ClienteBasculaVista [id=" + id + ", sexo=" + sexo + ", altura=" + altura + ", atleta=" + atleta
					+ ", nivelActividad=" + nivelActividad + ", peso=" + peso + ", masaOsea=" + masaOsea + ", iMC="
					+ iMC + ", edadMetabolica=" + edadMetabolica + ", masaGrasa=" + masaGrasa + ", agua=" + agua
					+ ", caloriasDiarias=" + caloriasDiarias + ", masaMagra=" + masaMagra + ", adiposidad=" + adiposidad
					+ ", valoracionFisica=" + valoracionFisica + ", tMB=" + tMB + ", idUsuario=" + idUsuario
					+ ", idTerminal=" + idTerminal + ", fechaCaptura=" + fechaCaptura + ", edadUsuario=" + edadUsuario
					+ ", nombre=" + nombre + ", foto=" + Arrays.toString(foto) + "]";
		}
	    
	    
	}
