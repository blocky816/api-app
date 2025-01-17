package com.tutorial.crud.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.ClienteBascula;

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
	    
		public static List<ClienteBasculaVista> ObtenerLista(List<ClienteBascula> cbList, Cliente cliente){ 
			List<ClienteBasculaVista> lista;
			AtomicInteger indice = new AtomicInteger(0);
			lista = cbList.stream().map(ultimoPesaje -> {
				ClienteBasculaVista upv=new ClienteBasculaVista();
				long nacimiento=new Date().getTime()-cliente.getFechaNacimiento().getTime();
				int yearsOld=(int) (nacimiento/(365*24 * 60 * 60 * 1000L));
				upv.adiposidad=ultimoPesaje.adiposidad;
				upv.agua=ultimoPesaje.agua;
				upv.altura=ultimoPesaje.altura;
				upv.atleta=ultimoPesaje.atleta;
				upv.caloriasDiarias=ultimoPesaje.caloriasDiarias;
				upv.edadMetabolica=ultimoPesaje.edadMetabolica;
				upv.edadUsuario=yearsOld;

				SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd HH:mm");
				String fechaTexto = formatter.format(ultimoPesaje.fechaCaptura);
				upv.fechaCaptura=fechaTexto;
				if(indice.getAndIncrement()==0) {
					upv.foto=cliente.getURLFoto().getImagen();
					upv.nombre=cliente.getNombre()+" "+cliente.getApellidoPaterno()+" "+cliente.getApellidoMaterno();
				}
				upv.id=ultimoPesaje.id;
				upv.idTerminal=ultimoPesaje.idTerminal;
				upv.idUsuario=ultimoPesaje.idUsuario;
				upv.iMC=ultimoPesaje.iMC;
				upv.masaGrasa=ultimoPesaje.masaGrasa;
				upv.masaMagra=ultimoPesaje.masaMagra;
				upv.masaOsea=ultimoPesaje.masaOsea;
				upv.nivelActividad=ultimoPesaje.nivelActividad;
				upv.peso=ultimoPesaje.peso;
				if (cliente != null){
					if (cliente.getIdSexo() == 1) upv.sexo = "0";
					if (cliente.getIdSexo() == 2) upv.sexo = "1";
				}
				else upv.sexo=ultimoPesaje.sexo;
				upv.tMB=ultimoPesaje.tMB;
				upv.valoracionFisica=ultimoPesaje.valoracionFisica;
				return upv;
			}).collect(Collectors.toList());
			return lista;
		}	    
	}
