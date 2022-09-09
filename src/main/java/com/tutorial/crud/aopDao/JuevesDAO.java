/*
 * Esta clase JuevesDAO.java crearemos los métodos que utilizará la clase JuevesDAOimpl, 
 * para conectarse con nuestra base de datos.
 * @autor: 	Daniel García Velasco 
 * 			Abimael Rueda Galindo
 * @version: 1
 * 12/07/2021
 * */
package com.tutorial.crud.aopDao;

//Librería
import java.util.List;

import com.tutorial.crud.entity.Jueves;

//Los metodos creado en el ...DAOImpl.java
public interface JuevesDAO {
	public List<Jueves> findAll();

    public Jueves findById(int id);

    public void save(Jueves objeto);
}
