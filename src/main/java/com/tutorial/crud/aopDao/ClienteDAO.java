/*
 * Esta clase ClienteDAO.java crearemos los métodos que utilizará la clase ClienteDAOimpl, 
 * para conectarse con nuestra base de datos.
 * @autor: 	Daniel García Velasco 
 * 			Abimael Rueda Galindo
 * @version: 1
 * 12/07/2021
 * */

package com.tutorial.crud.aopDao;
//Librerías
import java.util.List;

import com.tutorial.crud.entity.Cliente;

//Los metodos creado en el ...impl.java
public interface ClienteDAO 
{
	public List<Cliente> findAll();
    public Cliente findById(int id);
    public Cliente findByIdMembresia(long id);
    public void save(Cliente categoria);	

}
