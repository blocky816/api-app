package com.tutorial.crud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ListaTorniquetes {

    @JsonProperty("lista_torniquetes")
    private List<Torniquete> listaTorniquetes;

    public List<Torniquete> getListaTorniquetes() {
        return listaTorniquetes;
    }

    public void setListaTorniquetes(List<Torniquete> listaTorniquetes) {
        this.listaTorniquetes = listaTorniquetes;
    }

    @Override
    public String toString() {
        return "ListaTorniquetes{" +
                "listaTorniquetes=" + listaTorniquetes +
                '}';
    }
}
