/*
 * Esta clase TipoClienteDAO.java crearemos los métodos que utilizará la clase TipoClienteDAOimpl, 
 * para conectarse con nuestra base de datos.
 * @autor: 	Daniel García Velasco 
 * 			Abimael Rueda Galindo
 * @version: 1
 * 12/07/2021
 * */
package com.tutorial.crud.aopDao;

//Librerias
import java.util.List;

import com.tutorial.crud.entity.TipoCliente;

//Los metodos creado en el ...DAOImpl.java
public interface TipoClienteDAO {
	public List<TipoCliente> findAll();

    public TipoCliente findById(int id);

    public void save(TipoCliente objeto);
}
