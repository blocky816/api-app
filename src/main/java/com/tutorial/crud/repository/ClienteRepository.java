package com.tutorial.crud.repository;

import com.tutorial.crud.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Cliente findByIdCliente(int customerID);
}

