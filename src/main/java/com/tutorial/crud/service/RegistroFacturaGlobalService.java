package com.tutorial.crud.service;

import com.tutorial.crud.entity.RegistroFacturaGlobal;
import com.tutorial.crud.repository.RegistroFacturacionGlobalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class RegistroFacturaGlobalService {

    @Autowired
    RegistroFacturacionGlobalRepository registroRepository;

    public RegistroFacturaGlobal  save(RegistroFacturaGlobal registro){
    	return registroRepository.save(registro);
    }

}
