package com.tutorial.crud.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity
@Table(name="rutinas_ejercicios_nuevo")
public class RutinaEjercicioNuevo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    //@JsonBackReference(value = "rutina-ejercicio")
    //@JsonIgnore
    @ManyToOne
    @JoinColumn(name="id_rutina")
    private RutinaNuevo rutinanuevo;


    //@JsonBackReference(value = "ejercicio-rutina")
    //@JsonIgnore
    @ManyToOne
    @JoinColumn(name="id_ejercicio")
    private EjercicioNuevo ejercicionuevo;

    @Column(name="dia")
    private int dia;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RutinaNuevo getRutina() {
        return rutinanuevo;
    }

    public void setRutina(RutinaNuevo rutinanuevo) {
        this.rutinanuevo = rutinanuevo;
    }

    public EjercicioNuevo getEjercicio() {
        return ejercicionuevo;
    }

    public void setEjercicio(EjercicioNuevo ejercicionuevo) {
        this.ejercicionuevo = ejercicionuevo;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    @Override
    public String toString() {
        return "RutinaEjercicioNuevo{" +
                "id=" + id +
                ", rutina=" + rutinanuevo +
                ", ejercicio=" + ejercicionuevo +
                ", dia=" + dia +
                '}';
    }
}
