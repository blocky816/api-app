package com.tutorial.crud.service;

import com.tutorial.crud.entity.BasculaCliente;
import com.tutorial.crud.repository.BasculaClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BasculaClienteService {

    @Autowired
    BasculaClienteRepository basculaClienteRepository;

    public boolean existsByIdCliente(int idCliente) {
        return basculaClienteRepository.existsByIdCliente(idCliente);
    }

    public void save(BasculaCliente basculaCliente){
        basculaClienteRepository.save(basculaCliente);
    }

    public BasculaCliente findByIdCliente(int idCliente){
        return basculaClienteRepository.findByIdCliente(idCliente);
    }
}
