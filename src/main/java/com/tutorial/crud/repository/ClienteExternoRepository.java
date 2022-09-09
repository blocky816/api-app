package com.tutorial.crud.repository;

import com.tutorial.crud.entity.ClienteExterno;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteExternoRepository extends JpaRepository<ClienteExterno, Integer> {

}
