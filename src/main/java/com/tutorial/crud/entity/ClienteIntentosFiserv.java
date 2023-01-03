package com.tutorial.crud.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="cliente_intentos_fiserv")
public class ClienteIntentosFiserv {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name = "folio")
    private int folio;
    @Column(name="id_cliente")
    private int idCliente;
    private String nombre;
    private Long membresia;
    private float monto;
    private LocalDate fecha;
    //@Column(name="estado", columnDefinition="text")
    private String estado;

    @Column(name = "codigo_aprobacion")
    private String codigoAprobacion;

    @Column(name = "id_transaccion")
    private String transactionId;

    @Column(name = "card")
    private String card;

    @Column(name = "brand")
    private String brand;

    public int getId() {
        return id;
    }

    public int getFolio() {
        return folio;
    }

    public void setFolio(int folio) {
        this.folio = folio;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getMembresia() {
        return membresia;
    }

    public void setMembresia(Long membresia) {
        this.membresia = membresia;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodigoAprobacion() { return codigoAprobacion; }

    public void setCodigoAprobacion(String codigoAprobacion) { this.codigoAprobacion = codigoAprobacion; }

    public String getTransactionId() { return transactionId; }

    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public String getCard() { return card; }

    public void setCard(String card) { this.card = card; }

    public String getBrand() { return brand; }

    public void setBrand(String brand) { this.brand = brand; }

    @Override
    public String toString() {
        return "ClienteIntentosFiserv{" +
                "id=" + id +
                ", folio=" + folio +
                ", idCliente=" + idCliente +
                ", nombre='" + nombre + '\'' +
                ", membresia=" + membresia +
                ", monto=" + monto +
                ", fecha=" + fecha +
                ", estado='" + estado + '\'' +
                ", codigoAprobacion='" + codigoAprobacion + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", card='" + card + '\'' +
                ", brand='" + brand + '\'' +
                '}';
    }
}
