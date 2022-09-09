/*
 * Esta clase MovimientosDAO.java crearemos los métodos que utilizará la clase MovimientosDAOimpl, 
 * para conectarse con nuestra base de datos.
 * @autor: 	Daniel García Velasco 
 * 			Abimael Rueda Galindo
 * @version: 1
 * 12/07/2021
 * */
package com.tutorial.crud.aopDao;

//Librerías
import java.util.List;

import com.tutorial.crud.entity.Movimientos;


//Los metodos creado en el ...DAOImpl.java
public interface MovimientosDAO 
{
	public List<Movimientos> findAll();
    public List<Movimientos> findByIdCliente(int idCliente);
    public Movimientos findById(int id );
    public void save(Movimientos movimientos);	

}
