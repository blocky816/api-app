package com.tutorial.crud.repository;

import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.Club;
import com.tutorial.crud.entity.EstatusCobranza;
import com.tutorial.crud.entity.TipoMembresia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Cliente findByIdCliente(int customerID);

    List<Cliente> findAllByClubAndTipoMembresiaAndEstatusCobranza(Club club, TipoMembresia membership, EstatusCobranza collection);
}

