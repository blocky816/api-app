/*Y la clase CursoDetalleServiceImpl.java, será implementada por la interfaz anterior. 
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

import com.tutorial.crud.aopDao.CursoDetalleDAO;
import com.tutorial.crud.entity.CursoDetalle;

@Service //marca la clase java que realiza algún servicio 
public class CursoDetalleServiceImpl implements CursoDetalleService 
{
	@Autowired //Inyecta a nuestro DAO y lo utiliza. 
	private CursoDetalleDAO cursoDetalleDAO;
	//Método en el cual manda a llamar cursoDetalleDAO y le asigna lo que tenga a la lista. 
	@Override
	public List<CursoDetalle> findAll()
	{
		List<CursoDetalle> listCursosDetalles = cursoDetalleDAO.findAll();
		return listCursosDetalles;
	}
	//Método en el que se inserta el ID del DAO
	@Override
	public CursoDetalle findById(int id)
	{
		CursoDetalle cursoDetalle = cursoDetalleDAO.findById(id);
		return cursoDetalle;
	}
	//Guarda todo lo de la lista al DAO 
	@Override
	public void save(CursoDetalle cursoDetalle)
	{
		cursoDetalleDAO.save(cursoDetalle);
	}	
}
