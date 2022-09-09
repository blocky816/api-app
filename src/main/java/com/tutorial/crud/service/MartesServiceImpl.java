/*Y la clase MartesServiceImpl.java, será implementada por la interfaz anterior. 
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

import com.tutorial.crud.aopDao.MartesDAO;
import com.tutorial.crud.entity.Martes;
@Service //marca la clase java que realiza algún servicio 
public class MartesServiceImpl implements MartesService {
	
	@Autowired  //Inyecta a nuestro DAO y lo utiliza. 
	private MartesDAO martesDAO;
	//Método en el cual manda a llamar MartesDAO y le asigna lo que tenga a la lista.
	@Override
	public List<Martes> findAll() {
		List<Martes> listMartes= martesDAO.findAll();
        return listMartes;
	}
	//Método en el que se inserta el ID del DAO
	@Override
	public Martes findById(int clave) {
		Martes martes = martesDAO.findById(clave);
        return martes;
	}
	//Guarda todo lo de la lista al DAO 
	@Override
	public void save(Martes martes) {
		martesDAO.save(martes);
		
	}

}
