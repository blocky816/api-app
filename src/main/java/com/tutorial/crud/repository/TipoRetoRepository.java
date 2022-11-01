package com.tutorial.crud.repository;

import com.tutorial.crud.entity.TipoReto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TipoRetoRepository extends JpaRepository<TipoReto, UUID> {
    Optional<List<TipoReto>> findByActivo(boolean activo);
    Optional<TipoReto> findByNombre(String nombre);
}
