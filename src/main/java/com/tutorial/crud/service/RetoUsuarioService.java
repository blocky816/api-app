package com.tutorial.crud.service;

import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.Reto;
import com.tutorial.crud.entity.RetoUsuario;
import com.tutorial.crud.repository.RetoUsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class RetoUsuarioService {

    @Autowired
    RetoUsuarioRepository retoUsuarioRepository;

    public List<RetoUsuario> list(){
        return retoUsuarioRepository.findAll();
    }

    public Optional<RetoUsuario> getOne(UUID id){
        return retoUsuarioRepository.findById(id);
    }

    public RetoUsuario  save(RetoUsuario actividad){
    	return retoUsuarioRepository.save(actividad);
    }

	public List<RetoUsuario> getByActivo(boolean activo) {
    	return retoUsuarioRepository.findByActivo(activo).get();
	}

    public RetoUsuario existeCliente(Reto reto, Cliente cliente){ return retoUsuarioRepository.findByRetoAndCliente(reto, cliente); }

    public List<Tuple> getRetoUsuarioDTO(Integer idCliente) {
        //System.out.println("\nIDCLIENTE en Service: " + cLienteId);
        // Tuple t = retoRepository.getRetoUsuarioDTO(cLienteId);
        // System.out.println("Tupla desde reto sevice" + t);
        return retoUsuarioRepository.getRetoUsuarioDTO(idCliente);
    }
}
