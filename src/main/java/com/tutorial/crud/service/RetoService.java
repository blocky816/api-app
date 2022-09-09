package com.tutorial.crud.service;

import com.tutorial.crud.entity.Reto;
import com.tutorial.crud.repository.RetoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class RetoService {

    @Autowired
    RetoRepository retoRepository;

    public List<Reto> list(){
        return retoRepository.findAll();
    }

    public Optional<Reto> getOne(UUID id){
        return retoRepository.findById(id);
    }

    public Reto  save(Reto actividad){
    	return retoRepository.save(actividad);
    }

	public List<Reto> getByActivo(boolean activo) {
    	return retoRepository.findByActivo(activo).get();
	}

	public Optional<Reto> getByNombre(String nombre) {
        return retoRepository.findByNombre(nombre); 
	}
}
