/*Y la clase PedidoServiceImpl.java, será implementada por la interfaz anterior. 
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

import com.tutorial.crud.aopDao.PedidoDAO;
import com.tutorial.crud.entity.Pedido;


@Service //marca la clase java que realiza algún servicio 
public class PedidoServiceImpl implements PedidoService
{
	@Autowired //Inyecta a nuestro DAO y lo utiliza. 
    private PedidoDAO pedidoDAO;
	//Método en el cual manda a llamar PedidoDAO y le asigna lo que tenga a la lista. 
	@Override
	public List<Pedido> findAll() 
	{
		List<Pedido> listPedidos= pedidoDAO.findAll();
        return listPedidos;
	}
	//Método en el que se inserta el ID del DAO
	@Override
	public Pedido findById(int id) 
	{
		Pedido pedido = pedidoDAO.findById(id);
        return pedido;
	}
	//Guarda todo lo de la lista al DAO 
	@Override
	public void save(Pedido pedido) 
	{
		pedidoDAO.save(pedido);
	}
}
