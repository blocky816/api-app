/*Y la clase HorarioServiceImpl.java, será implementada por la interfaz anterior. 
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

import com.tutorial.crud.aopDao.HorarioDAO;
import com.tutorial.crud.entity.Horario;
@Service //marca la clase java que realiza algún servicio 
public class HorarioServiceImpl implements HorarioService {
	
	@Autowired //Inyecta a nuestro DAO y lo utiliza.
	private HorarioDAO horarioDAO;
	//Método en el cual manda a llamar HorarioDAO y le asigna lo que tenga a la lista. 
	@Override
	public List<Horario> findAll() {
		List<Horario> listViernes= horarioDAO.findAll();
        return listViernes;
	}
	//Método en el que se inserta el ID del DAO
	@Override
	public Horario findById(int clave) {
		Horario horario = horarioDAO.findById(clave);
        return horario;
	}
	//Guarda todo lo de la lista al DAO 
	@Override
	public void save(Horario horario) {
		horarioDAO.save(horario);		
	}

}
