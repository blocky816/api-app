package com.tutorial.crud.repository;

import com.tutorial.crud.entity.Encuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EncuestaRepository extends JpaRepository<Encuesta, Long> {
    public List<Encuesta> findAllByIdClienteOdooAndEncuestasIsNotNullAndRespuestasIsNullOrderByFechaEncuestasAsc(int idOdoo);
    public List<Encuesta> findAllByIdClienteOdooAndIdSurveyAndEncuestasIsNotNullAndPreguntasIsNotNullAndRespuestasIsNullOrderByFechaPreguntasAsc(int idOdoo, int idSurvey);
    public void deleteAllByIdClienteOdooAndRespuestasIsNull(int idOdoo);
    public void deleteAllByIdClienteOdooAndEncuestasIsNotNullAndPreguntasIsNullAndRespuestasIsNull(int idOdoo);
    public void deleteAllByIdClienteOdooAndIdSurveyAndEncuestasIsNotNullAndPreguntasIsNotNullAndRespuestasIsNull(int idOdoo, int idSurvey);


}
