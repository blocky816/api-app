package com.tutorial.crud.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tutorial.crud.Odoo.Spec.entity.Pregunta;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="formularios")
public class Formulario {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "nivel")
    private String nivel;

    @Column(name = "pregunta")
    private String pregunta;

    @Column(name = "respuesta")
    private String respuesta;

    @Column(name = "activo")
    private Boolean activo = true;

    @Column(name = "folio")
    private int folio;

    @OneToMany(mappedBy = "formulario", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Pregunta> preguntas;

    @Column(name = "created")
    private LocalDateTime created = LocalDateTime.now().withNano(0);

    public int getId() { return id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }

    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getNivel() { return nivel; }

    public void setNivel(String nivel) { this.nivel = nivel; }

    public String getPregunta() { return pregunta; }

    public void setPregunta(String pregunta) { this.pregunta = pregunta; }

    public String getRespuesta() { return respuesta; }

    public void setRespuesta(String respuesta) { this.respuesta = respuesta; }

    public Boolean getActivo() { return activo; }

    public void setActivo(Boolean activo) { this.activo = activo; }

    public int getFolio() { return folio; }

    public void setFolio(int folio) { this.folio = folio; }

    public LocalDateTime getCreated() { return created; }

    public void setCreated(LocalDateTime created) { this.created = created; }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

    @Override
    public String toString() {
        return "Formulario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", tipo='" + tipo + '\'' +
                ", nivel='" + nivel + '\'' +
                ", pregunta='" + pregunta + '\'' +
                ", respuesta='" + respuesta + '\'' +
                ", activo=" + activo +
                ", folio=" + folio +
                ", created=" + created +
                '}';
    }
}
