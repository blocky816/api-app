package com.tutorial.crud.service;

import com.tutorial.crud.entity.EjercicioNuevo;
import com.tutorial.crud.repository.EjercicioNuevoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class EjercicioNuevoService {

    @Autowired
    EjercicioNuevoRepository ejercicioNuevoRepository;

    public void save(EjercicioNuevo ejercicioNuevo){
        ejercicioNuevoRepository.save(ejercicioNuevo);
    }

    public Optional<EjercicioNuevo> findById(int id){
        return ejercicioNuevoRepository.findById(id);
    }

    //metodo para devolver todos los ejercicios activos
    //metodo para desactivar un ejercicio
}
