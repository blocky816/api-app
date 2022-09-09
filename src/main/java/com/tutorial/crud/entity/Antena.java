package com.tutorial.crud.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="antena") //Se utiliza para poner el nombre real de la tabla en la base de datos
public class Antena {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@OneToMany(mappedBy = "antena")
    List<CasetaAntena> casetaAntena;

	
	public int getId() {
		return id;
	}


	public List<CasetaAntena> getCasetaAntena() {
		return casetaAntena;
	}


	public void setCasetaAntena(List<CasetaAntena> casetaAntena) {
		this.casetaAntena = casetaAntena;
	}

		
	
}
