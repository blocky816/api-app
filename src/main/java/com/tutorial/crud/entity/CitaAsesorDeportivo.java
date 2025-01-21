package com.tutorial.crud.entity;

import com.tutorial.crud.enums.EstadoCita;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "citas_asesor_deportivo")
public class CitaAsesorDeportivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer idCliente;

    @Column(nullable = false)
    private LocalDateTime fechaHora;

    private String nombreAsesor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCita estado;

    @Column(nullable = false)
    private boolean correoEnviado;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // Constructor vacío para JPA
    public CitaAsesorDeportivo() {}

    // Constructor con parámetros
    public CitaAsesorDeportivo(Integer idCliente, LocalDateTime fechaHora, String nombreAsesor, EstadoCita estado, boolean correoEnviado) {
        this.idCliente = idCliente;
        this.fechaHora = fechaHora;
        this.nombreAsesor = nombreAsesor;
        this.estado = estado;
        this.correoEnviado = correoEnviado;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getNombreAsesor() {
        return nombreAsesor;
    }

    public void setNombreAsesor(String nombreAsesor) {
        this.nombreAsesor = nombreAsesor;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }

    public boolean isCorreoEnviado() {
        return correoEnviado;
    }

    public void setCorreoEnviado(boolean correoEnviado) {
        this.correoEnviado = correoEnviado;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "CitaAsesorDeportivo{" +
                "id=" + id +
                ", idCliente=" + idCliente +
                ", fechaHora=" + fechaHora +
                ", nombreAsesor='" + nombreAsesor + '\'' +
                ", estado=" + estado +
                ", correoEnviado=" + correoEnviado +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
