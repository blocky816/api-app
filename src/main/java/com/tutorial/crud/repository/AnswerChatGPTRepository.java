package com.tutorial.crud.repository;

import com.tutorial.crud.entity.AnswerChatGPT;
import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.FormularioRespuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AnswerChatGPTRepository extends JpaRepository<AnswerChatGPT, Integer> {
    @Query(value = "SELECT * FROM public.formularios_respuestas where idcliente = ?1 and created = " +
            "(select max(created) from formularios_respuestas where idcliente = ?1 and folio = ?2)",
            nativeQuery = true)
    List<FormularioRespuesta> findIfExistsDietInCurrentMonth(int customerID, int formFolio);

    List<AnswerChatGPT> findByCustomerAndCreatedAtBetweenOrderByCreatedAtDesc(Cliente customer, LocalDateTime startDate, LocalDateTime endDate);
}
