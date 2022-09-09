/* 
 * El servicio será el interemediario entre el DAO y 
 * el controlador(La clase que gestionará las peticiones de la API que veremos más adelante).
 * La interfaz de service tendría esta estructura MovimientosService.java:
 *	@autor: Daniel García Velasco 
 * 			Abimael Rueda Galindo
 *	@version: 1
 *12/07/2021
*/
package com.tutorial.crud.service;
//Librerías:
import java.util.List;

import com.tutorial.crud.entity.Movimientos;

//Interfaz en el que manda a llamar los métodos creados en MovimientosServiceImpl.java
public interface MovimientosService 
{
	public List<Movimientos> findAll();
    public List<Movimientos> findByIdCliente(int id);
    public Movimientos findById(int id);
    public void save(Movimientos movimientos);
}
