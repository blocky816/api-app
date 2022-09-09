package com.tutorial.crud.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public class ClienteBasculaDTO {
	
	@JsonProperty("Altura") 
    public int altura;
	
	@JsonProperty("Atleta") 
    public boolean atleta;
	
	@JsonProperty("NivelActividad") 
    public String nivelActividad;
	
	@JsonProperty("Peso") 
    public float peso;
	
	@JsonProperty("Edad") 
    public int edad;
}
