package com.tutorial.crud.repository;

import com.tutorial.crud.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    public Categoria findFirstByNombreOrderByIdAsc(String name);
}
