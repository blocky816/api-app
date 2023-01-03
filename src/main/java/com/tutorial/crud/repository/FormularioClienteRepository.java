package com.tutorial.crud.repository;

import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.FormularioCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormularioClienteRepository extends JpaRepository<FormularioCliente, Integer> {
    Boolean existsByFolioAndClienteAndActivo(int folio, Cliente cliente, Boolean activo);

    List<FormularioCliente> findByFolioAndActivo(int folio, Boolean activo);
}
