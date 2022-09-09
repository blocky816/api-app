package com.tutorial.crud.service;

import com.tutorial.crud.entity.Amonestaciones;
import com.tutorial.crud.entity.CAActividad;
import com.tutorial.crud.repository.ActividadRepository;
import com.tutorial.crud.repository.AmonestacionesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AmonestacionesService {

    @Autowired
    AmonestacionesRepository amonestacionesRepository;

    public List<Amonestaciones> list(){
        return amonestacionesRepository.findAll();
    }

    public Optional<Amonestaciones> getOne(int id){
        return amonestacionesRepository.findById(id);
    }

    public Amonestaciones  save(Amonestaciones actividad){
    	return amonestacionesRepository.save(actividad);
    }

	public List<Amonestaciones> getByIdChip(long idChip) {
    	return amonestacionesRepository.findByIdChip(idChip).get();
	}
}
