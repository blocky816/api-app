/*
 * Esta clase SabadoDAO.java crearemos los métodos que utilizará la clase SábadoDAOimpl, 
 * para conectarse con nuestra base de datos.
 * @autor: 	Daniel García Velasco 
 * 			Abimael Rueda Galindo
 * @version: 1
 * 12/07/2021
 * */
package com.tutorial.crud.aopDao;

//Librería
import java.util.List;
import com.tutorial.crud.entity.Sabado;

//Los metodos creado en el ...DAOImpl.java
public interface SabadoDAO {
	public List<Sabado> findAll();

    public Sabado findById(int id);

    public void save(Sabado objeto);
}

