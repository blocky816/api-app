/*Y la clase EstatusMembresiaServiceImpl.java, será implementada por la interfaz anterior. 
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

import com.tutorial.crud.aopDao.EstatusMembresiaDAO;
import com.tutorial.crud.entity.EstatusMembresia;
@Service //marca la clase java que realiza algún servicio 
public class EstatusMembresiaServiceImpl implements EstatusMembresiaService {
	
	@Autowired //Inyecta a nuestro DAO y lo utiliza. 
	private EstatusMembresiaDAO estatusMembresiaDAO;
	//Método en el cual manda a llamar EstatusMembresiaDAO y le asigna lo que tenga a la lista. 
	@Override
	public List<EstatusMembresia> findAll() {
		List<EstatusMembresia> listEstatusMembresia= estatusMembresiaDAO.findAll();
        return listEstatusMembresia;
	}
	//Método en el que se inserta el ID del DAO
	@Override
	public EstatusMembresia findById(int clave) {
		EstatusMembresia estatusMembresia = estatusMembresiaDAO.findById(clave);
        return estatusMembresia;
	}
	//Guarda todo lo de la lista al DAO 
	@Override
	public void save(EstatusMembresia estatusMembresia) {
		estatusMembresiaDAO.save(estatusMembresia);
		
	}

}
