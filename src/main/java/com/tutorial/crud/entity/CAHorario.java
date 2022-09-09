package com.tutorial.crud.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="ca_horario") //Se utiliza para poner el nombre real de la tabla en la base de datos
public class CAHorario {
	@Id //Define la llave primaria.
    @GeneratedValue(generator = "UUID") //Se estableció un tipo de variable UUID 
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id_horario", updatable = false, nullable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private UUID id;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="sala")
	private CASala sala;
	
	@Column(name = "duracion")
	private int duracion;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="actividad")
	private CAActividad actividad;

	@Column(name = "lunes")
	private boolean lunes;

	@Column(name = "martes")
	private boolean martes;

	@Column(name = "miercoles")
	private boolean miercoles;

	@Column(name = "jueves")
	private boolean jueves;	

	@Column(name = "viernes")
	private boolean viernes;

	@Column(name = "sabado")
	private boolean sabado;

	@Column(name = "domingo")
	private boolean domingo;
	
	@Column(name = "hora")
	private String hora;
	
	@Column(name = "rango")
	private String rango;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="tecnico")
	private CATecnico tecnico;
	
	@Column(name = "periodo_inicio")
	private String periodoInicio;
	
	@Column(name = "periodo_final")
	private String periodoFinal;
	
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "created") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	@Temporal(TemporalType.TIMESTAMP)
	private Date created = new Date();	
	
	@Column(name = "updated_by")
	private String updatedBy;
	
	@Column(name = "updated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated=new Date();
	
	@Column(name = "disponible")
	private boolean disponible;
	
	@Column(name = "activo")
	private boolean activo=true;
	


	
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
	

	public CATecnico getTecnico() {
		return tecnico;
	}

	public void setTecnico(CATecnico tecnico) {
		this.tecnico = tecnico;
	}

	@Override
	public String toString() {
		return "Horario [id=" + id + ", sala=" + sala + ", duracion=" + duracion + ", actividad=" + actividad
				+ ", lunes=" + lunes + ", martes=" + martes + ", miercoles=" + miercoles + ", jueves=" + jueves
				+ ", viernes=" + viernes + ", sabado=" + sabado + ", domingo=" + domingo + ", hora=" + hora
				+ ", periodoInicio=" + periodoInicio + ", periodoFinal=" + periodoFinal + ", createdBy=" + createdBy
				+ ", created=" + created + ", updatedBy=" + updatedBy + ", updated=" + updated + ", activo=" + activo
				+ "]";
	}

	public CASala getSala() {
		return sala;
	}

	public void setSala(CASala sala) {
		this.sala = sala;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public CAActividad getActividad() {
		return actividad;
	}

	public void setActividad(CAActividad actividad) {
		this.actividad = actividad;
	}

	public boolean isLunes() {
		return lunes;
	}

	public void setLunes(boolean lunes) {
		this.lunes = lunes;
	}

	public boolean isMartes() {
		return martes;
	}

	public void setMartes(boolean martes) {
		this.martes = martes;
	}

	public boolean isMiercoles() {
		return miercoles;
	}

	public void setMiercoles(boolean miercoles) {
		this.miercoles = miercoles;
	}

	public boolean isJueves() {
		return jueves;
	}

	public void setJueves(boolean jueves) {
		this.jueves = jueves;
	}

	public boolean isViernes() {
		return viernes;
	}

	public void setViernes(boolean viernes) {
		this.viernes = viernes;
	}

	public boolean isSabado() {
		return sabado;
	}

	public void setSabado(boolean sabado) {
		this.sabado = sabado;
	}

	public boolean isDomingo() {
		return domingo;
	}

	public void setDomingo(boolean domingo) {
		this.domingo = domingo;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getPeriodoInicio() {
		return periodoInicio;
	}

	public void setPeriodoInicio(String periodoInicio) {
		this.periodoInicio = periodoInicio;
	}

	public String getPeriodoFinal() {
		return periodoFinal;
	}

	public void setPeriodoFinal(String periodoFinal) {
		this.periodoFinal = periodoFinal;
	}

	public String getRango() {
		return rango;
	}

	public void setRango(String rango) {
		this.rango = rango;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	
	
}
