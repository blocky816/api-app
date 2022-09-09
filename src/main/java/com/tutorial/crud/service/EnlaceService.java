package com.tutorial.crud.service;

import com.tutorial.crud.entity.CAActividad;
import com.tutorial.crud.entity.Enlace;
import com.tutorial.crud.repository.ActividadRepository;
import com.tutorial.crud.repository.EnlaceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class EnlaceService {

    @Autowired
    EnlaceRepository enlaceRepository;

    public List<Enlace> list(){
        return enlaceRepository.findAll();
    }

    public Optional<Enlace> getOne(Integer id){
        return enlaceRepository.findById(id);
    }

    public Enlace  save(Enlace actividad){
    	return enlaceRepository.save(actividad);
    }
}
