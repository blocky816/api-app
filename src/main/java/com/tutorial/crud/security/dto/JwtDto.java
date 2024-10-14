package com.tutorial.crud.security.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tutorial.crud.util.CustomLocalDateTimeSerializer;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;

public class JwtDto {
    private String token;
    private String bearer = "Bearer";
    private String nombreUsuario;
    private Collection<? extends GrantedAuthority> authorities;
    private int club;
    private boolean activo;
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime inicioSesion;

    public JwtDto(String token, String nombreUsuario, Collection<? extends GrantedAuthority> authorities,int club,boolean activo, LocalDateTime inicioSesion) {
        this.token = token;
        this.nombreUsuario = nombreUsuario;
        this.authorities = authorities;
        this.club=club;
        this.activo=activo;
        this.inicioSesion=inicioSesion;
    }
    public JwtDto(String token, String nombreUsuario, Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.nombreUsuario = nombreUsuario;
        this.authorities = authorities;
    }
    

    public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public int getClub() {
		return club;
	}



	public void setClub(int club) {
		this.club = club;
	}



	public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBearer() {
        return bearer;
    }

    public void setBearer(String bearer) {
        this.bearer = bearer;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public LocalDateTime getInicioSesion() {
        return inicioSesion;
    }

    public void setInicioSesion(LocalDateTime inicioSesion) {
        this.inicioSesion = inicioSesion;
    }
}
