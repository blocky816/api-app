package com.tutorial.crud.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity 
@Table(name="rutina")
public class Rutina {

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	@Column(name="id") 
	private int id;
	
	@Column(name="nombre_rutina")
	private String nombreRutina;
	
	@Column(name="nombre_objetivo")
	private String nombreObjetivo;
	
	@Column(name="semanas")
	private int semanas;
	
	@Column(name="activo")
	private boolean activo;	

	@Column(name="plantilla")
	private boolean plantilla;
	

	@Column(name="tipo_plantilla")
	private String tipoPlantilla;
	
	private String comentarios;
	
	
	@Column(name="created")
	private Date created;
	
	@Column(name="updated")
	private Date updated;

	@OneToMany(mappedBy="rutina")
	private List<Cliente> cliente;
	
	@OneToMany(mappedBy="rutina",cascade=CascadeType.ALL)
	private List<RutinaEjercicio> ejercicios;
	
	public Rutina() {
		cliente=new ArrayList<Cliente>();
	}

	public int getIdCliente(int idCliente) {
		if(cliente.size()==1)
			return cliente.get(0).getIdCliente();
		else
			return idCliente;
	}
	
	public boolean rutinaAsignada() {
		return activo;
	}
	
	public List<RutinaEjercicio> getEjercicios() {
		return ejercicios;
	}

	public void setEjercicios(List<RutinaEjercicio> ejercicios) {
		this.ejercicios = ejercicios;
	}

	/*public Date obtenerUpdated() {
		return updated;
	}*/

	public void setCliente(Cliente cliente) {
		this.cliente.add(cliente) ;
	}

	public String getNombreRutina() {
		return nombreRutina;
	}

	public void setNombreRutina(String nombreRutina) {
		this.nombreRutina = nombreRutina;
	}

	public String getNombreObjetivo() {
		return nombreObjetivo;
	}

	public void setNombreObjetivo(String nombreObjetivo) {
		this.nombreObjetivo = nombreObjetivo;
	}

	public int getSemanas() {
		return semanas;
	}

	public void setSemanas(int semanas) {
		this.semanas = semanas;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	 public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
	/*public java.sql.Date getDiaInicio(){
		java.sql.Date diaInicio=new java.sql.Date(updated.getTime());
		return diaInicio;
		
	}*/
	/*public String getDiaFinal(){
		LocalDateTime fechaRutina=updated.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		fechaRutina=fechaRutina.plusWeeks(semanas);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedLocalDate = fechaRutina.format(formatter);
		return formattedLocalDate;
		
	}*/
	

	public boolean esPlantilla() {
		return plantilla;
	}

	public void setPlantilla(boolean plantilla) {
		this.plantilla = plantilla;
	}


	public String getTipoPlantilla() {
		return tipoPlantilla;
	}

	public void setTipoPlantilla(String tipoPlantilla) {
		this.tipoPlantilla = tipoPlantilla;
	}
	
	public int clientesAsignados() {
		return cliente.size();
	}

	public List<Cliente> obtenerClientes() {
		return cliente;
	}

	public void colocarCliente(List<Cliente> cliente) {
		this.cliente = cliente;
	}
	

	public int obtenerId() {
		return id;
	}


	@Override
	public String toString() {
		return "Rutina [id=" + id + ", nombreRutina=" + nombreRutina + ", nombreObjetivo=" + nombreObjetivo
				+ ", Activo=" + activo + ", created=" + created + ", updated=" + updated
				+ ", ejercicios=" + ejercicios + "]";
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	
	
	
}
