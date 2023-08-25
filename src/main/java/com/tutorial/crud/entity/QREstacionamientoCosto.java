package com.tutorial.crud.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "qr_estacionamiento_costo")
public class QREstacionamientoCosto {
    @Id
    @Column(name="id_usuario")
    private int userID;

    @Column(name = "nombre")
    private String name;

    @Column(name = "costo")
    private float cost;

    public int getUserID() { return userID; }

    public void setUserID(int userID) { this.userID = userID; }

    public float getCost() { return cost; }

    public void setCost(float cost) { this.cost = cost; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "QREstacionamientoCosto{" +
                "userID=" + userID +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                '}';
    }
}
