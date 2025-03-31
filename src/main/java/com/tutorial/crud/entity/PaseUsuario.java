package com.tutorial.crud.entity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
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
	private boolean activo;

	@Column(name = "subgrupo")
	private String subgrupo;

	@Column(name = "pagado")
	private Boolean pagado;

	@Column(name = "fecha_pago", columnDefinition = "TIMESTAMP(0)")
	private LocalDateTime fechaPago;

	@Column(name = "ultimo_uso", columnDefinition = "TIMESTAMP(0)")
	private LocalDateTime ultimoUso;

	@Column(name = "fecha_vigencia", columnDefinition = "TIMESTAMP(0)")
	private LocalDateTime fechaVigencia;

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

	/*public boolean esActivo() {
		return activo;
	}*/

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

	public String getSubgrupo() {
		return subgrupo;
	}

	public void setSubgrupo(String subgrupo) {
		this.subgrupo = subgrupo;
	}


	public boolean getActivo() {
		return activo;
	}

	public Boolean isPagado() {
		return pagado;
	}

	public void setPagado(Boolean pagado) {
		this.pagado = pagado;
	}

	public LocalDateTime getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(LocalDateTime fechaPago) {
		this.fechaPago = fechaPago;
	}

	public LocalDateTime getUltimoUso() {
		return ultimoUso;
	}

	public void setUltimoUso(LocalDateTime ultimoUso) {
		this.ultimoUso = ultimoUso;
	}

	public LocalDateTime getFechaVigencia() {
		return fechaVigencia;
	}

	public void setFechaVigencia(LocalDateTime fechaVigencia) {
		this.fechaVigencia = fechaVigencia;
	}

	public void calcularVigenciaSiAplica() {
		if (concepto != null && concepto.toLowerCase().contains("plus") && fechaPago != null) {
			// Si el concepto incluye "plus", la vigencia es de 30 días a partir de la fecha de pago
			fechaVigencia = fechaPago.plusDays(30);
		} else if (concepto != null && (concepto.toLowerCase().contains("fisio") || concepto.toLowerCase().contains("studio") || concepto.toLowerCase().contains("hidro") || concepto.toLowerCase().contains("spa")) && fechaPago != null) {
			fechaVigencia = fechaPago.plusMonths(1) // Avanza dos meses
					.with(TemporalAdjusters.lastDayOfMonth()) // Ajusta al último día del mes
					.withHour(23) // Establece la hora a 23
					.withMinute(59) // Establece los minutos a 59
					.withSecond(59);

		} else if (f_compra != null) {
			// Para otros pases, establecer la vigencia a partir de la fecha de compra
			fechaVigencia = convertDateToLocalDateTime(f_compra)
					.plusMonths(1)
					.withHour(23)
					.withMinute(59)
					.withSecond(59);
		} else {
			fechaVigencia = null; // o alguna lógica alternativa
		}
	}

	public LocalDateTime convertDateToLocalDateTime(Date date) {
		return date.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDateTime();
	}


	@Override
	public String toString() {
		return "PaseUsuario{" +
				"idVentaDetalle=" + idVentaDetalle +
				", cliente=" + cliente +
				", concepto='" + concepto + '\'' +
				", idProd=" + idProd +
				", cantidad=" + cantidad +
				", disponibles=" + disponibles +
				", consumido=" + consumido +
				", f_compra=" + f_compra +
				", createdBy='" + createdBy + '\'' +
				", created=" + created +
				", updatedBy='" + updatedBy + '\'' +
				", updated=" + updated +
				", activo=" + activo +
				", subgrupo='" + subgrupo + '\'' +
				", pagado=" + pagado +
				", fechaPago=" + fechaPago +
				", ultimoUso=" + ultimoUso +
				", fechaVigencia=" + fechaVigencia +
				'}';
	}
}
