package com.tutorial.crud.repository;

import com.tutorial.crud.entity.RutinaEjercicioNuevo;
import com.tutorial.crud.entity.RutinaNuevo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RutinaEjercicioNuevoRepository extends JpaRepository<RutinaEjercicioNuevo, Integer> {
    List<RutinaEjercicioNuevo> findAllByRutinanuevo(RutinaNuevo rutina);

    public void deleteByRutinanuevo(RutinaNuevo rutinaNuevo);
}
