package com.tutorial.crud.service;

import com.tutorial.crud.entity.EjercicioBusqueda;
import com.tutorial.crud.repository.EjercicioBusquedaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EjercicioBusquedaService {

    @Autowired
    EjercicioBusquedaRepository ejercicioBusquedaRepository;

    public EjercicioBusqueda save(EjercicioBusqueda ejercicioBusqueda) {
        return ejercicioBusquedaRepository.save(ejercicioBusqueda);
    }

    public EjercicioBusqueda findById(int id) {
        Optional<EjercicioBusqueda> ejercicio = ejercicioBusquedaRepository.findById(id);
        if (ejercicio.isPresent()) {
            return ejercicio.get();
        } else {
            throw new NullPointerException("El ejercicio no existe.");
        }
    }

    public void updateEjercicioBusqueda(int id, EjercicioBusqueda request) {
        Optional<EjercicioBusqueda> ejercicio = ejercicioBusquedaRepository.findById(id);
        if (ejercicio.isPresent()){
            EjercicioBusqueda update = ejercicio.get();
            update.setNombre(request.getNombre());
            update.setNivel(request.getNivel());
            update.setTipo(request.getTipo());
            update.setUrl(request.getUrl());
            ejercicioBusquedaRepository.save(update);
        } else {
            throw new NullPointerException("El ejercicio no existe.");
        }
    }

    public void deleteEjercicioBusqueda(int id) {
        Optional<EjercicioBusqueda> ejercicio = ejercicioBusquedaRepository.findById(id);
        if (ejercicio.isPresent() && ejercicio.get().getActivo().equals("true")){
            EjercicioBusqueda update = ejercicio.get();
            update.setActivo("false");
            ejercicioBusquedaRepository.save(update);
        } else {
            throw new NullPointerException("El ejercicio no existe.");
        }
    }

    public List<EjercicioBusqueda> findAll() {
        return ejercicioBusquedaRepository.findAllByActivo("true");
    }
}
