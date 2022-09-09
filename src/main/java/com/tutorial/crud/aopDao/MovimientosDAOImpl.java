/**En nuestra clase MovimientosDAOImpl, implementamos la interfaz, le añadiremos la anotación 
 * @Repository que indica que es un DAO,y mediante la anotación @Autowired inyectaremos EntityManager, 
 * que lo utilizaremos para crear una sessión y poder enviar las peticiones a la base de datos en cada método:
 *@autor: 	Daniel García Velasco 
 * 			Abimael Rueda Galindo
 * @version: 1
 * 12/07/2021
*/
package com.tutorial.crud.aopDao;

//Librerías
import java.util.List;
import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tutorial.crud.entity.Movimientos;


@Component("MovimientosDAOImpl") //Es el estereotipo principal, indica que la clase anotada es un component.
@Repository //Es un marcador para cualquier clase que cumpla el rol o el estereotipo de un repositorio (DAO).  
public class MovimientosDAOImpl implements MovimientosDAO
{
	//Inyección EntityManager que se ocupara para crear una sessión 
	@Autowired
	private EntityManager entityManager;

	@Override //comprobar si estás sobrescribiendo correctamente un método.
	@Transactional //Inyecta la dependencia del objeto implícitamente.
	//Método que inicia sesión y le asigna un query y a su vez se lo guarda a su lista, todo es de CATEGORIA.  
	public List<Movimientos> findAll() 
	{
	     Session currentSession = entityManager.unwrap(Session.class);
	     Query<Movimientos> theQuery = currentSession.createQuery("from Movimientos", Movimientos.class);
	     List<Movimientos> movimientos = theQuery.getResultList();
	     return movimientos;
	}
	/**
	 * Este metodo realiza un query mediante hql para poder obtener todos los movimiento realizados por un cliente por medio de su id
	 * @return lista es la lista de movimientos hechas por ese cliente 
	 */
	@Override
	@Transactional
	public List<Movimientos> findByIdCliente(int id) 
	{
		Session currentSession = entityManager.unwrap(Session.class);
        Query<Movimientos> query = currentSession.createQuery("FROM Movimientos m WHERE m.IDCliente=:o",Movimientos.class);
        query.setParameter("o",id);
        List<Movimientos> lista=(List<Movimientos>) query.getResultList();
        return lista;
	}
	
	/**
	 * Este metodo busca un movimiento en particular mediante su id y devuelve ese movimiento si es que se encontro
	 * @return movimientos objeto de clase Movimientos
	 */
	
	@Override
	@Transactional
	public Movimientos findById(int clave) {
		Session currentSession = entityManager.unwrap(Session.class);

		Movimientos movimientos = currentSession.get(Movimientos.class, clave);

        return movimientos;
	}
	
	/**
	 * Este último método guarda la informacion de Movimientos.
	 */
	
	@Override
    @Transactional
	public void save(Movimientos movimientos) 
	{
		Session currentSession = entityManager.unwrap(Session.class);
        currentSession.saveOrUpdate(movimientos);  
	}
	
}
