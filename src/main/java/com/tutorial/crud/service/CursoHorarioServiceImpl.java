/*Y la clase CursoHorarioServiceImpl.java, será implementada por la interfaz anterior. 
 * Le añadiremos la anotación @Service, para indicar que es un servicio y 
 * también de @Autowired para inyectar nuestro DAO y hacer uso de él:
 *	@autor: Daniel García Velasco 
 * 			Abimael Rueda Galindo
 *	@version: 1
 *12/07/2021
*/
package com.tutorial.crud.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tutorial.crud.aopDao.CursoHorarioDAO;
import com.tutorial.crud.entity.CursoHorario;

@Service  //marca la clase java que realiza algún servicio 
public class CursoHorarioServiceImpl implements CursoHorarioService 
{
	@Autowired //Inyecta a nuestro DAO y lo utiliza. 
	private CursoHorarioDAO cursoHorarioDAO;
	//Método en el cual manda a llamar CursoHorarioDAO y le asigna lo que tenga a la lista. 
	@Override
	public List<CursoHorario> findAll()
	{
		List<CursoHorario> listCursosHorarios = cursoHorarioDAO.findAll();
		return listCursosHorarios;
	}
	//Método en el que se inserta el ID del DAO
	@Override
	public CursoHorario findById(int id)
	{
		CursoHorario cursoHorario = cursoHorarioDAO.findById(id);
		return cursoHorario;
	}
	//Guarda todo lo de la lista al DAO 
	@Override
	public void save(CursoHorario cursoHorario)
	{
		cursoHorarioDAO.save(cursoHorario);
	}
}
