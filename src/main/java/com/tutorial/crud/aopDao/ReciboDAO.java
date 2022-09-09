/*
 * Esta clase ReciboDAO.java crearemos los métodos que utilizará la clase ReciboDAOimpl, 
 * para conectarse con nuestra base de datos.
 * @autor: 	Daniel García Velasco 
 * 			Abimael Rueda Galindo
 * @version: 1
 * 12/07/2021
 * */
package com.tutorial.crud.aopDao;

//Librería
import java.util.List;

import com.tutorial.crud.entity.Recibo;

//Los metodos creado en el ...DAOImpl.java
public interface ReciboDAO 
{
	public List<Recibo> findAll();
    public Recibo findById(String id);
    public void save(Recibo recibo);
}
