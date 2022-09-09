/*
 * Esta clase PedidoDAO.java crearemos los métodos que utilizará la clase PedidoDAOimpl, 
 * para conectarse con nuestra base de datos.
 * @autor: 	Daniel García Velasco 
 * 			Abimael Rueda Galindo
 * @version: 1
 * 12/07/2021
 * */
package com.tutorial.crud.aopDao;

//Librerías
import java.util.List;

import com.tutorial.crud.entity.Pedido;

//Los metodos creado en el ...DAOImpl.java
public interface PedidoDAO 
{
	public List<Pedido> findAll();
    public Pedido findById(int id);
    public void save(Pedido pedido);
}
