package com.tutorial.crud.service;

import com.tutorial.crud.entity.CAActividad;
import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.Ejercicio;
import com.tutorial.crud.repository.ActividadRepository;
import com.tutorial.crud.repository.EjercicioRepository;

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
public class EjercicioService {

    @Autowired
    EjercicioRepository ejercicioRepository;

	@Autowired
	private EntityManager entityManager;
	
    public List<Ejercicio> list(){
    	Session currentSession = entityManager.unwrap(Session.class);
		Query<Ejercicio> listaEjercicios = currentSession.createQuery("FROM Ejercicio e where e.activo=true", Ejercicio.class);
		List<Ejercicio> results = listaEjercicios.getResultList();
		return results;
    }

    public Ejercicio getOne(int id){
    	Session currentSession = entityManager.unwrap(Session.class);
		Query<Ejercicio> listaEjercicios = currentSession.createQuery("FROM Ejercicio e where e.activo=true and e.id=:o", Ejercicio.class);
		listaEjercicios.setParameter("o",id);
		List<Ejercicio> results = listaEjercicios.getResultList();
		return results.get(0);
    }

    public Ejercicio  save(Ejercicio ejercicio){
    	return ejercicioRepository.save(ejercicio);
    }
    
    public boolean delete(int id) {
		Session currentSession = entityManager.unwrap(Session.class);
    	String hql = "update Ejercicio e set e.activo=false where e.id=:o";
    	Query<?> query = currentSession.createQuery(hql);
    	query.setParameter("o", id);
    	// your code end
    	query.executeUpdate();
    	return true;
    }

}
