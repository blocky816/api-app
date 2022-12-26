package com.tutorial.crud.dto;

import java.util.ArrayList;

public class ArrayPruebaNuevo {

    ArrayList<ArrayPrueba2Nuevo> body;

    public ArrayList<ArrayPrueba2Nuevo> getBody() {
        return body;
    }

    public void setBody(ArrayList<ArrayPrueba2Nuevo> body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "ArrayPrueba [body=" + body + "]";
    }
}
