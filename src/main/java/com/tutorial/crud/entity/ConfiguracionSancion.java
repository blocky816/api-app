package com.tutorial.crud.entity;

import javax.persistence.*;

@Entity
@Table(name="configuracion_sancion")
public class ConfiguracionSancion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String concepto;
    private int codigo;

    private String endpoint;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getConcepto() { return concepto; }

    public void setConcepto(String concepto) { this.concepto = concepto; }

    public int getCodigo() { return codigo; }

    public void setCodigo(int codigo) { this.codigo = codigo; }

    public String getEndpoint() { return endpoint; }

    public void setEndpoint(String endpoint) { this.endpoint = endpoint; }

    @Override
    public String toString() {
        return "ConfiguracionSancion{" +
                "id=" + id +
                ", concepto='" + concepto + '\'' +
                ", codigo=" + codigo +
                ", endpoint='" + endpoint + '\'' +
                '}';
    }
}
