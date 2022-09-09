package com.tutorial.crud.service;

import com.tutorial.crud.entity.DatosUsuario;
import com.tutorial.crud.repository.DatosUsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class DatosUsuarioService {

    @Autowired
    DatosUsuarioRepository datosUsuarioRepository;


    public Optional<DatosUsuario> getOne(UUID id){
        return datosUsuarioRepository.findById(id);
    }

    public DatosUsuario  save(DatosUsuario actividad){
    	return datosUsuarioRepository.save(actividad);
    }
    
    public List<DatosUsuario> findByIdCliente(int idCliente){
    	return datosUsuarioRepository.findByIdCliente(idCliente);
    }
}
