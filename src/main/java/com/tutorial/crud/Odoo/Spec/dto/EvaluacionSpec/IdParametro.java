package com.tutorial.crud.Odoo.Spec.dto.EvaluacionSpec;

import java.util.ArrayList;

public class IdParametro {
    public int id;
    public String name;
    public Object area;
    public ArrayList<Object> unidad;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Object getArea() {
        return area;
    }
    public void setArea(Object area) {
        this.area = area;
    }
    public ArrayList<Object> getUnidad() {
        return unidad;
    }
    public void setUnidad(ArrayList<Object> unidad) {
        this.unidad = unidad;
    }
}
