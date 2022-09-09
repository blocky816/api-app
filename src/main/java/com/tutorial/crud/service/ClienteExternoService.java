package com.tutorial.crud.service;

import com.tutorial.crud.entity.Amonestaciones;
import com.tutorial.crud.entity.CAActividad;
import com.tutorial.crud.entity.CAApartados;
import com.tutorial.crud.entity.Categoria;
import com.tutorial.crud.entity.ClienteExterno;
import com.tutorial.crud.repository.ActividadRepository;
import com.tutorial.crud.repository.AmonestacionesRepository;
import com.tutorial.crud.repository.ClienteExternoRepository;

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
public class ClienteExternoService {

    @Autowired
    ClienteExternoRepository clienteExternoRepository;

	@Autowired
	private EntityManager entityManager;

    public List<ClienteExterno> list(){
        return clienteExternoRepository.findAll();
    }

    public Optional<ClienteExterno> getOne(int id){
        return clienteExternoRepository.findById(id);
    }

    public ClienteExterno  save(ClienteExterno clienteExterno){
    	return clienteExternoRepository.save(clienteExterno);
    }

	public boolean delete(ClienteExterno clienteExterno) {
		Session currentSession = entityManager.unwrap(Session.class);
		  // your code
		  String hql = "update ClienteExterno c set c.activo=false where c.id=:u";
		  Query<?> query = currentSession.createQuery(hql);
		  query.setParameter("u", clienteExterno.getId());
		  // your code end
		  query.executeUpdate();
		  return true;
		
	}
	public List<ClienteExterno> titulares() {
		Session currentSession = entityManager.unwrap(Session.class);
		  // your code
	     Query<ClienteExterno> theQuery = currentSession.createNativeQuery("select * from cliente_externo where titular is true", ClienteExterno.class);
	     List<ClienteExterno> categoria = theQuery.getResultList();
	     return categoria;
		
	}
}
