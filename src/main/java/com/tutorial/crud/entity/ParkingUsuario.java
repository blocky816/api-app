package com.tutorial.crud.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="parking_usuario") //Se utiliza para poner el nombre real de la tabla en la base de datos
public class ParkingUsuario {
	
	@Id //Define la llave primaria.    
	@Column(name = "id_venta_detalle")
	private int idVentaDetalle;
	
	@ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "idcliente")
	private Cliente cliente;
	
	@ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "idEmpleado")
	private RHEmpleado rhEmpleado;
	
	@Column(name = "concepto")
	private String concepto;
	
	@Column(name = "id_prod")
	private int idProd;

	@Column(name = "cantidad")
	private int cantidad;
	
	@Column(name = "f_compra")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCaptura;

	@Column(name = "pk")
	private boolean pk;
	
	@Column(name = "activo")
	private boolean activo=true;
	
	@Column(name = "capturado")
	private boolean capturado;
	
	@OneToMany(mappedBy = "parkingUsuario", cascade = CascadeType.ALL)
    List<Carro> carro;

	private String club;
	
	@Column(name = "estado_cobranza")
	private String estadoCobranza;
	
	@Column(name = "correo")
	private String correo;
	
	private String observaciones;
	
	 @OneToOne(mappedBy = "parking")
	 private RegistroTag registroTag;	 
	 
	public String getEstadoCobranza() {
		return estadoCobranza;
	}

	public void setEstadoCobranza(String estadoCobranza) {
		this.estadoCobranza = estadoCobranza;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getClub() {
		return club;
	}

	public void setClub(String club) {
		this.club = club;
	}

	public RegistroTag obtenerRegistroTag() {
		return registroTag;
	}

	public void setRegistroTag(RegistroTag registroTag) {
		this.registroTag = registroTag;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public int getIdVentaDetalle() {
		return idVentaDetalle;
	}

	public void setIdVentaDetalle(int idVentaDetalle) {
		this.idVentaDetalle = idVentaDetalle;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public RHEmpleado getRhEmpleado() {
		return rhEmpleado;
	}

	public void setRhEmpleado(RHEmpleado rhEmpleado) {
		this.rhEmpleado = rhEmpleado;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public int getIdProd() {
		return idProd;
	}

	public void setIdProd(int idProd) {
		this.idProd = idProd;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Date getFechaCaptura() {
		return fechaCaptura;
	}

	public void setFechaCaptura(Date fechaCaptura) {
		this.fechaCaptura = fechaCaptura;
	}

	public boolean isPk() {
		return pk;
	}

	public void setPk(boolean pk) {
		this.pk = pk;
	}

	public List<Carro> getCarro() {
		return carro;
	}

	public void setCarro(List<Carro> carro) {
		this.carro = carro;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	

	public boolean isCapturado() {
		return capturado;
	}

	public void setCapturado(boolean capturado) {
		this.capturado = capturado;
	}

	@Override
	public String toString() {
		return "ParkingUsuario [idVentaDetalle=" + idVentaDetalle + ", cliente=" + cliente.getIdCliente() + ", concepto=" + concepto
				+ ", idProd=" + idProd + ", cantidad=" + cantidad + ", f_compra=" + fechaCaptura + ", pk=" + pk + "]";
	}
	
	
}
