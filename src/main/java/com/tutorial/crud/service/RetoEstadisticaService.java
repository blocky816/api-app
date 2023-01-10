package com.tutorial.crud.service;

import com.tutorial.crud.entity.RetoEstadistica;
import com.tutorial.crud.repository.RetoEstadisticaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RetoEstadisticaService {
    @Autowired
    RetoEstadisticaRepository retoEstadisticaRepository;

    public RetoEstadistica save(RetoEstadistica retoEstadistica){
        return retoEstadisticaRepository.save(retoEstadistica);
    }
}
