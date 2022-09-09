/*Y la clase PagoServiceImpl.java, será implementada por la interfaz anterior. 
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

import com.tutorial.crud.aopDao.PagoDAO;
import com.tutorial.crud.entity.Pago;

@Service //marca la clase java que realiza algún servicio 
public class PagoServiceImpl implements PagoService
{
	@Autowired //Inyecta a nuestro DAO y lo utiliza. 
    private PagoDAO pagoDAO;
	//Método en el cual manda a llamar PagoDAO y le asigna lo que tenga a la lista. 
	@Override
	public List<Pago> findAll() 
	{
		List<Pago> listPagos= pagoDAO.findAll();
        return listPagos;
	}
	//Método en el que se inserta el ID del DAO
	@Override
	public Pago findById(int id) 
	{
		Pago pago = pagoDAO.findById(id);
        return pago;
	}
	//Guarda todo lo de la lista al DAO 
	@Override
	public void save(Pago pago) 
	{
		pagoDAO.save(pago);
	}
}
