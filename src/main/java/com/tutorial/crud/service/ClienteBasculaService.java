package com.tutorial.crud.service;

import com.tutorial.crud.entity.ClienteBascula;
import com.tutorial.crud.repository.ClienteBasculaRepository;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.persistence.EntityManager;

@Service
@Transactional
public class ClienteBasculaService {

	@Autowired
	private EntityManager entityManager;
	
    @Autowired
    ClienteBasculaRepository clienteBasculaRepository;

    public ClienteBascula  save(ClienteBascula clienteBascula){
    	return clienteBasculaRepository.save(clienteBascula);
    }

	public List<ClienteBascula> getByIdUsuario(int idUsuario) {
        return clienteBasculaRepository.findByIdUsuario(idUsuario); 
	}
	public ClienteBascula getUltimoPesaje(int idUsuario) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<ClienteBascula> query = currentSession.createNativeQuery("select * from cliente_bascula where id_usuario="+idUsuario+" order by fecha_captura desc",ClienteBascula.class);
		List<ClienteBascula>lista=query.getResultList();
		return lista.get(0);
	}
}
