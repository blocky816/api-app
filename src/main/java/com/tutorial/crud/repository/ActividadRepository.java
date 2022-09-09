package com.tutorial.crud.repository;

import com.tutorial.crud.entity.CAActividad;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ActividadRepository extends JpaRepository<CAActividad, UUID> {

	Optional<List<CAActividad>> findByActivo(boolean activo);

	Optional<CAActividad> findByNombre(String nombre);

}
