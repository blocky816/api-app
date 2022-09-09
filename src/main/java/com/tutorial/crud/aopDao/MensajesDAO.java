/*
 * Esta clase MensajesDAO.java crearemos los métodos que utilizará la clase MensajesDAOimpl, 
 * para conectarse con nuestra base de datos.
 * @autor: 	Daniel García Velasco 
 * 			Abimael Rueda Galindo
 * @version: 1
 * 12/07/2021
 * */
package com.tutorial.crud.aopDao;
//Librerías
import java.util.List;

import com.tutorial.crud.entity.Mensajes;

//Los metodos creado en el ...DAOImpl.java
public interface MensajesDAO {
	public List<Mensajes> findAll();

    public Mensajes findById(int id);

    public void save(Mensajes objeto);
}
