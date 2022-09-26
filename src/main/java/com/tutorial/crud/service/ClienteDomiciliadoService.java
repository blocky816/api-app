package com.tutorial.crud.service;

import com.tutorial.crud.entity.ClienteDomiciliado;
import com.tutorial.crud.repository.ClienteDomiciliadoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ClienteDomiciliadoService {

    @Autowired
    ClienteDomiciliadoRepository clienteRepository;


    public Optional<ClienteDomiciliado> getOne(int id){
        return clienteRepository.findById(id);
    }

    public ClienteDomiciliado  save(ClienteDomiciliado actividad){
    	return clienteRepository.save(actividad);
    }
}
