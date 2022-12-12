package com.tutorial.crud.repository;

import com.tutorial.crud.entity.Toalla;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ToallaRepository extends JpaRepository<Toalla, UUID> {

    public boolean existsByIdClienteAndFechaInicio(int idCliente, Date fechaInicio);

    public Toalla getByIdClienteAndFechaInicio(int idCliente, Date fechaInicio);

}