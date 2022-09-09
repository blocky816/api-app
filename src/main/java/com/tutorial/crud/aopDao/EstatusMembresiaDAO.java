/*
 * Esta clase EstatusMembresiaDAO.java crearemos los métodos que utilizará la clase EstatusMembresiaDAOimpl, 
 * para conectarse con nuestra base de datos.
 * @autor: 	Daniel García Velasco 
 * 			Abimael Rueda Galindo
 * @version: 1
 * 12/07/2021
 * */
package com.tutorial.crud.aopDao;

//Librerías
import java.util.List;

import com.tutorial.crud.entity.EstatusMembresia;

//Los metodos creado en el ...DAOImpl.java
public interface EstatusMembresiaDAO {
	public List<EstatusMembresia> findAll();

    public EstatusMembresia findById(int id);

    public void save(EstatusMembresia objeto);
}
