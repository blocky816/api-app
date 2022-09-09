/*Y la clase LunesServiceImpl.java, será implementada por la interfaz anterior. 
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

import com.tutorial.crud.aopDao.LunesDAO;
import com.tutorial.crud.entity.Lunes;
@Service //marca la clase java que realiza algún servicio 
public class LunesServiceImpl implements LunesService {
	
	@Autowired //Inyecta a nuestro DAO y lo utiliza.
	private LunesDAO lunesDAO;
	//Método en el cual manda a llamar LunesDAO y le asigna lo que tenga a la lista. 
	@Override
	public List<Lunes> findAll() {
		List<Lunes> listLunes= lunesDAO.findAll();
        return listLunes;
	}
	//Método en el que se inserta el ID del DAO
	@Override
	public Lunes findById(int clave) {
		Lunes lunes = lunesDAO.findById(clave);
        return lunes;
	}
	//Guarda todo lo de la lista al DAO 
	@Override
	public void save(Lunes lunes) {
		lunesDAO.save(lunes);
		
	}

}
