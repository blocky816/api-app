/*En este paquete tenemos nuestra clase ClienteAux.java esta clase solo la utilizamos para poder almacenar de 
 * manera temporal el tipo de objeto que nos devuelve el web service de alpha
 * 
 *	@autor: Daniel García Velasco 
 * 			Abimael Rueda Galindo
 *	@version: 1
 *12/07/2021
*/
package com.tutorial.crud.entity;

import java.util.Date;
import java.util.List;




public class ClienteAux 
{
	private int IdCliente; //Variables
	
	private long NoMembresia;	
	
	private String Nombre;
	
	private String ApellidoPaterno;
	
	private String ApellidoMaterno;
	
	private String NombreCompleto;
	
	private String Servicio;
	
	private String EstatusAcceso;
	
	private boolean TipoAcceso;
	
	private String URLFoto;
	
	private boolean DomicilioPago;
	
	private Date InicioActividades;
	
	private String Sexo;
	
	private Date FechaNacimiento;
	
	private boolean MensualidadPagada;
	
	private String Email;
	
	private Date FechaFinAcceso;
	
	private int IdSexo;
	
	private String Nacionalidad;
	
	private String Telefono;

	
	private int IdClienteGrupo;

	private int IdClienteSector;
	
	private int IdCaptura;
	
	private int IdCapturaFecha;
	
	private boolean Activo;
	
	private Date FechaCreacion;
	
	private Date FechaModificacion;
	
	private boolean tieneAcceso;
	
	private Club club;
	
	private TipoCliente TipoCliente;
	
	private Categoria categoria;
	
	private EstatusCliente estatusCliente;
	
	private EstatusMembresia estatusMembresia;
	
	private EstatusCobranza estatusCobranza;
	
    private List<Mensajes> mensajes;
	
	private TipoMembresia tipoMembresia;

	private List<HorarioOtroClub> HorarioOtroClub;

	//Se generaron todos los Getters y Setters.
	
	public int getIdCliente() {
		return IdCliente;
	}

	public void setIdCliente(int idCliente) {
		IdCliente = idCliente;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public String getApellidoPaterno() {
		return ApellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		ApellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return ApellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		ApellidoMaterno = apellidoMaterno;
	}

	public String getNombreCompleto() {
		return NombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		NombreCompleto = nombreCompleto;
	}

	public String getServicio() {
		return Servicio;
	}

	public void setServicio(String servicio) {
		Servicio = servicio;
	}

	public String getEstatusAcceso() {
		return EstatusAcceso;
	}

	public void setEstatusAcceso(String estatusAcceso) {
		EstatusAcceso = estatusAcceso;
	}

	public boolean isTipoAcceso() {
		return TipoAcceso;
	}

	public void setTipoAcceso(boolean tipoAcceso) {
		TipoAcceso = tipoAcceso;
	}

	public String getURLFoto() {
		return URLFoto;
	}

	public void setURLFoto(String uRLFoto) {
		URLFoto = uRLFoto;
	}

	public boolean isDomicilioPago() {
		return DomicilioPago;
	}

	public void setDomicilioPago(boolean domicilioPago) {
		DomicilioPago = domicilioPago;
	}

	public Date getInicioActividades() {
		return InicioActividades;
	}

	public void setInicioActividades(Date inicioActividades) {
		InicioActividades = inicioActividades;
	}

	public String getSexo() {
		return Sexo;
	}

	public void setSexo(String sexo) {
		Sexo = sexo;
	}

	public Date getFechaNacimiento() {
		return FechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		FechaNacimiento = fechaNacimiento;
	}

	public boolean isMensualidadPagada() {
		return MensualidadPagada;
	}

	public void setMensualidadPagada(boolean mensualidadPagada) {
		MensualidadPagada = mensualidadPagada;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public Date getFechaFinAcceso() {
		return FechaFinAcceso;
	}

	public void setFechaFinAcceso(Date fechaFinAcceso) {
		FechaFinAcceso = fechaFinAcceso;
	}

	public int getIdSexo() {
		return IdSexo;
	}

	public void setIdSexo(int idSexo) {
		IdSexo = idSexo;
	}

	public String getNacionalidad() {
		return Nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		Nacionalidad = nacionalidad;
	}

	public String getTelefono() {
		return Telefono;
	}

	public void setTelefono(String telefono) {
		Telefono = telefono;
	}

	public int getIdClienteGrupo() {
		return IdClienteGrupo;
	}

	public void setIdClienteGrupo(int idClienteGrupo) {
		IdClienteGrupo = idClienteGrupo;
	}

	public int getIdClienteSector() {
		return IdClienteSector;
	}

	public void setIdClienteSector(int idClienteSector) {
		IdClienteSector = idClienteSector;
	}

	public int getIdCaptura() {
		return IdCaptura;
	}

	public void setIdCaptura(int idCaptura) {
		IdCaptura = idCaptura;
	}

	public int getIdCapturaFecha() {
		return IdCapturaFecha;
	}

	public void setIdCapturaFecha(int idCapturaFecha) {
		IdCapturaFecha = idCapturaFecha;
	}

	public boolean isActivo() {
		return Activo;
	}

	public void setActivo(boolean activo) {
		Activo = activo;
	}

	public Date getFechaCreacion() {
		return FechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		FechaCreacion = fechaCreacion;
	}

	public Date getFechaModificacion() {
		return FechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		FechaModificacion = fechaModificacion;
	}

	public boolean isTieneAcceso() {
		return tieneAcceso;
	}

	public void setTieneAcceso(boolean tieneAcceso) {
		this.tieneAcceso = tieneAcceso;
	}

	public Club getClub() {
		return club;
	}

	public void setClub(Club club) {
		this.club = club;
	}

	public TipoCliente getTipoCliente() {
		return TipoCliente;
	}

	public void setTipoCliente(TipoCliente tipoCliente) {
		TipoCliente = tipoCliente;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public EstatusCliente getEstatusCliente() {
		return estatusCliente;
	}

	public void setEstatusCliente(EstatusCliente estatusCliente) {
		this.estatusCliente = estatusCliente;
	}

	public EstatusMembresia getEstatusMembresia() {
		return estatusMembresia;
	}

	public void setEstatusMembresia(EstatusMembresia estatusMembresia) {
		this.estatusMembresia = estatusMembresia;
	}

	public EstatusCobranza getEstatusCobranza() {
		return estatusCobranza;
	}

	public void setEstatusCobranza(EstatusCobranza estatusCobranza) {
		this.estatusCobranza = estatusCobranza;
	}


	public List<Mensajes> getMensajes() {
		return mensajes;
	}

	public void setMensajes(List<Mensajes> mensajes) {
		this.mensajes = mensajes;
	}

	public List<HorarioOtroClub> getHorarioOtroClub() {
		return HorarioOtroClub;
	}

	public void setHorarioOtroClub(List<HorarioOtroClub> horarioOtroClub) {
		HorarioOtroClub = horarioOtroClub;
	}

	public long getNoMembresia() {
		return NoMembresia;
	}

	public void setNoMembresia(long noMembresia) {
		NoMembresia = noMembresia;
	}

	public TipoMembresia getTipoMembresia() {
		return tipoMembresia;
	}

	public void setTipoMembresia(TipoMembresia tipoMembresia) {
		this.tipoMembresia = tipoMembresia;
	}

	public String getNombre() {
		return Nombre;
	}

	@Override
	public String toString() {
		return "Cliente [IdCliente=" + IdCliente + ",\n NoMembresia=" + NoMembresia + ",\n Nombre=" + Nombre
				+ ",\nApellidoPaterno=" + ApellidoPaterno + ",\n ApellidoMaterno=" + ApellidoMaterno + ",\n NombreCompleto="
				+ NombreCompleto + ",\n Servicio=" + Servicio + ",\n EstatusAcceso=" + EstatusAcceso + ",\n TipoAcceso="
				+ TipoAcceso + ",\n URLFoto=" + URLFoto + ",\n DomicilioPago=" + DomicilioPago + ",\n InicioActividades="
				+ InicioActividades + ",\n Sexo=" + Sexo + ",\n FechaNacimiento=" + FechaNacimiento + ",\n MensualidadPagada="
				+ MensualidadPagada + ",\n Email=" + Email + ",\n FechaFinAcceso=" + FechaFinAcceso + ",\n IdSexo=" + IdSexo
				+ ",\n Nacionalidad=" + Nacionalidad + ",\n Telefono=" + Telefono + ",\n IdClienteGrupo=" + IdClienteGrupo
				+ ",\n IdClienteSector=" + IdClienteSector + ",\n IdCaptura=" + IdCaptura + ",\n IdCapturaFecha="
				+ IdCapturaFecha + ",\n Activo=" + Activo + ",\n FechaCreacion=" + FechaCreacion + ",\n FechaModificacion="
				+ FechaModificacion + ",\n tieneAcceso=" + tieneAcceso + ",\n club=" + club + ",\n TipoCliente=" + TipoCliente
				+ ",\n categoria=" + categoria + ",\n estatusCliente=" + estatusCliente + ",\n estatusMembresia="
				+ estatusMembresia + ",\n estatusCobranza=" + estatusCobranza + ",\n mensajes=" + mensajes
				+ ",\n tipoMembresia=" + tipoMembresia + ",\n terminalid=" + HorarioOtroClub + ",]";
	}



	
}
