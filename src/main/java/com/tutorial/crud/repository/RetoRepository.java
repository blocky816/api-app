package com.tutorial.crud.repository;

import com.tutorial.crud.entity.Reto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RetoRepository extends JpaRepository<Reto, UUID> {

	Optional<List<Reto>> findByActivo(boolean activo);

	Optional<Reto> findByNombre(String nombre);

	//validar si un cliente ya existe en la bd
	/*@Query(value = "",
			nativeQuery = true)
	List<Tuple> getRetoUsuarioDTO(Integer club);*/
}
