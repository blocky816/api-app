package com.tutorial.crud.service;

import com.tutorial.crud.entity.Banda;
import com.tutorial.crud.repository.BandaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BandaService {

    @Autowired
    BandaRepository bandaRepository;

    public List<Banda> list(){
        return bandaRepository.findAll();
    }

    public Optional<Banda> getOne(int id){
        return bandaRepository.findById(id);
    }

    public Banda  save(Banda actividad){
    	return bandaRepository.save(actividad);
    }

}
