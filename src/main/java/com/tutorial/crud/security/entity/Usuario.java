package com.tutorial.crud.security.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String nombre; //membresia
    @NotNull
    @Column(unique = true)
    private String nombreUsuario; //idUduario
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "usuario_id"),
    inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Rol> roles = new HashSet<>();
    @NotNull
    private String Estatus;
    @NotNull
    private String cliente;
    @NotNull
    private String ClienteTipo;
    @NotNull
    private String EstatusCobranza;
    @NotNull
    private String nivel;
    
    public Usuario() {
    }

    public Usuario(@NotNull String nombre, @NotNull String nombreUsuario, @NotNull String email,
			@NotNull String password, @NotNull String estatus, @NotNull String cliente,
			@NotNull String clienteTipo, @NotNull String estatusCobranza, @NotNull String nivel) {
		super();
		this.nombre = nombre;
		this.nombreUsuario = nombreUsuario;
		this.email = email;
		this.password = password;
		this.Estatus = estatus;
		this.cliente = cliente;
		this.ClienteTipo = clienteTipo;
		this.EstatusCobranza = estatusCobranza;
		this.nivel = nivel;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }

	public String getEstatus() {
		return Estatus;
	}

	public void setEstatus(String estatus) {
		Estatus = estatus;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
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

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", nombreUsuario=" + nombreUsuario + ", email=" + email
				+ ", password=" + password + ", roles=" + roles + ", Estatus=" + Estatus + ", cliente=" + cliente
				+ ", ClienteTipo=" + ClienteTipo + ", EstatusCobranza=" + EstatusCobranza + ", nivel=" + nivel + "]";
	}
    
}
