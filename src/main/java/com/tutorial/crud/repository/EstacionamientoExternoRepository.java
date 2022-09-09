package com.tutorial.crud.repository;

import com.tutorial.crud.entity.EstacionamientoExterno;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EstacionamientoExternoRepository extends JpaRepository<EstacionamientoExterno, UUID> {

	EstacionamientoExterno findByIdRegistro(String idRegistro);


}
