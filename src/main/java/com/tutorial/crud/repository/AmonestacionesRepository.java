package com.tutorial.crud.repository;

import com.tutorial.crud.entity.Amonestaciones;
import com.tutorial.crud.entity.CAActividad;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AmonestacionesRepository extends JpaRepository<Amonestaciones, Integer> {

	Optional<List<Amonestaciones>> findByIdChip(long idCHip);


}
