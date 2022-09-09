package com.tutorial.crud.repository;

import com.tutorial.crud.entity.CAApartados;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApartadosRepository extends JpaRepository<CAApartados, UUID> {

	Optional<List<CAApartados>> findByActivo(boolean activo);

}
