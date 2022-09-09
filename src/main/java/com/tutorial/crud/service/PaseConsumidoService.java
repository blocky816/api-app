package com.tutorial.crud.service;

import com.tutorial.crud.entity.CAActividad;
import com.tutorial.crud.entity.CAApartadosUsuario;
import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.PaseConsumido;
import com.tutorial.crud.entity.PaseUsuario;
import com.tutorial.crud.repository.ActividadRepository;
import com.tutorial.crud.repository.PaseConsumidoRepository;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;

@Service
@Transactional
public class PaseConsumidoService {

    @Autowired
    PaseConsumidoRepository paseConsumidoRepository;

	@Autowired
	private EntityManager entityManager;
	
    public List<PaseConsumido> list(){
        return paseConsumidoRepository.findAll();
    }

    public Optional<PaseConsumido> getOne(UUID id){
        return paseConsumidoRepository.findById(id);
    }

    public PaseConsumido  save(PaseConsumido actividad){
    	return paseConsumidoRepository.save(actividad);
    }
    public PaseConsumido getOne(CAApartadosUsuario apartadoUsuario,PaseUsuario paseUsuario) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<PaseConsumido> listaApartadosUsuario = currentSession.createQuery("FROM PaseConsumido p where p.apartadosUsuario.id=:o and p.paseUsuario.idVentaDetalle=:u and p.activo=true", PaseConsumido.class);
		listaApartadosUsuario.setParameter("o",apartadoUsuario.obtenerId());
		listaApartadosUsuario.setParameter("u",paseUsuario.getIdVentaDetalle());
		List<PaseConsumido> results = listaApartadosUsuario.getResultList();
		return results.get(0);
	}

	public PaseConsumido getOne(int usuario, int idVentaDetalle) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<PaseConsumido> listaApartadosUsuario = currentSession.createQuery("FROM PaseConsumido p where p.apartadosUsuario.cliente.IdCliente=:o and p.paseUsuario.idVentaDetalle=:u and p.fechaRedencion is null", PaseConsumido.class);
		listaApartadosUsuario.setParameter("o",usuario);
		listaApartadosUsuario.setParameter("u",idVentaDetalle);
		List<PaseConsumido> results = listaApartadosUsuario.getResultList();
		return results.get(results.size()-1);		
	}
	public PaseConsumido obtenerPaseConsumido(int usuario, int idVentaDetalle) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<PaseConsumido> listaApartadosUsuario = currentSession.createQuery("FROM PaseConsumido p where p.paseUsuario.cliente.IdCliente=:o and p.paseUsuario.idVentaDetalle=:u and p.fechaRedencion is null", PaseConsumido.class);
		listaApartadosUsuario.setParameter("o",usuario);
		listaApartadosUsuario.setParameter("u",idVentaDetalle);
		List<PaseConsumido> results = listaApartadosUsuario.getResultList();
		return results.get(results.size()-1);		
	}
}
