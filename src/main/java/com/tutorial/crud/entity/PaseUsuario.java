package com.tutorial.crud.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="pase_usuario") //Se utiliza para poner el nombre real de la tabla en la base de datos
public class PaseUsuario {
	
	@Id //Define la llave primaria.    
	@Column(name = "id_venta_detalle")
	private int idVentaDetalle;
	
	@ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "idcliente")
	private Cliente cliente;
	
	@Column(name = "concepto")
	private String concepto;
	
	@Column(name = "id_prod")
	private int idProd;

	@Column(name = "cantidad")
	private int cantidad=5;

	@Column(name = "disponibles")
	private int disponibles=5;

	@Column(name = "consumido")
	private int consumido=cantidad-disponibles;
	
	
	
	@Column(name = "f_compra")
	@Temporal(TemporalType.TIMESTAMP)
	private Date f_compra;

	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "created") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	@Temporal(TemporalType.TIMESTAMP)
	private Date created = new Date();	
	
	@Column(name = "updated_by")
	private String updatedBy;
	
	@Column(name = "updated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated=new Date();
	
	@Column(name = "activo")
	private boolean activo=true;
	
	public String obtenerCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date obtenerCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String obtenerUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date obtenerUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public boolean esActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public int getIdVentaDetalle() {
		return idVentaDetalle;
	}

	public void setIdVentaDetalle(int idVentaDetalle) {
		this.idVentaDetalle = idVentaDetalle;
	}

	public int getCliente() {
		if(cliente!=null)
			return cliente.getIdCliente();
		else
			return 0;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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

	public int getDisponibles() {
		return disponibles;
	}

	public void setDisponibles(int disponibles) {
		this.disponibles = disponibles;
	}

	public long getF_compra() {
		return f_compra.getTime();
	}

	public void setF_compra(Date f_compra) {
		this.f_compra = f_compra;
	}
	
	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	
	public int getConsumido() {
		return consumido;
	}

	public void setConsumido(int consumido) {
		this.consumido = consumido;
	}

	@Override
	public String toString() {
		return "PaseUsuario [idVentaDetalle=" + idVentaDetalle + ", cliente=" + cliente.getIdCliente() + ", idProd=" + idProd
				+ ", cantidad=" + cantidad + ", disponibles=" + disponibles + ", f_compra=" + f_compra + ", createdBy="
				+ createdBy + ", created=" + created + ", updatedBy=" + updatedBy + ", updated=" + updated + ", activo="
				+ activo + "]";
	}	
}
