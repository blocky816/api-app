/*
 * Esta clase PagoDAO.java crearemos los métodos que utilizará la clase PagoDAOimpl, 
 * para conectarse con nuestra base de datos.
 * @autor: 	Daniel García Velasco 
 * 			Abimael Rueda Galindo
 * @version: 1
 * 12/07/2021
 * */
package com.tutorial.crud.aopDao;

//Librerías
import java.util.List;

import com.tutorial.crud.entity.Pago;

//Los metodos creado en el ...DAOImpl.java
public interface PagoDAO 
{
	public List<Pago> findAll();

    public Pago findById(int id);

    public void save(Pago pago);
}
