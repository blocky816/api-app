package com.tutorial.crud.service;

import com.tutorial.crud.entity.EstacionamientoExterno;
import com.tutorial.crud.entity.QRCortesia;
import com.tutorial.crud.repository.EstacionamientoExternoRepository;
import com.tutorial.crud.repository.QRCortesiaRepository;

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
public class QRCortesiaService {

	@Autowired
	private EntityManager entityManager;
	
    @Autowired
    QRCortesiaRepository qrCortesiaRepository;

    public List<QRCortesia> list(){
    	Session currentSession = entityManager.unwrap(Session.class);
    	Query<QRCortesia> listaClases = currentSession.createQuery("FROM QRCortesia order by idRegistro", QRCortesia.class);
    	List<QRCortesia>lista=listaClases.getResultList();
    	return lista;
    }

    public Optional<QRCortesia> getOne(UUID id){
        return qrCortesiaRepository.findById(id);
    }

    public QRCortesia  save(QRCortesia actividad){
    	return qrCortesiaRepository.save(actividad);
    }

	public QRCortesia getByIdRegistro(String idRegistro) {
    	return qrCortesiaRepository.findByIdRegistro(idRegistro);
	}
	public List<QRCortesia> findByRedimido(boolean redimido){
		return qrCortesiaRepository.findByRedimido(redimido);
	}
}
