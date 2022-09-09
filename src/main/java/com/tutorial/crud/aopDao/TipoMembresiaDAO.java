/*
 * Esta clase TipoMembresiaDAO.java crearemos los métodos que utilizará la clase TipoMembresíaDAOimpl, 
 * para conectarse con nuestra base de datos.
 * @autor: 	Daniel García Velasco 
 * 			Abimael Rueda Galindo
 * @version: 1
 * 12/07/2021
 * */
package com.tutorial.crud.aopDao;

//Librerías
import java.util.List;

import com.tutorial.crud.entity.TipoMembresia;

//Los metodos creado en el ...DAOImpl.java
public interface TipoMembresiaDAO {
	public List<TipoMembresia> findAll();

    public TipoMembresia findById(int id);

    public void save(TipoMembresia objeto);
}
