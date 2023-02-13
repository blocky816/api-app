package com.tutorial.crud.repository;

import com.tutorial.crud.entity.ConfiguracionSancion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ConfiguracionSancionRepository extends JpaRepository<ConfiguracionSancion, Integer> {
    ConfiguracionSancion findByConcepto(String concepto);
}
