package com.tutorial.crud.service;

import com.tutorial.crud.entity.TipoReto;
import com.tutorial.crud.repository.TipoRetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class TipoRetoService {
    @Autowired
    TipoRetoRepository tipoRetoRepository;

    public List<TipoReto> list(){
        return tipoRetoRepository.findAll();
    }

    public Optional<TipoReto> getOne(UUID id){
        return tipoRetoRepository.findById(id);
    }

    public TipoReto save(TipoReto tipoReto){
        return tipoRetoRepository.save(tipoReto);
    }

    public List<TipoReto> getByActivo(boolean activo) {
        return tipoRetoRepository.findByActivo(activo).get();
    }

    public Optional<TipoReto> getByNombre(String nombre) {
        return tipoRetoRepository.findByNombre(nombre);
    }
}
