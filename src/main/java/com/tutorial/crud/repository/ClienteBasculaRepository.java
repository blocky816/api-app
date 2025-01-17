package com.tutorial.crud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorial.crud.entity.ClienteBascula;

@Repository
public interface ClienteBasculaRepository extends JpaRepository<ClienteBascula, Integer> {


	List<ClienteBascula> findByIdUsuario(int idUsuario);
	List<ClienteBascula> findTop3ByIdUsuarioOrderByFechaCapturaDesc(int idUsuario);
}
