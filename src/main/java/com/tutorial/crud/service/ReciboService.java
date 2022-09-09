/*Como en el paquete anterior tendremos una clase y una interface. 
 * El servicio será el que hará de interemediario entre el DAO y 
 * el controlador(La clase que gestionará las peticiones de la API que veremos más adelante).
 * La interfaz de service tendría esta estructurar ReciboService.java:
 *	@autor: Daniel García Velasco 
 * 			Abimael Rueda Galindo
 *	@version: 1
 *12/07/2021
*/
package com.tutorial.crud.service;

import java.util.List;

import com.tutorial.crud.entity.Recibo;

//Interfaz en el que manda a llamar los métodos creados en ReciboServiceImpl.java
public interface ReciboService 
{
	public List<Recibo> findAll();
    public Recibo findById(String id);
    public void save(Recibo recibo);
}
