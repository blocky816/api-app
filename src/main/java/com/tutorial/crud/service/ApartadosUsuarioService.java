package com.tutorial.crud.service;

import com.tutorial.crud.entity.CAApartados;
import com.tutorial.crud.entity.CAApartadosUsuario;
import com.tutorial.crud.entity.CAHorario;
import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.Body;
import com.tutorial.crud.repository.ApartadosUsuarioRepository;
import com.tutorial.crud.repository.HorarioRepository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;

@Service
@Transactional
public class ApartadosUsuarioService {

    @Autowired
    ApartadosUsuarioRepository apartadosRepository;
    
    @Autowired
    ClienteService clienteService;
    
	@Autowired
	private EntityManager entityManager;

    public List<CAApartadosUsuario> list(){
        return apartadosRepository.findAll();
    }

    public Optional<CAApartadosUsuario> getOne(UUID id){
        return apartadosRepository.findById(id);
    }

    public CAApartadosUsuario  save(CAApartadosUsuario horario){
    	return apartadosRepository.save(horario);
    }

	public Cliente getByIdCliente(int usuario) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<Cliente> listaApartadosUsuario = currentSession.createQuery("select cliente FROM CAApartadosUsuario a where a.cliente.IdCliente=:o", Cliente.class);
		listaApartadosUsuario.setParameter("o",usuario);
		List<Cliente> results = listaApartadosUsuario.getResultList();
		return results.get(0);
	}
	
	public boolean isEmpty(Cliente cliente, CAApartados apartado) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<CAApartadosUsuario> listaApartadosUsuario = currentSession.createQuery("FROM CAApartadosUsuario a where a.cliente.IdCliente=:o and a.apartados.id=:u and a.activo=true", CAApartadosUsuario.class);
		listaApartadosUsuario.setParameter("u", apartado.getId());
		listaApartadosUsuario.setParameter("o", cliente.getIdCliente());
		List<CAApartadosUsuario> results = listaApartadosUsuario.getResultList();
		return results.isEmpty();
	}

	public boolean delete(Cliente cliente, CAApartados apartado) {
		Session currentSession = entityManager.unwrap(Session.class);
	  // your code
	  String hql = "update CAApartadosUsuario a set a.activo=false where a.cliente.IdCliente=:o AND a.apartados.id=:u";
	  Query<?> query = currentSession.createQuery(hql);
	  query.setParameter("o", cliente.getIdCliente());
	  query.setParameter("u", apartado.getId());
	  // your code end
	  query.executeUpdate();
	  return true;
	}

   	@Transactional(rollbackFor = SQLException.class)
	public CAApartadosUsuario crearApartado(Body body, CAHorario horario1, CAApartados apartado,CAApartadosUsuario apartadosUsuario) throws IOException {
		apartado.setConteo(apartado.getConteo()+1); 
		if(horario1.getActividad().getMax()<apartado.getConteo()) {
        	TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new RuntimeException("NO hay cupo disponible ");
		}else {
			Cliente cli=clienteService.findById(body.getUsuario());
			if(this.isEmpty(cli, apartado)) {
				apartadosUsuario.setActivo(true);
				apartadosUsuario.setCreated(new Date());
				apartadosUsuario.setCreatedBy("admin");
				apartadosUsuario.setUpdated(new Date());
				apartadosUsuario.setUpdatedBy("admin");
				apartadosUsuario.setCliente(cli);
				apartadosUsuario.setApartados(apartado);
				apartadosUsuario=this.save(apartadosUsuario);
				return apartadosUsuario;
				
			}else {
	        	TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				throw new IOException("Este usuario ya aparto esta clase ");
			}
		}
		
	}

	public CAApartadosUsuario getOne(int usuario, UUID id) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<CAApartadosUsuario> listaApartadosUsuario = currentSession.createQuery("FROM CAApartadosUsuario a where a.cliente.IdCliente=:o and a.apartados.id=:u ", CAApartadosUsuario.class);
		listaApartadosUsuario.setParameter("u", id);
		listaApartadosUsuario.setParameter("o", usuario);
		List<CAApartadosUsuario> results = listaApartadosUsuario.getResultList();
		return results.get(0);
	}

	public boolean delete(CAApartados apartado) {
		Session currentSession = entityManager.unwrap(Session.class);
		  // your code
		  String hql = "update CAApartadosUsuario a set a.activo=false where a.apartados.id=:u";
		  Query<?> query = currentSession.createQuery(hql);
		  query.setParameter("u", apartado.getId());
		  // your code end
		  query.executeUpdate();
		  return true;
		
	}
}
