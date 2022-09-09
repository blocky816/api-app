package com.tutorial.crud.repository;

import com.tutorial.crud.entity.EntrenamientoUsuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EntrenamientoUsuarioRepository extends JpaRepository<EntrenamientoUsuario, UUID> {


}
