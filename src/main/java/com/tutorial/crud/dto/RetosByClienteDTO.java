package com.tutorial.crud.dto;

import java.util.Date;
import java.util.UUID;

public class RetosByClienteDTO {
    private UUID id;
    private String nombre;
    private String descripcion;
    private TipoRetoDTO tipoReto;
    private int noInscritos;
    private int cupoMaximo;
    private Date fechaInicio;
    private Date fechaFin;
    private Boolean isInscrito = false;
    private String dispositivo;
    private int club;
    private String banner;
    private String icono;
    private int max;

    public RetosByClienteDTO(UUID id, String nombre, String descripcion, TipoRetoDTO tipoReto, int noInscritos,
                             int cupoMaximo, Date fechaInicio, Date fechaFin, Boolean isInscrito, String dispositivo,
                             int club, String banner, String icono, int max) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipoReto = tipoReto;
        this.noInscritos = noInscritos;
        this.cupoMaximo = cupoMaximo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.isInscrito = isInscrito;
        this.dispositivo = dispositivo;
        this.club = club;
        this.banner = banner;
        this.icono = icono;
        this.max = max;
    }

    public UUID getId() { return id; }

    public void setId(UUID id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public TipoRetoDTO getTipoReto() { return tipoReto; }

    public void setTipoReto(TipoRetoDTO tipoReto) { this.tipoReto = tipoReto; }

    public int getNoInscritos() { return noInscritos; }

    public void setNoInscritos(int noInscritos) { this.noInscritos = noInscritos; }

    public int getCupoMaximo() { return cupoMaximo; }

    public void setCupoMaximo(int cupoMaximo) { this.cupoMaximo = cupoMaximo; }

    public Date getFechaInicio() { return fechaInicio; }

    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

    public Date getFechaFin() { return fechaFin; }

    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }

    public Boolean getInscrito() { return isInscrito; }

    public void setInscrito(Boolean inscrito) { isInscrito = inscrito; }

    public String getDispositivo() { return dispositivo; }

    public void setDispositivo(String dispositivo) { this.dispositivo = dispositivo; }

    public int getClub() { return club; }

    public void setClub(int club) { this.club = club; }

    public String getBanner() { return banner; }

    public void setBanner(String banner) { this.banner = banner; }

    public String getIcono() { return icono; }

    public void setIcono(String icono) { this.icono = icono; }

    public int getMax() { return max; }

    public void setMax(int max) { this.max = max; }
}
