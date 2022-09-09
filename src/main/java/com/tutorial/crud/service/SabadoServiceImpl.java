/*Y la clase SabadoServiceImpl.java, será implementada por la interfaz anterior. 
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

import com.tutorial.crud.aopDao.SabadoDAO;
import com.tutorial.crud.entity.Sabado;
@Service //marca la clase java que realiza algún servicio 
public class SabadoServiceImpl implements SabadoService {
	
	@Autowired  //Inyecta a nuestro DAO y lo utiliza. 
	private SabadoDAO sabadoDAO;
	//Método en el cual manda a llamar SabadoDAO y le asigna lo que tenga a la lista. 
	@Override
	public List<Sabado> findAll() {
		List<Sabado> listViernes= sabadoDAO.findAll();
        return listViernes;
	}
	//Método en el que se inserta el ID del DAO
	@Override
	public Sabado findById(int clave) {
		Sabado sabado = sabadoDAO.findById(clave);
        return sabado;
	}
	//Guarda todo lo de la lista al DAO 
	@Override
	public void save(Sabado sabado) {
		sabadoDAO.save(sabado);		
	}

}
