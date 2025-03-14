package com.tutorial.crud.Odoo.Spec.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tutorial.crud.entity.Cliente;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "respuestas_formularios")
public class RespuestaFormulario {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idCliente")
    @JsonIgnoreProperties({"noMembresia", "nombre", "apellidoPaterno", "apellidoMaterno", "nombreCompleto", "servicio", "estatusAcceso", "tipoAcceso", "urlfoto", "domicilioPago", "inicioActividades", "sexo",
    "fechaNacimiento", "mensualidadPagada", "email", "fechaFinAcceso", "idSexo", "nacionalidad", "telefono", "idClienteGrupo", "idClienteSector", "idCaptura", "idCapturaFecha", "activo", "fechaCreacion",
    "fechaModificacion", "tieneAcceso", "token", "domiciliado", "cargoDomiciliacion", "monto", "club", "tipoCliente", "categoria", "estatusCliente", "estatusMembresia", "estatusCobranza", "mensajes",
    "tipoMembresia", "horarioOtroClub", "apartadosUsuario", "agendaUsuario", "rutina", "diaInicioRutina", "semanas", "rutinanuevo", "diaInicioRutinanuevo", "semanasnuevo", "idComplexBand", "formulario",
    "esTitular", "idTitular", "answerChatGPTS", "idOdoo", "ultimoUso", "profesionalizacion", "consentimiento"})
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "idPregunta")
    @JsonBackReference
    private Pregunta pregunta;

    private String respuesta;

    private LocalDateTime fechaRespuesta;

    private Boolean activo;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public LocalDateTime getFechaRespuesta() {
        return fechaRespuesta;
    }

    public void setFechaRespuesta(LocalDateTime fechaRespuesta) {
        this.fechaRespuesta = fechaRespuesta;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "RespuestaFormulario{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", pregunta=" + pregunta +
                ", respuesta='" + respuesta + '\'' +
                ", fechaRespuesta=" + fechaRespuesta +
                ", activo=" + activo +
                '}';
    }
}
