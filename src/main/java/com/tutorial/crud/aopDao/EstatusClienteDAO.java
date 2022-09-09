/*
 * Esta clase EstatusClienteDAO.java crearemos los métodos que utilizará la clase EstatusClienteDAOimpl, 
 * para conectarse con nuestra base de datos.
 * @autor: 	Daniel García Velasco 
 * 			Abimael Rueda Galindo
 * @version: 1
 * 12/07/2021
 * */
package com.tutorial.crud.aopDao;

//Librerías
import java.util.List;

import com.tutorial.crud.entity.EstatusCliente;

//Los metodos creado en el ...DAOImpl.java
public interface EstatusClienteDAO 
{
	public List<EstatusCliente> findAll();
    public EstatusCliente findById(int id);
    public void save(EstatusCliente estatusCliente);	

}
