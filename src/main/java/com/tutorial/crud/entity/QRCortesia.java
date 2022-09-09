package com.tutorial.crud.entity;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.http.converter.HttpMessageNotWritableException;

@Entity
@Table(name="qr_cortesia") //Se utiliza para poner el nombre real de la tabla en la base de datos
public class QRCortesia {
	@Id //Define la llave primaria.
    @GeneratedValue(generator = "UUID") //Se estableció un tipo de variable UUID 
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false) //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
    private UUID id;
	
	@Column(name = "id_registro")
	private String idRegistro;

	@Column(name = "hora_entrada")
	private Date horaEntrada;

	@Column(name = "hora_salida")
	private Date horaSalida;	

	@Column(name = "horas")
	private int horas;

	@Column(name = "costo_hora")
	private float costoHora;

	@Column(name = "costo_total")
	private float costoTotal;
	
	@Column(name = "club")
	private String club;
	
	@Column(name = "pagado")
	private boolean pagado;
	
	@Column(name = "turno")
	private String turno;
	
	@Column(name = "qr")
	private boolean qr;
	
	@Column(name = "redimido")
	private boolean redimido;
	
	
	@ManyToOne()
    @JoinColumn(name="id_caseta")
	private Caseta idCaseta;
	
	@Column(name = "mensaje")
	private String mensaje;
	
	@Column(name = "acceso")
	private boolean acceso;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tipo_acceso")
    private TipoAcceso tipoAcceso;
	
	

	public boolean isAcceso() {
		return acceso;
	}

	public boolean isRedimido() {
		return redimido;
	}

	public void setRedimido(boolean redimido) {
		this.redimido = redimido;
	}

	public boolean isQr() {
		return qr;
	}

	public void setQr(boolean qr) {
		this.qr = qr;
	}

	public void setIdCaseta(Caseta idCaseta) {
		this.idCaseta = idCaseta;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public void setAcceso(boolean acceso) {
		this.acceso = acceso;
	}

	public void setTipoAcceso(TipoAcceso tipoAcceso) {
		this.tipoAcceso = tipoAcceso;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public String getIdRegistro() {
		return idRegistro;
	}

	public void setIdRegistro(String idRegistro) {
		this.idRegistro = idRegistro;
	}

	public String getHoraEntrada() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		// Aqui usamos la instancia formatter para darle el formato a la fecha. Es importante ver que el resultado es un string.
		String fechaTexto = formatter.format(horaEntrada);
		return fechaTexto;
	}
	public void setHoraEntrada(String horaEntrada) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date fecha = formatter.parse(horaEntrada);
		this.horaEntrada = fecha;
	}
	public Date obtenerHoraEntrada() {
		return horaEntrada;
	}
	
	public Date obtenerHoraSalida() {
		return horaSalida;
	}
	
	public void colocarHoraEntrada(Date horaEntrada) {
		this.horaEntrada=horaEntrada;
	}

	public String getHoraSalida() {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			// Aqui usamos la instancia formatter para darle el formato a la fecha. Es importante ver que el resultado es un string.
			String fechaTexto = formatter.format(horaSalida);
			return  fechaTexto;
			
		}catch(Exception e) {
			return " ";
		}
	}

	public void setHoraSalida(Date horaSalida) {
		this.horaSalida = horaSalida;
	}

	public int getHoras() {
		return horas;
	}

	public void setHoras(int horas) {
		this.horas = horas;
	}

	public float getCostoHora() {
		return costoHora;
	}

	public void setCostoHora(float costoHora) {
		this.costoHora = costoHora;
	}

	public float getCostoTotal() {
		return costoTotal;
	}

	public void setCostoTotal(float costoTotal) {
		this.costoTotal = costoTotal;
	}

	public String getClub() {
		return club;
	}

	public void setClub(String club) {
		this.club = club;
	}

	public boolean isPagado() {
		return pagado;
	}

	public void setPagado(boolean pagado) {
		this.pagado = pagado;
	}

	
	
	public UUID getId() {
		return id;
	}

	@Override
	public String toString() {
		return "EstacionamientoExterno [id=" + id + ", idRegistro=" + idRegistro + ", horaEntrada=" + horaEntrada
				+ ", horaSalida=" + horaSalida + ", horas=" + horas + ", costoHora=" + costoHora + ", costoTotal="
				+ costoTotal + ", club=" + club + ", pagado=" + pagado + ", turno=" + turno + "]";
	}



	
}
