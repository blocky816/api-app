package com.tutorial.crud.Odoo.Spec.repository;

import com.tutorial.crud.Odoo.Spec.dto.PaseReporteDTO;
import com.tutorial.crud.Odoo.Spec.entity.PaseHealthStudioConsumido;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PasesHealthStudioConsumidoRepository extends JpaRepository<PaseHealthStudioConsumido, Long> {
    @Query("SELECT COUNT(puc) " +
            "FROM PaseHealthStudioConsumido puc " +
            "WHERE puc.fechaConsumo BETWEEN :startDate AND :endDate " +
            "AND (LOWER(puc.paseUsuario.concepto) LIKE %:keyword1% OR " +
            "LOWER(puc.paseUsuario.concepto) LIKE %:keyword2% OR " +
            "LOWER(puc.paseUsuario.concepto) LIKE %:keyword3% OR " +
            "LOWER(puc.paseUsuario.concepto) LIKE %:keyword4% OR " +
            "LOWER(puc.paseUsuario.concepto) LIKE %:keyword5% OR " +
            "LOWER(puc.paseUsuario.concepto) LIKE %:keyword6% OR " +
            "LOWER(puc.paseUsuario.concepto) LIKE %:keyword7% OR " +
            "LOWER(FUNCTION('unaccent', puc.paseUsuario.concepto)) LIKE LOWER(FUNCTION('unaccent', :keyword8)) )")
    long countConsumosByFecha(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("keyword1") String keyword1,
            @Param("keyword2") String keyword2,
            @Param("keyword3") String keyword3,
            @Param("keyword4") String keyword4,
            @Param("keyword5") String keyword5,
            @Param("keyword6") String keyword6,
            @Param("keyword7") String keyword7,
            @Param("keyword8") String keyword8);

    @Query("SELECT new com.tutorial.crud.Odoo.Spec.dto.PaseReporteDTO(" +
            "puc.paseUsuario.cliente.idCliente, " +
            "puc.paseUsuario.cliente.NombreCompleto, " +
            "puc.paseUsuario.concepto, " +
            "puc.fechaConsumo, " +
            "puc.consumidoPor) " +
            "FROM PaseHealthStudioConsumido puc " +
            "WHERE puc.fechaConsumo BETWEEN :startDate AND :endDate " +
            "AND (LOWER(puc.paseUsuario.concepto) LIKE %:keyword1% " +
            "OR LOWER(puc.paseUsuario.concepto) LIKE %:keyword2% " +
            "OR LOWER(puc.paseUsuario.concepto) LIKE %:keyword3% " +
            "OR LOWER(puc.paseUsuario.concepto) LIKE %:keyword4% " +
            "OR LOWER(puc.paseUsuario.concepto) LIKE %:keyword5% " +
            "OR LOWER(puc.paseUsuario.concepto) LIKE %:keyword6% " +
            "OR LOWER(puc.paseUsuario.concepto) LIKE %:keyword7% " +
            "OR LOWER(FUNCTION('unaccent', puc.paseUsuario.concepto)) LIKE LOWER(FUNCTION('unaccent', :keyword8)) )")
    List<PaseReporteDTO> findConsumosByFechaPaged(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("keyword1") String keyword1,
            @Param("keyword2") String keyword2,
            @Param("keyword3") String keyword3,
            @Param("keyword4") String keyword4,
            @Param("keyword5") String keyword5,
            @Param("keyword6") String keyword6,
            @Param("keyword7") String keyword7,
            @Param("keyword8") String keyword8,
            Pageable pageable);
}
