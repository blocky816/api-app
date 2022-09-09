package com.tutorial.crud.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorial.crud.entity.Referencia;

import java.util.UUID;

@Repository
public interface ReferenciaRepository extends JpaRepository<Referencia, UUID> {


}
