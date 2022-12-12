package com.tutorial.crud.repository;

import com.tutorial.crud.entity.Empleado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
    
    public Empleado findByIdEmpleado(int idEmpleado);
}