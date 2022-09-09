package com.tutorial.crud.repository;

import com.tutorial.crud.entity.Enlace;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EnlaceRepository extends JpaRepository<Enlace, Integer> {

}
