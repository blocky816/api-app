package com.tutorial.crud.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Receiver {
	@JsonProperty
    public String Rfc;
	@JsonProperty
    public String Name;
	@JsonProperty
    public String CfdiUse;
	

	@JsonProperty
	private String FiscalRegime;
	@JsonProperty
	private String TaxZipCode;
	
    @JsonIgnore	
	public String getRfc() {
		return Rfc;
	}
	

    @JsonIgnore	
	public void setRfc(String rfc) {
		this.Rfc = rfc;
	}
    @JsonIgnore	
	public String getName() {
		return Name;
	}
    @JsonIgnore	
	public void setName(String name) {
		this.Name = name;
	}
    @JsonIgnore	
	public String getCfdiUse() {
		return CfdiUse;
	}
    @JsonIgnore	
	public void setCfdiUse(String cfdiUse) {
		this.CfdiUse = cfdiUse;
	}


    @JsonIgnore	
	public String getFiscalRegime() {
		return FiscalRegime;
	}


    @JsonIgnore	
	public void setFiscalRegime(String fiscalRegime) {
		FiscalRegime = fiscalRegime;
	}


    @JsonIgnore	
	public String getTaxZipCode() {
		return TaxZipCode;
	}


    @JsonIgnore	
	public void setTaxZipCode(String taxZipCode) {
		TaxZipCode = taxZipCode;
	}


	@Override
	public String toString() {
		return "Receiver [Rfc=" + Rfc + ", Name=" + Name + ", CfdiUse=" + CfdiUse + ", FiscalRegime=" + FiscalRegime
				+ ", TaxZipCode=" + TaxZipCode + "]";
	}

    
    
}