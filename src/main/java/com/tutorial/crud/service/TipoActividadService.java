package com.tutorial.crud.service;

import com.tutorial.crud.entity.CATipoActividad;
import com.tutorial.crud.repository.TipoActividadRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class TipoActividadService {

    @Autowired
    TipoActividadRepository tipoActividadRepository;

    public List<CATipoActividad> list(){
        return tipoActividadRepository.findAll();
    }

    public List<CATipoActividad> getByActivo(boolean activo){
    	return tipoActividadRepository.findByActivo(activo).get();
    }
    public Optional<CATipoActividad> getOne(UUID id){
        return tipoActividadRepository.findById(id);
    }

    public Optional<CATipoActividad> getByNombre(String nombre){
        return tipoActividadRepository.findByNombre(nombre);
    }

    public CATipoActividad  save(CATipoActividad tipoActividad){
    	return tipoActividadRepository.save(tipoActividad);
    }
}
