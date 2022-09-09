package com.tutorial.crud.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Immutable;

public class ReportePromocion {
		
		private int idPromocion;
		
		private String promocion;

		 private String concepto;
		
		private Date fechaInicio;
		
		 private Date fechaFin;
		
		 private Date idCapturoFecha;
		 
		 private float subImporte;
		 
		 private float iva;
		 
		 private String recibo;
		 
		 private Date reciboFecha;
		 
		 private int idOrdenDeVenta;
		 
		 private float total;
		
		 private float restante;
		
		private float pagado;
		
		private String descripcion;
		
		private String club;

		private int idCliente;
		
		private String cliente;

		private long membresia;
		
		private String observaciones;

		public int getIdPromocion() {
			return idPromocion;
		}

		public void setIdPromocion(int idPromocion) {
			this.idPromocion = idPromocion;
		}

		public String getPromocion() {
			return promocion;
		}

		public void setPromocion(String promocion) {
			this.promocion = promocion;
		}

		public String getConcepto() {
			return concepto;
		}

		public void setConcepto(String concepto) {
			this.concepto = concepto;
		}

		

		public Date getFechaInicio() {
			return fechaInicio;
		}

		public void setFechaInicio(Date fechaInicio) {
			this.fechaInicio = fechaInicio;
		}

		public Date getFechaFin() {
			return fechaFin;
		}

		public void setFechaFin(Date fechaFin) {
			this.fechaFin = fechaFin;
		}

		public Date getIdCapturoFecha() {
			return idCapturoFecha;
		}

		public void setIdCapturoFecha(Date idCapturoFecha) {
			this.idCapturoFecha = idCapturoFecha;
		}

		public float getRestante() {
			return restante;
		}

		public void setRestante(float restante) {
			this.restante = restante;
		}

		public float getPagado() {
			return pagado;
		}

		public void setPagado(float pagado) {
			this.pagado = pagado;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}

		public String getClub() {
			return club;
		}

		public void setClub(String club) {
			this.club = club;
		}

		public int getIdCliente() {
			return idCliente;
		}

		public void setIdCliente(int idCliente) {
			this.idCliente = idCliente;
		}

		public String getCliente() {
			return cliente;
		}

		public void setCliente(String cliente) {
			this.cliente = cliente;
		}

		public long getMembresia() {
			return membresia;
		}

		public void setMembresia(long membresia) {
			this.membresia = membresia;
		}

		public String getObservaciones() {
			return observaciones;
		}

		public void setObservaciones(String observaciones) {
			this.observaciones = observaciones;
		}

		public float getSubImporte() {
			return subImporte;
		}

		public void setSubImporte(float subImporte) {
			this.subImporte = subImporte;
		}

		public float getIva() {
			return iva;
		}

		public void setIva(float iva) {
			this.iva = iva;
		}

		public String getRecibo() {
			return recibo;
		}

		public void setRecibo(String recibo) {
			this.recibo = recibo;
		}

		public Date getReciboFecha() {
			return reciboFecha;
		}

		public void setReciboFecha(Date reciboFecha) {
			this.reciboFecha = reciboFecha;
		}

		public int getIdOrdenDeVenta() {
			return idOrdenDeVenta;
		}

		public void setIdOrdenDeVenta(int idOrdenDeVenta) {
			this.idOrdenDeVenta = idOrdenDeVenta;
		}

		public float getTotal() {
			return total;
		}

		public void setTotal(float total) {
			this.total = total;
		}

		@Override
		public String toString() {
			return "ReportePromocion [idPromocion=" + idPromocion + ", promocion=" + promocion + ", concepto="
					+ concepto +  ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin
					+ ", idCapturoFecha=" + idCapturoFecha + ", subImporte=" + subImporte + ", iva=" + iva + ", recibo="
					+ recibo + ", reciboFecha=" + reciboFecha + ", idOrdenDeVenta=" + idOrdenDeVenta + ", total="
					+ total + ", restante=" + restante + ", pagado=" + pagado + ", descripcion=" + descripcion
					+ ", club=" + club + ", idCliente=" + idCliente + ", cliente=" + cliente + ", membresia="
					+ membresia + ", observaciones=" + observaciones + "]";
		}

		
	

}
