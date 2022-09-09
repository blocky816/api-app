package com.tutorial.crud.service;

import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.OrdenAlpha;
import com.tutorial.crud.repository.OrdenAlphaRepository;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

@Service
@Transactional
public class OrdenAlphaService {

    @Autowired
    OrdenAlphaRepository ordenAlphaRepository;

	@Autowired
	private EntityManager entityManager;
    
    public List<OrdenAlpha> list(){
        return ordenAlphaRepository.findAll();
    }

    public Optional<OrdenAlpha> getOne(int id){
        return ordenAlphaRepository.findById(id);
    }

    public OrdenAlpha  save(OrdenAlpha ordenAlpha){
    	return ordenAlphaRepository.save(ordenAlpha);
    }

	public OrdenAlpha getByIdCliente(int idCliente) {
		try {
			Session currentSession = entityManager.unwrap(Session.class);
			Query<OrdenAlpha> listaApartadosUsuario = currentSession.createQuery("FROM OrdenAlpha o where o.idCliente=:o", OrdenAlpha.class);
			listaApartadosUsuario.setParameter("o",idCliente);
			List<OrdenAlpha> results = listaApartadosUsuario.getResultList();		
			return results.get(results.size()-1);			
		}catch(IndexOutOfBoundsException e) {
			
		}
		return null;
		
		
	}
    
}
