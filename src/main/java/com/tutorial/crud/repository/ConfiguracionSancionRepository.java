package com.tutorial.crud.repository;

import com.tutorial.crud.entity.ConfiguracionSancion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ConfiguracionSancionRepository extends JpaRepository<ConfiguracionSancion, Integer> {
    ConfiguracionSancion findByConcepto(String concepto);

    @Query(value = "SELECT codigo FROM configuracion_sancion WHERE LOWER(concepto) LIKE LOWER(CONCAT('%', ?1, '%'))",
            nativeQuery = true)
    Set<Integer> getCodigoByConcepto(String concepto);
}
