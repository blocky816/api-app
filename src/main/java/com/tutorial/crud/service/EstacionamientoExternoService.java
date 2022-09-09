package com.tutorial.crud.service;

import com.tutorial.crud.entity.EstacionamientoExterno;
import com.tutorial.crud.repository.EstacionamientoExternoRepository;

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
public class EstacionamientoExternoService {

	@Autowired
	private EntityManager entityManager;
	
    @Autowired
    EstacionamientoExternoRepository estacionamientoExternoRepository;

    public List<EstacionamientoExterno> list(String club){
    	Session currentSession = entityManager.unwrap(Session.class);
    	Query<EstacionamientoExterno> listaClases = currentSession.createNativeQuery("select * FROM estacionamiento_externo where estacionamiento_externo.club='"+club+"' order by cast(id_registro as integer) desc;", EstacionamientoExterno.class);
    	List<EstacionamientoExterno>lista=listaClases.getResultList();
    	return lista;
    }

    public Optional<EstacionamientoExterno> getOne(UUID id){
        return estacionamientoExternoRepository.findById(id);
    }

    public EstacionamientoExterno  save(EstacionamientoExterno actividad){
    	return estacionamientoExternoRepository.save(actividad);
    }

	public EstacionamientoExterno getByIdRegistro(String idRegistro,String club) {
		Session currentSession = entityManager.unwrap(Session.class);
    	Query<EstacionamientoExterno> listaClases = currentSession.createNativeQuery("select * FROM estacionamiento_externo where estacionamiento_externo.club='"+club+"' and id_registro='"+idRegistro+"';", EstacionamientoExterno.class);
    	List<EstacionamientoExterno>lista=listaClases.getResultList();
    	return lista.get(0);
	}
}
