/*Y la clase HorarioOtroClubServiceImpl.java, será implementada por la interfaz anterior. 
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

import com.tutorial.crud.aopDao.HorarioOtroClubDAO;
import com.tutorial.crud.entity.HorarioOtroClub;
import com.tutorial.crud.entity.HorarioOtroClubId;
@Service //marca la clase java que realiza algún servicio 
public class HorarioOtroClubServiceImpl implements HorarioOtroClubService {
	
	@Autowired //Inyecta a nuestro DAO y lo utiliza. 
	private HorarioOtroClubDAO horarioOtroClubDAO;
	//Método en el cual manda a llamar HorarioOtroClubDAO y le asigna lo que tenga a la lista. 
	@Override
	public List<HorarioOtroClub> findAll() {
		List<HorarioOtroClub> listHorarioOtroClub= horarioOtroClubDAO.findAll();
        return listHorarioOtroClub;
	}
	//Método en el que se inserta el ID del DAO
	@Override
	public HorarioOtroClub findById(HorarioOtroClubId clave) {
		HorarioOtroClub horarioOtroClub = horarioOtroClubDAO.findById(clave);
        return horarioOtroClub;
	}
	//Guarda todo lo de la lista al DAO 
	@Override
	public void save(HorarioOtroClub horarioOtroClub) {
		horarioOtroClubDAO.save(horarioOtroClub);		
	}

}
