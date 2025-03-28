package com.tutorial.crud.Odoo.Spec.dto;

import com.tutorial.crud.entity.PaseUsuario;

import java.util.List;

public class PaseHealthStudioDTO {
    private Integer idCliente;
    private String nombreCompleto;
    private String sexo;
    private String telefono;
    private String acceso;
    private String club;
    //private String tipoMembresia;
    //private String estadoCobranza;
    //private String estatusMembresia;
    private List<PaseUsuario> pases;

    /*public PaseHealthStudioDTO(String nombreCompleto, String sexo, String telefono, String acceso, String club, String tipoMembresia, String estadoCobranza, String estatusMembresia, List<PaseUsuario> pases) {
        this.nombreCompleto = nombreCompleto;
        this.sexo = sexo;
        this.telefono = telefono;
        this.acceso = acceso;
        this.club = club;
        this.tipoMembresia = tipoMembresia;
        this.estadoCobranza = estadoCobranza;
        this.estatusMembresia = estatusMembresia;
        this.pases = pases;
    }*/

    public PaseHealthStudioDTO(Integer idCliente, String nombreCompleto, String sexo, String telefono, String acceso, String club, List<PaseUsuario> pases) {
        this.idCliente = idCliente;
        this.nombreCompleto = nombreCompleto;
        this.sexo = sexo;
        this.telefono = telefono;
        this.acceso = acceso;
        this.club = club;
        this.pases = pases;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public PaseHealthStudioDTO() {
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getAcceso() {
        return acceso;
    }

    public void setAcceso(String acceso) {
        this.acceso = acceso;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

   /* public String getTipoMembresia() {
        return tipoMembresia;
    }

    public void setTipoMembresia(String tipoMembresia) {
        this.tipoMembresia = tipoMembresia;
    }

    public String getEstadoCobranza() {
        return estadoCobranza;
    }

    public void setEstadoCobranza(String estadoCobranza) {
        this.estadoCobranza = estadoCobranza;
    }

    public String getEstatusMembresia() {
        return estatusMembresia;
    }

    public void setEstatusMembresia(String estatusMembresia) {
        this.estatusMembresia = estatusMembresia;
    }*/

    public List<PaseUsuario> getPases() {
        return pases;
    }

    public void setPases(List<PaseUsuario> pases) {
        this.pases = pases;
    }

    @Override
    public String toString() {
        return "PaseHealthStudioDTO{" +
                "nombreCompleto='" + nombreCompleto + '\'' +
                ", sexo='" + sexo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", acceso='" + acceso + '\'' +
                ", club='" + club + '\'' +
                ", pases=" + pases +
                '}';
    }
}
