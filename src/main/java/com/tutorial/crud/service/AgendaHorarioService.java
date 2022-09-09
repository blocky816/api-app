package com.tutorial.crud.service;

import com.tutorial.crud.entity.AgendaHorario;
import com.tutorial.crud.repository.HorarioAgendaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AgendaHorarioService {

    @Autowired
    HorarioAgendaRepository horarioRepository;

    public Optional<AgendaHorario> getOne(UUID id){
        return horarioRepository.findById(id);
    }

    public AgendaHorario  save(AgendaHorario apartadosUsuario){
    	return horarioRepository.save(apartadosUsuario);
    }

}
