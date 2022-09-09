/**implementacion de MovimientosService
 * Le añadiremos la anotación @Service, para indicar que es un servicio y 
 * también de @Autowired para inyectar nuestro DAO y hacer uso de él:
 *	@autor: Daniel García Velasco 
 * 			Abimael Rueda Galindo
 *	@version: 1
 *12/07/2021
*/
package com.tutorial.crud.service;

//Librerías
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tutorial.crud.aopDao.MovimientosDAO;
import com.tutorial.crud.entity.Movimientos;


@Service //marca la clase java que realiza algún servicio 
public class MovimientosServiceImpl implements MovimientosService
{ 
	@Autowired //Inyecta a nuestro DAO y lo utiliza. 
    private MovimientosDAO movimientosDAO;
	
	/**
	 * Encuentra todos los movimientos registrados en la base de datos
	 */
	@Override
	public List<Movimientos> findAll() 
	{
		List<Movimientos> listMovimientos= movimientosDAO.findAll();
        return listMovimientos;
	}
	
	/**
	 * Método en el que se inserta en el parametro id el ID del cliente para encontrar todos los movimientos realizados por el
	 */
	@Override
	public List<Movimientos> findByIdCliente(int id) 
	{
		List<Movimientos> movimientos = movimientosDAO.findByIdCliente(id);
        return movimientos;
	}
	/**
	 * En este metodo se busca de manera individual algun movimiento por medio de su id
	 */
	@Override
	public Movimientos findById(int id) 
	{
		Movimientos movimientos = movimientosDAO.findById(id);
        return movimientos;
	}

	/**
	 * Guarda un movimiento nuevo en la base de datos haciendo uso del dao de esta entidad
	 */
	@Override
	public void save(Movimientos movimientos) 
	{
		movimientosDAO.save(movimientos);
	}
}
