/*
 * Esta clase LunesDAO.java crearemos los métodos que utilizará la clase LunesDAOimpl, 
 * para conectarse con nuestra base de datos.
 * @autor: 	Daniel García Velasco 
 * 			Abimael Rueda Galindo
 * @version: 1
 * 12/07/2021
 * */
package com.tutorial.crud.aopDao;

//Librerías
import java.util.List;

import com.tutorial.crud.entity.Lunes;

//Los metodos creado en el ...DAOImpl.java
public interface LunesDAO {
	public List<Lunes> findAll();

    public Lunes findById(int id);

    public void save(Lunes lunes);
}
