package com.tutorial.crud.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="rutinas_nuevo")
public class RutinaNuevo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="nombre")
    private String nombreRutina;

    @Column(name="objetivo")
    private String nombreObjetivo;

    @Column(name="semanas")
    private int semanas;

    @Column(name="tipo")
    private String tipoPlantilla;

    @Column(name="comentarios")
    private String comentarios;

    @Column(name="created")
    private LocalDateTime created;

    @Column(name="updated")
    private LocalDateTime updated;

    @Column(name="activo")
    private Boolean activo;

    //@JsonBackReference(value = "rutinanuevo-cliente")
    //@JsonIgnore
    @OneToMany(mappedBy="rutinanuevo")
    private List<Cliente> cliente;


    //@JsonManagedReference(value = "rutina-ejercicio")
    //@JsonIgnore
    @OneToMany(mappedBy="rutinanuevo",cascade=CascadeType.ALL)
    private List<RutinaEjercicioNuevo> ejercicios;

    public int getId() { return id; }

    public String getNombreRutina() { return nombreRutina; }

    public void setNombreRutina(String nombreRutina) { this.nombreRutina = nombreRutina; }

    public String getNombreObjetivo() { return nombreObjetivo; }

    public void setNombreObjetivo(String nombreObjetivo) { this.nombreObjetivo = nombreObjetivo; }

    public int getSemanas() { return semanas; }

    public void setSemanas(int semanas) { this.semanas = semanas; }

    public String getTipoPlantilla() { return tipoPlantilla; }

    public void setTipoPlantilla(String tipoPlantilla) { this.tipoPlantilla = tipoPlantilla; }

    public String getComentarios() { return comentarios; }

    public void setComentarios(String comentarios) { this.comentarios = comentarios; }

    public LocalDateTime getCreated() { return created; }

    public void setCreated(LocalDateTime created) { this.created = created; }

    public LocalDateTime getUpdated() { return updated; }

    public void setUpdated(LocalDateTime updated) { this.updated = updated; }

    public Boolean getActivo() { return activo; }

    public void setActivo(Boolean activo) { this.activo = activo; }

    public List<Cliente> getCliente() { return cliente; }

    //public void setCliente(List<Cliente> cliente) { this.cliente = cliente; }

    public List<RutinaEjercicioNuevo> getEjercicios() { return ejercicios; }

    public void setEjercicios(List<RutinaEjercicioNuevo> ejercicios) { this.ejercicios = ejercicios; }

    //método para obtener el número de clientes asignados a la rutina
    //public int clientesAsignados() { return cliente.size();}

    //agregar un nuevo cliente a rutina
    //public void setCliente(Cliente cliente) { this.cliente.add(cliente) ; }
    public void setCliente(Cliente cliente) {
        this.cliente.add(cliente) ;
    }

    //inicializar el array de clientes al crear una nueva rutina
    //public Rutina() { cliente=new ArrayList<Cliente>(); }

    public List<Cliente> obtenerClientes() {
        return cliente;
    }
    public void colocarCliente(List<Cliente> cliente) {
        this.cliente = cliente;
    }

    public int getIdCliente(int idCliente) {
        if(cliente.size()==1)
            return cliente.get(0).getIdCliente();
        else
            return idCliente;
    }

    @Override
    public String toString() {
        return "RutinaNuevo{" +
                "id=" + id +
                ", nombreRutina='" + nombreRutina + '\'' +
                ", nombreObjetivo='" + nombreObjetivo + '\'' +
                ", semanas=" + semanas +
                ", tipoPlantilla='" + tipoPlantilla + '\'' +
                ", comentarios='" + comentarios + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", activo=" + activo +
                ", cliente=" + cliente +
                ", ejercicios=" + ejercicios +
                '}';
    }
}
