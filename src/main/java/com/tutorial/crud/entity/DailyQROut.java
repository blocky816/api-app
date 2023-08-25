package com.tutorial.crud.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "qr_corte")
public class DailyQROut {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_qr_corte")
    private long idQRCorte;
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP(0)")
    private LocalDateTime createdAt;
    @Column(name = "created_by", nullable = false)
    private int createdBy;
    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP(0)")
    private LocalDateTime updatedAt;
    @Column(name = "updated_by", nullable = false)
    private int updatedBy;
    @Column(name = "club")
    private String club;
    @Column(name = "total")
    private float total;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_qr_corte")
    @JsonManagedReference
    private List<QRParking> qrParkings;

    public long getIdQRCorte() {
        return idQRCorte;
    }

    public void setIdQRCorte(long idQRCorte) {
        this.idQRCorte = idQRCorte;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public List<QRParking> getQrParkings() {
        return qrParkings;
    }

    public void setQrParkings(List<QRParking> qrParkings) {
        this.qrParkings = qrParkings;
    }

    @Override
    public String toString() {
        return "DailyQROut{" +
                "idQRCorte=" + idQRCorte +
                ", createdAt=" + createdAt +
                ", createdBy='" + createdBy + '\'' +
                ", updatedAt=" + updatedAt +
                ", updatedBy='" + updatedBy + '\'' +
                ", club='" + club + '\'' +
                ", total=" + total +
                ", qrParkings=" + qrParkings +
                '}';
    }
}
