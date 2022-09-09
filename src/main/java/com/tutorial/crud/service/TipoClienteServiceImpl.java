/*Y la clase TipoClienteServiceImpl.java, será implementada por la interfaz anterior. 
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

import com.tutorial.crud.aopDao.TipoClienteDAO;
import com.tutorial.crud.entity.TipoCliente;
@Service //marca la clase java que realiza algún servicio 
public class TipoClienteServiceImpl implements TipoClienteService {
	
	@Autowired //Inyecta a nuestro DAO y lo utiliza. 
	private TipoClienteDAO tipoClienteDAO;
	//Método en el cual manda a llamar TipoClienteDAO y le asigna lo que tenga a la lista. 
	@Override
	public List<TipoCliente> findAll() {
		List<TipoCliente> listTipoCliente= tipoClienteDAO.findAll();
        return listTipoCliente;
	}
	//Método en el que se inserta el ID del DAO
	@Override
	public TipoCliente findById(int clave) {
		TipoCliente tipoCliente = tipoClienteDAO.findById(clave);
        return tipoCliente;
	}
	//Guarda todo lo de la lista al DAO 
	@Override
	public void save(TipoCliente tipoCliente) {
		tipoClienteDAO.save(tipoCliente);
		
	}

}
