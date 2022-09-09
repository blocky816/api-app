package com.tutorial.crud.service;

import com.tutorial.crud.entity.CAActividad;
import com.tutorial.crud.entity.RegistroGimnasio;
import com.tutorial.crud.repository.ActividadRepository;
import com.tutorial.crud.repository.RegistroGimnasioRepository;

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
public class RegistroGimnasioService {

	@Autowired
	private EntityManager entityManager;
	
    @Autowired
    RegistroGimnasioRepository registroGimnasioRepository;

    public List<RegistroGimnasio> list(){
        return registroGimnasioRepository.findAll();
    }

    public Optional<RegistroGimnasio> getOne(UUID id){
        return registroGimnasioRepository.findById(id);
    }

    public RegistroGimnasio  save(RegistroGimnasio actividad){
    	return registroGimnasioRepository.save(actividad);
    }
    
    public boolean accedio(int cliente, UUID apartado) {
    	Session currentSession = entityManager.unwrap(Session.class);
		Query<RegistroGimnasio> listaClientes = currentSession.createQuery(" from RegistroGimnasio where idCliente.IdCliente=:o and idApartados.id=:u",RegistroGimnasio.class);
		listaClientes.setParameter("o",cliente);
		listaClientes.setParameter("u",apartado);
		List<RegistroGimnasio> listResults = listaClientes.getResultList();
		if(!listResults.isEmpty())
			return true;
		return false;
    }

}
