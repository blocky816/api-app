package com.tutorial.crud.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="estado_de_cobranza") //Se utiliza para poner el nombre real de la tabla en la base de datos
public class EstadoDeCobranza {
	
	
	@Id
	@Column(name="id")
	private int id;
	
	@Column(name = "mes")
	private String mes;

	@Column(name = "corriente_titular",columnDefinition = "int default 0")
	private int corrienteTitular=0;	
	
	@Column(name = "corriente_dependiente",columnDefinition = "int default 0")
	private int corrienteDependiente=0;
	
	@Column(name = "etapa1_titular",columnDefinition = "int default 0")
	private int etapa1Titular=0;
	
	@Column(name = "etapa1_dependiente",columnDefinition = "int default 0")
	private int etapa1Dependiente=0;
	
	@Column(name = "etapa2_titular",columnDefinition = "int default 0")
	private int etapa2Titular=0;
	
	@Column(name = "etapa2_dependiente",columnDefinition = "int default 0")
	private int etapa2Dependiente=0;
	
	@Column(name = "etapa3_titular",columnDefinition = "int default 0")
	private int etapa3Titular=0;
	
	@Column(name = "etapa3_dependiente",columnDefinition = "int default 0")
	private int etapa3Dependiente=0;
	
	@Column(name = "baja_titular",columnDefinition = "int default 0")
	private int bajaTitular=0;
	
	@Column(name = "baja_dependiente",columnDefinition = "int default 0")
	private int bajaDependiente=0;
	
	@Column(name = "consolidado_titular",columnDefinition = "int default 0")
	private int consolidadoTitular=0;
	
	@Column(name = "consolidado_dependiente",columnDefinition = "int default 0")
	private int consolidadoDependiente=0;
	
	@Column(name = "club")
	private String club;
	
	@Column(name = "anio")
	private int anio;

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public int getCorrienteTitular() {
		return corrienteTitular;
	}

	public void setCorrienteTitular(int corrienteTitular) {
		this.corrienteTitular = corrienteTitular;
	}

	public int getCorrienteDependiente() {
		return corrienteDependiente;
	}

	public void setCorrienteDependiente(int corrienteDependiente) {
		this.corrienteDependiente = corrienteDependiente;
	}

	public int getEtapa1Titular() {
		return etapa1Titular;
	}

	public void setEtapa1Titular(int etapa1Titular) {
		this.etapa1Titular = etapa1Titular;
	}

	public int getEtapa1Dependiente() {
		return etapa1Dependiente;
	}

	public void setEtapa1Dependiente(int etapa1Dependiente) {
		this.etapa1Dependiente = etapa1Dependiente;
	}

	public int getEtapa2Titular() {
		return etapa2Titular;
	}

	public void setEtapa2Titular(int etapa2Titular) {
		this.etapa2Titular = etapa2Titular;
	}

	public int getEtapa2Dependiente() {
		return etapa2Dependiente;
	}

	public void setEtapa2Dependiente(int etapa2Dependiente) {
		this.etapa2Dependiente = etapa2Dependiente;
	}

	public int getEtapa3Titular() {
		return etapa3Titular;
	}

	public void setEtapa3Titular(int etapa3Titular) {
		this.etapa3Titular = etapa3Titular;
	}

	public int getEtapa3Dependiente() {
		return etapa3Dependiente;
	}

	public void setEtapa3Dependiente(int etapa3Dependiente) {
		this.etapa3Dependiente = etapa3Dependiente;
	}

	
	
	public int getBajaTitular() {
		return bajaTitular;
	}

	public void setEtapa4Titular(int etapa4Titular) {
		this.bajaTitular = etapa4Titular;
	}

	public int getBajaDependiente() {
		return bajaDependiente;
	}

	public void setEtapa4Dependiente(int etapa4Dependiente) {
		this.bajaDependiente = etapa4Dependiente;
	}

	
	
	public int getConsolidadoTitular() {
		return consolidadoTitular;
	}

	public void setConsolidadoTitular(int consolidadoTitular) {
		this.consolidadoTitular = consolidadoTitular;
	}

	public int getConsolidadoDependiente() {
		return consolidadoDependiente;
	}

	public void setConsolidadoDependiente(int consolidadoDependiente) {
		this.consolidadoDependiente = consolidadoDependiente;
	}

	public String getClub() {
		return club;
	}

	public void setClub(String club) {
		this.club = club;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	@Override
	public String toString() {
		return "EstadoDeCobranza [id=" + id + ", mes=" + mes + ", corrienteTitular=" + corrienteTitular
				+ ", corrienteDependiente=" + corrienteDependiente + ", etapa1Titular=" + etapa1Titular
				+ ", etapa1Dependiente=" + etapa1Dependiente + ", etapa2Titular=" + etapa2Titular
				+ ", etapa2Dependiente=" + etapa2Dependiente + ", etapa3Titular=" + etapa3Titular
				+ ", etapa3Dependiente=" + etapa3Dependiente + ", etapa4Titular=" + bajaTitular
				+ ", etapa4Dependiente=" + bajaDependiente + ", consolidadoTitular=" + consolidadoTitular
				+ ", consolidadoDependiente=" + consolidadoDependiente + ", club=" + club + ", anio=" + anio + "]";
	}

	
	
}
