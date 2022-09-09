/*Y la clase PedidoDetalleServiceImpl.java, será implementada por la interfaz anterior. 
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

import com.tutorial.crud.aopDao.PedidoDetalleDAO;
import com.tutorial.crud.entity.PedidoDetalle;

@Service //marca la clase java que realiza algún servicio 
public class PedidoDetalleServiceImpl implements PedidoDetalleService
{
	@Autowired //Inyecta a nuestro DAO y lo utiliza. 
    private PedidoDetalleDAO pedidoDetalleDAO;
	//Método en el cual manda a llamar PedidoDetalleDAO y le asigna lo que tenga a la lista. 
	@Override
	public List<PedidoDetalle> findAll() 
	{
		List<PedidoDetalle> listPedidosDetalles= pedidoDetalleDAO.findAll();
        return listPedidosDetalles;
	}
	//Método en el que se inserta el ID del DAO
	@Override
	public PedidoDetalle findById(int id) 
	{
		PedidoDetalle pedidoDetalle = pedidoDetalleDAO.findById(id);
        return pedidoDetalle;
	}
	//Guarda todo lo de la lista al DAO 
	@Override
	public void save(PedidoDetalle pedidoDetalle) 
	{
		pedidoDetalleDAO.save(pedidoDetalle);
	}
}
