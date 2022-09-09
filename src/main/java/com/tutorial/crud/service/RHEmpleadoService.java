package com.tutorial.crud.service;

import com.tutorial.crud.entity.RHEmpleado;
import com.tutorial.crud.repository.RHEmpleadoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RHEmpleadoService {

    @Autowired
    RHEmpleadoRepository rhEmpleadoRepository;

    public List<RHEmpleado> list(){
        return rhEmpleadoRepository.findAll();
    }

    public Optional<RHEmpleado> getOne(int id){
        return rhEmpleadoRepository.findById(id);
    }

    public RHEmpleado  save(RHEmpleado actividad){
    	return rhEmpleadoRepository.save(actividad);
    }

    public RHEmpleado findByIdEmpleado(int idEmpleado) {
    	return rhEmpleadoRepository.findByIdEmpleado(idEmpleado);
    }
}
