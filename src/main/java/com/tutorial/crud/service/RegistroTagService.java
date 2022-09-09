package com.tutorial.crud.service;

import com.tutorial.crud.entity.ParkingUsuario;
import com.tutorial.crud.entity.RegistroTag;
import com.tutorial.crud.repository.RegistroTagRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class RegistroTagService {

    @Autowired
    RegistroTagRepository registroTagRepository;

    public List<RegistroTag> list(){
        return registroTagRepository.findAll();
    }

    public Optional<RegistroTag> getOne(int id){
        return registroTagRepository.findById(id);
    }

    public RegistroTag  save(RegistroTag actividad){
    	return registroTagRepository.save(actividad);
    }

    public RegistroTag findByIdChip(long idChip) {
    	return registroTagRepository.findByIdChip(idChip);
    }
    public RegistroTag findByParking(ParkingUsuario idParking) {
    	return registroTagRepository.findByParking(idParking);
    }
    
}
