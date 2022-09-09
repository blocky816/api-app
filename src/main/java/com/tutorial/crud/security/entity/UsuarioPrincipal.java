package com.tutorial.crud.security.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

public class UsuarioPrincipal implements UserDetails {
    private String nombre; //Membresia
    private String nombreUsuario; // IdUsuario
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private String Estatus;
    private String cliente;
    private String ClienteTipo;
    private String EstatusCobranza;
    private String nivel;

    public UsuarioPrincipal(String nombre, String nombreUsuario, String email, String password,
			Collection<? extends GrantedAuthority> authorities, String estatus, String cliente, String clienteTipo,
			String estatusCobranza, String nivel) {
		super();
		this.nombre = nombre;
		this.nombreUsuario = nombreUsuario;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
		this.Estatus = estatus;
		this.cliente = cliente;
		this.ClienteTipo = clienteTipo;
		this.EstatusCobranza = estatusCobranza;
		this.nivel = nivel;
	}

	public static UsuarioPrincipal build(Usuario usuario){
        List<GrantedAuthority> authorities =
                usuario.getRoles().stream().map(rol -> new SimpleGrantedAuthority(rol
                .getRolNombre().name())).collect(Collectors.toList());
        return new UsuarioPrincipal(usuario.getNombre(), usuario.getNombreUsuario(), usuario.getEmail(), usuario.getPassword(), authorities, usuario.getEstatus(),usuario.getCliente(),usuario.getClienteTipo(),usuario.getEstatusCobranza(),usuario.getNivel());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return nombreUsuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }


    
    
}
