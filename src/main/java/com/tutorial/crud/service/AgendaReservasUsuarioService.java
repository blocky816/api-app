package com.tutorial.crud.service;

import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.AgendaHorario;
import com.tutorial.crud.entity.AgendaReservas;
import com.tutorial.crud.entity.AgendaReservasUsuario;
import com.tutorial.crud.entity.Body;
import com.tutorial.crud.repository.ReservasAgendaUsuarioRepository;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;

@Service
@Transactional
public class AgendaReservasUsuarioService {

    @Autowired
    ReservasAgendaUsuarioRepository apartadosRepository;
    
    @Autowired
    ClienteService clienteService;
    
	@Autowired
	private EntityManager entityManager;

    public AgendaReservasUsuario  save(AgendaReservasUsuario horario){
    	return apartadosRepository.save(horario);
    }

    public boolean isEmpty(Cliente cliente, AgendaReservas apartado) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<AgendaReservasUsuario> listaApartadosUsuario = currentSession.createQuery("FROM AgendaReservasUsuario a where a.cliente.IdCliente=:o and a.reservas.id=:u and a.activo=true", AgendaReservasUsuario.class);
		listaApartadosUsuario.setParameter("u", apartado.getId());
		listaApartadosUsuario.setParameter("o", cliente.getIdCliente());
		List<AgendaReservasUsuario> results = listaApartadosUsuario.getResultList();
		return results.isEmpty();
	}
	public boolean delete(Cliente cliente, AgendaReservas apartado) {
		Session currentSession = entityManager.unwrap(Session.class);
	  // your code
	  String hql = "update AgendaReservasUsuario a set a.activo=false where a.cliente.IdCliente=:o AND a.reservas.id=:u";
	  Query<?> query = currentSession.createQuery(hql);
	  query.setParameter("o", cliente.getIdCliente());
	  query.setParameter("u", apartado.getId());
	  // your code end
	  query.executeUpdate();
	  return true;
	}

   	@Transactional(rollbackFor = SQLException.class)
	public AgendaReservasUsuario crearReservacion(Body body, AgendaHorario horario1, AgendaReservas apartado,AgendaReservasUsuario apartadosUsuario) throws IOException {
		apartado.setConteo(apartado.getConteo()+1); 
		if(horario1.getCupoMaximo()<apartado.getConteo()) {
        	TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new RuntimeException("NO hay cupo disponible ");
		}else {
			Cliente cli=clienteService.findById(body.getUsuario());
			if(this.isEmpty(cli, apartado)) {
				apartadosUsuario.setActivo(true);
				apartadosUsuario.setCreated(new Date());
				apartadosUsuario.setCreatedBy("admin");
				apartadosUsuario.setUpdated(new Date());
				apartadosUsuario.setUpdatedBy("admin");
				apartadosUsuario.setCliente(cli);
				apartadosUsuario.setReservas(apartado);
				apartadosUsuario=this.save(apartadosUsuario);
				return apartadosUsuario;
				
			}else {
	        	TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				throw new IOException("Este usuario ya aparto esta clase ");
			}
		}
		
	}

	public AgendaReservasUsuario getOne(int usuario, UUID id) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<AgendaReservasUsuario> listaApartadosUsuario = currentSession.createQuery("FROM AgendaReservasUsuario a where a.cliente.IdCliente=:o and a.reservas.id=:u ", AgendaReservasUsuario.class);
		listaApartadosUsuario.setParameter("u", id);
		listaApartadosUsuario.setParameter("o", usuario);
		List<AgendaReservasUsuario> results = listaApartadosUsuario.getResultList();
		return results.get(0);
	}

	public boolean delete(AgendaReservas apartado) {
		Session currentSession = entityManager.unwrap(Session.class);
		  // your code
		  String hql = "update AgendaReservasUsuario a set a.activo=false where a.apartados.id=:u";
		  Query<?> query = currentSession.createQuery(hql);
		  query.setParameter("u", apartado.getId());
		  // your code end
		  query.executeUpdate();
		  return true;
		
	}
	
	/*public Cliente getByIdCliente(int usuario) {
	Session currentSession = entityManager.unwrap(Session.class);
	Query<Cliente> listaApartadosUsuario = currentSession.createQuery("select cliente FROM CAApartadosUsuario a where a.cliente.IdCliente=:o", Cliente.class);
	listaApartadosUsuario.setParameter("o",usuario);
	List<Cliente> results = listaApartadosUsuario.getResultList();
	return results.get(0);
}*/


}
