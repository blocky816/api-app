package com.tutorial.crud.service;

import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.ParkingUsuario;
import com.tutorial.crud.entity.RHEmpleado;
import com.tutorial.crud.repository.ParkingUsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ParkingUsuarioService {

    @Autowired
    ParkingUsuarioRepository parkingUsuarioRepository;

    public List<ParkingUsuario> list(){
        return parkingUsuarioRepository.findAll();
    }

    public Optional<ParkingUsuario> getOne(int id){
        return parkingUsuarioRepository.findById(id);
    }
    
    public List<ParkingUsuario> findByIdCliente(Cliente cliente) {
    	return parkingUsuarioRepository.findByCliente(cliente);
    }
    public List<ParkingUsuario> findByRhEmpleado(RHEmpleado empleado) {
    	return parkingUsuarioRepository.findByRhEmpleado(empleado);
    }
    public List<ParkingUsuario> findByCapturado(boolean capturado) {
    	return parkingUsuarioRepository.findByCapturado(capturado);
    }

    public ParkingUsuario  save(ParkingUsuario actividad){
    	return parkingUsuarioRepository.save(actividad);
    }
}
