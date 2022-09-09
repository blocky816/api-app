/*
 * Esta clase OrdenVentaDAO.java crearemos los métodos que utilizará la clase OrdenVentaDAOimpl, 
 * para conectarse con nuestra base de datos.
 * @autor: 	Daniel García Velasco 
 * 			Abimael Rueda Galindo
 * @version: 1
 * 12/07/2021
 * */
package com.tutorial.crud.aopDao;

//Librerías
import java.util.List;

import com.tutorial.crud.entity.OrdenVenta;

//Los metodos creado en el ...DAOImpl.java
public interface OrdenVentaDAO 
{
	public List<OrdenVenta> findAll();

    public OrdenVenta findById(int id);

    public void save(OrdenVenta club);
}
