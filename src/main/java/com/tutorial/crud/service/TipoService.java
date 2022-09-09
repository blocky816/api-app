package com.tutorial.crud.service;

import com.tutorial.crud.entity.Tipo;
import com.tutorial.crud.repository.TipoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class TipoService {

    @Autowired
    TipoRepository tipoRepository;

    public List<Tipo> list(){
        return tipoRepository.findAll();
    }

    public Optional<Tipo> getOne(UUID id){
        return tipoRepository.findById(id);
    }

    public Tipo  save(Tipo actividad){
    	return tipoRepository.save(actividad);
    }

	public List<Tipo> getByActivo(boolean activo) {
    	return tipoRepository.findByActivo(activo).get();
	}

	public Optional<Tipo> getByNombre(String nombre) {
        return tipoRepository.findByNombre(nombre); 
	}
}
