/*Y la clase MensajesServiceImpl.java, será implementada por la interfaz anterior. 
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

import com.tutorial.crud.aopDao.MensajesDAO;
import com.tutorial.crud.entity.Mensajes;
@Service //marca la clase java que realiza algún servicio 
public class MensajesServiceImpl implements MensajesService {
	
	@Autowired //Inyecta a nuestro DAO y lo utiliza. 
	private MensajesDAO mensajesDAO;
	//Método en el cual manda a llamar MensajesDAO y le asigna lo que tenga a la lista.
	@Override
	public List<Mensajes> findAll() {
		List<Mensajes> listMensajes= mensajesDAO.findAll();
        return listMensajes;
	}
	//Método en el que se inserta el ID del DAO
	@Override
	public Mensajes findById(int clave) {
		Mensajes mensajes = mensajesDAO.findById(clave);
        return mensajes;
	}
	//Guarda todo lo de la lista al DAO 
	@Override
	public void save(Mensajes mensajes) {
		mensajesDAO.save(mensajes);
		
	}

}
