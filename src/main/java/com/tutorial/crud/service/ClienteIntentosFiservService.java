package com.tutorial.crud.service;

import com.tutorial.crud.entity.ClienteIntentosFiserv;
import com.tutorial.crud.repository.ClienteIntentosFiservRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClienteIntentosFiservService {
    @Autowired
    ClienteIntentosFiservRepository clienteIntentosFiservRepository;


    public List<ClienteIntentosFiserv> saveAll(List<ClienteIntentosFiserv> clienteIntentosFiserv){
        return clienteIntentosFiservRepository.saveAll(clienteIntentosFiserv);
    }

    public Integer getLastFolio() {
        return clienteIntentosFiservRepository.getLastFolio();
    }
}
