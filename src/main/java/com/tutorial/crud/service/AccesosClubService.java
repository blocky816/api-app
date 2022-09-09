package com.tutorial.crud.service;

import com.tutorial.crud.entity.AccesosClub;
import com.tutorial.crud.repository.AccesosClubRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AccesosClubService {

    @Autowired
    AccesosClubRepository accesosClubRepository;


    public Optional<AccesosClub> getOne(UUID id){
        return accesosClubRepository.findById(id);
    }

    public AccesosClub  save(AccesosClub actividad){
    	return accesosClubRepository.save(actividad);
    }
}
