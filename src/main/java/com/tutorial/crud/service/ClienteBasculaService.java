package com.tutorial.crud.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutorial.crud.controller.RutinaController;
import com.tutorial.crud.dto.ClienteBasculaVista;
import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.ClienteBascula;
import com.tutorial.crud.repository.ClienteBasculaRepository;

@Service
@Transactional
public class ClienteBasculaService {

	@Autowired
	private EntityManager entityManager;
	
    @Autowired
    ClienteBasculaRepository clienteBasculaRepository;
	@Autowired 
	RutinaController rutinaController;

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
	public List<ClienteBasculaVista> getClienteBasculaDefault(int idCliente) {
			ClienteBasculaVista upv = new ClienteBasculaVista();
			Cliente cliente = rutinaController.obtenerCliente(idCliente);
			if (cliente != null){
				if (cliente.getIdSexo() == 1) upv.sexo = "0";
				if (cliente.getIdSexo() == 2) upv.sexo = "1";
				// upv.foto=cliente.getURLFoto().getImagen();
				// upv.nombre=cliente.getNombre()+" "+cliente.getApellidoPaterno()+" "+cliente.getApellidoMaterno();
				return List.of(upv);
			}
			else return new ArrayList<>();
	}
	public List<ClienteBasculaVista> getUltimosPesajes(int idUsuario) {
		List<ClienteBascula> lista=clienteBasculaRepository.findTop3ByIdUsuarioOrderByFechaCapturaDesc(idUsuario);

		Cliente cliente = rutinaController.obtenerCliente(idUsuario);

		List<ClienteBasculaVista> vistas = ClienteBasculaVista.ObtenerLista(lista, cliente);

		return vistas;
	}

}
