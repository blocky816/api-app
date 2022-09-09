package com.tutorial.crud.service;
import com.tutorial.crud.entity.TerminalRedencion;
import com.tutorial.crud.repository.TerminalRedencionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TerminalRedencionService {

    @Autowired
    TerminalRedencionRepository terminalRedencionRepository;

    public List<TerminalRedencion> list(){
        return terminalRedencionRepository.findAll();
    }

    public Optional<TerminalRedencion> getOne(int id){
        return terminalRedencionRepository.findById(id);
    }

    public TerminalRedencion  save(TerminalRedencion actividad){
    	return terminalRedencionRepository.save(actividad);
    }
}
