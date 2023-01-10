package com.tutorial.crud.service;

import com.tutorial.crud.entity.RetoAcumulable;
import com.tutorial.crud.repository.RetoAcumulableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RetoAcumulableService {
    @Autowired
    RetoAcumulableRepository retoAcumulableRepository;

    /*
    public Optional<RetoUsuario> getOne(UUID id){
        return retoUsuarioRepository.findById(id);
    }*/

    public List<RetoAcumulable> list(){
        return retoAcumulableRepository.findAll();
    }

    public RetoAcumulable save(RetoAcumulable retoAcumulable){
        return retoAcumulableRepository.save(retoAcumulable);
    }
}
