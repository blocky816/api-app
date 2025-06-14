package com.tutorial.crud.repository;

import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.FormularioRespuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormularioRespuestaRepository extends JpaRepository<FormularioRespuesta, Integer> {
    List<FormularioRespuesta> findByClienteAndFolio(Cliente customer, int formFolio);

    @Query(value = "SELECT * FROM public.formularios_respuestas where idcliente = ?1 and created = " +
            "(select max(created) from formularios_respuestas where idcliente = ?1 and folio = ?2)",
            nativeQuery = true)
    List<FormularioRespuesta> findLastAnswersFormByCustomer(int customer, int formFolio);
}
