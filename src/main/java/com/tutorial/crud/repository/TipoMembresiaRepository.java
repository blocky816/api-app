package com.tutorial.crud.repository;

import com.tutorial.crud.entity.TipoMembresia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoMembresiaRepository extends JpaRepository<TipoMembresia, Integer> {
    public TipoMembresia findFirstByNombreOrderByIdTipoMembresiaAsc(String name);
}
