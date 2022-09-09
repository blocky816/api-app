/*Y la clase TipoMembresiaServiceImpl.java, será implementada por la interfaz anterior. 
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

import com.tutorial.crud.aopDao.TipoMembresiaDAO;
import com.tutorial.crud.entity.TipoMembresia;

@Service  //marca la clase java que realiza algún servicio 
public class TipoMembresiaServiceImpl implements TipoMembresiaService {
	
	@Autowired //Inyecta a nuestro DAO y lo utiliza.
	private TipoMembresiaDAO tipoMembresiaDAO;
	//Método en el cual manda a llamar TipoMembresiaDAO y le asigna lo que tenga a la lista. 
	@Override
	public List<TipoMembresia> findAll() {
		List<TipoMembresia> listTipoMembresia= tipoMembresiaDAO.findAll();
        return listTipoMembresia;
	}
	//Método en el que se inserta el ID del DAO
	@Override
	public TipoMembresia findById(int clave) {
		TipoMembresia tipoMembresia = tipoMembresiaDAO.findById(clave);
        return tipoMembresia;
	}
	//Guarda todo lo de la lista al DAO 
	@Override
	public void save(TipoMembresia tipoMembresia) {
		tipoMembresiaDAO.save(tipoMembresia);
		
	}

}
