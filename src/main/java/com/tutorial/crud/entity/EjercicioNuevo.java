package com.tutorial.crud.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="ejercicios_nuevo")
public class EjercicioNuevo {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_ejercicio")
    private int id;

    @Column(name = "nombre")
    private String nombre;
    @Column(name = "dia")
    private int dia;

    @Column(name = "club")
    private int club;

    @Column(name = "nivel")
    private String nivel;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "general")
    private Boolean general;

    @Column(name = "imagen", columnDefinition = "text")
    private String imagen;

    @Column(name = "activo")
    private Boolean activo;

    //@JsonManagedReference(value = "ejercicio-rutina")
    //@JsonIgnore
    @OneToMany(mappedBy="ejercicionuevo")
    List<RutinaEjercicioNuevo> ejercicios;

    public int getId() { return id; }

    public int getDia() { return dia; }

    public void setDia(int dia) { this.dia = dia; }

    public int getClub() { return club; }

    public void setClub(int club) { this.club = club; }

    public String getNivel() { return nivel; }

    public void setNivel(String nivel) { this.nivel = nivel; }

    public String getTipo() { return tipo; }

    public void setTipo(String tipo) { this.tipo = tipo; }

    public Boolean getGeneral() { return general; }

    public void setGeneral(Boolean general) { this.general = general; }

    public String getImagen() { return imagen; }

    public void setImagen(String imagen) { this.imagen = imagen; }

    public Boolean getActivo() { return activo; }

    public void setActivo(Boolean activo) { this.activo = activo; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<RutinaEjercicioNuevo> getEjercicios() { return ejercicios; }

    public void setEjercicios(List<RutinaEjercicioNuevo> ejercicios) { this.ejercicios = ejercicios; }

    @Override
    public String toString() {
        return "EjercicioNuevo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", dia=" + dia +
                ", club=" + club +
                ", nivel='" + nivel + '\'' +
                ", tipo='" + tipo + '\'' +
                ", general=" + general +
                ", imagen='" + imagen + '\'' +
                ", activo=" + activo +
                '}';
    }
}
