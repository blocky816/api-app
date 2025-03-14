package com.tutorial.crud.Odoo.Spec.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tutorial.crud.entity.Formulario;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "preguntas")
public class Pregunta {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idPregunta;

    @ManyToOne
    @JoinColumn(name = "idFormulario")
    @JsonBackReference
    private Formulario formulario;

    private String descripcion;

    private String tipo;

    private Boolean activo;

    @OneToMany(mappedBy = "pregunta", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<RespuestaFormulario> respuestas;

    public Long getId() {
        return idPregunta;
    }

    public void setId(Long id) {
        this.idPregunta = id;
    }

    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<RespuestaFormulario> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<RespuestaFormulario> respuestas) {
        this.respuestas = respuestas;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Pregunta{" +
                "id=" + idPregunta+
                ", formulario=" + formulario +
                ", descripcion='" + descripcion + '\'' +
                ", tipo='" + tipo + '\'' +
                ", activo=" + activo +
                ", respuestas=" + respuestas +
                '}';
    }
}
