/*Y la clase JuevesServiceImpl.java, será implementada por la interfaz anterior. 
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

import com.tutorial.crud.aopDao.JuevesDAO;
import com.tutorial.crud.entity.Jueves;
@Service //marca la clase java que realiza algún servicio 
public class JuevesServiceImpl implements JuevesService {
	
	@Autowired //Inyecta a nuestro DAO y lo utiliza. 
	private JuevesDAO juevesDAO;
	//Método en el cual manda a llamar juevesDAO y le asigna lo que tenga a la lista. 
	@Override
	public List<Jueves> findAll() {
		List<Jueves> listMiercoles= juevesDAO.findAll();
        return listMiercoles;
	}
	//Método en el que se inserta el ID del DAO
	@Override
	public Jueves findById(int clave) {
		Jueves jueves = juevesDAO.findById(clave);
        return jueves;
	}
	//Guarda todo lo de la lista al DAO 
	@Override
	public void save(Jueves jueves) {
		juevesDAO.save(jueves);
		
	}

}
