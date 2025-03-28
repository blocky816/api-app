package com.tutorial.crud.Odoo.Spec.dto;

import java.util.List;

public class PaginacionDTO {
    private List<PaseReporteDTO> registros;
    private int totalPages;
    private long totalRecords;

    // Constructor
    public PaginacionDTO(List<PaseReporteDTO> registros, int totalPages, long totalRecords) {
        this.registros = registros;
        this.totalPages = totalPages;
        this.totalRecords = totalRecords;
    }

    // Getters y setters
    public List<PaseReporteDTO> getRegistros() {
        return registros;
    }

    public void setRegistros(List<PaseReporteDTO> registros) {
        this.registros = registros;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }
}
