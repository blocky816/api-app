package com.tutorial.crud.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UsuarioOnesignal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "cliente_id")
    @ManyToOne
    private Cliente clienteId;

    @Column(name = "push_subscription_id")
    private String pushSubscriptionId;
    @Column(name = "push_subscription_token")
    private String pushSubscriptionToken;
    
    @Column(name = "onesignal_id")
    private String onesignalId;

    @Column(name = "suscrito")
    private Boolean suscrito;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;
    public UsuarioOnesignal() { }
    
    public UsuarioOnesignal(Integer externalId, UsuarioOnesignal usuario) {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(externalId);
        clienteId = cliente;
        pushSubscriptionId = usuario.getPushSubscriptionId();
        pushSubscriptionToken = usuario.getPushSubscriptionToken();
        onesignalId = usuario.getOnesignalId();
        suscrito = usuario.getSuscrito();

        fechaCreacion = LocalDateTime.now();
        fechaModificacion = LocalDateTime.now();
    }

    public LocalDateTime getFechaSuscrito() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getClienteId() {
        return clienteId;
    }

    public void setClienteId(Cliente clienteId) {
        this.clienteId = clienteId;
    }

    public String getPushSubscriptionId() {
        return pushSubscriptionId;
    }

    public void setPushSubscriptionId(String pushSubscriptionId) {
        this.pushSubscriptionId = pushSubscriptionId;
    }

    public String getPushSubscriptionToken() {
        return pushSubscriptionToken;
    }

    public void setPushSubscriptionToken(String pushSubscriptionToken) {
        this.pushSubscriptionToken = pushSubscriptionToken;
    }

    public Boolean getSuscrito() {
        return suscrito;
    }

    public void setSuscrito(Boolean suscrito) {
        this.suscrito = suscrito;
    }

    public String getOnesignalId() {
        return onesignalId;
    }

    public void setOnesignalId(String onesignalId) {
        this.onesignalId = onesignalId;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

}
