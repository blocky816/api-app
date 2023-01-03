package com.tutorial.crud.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="bascula_cliente")
public class BasculaCliente {

    @Id //Define la llave primaria.
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "uuid", updatable = false, nullable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private UUID id;

    @Column(name = "idcliente")
    private int idCliente;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "intentos")
    private int intentos = 0;

    public UUID getId() { return id; }

    public int getIdCliente() { return idCliente; }

    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public Boolean getActivo() { return activo; }

    public void setActivo(Boolean activo) { this.activo = activo; }

    public int getIntentos() { return intentos; }

    public void setIntentos(int intentos) { this.intentos = intentos; }

    @Override
    public String toString() {
        return "BasculaCliente{" +
                "id=" + id +
                ", idCliente=" + idCliente +
                ", activo=" + activo +
                ", intentos=" + intentos +
                '}';
    }
}
