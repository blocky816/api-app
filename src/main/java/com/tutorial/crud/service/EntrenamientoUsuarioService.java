package com.tutorial.crud.service;

import com.tutorial.crud.entity.EntrenamientoUsuario;
import com.tutorial.crud.repository.EntrenamientoUsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class EntrenamientoUsuarioService {

    @Autowired
    EntrenamientoUsuarioRepository entrenamientoUsuarioRepository;


    public Optional<EntrenamientoUsuario> getOne(UUID id){
        return entrenamientoUsuarioRepository.findById(id);
    }

    public EntrenamientoUsuario  save(EntrenamientoUsuario actividad){
    	return entrenamientoUsuarioRepository.save(actividad);
    }
}
