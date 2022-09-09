package com.tutorial.crud.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
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
@Table(name="Amonestaciones") //Se utiliza para poner el nombre real de la tabla en la base de datos
public class Amonestaciones {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "id_chip")
	private long idChip;
	
	@Column(name = "cantidad_amonestaciones")
	private int cantidadAmonestaciones;
	
	@Column(name = "hora_entrada")
	private Date horaEntrada;
	
	@Column(name = "hora_salida")
	private Date horaSalida;

	public long getIdChip() {
		return idChip;
	}

	public void setIdChip(long idChip) {
		this.idChip = idChip;
	}

	public int getCantidadAmonestaciones() {
		return cantidadAmonestaciones;
	}

	public void setCantidadAmonestaciones(int cantidadAmonestaciones) {
		this.cantidadAmonestaciones = cantidadAmonestaciones;
	}

	public int getId() {
		return id;
	}

	public String getHoraEntrada() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		// Aqui usamos la instancia formatter para darle el formato a la fecha. Es importante ver que el resultado es un string.
		//formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		String fechaTexto = formatter.format(horaEntrada);
		return fechaTexto;
	}

	public String obtenerHoraEntrada() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		// Aqui usamos la instancia formatter para darle el formato a la fecha. Es importante ver que el resultado es un string.
		//formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		String fechaTexto = formatter.format(horaEntrada);
		return fechaTexto;
	}
	
	public Date obtenerHoraEntradaDate() {
		return this.horaEntrada;
	}
	public Date obtenerHoraSalidaDate() {
		return this.horaSalida;
	}
	public void setHoraEntrada(Date horaEntrada) {
		this.horaEntrada = horaEntrada;
	}

	public String getHoraSalida() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		// Aqui usamos la instancia formatter para darle el formato a la fecha. Es importante ver que el resultado es un string.
		//formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		String fechaTexto = formatter.format(horaSalida);
		return fechaTexto;
	}

	public String obtenerHoraSalida() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy  00:00:00");
		// Aqui usamos la instancia formatter para darle el formato a la fecha. Es importante ver que el resultado es un string.
		//formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		String fechaTexto = formatter.format(horaSalida);
		return fechaTexto;
	}
	
	public void setHoraSalida(Date horaSalida) {
		this.horaSalida = horaSalida;
	}

	@Override
	public String toString() {
		return "Amonestaciones [id=" + id + ", idChip=" + idChip + ", cantidadAmonestaciones=" + cantidadAmonestaciones
				+ "]";
	}

	
		
	
}
