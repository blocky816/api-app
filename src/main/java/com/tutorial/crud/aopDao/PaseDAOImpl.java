/*En nuestra clase PaseDAOImpl, implementamos la interfaz, le añadiremos la anotación 
 * @Repository que indica que es un DAO,y mediante la anotación @Autowired inyectaremos EntityManager, 
 * que lo utilizaremos para crear una sessión y poder enviar las peticiones a la base de datos en cada método:
 *@autor: 	Daniel García Velasco 
 * 			Abimael Rueda Galindo
 * @version: 1
 * 12/07/2021
*/
package com.tutorial.crud.aopDao;

import java.util.List;
import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tutorial.crud.entity.Pase;

@Component("paseDAOImpl") //Es un marcador para cualquier clase que cumpla el rol o el estereotipo de un repositorio (DAO). 
@Repository //Es un marcador para cualquier clase que cumpla el rol o el estereotipo de un repositorio (DAO). 
public class PaseDAOImpl implements PaseDAO
{
	@Autowired //Inyección EntityManager que se ocupara para crear una sessión 
	private EntityManager entityManager;

	@Override //comprobar si estás sobrescribiendo correctamente un método.
	@Transactional //Inyecta la dependencia del objeto implícitamente.
	//Método que inicia sesión y le asigna un query y a su vez se lo guarda a su lista, todo es de CATEGORIA. 
	public List<Pase> findAll() 
	{
	     Session currentSession = entityManager.unwrap(Session.class);
	     Query<Pase> theQuery = currentSession.createQuery("from Pase", Pase.class);
	     List<Pase> pase = theQuery.getResultList();
	     return pase;
	}
	//Método que busca el ID le asigna información.
	@Override
	@Transactional
	public Pase findById(int id) 
	{
		Session currentSession = entityManager.unwrap(Session.class);
        Pase pase = currentSession.get(Pase.class, id);
        return pase;
	}
	//Este último método guarda la informacion de CATEGORIA.
	@Override
    @Transactional
	public void save(Pase pase) 
	{
		Session currentSession = entityManager.unwrap(Session.class);
        currentSession.saveOrUpdate(pase);  
	}
}
