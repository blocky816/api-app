package com.tutorial.crud.service;

import com.tutorial.crud.entity.RHEmpleado;
import com.tutorial.crud.entity.RHFirma;
import com.tutorial.crud.entity.RHSolicitud;
import com.tutorial.crud.repository.RHFirmaRepository;
import com.tutorial.crud.repository.RHSolicitudRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RHFirmaService {

    @Autowired
    RHFirmaRepository rhfirmaRepository;

    public List<RHFirma> list(){
        return rhfirmaRepository.findAll();
    }

    public Optional<RHFirma> getOne(int id){
        return rhfirmaRepository.findById(id);
    }

    public RHFirma  save(RHFirma solicitud){
    	return rhfirmaRepository.save(solicitud);
    }

    public RHFirma findByIdSolicitud(RHSolicitud solicitud) {
    	return rhfirmaRepository.findBySolicitud(solicitud);
    }

}
