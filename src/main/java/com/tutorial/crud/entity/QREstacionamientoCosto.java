package com.tutorial.crud.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

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

    @Column(name = "club")
    private String club;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at", columnDefinition = "TIMESTAMP(0)")
    private LocalDateTime createdAt;
    @Column(name = "created_by")
    private int createdBy;
    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP(0)")
    private LocalDateTime updatedAt;
    @Column(name = "updated_by")
    private int updatedBy;

    public int getUserID() { return userID; }

    public void setUserID(int userID) { this.userID = userID; }

    public float getCost() { return cost; }

    public void setCost(float cost) { this.cost = cost; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public int getCreatedBy() { return createdBy; }

    public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public int getUpdatedBy() { return updatedBy; }

    public void setUpdatedBy(int updatedBy) { this.updatedBy = updatedBy; }

    public String getClub() { return club; }

    public void setClub(String club) { this.club = club; }

    @Override
    public String toString() {
        return "QREstacionamientoCosto{" +
                "userID=" + userID +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", club='" + club + '\'' +
                ", createdAt=" + createdAt +
                ", createdBy=" + createdBy +
                ", updatedAt=" + updatedAt +
                ", updatedBy=" + updatedBy +
                '}';
    }
}
