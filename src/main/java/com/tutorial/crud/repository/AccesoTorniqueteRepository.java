package com.tutorial.crud.repository;

import com.tutorial.crud.entity.AccesoTorniquete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccesoTorniqueteRepository extends JpaRepository<AccesoTorniquete, Integer> {
}
