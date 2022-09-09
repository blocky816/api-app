package com.tutorial.crud.entity;


import java.sql.Timestamp;
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
@Table(name="registro_parking") //Se utiliza para poner el nombre real de la tabla en la base de datos
public class RegistroParking {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne()
    @JoinColumn(name="id_antena")
	private Antena idAntena;

	@ManyToOne(optional = false)
    @JoinColumn(name="id_caseta")
	private Caseta idCaseta;

	@ManyToOne()
    @JoinColumn(name="id_chip")
	private RegistroTag idChip;

	@Column(name = "hora_evento")
	private Date horaEvento;
	
	@Column(name = "hora_entrada")
	private Timestamp horaEntrada;
	
	@Column(name = "mensaje")
	private String mensaje;
	
	@Column(name = "raw")
	private String raw;	
	
	@Column(name = "acceso")
	private boolean acceso;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tipo_acceso")
    private TipoAcceso tipoAcceso;

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

	public Timestamp getHoraEntrada() {
		return horaEntrada;
	}

	public void setHoraEntrada(long horaEntrada) {
		this.horaEntrada =new Timestamp(horaEntrada);
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getRaw() {
		return raw;
	}

	public void setRaw(String raw) {
		this.raw = raw;
	}

	public Antena getIdAntena() {
		return idAntena;
	}

	public void setIdAntena(Antena idAntena) {
		this.idAntena = idAntena;
	}

	public Caseta getIdCaseta() {
		return idCaseta;
	}

	public void setIdCaseta(Caseta idCaseta) {
		this.idCaseta = idCaseta;
	}

	public RegistroTag getIdChip() {
		return idChip;
	}

	public void setIdChip(RegistroTag idChip) {
		this.idChip = idChip;
	}

	public Date getHoraEvento() {
		return horaEvento;
	}

	public void setHoraEvento(Date horaEvento) {
		this.horaEvento = horaEvento;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "RegistroParking [id=" + id + ", idAntena=" + idAntena + ", idCaseta=" + idCaseta + ", idChip=" + idChip
				+ ", horaEvento=" + horaEvento + ", horaEntrada=" + horaEntrada + ", mensaje=" + mensaje + ", raw="
				+ raw + ", acceso=" + acceso + ", tipoAcceso=" + tipoAcceso + "]";
	}	

	
	
}
