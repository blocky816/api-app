package com.tutorial.crud.service;

import com.tutorial.crud.entity.Antena;
import com.tutorial.crud.repository.AntenaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AntenaService {

    @Autowired
    AntenaRepository antenaRepository;

    public List<Antena> list(){
        return antenaRepository.findAll();
    }

    public Optional<Antena> getOne(int id){
        return antenaRepository.findById(id);
    }

    public Antena  save(Antena antena){
    	return antenaRepository.save(antena);
    }
}
