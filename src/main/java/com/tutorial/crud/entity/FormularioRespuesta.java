package com.tutorial.crud.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="formularios_respuestas")
public class FormularioRespuesta {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "folio")
    private int folio;

    @ManyToOne
    @JoinColumn(name="idcliente")
    private Cliente cliente;

    @Column(name = "pregunta")
    private String pregunta;

    @Column(name="respuesta")
    private String respuesta;

    @Column(name = "created")
    private LocalDateTime created = LocalDateTime.now().withNano(0);

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getFolio() { return folio; }

    public void setFolio(int folio) { this.folio = folio; }

    public Cliente getCliente() { return cliente; }

    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public String getPregunta() { return pregunta; }

    public void setPregunta(String pregunta) { this.pregunta = pregunta; }

    public String getRespuesta() { return respuesta; }

    public void setRespuesta(String respuesta) { this.respuesta = respuesta; }

    public LocalDateTime getCreated() { return created; }

    public void setCreated(LocalDateTime created) { this.created = created; }

    @Override
    public String toString() {
        return "FormularioRespuesta{" +
                "id=" + id +
                ", folio=" + folio +
                ", cliente=" + cliente +
                ", pregunta='" + pregunta + '\'' +
                ", respuesta='" + respuesta + '\'' +
                ", created=" + created +
                '}';
    }
}
