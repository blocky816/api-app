package com.tutorial.crud.service;

import com.tutorial.crud.entity.RegistroParking;
import com.tutorial.crud.repository.RegistroParkingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class RegistroParkingService {

    @Autowired
    RegistroParkingRepository registroParkingRepository;

    public List<RegistroParking> list(){
        return registroParkingRepository.findAll();
    }

    public Optional<RegistroParking> getOne(int id){
        return registroParkingRepository.findById(id);
    }

    public RegistroParking  save(RegistroParking registroParking){
    	return registroParkingRepository.save(registroParking);
    }

}
