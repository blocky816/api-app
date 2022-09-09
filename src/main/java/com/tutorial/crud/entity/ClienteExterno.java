package com.tutorial.crud.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.tutorial.crud.service.ClubService;

@Entity
@Table(name="cliente_externo") //Se utiliza para poner el nombre real de la tabla en la base de datos
public class ClienteExterno {
	
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
 
	
	@OneToOne(cascade=CascadeType.ALL) //Relación de uno a uno
	@JoinColumn(name="idclub") //Se utiliza para marcar una propiedad
	private Club club;
	
	@Column(name = "nombre_completo")
	private String nombreCompleto;
	
	@Column(name = "sexo")
	private String sexo;

	@Column(name = "telefono")
	private String telefono;
	
	@Column(name = "correo_electronico")
	private String correoElectronico;

	@Column(name = "tipo")
	private String tipo;
	
	@Column(name = "fecha_registro")
	private Date fechaRegistro;

	@Column(name = "activo")
	private boolean activo=true;
	
	@Column(name = "titular")
	private boolean titular;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="id_dependiente")
	private List<ClienteExterno> dependientes;

	
	
	public void setId(int id) {
		this.id = id;
	}

	public boolean isTitular() {
		return titular;
	}

	public void setTitular(boolean titular) {
		this.titular = titular;
	}

	public String getClub() {
		return club.getNombre();
	}

	public void setClub(Club club) {
		
		this.club = club;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getFechaRegistro() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		// Aqui usamos la instancia formatter para darle el formato a la fecha. Es importante ver que el resultado es un string.
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		String fechaTexto = formatter.format(fechaRegistro);
		return fechaTexto;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public int getId() {
		return id;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public List<ClienteExterno> obtenerDependientes() {
		return dependientes;
	}
	public void setDependientes(List<ClienteExterno> dependientes) {
		this.dependientes = dependientes;
	}

	@Override
	public String toString() {
		return "ClienteExterno [id=" + id + ", club=" + club + ", nombreCompleto=" + nombreCompleto + ", sexo=" + sexo
				+ ", telefono=" + telefono + ", correoElectronico=" + correoElectronico + ", tipo=" + tipo
				+ ", fechaRegistro=" + fechaRegistro + ", activo=" + activo + ", dependientes=" + dependientes + "]";
	}
	
	
	
    
}
