/*
 * Esta clase DomingoDAO.java crearemos los métodos que utilizará la clase DomingoDAOimpl, 
 * para conectarse con nuestra base de datos.
 * @autor: 	Daniel García Velasco 
 * 			Abimael Rueda Galindo
 * @version: 1
 * 12/07/2021
 * */
package com.tutorial.crud.aopDao;

//Librerías
import java.util.List;

import com.tutorial.crud.entity.Domingo;

//Los metodos creado en el ...DAOImpl.java
public interface DomingoDAO {
	public List<Domingo> findAll();

    public Domingo findById(int id);

    public void save(Domingo objeto);
}

