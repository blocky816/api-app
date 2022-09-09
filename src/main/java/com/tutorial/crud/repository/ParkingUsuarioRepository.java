package com.tutorial.crud.repository;

import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.ParkingUsuario;
import com.tutorial.crud.entity.RHEmpleado;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ParkingUsuarioRepository extends JpaRepository<ParkingUsuario, Integer> {
	List<ParkingUsuario> findByCliente(Cliente cliente);
	List<ParkingUsuario> findByRhEmpleado(RHEmpleado empleado);
	List<ParkingUsuario> findByCapturado(boolean capturado);
}
