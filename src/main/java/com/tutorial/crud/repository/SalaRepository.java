package com.tutorial.crud.repository;

import com.tutorial.crud.entity.CASala;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SalaRepository extends JpaRepository<CASala, UUID> {
	CASala findByNombre(String nombre);

	Optional<List<CASala>> findByActivo(boolean activo);

}
