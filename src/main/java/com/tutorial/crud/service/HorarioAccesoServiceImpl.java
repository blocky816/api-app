/*Y la clase HorarioAccesoServiceImpl.java, será implementada por la interfaz anterior. 
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

import com.tutorial.crud.aopDao.HorarioAccesoDAO;
import com.tutorial.crud.entity.HorarioAcceso;
@Service //marca la clase java que realiza algún servicio 
public class HorarioAccesoServiceImpl implements HorarioAccesoService {
	
	@Autowired //Inyecta a nuestro DAO y lo utiliza. 
	private HorarioAccesoDAO horarioAccesoDAO;
	//Método en el cual manda a llamar HorarioAccesoDAO y le asigna lo que tenga a la lista.
	@Override
	public List<HorarioAcceso> findAll() {
		List<HorarioAcceso> listHorarioAcceso= horarioAccesoDAO.findAll();
        return listHorarioAcceso;
	}
	//Método en el que se inserta el ID del DAO
	@Override
	public HorarioAcceso findById(int clave) {
		HorarioAcceso horarioAcceso = horarioAccesoDAO.findById(clave);
        return horarioAcceso;
	}
	//Guarda todo lo de la lista al DAO 
	@Override
	public void save(HorarioAcceso horarioAcceso) {
		horarioAccesoDAO.save(horarioAcceso);		
	}

}
