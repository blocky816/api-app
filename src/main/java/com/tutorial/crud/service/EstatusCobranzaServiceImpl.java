/*Y la clase EstatusCobranzaServiceImpl.java, será implementada por la interfaz anterior. 
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

import com.tutorial.crud.aopDao.EstatusCobranzaDAO;
import com.tutorial.crud.entity.EstatusCobranza;
@Service //marca la clase java que realiza algún servicio 
public class EstatusCobranzaServiceImpl implements EstatusCobranzaService {
	
	@Autowired //Inyecta a nuestro DAO y lo utiliza. 
	private EstatusCobranzaDAO estatusCobranzaDAO;
	//Método en el cual manda a llamar categoriaDAO y le asigna lo que tenga a la lista. 
	@Override
	public List<EstatusCobranza> findAll() {
		List<EstatusCobranza> listEstatusCobranza= estatusCobranzaDAO.findAll();
        return listEstatusCobranza;
	}
	//Método en el que se inserta el ID del DAO
	@Override
	public EstatusCobranza findById(int clave) {
		EstatusCobranza estatusCobranza = estatusCobranzaDAO.findById(clave);
        return estatusCobranza;
	}
	//Guarda todo lo de la lista al DAO 
	@Override
	public void save(EstatusCobranza estatusCobranza) {
		estatusCobranzaDAO.save(estatusCobranza);
		
	}

}
