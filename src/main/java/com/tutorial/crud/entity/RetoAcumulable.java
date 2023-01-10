package com.tutorial.crud.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;
@Entity
@Table(name = "retos_acumulable")
public class RetoAcumulable {

    @Id //Define la llave primaria.
    @GeneratedValue(generator = "UUID") //Se estableció un ti   po de variable UUID
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "pasos")
    private int pasos;

    @Column(name = "calorias")
    private Double calorias;

    @Column(name = "distancia")
    private Double distancia;

    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="id_reto_usuario")
    private RetoUsuario retoUsuario;

    @Column(name = "is_summable")
    private Boolean isSummable;

    public UUID getId() { return id; }

    //public void setId(UUID id) { this.id = id; }

    public int getPasos() { return pasos; }

    public void setPasos(int pasos) { this.pasos = pasos; }

    public Double getCalorias() { return calorias; }

    public void setCalorias(Double calorias) { this.calorias = calorias; }

    public Double getDistancia() { return distancia; }

    public void setDistancia(Double distancia) { this.distancia = distancia; }

    public RetoUsuario getRetoUsuario() { return retoUsuario; }

    public void setRetoUsuario(RetoUsuario retoUsuario) { this.retoUsuario = retoUsuario; }

    public Boolean getSummable() { return isSummable; }

    public void setSummable(Boolean summable) { isSummable = summable; }

    @Override
    public String toString() {
        return "RetoAcumulable{" +
                "id=" + id +
                ", pasos=" + pasos +
                ", calorias=" + calorias +
                ", distancia=" + distancia +
                ", retoUsuario=" + retoUsuario +
                ", isSummable=" + isSummable +
                '}';
    }
}
