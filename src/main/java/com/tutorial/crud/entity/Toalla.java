package com.tutorial.crud.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="toallas")
public class Toalla {
    
    @Id //Define la llave primaria.
    @GeneratedValue(generator = "UUID") //Se estableció un tipo de variable UUID 
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private UUID id;
	
	@Column(name = "idcliente")
	private int idCliente;

    @Column(name = "cliente")
    private String cliente;
	
	@Column(name = "club")
	private String club;

    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.TIMESTAMP)
	private Date fechaInicio;

    @Column(name = "fecha_fin")
    @Temporal(TemporalType.TIMESTAMP)
	private Date fechaFin;

    @Column(name = "tiempo_total")
    @Temporal(TemporalType.TIME)
    private Date tiempoTotal;

    @Column(name = "toalla_ch")
	private int toallaCH;

    @Column(name = "toalla_g")
	private int toallaG;

    @Column(name = "asignacion")
	private Boolean asignacion;

    @Column(name = "empleado")
	private String empleado;

    @Column(name = "sancion")
    private String sancion;

    /*@Column(name = "usuario")
    private String usuario;*/

    public UUID getId() { return id; }

    public int getIdCliente() { return idCliente; }

    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getClub() { return club; }

    public void setClub(String club) { this.club = club; }

    public Date getFechaInicio() { return fechaInicio; }

    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

    public Date getFechaFin() { return fechaFin; }

    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }

    public int getToallaCH() { return toallaCH; }

    public void setToallaCH(int toallaCH) { this.toallaCH = toallaCH; }

    public int getToallaG() { return toallaG; }

    public void setToallaG(int toallaG) { this.toallaG = toallaG; }

    public Boolean getAsignacion() { return asignacion; }

    public void setAsignacion(Boolean asignacion) { this.asignacion = asignacion; }

    public String getEmpleado() { return empleado; }

    public void setEmpleado(String empleado) { this.empleado = empleado; }

    public String getSancion() { return sancion; }

    public void setSancion(String sancion) { this.sancion = sancion; }

    public String getCliente() { return cliente; }

    public void setCliente(String cliente) { this.cliente = cliente; }

    public Date getTiempoTotal() { return tiempoTotal; }

    public void setTiempoTotal(Date tiempoTotal) { this.tiempoTotal = tiempoTotal; }

    /*public String getUsuario() { return usuario; }

    public void setUsuario(String usuario) { this.usuario = usuario; }*/

    @Override
    public String toString() {
        return "Toalla{" +
                "id=" + id +
                ", idCliente=" + idCliente +
                ", cliente='" + cliente + '\'' +
                ", club='" + club + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", tiempoTotal=" + tiempoTotal +
                ", toallaCH=" + toallaCH +
                ", toallaG=" + toallaG +
                ", asignacion=" + asignacion +
                ", empleado='" + empleado + '\'' +
                ", sancion='" + sancion + '\'' +
                '}';
    }
}
