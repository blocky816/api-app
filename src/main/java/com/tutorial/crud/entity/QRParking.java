package com.tutorial.crud.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "qr_parking", uniqueConstraints = { @UniqueConstraint(columnNames = { "id_registro", "club" }) })
public class QRParking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP(0)")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP(0)")
    private LocalDateTime updatedAt;
    @Column(name = "id_registro")
    private String idRegistro;
    @Column(name = "club")
    private String club;
    @Column(name = "id_usuario")
    private int idUsuario;
    @Column(name = "costo")
    private float costo;
    @Column(name = "debito")
    private float debito;
    @Column(name = "pagado", columnDefinition = "boolean default false")
    private boolean pagado;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_qr_corte", referencedColumnName = "id_qr_corte")
    @JsonBackReference
    private DailyQROut dailyQROut;
    @Column(name = "cambio")
    private float cambio;
    @Column(name = "devuelto", columnDefinition = "boolean default false")
    private boolean devuelto;
    @Column(name = "observaciones", columnDefinition = "varchar(350)")
    private String observaciones;
    @Column(name = "cambio_final")
    private float cambioFinal;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(String idRegistro) {
        this.idRegistro = idRegistro;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public float getDebito() { return debito; }

    public void setDebito(float debito) { this.debito = debito; }

    public boolean isPagado() { return pagado; }

    public void setPagado(boolean pagado) { this.pagado = pagado; }

    public DailyQROut getDailyQROut() {
        return dailyQROut;
    }

    public void setDailyQROut(DailyQROut dailyQROut) {
        this.dailyQROut = dailyQROut;
    }

    public float getCambio() { return cambio; }

    public void setCambio(float cambio) { this.cambio = cambio; }

    public boolean isDevuelto() { return devuelto; }

    public void setDevuelto(boolean devuelto) { this.devuelto = devuelto; }

    public String getObservaciones() { return observaciones; }

    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public float getCambioFinal() { return cambioFinal; }

    public void setCambioFinal(float cambioFinal) { this.cambioFinal = cambioFinal; }

    @Override
    public String toString() {
        return "QRParking{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", idRegistro='" + idRegistro + '\'' +
                ", club='" + club + '\'' +
                ", idUsuario=" + idUsuario +
                ", costo=" + costo +
                ", debito=" + debito +
                ", pagado=" + pagado +
                ", dailyQROut=" + dailyQROut +
                ", cambio=" + cambio +
                ", devuelto=" + devuelto +
                ", observaciones='" + observaciones + '\'' +
                ", cambioFinal=" + cambioFinal +
                '}';
    }
}
