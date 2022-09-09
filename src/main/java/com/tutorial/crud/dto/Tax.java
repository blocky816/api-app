package com.tutorial.crud.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Tax {
    @JsonProperty
    public double Total;
    @JsonProperty
    public String Name;
    @JsonProperty
    public double Base;
    @JsonProperty
    public double Rate;
    @JsonProperty
    public boolean IsRetention;
    

    @JsonIgnore
	public double getTotal() {
		return Total;
	}
    @JsonIgnore
	public void setTotal(double total) {
		this.Total = total;
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
	public double getBase() {
		return Base;
	}
    @JsonIgnore
	public void setBase(double base) {
		this.Base = base;
	}
    @JsonIgnore
	public double getRate() {
		return Rate;
	}
    @JsonIgnore
	public void setRate(double rate) {
		this.Rate = rate;
	}
    @JsonIgnore
	public boolean isRetention() {
		return IsRetention;
	}
    @JsonIgnore
	public void setRetention(boolean isRetention) {
		this.IsRetention = isRetention;
	}
	@Override
	public String toString() {
		return "Tax [Total=" + Total + ", Name=" + Name + ", Base=" + Base + ", Rate=" + Rate + ", IsRetention="
				+ IsRetention + "]";
	}

    
    
    
}