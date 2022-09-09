/*
 * Esta clase HorarioDAO.java crearemos los métodos que utilizará la clase HorarioDAOimpl, 
 * para conectarse con nuestra base de datos.
 * @autor: 	Daniel García Velasco 
 * 			Abimael Rueda Galindo
 * @version: 1
 * 12/07/2021
 * */
package com.tutorial.crud.aopDao;

//Librerías
import java.util.List;

import com.tutorial.crud.entity.Horario;

//Los metodos creado en el ...DAOImpl.java
public interface HorarioDAO {
	public List<Horario> findAll();

    public Horario findById(int id);

    public void save(Horario objeto);
}
