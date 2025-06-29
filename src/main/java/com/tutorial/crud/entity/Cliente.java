/*En este paquete tenemos nuestro clase Cliente.java y utilizaremos las @anotaciones 
 * JPA para relacionarla con nuestra tabla Cliente, quedaría de la siguiente forma:
 *	@autor: Daniel García Velasco 
 * 			Abimael Rueda Galindo
 *	@version: 1
 *12/07/2021
*/
package com.tutorial.crud.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

import javax.persistence.*;


@Entity //Sirve únicamente para indicarle a JPA que esa clase es una Entity.
@Table(name="cliente") //se utiliza para poner el nombre real de la tabla en la base de datos
public class Cliente implements Serializable
{
	/*@Id
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name="idCliente", unique = true)*/
	@Id //Define la llave primaria.
	@Column(name="idcliente") //Permite establecer el nombre de la columna de la tabla con la que el atributo debe de mapear.
	private int idCliente; //Variables
	
	@Column(name="nomembresia")
	private long NoMembresia;	
	
	@Column(name="nombre")
	private String Nombre;
	
	@Column(name="apellidopaterno")
	private String ApellidoPaterno;
	
	@Column(name="apellidomaterno")
	private String ApellidoMaterno;
	
	@Column(name="nombrecompleto")
	private String NombreCompleto;
	
	@Column(name="servicio")
	private String Servicio;
	
	@Column(name="estatusacceso")
	private String EstatusAcceso;
	
	@Column(name="tipoacceso")
	private boolean TipoAcceso;
	
	@OneToOne(cascade=CascadeType.ALL) //Relación de uno a uno
	@JoinColumn(name="id_foto") //Se utiliza para marcar una propiedad
	private Foto URLFoto;
	
	@Column(name="domiciliopago")
	private Boolean DomicilioPago;
	
	@Column(name="inicioactividades")
	private Date InicioActividades;
	
	@Column(name="sexo")
	private String Sexo;
	
	@Column(name="fechanacimiento")
	private Date FechaNacimiento;
	
	@Column(name="mensualidadpagada")
	private boolean MensualidadPagada;
	
	@Column(name="email")
	private String Email;
	
	@Column(name="fechafinacceso")
	private Date FechaFinAcceso;
	
	@Column(name="idsexo")
	private int IdSexo;
	
	@Column(name="nacionalidad")
	private String Nacionalidad;
	
	@Column(name="telefono")
	private String telefono;

	
	@Column(name="idclientegrupo")
	private int IdClienteGrupo;

	@Column(name="idclientesector")
	private int IdClienteSector;
	
	@Column(name="idcaptura")
	private int IdCaptura;
	
	@Column(name="idcapturafecha")
	private int IdCapturaFecha;
	
	@Column(name="activo")
	private boolean Activo;
	
	@Column(name="fechacreacion")
	private Date FechaCreacion;
	
	@Column(name="fechamodificacion")
	private Date FechaModificacion;
	
	@Column(name="tieneacceso")
	private boolean tieneAcceso;
	
	private String token;
	@Column(columnDefinition = "boolean default false")
	private boolean domiciliado;

	@Column(columnDefinition = "boolean default false",name = "cargo_domiciliacion")
	private boolean cargoDomiciliacion;
	

	@Column(columnDefinition = "real default 0")
	private float monto;
	
	@OneToOne(cascade=CascadeType.ALL) //Relación de uno a uno
	@JoinColumn(name="idclub") //Se utiliza para marcar una propiedad
	private Club club;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="tipocliente")
	private TipoCliente TipoCliente;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name=" categoria")
	private Categoria categoria;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="estatuscliente")
	private EstatusCliente estatusCliente;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="estatusmembresia")
	private EstatusMembresia estatusMembresia;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="estatuscobranza")
	private EstatusCobranza estatusCobranza;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="cliente") //Relación de uno a muchos
    private List<Mensajes> mensajes;
	
	@OneToOne(cascade=CascadeType.ALL )
	@JoinColumn(name="tipomembresia")
	private TipoMembresia tipoMembresia;

	@ManyToMany(cascade = { CascadeType.ALL }) //Relación de muchos a muchos
	@JoinTable(
	        name = "cliente_horariootroclub",
	        joinColumns = {@JoinColumn(name="id_cliente")},
	        inverseJoinColumns = {@JoinColumn(name = "terminalid",referencedColumnName = "terminalid"),
	        					  @JoinColumn(name = "desde",referencedColumnName = "desde"),
	        					  @JoinColumn(name = "hasta",referencedColumnName = "hasta")}
	    )	
	private List<HorarioOtroClub> HorarioOtroClub;

	//@OneToMany(mappedBy = "cliente")
	 //List<RetoUsuario> retoUsuario;
	
	 @OneToMany(mappedBy = "cliente")
	 List<CAApartadosUsuario> apartadosUsuario;
	 
	 @OneToMany(mappedBy = "cliente")
	 List<AgendaReservasUsuario> agendaUsuario;
	 
	 @ManyToOne(cascade=CascadeType.ALL )
	@JoinColumn(name="id_rutina")
	private Rutina rutina;

	@Column(name="dia_inicio_rutina")
	private Date diaInicioRutina;
	
	@Column(name="semanas")
	private int semanas;

	@ManyToOne(cascade=CascadeType.ALL )
	@JoinColumn(name="id_rutina_nuevo")
	//@JsonManagedReference(value = "rutinanuevo-cliente")
	private RutinaNuevo rutinanuevo;

	@Column(name="dia_inicio_rutina_nuevo")
	private LocalDateTime diaInicioRutinanuevo;

	@Column(name="semanas_nuevo")
	private Integer semanasnuevo;

	@Column(name="id_complexband", unique=true)
	private String idComplexBand;

	@Column(name = "id_formulario")
	private Integer idFormulario;

	@Column(name = "es_titular")
	private Boolean esTitular;

	@Column(name = "id_titular")
	private int idTitular;

	@OneToMany(mappedBy = "customer")
	@JsonManagedReference
	List<AnswerChatGPT> answerChatGPTS;

	@Column(name = "idodoo")
	private int idOdoo;

	@Column(name = "ultimo_uso", columnDefinition = "TIMESTAMP(0)")
	private LocalDateTime ultimoUso;

	@Column(name = "profesionalizacion") private Boolean profesionalizacion;

	@Column(name = "consentimiento") private Boolean consentimiento;

	//Se generaron todos los Getters y Setters.

	public int getIdCliente() {
		return idCliente;
	}

	public float obtenerMonto() {
		return monto;
	}

	public void setMonto(float monto) {
		this.monto = monto;
	}

	public boolean isCargoDomiciliacion() {
		return cargoDomiciliacion;
	}

	public void setCargoDomiciliacion(boolean cargoDomiciliacion) {
		this.cargoDomiciliacion = cargoDomiciliacion;
	}

	public String obtenerToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isDomiciliado() {
		return domiciliado;
	}

	public void setDomiciliado(boolean domiciliado) {
		this.domiciliado = domiciliado;
	}

	public void setAgendaUsuario(List<AgendaReservasUsuario> agendaUsuario) {
		this.agendaUsuario = agendaUsuario;
	}

	public void setRutina(Rutina rutina) {
		this.rutina = rutina;
	}

	public Rutina obtenerRutina() {
		return rutina;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
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

	public Foto getURLFoto() {
		return URLFoto;
	}

	public void setURLFoto(Foto uRLFoto) {
		URLFoto = uRLFoto;
	}

	public boolean isDomicilioPago() {
		return DomicilioPago;
	}

	public void setDomicilioPago(Boolean domicilioPago) {
		this.DomicilioPago = domicilioPago;
	}

	public Boolean getDomicilioPago() {
		return DomicilioPago;
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

	
	
	public List<CAApartadosUsuario> obtenerApartado() {
		return apartadosUsuario;
	}

	public java.sql.Date obtenerDiaInicio(){
		java.sql.Date diaInicio=new java.sql.Date(diaInicioRutina.getTime());
		return diaInicio;
		
	}
	public void setDiaInicio(Date date) {
		this.diaInicioRutina=date;
	}
	public String obtenerDiaFinal(){
		LocalDateTime fechaRutina=diaInicioRutina.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		fechaRutina=fechaRutina.plusWeeks(semanas);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedLocalDate = fechaRutina.format(formatter);
		return formattedLocalDate;

	}

	public java.sql.Date obtenerDiaInicioNuevo(){
		java.sql.Date diaInicio=new java.sql.Date(diaInicioRutinanuevo.toEpochSecond(ZoneOffset.UTC));
		return diaInicio;

	}
	public void setDiaInicioNuevo(LocalDateTime date) {
		this.diaInicioRutinanuevo = date;
	}
	public LocalDateTime obtenerDiaFinalNuevo(){
		//LocalDateTime fechaRutina = diaInicioRutinanuevo.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		LocalDateTime fechaRutina = diaInicioRutinanuevo;
		fechaRutina = fechaRutina.plusWeeks(semanasnuevo);
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		//String formattedLocalDate = fechaRutina.format(formatter);
		return fechaRutina;

	}
	public int obtenerSemanas() {
		return semanas;
	}

	public void setSemanas(int semanas) {
		this.semanas = semanas;
	}

	public String getIdComplexBand() { return idComplexBand; }

	public void setIdComplexBand(String idComplexBand) { this.idComplexBand = idComplexBand; }

	public RutinaNuevo obtenerRutinanuevo() {
		return rutinanuevo;
	}

	public void setRutinanuevo(RutinaNuevo rutinanuevo) {
		this.rutinanuevo = rutinanuevo;
	}

	public LocalDateTime getDiaInicioRutinanuevo() {
		return diaInicioRutinanuevo;
	}

	public void setDiaInicioRutinanuevo(LocalDateTime diaInicioRutinanuevo) {
		this.diaInicioRutinanuevo = diaInicioRutinanuevo;
	}

	public Integer obtenerSemanasnuevo() {
		return semanasnuevo;
	}

	public void setSemanasnuevo(Integer semanasnuevo) {
		this.semanasnuevo = semanasnuevo;
	}

	public Integer getFormulario() { return idFormulario; }

	public void setFormulario(Integer idFormulario) { this.idFormulario = idFormulario; }

	public Boolean getEsTitular() { return esTitular; }

	public void setEsTitular(Boolean esTitular) { this.esTitular = esTitular; }

	public int getIdTitular() { return idTitular; }

	public void setIdTitular(int idTitular) { this.idTitular = idTitular; }

	public List<AnswerChatGPT> getAnswerChatGPTS() { return answerChatGPTS; }

	public void setAnswerChatGPTS(List<AnswerChatGPT> answerChatGPTS) { this.answerChatGPTS = answerChatGPTS; }

	public int getIdOdoo() { return idOdoo; }

	public void setIdOdoo(int idOdoo) { this.idOdoo = idOdoo; }

	public String getTelefono() { return telefono; }

	public void setTelefono(String telefono) { this.telefono = telefono; }

	public LocalDateTime getUltimoUso() {
		return ultimoUso;
	}

	public void setUltimoUso(LocalDateTime ultimoUso) {
		this.ultimoUso = ultimoUso;
	}

	public Boolean getProfesionalizacion() {
		return profesionalizacion;
	}

	public void setProfesionalizacion(Boolean profesionalizacion) {
		this.profesionalizacion = profesionalizacion;
	}

	public Boolean getConsentimiento() {
		return consentimiento;
	}

	public void setConsentimiento(Boolean consentimiento) {
		this.consentimiento = consentimiento;
	}

	@Override
	public String toString() {
		return "Cliente [IdCliente=" + idCliente + ",\n NoMembresia=" + NoMembresia + ",\n Nombre=" + Nombre
				+ ",\nApellidoPaterno=" + ApellidoPaterno + ",\n ApellidoMaterno=" + ApellidoMaterno + ",\n NombreCompleto="
				+ NombreCompleto + ",\n Servicio=" + Servicio + ",\n EstatusAcceso=" + EstatusAcceso + ",\n TipoAcceso="
				+ TipoAcceso + ",\n URLFoto=" + URLFoto + ",\n DomicilioPago=" + DomicilioPago + ",\n InicioActividades="
				+ InicioActividades + ",\n Sexo=" + Sexo + ",\n FechaNacimiento=" + FechaNacimiento + ",\n MensualidadPagada="
				+ MensualidadPagada + ",\n Email=" + Email + ",\n FechaFinAcceso=" + FechaFinAcceso + ",\n IdSexo=" + IdSexo
				+ ",\n Nacionalidad=" + Nacionalidad + ",\n Telefono=" + telefono + ",\n IdClienteGrupo=" + IdClienteGrupo
				+ ",\n IdClienteSector=" + IdClienteSector + ",\n IdCaptura=" + IdCaptura + ",\n IdCapturaFecha="
				+ IdCapturaFecha + ",\n Activo=" + Activo + ",\n FechaCreacion=" + FechaCreacion + ",\n FechaModificacion="
				+ FechaModificacion + ",\n tieneAcceso=" + tieneAcceso + ",\n club=" + club + ",\n TipoCliente=" + TipoCliente
				+ ",\n categoria=" + categoria + ",\n estatusCliente=" + estatusCliente + ",\n estatusMembresia="
				+ estatusMembresia + ",\n estatusCobranza=" + estatusCobranza + ",\n mensajes=" + mensajes
				+ ",\n tipoMembresia=" + tipoMembresia + ",\n terminalid=" + HorarioOtroClub + ",\n idComplexBand=" + idComplexBand
				+ ",\n esTitular=" + esTitular + ",]";
	}
}
