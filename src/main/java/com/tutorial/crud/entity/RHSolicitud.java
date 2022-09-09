package com.tutorial.crud.entity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

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
@Table(name="rh_solicitud") //Se utiliza para poner el nombre real de la tabla en la base de datos
public class RHSolicitud {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "fecha_inicio")
	private Date fechaInicio;
	
	@Column(name = "fecha_fin")
	private Date fechaFin;

	@Column(name = "fecha_solicitud")
	private Date fechaSolicitud;
	
    @ManyToOne()
    @JoinColumn(name = "id_empleado")
    private RHEmpleado empleado;
    
    @Column(name = "solicita")
    private String solicita;
    
    @Column(name = "dias_extra")
    private int diasMenos;
    
    @OneToOne(mappedBy = "solicitud",cascade=CascadeType.ALL)
	private RHAprovados aprovados;
    
    @OneToOne(mappedBy = "solicitud",cascade=CascadeType.ALL)
	 private RHFirma firma;

    
    
	public int getDiasMenos() {
		return diasMenos;
	}

	public void setDiasMenos(int diasExtra) {
		this.diasMenos = diasExtra;
	}
	public String getValidacion() {
		return firma.getValidacion();
	}
	public String getAprovados() {
		return aprovados.getAprovado();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public Date obtenerFechaInicio() {
		return fechaInicio;
	}
	public String getFechaInicio() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		// Aqui usamos la instancia formatter para darle el formato a la fecha. Es importante ver que el resultado es un string.
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		String fechaTexto = formatter.format(fechaInicio);
		return fechaTexto;
	}
	public void setFechaInicio(Date horaEntrada) {
		this.fechaInicio = horaEntrada;
	}
	
	public String getFechaFin() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		// Aqui usamos la instancia formatter para darle el formato a la fecha. Es importante ver que el resultado es un string.
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		String fechaTexto = formatter.format(fechaFin);
		return fechaTexto;
	}
	public void setFechaFin(Date horaEntrada) {		
		this.fechaFin = horaEntrada;
	}
	
	public String getFechaSolicitud() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		// Aqui usamos la instancia formatter para darle el formato a la fecha. Es importante ver que el resultado es un string.
		String fechaTexto = formatter.format(fechaSolicitud);
		return fechaTexto;
	}
	public void setFechaSolicitud(Date fecha)  {
		this.fechaSolicitud = fecha;
	}

	public void setEmpleado(RHEmpleado empleado) {
		this.empleado = empleado;
	}
	
	public String getSolicita() {
		return solicita;
	}

	public void setSolicita(String solicita) {
		this.solicita = solicita;
	}

	public void setAprovados(RHAprovados aprovados) {
		this.aprovados = aprovados;
	}

	public void setFirma(RHFirma firma) {
		this.firma = firma;
	}

	public long getDiasSolicitados() {
		 long daysBetween = Duration.between(fechaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
				 fechaFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()).toDays();
		return daysBetween+1;
	}
	@Override
	public String toString() {
		return "RHSolicitud [id=" + id + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", fechaSolicitud="
				+ fechaSolicitud + ", empleado=" + empleado + "]";
	}

    
}
