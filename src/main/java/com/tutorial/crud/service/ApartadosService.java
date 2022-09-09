package com.tutorial.crud.service;

import com.tutorial.crud.entity.CAApartados;
import com.tutorial.crud.entity.CAHorario;
import com.tutorial.crud.entity.CASala;
import com.tutorial.crud.repository.ApartadosRepository;

import org.hibernate.Session;
import org.hibernate.annotations.Type;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

@Service
@Transactional
public class ApartadosService {

    @Autowired
    ApartadosRepository apartadosRepository;

	@Autowired
	private EntityManager entityManager;
	
    public List<CAApartados> list(){
        return apartadosRepository.findAll();
    }

    public CAApartados getOne(UUID id){
        return apartadosRepository.findById(id).get();
    }

    public CAApartados  save(CAApartados apartados){
    	return apartadosRepository.save(apartados);
    }

    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID uuid;
    public CAApartados  getHorario(CAHorario horario){
    	uuid=horario.getId();
    	StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("myprocedure");
    	// set parameters
    	storedProcedure.registerStoredProcedureParameter("col1", UUID.class, ParameterMode.IN);
    	storedProcedure.setParameter("col1",uuid);
    	// execute SP
    	storedProcedure.execute();
    	if(storedProcedure.getSingleResult()==null)
    		return null;
    	uuid = UUID.fromString((String) storedProcedure.getSingleResult());
		CAApartados ap = apartadosRepository.getOne(uuid);
		return ap;
    }

	public List<CAApartados> getByActivo(boolean activo) {
    	return apartadosRepository.findByActivo(activo).get();
	}


	public CAApartados getHorario(UUID id, String dia) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<CAApartados> listaApartados = currentSession.createQuery("FROM CAApartados a where a.horario.id=:o and a.dia=:u", CAApartados.class);
		listaApartados.setParameter("o",id);
		listaApartados.setParameter("u",dia);
		List<CAApartados> results = listaApartados.getResultList();
		return results.get(0);
	}
}
