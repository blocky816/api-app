package com.tutorial.crud.repository;

import com.tutorial.crud.entity.PaseConsumido;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PaseConsumidoRepository extends JpaRepository<PaseConsumido, UUID> {
}
