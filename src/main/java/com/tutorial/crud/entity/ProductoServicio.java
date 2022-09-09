package com.tutorial.crud.entity;


public class ProductoServicio {
    private int idProdServ;
    
    private String clave;
    
    private String concepto;
    
    private float total;
    
    private String monedaTipo;
    
    private String codigo;
    
    private String nivel1;

	public int getIdProdServ() {
		return idProdServ;
	}

	public void setIdProdServ(int idProdServ) {
		this.idProdServ = idProdServ;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public String getMonedaTipo() {
		return monedaTipo;
	}

	public void setMonedaTipo(String monedaTipo) {
		this.monedaTipo = monedaTipo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNivel1() {
		return nivel1;
	}

	public void setNivel1(String nivel1) {
		this.nivel1 = nivel1;
	}

	@Override
	public String toString() {
		return "ProductoServicio [idProdServ=" + idProdServ + ", clave=" + clave + ", concepto=" + concepto + ", total="
				+ total + ", monedaTipo=" + monedaTipo + ", codigo=" + codigo + ", nivel1=" + nivel1 + "]";
	}
    

  
}
