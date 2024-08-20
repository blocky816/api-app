package com.tutorial.crud.dto;

import java.util.Date;

public class LicensePlateDTO {
    private Integer id;
    private long idChip;
    private Integer parking;
    private Boolean activo;
    private Date fechaFin;
    private String club;
    private Integer idCliente;
    private String placa;

    public LicensePlateDTO(Integer id, Long idChip, Integer parking, Boolean activo, Date fechaFin, String club, Integer idCliente, String placa) {
        this.id = id != null ? id : null;
        this.idChip = idChip != null ? idChip : null;
        this.parking = parking != null ? parking : null;
        this.activo = activo != null ? activo : null;
        this.fechaFin = fechaFin != null ? fechaFin : null;
        this.club = club != null ? club : null;
        this.idCliente = idCliente != null ? idCliente : null;
        this.placa = placa != null ? placa : "";
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

    public int getIdCliente() { return this.idCliente; }

    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getPlaca() { return placa.toUpperCase(); }

    public void setPlaca(String placa) { this.placa = placa; }
}
