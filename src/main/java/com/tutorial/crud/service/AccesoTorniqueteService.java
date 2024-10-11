package com.tutorial.crud.service;

import com.tutorial.crud.entity.AccesoTorniquete;
import com.tutorial.crud.repository.AccesoTorniqueteRepository;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class AccesoTorniqueteService {
    private static final Logger logger = Logger.getLogger(AccesoTorniqueteService.class.getName());
    private final AccesoTorniqueteRepository accesoTorniqueteRepository;

    public AccesoTorniqueteService(AccesoTorniqueteRepository accesoTorniqueteRepository) {
        this.accesoTorniqueteRepository = accesoTorniqueteRepository;
    }

    public String guardarAccesoTorniquete(AccesoTorniquete accesoTorniquete) {
        try {
            AccesoTorniquete acceso = accesoTorniqueteRepository.save(accesoTorniquete);
            return "Acceso guardado"; // Mensaje de éxito
        } catch (Exception e) {
            return "Error al guardar acceso: " + e.getMessage(); // Mensaje de error
        }
    }
}
