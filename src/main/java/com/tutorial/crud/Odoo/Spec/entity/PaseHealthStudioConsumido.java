package com.tutorial.crud.Odoo.Spec.entity;

import com.tutorial.crud.entity.PaseUsuario;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="pase_health_studio_consumido")
public class PaseHealthStudioConsumido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConsumo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pase_usuario")
    private PaseUsuario paseUsuario;

    @Column(name = "consumido_por")
    private String consumidoPor;

    @Column(name = "fecha_consumo")
    private LocalDateTime fechaConsumo;

    public Long getIdConsumo() {
        return idConsumo;
    }

    public void setIdConsumo(Long idConsumo) {
        this.idConsumo = idConsumo;
    }

    public PaseUsuario getPaseUsuario() {
        return paseUsuario;
    }

    public void setPaseUsuario(PaseUsuario paseUsuario) {
        this.paseUsuario = paseUsuario;
    }

    public String getConsumidoPor() {
        return consumidoPor;
    }

    public void setConsumidoPor(String consumidoPor) {
        this.consumidoPor = consumidoPor;
    }

    public LocalDateTime getFechaConsumo() {
        return fechaConsumo;
    }

    public void setFechaConsumo(LocalDateTime fechaConsumo) {
        this.fechaConsumo = fechaConsumo;
    }

}
