package com.tutorial.crud.dto;

import javax.validation.constraints.NotBlank;

public class configuracionDTO {
	@NotBlank
	private String serviceName;
	@NotBlank
	private String endpointAlpha;
	
	public configuracionDTO() {
		super();
	}

	public configuracionDTO(@NotBlank String serviceName, @NotBlank String endpointAlpha) {
		super();
		this.serviceName = serviceName;
		this.endpointAlpha = endpointAlpha;
	}
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getEnpoint_alpha() {
		return endpointAlpha;
	}

	public void setEnpointAlpha(String endpointAlpha) {
		this.endpointAlpha = endpointAlpha;
	}
}
