/*En nuestra clase EstatusCobranzaDAOImpl, implementamos la interfaz, le añadiremos la anotación 
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

import com.tutorial.crud.entity.EstatusCobranza;
@Component("estatuscobranzaDAOImpl")  //Es el estereotipo principal, indica que la clase anotada es un component.
@Repository //Es un marcador para cualquier clase que cumpla el rol o el estereotipo de un repositorio (DAO).
public class EstatusCobranzaDAOImpl implements EstatusCobranzaDAO {
 
	//Inyección EntityManager que se ocupara para crear una sessión 
	 @Autowired
	private EntityManager entityManager;
	@Override //comprobar si estás sobrescribiendo correctamente un método.
	@Transactional  //Inyecta la dependencia del objeto implícitamente.
	//Método que inicia sesión y le asigna un query y a su vez se lo guarda a su lista, todo es de ESTATUSCOBRANZA.
	public List<EstatusCobranza> findAll() {
		 Session currentSession = entityManager.unwrap(Session.class);

	        Query<EstatusCobranza> theQuery = currentSession.createQuery("from EstatusCobranza", EstatusCobranza.class);

	        List<EstatusCobranza> estatusCobranza = theQuery.getResultList();

	        return estatusCobranza;
	}

	//Método que busca el ID le asigna información.
	@Override
	@Transactional
	public void save(EstatusCobranza estatusCobranza) {
		Session currentSession = entityManager.unwrap(Session.class);

        currentSession.merge(estatusCobranza);  
	}
	//Este último método guarda la informacion de ESTATUSCOBRANZA
	@Override
	@Transactional
	public EstatusCobranza findById(int clave) {
		Session currentSession = entityManager.unwrap(Session.class);

		EstatusCobranza estatusCobranza = currentSession.get(EstatusCobranza.class, clave);

        return estatusCobranza;
	}

}
