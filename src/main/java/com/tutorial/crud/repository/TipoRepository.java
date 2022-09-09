package com.tutorial.crud.repository;

import com.tutorial.crud.entity.Tipo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TipoRepository extends JpaRepository<Tipo, UUID> {

	Optional<List<Tipo>> findByActivo(boolean activo);

	Optional<Tipo> findByNombre(String nombre);

}
