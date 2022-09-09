/*Y la clase MiercolesServiceImpl.java, será implementada por la interfaz anterior. 
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

import com.tutorial.crud.aopDao.MiercolesDAO;
import com.tutorial.crud.entity.Miercoles;
@Service //marca la clase java que realiza algún servicio 
public class MiercolesServiceImpl implements MiercolesService {
	
	@Autowired //Inyecta a nuestro DAO y lo utiliza. 
	private MiercolesDAO miercolesDAO;
	//Método en el cual manda a llamar MiercolesDAO y le asigna lo que tenga a la lista. 
	@Override
	public List<Miercoles> findAll() {
		List<Miercoles> listMiercoles= miercolesDAO.findAll();
        return listMiercoles;
	}
	//Método en el que se inserta el ID del DAO
	@Override
	public Miercoles findById(int clave) {
		Miercoles miercoles = miercolesDAO.findById(clave);
        return miercoles;
	}
	//Guarda todo lo de la lista al DAO 
	@Override
	public void save(Miercoles miercoles) {
		miercolesDAO.save(miercoles);
		
	}

}
