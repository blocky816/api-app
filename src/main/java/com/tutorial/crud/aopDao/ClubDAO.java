/*
 * Esta clase ClubDAO.java crearemos los métodos que utilizará la clase ClubDAOimpl, 
 * para conectarse con nuestra base de datos.
 * @autor: 	Daniel García Velasco 
 * 			Abimael Rueda Galindo
 * @version: 1
 * 12/07/2021
 * */
package com.tutorial.crud.aopDao;
//Librerías
import java.util.List;

import com.tutorial.crud.entity.Club;



//Los metodos creado en el ...DAOImpl.java
public interface ClubDAO {
	public List<Club> findAll();

    public Club findById(int id);
    public Club findByNombre(String nombre);
    public void save(Club club);

}


