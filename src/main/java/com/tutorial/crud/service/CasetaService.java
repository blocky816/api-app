package com.tutorial.crud.service;

import com.tutorial.crud.entity.Antena;
import com.tutorial.crud.entity.Caseta;
import com.tutorial.crud.repository.AntenaRepository;
import com.tutorial.crud.repository.CasetaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CasetaService {

    @Autowired
    CasetaRepository casetaRepository;

    public List<Caseta> list(){
        return casetaRepository.findAll();
    }

    public Optional<Caseta> getOne(int id){
        return casetaRepository.findById(id);
    }

    public Caseta  save(Caseta antena){
    	return casetaRepository.save(antena);
    }
}
