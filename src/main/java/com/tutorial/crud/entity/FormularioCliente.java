package com.tutorial.crud.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="formularios_clientes")
public class FormularioCliente {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "folio")
    private int folio;

    @ManyToOne
    @JoinColumn(name="idcliente")
    private Cliente cliente;

    @Column(name = "activo")
    private Boolean activo = true;

    @Column(name = "formulario_date")
    private LocalDateTime formularioDate;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getFolio() { return folio; }

    public void setFolio(int folio) { this.folio = folio; }

    public Cliente getCliente() { return cliente; }

    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Boolean getActivo() { return activo; }

    public void setActivo(Boolean activo) { this.activo = activo; }

    public LocalDateTime getFormularioDate() { return formularioDate; }

    public void setFormularioDate(LocalDateTime formularioDate) { this.formularioDate = formularioDate; }

    @Override
    public String toString() {
        return "FormularioCliente{" +
                "id=" + id +
                ", folio=" + folio +
                ", cliente=" + cliente +
                ", activo=" + activo +
                ", created=" + formularioDate +
                '}';
    }
}
