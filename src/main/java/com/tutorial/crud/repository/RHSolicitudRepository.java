package com.tutorial.crud.repository;

import com.tutorial.crud.entity.RHEmpleado;
import com.tutorial.crud.entity.RHSolicitud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RHSolicitudRepository extends JpaRepository<RHSolicitud, Integer> {

	List<RHSolicitud> findByEmpleado(RHEmpleado empleado);


}
