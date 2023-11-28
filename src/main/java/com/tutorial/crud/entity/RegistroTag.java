package com.tutorial.crud.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="registro_tag") //Se utiliza para poner el nombre real de la tabla en la base de datos
@JsonIgnoreProperties(value = { "createdAt", "updatedAt" })
public class RegistroTag {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "id_chip")
	private long idChip;

	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_parking", referencedColumnName = "id_venta_detalle")
    private ParkingUsuario parking;

	@Column(name = "activo")
	private boolean activo;

	@Column(name = "fecha_fin")
	private Date fechaFin;
	
	@Column(name = "Club")
	private String club;

	@CreationTimestamp
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "created_at", columnDefinition = "TIMESTAMP(0)")
	private LocalDateTime createdAt;
	@UpdateTimestamp
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "updated_at", columnDefinition = "TIMESTAMP(0)")
	private LocalDateTime updatedAt;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIdChip() {
		return Long.toHexString(idChip);
	}
	
	public long obtenerIdChip() {
		return idChip;
	}

	public void setIdChip(long idChip) {
		this.idChip = idChip;
	}

	
	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getClub() {
		return club;
	}

	public void setClub(String club) {
		this.club = club;
	}


	public int getParking() {
		return parking.getIdVentaDetalle();
	}
	public ParkingUsuario obtenerParking() {
		return parking;
	}

	public void setParking(ParkingUsuario parking) {
		this.parking = parking;
	}

	public LocalDateTime getCreatedAt() { return createdAt; }

	public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

	public LocalDateTime getUpdatedAt() { return updatedAt; }

	public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

	@Override
	public String toString() {
		return "RegistroTag [id=" + id + ", idChip=" + idChip + ", parking=" + parking + ", activo=" + activo
				+ ", fecha_Fin=" + fechaFin + ", club=" + club  + "]";
	}

	
	
	
}
