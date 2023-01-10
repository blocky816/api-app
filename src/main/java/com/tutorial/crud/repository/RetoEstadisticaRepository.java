package com.tutorial.crud.repository;

import com.tutorial.crud.entity.RetoEstadistica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RetoEstadisticaRepository extends JpaRepository<RetoEstadistica, UUID> {
}
