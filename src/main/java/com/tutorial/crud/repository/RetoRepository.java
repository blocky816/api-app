package com.tutorial.crud.repository;

import com.tutorial.crud.entity.Reto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RetoRepository extends JpaRepository<Reto, UUID> {

	Optional<List<Reto>> findByActivo(boolean activo);

	Optional<Reto> findByNombre(String nombre);

}
