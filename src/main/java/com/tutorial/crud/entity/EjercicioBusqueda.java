package com.tutorial.crud.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="ejercicios_busqueda")
public class EjercicioBusqueda {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_ejercicio")
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "activo")
    private String activo = "true";

    @Column(name = "url")
    private String url;

    @Column(name = "nivel")
    private String nivel;

    @Column(name = "tipo")
    private String tipo;

    public int getId() { return id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getActivo() { return activo; }

    public void setActivo(String activo) { this.activo = activo; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    public String getNivel() { return nivel; }

    public void setNivel(String nivel) { this.nivel = nivel; }

    public String getTipo() { return tipo; }

    public void setTipo(String tipo) { this.tipo = tipo; }

    @Override
    public String toString() {
        return "EjercicioBusqueda{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", activo='" + activo + '\'' +
                ", url='" + url + '\'' +
                ", nivel='" + nivel + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
