/*
 * Esta clase CategoriaDAO.java crearemos los métodos que utilizará la clase CategoriaDAOimpl, 
 * para conectarse con nuestra base de datos.
 * @autor: 	Daniel García Velasco 
 * 			Abimael Rueda Galindo
 * @version: 1
 * 12/07/2021
 * */
package com.tutorial.crud.aopDao;

//Librerías
import java.util.List;

import com.tutorial.crud.entity.Categoria;


//Los metodos creado en el ...DAOImpl.java
public interface CategoriaDAO 
{
	public List<Categoria> findAll();
    public Categoria findById(int id);
    public void save(Categoria categoria);	

}
