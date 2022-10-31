package com.tutorial.crud.repository;

import com.tutorial.crud.entity.ClienteIntentosFiserv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteIntentosFiservRepository extends JpaRepository<ClienteIntentosFiserv, Integer> {
    @Query(value = "select max(folio) from cliente_intentos_fiserv",
            nativeQuery = true)
    Integer getLastFolio();
}
