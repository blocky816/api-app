package com.tutorial.crud.repository;

import com.tutorial.crud.entity.EjercicioBusqueda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EjercicioBusquedaRepository extends JpaRepository<EjercicioBusqueda, Integer> {
    List<EjercicioBusqueda> findAllByActivo(String activo);
}
