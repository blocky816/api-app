package com.tutorial.crud.repository;

import com.tutorial.crud.entity.CitaAsesorDeportivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaAsesorDeportivoRepository extends JpaRepository<CitaAsesorDeportivo, Long> {
}
