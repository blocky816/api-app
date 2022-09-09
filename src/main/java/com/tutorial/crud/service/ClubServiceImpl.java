/*Y la clase ClubServiceImpl.java, será implementada por la interfaz anterior. 
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

import com.tutorial.crud.aopDao.ClubDAO;
import com.tutorial.crud.entity.Club;

@Service //marca la clase java que realiza algún servicio 
public class ClubServiceImpl implements ClubService {

	@Autowired  //Inyecta a nuestro DAO y lo utiliza. 
    private ClubDAO clubDAO;
	//Método en el cual manda a llamar clubDAO y le asigna lo que tenga a la lista. 
	@Override
	public List<Club> findAll() {
		List<Club> listClubs= clubDAO.findAll();
        return listClubs;
	}
	//Método en el que se inserta el ID del DAO
	@Override
	public Club findById(int id) {
		Club club = clubDAO.findById(id);
        return club;
	}
	//Guarda todo lo de la lista al DAO 
	@Override
	public void save(Club club) {
		clubDAO.save(club);

	}
	public Club findByNombre(String nombre) {
		Club club = clubDAO.findByNombre(nombre);
        return club;
	}
}
