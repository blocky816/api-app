package com.tutorial.crud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProductoDTO {

    @JsonProperty("id_lista_precio")
    private int idListaPrecio;

    @JsonProperty("id_product_tmpl")
    private int idProductTmpl;

    @JsonProperty("product_id")
    private int productId;

    @JsonProperty("name_product_tmpl")
    private String nameProductTmpl;

    @JsonProperty("display_name_product_tmpl")
    private String displayNameProductTmpl;
    @JsonProperty("description_producto")
    private String descriptionProducto;

    @JsonProperty("image_1920_product_tmpl")
    private String image1920ProductTmpl;

    @JsonProperty("precio_default")
    private double precioDefault;

    @JsonProperty("catalogo_precios")
    private List<CatalogoPrecioDTO> catalogoPrecios;

    // Constructor, getters, and setters
    public ProductoDTO() {}

    public ProductoDTO(Integer idListaPrecio, Integer idProductTmpl, Integer productId, String nameProductTmpl,
                    String displayNameProductTmpl, String descriptionProducto, String image1920ProductTmpl, Double precioDefault,
                    List<CatalogoPrecioDTO> catalogoPrecios) {
        this.idListaPrecio = idListaPrecio;
        this.idProductTmpl = idProductTmpl;
        this.productId = productId;
        this.nameProductTmpl = nameProductTmpl;
        this.displayNameProductTmpl = displayNameProductTmpl;
        this.descriptionProducto = descriptionProducto;
        this.image1920ProductTmpl = image1920ProductTmpl;
        this.precioDefault = precioDefault;
        this.catalogoPrecios = catalogoPrecios;
    }

    public Integer getIdListaPrecio() {
        return idListaPrecio;
    }

    public void setIdListaPrecio(Integer idListaPrecio) {
        this.idListaPrecio = idListaPrecio;
    }

    public Integer getIdProductTmpl() {
        return idProductTmpl;
    }

    public void setIdProductTmpl(Integer idProductTmpl) {
        this.idProductTmpl = idProductTmpl;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getNameProductTmpl() {
        return nameProductTmpl;
    }

    public void setNameProductTmpl(String nameProductTmpl) {
        this.nameProductTmpl = nameProductTmpl;
    }

    public String getDisplayNameProductTmpl() {
        return displayNameProductTmpl;
    }

    public void setDisplayNameProductTmpl(String displayNameProductTmpl) {
        this.displayNameProductTmpl = displayNameProductTmpl;
    }

    public String getDescriptionProducto() {
        return descriptionProducto;
    }

    public void setDescriptionProducto(String descriptionProducto) {
        this.descriptionProducto = descriptionProducto;
    }

    public String getImage1920ProductTmpl() {
        return image1920ProductTmpl;
    }

    public void setImage1920ProductTmpl(String image1920ProductTmpl) {
        this.image1920ProductTmpl = image1920ProductTmpl;
    }

    public Double getPrecioDefault() {
        return precioDefault;
    }

    public void setPrecioDefault(Double precioDefault) {
        this.precioDefault = precioDefault;
    }

    public List<CatalogoPrecioDTO> getCatalogoPrecios() {
        return catalogoPrecios;
    }

    public void setCatalogoPrecios(List<CatalogoPrecioDTO> catalogoPrecios) {
        this.catalogoPrecios = catalogoPrecios;
    }
}
