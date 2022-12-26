package com.tutorial.crud.service;

import com.tutorial.crud.entity.ConversacionAsesor;
import com.tutorial.crud.repository.ConversacionAsesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ConversacionAsesorService {

    @Autowired
    ConversacionAsesorRepository conversacionAsesorRepository;

    public void save(ConversacionAsesor conversacionAsesor){
        conversacionAsesorRepository.save(conversacionAsesor);
    }

     public List<ConversacionAsesor> findAllByFolioAndActivoOrderByFechaAsc(int idCliente, Boolean activo){
        return conversacionAsesorRepository.findAllByFolioAndActivoOrderByFechaAsc(idCliente, activo);
     }
}
