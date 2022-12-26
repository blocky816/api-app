package com.tutorial.crud.repository;

import com.tutorial.crud.entity.ConversacionAsesor;
import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConversacionAsesorRepository extends JpaRepository<ConversacionAsesor, UUID> {

    public List<ConversacionAsesor> findAllByFolioAndActivoOrderByFechaAsc(int idCliente, Boolean activo);
}
