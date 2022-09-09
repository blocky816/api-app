/*
 * Esta clase PaseDAO.java crearemos los métodos que utilizará la clase PaseDAOimpl, 
 * para conectarse con nuestra base de datos.
 * @autor: 	Daniel García Velasco 
 * 			Abimael Rueda Galindo
 * @version: 1
 * 12/07/2021
 * */
package com.tutorial.crud.aopDao;

//Librerias
import java.util.List;

import com.tutorial.crud.entity.Pase;

//Los metodos creado en el ...DAOImpl.java
public interface PaseDAO 
{
	public List<Pase> findAll();
    public Pase findById(int id);
    public void save(Pase pase);
}
