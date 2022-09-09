/*Y la clase EstatusClienteServiceImpl.java, será implementada por la interfaz anterior. 
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

import com.tutorial.crud.aopDao.EstatusClienteDAO;
import com.tutorial.crud.entity.EstatusCliente;

@Service  //marca la clase java que realiza algún servicio 
public class EstatusClienteServiceImpl implements EstatusClienteService
{
	@Autowired  //Inyecta a nuestro DAO y lo utiliza. 
    private EstatusClienteDAO estatusClienteDAO;
	//Método en el cual manda a llamar EstatusClienteDAO y le asigna lo que tenga a la lista. 
	@Override
	public List<EstatusCliente> findAll() 
	{
		List<EstatusCliente> listEstatusClientes= estatusClienteDAO.findAll();
        return listEstatusClientes;
	}
	//Método en el que se inserta el ID del DAO
	@Override
	public EstatusCliente findById(int id) 
	{
		EstatusCliente estatusCliente = estatusClienteDAO.findById(id);
        return estatusCliente;
	}
	//Guarda todo lo de la lista al DAO 
	@Override
	public void save(EstatusCliente estatusCliente) 
	{
		estatusClienteDAO.save(estatusCliente);
	}
}
