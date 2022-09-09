package com.tutorial.crud.dto;

import java.io.Serializable;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {
    @JsonProperty
    public String ProductCode;
    @JsonProperty
    public String IdentificationNumber;
    @JsonProperty
    public String Description;
    @JsonProperty
    public String UnitCode;
    @JsonProperty
    public double UnitPrice;
    @JsonProperty
    public double Quantity;
    @JsonProperty
    public double Subtotal;
    @JsonProperty
    public ArrayList<Tax> Taxes;
    @JsonProperty
    public double Total;
    @JsonProperty
    public double Discount;
    @JsonProperty
    private String TaxObject;
    
	@JsonIgnore	
    public String getTaxObject() {
		return TaxObject;
	}
	@JsonIgnore	
	public void setTaxObject(String taxObject) {
		TaxObject = taxObject;
	}
	@JsonIgnore	
	public String getProductCode() {
		return ProductCode;
	}
    @JsonIgnore	
	public void setProductCode(String productCode) {
		this.ProductCode = productCode;
	}
    @JsonIgnore	
	public String getIdentificationNumber() {
		return IdentificationNumber;
	}
    @JsonIgnore	
	public void setIdentificationNumber(String identificationNumber) {
		this.IdentificationNumber = identificationNumber;
	}
    @JsonIgnore	
	public String getDescription() {
		return Description;
	}
    @JsonIgnore	
	public void setDescription(String description) {
		this.Description = description;
	}
    @JsonIgnore	
	public String getUnitCode() {
		return UnitCode;
	}
    @JsonIgnore	
	public void setUnitCode(String unitCode) {
		this.UnitCode = unitCode;
	}
    @JsonIgnore	
	public double getUnitPrice() {
		return UnitPrice;
	}
    @JsonIgnore	
	public void setUnitPrice(double unitPrice) {
		this.UnitPrice = unitPrice;
	}
    @JsonIgnore	
	public double getQuantity() {
		return Quantity;
	}
    @JsonIgnore	
	public void setQuantity(double quantity) {
		this.Quantity = quantity;
	}
    @JsonIgnore	
	public double getSubtotal() {
		return Subtotal;
	}
    @JsonIgnore	
	public void setSubtotal(double subtotal) {
		this.Subtotal = subtotal;
	}
    @JsonIgnore	
	public ArrayList<Tax> getTaxes() {
		return Taxes;
	}
    @JsonIgnore	
	public void setTaxes(ArrayList<Tax> taxes) {
		this.Taxes = taxes;
	}
    @JsonIgnore	
	public double getTotal() {
		return Total;
	}
    @JsonIgnore	
	public void setTotal(double total) {
		this.Total = total;
	}
    @JsonIgnore	
	public double getDiscount() {
		return Discount;
	}
    @JsonIgnore	
	public void setDiscount(double discount) {
		this.Discount = discount;
	}
	@Override
	public String toString() {
		return "Item [ProductCode=" + ProductCode + ", IdentificationNumber=" + IdentificationNumber + ", Description="
				+ Description + ", UnitCode=" + UnitCode + ", UnitPrice=" + UnitPrice + ", Quantity=" + Quantity
				+ ", Subtotal=" + Subtotal + ", Taxes=" + Taxes + ", Total=" + Total + ", Discount=" + Discount + "]";
	}
    
    
}
