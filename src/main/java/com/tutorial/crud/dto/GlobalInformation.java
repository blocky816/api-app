package com.tutorial.crud.dto;

import java.io.Serializable;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GlobalInformation {
    @JsonProperty
    public String Periodicity;
    @JsonProperty
    public String Months;
    @JsonProperty
    public String Year;
    

	@JsonIgnore	
	public String getPeriodicity() {
		return Periodicity;
	}
	@JsonIgnore	
	public void setPeriodicity(String periodicity) {
		Periodicity = periodicity;
	}
	@JsonIgnore	
	public String getMonths() {
		return Months;
	}
	@JsonIgnore	
	public void setMonths(String months) {
		Months = months;
	}
	@JsonIgnore	
	public String getYear() {
		return Year;
	}
	@JsonIgnore	
	public void setYear(String year) {
		Year = year;
	}
    
    
    
    
    
}
