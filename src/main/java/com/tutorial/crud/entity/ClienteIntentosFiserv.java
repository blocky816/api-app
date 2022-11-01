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

    @Override
    public String toString() {
        return "ClienteIntentosFiserv{" +
                "idCliente=" + idCliente +
                ", nombre='" + nombre + '\'' +
                ", membresia='" + membresia + '\'' +
                ", monto=" + monto +
                ", fecha=" + fecha +
                ", estado='" + estado + '\'' +
                '}';
    }
}
