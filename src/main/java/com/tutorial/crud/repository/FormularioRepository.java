package com.tutorial.crud.repository;

import com.tutorial.crud.entity.Formulario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormularioRepository extends JpaRepository<Formulario, Integer> {
    List<Formulario> findAllByFolioAndActivo(int folio, Boolean activo);

    @Query(value = "select max(folio) from formularios", nativeQuery = true)
    Integer getLastFormularioId();

    Boolean existsByFolioAndActivo(int folio, boolean activo);

    Formulario findTopByFolio(int folio);
}
