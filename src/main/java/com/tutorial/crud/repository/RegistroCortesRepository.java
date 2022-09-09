package com.tutorial.crud.repository;

import com.tutorial.crud.entity.RegistroCortes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RegistroCortesRepository extends JpaRepository<RegistroCortes, Integer> {
	public RegistroCortes findByIdCorte(String id);

}
