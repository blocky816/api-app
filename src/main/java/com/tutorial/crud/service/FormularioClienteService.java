package com.tutorial.crud.service;

import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.FormularioCliente;
import com.tutorial.crud.repository.FormularioClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FormularioClienteService {

    @Autowired
    FormularioClienteRepository formularioClienteRepository;

    public void save(FormularioCliente formularioCliente) {
        formularioClienteRepository.save(formularioCliente);
    }

    public List<FormularioCliente> findByFolioAndActivo(int folio, Boolean activo) {
        return formularioClienteRepository.findByFolioAndActivo(folio, activo);
    }
}
