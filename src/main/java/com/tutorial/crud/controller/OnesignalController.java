package com.tutorial.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tutorial.crud.dto.UsuarioOnesignalDto;
import com.tutorial.crud.entity.UsuarioOnesignal;
import com.tutorial.crud.service.OnesignalService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/notificaciones/onesignal")
public class OnesignalController {

    @Autowired
    private OnesignalService onesignalService;

    @PostMapping("/registrar-subscripcion/{externalId}")
    public ResponseEntity<?> guardarUsuario(@PathVariable("externalId") Integer externalId, @RequestBody UsuarioOnesignal usuario) {
        
        return onesignalService.registrarUsuario(externalId, usuario);
    }
    
}
