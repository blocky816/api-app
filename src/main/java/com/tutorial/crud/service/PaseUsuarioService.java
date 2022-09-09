package com.tutorial.crud.service;

import com.tutorial.crud.entity.PaseUsuario;
import com.tutorial.crud.repository.PaseUsuarioRepository;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

@Service
@Transactional
public class PaseUsuarioService {

    @Autowired
    PaseUsuarioRepository paseUsuarioRepository;    

	@Autowired
	private EntityManager entityManager;

    public List<PaseUsuario> list(){
        return paseUsuarioRepository.findAll();
    }

    public Optional<PaseUsuario> getOne(int id){
        return paseUsuarioRepository.findById(id);
    }

    public PaseUsuario  save(PaseUsuario paseUsuario){
    	return paseUsuarioRepository.save(paseUsuario);
    }

	public List<PaseUsuario> getByActivo(boolean activo) {
    	return paseUsuarioRepository.findByActivo(activo).get();
	}

	public List<PaseUsuario> getByIdCliente(int usuario) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<PaseUsuario> listaPaseUsuario = currentSession.createQuery("FROM PaseUsuario p where (p.cliente.IdCliente=:o and p.idProd=1746) or (p.cliente.IdCliente=:o and p.disponibles>0) and p.activo=true order by idVentaDetalle", PaseUsuario.class);
		listaPaseUsuario.setParameter("o",usuario);
		List<PaseUsuario> results = listaPaseUsuario.getResultList();
		
		return results;
	}

	public List<PaseUsuario> getByIdClienteGimnasio(int usuario) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<PaseUsuario> listaPaseUsuario = currentSession.createQuery("FROM PaseUsuario p where p.cliente.IdCliente=:o and p.idProd=1746 and p.activo=true order by idVentaDetalle", PaseUsuario.class);
		listaPaseUsuario.setParameter("o",usuario);
		List<PaseUsuario> results = listaPaseUsuario.getResultList();
		
		return results;
	}
	
	public boolean cancelarPasesVencidos(int usuario) {
		Session currentSession = entityManager.unwrap(Session.class);
		  // your code
		  String hql = "update PaseUsuario p set p.activo=false where p.cliente.IdCliente in (SELECT c.IdCliente FROM Cliente c WHERE c.FechaFinAcceso<=current_date()) and p.concepto='SP Mensualidad Gym'";
		  Query<?> query = currentSession.createQuery(hql);
		  // your code end
		  query.executeUpdate();
		  return true;
	}
	
	public boolean activarPases(int usuario) {
		Session currentSession = entityManager.unwrap(Session.class);
		  // your code
		  String hql = "update PaseUsuario p set p.activo=true where p.cliente.IdCliente in (SELECT c.IdCliente FROM Cliente c WHERE c.FechaFinAcceso>=current_date()) and p.concepto='SP Mensualidad Gym'";
		  Query<?> query = currentSession.createQuery(hql);
		  // your code end
		  query.executeUpdate();
		  return true;
	}

	public List<PaseUsuario> getPasesGimnasio(int usuario) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<PaseUsuario> listaPaseUsuario = currentSession.createQuery("FROM PaseUsuario p where (p.cliente.IdCliente=:o and p.idProd=1746) and p.activo=true order by idVentaDetalle", PaseUsuario.class);
		listaPaseUsuario.setParameter("o",usuario);
		List<PaseUsuario> results = listaPaseUsuario.getResultList();
		
		return results;
	}
	public List<PaseUsuario> getPasesAlberca(int usuario) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<PaseUsuario> listaPaseUsuario = currentSession.createQuery("FROM PaseUsuario p where (p.cliente.IdCliente=:o and (p.idProd>=1847 and p.idProd<=1849)) and p.activo=true order by idVentaDetalle desc", PaseUsuario.class);
		listaPaseUsuario.setParameter("o",usuario);
		List<PaseUsuario> results = listaPaseUsuario.getResultList();
		
		return results;
	}
	
	public List<PaseUsuario> getPasesClasesNado(int usuario) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<PaseUsuario> listaPaseUsuario = currentSession.createQuery("FROM PaseUsuario p where (p.cliente.IdCliente=:o and (p.idProd>=1834 and p.idProd<=1846)) and p.activo=true order by idVentaDetalle desc", PaseUsuario.class);
		listaPaseUsuario.setParameter("o",usuario);
		List<PaseUsuario> results = listaPaseUsuario.getResultList();
		
		return results;
	}
	
	public List<PaseUsuario> getByIdClienteQR(int usuario) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<PaseUsuario> listaPaseUsuario = currentSession.createQuery("FROM PaseUsuario p where p.cliente.IdCliente=:o and (p.idProd=1808 or p.idProd=1856) and p.disponibles>0 and p.activo=true order by idVentaDetalle", PaseUsuario.class);
		listaPaseUsuario.setParameter("o",usuario);
		List<PaseUsuario> results = listaPaseUsuario.getResultList();
		
		return results;
	}

	public List<PaseUsuario> getPasesTrote(int usuario) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<PaseUsuario> listaPaseUsuario = currentSession.createQuery("FROM PaseUsuario p where (p.cliente.IdCliente=:o and (p.idProd=1746)) and p.activo=true order by idVentaDetalle desc", PaseUsuario.class);
		listaPaseUsuario.setParameter("o",usuario);
		List<PaseUsuario> results = listaPaseUsuario.getResultList();
		
		return results;
	}
}
