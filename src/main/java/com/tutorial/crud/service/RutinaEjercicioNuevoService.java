package com.tutorial.crud.service;


import com.tutorial.crud.entity.RutinaEjercicioNuevo;
import com.tutorial.crud.entity.RutinaNuevo;
import com.tutorial.crud.repository.RutinaEjercicioNuevoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RutinaEjercicioNuevoService {

    @Autowired
    RutinaEjercicioNuevoRepository rutinaEjercicioNuevoRepository;

    //metodos save, getOne y delete
    public List<RutinaEjercicioNuevo> findAllByRutinanuevo(RutinaNuevo rutina){
        return rutinaEjercicioNuevoRepository.findAllByRutinanuevo(rutina);
    }

    public void deleteByRutinanuevo(RutinaNuevo rutinaNuevo){
        rutinaEjercicioNuevoRepository.deleteByRutinanuevo(rutinaNuevo);
    }
}
