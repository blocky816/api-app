package com.tutorial.crud.service;

import com.tutorial.crud.entity.Referencia;
import com.tutorial.crud.repository.ReferenciaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ReferenciaService {

    @Autowired
    ReferenciaRepository accesosClubRepository;


    public Optional<Referencia> getOne(UUID id){
        return accesosClubRepository.findById(id);
    }

    public Referencia  save(Referencia actividad){
    	return accesosClubRepository.save(actividad);
    }
}
