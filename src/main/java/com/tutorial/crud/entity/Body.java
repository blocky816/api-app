package com.tutorial.crud.entity;

import java.sql.Date;
import java.util.UUID;

public class Body {
	private String observaciones;
	private int idRutina;
	private String asesor;
	
	private String year;
	private String regimenFiscal;
	
	private String tipoCFDI;
	
	private String email;
	
	private String uso;
	
	private String pago;
	
	private String domicilio;
	
	private String codigoPostal;
	
	private String rfc;
	
	private String name;
	
	private String actividad;
	
	private java.util.Date anio;
	
	private String recibo;
	
	private String factura;
	
	private String folio;
	
	private String lugarVenta;
	
	private boolean qr;
	
	private boolean acceso;

	private int idTipoAcceso;
	
	private int idAntena;
	
	private int idCaseta;
	
	private Date hora;	
	
	private int usuario;
	
	private int empleado;
	
	private int idChip;
	
	private int idChipAnterior;
	private int idChipNuevo;
	
	private int idSolicitud;
	
	private String aprovado;
	
	private String entragaDocumento;
	
	private String validacion;
	
	private boolean superr;
	
	private int mes;
	
	private int cobranza;

	private int terminal;
	
	private int idVentaDetalle;

	private String club;

	private String dia;
	
	private int idClub;
	
	private UUID id;

	private Date fechaInicio;
	
	private Date fechaFin;
	
	private String periodoInicio;
	
	private String periodoFinal;
	
	private String solicita;
	
	private int diasMenos;
	
	private long fecha;
	
	private String mensaje;
	
	private String raw;
	
	private boolean admin;
	
	private String idFactura;
	
	private String type;
	
	private String formato;
	
	private String date;
	
	private String nombre;
	
	
	
	
	
	public int getIdChipAnterior() {
		return idChipAnterior;
	}

	public void setIdChipAnterior(int idChipAnterior) {
		this.idChipAnterior = idChipAnterior;
	}

	public int getIdChipNuevo() {
		return idChipNuevo;
	}

	public void setIdChipNuevo(int idChipNuevo) {
		this.idChipNuevo = idChipNuevo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(String idFactura) {
		this.idFactura = idFactura;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public int getIdRutina() {
		return idRutina;
	}

	public void setIdRutina(int idRutina) {
		this.idRutina = idRutina;
	}

	public String getAsesor() {
		return asesor;
	}

	public void setAsesor(String asesor) {
		this.asesor = asesor;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getRegimenFiscal() {
		return regimenFiscal;
	}

	public void setRegimenFiscal(String regimenFiscal) {
		this.regimenFiscal = regimenFiscal;
	}

	public String getTipoCFDI() {
		return tipoCFDI;
	}

	public void setTipoCFDI(String tipoCFDI) {
		this.tipoCFDI = tipoCFDI;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUso() {
		return uso;
	}

	public void setUso(String uso) {
		this.uso = uso;
	}

	public String getPago() {
		return pago;
	}

	public void setPago(String pago) {
		this.pago = pago;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getActividad() {
		return actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public java.util.Date getAnio() {
		return anio;
	}

	public void setAnio(java.util.Date anio) {
		this.anio = anio;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getFactura() {
		return factura;
	}

	public void setFactura(String factura) {
		this.factura = factura;
	}

	public String getRecibo() {
		return recibo;
	}

	public void setRecibo(String recibo) {
		this.recibo = recibo;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getLugarVenta() {
		return lugarVenta;
	}

	public void setLugarVenta(String lugarVenta) {
		this.lugarVenta = lugarVenta;
	}

	public boolean isQr() {
		return qr;
	}

	public void setQr(boolean qr) {
		this.qr = qr;
	}

	public boolean isAcceso() {
		return acceso;
	}

	public void setAcceso(boolean acceso) {
		this.acceso = acceso;
	}

	public int getIdTipoAcceso() {
		return idTipoAcceso;
	}

	public void setIdTipoAcceso(int idTipoAcceso) {
		this.idTipoAcceso = idTipoAcceso;
	}

	public long getFecha() {
		return fecha;
	}

	public void setFecha(long fecha) {
		this.fecha = fecha;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getRaw() {
		return raw;
	}

	public void setRaw(String raw) {
		this.raw = raw;
	}

	public int getIdAntena() {
		return idAntena;
	}

	public void setIdAntena(int idAntena) {
		this.idAntena = idAntena;
	}

	public int getIdCaseta() {
		return idCaseta;
	}

	public void setIdCaseta(int idCaseta) {
		this.idCaseta = idCaseta;
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public int getDiasMenos() {
		return diasMenos;
	}

	public void setDiasExtra(int diasMenos) {
		this.diasMenos = diasMenos;
	}

	public String getSolicita() {
		return solicita;
	}

	public void setSolicita(String solicita) {
		this.solicita = solicita;
	}

	public int getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(int idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public String getAprovado() {
		return aprovado;
	}

	public void setAprovado(String aprovado) {
		this.aprovado = aprovado;
	}

	public String getEntragaDocumento() {
		return entragaDocumento;
	}

	public void setEntragaDocumento(String entragaDocumento) {
		this.entragaDocumento = entragaDocumento;
	}

	public String getValidacion() {
		return validacion;
	}

	public void setValidacion(String validacion) {
		this.validacion = validacion;
	}

	public int getEmpleado() {
		return empleado;
	}

	public void setEmpleado(int empleado) {
		this.empleado = empleado;
	}

	public String getPeriodoInicio() {
		return periodoInicio;
	}

	public void setPeriodoInicio(String periodoInicio) {
		this.periodoInicio = periodoInicio;
	}

	public String getPeriodoFinal() {
		return periodoFinal;
	}

	public void setPeriodoFinal(String periodoFinal) {
		this.periodoFinal = periodoFinal;
	}

	public int getUsuario() {
		return usuario;
	}

	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}

	public String getClub() {
		return club;
	}

	public void setClub(String club) {
		this.club = club;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public int getTerminal() {
		return terminal;
	}

	public void setTerminal(int terminal) {
		this.terminal = terminal;
	}

	public int getIdVentaDetalle() {
		return idVentaDetalle;
	}

	public void setIdVentaDetalle(int idVentaDetalle) {
		this.idVentaDetalle = idVentaDetalle;
	}
	
	public boolean isSuper() {
		return superr;
	}

	public void setSuper(boolean superr) {
		this.superr = superr;
	}
	
	
	public int getIdClub() {
		return idClub;
	}

	public void setIdClub(int idClub) {
		this.idClub = idClub;
	}
	
	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	
	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getCobranza() {
		return cobranza;
	}

	public void setCobranza(int cobranza) {
		this.cobranza = cobranza;
	}
	
	

	public int getIdChip() {
		return idChip;
	}

	public void setIdChip(int idChip) {
		this.idChip = idChip;
	}

	@Override
	public String toString() {
		return "Body [recibo=" + recibo + ", folio=" + folio + ", lugarVenta=" + lugarVenta + ", qr=" + qr + ", acceso="
				+ acceso + ", idTipoAcceso=" + idTipoAcceso + ", idAntena=" + idAntena + ", idCaseta=" + idCaseta
				+ ", hora=" + hora + ", usuario=" + usuario + ", empleado=" + empleado + ", idChip=" + idChip
				+ ", idSolicitud=" + idSolicitud + ", aprovado=" + aprovado + ", entragaDocumento=" + entragaDocumento
				+ ", validacion=" + validacion + ", superr=" + superr + ", mes=" + mes + ", cobranza=" + cobranza
				+ ", terminal=" + terminal + ", idVentaDetalle=" + idVentaDetalle + ", club=" + club + ", dia=" + dia
				+ ", idClub=" + idClub + ", id=" + id + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin
				+ ", periodoInicio=" + periodoInicio + ", periodoFinal=" + periodoFinal + ", solicita=" + solicita
				+ ", diasMenos=" + diasMenos + ", fecha=" + fecha + ", mensaje=" + mensaje + ", raw=" + raw + "]";
	}
	

	

	
}
