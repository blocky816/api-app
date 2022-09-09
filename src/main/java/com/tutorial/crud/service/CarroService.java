package com.tutorial.crud.service;

import com.tutorial.crud.entity.Carro;
import com.tutorial.crud.repository.CarroRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CarroService {

    @Autowired
    CarroRepository carroRepository;

    public List<Carro> list(){
        return carroRepository.findAll();
    }

    public Optional<Carro> getOne(int id){
        return carroRepository.findById(id);
    }

    public Carro  save(Carro carro){
    	return carroRepository.save(carro);
    }
}
