package com.tutorial.crud.dto;

import com.tutorial.crud.entity.ParkingUsuario;

import java.time.LocalDateTime;
import java.util.Date;

public class RegistroTagDTO {
    private int id;
    private long idChip;
    private int parking;
    private boolean activo;
    private Date fechaFin;
    private String club;

    public RegistroTagDTO(int id, long idChip, int parking, boolean activo, Date fechaFin, String club) {
        this.id = id;
        this.idChip = idChip;
        this.parking = parking;
        this.activo = activo;
        this.fechaFin = fechaFin;
        this.club = club;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdChip() {
        return Long.toHexString(idChip);
    }

    public void setIdChip(long idChip) {
        this.idChip = idChip;
    }

    public int getParking() {
        return parking;
    }

    public void setParking(int parking) {
        this.parking = parking;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }
}
