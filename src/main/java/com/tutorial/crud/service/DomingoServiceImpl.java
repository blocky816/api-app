/*Y la clase DomingoServiceImpl.java, será implementada por la interfaz anterior. 
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

import com.tutorial.crud.aopDao.DomingoDAO;
import com.tutorial.crud.entity.Domingo;

@Service //marca la clase java que realiza algún servicio 
public class DomingoServiceImpl implements DomingoService {
	
	@Autowired //Inyecta a nuestro DAO y lo utiliza. 
	private DomingoDAO domingoDAO;
	//Método en el cual manda a llamar DomingoDAO y le asigna lo que tenga a la lista. 
	@Override
	public List<Domingo> findAll() {
		List<Domingo> listDomingo= domingoDAO.findAll();
        return listDomingo;
	}
	//Método en el que se inserta el ID del DAO
	@Override
	public Domingo findById(int clave) {
		Domingo domingo = domingoDAO.findById(clave);
        return domingo;
	}
	//Guarda todo lo de la lista al DAO 
	@Override
	public void save(Domingo domingo) {
		domingoDAO.save(domingo);		
	}

}


