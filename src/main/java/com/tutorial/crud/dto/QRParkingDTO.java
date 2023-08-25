package com.tutorial.crud.dto;

public class QRParkingDTO {
    private String idRegistro;
    private String club;
    private int idUsuario;
    private float costo;
    private float debito;
    private boolean pagado;

    public String getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(String idRegistro) {
        this.idRegistro = idRegistro;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public float getDebito() {
        return debito;
    }

    public void setDebito(float debito) {
        this.debito = debito;
    }

    public boolean getPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }



    @Override
    public String toString() {
        return "QRParkingDTO{" +
                "idRegistro='" + idRegistro + '\'' +
                ", club='" + club + '\'' +
                ", idUsuario=" + idUsuario +
                ", costo=" + costo +
                ", debito=" + debito +
                ", pagado=" + pagado +
                '}';
    }
}
