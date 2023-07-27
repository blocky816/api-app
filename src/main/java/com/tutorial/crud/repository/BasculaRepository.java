package com.tutorial.crud.repository;

import com.tutorial.crud.entity.Bascula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasculaRepository extends JpaRepository<Bascula, Integer> {
}
