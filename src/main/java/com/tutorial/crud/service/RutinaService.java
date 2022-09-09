package com.tutorial.crud.service;

import com.tutorial.crud.entity.Rutina;
import com.tutorial.crud.repository.RutinaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
@Transactional
public class RutinaService {

    @Autowired
    RutinaRepository rutinaRepository;


    public Optional<Rutina> getOne(int id){
        return rutinaRepository.findById(id);
    }

    public Rutina  save(Rutina actividad){
    	return rutinaRepository.save(actividad);
    }

    public void delete(Rutina rutina) {
    	rutinaRepository.delete(rutina);
    }
}
