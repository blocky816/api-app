package com.tutorial.crud.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="reto") //Se utiliza para poner el nombre real de la tabla en la base de datos
public class Reto {
	@Id //Define la llave primaria.
    @GeneratedValue(generator = "UUID") //Se estableció un tipo de variable UUID 
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id_reto", updatable = false, nullable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private UUID id;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@OneToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="tipo_reto")
	private TipoReto tipoReto;
	
	@Column(name="no_inscritos")
	private int noInscritos;

	@Column(name="cupo_maximo")
	private int cupoMaximo;
	
	@Column(name = "fecha_inicio")
	private String fechaInicio;

	@Column(name = "fecha_fin")
	private String fechaFin;
	
	 //@OneToMany(mappedBy = "reto")
	 //List<RetoUsuario> retoUsuario;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "created") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	@Temporal(TemporalType.TIMESTAMP)
	private Date created = new Date();	
	
	@Column(name = "updated_by")
	private String updatedBy;
	
	@Column(name = "updated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated = new Date();
	
	@Column(name = "activo")
	private boolean activo = true;

	//Nuevos campos
	@Column(name = "dispositivo")
	private String dispositivo;

	@Column(name = "club")
	private int club;

	@Column(name="banner", columnDefinition="text")
	private String banner;
	@Column(name="icono", columnDefinition="text")
	private String icono;

	@Column(name = "max")
	private int max;

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public TipoReto getTipoReto() {
		return tipoReto;
	}

	public void setTipoReto(TipoReto tipoReto) {
		this.tipoReto = tipoReto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public UUID getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getNoInscritos() { return noInscritos; }

	public void setNoInscritos(int noInscritos) { this.noInscritos = noInscritos; }

	public int getCupoMaximo() { return cupoMaximo; }

	public void setCupoMaximo(int cupoMaximo) { this.cupoMaximo = cupoMaximo; }

	/*public List<RetoUsuario> getRetoUsuario() { return retoUsuario; }*/

	/*public void setRetoUsuario(List<RetoUsuario> retoUsuario) { this.retoUsuario = retoUsuario; }*/

	public String getDispositivo() { return dispositivo; }

	public void setDispositivo(String dispositivo) { this.dispositivo = dispositivo; }

	public int getClub() { return club; }

	public void setClub(int club) { this.club = club; }

	public String getBanner() { return banner; }

	public void setBanner(String banner) { this.banner = banner; }

	public String getIcono() { return icono; }

	public void setIcono(String icono) { this.icono = icono; }

	public int getMax() { return max; }

	public void setMax(int max) { this.max = max; }
}
