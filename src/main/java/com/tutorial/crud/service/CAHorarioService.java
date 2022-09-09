package com.tutorial.crud.service;

import com.tutorial.crud.entity.CAHorario;
import com.tutorial.crud.repository.HorarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class CAHorarioService {

    @Autowired
    HorarioRepository horarioRepository;

    public List<CAHorario> list(){
        return horarioRepository.findAll();
    }

    public Optional<CAHorario> getOne(UUID id){
        return horarioRepository.findById(id);
    }

    public CAHorario  save(CAHorario apartadosUsuario){
    	return horarioRepository.save(apartadosUsuario);
    }

	public List<CAHorario> getByActivo(boolean activo) {
    	return horarioRepository.findByActivo(activo).get();
	}

}
