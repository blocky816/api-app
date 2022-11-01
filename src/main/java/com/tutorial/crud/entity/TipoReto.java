package com.tutorial.crud.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="tipo_retos")
public class TipoReto {

    @Id //Define la llave primaria.
    @GeneratedValue(generator = "UUID") //Se estableció un tipo de variable UUID
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private UUID id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    @Temporal(TemporalType.TIMESTAMP)
    private Date created = new Date();

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated=new Date();

    @Column(name = "is_activo")
    private boolean activo = true;

    @Column(name = "dificultad")
    private String dificultad;

    private Boolean time = false;

    private int max;

    public UUID getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }


    public void setCreated(Date created) {
        this.created = created;
    }


    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }


    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean isActivo() {
        return activo;
    }

    public String getDificultad() { return dificultad; }

    public void setDificultad(String dificultad) { this.dificultad = dificultad; }

    public Boolean getTime() { return time; }

    public void setTime(Boolean time) { this.time = time; }

    public int getMax() { return max; }

    public void setMax(int max) { this.max = max; }

    @Override
    public String toString() {
        return "TipoReto [id=" + id + ", nombre=" + nombre + ", createdBy=" + createdBy + ", created=" + created
                + ", updatedBy=" + updatedBy + ", updated=" + updated + ", activo=" + activo + ", dificultad="
                + dificultad + ", Time =" + time + ", Max Value =" + max +"]";
    }

}
