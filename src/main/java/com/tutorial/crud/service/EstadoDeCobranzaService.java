package com.tutorial.crud.service;

import com.tutorial.crud.entity.EstadoDeCobranza;
import com.tutorial.crud.entity.configuracion;
import com.tutorial.crud.entity.CAActividad;
import com.tutorial.crud.entity.CAApartados;
import com.tutorial.crud.repository.ActividadRepository;
import com.tutorial.crud.repository.EstadoDeCobranzaRepository;

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
public class EstadoDeCobranzaService {

	@Autowired
	private EntityManager entityManager;
	
    @Autowired
    EstadoDeCobranzaRepository estadoDeCobranzaRepository;

    public List<EstadoDeCobranza> list(){
        return estadoDeCobranzaRepository.findAll();
    }

    public Optional<EstadoDeCobranza> getOne(int id){
        return estadoDeCobranzaRepository.findById(id);
    }

    public EstadoDeCobranza  save(EstadoDeCobranza actividad){
    	return estadoDeCobranzaRepository.save(actividad);
    }

	public Optional<EstadoDeCobranza> getByMes(String mes) {
        return estadoDeCobranzaRepository.findByMes(mes); 
	}

	public EstadoDeCobranza getByMesAndClubAndYear(String mes, String nombre, int year) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<EstadoDeCobranza> listaApartadosUsuario = currentSession.createQuery("FROM EstadoDeCobranza e where e.mes=:a and e.club=:e and e.anio=:i",EstadoDeCobranza.class);
		listaApartadosUsuario.setParameter("a",mes);
		listaApartadosUsuario.setParameter("e",nombre);
		listaApartadosUsuario.setParameter("i",year);
		List<EstadoDeCobranza> lista = listaApartadosUsuario.getResultList();
		
		return lista.get(0);
	}
}
