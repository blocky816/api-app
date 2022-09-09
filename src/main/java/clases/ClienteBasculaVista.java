package clases;

import java.io.Serializable;
import java.util.Arrays;


public class ClienteBasculaVista implements Serializable{
	
		public int id;
		
	    public String sexo;
		
	    public int altura;
		
	    public boolean atleta;
		
	    public String nivelActividad;		
		
	    public float peso;
	    
	    public float masaOsea;

	    public float iMC;
	    
	    public int edadMetabolica;
	    
	    public float masaGrasa;
	    
	    public float agua;
	    
	    public float caloriasDiarias;
	    
	    public float masaMagra;
	    
	    public float adiposidad;
	    
	    public int valoracionFisica;
	    
	    public float tMB;
	    
	    public int idUsuario;
	    
	    public int idTerminal;
	    
	    public String fechaCaptura;
	    

	    public int edadUsuario;

	    public String nombre;
	    
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
