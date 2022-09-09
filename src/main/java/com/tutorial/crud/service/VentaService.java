/*Como en el paquete anterior tendremos una clase y una interface. 
 * El servicio será el que hará de interemediario entre el DAO y 
 * el controlador(La clase que gestionará las peticiones de la API que veremos más adelante).
 * La interfaz de service tendría esta estructura VentaService.java:
 *	@autor: Daniel García Velasco 
 * 			Abimael Rueda Galindo
 *	@version: 1
 *12/07/2021
*/
package com.tutorial.crud.service;

import java.util.List;

import com.tutorial.crud.entity.Venta;
//Interfaz en el que manda a llamar los métodos creados en VentaServiceImpl.java
public interface VentaService 
{
	public List<Venta> findAll();
    public Venta findById(int id);
    public void save(Venta venta);
}
