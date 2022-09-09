/*
 * Esta clase HorarioAccesoDAO.java crearemos los métodos que utilizará la clase HorarioAccesoDAOimpl, 
 * para conectarse con nuestra base de datos.
 * @autor: 	Daniel García Velasco 
 * 			Abimael Rueda Galindo
 * @version: 1
 * 12/07/2021
 * */
package com.tutorial.crud.aopDao;

//Librerías
import java.util.List;

import com.tutorial.crud.entity.HorarioAcceso;

//Los metodos creado en el ...DAOImpl.java
public interface HorarioAccesoDAO {
	public List<HorarioAcceso> findAll();

    public HorarioAcceso findById(int id);

    public void save(HorarioAcceso objeto);
}
