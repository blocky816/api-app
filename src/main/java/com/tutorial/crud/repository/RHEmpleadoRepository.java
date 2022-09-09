package com.tutorial.crud.repository;

import com.tutorial.crud.entity.RHEmpleado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RHEmpleadoRepository extends JpaRepository<RHEmpleado, Integer> {
	RHEmpleado findByIdEmpleado(int idEMpleado);

}
