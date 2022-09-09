/*
 * Esta clase CursoHorarioDAO.java crearemos los métodos que utilizará la clase CursoHorarioDAOimpl, 
 * para conectarse con nuestra base de datos.
 * @autor: 	Daniel García Velasco 
 * 			Abimael Rueda Galindo
 * @version: 1
 * 12/07/2021
 * */
package com.tutorial.crud.aopDao;
//Librerías
import java.util.List;

import com.tutorial.crud.entity.CursoHorario;


//Los metodos creado en el ...DAOImpl.java
public interface CursoHorarioDAO 
{
	public List<CursoHorario> findAll();
	public CursoHorario findById(int id);
	public void save(CursoHorario cursoHorario);
}
