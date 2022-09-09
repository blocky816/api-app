package com.tutorial.crud.service;

import com.tutorial.crud.entity.CAActividad;
import com.tutorial.crud.repository.ActividadRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ActividadService {

    @Autowired
    ActividadRepository actividadRepository;

    public List<CAActividad> list(){
        return actividadRepository.findAll();
    }

    public Optional<CAActividad> getOne(UUID id){
        return actividadRepository.findById(id);
    }

    public CAActividad  save(CAActividad actividad){
    	return actividadRepository.save(actividad);
    }

	public List<CAActividad> getByActivo(boolean activo) {
    	return actividadRepository.findByActivo(activo).get();
	}

	public Optional<CAActividad> getByNombre(String nombre) {
        return actividadRepository.findByNombre(nombre); 
	}
}
