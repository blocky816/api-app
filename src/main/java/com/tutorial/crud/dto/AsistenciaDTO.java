package com.tutorial.crud.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tutorial.crud.util.RespuestasDeserializer;

import java.util.List;

public class AsistenciaDTO {
    private List<Object> id_deportista;
    private List<Object> grupo_id;
    private String fecha_hora;
    @JsonDeserialize(using = RespuestasDeserializer.class) // Aplicamos el deserializador personalizado
    private Object type_attendance;

    // Getters y setters
    public List<Object> getId_deportista() { return id_deportista; }
    public void setId_deportista(List<Object> id_deportista) { this.id_deportista = id_deportista; }
    public List<Object> getGrupo_id() { return grupo_id; }
    public void setGrupo_id(List<Object> grupo_id) { this.grupo_id = grupo_id; }
    public String getFecha_hora() { return fecha_hora; }
    public void setFecha_hora(String fecha_hora) { this.fecha_hora = fecha_hora; }
    public Object getType_attendance() { return type_attendance; }
    public void setType_attendance(Object type_attendance) { this.type_attendance = type_attendance; }
}
