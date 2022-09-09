package com.tutorial.crud.service;

import com.tutorial.crud.entity.Pluma;
import com.tutorial.crud.repository.PlumaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PlumaService {

    @Autowired
    PlumaRepository plumaRepository;

    public List<Pluma> list(){
        return plumaRepository.findAll();
    }

    public Optional<Pluma> getOne(int id){
        return plumaRepository.findById(id);
    }

    public Pluma  save(Pluma actividad){
    	return plumaRepository.save(actividad);
    }

    public Optional<List<Pluma>> findByClub(String club){
    	return plumaRepository.findByClub(club);
    }
}
