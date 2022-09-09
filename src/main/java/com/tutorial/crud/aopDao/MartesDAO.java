/*
 * Esta clase MartesDAO.java crearemos los métodos que utilizará la clase MartesDAOimpl, 
 * para conectarse con nuestra base de datos.
 * @autor: 	Daniel García Velasco 
 * 			Abimael Rueda Galindo
 * @version: 1
 * 12/07/2021
 * */
package com.tutorial.crud.aopDao;

//Librerías
import java.util.List;

import com.tutorial.crud.entity.Martes;

//Los metodos creado en el ...DAOImpl.java
public interface MartesDAO {
	public List<Martes> findAll();

    public Martes findById(int id);

    public void save(Martes objeto);
}
