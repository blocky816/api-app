package com.tutorial.crud.dto;

import com.tutorial.crud.entity.MonthlyClass;

import javax.sql.rowset.spi.SyncResolver;
import java.time.LocalDateTime;
import java.util.List;

public class MonthlyPassDTO {
    private String nombre;
    private String descripcion;
    private int uso_maximo;
    private int id;
    private String codigo;
    private LocalDateTime inicio;
    private LocalDateTime fin;
    private List<String> tipos_membresia;
    private boolean pases_exclusivos;
    private List<MonthlyClassDTO> pases_incluidos;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getUso_maximo() {
        return uso_maximo;
    }

    public void setUso_maximo(int uso_maximo) {
        this.uso_maximo = uso_maximo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public LocalDateTime getFin() {
        return fin;
    }

    public void setFin(LocalDateTime fin) {
        this.fin = fin;
    }

    public List<String> getTipos_membresia() {
        return tipos_membresia;
    }

    public void setTipos_membresia(List<String> tipos_membresia) {
        this.tipos_membresia = tipos_membresia;
    }

    public boolean isPases_exclusivos() {
        return pases_exclusivos;
    }

    public void setPases_exclusivos(boolean pases_exclusivos) {
        this.pases_exclusivos = pases_exclusivos;
    }

    public List<MonthlyClassDTO> getPases_incluidos() {
        return pases_incluidos;
    }

    public void setPases_incluidos(List<MonthlyClassDTO> pases_incluidos) {
        this.pases_incluidos = pases_incluidos;
    }
}
