package com.tutorial.crud.service;

import com.tutorial.crud.entity.RutinaNuevo;
import com.tutorial.crud.repository.RutinaNuevoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RutinaNuevoService {

    @Autowired
    RutinaNuevoRepository rutinaNuevoRepository;

    //metodo save, getOne y delete
    public void save(RutinaNuevo rutinaNuevo){
        rutinaNuevoRepository.save(rutinaNuevo);
    }

    public List<RutinaNuevo> findAllByActivo(Boolean activo) {
        return rutinaNuevoRepository.findAllByActivo(activo);
    }

    public Optional<RutinaNuevo> findById(int id){
        return rutinaNuevoRepository.findById(id);
    }

    public List<RutinaNuevo> findAllByActivoAndTipoPlantilla() {
        return rutinaNuevoRepository.findAllByActivoAndTipoPlantilla();
    }
}
