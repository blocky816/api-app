/*Y la clase VentaServiceImpl.java, será implementada por la interfaz anterior. 
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

import com.tutorial.crud.aopDao.VentaDAO;
import com.tutorial.crud.entity.Venta;

@Service //marca la clase java que realiza algún servicio 
public class VentaServiceImpl implements VentaService
{
	@Autowired //Inyecta a nuestro DAO y lo utiliza. 
    private VentaDAO ventaDAO;
	//Método en el cual manda a llamar VentaDAO y le asigna lo que tenga a la lista. 
	@Override
	public List<Venta> findAll() 
	{
		List<Venta> listVentas= ventaDAO.findAll();
        return listVentas;
	}
	//Método en el que se inserta el ID del DAO
	@Override
	public Venta findById(int id) 
	{
		Venta venta = ventaDAO.findById(id);
        return venta;
	}
	//Guarda todo lo de la lista al DAO 
	@Override
	public void save(Venta venta) 
	{
		ventaDAO.save(venta);
	}
}
