/*
 * Esta clase ViernesDAO.java crearemos los métodos que utilizará la clase ViernesDAOimpl, 
 * para conectarse con nuestra base de datos.
 * @autor: 	Daniel García Velasco 
 * 			Abimael Rueda Galindo
 * @version: 1
 * 12/07/2021
 * */
package com.tutorial.crud.aopDao;

//Librerías
import java.util.List;

import com.tutorial.crud.entity.Viernes;

//Los metodos creado en el ...DAOImpl.java
public interface ViernesDAO {
	public List<Viernes> findAll();

    public Viernes findById(int id);

    public void save(Viernes objeto);
}
