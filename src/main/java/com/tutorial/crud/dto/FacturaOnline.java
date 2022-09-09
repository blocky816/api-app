package com.tutorial.crud.dto;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FacturaOnline {

    @JsonProperty
    public String Currency;

    @JsonProperty
    public String ExpeditionPlace;

    @JsonProperty
    public String CfdiType;

    @JsonProperty
    public String PaymentForm;
    

    @JsonProperty
    public String Date;
    

    @JsonProperty
    public String Observations;

    @JsonProperty
    public String PaymentMethod;

    @JsonProperty
    public Receiver Receiver;    

    @JsonProperty
    public GlobalInformation GlobalInformation;

    @JsonProperty
    public ArrayList<Item> Items;    



	@JsonIgnore	
	public String getObservations() {
		return Observations;
	}
	@JsonIgnore	
	public void setObservations(String observations) {
		Observations = observations;
	}
	@JsonIgnore	
	public String getDate() {
		return Date;
	}
	@JsonIgnore	
	public void setDate(String date) {
		Date = date;
	}
	@JsonIgnore	
	public String getCurrency() {
		return Currency;
	}
    @JsonIgnore	
	public void setCurrency(String currency) {
		this.Currency = currency;
	}
    @JsonIgnore	
	public String getExpeditionPlace() {
		return ExpeditionPlace;
	}
    @JsonIgnore	
	public void setExpeditionPlace(String expeditionPlace) {
		this.ExpeditionPlace = expeditionPlace;
	}
    @JsonIgnore	
	public String getCfdiType() {
		return CfdiType;
	}
    @JsonIgnore	
	public void setCfdiType(String cfdiType) {
		this.CfdiType = cfdiType;
	}
    @JsonIgnore	
	public String getPaymentForm() {
		return PaymentForm;
	}
    @JsonIgnore	
	public void setPaymentForm(String paymentForm) {
		this.PaymentForm = paymentForm;
	}
    @JsonIgnore	
	public String getPaymentMethod() {
		return PaymentMethod;
	}
    @JsonIgnore	
	public void setPaymentMethod(String paymentMethod) {
		this.PaymentMethod = paymentMethod;
	}
    @JsonIgnore	
	public Receiver getReceiver() {
		return Receiver;
	}
    @JsonIgnore	
	public void setReceiver(Receiver receiver) {
		this.Receiver = receiver;
	}
    @JsonIgnore	
	public ArrayList<Item> getItems() {
		return Items;
	}
    @JsonIgnore	
	public void setItems(ArrayList<Item> items) {
		this.Items = items;
	}
    
	public GlobalInformation getGlobalInformation() {
		return GlobalInformation;
	}
	public void setGlobalInformation(GlobalInformation globalInformation) {
		GlobalInformation = globalInformation;
	}
	@Override
	public String toString() {
		return "FacturaOnline [Currency=" + Currency + ", ExpeditionPlace=" + ExpeditionPlace + ", CfdiType=" + CfdiType
				+ ", PaymentForm=" + PaymentForm + ", PaymentMethod=" + PaymentMethod + ", Receiver=" + Receiver
				+ ", Items=" + Items + "]";
	}
	
    
    
}
