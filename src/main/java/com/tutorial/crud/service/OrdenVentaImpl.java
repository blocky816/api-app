/*Y la clase OrdenVentaServiceImpl.java, será implementada por la interfaz anterior. 
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

import com.tutorial.crud.aopDao.OrdenVentaDAO;
import com.tutorial.crud.entity.OrdenVenta;

@Service //marca la clase java que realiza algún servicio 
public class OrdenVentaImpl implements OrdenVentaService
{
	@Autowired //Inyecta a nuestro DAO y lo utiliza. 
    private OrdenVentaDAO ordenVentaDAO;
	//Método en el cual manda a llamar OrdenVentaDAO y le asigna lo que tenga a la lista. 
	@Override
	public List<OrdenVenta> findAll() 
	{
		List<OrdenVenta> listOrdenVentas= ordenVentaDAO.findAll();
        return listOrdenVentas;
	}
	//Método en el que se inserta el ID del DAO
	@Override
	public OrdenVenta findById(int id) 
	{
		OrdenVenta ordenVenta = ordenVentaDAO.findById(id);
        return ordenVenta;
	}
	//Guarda todo lo de la lista al DAO 
	@Override
	public void save(OrdenVenta ordenVenta) 
	{
		ordenVentaDAO.save(ordenVenta);
	}
}
