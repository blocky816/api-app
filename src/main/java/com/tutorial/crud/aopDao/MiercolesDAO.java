/*
 * Esta clase MiércolesDAO.java crearemos los métodos que utilizará la clase MiercólesDAOimpl, 
 * para conectarse con nuestra base de datos.
 * @autor: 	Daniel García Velasco 
 * 			Abimael Rueda Galindo
 * @version: 1
 * 12/07/2021
 * */
package com.tutorial.crud.aopDao;

//Librerías
import java.util.List;

import com.tutorial.crud.entity.Miercoles;

//Los metodos creado en el ...DAOImpl.java
public interface MiercolesDAO {
	public List<Miercoles> findAll();

    public Miercoles findById(int id);

    public void save(Miercoles objeto);
}
