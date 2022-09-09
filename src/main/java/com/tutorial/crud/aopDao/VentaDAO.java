/*
 * Esta clase VentaDAO.java crearemos los métodos que utilizará la clase VentaDAOimpl, 
 * para conectarse con nuestra base de datos.
 * @autor: 	Daniel García Velasco 
 * 			Abimael Rueda Galindo
 * @version: 1
 * 12/07/2021
 * */
package com.tutorial.crud.aopDao;

//Librería
import java.util.List;

import com.tutorial.crud.entity.Venta;

//Los metodos creado en el ...DAOImpl.java
public interface VentaDAO 
{
	public List<Venta> findAll();
    public Venta findById(int id);
    public void save(Venta venta);
}
