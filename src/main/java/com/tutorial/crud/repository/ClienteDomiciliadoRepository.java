package com.tutorial.crud.repository;

import com.tutorial.crud.entity.ClienteDomiciliado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClienteDomiciliadoRepository extends JpaRepository<ClienteDomiciliado, Integer> {


}
