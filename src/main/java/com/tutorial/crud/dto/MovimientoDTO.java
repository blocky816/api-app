package com.tutorial.crud.dto;

public class MovimientoDTO implements Comparable<MovimientoDTO>{
	 private String cliente;
	 private float debito;
	 private long idclienteMovimiento;
	 private String concepto;
	 private String folio;
	 private long idordenDeVentaDetalle;
	 private float credito;
	 private String fechaDeAplicacion;
	 private float saldo;
	 private int idcliente;
	 private long idordenDeVenta;


	 // Getter Methods 

	 public String getCliente() {
	  return cliente;
	 }

	 public float getDebito() {
	  return debito;
	 }

	 public long getIdclienteMovimiento() {
	  return idclienteMovimiento;
	 }

	 public String getConcepto() {
	  return concepto;
	 }

	 public String getFolio() {
	  return folio;
	 }

	 public long getIdordenDeVentaDetalle() {
	  return idordenDeVentaDetalle;
	 }

	 public float getCredito() {
	  return credito;
	 }

	 public String getFechaDeAplicacion() {
	  return fechaDeAplicacion;
	 }

	 public float getSaldo() {
	  return saldo;
	 }

	 public long getIdcliente() {
	  return idcliente;
	 }

	 public long getIdordenDeVenta() {
	  return idordenDeVenta;
	 }

	 // Setter Methods 

	 public void setCliente(String cliente) {
	  this.cliente = cliente;
	 }

	 public void setDebito(float debito) {
	  this.debito = debito;
	 }

	 

	 public void setConcepto(String concepto) {
	  this.concepto = concepto;
	 }

	 public void setFolio(String folio) {
	  this.folio = folio;
	 }

	 public void setCredito(float credito) {
	  this.credito = credito;
	 }

	 public void setFechaDeAplicacion(String fechaDeAplicacion) {
	  this.fechaDeAplicacion = fechaDeAplicacion;
	 }

	 public void setSaldo(float saldo) {
	  this.saldo = saldo;
	 }

	 

	@Override
	public String toString() {
		return "MovimientoDTO [cliente=" + cliente + ", debito=" + debito + ", idclienteMovimiento="
				+ idclienteMovimiento + ", concepto=" + concepto + ", folio=" + folio + ", idordenDeVentaDetalle="
				+ idordenDeVentaDetalle + ", credito=" + credito + ", fechaDeAplicacion=" + fechaDeAplicacion
				+ ", saldo=" + saldo + ", idcliente=" + idcliente + ", idordenDeVenta=" + idordenDeVenta + "]";
	}
	/*@Override
    public int compareTo( MovimientoDTO o ) {
        long byAge = Long.compare(this.getIdordenDeVentaDetalle(), o.getIdordenDeVentaDetalle());
        if ( byAge != 0 ) {
            return (int) -byAge;
        }
		return idcliente;
        
    }*/
	
	public int compareTo( MovimientoDTO o ) {
		String a=this.getFechaDeAplicacion();
        String b=o.getFechaDeAplicacion();
        return -a.compareTo(b);
        
    }

	public void setIdclienteMovimiento(long idclienteMovimiento) {
		this.idclienteMovimiento = idclienteMovimiento;
	}

	public void setIdordenDeVentaDetalle(long idordenDeVentaDetalle) {
		this.idordenDeVentaDetalle = idordenDeVentaDetalle;
	}

	public void setIdcliente(int idcliente) {
		this.idcliente = idcliente;
	}

	public void setIdordenDeVenta(long idordenDeVenta) {
		this.idordenDeVenta = idordenDeVenta;
	}
	}