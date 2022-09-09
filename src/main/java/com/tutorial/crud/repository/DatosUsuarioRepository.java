package com.tutorial.crud.repository;

import com.tutorial.crud.entity.DatosUsuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DatosUsuarioRepository extends JpaRepository<DatosUsuario, UUID> {


	List<DatosUsuario> findByIdCliente(int idCliente);
}
