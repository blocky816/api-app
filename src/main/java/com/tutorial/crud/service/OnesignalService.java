package com.tutorial.crud.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.UsuarioOnesignal;
import com.tutorial.crud.repository.UsuarioOnesignalRepository;

@Service
public class OnesignalService {
    @Autowired
    private UsuarioOnesignalRepository usuarioOnesignalRepository;

    public ResponseEntity<?> registrarUsuario(Integer externalId, UsuarioOnesignal usuario) {
        if(externalId  == null || usuario.getOnesignalId() == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "El id del usuario y el id de onesignal son obligatorios"));
        }

        List<UsuarioOnesignal> usuariosEncontrados = usuarioOnesignalRepository.findByPushSubscriptionId(usuario.getPushSubscriptionId()); // buscamos las subscripciones de un celular 
        if(!usuariosEncontrados.isEmpty()) {
            UsuarioOnesignal usuarioEncontrado = usuariosEncontrados.stream().max((u1, u2) -> u1.getFechaModificacion().compareTo(u2.getFechaModificacion()))
                .orElse(null);
            if(usuarioEncontrado != null) {
                usuarioEncontrado.setSuscrito(usuario.getSuscrito());
                var cliente = new Cliente();
                cliente.setIdCliente(externalId);
                usuarioEncontrado.setClienteId(cliente);
                usuarioEncontrado.setFechaModificacion(LocalDateTime.now());
                usuarioOnesignalRepository.save(usuarioEncontrado);
            }
            return ResponseEntity.ok(Map.of("msg", "Usuario actualizado correctamente"));
        }

        UsuarioOnesignal usuarioOnesignal = new UsuarioOnesignal(externalId, usuario);

        usuarioOnesignalRepository.save(usuarioOnesignal);

        return ResponseEntity.ok(Map.of("msg", "Usuario registrado correctamente"));
    }

}
