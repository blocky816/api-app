package com.tutorial.crud.repository;

import com.tutorial.crud.entity.OrdenAlpha;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrdenAlphaRepository extends JpaRepository<OrdenAlpha, Integer> {

}
