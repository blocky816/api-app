package com.tutorial.crud.repository;

import com.tutorial.crud.entity.ClienteBascula;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteBasculaRepository extends JpaRepository<ClienteBascula, Integer> {


	List<ClienteBascula> findByIdUsuario(int idUsuario);
}
