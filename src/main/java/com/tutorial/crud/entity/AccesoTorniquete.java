package com.tutorial.crud.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "accesos_torniquete")
public class AccesoTorniquete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "torniquete_id", nullable = false)
    private Torniquete torniquete;
    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false) // Cambia `usuario_id` a lo que sea relevante
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "tipo_acceso", nullable = false)
    private TipoAcceso tipoAcceso;
    @Column(name = "fecha_acceso", columnDefinition = "TIMESTAMP(0)")
    private LocalDateTime fechaAcceso;
    private String sentido; // "entrada" o "salida"
    private String estado; // "autorizado", "denegado"

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Torniquete getTorniquete() {
        return torniquete;
    }

    public void setTorniquete(Torniquete torniquete) {
        this.torniquete = torniquete;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public TipoAcceso getTipoAcceso() {
        return tipoAcceso;
    }

    public void setTipoAcceso(TipoAcceso tipoAcceso) {
        this.tipoAcceso = tipoAcceso;
    }

    public LocalDateTime getFechaAcceso() {
        return fechaAcceso;
    }

    public void setFechaAcceso(LocalDateTime fechaAcceso) {
        this.fechaAcceso = fechaAcceso;
    }

    public String getSentido() {
        return sentido;
    }

    public void setSentido(String sentido) {
        this.sentido = sentido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @PrePersist
    public void prePersist() {
        this.fechaAcceso = LocalDateTime.now().minusHours(1);
    }

    @Override
    public String toString() {
        return "AccesoTorniquete{" +
                "id=" + id +
                ", torniquete=" + torniquete +
                ", cliente=" + cliente +
                ", tipoAcceso=" + tipoAcceso +
                ", fechaAccceso=" + fechaAcceso +
                ", sentido='" + sentido + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
