/*Y la clase ClienteServiceImpl.java, será implementada por la interfaz anterior. 
 * Le añadiremos la anotación @Service, para indicar que es un servicio y 
 * también de @Autowired para inyectar nuestro DAO y hacer uso de él:
 *	@autor: Daniel García Velasco 
 * 			Abimael Rueda Galindo
 *	@version: 1
 *12/07/2021
*/
package com.tutorial.crud.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tutorial.crud.aopDao.ClienteDAO;
import com.tutorial.crud.dto.ClienteDTOO;
import com.tutorial.crud.entity.CAApartados;
import com.tutorial.crud.entity.CASala;
import com.tutorial.crud.entity.Cliente;
@Service //marca la clase java que realiza algún servicio 
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private EntityManager entityManager;
	
	@Autowired  //Inyecta a nuestro DAO y lo utiliza. 
	private ClienteDAO clienteDAO;
	//Método en el cual manda a llamar categoriaDAO y le asigna lo que tenga a la lista. 
	@Override 
	public List<Cliente> findAll() {
		List<Cliente> listCliente= clienteDAO.findAll();
        return listCliente;
	}
	//Método en el que se inserta el ID del DAO
	@Override
	public Cliente findById(int clave) {
		Cliente cliente = clienteDAO.findById(clave);
        return cliente;
	}
	
	@Override

    public Cliente findByNoMembresia(long clave) {
		Cliente cliente=clienteDAO.findByIdMembresia(clave);
		return cliente;
	}
	
	//Guarda todo lo de la lista al DAO 
	@Override
	public void save(Cliente cliente) {
		clienteDAO.save(cliente);		
	}
	@Override
	public boolean findCitas(Date fechaActual, CASala obtenerSala, int cliente) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<CAApartados> listaApartadosUsuario = currentSession.createQuery("select a.apartados FROM CAApartadosUsuario a where a.cliente.IdCliente=:o and a.apartados.horario.sala.id=:u",CAApartados.class);
		listaApartadosUsuario.setParameter("o",cliente);
		listaApartadosUsuario.setParameter("u",obtenerSala.getId());
		List<CAApartados> lista = listaApartadosUsuario.getResultList();
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List<CAApartados> lista2=new ArrayList<CAApartados>();
		for(int i=0;i<lista.size();i++) {
			String rango =lista.get(i).getHorario().getRango();
			String[] hora=rango.split("-");
			try {
				Date fecha = formato.parse(lista.get(i).getDia()+" "+hora[0]);
				Date fecha2 = formato.parse(lista.get(i).getDia()+" "+hora[1]);
				if(fechaActual.after(fecha) && fechaActual.before(fecha2)) {
					lista2.add(lista.get(i));
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		if(lista2.size()>0) 
			return true;
		return false;
		/*Session currentSession = entityManager.unwrap(Session.class);
		Query<CAApartados> listaApartadosUsuario = currentSession.createQuery("select a.apartados FROM CAApartadosUsuario a where a.cliente.IdCliente=:o and a.apartados.horario.sala.id=:u and a.activo=true",CAApartados.class);
		listaApartadosUsuario.setParameter("o",cliente);
		listaApartadosUsuario.setParameter("u",obtenerSala.getId());
		List<CAApartados> lista = listaApartadosUsuario.getResultList();
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List<CAApartados> lista2=new ArrayList<CAApartados>();
		for(int i=0;i<lista.size();i++) {
			try {
				Date fecha = formato.parse(lista.get(i).getDia()+" 00:00");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(fecha); // Configuramos la fecha que se recibe
				calendar.add(Calendar.DAY_OF_YEAR, 1);
				if(fechaActual.after(fecha) && fechaActual.before(calendar.getTime())) {
					lista2.add(lista.get(i));
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
		}
			if(lista2.size()>0) 
				return true;
			return false;*/
	}
	@Override
	public boolean findCitas(UUID idApartados, int cliente) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<CAApartados> listaApartadosUsuario = currentSession.createQuery("select a.apartados FROM CAApartadosUsuario a where a.cliente.IdCliente=:o and a.apartados.id=:u",CAApartados.class);
		listaApartadosUsuario.setParameter("o",cliente);
		listaApartadosUsuario.setParameter("u",idApartados);
		List<CAApartados> lista = listaApartadosUsuario.getResultList();
		if(lista.size()>0) 
			return true;
		return false;
		/*Session currentSession = entityManager.unwrap(Session.class);
		Query<CAApartados> listaApartadosUsuario = currentSession.createQuery("select a.apartados FROM CAApartadosUsuario a where a.cliente.IdCliente=:o and a.apartados.horario.sala.id=:u and a.activo=true",CAApartados.class);
		listaApartadosUsuario.setParameter("o",cliente);
		listaApartadosUsuario.setParameter("u",obtenerSala.getId());
		List<CAApartados> lista = listaApartadosUsuario.getResultList();
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List<CAApartados> lista2=new ArrayList<CAApartados>();
		for(int i=0;i<lista.size();i++) {
			try {
				Date fecha = formato.parse(lista.get(i).getDia()+" 00:00");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(fecha); // Configuramos la fecha que se recibe
				calendar.add(Calendar.DAY_OF_YEAR, 1);
				if(fechaActual.after(fecha) && fechaActual.before(calendar.getTime())) {
					lista2.add(lista.get(i));
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
		}
			if(lista2.size()>0) 
				return true;
			return false;*/
	}
	@Override
	public List<ClienteDTOO> findClientesByIdClub(int clubId) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query listaClientes = currentSession.createNativeQuery("select idcliente, nombre,estatusacceso from cliente where idclub="+clubId+";");
		List<Object[]> listResults = listaClientes.getResultList();
		List<ClienteDTOO> listaDTO= new ArrayList<ClienteDTOO>();
		for (Object[] record : listResults) {
			ClienteDTOO cliente=new ClienteDTOO();
			cliente.setEstatusAccesos((String) record[2]);
			cliente.setIdCliente((int) record[0]);
			cliente.setNombre((String) record[1]);
			listaDTO.add(cliente);
			
		}
		return listaDTO;
	}
	@Override
	public CAApartados findApartados(Date fechaActual, CASala obtenerSala, int cliente) {
			Session currentSession = entityManager.unwrap(Session.class);
			Query<CAApartados> listaApartadosUsuario = currentSession.createQuery("select a.apartados FROM CAApartadosUsuario a where a.cliente.IdCliente=:o and a.apartados.horario.sala.id=:u",CAApartados.class);
			listaApartadosUsuario.setParameter("o",cliente);
			listaApartadosUsuario.setParameter("u",obtenerSala.getId());
			List<CAApartados> lista = listaApartadosUsuario.getResultList();
			SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			List<CAApartados> lista2=new ArrayList<CAApartados>();
			for(int i=0;i<lista.size();i++) {
				String rango =lista.get(i).getHorario().getRango();
				String[] hora=rango.split("-");
				try {
					
					Date fecha = formato.parse(lista.get(i).getDia()+" "+hora[0]);
					Date fecha2 = formato.parse(lista.get(i).getDia()+" "+hora[1]);
					
					if(fechaActual.after(fecha) && fechaActual.before(fecha2)) {
						lista2.add(lista.get(i));
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
			if(lista2.size()>0) 
				return lista2.get(0);
			return null;
	}
	@Override
	public List<ClienteDTOO> asistenciaClientes(UUID id) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query listaClientes = currentSession.createNativeQuery("select cliente.idcliente,nombre from pase_consumido"
				+ " join ca_apartados_usuario on pase_consumido.apartado_usuario=ca_apartados_usuario.id "
				+ " join ca_apartados on ca_apartados_usuario.id_apartados=ca_apartados.id_apartados "
				+ " join cliente on ca_apartados_usuario.idcliente=cliente.idcliente"
				+ " where terminal_redencion_id is not null and ca_apartados.id_apartados='"+id+"';");
		List<Object[]> listResults = listaClientes.getResultList();
		List<ClienteDTOO> listaDTO= new ArrayList<ClienteDTOO>();
		for (Object[] record : listResults) {
			ClienteDTOO cliente=new ClienteDTOO();
			cliente.setIdCliente((int) record[0]);
			cliente.setNombre((String) record[1]);
			listaDTO.add(cliente);
			
		}
		return listaDTO;
	}
	@Override
	public List<ClienteDTOO> asistenciaGimnasioClientes(UUID id) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query listaClientes = currentSession.createNativeQuery("select idCliente,nombre from registro_gimnasio"
				+ " join cliente on cliente.idCliente=registro_gimnasio.id_cliente"
				+ " where id_apartados='"+id+"';");
		List<Object[]> listResults = listaClientes.getResultList();
		List<ClienteDTOO> listaDTO= new ArrayList<ClienteDTOO>();
		for (Object[] record : listResults) {
			ClienteDTOO cliente=new ClienteDTOO();
			cliente.setIdCliente((int) record[0]);
			cliente.setNombre((String) record[1]);
			listaDTO.add(cliente);			
		}
		return listaDTO;
	}
}
