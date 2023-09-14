package com.tutorial.crud.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name="employees")
public class Employee {
    @Id
    @Column(name="id")
    private int id;

    @Column(name="empleado")
    private String empleado;

    @Column(name="iniciales")
    private String iniciales;

    @Column(name="activo")
    private String activo;

    @Column(name="club")
    private String club;

    @Column(name="departamento")
    private String departamento;

    @Column(name="puesto")
    private String puesto;

    @Column(name="clave_externa")
    private String claveExterna;

    @Column(name="id_empleado")
    private int idEmpleado;

    @Column(name="rfc")
    private String rfc;

    @Column(name="curp")
    private String curp;

    @Column(name="imss")
    private String imss;

    @Column(name="empleado_tipo")
    private String empleadoTipo;

    @Column(name="fecha_alta")
    private LocalDate fechaAlta;

    @Column(name="fecha_nacimiento")
    private LocalDate fechaNacimiento;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getEmpleado() { return empleado; }

    public void setEmpleado(String empleado) { this.empleado = empleado; }

    public String getIniciales() { return iniciales; }

    public void setIniciales(String iniciales) { this.iniciales = iniciales; }

    public String getActivo() { return activo; }

    public void setActivo(String activo) { this.activo = activo; }

    public String getClub() { return club; }

    public void setClub(String club) { this.club = club; }

    public String getDepartamento() { return departamento; }

    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public String getPuesto() { return puesto; }

    public void setPuesto(String puesto) { this.puesto = puesto; }

    public String getClaveExterna() { return claveExterna; }

    public void setClaveExterna(String claveExterna) { this.claveExterna = claveExterna; }

    public int getIdEmpleado() { return idEmpleado; }

    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }

    public String getRfc() { return rfc; }

    public void setRfc(String rfc) { this.rfc = rfc; }

    public String getCurp() { return curp; }

    public void setCurp(String curp) { this.curp = curp; }

    public String getImss() { return imss; }

    public void setImss(String imss) { this.imss = imss; }

    public String getEmpleadoTipo() { return empleadoTipo; }

    public void setEmpleadoTipo(String empleadoTipo) { this.empleadoTipo = empleadoTipo; }

    public LocalDate getFechaAlta() { return fechaAlta; }

    public void setFechaAlta(LocalDate fechaAlta) { this.fechaAlta = fechaAlta; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }

    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", empleado='" + empleado + '\'' +
                ", iniciales='" + iniciales + '\'' +
                ", activo='" + activo + '\'' +
                ", club='" + club + '\'' +
                ", departamento='" + departamento + '\'' +
                ", puesto='" + puesto + '\'' +
                ", claveExterna='" + claveExterna + '\'' +
                ", idEmpleado=" + idEmpleado +
                ", rfc='" + rfc + '\'' +
                ", curp='" + curp + '\'' +
                ", imss='" + imss + '\'' +
                ", empleadoTipo='" + empleadoTipo + '\'' +
                ", fechaAlta=" + fechaAlta +
                ", fechaNacimiento=" + fechaNacimiento +
                '}';
    }
}
