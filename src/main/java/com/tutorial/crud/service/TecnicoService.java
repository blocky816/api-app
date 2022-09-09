package com.tutorial.crud.service;

import com.tutorial.crud.entity.CATecnico;
import com.tutorial.crud.repository.TecnicoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class TecnicoService {

    @Autowired
    TecnicoRepository tecnicoRepository;

    public List<CATecnico> list(){
        return tecnicoRepository.findAll();
    }

    public Optional<CATecnico> getOne(UUID id){
        return tecnicoRepository.findById(id);
    }

    public CATecnico  save(CATecnico tecnico){
    	return tecnicoRepository.save(tecnico);
    }

	public List<CATecnico> getByActivo(boolean activo) {
    	return tecnicoRepository.findByActivo(activo).get();
	}

	public Optional<CATecnico> getByNombre(String nombre) {
        return tecnicoRepository.findByNombre(nombre);
	}
}
