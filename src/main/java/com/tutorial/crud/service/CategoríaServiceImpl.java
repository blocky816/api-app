/*Y la clase CategoriaServiceImpl.java, será implementada por la interfaz anterior. 
 * Le añadiremos la anotación @Service, para indicar que es un servicio y 
 * también de @Autowired para inyectar nuestro DAO y hacer uso de él:
 *	@autor: Daniel García Velasco 
 * 			Abimael Rueda Galindo
 *	@version: 1
 *12/07/2021
*/
package com.tutorial.crud.service;

//Librerías
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tutorial.crud.aopDao.CategoriaDAO;
import com.tutorial.crud.entity.Categoria;

@Service //marca la clase java que realiza algún servicio 
public class CategoríaServiceImpl implements CategoriaService
{ 
	@Autowired //Inyecta a nuestro DAO y lo utiliza. 
    private CategoriaDAO categoriaDAO;
	
	//Método en el cual manda a llamar categoriaDAO y le asigna lo que tenga a la lista. 
	@Override
	public List<Categoria> findAll() 
	{
		List<Categoria> listCategorias= categoriaDAO.findAll();
        return listCategorias;
	}
	
	//Método en el que se inserta el ID del DAO
	@Override
	public Categoria findById(int id) 
	{
		Categoria categoria = categoriaDAO.findById(id);
        return categoria;
	}

	//Guarda todo lo de la lista al DAO 
	@Override
	public void save(Categoria categoria) 
	{
		categoriaDAO.save(categoria);
	}
}
