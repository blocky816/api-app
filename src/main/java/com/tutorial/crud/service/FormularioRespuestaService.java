package com.tutorial.crud.service;

import com.tutorial.crud.repository.FormularioRespuestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class FormularioRespuestaService {

    @Autowired
    FormularioRespuestaRepository formularioRespuestaRepository;
}
