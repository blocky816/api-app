/*Y la clase ReciboServiceImpl.java, será implementada por la interfaz anterior. 
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

import com.tutorial.crud.aopDao.ReciboDAO;
import com.tutorial.crud.entity.Recibo;
@Service //marca la clase java que realiza algún servicio 
public class ReciboServiceImpl implements ReciboService
{
	@Autowired //Inyecta a nuestro DAO y lo utiliza. 
    private ReciboDAO reciboDAO;
	//Método en el cual manda a llamar ReciboDAO y le asigna lo que tenga a la lista. 
	@Override
	public List<Recibo> findAll() 
	{
		List<Recibo> listRecibos= reciboDAO.findAll();
        return listRecibos;
	}
	//Método en el que se inserta el ID del DAO
	@Override
	public Recibo findById(String id) 
	{
		Recibo recibo = reciboDAO.findById(id);
        return recibo;
	}
	//Guarda todo lo de la lista al DAO 
	@Override
	public void save(Recibo recibo) 
	{
		reciboDAO.save(recibo);
	}
}
