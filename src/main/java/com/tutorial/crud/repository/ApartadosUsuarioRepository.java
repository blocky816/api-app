package com.tutorial.crud.repository;

import com.tutorial.crud.entity.CAApartados;
import com.tutorial.crud.entity.CAApartadosUsuario;
import com.tutorial.crud.entity.CAHorario;
import com.tutorial.crud.entity.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import javax.transaction.Transactional;

@Repository
public interface ApartadosUsuarioRepository extends JpaRepository<CAApartadosUsuario, UUID> {
			
}
