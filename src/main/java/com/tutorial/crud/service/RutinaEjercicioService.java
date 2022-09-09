package com.tutorial.crud.service;

import com.tutorial.crud.entity.RutinaEjercicio;
import com.tutorial.crud.repository.RutinaEjercicioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
public class RutinaEjercicioService {

	
    @Autowired
    RutinaEjercicioRepository rutinaEjercicioRepository;

    public Optional<RutinaEjercicio> getOne(int id){
        return rutinaEjercicioRepository.findById(id);
    }

    public RutinaEjercicio  save(RutinaEjercicio actividad){
    	return rutinaEjercicioRepository.save(actividad);
    }

    public void delete(RutinaEjercicio rutinaEjercicio) {
    	 rutinaEjercicioRepository.delete(rutinaEjercicio);
    }
}
