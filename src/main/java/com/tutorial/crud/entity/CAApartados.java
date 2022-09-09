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
@Table(name="ca_apartados") //Se utiliza para poner el nombre real de la tabla en la base de datos
public class CAApartados {
	@Id //Define la llave primaria.
    @GeneratedValue(generator = "UUID") //Se estableció un tipo de variable UUID 
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id_apartados", updatable = false, nullable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private UUID id;	
	
	@ManyToOne(optional = false)
    @JoinColumn(name="id_horario")
    private CAHorario horario;
	
	@Column(name = "conteo")
	private int conteo;
	
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

	 @OneToMany(mappedBy = "apartados")
	 List<CAApartadosUsuario> apartadosUsuario;
	 
	 
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

	public CAHorario getHorario() {
		return horario;
	}

	public void setHorario(CAHorario horario) {
		this.horario = horario;
	}

	public int getConteo() {
		return conteo;
	}

	public void setConteo(int conteo) {
		this.conteo = conteo;
	}
	
	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	@Override
	public String toString() {
		return "CAApartados [id=" + id + ", horario=" + horario + ", conteo=" + conteo + ", createdBy=" + createdBy
				+ ", created=" + created + ", updatedBy=" + updatedBy + ", updated=" + updated + ", activo=" + activo
				+ "]";
	}
	
}
