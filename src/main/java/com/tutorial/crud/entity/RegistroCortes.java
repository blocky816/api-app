package com.tutorial.crud.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="registro_cortes") //Se utiliza para poner el nombre real de la tabla en la base de datos
public class RegistroCortes {
	@Id //Define la llave primaria.
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private int id;

	@Column(name = "club")
	private String club;

    @Column(name="id_corte",unique = true)
	private String idCorte;
	
	@Column(name = "lugar_corte")
	private String lugarCorte;
	
	@Column(name = "fecha_corte")
	private Date fechaCorte;

	@Column(name = "total_qr")
	private int totalQr;

	public String getClub() {
		return club;
	}

	public void setClub(String club) {
		this.club = club;
	}

	public String getIdCorte() {
		return idCorte;
	}

	public void setIdCorte(String idCorte) {
		this.idCorte = idCorte;
	}

	public String getLugarCorte() {
		return lugarCorte;
	}

	public void setLugarCorte(String lugarCorte) {
		this.lugarCorte = lugarCorte;
	}

	public Date getFechaCorte() {
		return fechaCorte;
	}

	public void setFechaCorte(Date fechaCorte) {
		this.fechaCorte = fechaCorte;
	}

	public int getId() {
		return id;
	}

	public int getTotalQr() {
		return totalQr;
	}

	public void setTotalQr(int totalQr) {
		this.totalQr = totalQr;
	}

	@Override
	public String toString() {
		return "RegistroCortes [id=" + id + ", club=" + club + ", idCorte=" + idCorte + ", lugarCorte=" + lugarCorte
				+ ", fechaCorte=" + fechaCorte + ", totalQr=" + totalQr + "]";
	}

	

}
