package com.tutorial.crud.security.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

public class NuevoUsuario {
    @NotBlank
    private String nombre;
    @NotBlank
    private String nombreUsuario;
    @Email
    private String email;
    @NotBlank
    private String password;
    private Set<String> roles = new HashSet<>();
    @NotBlank
    private String Estatus;
    @NotBlank
    private String Cliente;
    @NotBlank
    private String ClienteTipo;
    @NotBlank
    private String EstatusCobranza;
    @NotBlank
    private String Nivel1;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

	public String getEstatus() {
		return Estatus;
	}

	public void setEstatus(String estatus) {
		Estatus = estatus;
	}

	public String getCliente() {
		return Cliente;
	}

	public void setCliente(String cliente) {
		this.Cliente = cliente;
	}

	public String getClienteTipo() {
		return ClienteTipo;
	}

	public void setClienteTipo(String clienteTipo) {
		ClienteTipo = clienteTipo;
	}

	public String getEstatusCobranza() {
		return EstatusCobranza;
	}

	public void setEstatusCobranza(String estatusCobranza) {
		EstatusCobranza = estatusCobranza;
	}

	public String getNivel1() {
		return Nivel1;
	}

	public void setNivel1(String nivel) {
		this.Nivel1 = nivel;
	}

	@Override
	public String toString() {
		return "NuevoUsuario [nombre=" + nombre + ", nombreUsuario=" + nombreUsuario + ", email=" + email
				+ ", password=" + password + ", roles=" + roles + ", Estatus=" + Estatus + ", Cliente=" + Cliente
				+ ", ClienteTipo=" + ClienteTipo + ", EstatusCobranza=" + EstatusCobranza + ", Nivel1=" + Nivel1 + "]";
	}
    
    
}
