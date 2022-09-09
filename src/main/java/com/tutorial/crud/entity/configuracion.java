package com.tutorial.crud.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class configuracion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String serviceName;
	private String endpointAlpha;
	
	public configuracion() {
		super();
	}

	public configuracion(int id, String serviceName, String endpointAlpha) {
		super();
		this.id = id;
		this.serviceName = serviceName;
		this.endpointAlpha = endpointAlpha;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getServicenNme() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getEndpointAlpha() {
		return endpointAlpha;
	}

	public void setEndpointAlpha(String endpointAlpha) {
		this.endpointAlpha = endpointAlpha;
	}
}
