package com.tutorial.crud.service;

import com.tutorial.crud.entity.AgendaReservas;
import com.tutorial.crud.repository.ReservasAgendaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class AgendaReservasService {

    @Autowired
    ReservasAgendaRepository reservasRepository;

	/*@Autowired
	private EntityManager entityManager;*/
	

    public AgendaReservas getOne(UUID id){
        return reservasRepository.findById(id).get();
    }

    public AgendaReservas  save(AgendaReservas apartados){
    	return reservasRepository.save(apartados);
    }

	/*public CAApartados getHorario(UUID id, String dia) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<CAApartados> listaApartados = currentSession.createQuery("FROM CAApartados a where a.horario.id=:o and a.dia=:u", CAApartados.class);
		listaApartados.setParameter("o",id);
		listaApartados.setParameter("u",dia);
		List<CAApartados> results = listaApartados.getResultList();
		return results.get(0);
	}*/
	
	 /* @Type(type="org.hibernate.type.UUIDCharType")
    private UUID uuid;
    public AgendaReservas  getHorario(AgendaHorario horario){
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
		CAApartados ap = reservasRepository.getOne(uuid);
		return ap;
    }*/

}
