package com.tutorial.crud.repository;

import com.tutorial.crud.entity.Pluma;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlumaRepository extends JpaRepository<Pluma, Integer> {

	Optional<List<Pluma>> findByClub(String club);
}
