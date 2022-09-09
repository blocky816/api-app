package com.tutorial.crud.dto;

import java.util.Arrays;
import java.util.UUID;

import com.tutorial.crud.entity.CASala;
import com.tutorial.crud.entity.Cliente;

public class ClienteDTO {
	public int idCliente;
	public String nombre;
	public String email;
	public byte[] imagen;
	public UUID id;
	public String nombreSala;
	public String dia;
	private String check;
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public byte[] getImagen() {
		return imagen;
	}
	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getNombreSala() {
		return nombreSala;
	}
	public void setNombreSala(String nombreSala) {
		this.nombreSala = nombreSala;
	}
	public String getDia() {
		return dia;
	}
	public void setDia(String dia) {
		this.dia = dia;
	}
	public String getCheck() {
		return check;
	}
	public void setCheck(String check) {
		this.check = check;
	}
	@Override
	public String toString() {
		return "ClienteDTO [idCliente=" + idCliente + ", nombre=" + nombre + ", email=" + email + ", imagen="
				+ Arrays.toString(imagen) + ", id=" + id + ", nombreSala=" + nombreSala + ", dia=" + dia + ", check="
				+ check + "]";
	}
	
	

}
