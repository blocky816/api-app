package com.tutorial.crud.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CitaAsesorRequest {
    @NotNull(message = "El ID del cliente no puede ser nulo.")
    private Integer idCliente;

    @NotNull(message = "La fecha no puede ser nula.")
    @Size(min = 10, max = 10, message = "La fecha debe tener el formato 'yyyy-MM-dd'.")
    private String fecha;

    @NotNull(message = "La hora no puede ser nula.")
    @Size(min = 5, max = 5, message = "La hora debe tener el formato 'HH:mm'.")
    private String hora;

    @NotNull(message = "El nombre del asesor no puede ser nulo.")
    @Size(min = 2, message = "El nombre del asesor debe tener al menos 2 caracteres.")
    private String nombreAsesor;

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getNombreAsesor() {
        return nombreAsesor;
    }

    public void setNombreAsesor(String nombreAsesor) {
        this.nombreAsesor = nombreAsesor;
    }

    @Override
    public String toString() {
        return "CitaAsesorRequest{" +
                "idCliente=" + idCliente +
                ", fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                ", nombreAsesor='" + nombreAsesor + '\'' +
                '}';
    }
}
