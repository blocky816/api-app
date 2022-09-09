/*
 * Esta clase PedidoDetalleDAO.java crearemos los métodos que utilizará la clase PedidoDetalleDAOimpl, 
 * para conectarse con nuestra base de datos.
 * @autor: 	Daniel García Velasco 
 * 			Abimael Rueda Galindo
 * @version: 1
 * 12/07/2021
 * */
package com.tutorial.crud.aopDao;

//Librerías
import java.util.List;

import com.tutorial.crud.entity.PedidoDetalle;

//Los metodos creado en el ...DAOImpl.java
public interface PedidoDetalleDAO 
{
	public List<PedidoDetalle> findAll();
    public PedidoDetalle findById(int id);
    public void save(PedidoDetalle pedidoDetalle);
}
