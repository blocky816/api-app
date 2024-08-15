package com.tutorial.crud.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "monthly_passes")
public class MonthlyPass {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP(0)")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP(0)")
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "monthly_class_id")
    private MonthlyClass monthlyClass;
    @Column(name = "quantity_available")
    private int quantityAvailable;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Cliente customer;
    @Column(name = "pass_active")
    private boolean passActive = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public MonthlyClass getMonthlyClass() {
        return monthlyClass;
    }

    public void setMonthlyClass(MonthlyClass monthlyClass) {
        this.monthlyClass = monthlyClass;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public Cliente getCustomer() {
        return customer;
    }

    public void setCustomer(Cliente customer) {
        this.customer = customer;
    }

    public boolean isPassActive() {
        return passActive;
    }

    public void setPassActive(boolean passActive) {
        this.passActive = passActive;
    }

    @Override
    public String toString() {
        return "MonthlyPass{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", monthlyClass=" + monthlyClass +
                ", quantityAvailable=" + quantityAvailable +
                ", customer=" + customer +
                ", passActive=" + passActive +
                '}';
    }
}
