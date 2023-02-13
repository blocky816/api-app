package com.tutorial.crud.service;

import com.tutorial.crud.entity.ConfiguracionSancion;
import com.tutorial.crud.repository.ConfiguracionSancionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ConfiguracionSancionService {

    @Autowired
    ConfiguracionSancionRepository configuracionSancionRepository;

    public ConfiguracionSancion findByConcepto(String concepto) {
        return configuracionSancionRepository.findByConcepto(concepto);
    }
}
