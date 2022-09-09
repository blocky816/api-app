package com.tutorial.crud.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="registro_salida_sp") 
public class RegistroSalidaSP {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
    @JoinColumn(name="folio")
	private int folio;
	
	@Column(name="disponible")
	private int disponible;

	@Column(name = "hora_salida")
	private Date horaSalida;
	
	@Column(name = "acceso")
	private boolean acceso;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tipo_acceso")
    private TipoAcceso tipoAcceso;

	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}

	public int getDisponible() {
		return disponible;
	}

	public void setDisponible(int disponible) {
		this.disponible = disponible;
	}

	public Date getHoraSalida() {
		return horaSalida;
	}

	public void setHoraSalida(Date horaSalida) {
		this.horaSalida = horaSalida;
	}

	public boolean isAcceso() {
		return acceso;
	}

	public void setAcceso(boolean acceso) {
		this.acceso = acceso;
	}

	public TipoAcceso getTipoAcceso() {
		return tipoAcceso;
	}

	public void setTipoAcceso(TipoAcceso tipoAcceso) {
		this.tipoAcceso = tipoAcceso;
	}

	public int getId() {
		return id;
	}
		
}
