package com.tutorial.crud.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "retos_estadisticas")
public class RetoEstadistica {
    @Id //Define la llave primaria.
    @GeneratedValue(generator = "UUID") //Se estableció un tipo de variable UUID
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "frecuencia_cardiaca")
    private int frecuenciaCardiaca;

    @Column(name = "frecuencia_respiratoria")
    private int frecuenciaRespiratoria;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="id_reto_usuario")
    private RetoUsuario retoUsuario;

    @Column(name = "pasos")
    private int pasos;

    @Column(name = "calorias")
    private Double calorias;

    @Column(name = "distancia")
    private Double distancia;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public UUID getId() { return id; }

    /*public void setId(UUID id) { this.id = id; }*/

    public int getFrecuenciaCardiaca() { return frecuenciaCardiaca; }

    public void setFrecuenciaCardiaca(int frecuenciaCardiaca) { this.frecuenciaCardiaca = frecuenciaCardiaca; }

    public int getFrecuenciaRespiratoria() { return frecuenciaRespiratoria; }

    public void setFrecuenciaRespiratoria(int frecuenciaRespiratoria) { this.frecuenciaRespiratoria = frecuenciaRespiratoria; }

    public RetoUsuario getRetoUsuario() { return retoUsuario; }

    public void setRetoUsuario(RetoUsuario retoUsuario) { this.retoUsuario = retoUsuario; }

    public int getPasos() { return pasos; }

    public void setPasos(int pasos) { this.pasos = pasos; }

    public Double getCalorias() { return calorias; }

    public void setCalorias(Double calorias) { this.calorias = calorias; }

    public Double getDistancia() { return distancia; }

    public void setDistancia(Double distancia) { this.distancia = distancia; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "RetoEstadistica{" +
                "id=" + id +
                ", frecuenciaCardiaca=" + frecuenciaCardiaca +
                ", frecuenciaRespiratoria=" + frecuenciaRespiratoria +
                ", retoUsuario=" + retoUsuario +
                ", pasos=" + pasos +
                ", calorias=" + calorias +
                ", distancia=" + distancia +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
