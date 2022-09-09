package com.tutorial.crud.service;

import com.tutorial.crud.entity.TipoAcceso;
import com.tutorial.crud.repository.TipoAccesoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TipoAccesoService {

    @Autowired
    TipoAccesoRepository tipoAccesoRepository;

    public List<TipoAcceso> list(){
        return tipoAccesoRepository.findAll();
    }

    public Optional<TipoAcceso> getOne(int id){
        return tipoAccesoRepository.findById(id);
    }

    public TipoAcceso  save(TipoAcceso actividad){
    	return tipoAccesoRepository.save(actividad);
    }

	
}
