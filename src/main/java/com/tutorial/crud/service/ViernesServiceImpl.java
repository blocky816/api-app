/*Y la clase ViernesServiceImpl.java, será implementada por la interfaz anterior. 
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

import com.tutorial.crud.aopDao.ViernesDAO;
import com.tutorial.crud.entity.Viernes;
@Service //marca la clase java que realiza algún servicio 
public class ViernesServiceImpl implements ViernesService {
	
	@Autowired //Inyecta a nuestro DAO y lo utiliza. 
	private ViernesDAO viernesDAO;
	//Método en el cual manda a llamar ViernesDAO y le asigna lo que tenga a la lista. 
	@Override
	public List<Viernes> findAll() {
		List<Viernes> listViernes= viernesDAO.findAll();
        return listViernes;
	}
	//Método en el que se inserta el ID del DAO
	@Override
	public Viernes findById(int clave) {
		Viernes viernes = viernesDAO.findById(clave);
        return viernes;
	}
	//Guarda todo lo de la lista al DAO 
	@Override
	public void save(Viernes viernes) {
		viernesDAO.save(viernes);		
	}

}
