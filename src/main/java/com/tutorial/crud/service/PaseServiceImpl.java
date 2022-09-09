/*Y la clase PaseServiceImpl.java, será implementada por la interfaz anterior. 
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

import com.tutorial.crud.aopDao.PaseDAO;
import com.tutorial.crud.entity.Pase;


@Service //marca la clase java que realiza algún servicio 
public class PaseServiceImpl implements PaseService
{
	@Autowired //Inyecta a nuestro DAO y lo utiliza. 
    private PaseDAO paseDAO;
	//Método en el cual manda a llamar PaseDAO y le asigna lo que tenga a la lista. 
	@Override
	public List<Pase> findAll() 
	{
		List<Pase> listPases= paseDAO.findAll();
        return listPases;
	}
	//Método en el que se inserta el ID del DAO
	@Override
	public Pase findById(int id) 
	{
		Pase pase = paseDAO.findById(id);
        return pase;
	}
	//Guarda todo lo de la lista al DAO 
	@Override
	public void save(Pase pase) 
	{
		paseDAO.save(pase);
	}
}
