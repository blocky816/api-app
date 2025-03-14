package com.tutorial.crud.Odoo.Spec.repository;

import com.tutorial.crud.Odoo.Spec.entity.RespuestaFormulario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RespuestaFormularioRepository extends JpaRepository<RespuestaFormulario, Long> {
    List<RespuestaFormulario> findByClienteIdClienteAndActivoTrue(Integer idCliente);

    @Modifying
    @Query("UPDATE RespuestaFormulario rf SET rf.activo = false WHERE rf.cliente.idCliente = :clienteId AND rf.activo = true")
    void desactivarRespuestasActivas(@Param("clienteId") Integer clienteId);
}
