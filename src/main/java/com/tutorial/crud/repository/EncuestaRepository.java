package com.tutorial.crud.repository;

import com.tutorial.crud.entity.Encuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EncuestaRepository extends JpaRepository<Encuesta, Long> {
    public List<Encuesta> findAllByIdClienteAndEncuestasIsNotNullAndPreguntasIsNullAndRespuestasIsNullOrderByFechaEncuestasAsc(int idCliente);
    public List<Encuesta> findAllByIdClienteAndIdSurveyAndEncuestasIsNotNullAndPreguntasIsNotNullAndRespuestasIsNullOrderByFechaPreguntasAsc(int idCliente, int idSurvey);
}
