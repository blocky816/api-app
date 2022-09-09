package com.tutorial.crud.repository;

import com.tutorial.crud.entity.EstadoDeCobranza;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadoDeCobranzaRepository extends JpaRepository<EstadoDeCobranza, Integer> {
	Optional<EstadoDeCobranza> findByMes(String mes);

}
