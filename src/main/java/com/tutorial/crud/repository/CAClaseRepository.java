package com.tutorial.crud.repository;

import com.tutorial.crud.entity.CAClase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CAClaseRepository extends JpaRepository<CAClase, UUID> {
    @Query(value = "SELECT cast(id as text), nombre, tecnico, tipo_actividad, color, lugar, duracion, nivel, hora, " +
            "cupo_actual, cupo_maximo, rango, disponible, dia, paga, cast(id_apartados as text), club " +
            "FROM clases " +
            "WHERE dia = :dia " +
            "AND (club = 'Sports Plaza' and nombre LIKE '%GIMNASIO%') " +
            "AND disponible = true " +
            //"AND TO_TIMESTAMP(dia || ' ' || split_part(rango, '-', 2), 'YYYY-MM-DD HH24:MI') > CURRENT_TIMESTAMP " +
            "ORDER BY TO_TIMESTAMP(split_part(rango, '-', 1), 'HH24:MI')", nativeQuery = true)
    List<CAClase> getAquadomeClasses(@Param("dia") String dia);
}
