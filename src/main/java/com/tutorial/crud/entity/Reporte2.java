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

public class Reporte2 {
		
		private long idMembresiaPorPeriodo;
		
		private int idCliente ;

		 private int idPeriodo ;
		
		private int idPeriodoMes ;
		
		private int idEstatus ;
		
		 private String cliente ;
		
		 private int idMembresiaTipo ;
		
		 private boolean titular;
		
		private int idTitular ;
		
		private int idNivel0;
		
		private int idNivel1 ;

		private int idClienteSector ;
		
		private int idClienteGrupo ;

		private int idClienteSubgrupo ;
		
		private int idClienteGiro ;
		
		private int idClienteCategoria ;
		
		private long membresia ;
		
		private int idEmpresaTitular ;

		private int idEstatusCobranza ;
		
		private int idEstatusMembresia ;

		private int idEstatusAcceso ;
		
		private Date inicioDeActividades;		

		private String observaciones ;

		private int periodo ;

		private int idMes ;
		
		private String mes ;
		
		private String nombreComercial ;
		
		private String nivel1 ;
		
		private String titularAdicional ;
		
		private String membresiaTipo;
		
		private String estatus;
		
		private String estatusCobranza ;
		
		private String estatusAcceso ;
		
		private String estatusMembresia ;
		
		private String clienteSector ;
		
		private String clienteGrupo ;
		
		private String clienteSubgrupo ;
		
		private String clienteGiro ;
		
		private int idClienteTipo ;
		
		private String clienteCategoria ;
		
		private String clienteTipo ;
		
		private String membresiaAsociada;

		public long getIdMembresiaPorPeriodo() {
			return idMembresiaPorPeriodo;
		}

		public void setIdMembresiaPorPeriodo(long idMembresiaPorPeriodo) {
			this.idMembresiaPorPeriodo = idMembresiaPorPeriodo;
		}

		public int getIdCliente() {
			return idCliente;
		}

		public void setIdCliente(int idCliente) {
			this.idCliente = idCliente;
		}

		public int getIdPeriodo() {
			return idPeriodo;
		}

		public void setIdPeriodo(int idPeriodo) {
			this.idPeriodo = idPeriodo;
		}

		public int getIdPeriodoMes() {
			return idPeriodoMes;
		}

		public void setIdPeriodoMes(int idPeriodoMes) {
			this.idPeriodoMes = idPeriodoMes;
		}

		public int getIdEstatus() {
			return idEstatus;
		}

		public void setIdEstatus(int idEstatus) {
			this.idEstatus = idEstatus;
		}

		public String getCliente() {
			return cliente;
		}

		public void setCliente(String cliente) {
			this.cliente = cliente;
		}

		public int getIdMembresiaTipo() {
			return idMembresiaTipo;
		}

		public void setIdMembresiaTipo(int idMembresiaTipo) {
			this.idMembresiaTipo = idMembresiaTipo;
		}

		public boolean isTitular() {
			return titular;
		}

		public void setTitular(boolean titular) {
			this.titular = titular;
		}

		public int getIdTitular() {
			return idTitular;
		}

		public void setIdTitular(int idTitular) {
			this.idTitular = idTitular;
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

		public int getIdClienteSector() {
			return idClienteSector;
		}

		public void setIdClienteSector(int idClienteSector) {
			this.idClienteSector = idClienteSector;
		}

		public int getIdClienteGrupo() {
			return idClienteGrupo;
		}

		public void setIdClienteGrupo(int idClienteGrupo) {
			this.idClienteGrupo = idClienteGrupo;
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

		public int getIdClienteCategoria() {
			return idClienteCategoria;
		}

		public void setIdClienteCategoria(int idClienteCategoria) {
			this.idClienteCategoria = idClienteCategoria;
		}

		public long getMembresia() {
			return membresia;
		}

		public void setMembresia(long membresia) {
			this.membresia = membresia;
		}

		public int getIdEmpresaTitular() {
			return idEmpresaTitular;
		}

		public void setIdEmpresaTitular(int idEmpresaTitular) {
			this.idEmpresaTitular = idEmpresaTitular;
		}

		public int getIdEstatusCobranza() {
			return idEstatusCobranza;
		}

		public void setIdEstatusCobranza(int idEstatusCobranza) {
			this.idEstatusCobranza = idEstatusCobranza;
		}

		public int getIdEstatusMembresia() {
			return idEstatusMembresia;
		}

		public void setIdEstatusMembresia(int idEstatusMembresia) {
			this.idEstatusMembresia = idEstatusMembresia;
		}

		public int getIdEstatusAcceso() {
			return idEstatusAcceso;
		}

		public void setIdEstatusAcceso(int idEstatusAcceso) {
			this.idEstatusAcceso = idEstatusAcceso;
		}

		public Date getInicioDeActividades() {
			return inicioDeActividades;
		}

		public void setInicioDeActividades(Date inicioDeActividades) {
			this.inicioDeActividades = inicioDeActividades;
		}

		public String getObservaciones() {
			return observaciones;
		}

		public void setObservaciones(String observaciones) {
			this.observaciones = observaciones;
		}

		public int getPeriodo() {
			return periodo;
		}

		public void setPeriodo(int periodo) {
			this.periodo = periodo;
		}

		public int getIdMes() {
			return idMes;
		}

		public void setIdMes(int idMes) {
			this.idMes = idMes;
		}

		public String getMes() {
			return mes;
		}

		public void setMes(String mes) {
			this.mes = mes;
		}

		public String getNombreComercial() {
			return nombreComercial;
		}

		public void setNombreComercial(String nombreComercial) {
			this.nombreComercial = nombreComercial;
		}

		public String getNivel1() {
			return nivel1;
		}

		public void setNivel1(String nivel1) {
			this.nivel1 = nivel1;
		}

		public String getTitularAdicional() {
			return titularAdicional;
		}

		public void setTitularAdicional(String titularAdicional) {
			this.titularAdicional = titularAdicional;
		}

		public String getMembresiaTipo() {
			return membresiaTipo;
		}

		public void setMembresiaTipo(String membresiaTipo) {
			this.membresiaTipo = membresiaTipo;
		}

		public String getEstatus() {
			return estatus;
		}

		public void setEstatus(String estatus) {
			this.estatus = estatus;
		}

		public String getEstatusCobranza() {
			return estatusCobranza;
		}

		public void setEstatusCobranza(String estatusCobranza) {
			this.estatusCobranza = estatusCobranza;
		}

		public String getEstatusAcceso() {
			return estatusAcceso;
		}

		public void setEstatusAcceso(String estatusAcceso) {
			this.estatusAcceso = estatusAcceso;
		}

		public String getEstatusMembresia() {
			return estatusMembresia;
		}

		public void setEstatusMembresia(String estatusMembresia) {
			this.estatusMembresia = estatusMembresia;
		}

		public String getClienteSector() {
			return clienteSector;
		}

		public void setClienteSector(String clienteSector) {
			this.clienteSector = clienteSector;
		}

		public String getClienteGrupo() {
			return clienteGrupo;
		}

		public void setClienteGrupo(String clienteGrupo) {
			this.clienteGrupo = clienteGrupo;
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

		public int getIdClienteTipo() {
			return idClienteTipo;
		}

		public void setIdClienteTipo(int idClienteTipo) {
			this.idClienteTipo = idClienteTipo;
		}

		public String getClienteCategoria() {
			return clienteCategoria;
		}

		public void setClienteCategoria(String clienteCategoria) {
			this.clienteCategoria = clienteCategoria;
		}

		public String getClienteTipo() {
			return clienteTipo;
		}

		public void setClienteTipo(String clienteTipo) {
			this.clienteTipo = clienteTipo;
		}

		public String getMembresiaAsociada() {
			return membresiaAsociada;
		}

		public void setMembresiaAsociada(String membresiaAsociada) {
			this.membresiaAsociada = membresiaAsociada;
		}

		@Override
		public String toString() {
			return "Reporte2 [idMembresiaPorPeriodo=" + idMembresiaPorPeriodo + ", idCliente=" + idCliente
					+ ", idPeriodo=" + idPeriodo + ", idPeriodoMes=" + idPeriodoMes + ", idEstatus=" + idEstatus
					+ ", cliente=" + cliente + ", idMembresiaTipo=" + idMembresiaTipo + ", titular=" + titular
					+ ", idTitular=" + idTitular + ", idNivel0=" + idNivel0 + ", idNivel1=" + idNivel1
					+ ", idClienteSector=" + idClienteSector + ", idClienteGrupo=" + idClienteGrupo
					+ ", idClienteSubgrupo=" + idClienteSubgrupo + ", idClienteGiro=" + idClienteGiro
					+ ", idClienteCategoria=" + idClienteCategoria + ", membresia=" + membresia + ", idEmpresaTitular="
					+ idEmpresaTitular + ", idEstatusCobranza=" + idEstatusCobranza + ", idEstatusMembresia="
					+ idEstatusMembresia + ", idEstatusAcceso=" + idEstatusAcceso + ", inicioDeActividades="
					+ inicioDeActividades + ", observaciones=" + observaciones + ", periodo=" + periodo + ", idMes="
					+ idMes + ", mes=" + mes + ", nombreComercial=" + nombreComercial + ", nivel1=" + nivel1
					+ ", titularAdicional=" + titularAdicional + ", membresiaTipo=" + membresiaTipo + ", estatus="
					+ estatus + ", estatusCobranza=" + estatusCobranza + ", estatusAcceso=" + estatusAcceso
					+ ", estatusMembresia=" + estatusMembresia + ", clienteSector=" + clienteSector + ", clienteGrupo="
					+ clienteGrupo + ", clienteSubgrupo=" + clienteSubgrupo + ", clienteGiro=" + clienteGiro
					+ ", idClienteTipo=" + idClienteTipo + ", clienteCategoria=" + clienteCategoria + ", clienteTipo="
					+ clienteTipo + ", membresiaAsociada=" + membresiaAsociada + "]";
		}
		
		

		
}
