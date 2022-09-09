/*
 * Esta clase CursoDetalleDAO.java crearemos los métodos que utilizará la clase CursoDetalleDAOimpl, 
 * para conectarse con nuestra base de datos.
 * @autor: 	Daniel García Velasco 
 * 			Abimael Rueda Galindo
 * @version: 1
 * 12/07/2021
 * */
package com.tutorial.crud.aopDao;

//Librerías
import java.util.List;

import com.tutorial.crud.entity.CursoDetalle;


//Los metodos creado en el ...DAOImpl.java
public interface CursoDetalleDAO 
{
	public List<CursoDetalle> findAll();
	public CursoDetalle findById(int id);
	public void save(CursoDetalle cursoDetalle);
}
