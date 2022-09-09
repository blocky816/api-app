package com.tutorial.crud.service;

import com.tutorial.crud.entity.PaseUsuario;
import com.tutorial.crud.entity.RegistroSalidaSP;
import com.tutorial.crud.entity.TipoAcceso;
import com.tutorial.crud.repository.RegistroSalidaSPRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class RegistroSalidaSPService {

    @Autowired
    RegistroSalidaSPRepository registroSalidaSPRepository;


    public Optional<RegistroSalidaSP> getOne(int id){
        return registroSalidaSPRepository.findById(id);
    }

    public RegistroSalidaSP  save(RegistroSalidaSP registroParking){
    	return registroSalidaSPRepository.save(registroParking);
    }
    
    public List<RegistroSalidaSP> getByAcceso(boolean activo) {
    	return registroSalidaSPRepository.findByAcceso(activo);
	}
    public List<RegistroSalidaSP> getByFolio(int folio) {
    	return registroSalidaSPRepository.findByFolio(folio);
	}
    public List<RegistroSalidaSP> getByTipoAcceso(TipoAcceso activo) {
    	return registroSalidaSPRepository.findByTipoAcceso(activo);
	}

}
