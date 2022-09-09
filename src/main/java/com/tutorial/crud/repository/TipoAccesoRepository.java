package com.tutorial.crud.repository;

import com.tutorial.crud.entity.TipoAcceso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TipoAccesoRepository extends JpaRepository<TipoAcceso, Integer> {

}
