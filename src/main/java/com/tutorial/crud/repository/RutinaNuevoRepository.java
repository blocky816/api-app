package com.tutorial.crud.repository;

import com.tutorial.crud.entity.RutinaNuevo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RutinaNuevoRepository extends JpaRepository<RutinaNuevo, Integer> {
    List<RutinaNuevo> findAllByActivo(Boolean activo);


    @Query(value = "select * from rutinas_nuevo where activo = True and tipo = 'General' order by lower(nombre)", nativeQuery = true)
    List<RutinaNuevo> findAllByActivoAndTipoPlantilla();

    List<RutinaNuevo> findBySegmentoAndTipoPlantillaAndActivo(String segmento, String tipoPlantilla, Boolean activo);
}
