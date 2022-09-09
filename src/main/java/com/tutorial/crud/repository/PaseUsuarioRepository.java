package com.tutorial.crud.repository;

import com.tutorial.crud.entity.PaseUsuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaseUsuarioRepository extends JpaRepository<PaseUsuario, Integer> {

	Optional<List<PaseUsuario>> findByActivo(boolean activo);
}
