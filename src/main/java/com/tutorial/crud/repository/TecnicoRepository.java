package com.tutorial.crud.repository;

import com.tutorial.crud.entity.CATecnico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TecnicoRepository extends JpaRepository<CATecnico, UUID> {

	Optional<List<CATecnico>> findByActivo(boolean activo);

	Optional<CATecnico> findByNombre(String nombre);

}
