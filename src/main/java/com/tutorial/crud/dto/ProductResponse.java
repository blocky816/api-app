package com.tutorial.crud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProductResponse {
    @JsonProperty("id")
    private int id;
    @JsonProperty("product_id")
    private List<Object> product_id; // Cambiar a un tipo más específico si es necesario
    @JsonProperty("price_unit")
    private double price_unit;
    @JsonProperty("payment_id")
    private boolean payment_id;
    @JsonProperty("quantity")
    private double quantity;
    @JsonProperty("move_id")
    private List<Object> move_id; // Cambiar a un tipo más específico si es necesario

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Object> getProduct_id() {
        return product_id;
    }

    public void setProduct_id(List<Object> product_id) {
        this.product_id = product_id;
    }

    public double getPrice_unit() {
        return price_unit;
    }

    public void setPrice_unit(double price_unit) {
        this.price_unit = price_unit;
    }

    public boolean isPayment_id() {
        return payment_id;
    }

    public void setPayment_id(boolean payment_id) {
        this.payment_id = payment_id;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public List<Object> getMove_id() {
        return move_id;
    }

    public void setMove_id(List<Object> move_id) {
        this.move_id = move_id;
    }

    @Override
    public String toString() {
        return "ProductResponse{" +
                "id=" + id +
                ", product_id=" + product_id +
                ", price_unit=" + price_unit +
                ", payment_id=" + payment_id +
                ", quantity=" + quantity +
                ", move_id=" + move_id +
                '}';
    }
}
