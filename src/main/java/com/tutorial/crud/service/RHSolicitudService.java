package com.tutorial.crud.service;

import com.tutorial.crud.entity.CAClase;
import com.tutorial.crud.entity.RHEmpleado;
import com.tutorial.crud.entity.RHSolicitud;
import com.tutorial.crud.repository.RHSolicitudRepository;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

@Service
@Transactional
public class RHSolicitudService {

	@Autowired
	private EntityManager entityManager;
    @Autowired
    RHSolicitudRepository rhsolicitudRepository;

    public List<RHSolicitud> list(){
        return rhsolicitudRepository.findAll();
    }

    public Optional<RHSolicitud> getOne(int id){
        return rhsolicitudRepository.findById(id);
    }

    public RHSolicitud  save(RHSolicitud solicitud){
    	return rhsolicitudRepository.save(solicitud);
    }

	public List<RHSolicitud> getByEmpleado(RHEmpleado empleado) {
		Session currentSession = entityManager.unwrap(Session.class);
        Query<RHSolicitud> listaClases;
		listaClases = currentSession.createNativeQuery("select * from rh_solicitud  where id_empleado="+empleado.getId()+" order by fecha_solicitud;",RHSolicitud.class);
		List<RHSolicitud> lista =listaClases.getResultList();
		return lista;
	}

}
