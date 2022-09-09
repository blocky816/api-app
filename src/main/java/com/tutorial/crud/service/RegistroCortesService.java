package com.tutorial.crud.service;

import com.tutorial.crud.entity.RegistroCortes;
import com.tutorial.crud.repository.RegistroCortesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class RegistroCortesService {
	
    @Autowired
    RegistroCortesRepository registroCortesRepository;

    public RegistroCortes findByIdCorte(String id) {
    	return registroCortesRepository.findByIdCorte(id);
    }

    public RegistroCortes  save(RegistroCortes registroNuevo){
    	return registroCortesRepository.save(registroNuevo);
    }

}
