package com.tutorial.crud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CatalogoPrecioDTO {
    @JsonProperty("id")
    private int id;

    @JsonProperty("min_quantity")
    private double minQuantity;

    @JsonProperty("fixed_price")
    private double fixedPrice;

    // Constructor, getters, and setters
    public CatalogoPrecioDTO() {}

    public CatalogoPrecioDTO(Integer id, Double minQuantity, Double fixedPrice) {
        this.id = id;
        this.minQuantity = minQuantity;
        this.fixedPrice = fixedPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(Double minQuantity) {
        this.minQuantity = minQuantity;
    }

    public Double getFixedPrice() {
        return fixedPrice;
    }

    public void setFixedPrice(Double fixedPrice) {
        this.fixedPrice = fixedPrice;
    }
}
