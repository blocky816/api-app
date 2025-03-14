package com.tutorial.crud.Odoo.Spec.repository;

import com.tutorial.crud.Odoo.Spec.entity.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreguntaRepository extends JpaRepository<Pregunta, Long> {
    public List<Pregunta> findByDescripcionIn(List<String> preguntasTexto);
}
