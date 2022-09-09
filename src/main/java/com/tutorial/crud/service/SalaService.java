package com.tutorial.crud.service;

import com.tutorial.crud.entity.CAApartados;
import com.tutorial.crud.entity.CASala;
import com.tutorial.crud.repository.SalaRepository;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;

@Service
@Transactional
public class SalaService {

    @Autowired
    SalaRepository salaRepository;
    
	@Autowired
	private EntityManager entityManager;

    public List<CASala> list(){
        return salaRepository.findAll();
    }

    public Optional<CASala> getOne(UUID id){
        return salaRepository.findById(id);
    }
    
    public CASala getByNombre(String nombre){
        return salaRepository.findByNombre(nombre);
    }

    public CASala  save(CASala sala){
    	return salaRepository.save(sala);
    }

	public List<CASala> getByActivo(boolean activo) {
    	return salaRepository.findByActivo(activo).get();
	}

	public CASala getSalaClub(String sala, String club) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<CASala> listaSala = currentSession.createQuery("FROM CASala s where s.nombre=:o and s.club.Nombre=:u", CASala.class);
		listaSala.setParameter("o",sala);
		listaSala.setParameter("u",club);
		List<CASala> results = listaSala.getResultList();
		return results.get(0);
	}
}
