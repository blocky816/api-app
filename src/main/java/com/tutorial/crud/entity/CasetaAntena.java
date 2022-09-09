package com.tutorial.crud.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="caseta_antena") //Se utiliza para poner el nombre real de la tabla en la base de datos
public class CasetaAntena {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@ManyToOne
    @JoinColumn(name = "caseta_id")
    private Caseta caseta;

	@ManyToOne
    @JoinColumn(name = "antena_id")
    private Antena antena;
	
	@Column(name = "sentido")
	private boolean sentido;
	
	
	public int getId() {
		return id;
	}


	public Caseta getCaseta() {
		return caseta;
	}


	public void setCaseta(Caseta caseta) {
		this.caseta = caseta;
	}


	public Antena getAntena() {
		return antena;
	}


	public void setAntena(Antena antena) {
		this.antena = antena;
	}


	public boolean isSentido() {
		return sentido;
	}


	public void setSentido(boolean sentido) {
		this.sentido = sentido;
	}

	
}
