package com.tutorial.crud.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Immutable;

public class Reporte {
		
		private int idCliente;
		
		private String cliente;

		 private int idEmpresa;
		
		private int idContacto;
		
		private String contacto;
		
		 private String razonSocial;
		
		 private String deudor;
		
		 private int idTitular;
		
		private int idEstatus;
		
		private String estatus;
		
		private int idClienteTipo;

		private String clienteTipo;
		
		private int idClienteGrupo;

		private String clienteGrupo;
		
		private boolean titular;
		
		private String titularSN;
		
		private int idNivel0;
		
		private int idNivel1;

		private int idNivel2;
		
		private int idNivel3;

		private int idNivel4;
		
		private String nombreDelTitular;		

		private float cargo;

		private float abono;

		private float saldo;
		
		private int idGrupoEmpresarial;
		
		private String codMoneda;
		
		private int idVendedorAsignado;
		
		private String empleado;
		
		private String iniciales;
		
		private int idAdicionalTipo;
		
		private String adicionalTipo;
		
		private long membresia;
		
		private String cuotaTipo;
		
		private int idClienteCategoria;
		
		private int idMembresiaTipo;
		
		private String membresiaTipo;
		
		private String clienteCategoria;
		
		private int huella;
		
		private int idCapturo;
		
		private Date idCapturoFecha;
		
		private String capturo;
		
		private boolean membresiaEmpresarial;
		
		private int idEmpresaTitular;
		
		private int idClienteSector;
		
		private int idClienteSubgrupo;
		
		private int idClienteGiro;
		
		private String clienteSector;
		
		private String clienteSubgrupo;
		
		private String clienteGiro;
		
		private int idEstatusCobranza;
		
		private int idEstatuMembresia;
		
		private String estatusCobranza;
		
		private String estatusMembresia;
		
		private Date fechaNacimiento;
		
		private int mesNacimiento;
		
		private int diaNacimiento;
		
		private int AnioNacimiento;
		
		private int edad;
		
		private int idSexo;
		
		private int idEstadoCivil;
		
		private String estadoCivil;
		
		private String sexo;
		
		private String membresiaAsociada;
		
		private Date inicioDeActividades;
		
		private int idEstatusAccceso;
		
		private String estatusAcceso;
		
		private String Empresa;
		
		private String nivel1;
		
		private String email;
		
		private String telefonoCasa;
		
		private String telefonoMovil;
		
		private String telefonoOficina;

		public int getIdCliente() {
			return idCliente;
		}

		public void setIdCliente(int idCliente) {
			this.idCliente = idCliente;
		}

		public String getCliente() {
			return cliente;
		}

		public void setCliente(String cliente) {
			this.cliente = cliente;
		}

		public int getIdEmpresa() {
			return idEmpresa;
		}

		public void setIdEmpresa(int idEmpresa) {
			this.idEmpresa = idEmpresa;
		}

		public int getIdContacto() {
			return idContacto;
		}

		public void setIdContacto(int idContacto) {
			this.idContacto = idContacto;
		}

		public String getContacto() {
			return contacto;
		}

		public void setContacto(String contacto) {
			this.contacto = contacto;
		}

		public String getRazonSocial() {
			return razonSocial;
		}

		public void setRazonSocial(String razonSocial) {
			this.razonSocial = razonSocial;
		}

		public String getDeudor() {
			return deudor;
		}

		public void setDeudor(String deudor) {
			this.deudor = deudor;
		}

		public int getIdTitular() {
			return idTitular;
		}

		public void setIdTitular(int idTitular) {
			this.idTitular = idTitular;
		}

		public int getIdEstatus() {
			return idEstatus;
		}

		public void setIdEstatus(int idEstatus) {
			this.idEstatus = idEstatus;
		}

		public String getEstatus() {
			return estatus;
		}

		public void setEstatus(String estatus) {
			this.estatus = estatus;
		}

		public int getIdClienteTipo() {
			return idClienteTipo;
		}

		public void setIdClienteTipo(int idClienteTipo) {
			this.idClienteTipo = idClienteTipo;
		}

		public String getClienteTipo() {
			return clienteTipo;
		}

		public void setClienteTipo(String clienteTipo) {
			this.clienteTipo = clienteTipo;
		}

		public int getIdClienteGrupo() {
			return idClienteGrupo;
		}

		public void setIdClienteGrupo(int idClienteGrupo) {
			this.idClienteGrupo = idClienteGrupo;
		}

		public String getClienteGrupo() {
			return clienteGrupo;
		}

		public void setClienteGrupo(String clienteGrupo) {
			this.clienteGrupo = clienteGrupo;
		}

		public boolean isTitular() {
			return titular;
		}

		public void setTitular(boolean titular) {
			this.titular = titular;
		}

		public String getTitularSN() {
			return titularSN;
		}

		public void setTitularSN(String titularSN) {
			this.titularSN = titularSN;
		}

		public int getIdNivel0() {
			return idNivel0;
		}

		public void setIdNivel0(int idNivel0) {
			this.idNivel0 = idNivel0;
		}

		public int getIdNivel1() {
			return idNivel1;
		}

		public void setIdNivel1(int idNivel1) {
			this.idNivel1 = idNivel1;
		}

		public int getIdNivel2() {
			return idNivel2;
		}

		public void setIdNivel2(int idNivel2) {
			this.idNivel2 = idNivel2;
		}

		public int getIdNivel3() {
			return idNivel3;
		}

		public void setIdNivel3(int idNivel3) {
			this.idNivel3 = idNivel3;
		}

		public int getIdNivel4() {
			return idNivel4;
		}

		public void setIdNivel4(int idNivel4) {
			this.idNivel4 = idNivel4;
		}

		public String getNombreDelTitular() {
			return nombreDelTitular;
		}

		public void setNombreDelTitular(String nombreDelTitular) {
			this.nombreDelTitular = nombreDelTitular;
		}

		public float getCargo() {
			return cargo;
		}

		public void setCargo(float cargo) {
			this.cargo = cargo;
		}

		public float getAbono() {
			return abono;
		}

		public void setAbono(float abono) {
			this.abono = abono;
		}

		public float getSaldo() {
			return saldo;
		}

		public void setSaldo(float saldo) {
			this.saldo = saldo;
		}

		public int getIdGrupoEmpresarial() {
			return idGrupoEmpresarial;
		}

		public void setIdGrupoEmpresarial(int idGrupoEmpresarial) {
			this.idGrupoEmpresarial = idGrupoEmpresarial;
		}

		public String getCodMoneda() {
			return codMoneda;
		}

		public void setCodMoneda(String codMoneda) {
			this.codMoneda = codMoneda;
		}

		public int getIdVendedorAsignado() {
			return idVendedorAsignado;
		}

		public void setIdVendedorAsignado(int idVendedorAsignado) {
			this.idVendedorAsignado = idVendedorAsignado;
		}

		public String getEmpleado() {
			return empleado;
		}

		public void setEmpleado(String empleado) {
			this.empleado = empleado;
		}

		public String getIniciales() {
			return iniciales;
		}

		public void setIniciales(String iniciales) {
			this.iniciales = iniciales;
		}

		public int getIdAdicionalTipo() {
			return idAdicionalTipo;
		}

		public void setIdAdicionalTipo(int idAdicionalTipo) {
			this.idAdicionalTipo = idAdicionalTipo;
		}

		public String getAdicionalTipo() {
			return adicionalTipo;
		}

		public void setAdicionalTipo(String adicionalTipo) {
			this.adicionalTipo = adicionalTipo;
		}

		public long getMembresia() {
			return membresia;
		}

		public void setMembresia(long membresia) {
			this.membresia = membresia;
		}

		public String getCuotaTipo() {
			return cuotaTipo;
		}

		public void setCuotaTipo(String cuotaTipo) {
			this.cuotaTipo = cuotaTipo;
		}

		public int getIdClienteCategoria() {
			return idClienteCategoria;
		}

		public void setIdClienteCategoria(int idClienteCategoria) {
			this.idClienteCategoria = idClienteCategoria;
		}

		public int getIdMembresiaTipo() {
			return idMembresiaTipo;
		}

		public void setIdMembresiaTipo(int idMembresiaTipo) {
			this.idMembresiaTipo = idMembresiaTipo;
		}

		public String getMembresiaTipo() {
			return membresiaTipo;
		}

		public void setMembresiaTipo(String membresiaTipo) {
			this.membresiaTipo = membresiaTipo;
		}

		public String getClienteCategoria() {
			return clienteCategoria;
		}

		public void setClienteCategoria(String clienteCategoria) {
			this.clienteCategoria = clienteCategoria;
		}

		public int getHuella() {
			return huella;
		}

		public void setHuella(int huella) {
			this.huella = huella;
		}

		public int getIdCapturo() {
			return idCapturo;
		}

		public void setIdCapturo(int idCapturo) {
			this.idCapturo = idCapturo;
		}

		public Date getIdCapturoFecha() {
			return idCapturoFecha;
		}

		public void setIdCapturoFecha(Date idCapturoFecha) {
			this.idCapturoFecha = idCapturoFecha;
		}

		public String getCapturo() {
			return capturo;
		}

		public void setCapturo(String capturo) {
			this.capturo = capturo;
		}

		public boolean isMembresiaEmpresarial() {
			return membresiaEmpresarial;
		}

		public void setMembresiaEmpresarial(boolean membresiaEmpresarial) {
			this.membresiaEmpresarial = membresiaEmpresarial;
		}

		public int getIdEmpresaTitular() {
			return idEmpresaTitular;
		}

		public void setIdEmpresaTitular(int idEmpresaTitular) {
			this.idEmpresaTitular = idEmpresaTitular;
		}

		public int getIdClienteSector() {
			return idClienteSector;
		}

		public void setIdClienteSector(int idClienteSector) {
			this.idClienteSector = idClienteSector;
		}

		public int getIdClienteSubgrupo() {
			return idClienteSubgrupo;
		}

		public void setIdClienteSubgrupo(int idClienteSubgrupo) {
			this.idClienteSubgrupo = idClienteSubgrupo;
		}

		public int getIdClienteGiro() {
			return idClienteGiro;
		}

		public void setIdClienteGiro(int idClienteGiro) {
			this.idClienteGiro = idClienteGiro;
		}

		public String getClienteSector() {
			return clienteSector;
		}

		public void setClienteSector(String clienteSector) {
			this.clienteSector = clienteSector;
		}

		public String getClienteSubgrupo() {
			return clienteSubgrupo;
		}

		public void setClienteSubgrupo(String clienteSubgrupo) {
			this.clienteSubgrupo = clienteSubgrupo;
		}

		public String getClienteGiro() {
			return clienteGiro;
		}

		public void setClienteGiro(String clienteGiro) {
			this.clienteGiro = clienteGiro;
		}

		public int getIdEstatusCobranza() {
			return idEstatusCobranza;
		}

		public void setIdEstatusCobranza(int idEstatusCobranza) {
			this.idEstatusCobranza = idEstatusCobranza;
		}

		public int getIdEstatuMembresia() {
			return idEstatuMembresia;
		}

		public void setIdEstatuMembresia(int idEstatuMembresia) {
			this.idEstatuMembresia = idEstatuMembresia;
		}

		public String getEstatusCobranza() {
			return estatusCobranza;
		}

		public void setEstatusCobranza(String estatusCobranza) {
			this.estatusCobranza = estatusCobranza;
		}

		public String getEstatusMembresia() {
			return estatusMembresia;
		}

		public void setEstatusMembresia(String estatusMembresia) {
			this.estatusMembresia = estatusMembresia;
		}

		public Date getFechaNacimiento() {
			return fechaNacimiento;
		}

		public void setFechaNacimiento(Date fechaNacimiento) {
			this.fechaNacimiento = fechaNacimiento;
		}

		public int getMesNacimiento() {
			return mesNacimiento;
		}

		public void setMesNacimiento(int mesNacimiento) {
			this.mesNacimiento = mesNacimiento;
		}

		public int getDiaNacimiento() {
			return diaNacimiento;
		}

		public void setDiaNacimiento(int diaNacimiento) {
			this.diaNacimiento = diaNacimiento;
		}

		public int getAnioNacimiento() {
			return AnioNacimiento;
		}

		public void setAnioNacimiento(int anioNacimiento) {
			AnioNacimiento = anioNacimiento;
		}

		public int getEdad() {
			return edad;
		}

		public void setEdad(int edad) {
			this.edad = edad;
		}

		public int getIdSexo() {
			return idSexo;
		}

		public void setIdSexo(int idSexo) {
			this.idSexo = idSexo;
		}

		public int getIdEstadoCivil() {
			return idEstadoCivil;
		}

		public void setIdEstadoCivil(int idEstadoCivil) {
			this.idEstadoCivil = idEstadoCivil;
		}

		public String getEstadoCivil() {
			return estadoCivil;
		}

		public void setEstadoCivil(String estadoCivil) {
			this.estadoCivil = estadoCivil;
		}

		public String getSexo() {
			return sexo;
		}

		public void setSexo(String sexo) {
			this.sexo = sexo;
		}

		public String getMembresiaAsociada() {
			return membresiaAsociada;
		}

		public void setMembresiaAsociada(String membresiaAsociada) {
			this.membresiaAsociada = membresiaAsociada;
		}

		public Date getInicioDeActividades() {
			return inicioDeActividades;
		}

		public void setInicioDeActividades(Date inicioDeActividades) {
			this.inicioDeActividades = inicioDeActividades;
		}

		public int getIdEstatusAccceso() {
			return idEstatusAccceso;
		}

		public void setIdEstatusAccceso(int idEstatusAccceso) {
			this.idEstatusAccceso = idEstatusAccceso;
		}

		public String getEstatusAcceso() {
			return estatusAcceso;
		}

		public void setEstatusAcceso(String estatusAcceso) {
			this.estatusAcceso = estatusAcceso;
		}

		public String getEmpresa() {
			return Empresa;
		}

		public void setEmpresa(String empresa) {
			Empresa = empresa;
		}

		public String getNivel1() {
			return nivel1;
		}

		public void setNivel1(String nivel1) {
			this.nivel1 = nivel1;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getTelefonoCasa() {
			return telefonoCasa;
		}

		public void setTelefonoCasa(String telefonoCasa) {
			this.telefonoCasa = telefonoCasa;
		}

		public String getTelefonoMovil() {
			return telefonoMovil;
		}

		public void setTelefonoMovil(String telefonoMovil) {
			this.telefonoMovil = telefonoMovil;
		}

		public String getTelefonoOficina() {
			return telefonoOficina;
		}

		public void setTelefonoOficina(String telefonoOficina) {
			this.telefonoOficina = telefonoOficina;
		}

		@Override
		public String toString() {
			return "Reporte [idCliente=" + idCliente + ", cliente=" + cliente + ", idEmpresa=" + idEmpresa
					+ ", idContacto=" + idContacto + ", contacto=" + contacto + ", razonSocial=" + razonSocial
					+ ", deudor=" + deudor + ", idTitular=" + idTitular + ", idEstatus=" + idEstatus + ", estatus="
					+ estatus + ", idClienteTipo=" + idClienteTipo + ", clienteTipo=" + clienteTipo
					+ ", idClienteGrupo=" + idClienteGrupo + ", clienteGrupo=" + clienteGrupo + ", titular=" + titular
					+ ", titularSN=" + titularSN + ", idNivel0=" + idNivel0 + ", idNivel1=" + idNivel1 + ", idNivel2="
					+ idNivel2 + ", idNivel3=" + idNivel3 + ", idNivel4=" + idNivel4 + ", nombreDelTitular="
					+ nombreDelTitular + ", cargo=" + cargo + ", abono=" + abono + ", saldo=" + saldo
					+ ", idGrupoEmpresarial=" + idGrupoEmpresarial + ", codMoneda=" + codMoneda
					+ ", idVendedorAsignado=" + idVendedorAsignado + ", empleado=" + empleado + ", iniciales="
					+ iniciales + ", idAdicionalTipo=" + idAdicionalTipo + ", adicionalTipo=" + adicionalTipo
					+ ", membresia=" + membresia + ", cuotaTipo=" + cuotaTipo + ", idClienteCategoria="
					+ idClienteCategoria + ", idMembresiaTipo=" + idMembresiaTipo + ", membresiaTipo=" + membresiaTipo
					+ ", clienteCategoria=" + clienteCategoria + ", huella=" + huella + ", idCapturo=" + idCapturo
					+ ", idCapturoFecha=" + idCapturoFecha + ", capturo=" + capturo + ", membresiaEmpresarial="
					+ membresiaEmpresarial + ", idEmpresaTitular=" + idEmpresaTitular + ", idClienteSector="
					+ idClienteSector + ", idClienteSubgrupo=" + idClienteSubgrupo + ", idClienteGiro=" + idClienteGiro
					+ ", clienteSector=" + clienteSector + ", clienteSubgrupo=" + clienteSubgrupo + ", clienteGiro="
					+ clienteGiro + ", idEstatusCobranza=" + idEstatusCobranza + ", idEstatuMembresia="
					+ idEstatuMembresia + ", estatusCobranza=" + estatusCobranza + ", estatusMembresia="
					+ estatusMembresia + ", fechaNacimiento=" + fechaNacimiento + ", mesNacimiento=" + mesNacimiento
					+ ", diaNacimiento=" + diaNacimiento + ", AnioNacimiento=" + AnioNacimiento + ", edad=" + edad
					+ ", idSexo=" + idSexo + ", idEstadoCivil=" + idEstadoCivil + ", estadoCivil=" + estadoCivil
					+ ", sexo=" + sexo + ", membresiaAsociada=" + membresiaAsociada + ", inicioDeActividades="
					+ inicioDeActividades + ", idEstatusAccceso=" + idEstatusAccceso + ", estatusAcceso="
					+ estatusAcceso + ", Empresa=" + Empresa + ", nivel1=" + nivel1 + ", email=" + email
					+ ", telefonoCasa=" + telefonoCasa + ", telefonoMovil=" + telefonoMovil + ", telefonoOficina="
					+ telefonoOficina + "]";
		}
		
				

}
