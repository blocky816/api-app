/*Y la clase FotoServiceImpl.java, será implementada por la interfaz anterior. 
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

import com.tutorial.crud.entity.Foto;
import com.tutorial.crud.repository.FotoRepository;


import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class FotoServiceImpl
{ 
    @Autowired
    FotoRepository fotoDAO;
	
	//Método en el cual manda a llamar categoriaDAO y le asigna lo que tenga a la lista. 
	public List<Foto> findAll() 
	{
		List<Foto> listFotos= fotoDAO.findAll();
        return listFotos;
	}
	

	public Foto findById(int id) 
	{
		Foto foto = fotoDAO.findById(id).get();
        return foto;
	}


	public void save(Foto foto) 
	{
		fotoDAO.save(foto);
	}
	public void delete(Foto foto) {
		fotoDAO.delete(foto);
	}
}
