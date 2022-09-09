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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="agenda_reservas") //Se utiliza para poner el nombre real de la tabla en la base de datos
public class AgendaReservas {
	@Id //Define la llave primaria.
    @GeneratedValue(generator = "UUID") //Se estableció un tipo de variable UUID 
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id_apartados", updatable = false, nullable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private UUID id;	
	
	@ManyToOne(optional = false)
    @JoinColumn(name="id_horario_agenda")
    private AgendaHorario horario;
	
	@Column(name = "conteo")
	private int conteo;
	
	@Column(name = "asesor")
	private String asesor;
	
	@Column(name = "dia")
	private String dia;
	
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
	
	@Column(name = "activo")
	private boolean activo=true;

	 @OneToMany(mappedBy = "reservas")
	 List<AgendaReservasUsuario> reservasUsuario;
	 
	 
	 

	public String getDia() {
		return dia;
	}

	public AgendaHorario getHorario() {
		return horario;
	}

	public int getConteo() {
		return conteo;
	}

	public void setConteo(int conteo) {
		this.conteo = conteo;
	}

	public String getAsesor() {
		return asesor;
	}

	public void setAsesor(String asesor) {
		this.asesor = asesor;
	}

	public List<AgendaReservasUsuario> getReservasUsuario() {
		return reservasUsuario;
	}

	public void setReservasUsuario(List<AgendaReservasUsuario> reservasUsuario) {
		this.reservasUsuario = reservasUsuario;
	}

	public UUID getId() {
		return id;
	}

	public void setHorario(AgendaHorario horario) {
		this.horario = horario;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	 
	 
	 
	 
}
