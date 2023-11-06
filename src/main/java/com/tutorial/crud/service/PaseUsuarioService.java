package com.tutorial.crud.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import com.tutorial.crud.entity.PaseUsuario;
import com.tutorial.crud.repository.PaseUsuarioRepository;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

@Service
@Transactional
public class PaseUsuarioService {

    @Autowired
    PaseUsuarioRepository paseUsuarioRepository;    

	@Autowired
	private EntityManager entityManager;

    public List<PaseUsuario> list(){
        return paseUsuarioRepository.findAll();
    }

    public Optional<PaseUsuario> getOne(int id){
        return paseUsuarioRepository.findById(id);
    }

    public PaseUsuario  save(PaseUsuario paseUsuario){
    	return paseUsuarioRepository.save(paseUsuario);
    }

	public List<PaseUsuario> getByActivo(boolean activo) {
    	return paseUsuarioRepository.findByActivo(activo).get();
	}

	public List<PaseUsuario> getByIdCliente(int usuario) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<PaseUsuario> listaPaseUsuario = currentSession.createQuery("FROM PaseUsuario p where (p.cliente.idCliente=:o and p.idProd=1746) or (p.cliente.idCliente=:o and p.disponibles>0) and p.activo=true order by idVentaDetalle", PaseUsuario.class);
		listaPaseUsuario.setParameter("o",usuario);
		List<PaseUsuario> results = listaPaseUsuario.getResultList();
		
		return results;
	}

	public List<PaseUsuario> getByIdClienteGimnasio(int usuario) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<PaseUsuario> listaPaseUsuario = currentSession.createQuery("FROM PaseUsuario p where p.cliente.idCliente=:o and p.idProd=1746 and p.activo=true order by idVentaDetalle", PaseUsuario.class);
		listaPaseUsuario.setParameter("o",usuario);
		List<PaseUsuario> results = listaPaseUsuario.getResultList();
		
		return results;
	}
	
	public boolean cancelarPasesVencidos(int usuario) {
		Session currentSession = entityManager.unwrap(Session.class);
		//String hql = "update PaseUsuario p set p.activo=false where p.cliente.IdCliente in (SELECT c.IdCliente FROM Cliente c WHERE c.FechaFinAcceso<=current_date()) and p.concepto like '% Gym %'";
		//String hql = "update PaseUsuario p set p.activo = false where p.idVentaDetalle = 410750";
		String hql = "update PaseUsuario p set p.activo=false where p.cliente.idCliente in (SELECT c.idCliente FROM Cliente c WHERE c.FechaFinAcceso<=current_date()) and (lower(p.concepto) like '% gym %' or lower(p.concepto) like '% gimnasio %')";
		Query query = currentSession.createQuery(hql);
		query.executeUpdate();

		return true;
	}
	
	public boolean activarPases(int usuario) {
		Session currentSession = entityManager.unwrap(Session.class);
		  // your code
		  //String hql = "update PaseUsuario p set p.activo=true where p.cliente.IdCliente in (SELECT c.IdCliente FROM Cliente c WHERE c.FechaFinAcceso>=current_date()) and p.concepto like 'SP % Gym %'";
		String hql = "update PaseUsuario p set p.activo=true where p.cliente.idCliente in (SELECT c.idCliente FROM Cliente c WHERE c.FechaFinAcceso>=current_date()) and (lower(p.concepto) like '% gym %' or lower(p.concepto) like '% gimnasio %')";
		  Query<?> query = currentSession.createQuery(hql);
		  // your code end
		  query.executeUpdate();
		  return true;
	}

	public List<PaseUsuario> getPasesGimnasio(int usuario) {
		Session currentSession = entityManager.unwrap(Session.class);
		//Query<PaseUsuario> listaPaseUsuario = currentSession.createQuery("FROM PaseUsuario p where (p.cliente.IdCliente=:o and p.concepto like 'SP % Gym %') and p.activo=true order by idVentaDetalle", PaseUsuario.class);
		Query<PaseUsuario> listaPaseUsuario = currentSession.createQuery("FROM PaseUsuario p where (p.cliente.idCliente=:o and (lower(p.concepto) like '% gym %' or lower(p.concepto) like '% gimnasio %')) and p.activo=true order by idVentaDetalle", PaseUsuario.class);
		listaPaseUsuario.setParameter("o",usuario);
		List<PaseUsuario> results = listaPaseUsuario.getResultList();
		return results;
	}
	/*public List<PaseUsuario> getPasesAlberca(int usuario) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<PaseUsuario> listaPaseUsuario = currentSession.createQuery("FROM PaseUsuario p where (p.cliente.IdCliente=:o and (p.idProd>=1847 and p.idProd<=1849)) and p.activo=true order by idVentaDetalle desc", PaseUsuario.class);
		listaPaseUsuario.setParameter("o",usuario);
		List<PaseUsuario> results = listaPaseUsuario.getResultList();
		
		return results;
	}*/

	public boolean getPasesAlberca(int usuario) throws IOException, InterruptedException {
		// create a client
		var client = HttpClient.newHttpClient();
		// create a request
		var request = HttpRequest.newBuilder(
				URI.create("http://192.168.20.107:8000/ServiciosClubAlpha/api/sports/alberca/"))
				.header("accept", "application/json")
				//.headers("Content-Type", "text/plain;charset=UTF-8")
				.POST(HttpRequest.BodyPublishers.ofString("{\n" +
						"    \"usuario\":"+usuario+",\n" +
						"    \"terminal\":\"69\",\n" +
						"    \"super\":false,\n" +
						"    \"idVentaDetalle\":\"1101877\"\n" +
						"}"))
				.build();

		// use the client to send the request
		var response = client.send(request, BodyHandlers.ofString());
		// the response:
		//System.out.println("Response body pases alberca => " + response.body());
		//return response;
		return Boolean.parseBoolean(response.body());
	}
	
	public List<PaseUsuario> getPasesClasesNado(int usuario) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<PaseUsuario> listaPaseUsuario = currentSession.createQuery("FROM PaseUsuario p where (p.cliente.idCliente=:o and (p.idProd>=1834 and p.idProd<=1846)) and p.activo=true order by idVentaDetalle desc", PaseUsuario.class);
		listaPaseUsuario.setParameter("o",usuario);
		List<PaseUsuario> results = listaPaseUsuario.getResultList();
		
		return results;
	}
	
	public List<PaseUsuario> getByIdClienteQR(int usuario) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<PaseUsuario> listaPaseUsuario = currentSession.createQuery("FROM PaseUsuario p where p.cliente.idCliente=:o and (p.idProd=2503) and p.disponibles>0 and p.activo=true order by idVentaDetalle", PaseUsuario.class);
		//Query<PaseUsuario> listaPaseUsuario = currentSession.createQuery("FROM PaseUsuario p where p.cliente.IdCliente=:o and (p.idProd=1808 or p.idProd=1856) and p.disponibles>0 and p.activo=true order by idVentaDetalle", PaseUsuario.class);
		listaPaseUsuario.setParameter("o",usuario);
		List<PaseUsuario> results = listaPaseUsuario.getResultList();
		
		return results;
	}

	public List<PaseUsuario> getPasesTrote(int usuario) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<PaseUsuario> listaPaseUsuario = currentSession.createQuery("FROM PaseUsuario p where (p.cliente.idCliente=:o and (p.idProd=1746)) and p.activo=true order by idVentaDetalle desc", PaseUsuario.class);
		listaPaseUsuario.setParameter("o",usuario);
		List<PaseUsuario> results = listaPaseUsuario.getResultList();
		
		return results;
	}

	public PaseUsuario findByIdVentaDetalle(int idVentaDetalle) {
		return paseUsuarioRepository.findByIdVentaDetalle(idVentaDetalle);
	}
}
