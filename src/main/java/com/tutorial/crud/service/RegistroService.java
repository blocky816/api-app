package com.tutorial.crud.service;

import com.tutorial.crud.entity.Registro;
import com.tutorial.crud.repository.RegistroRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class RegistroService {

    @Autowired
    RegistroRepository registroRepository;

    public List<Registro> list(){
        return registroRepository.findAll();
    }

    public Optional<Registro> getOne(UUID id){
        return registroRepository.findById(id);
    }

    public Registro  save(Registro registro){
    	return registroRepository.save(registro);
    }
}
