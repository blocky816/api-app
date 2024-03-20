package com.tutorial.crud.entity;

import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="conversaciones_asesor")
public class ConversacionAsesor {

    @Id //Define la llave primaria.
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id_conversacion", updatable = false, nullable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private UUID id;

    @Column(name = "idcliente")
    private int idCliente;

    @Column(name = "cliente")
    private String cliente;
    @Column(name = "mensaje", columnDefinition = "text")
    private String mensaje;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "bascula")
    private Boolean bascula;

    @Column(name = "visto", insertable = false)
    private Boolean visto;

    @Column(name = "folio")
    private int folio;

    @Column(name = "asesor")
    private String asesor;

    public UUID getId() { return id; }

    public int getIdCliente() { return idCliente; }

    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getCliente() { return cliente; }

    public void setCliente(String cliente) { this.cliente = cliente; }

    public String getMensaje() { return mensaje; }

    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public String getTipo() { return tipo; }

    public void setTipo(String tipo) { this.tipo = tipo; }

    public LocalDateTime getFecha() { return fecha; }

    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public Boolean getActivo() { return activo; }

    public void setActivo(Boolean activo) { this.activo = activo; }

    public Boolean getBascula() { return bascula; }

    public void setBascula(Boolean bascula) { this.bascula = bascula; }

    public Boolean getVisto() { return visto; }

    public void setVisto(Boolean visto) { this.visto = visto; }

    public int getFolio() { return folio; }

    public void setFolio(int folio) { this.folio = folio; }

    public String getAsesor() { return asesor; }

    public void setAsesor(String asesor) { this.asesor = asesor; }

    @Override
    public String toString() {
        return "ConversacionAsesor{" +
                "id=" + id +
                ", idCliente=" + idCliente +
                ", cliente='" + cliente + '\'' +
                ", mensaje='" + mensaje + '\'' +
                ", tipo='" + tipo + '\'' +
                ", fecha=" + fecha +
                ", activo=" + activo +
                ", bascula=" + bascula +
                ", visto=" + visto +
                ", folio=" + folio +
                ", asesor='" + asesor + '\'' +
                '}';
    }
}