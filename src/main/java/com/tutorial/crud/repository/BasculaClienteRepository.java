package com.tutorial.crud.repository;

import com.tutorial.crud.entity.BasculaCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BasculaClienteRepository extends JpaRepository<BasculaCliente, UUID> {

    public boolean existsByIdCliente(int idCliente);

    public BasculaCliente findByIdCliente(int idCliente);
}
