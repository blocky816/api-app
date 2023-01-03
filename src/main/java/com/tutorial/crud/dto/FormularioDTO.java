package com.tutorial.crud.dto;

import com.tutorial.crud.aopDao.Pregunta;
import java.util.ArrayList;

public class FormularioDTO {

    private Integer id;
    private String nombre;
    private String nivel;
    private String tipo;

    private ArrayList<Pregunta> formulario;

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNivel() { return nivel; }

    public void setNivel(String nivel) { this.nivel = nivel; }

    public String getTipo() { return tipo; }

    public void setTipo(String tipo) { this.tipo = tipo; }

    public ArrayList<Pregunta> getFormulario() { return formulario;
    }

    public void setFormulario(ArrayList<Pregunta> formulario) { this.formulario = formulario; }
}
