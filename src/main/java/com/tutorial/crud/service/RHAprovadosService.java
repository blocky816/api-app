package com.tutorial.crud.service;

import com.tutorial.crud.entity.RHAprovados;
import com.tutorial.crud.entity.RHEmpleado;
import com.tutorial.crud.entity.RHFirma;
import com.tutorial.crud.entity.RHSolicitud;
import com.tutorial.crud.repository.RHAprovadosRepository;
import com.tutorial.crud.repository.RHSolicitudRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RHAprovadosService {

    @Autowired
    RHAprovadosRepository rhsaprovadosRepository;

    public List<RHAprovados> list(){
        return rhsaprovadosRepository.findAll();
    }

    public Optional<RHAprovados> getOne(int id){
        return rhsaprovadosRepository.findById(id);
    }

    public RHAprovados  save(RHAprovados solicitud){
    	return rhsaprovadosRepository.save(solicitud);
    }

    public RHAprovados findByIdSolicitud(RHSolicitud solicitud) {
    	return rhsaprovadosRepository.findBySolicitud(solicitud);
    }
}
