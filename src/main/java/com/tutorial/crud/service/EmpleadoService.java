package com.tutorial.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tutorial.crud.entity.Empleado;
import com.tutorial.crud.repository.EmpleadoRepository;

@Service
public class EmpleadoService {
    
    @Autowired
    EmpleadoRepository empleadoRepository;

    public List<Empleado> findAll(){
        return empleadoRepository.findAll();
    }
    
    public Empleado save(Empleado empleado){
    	return empleadoRepository.save(empleado);
    }

    public Empleado findByIdEmpleado(int idEmpleado) {
    	return empleadoRepository.findByIdEmpleado(idEmpleado);
    }

    public boolean existsById(int idEmpleado) {
        return empleadoRepository.existsById(idEmpleado);
    }

    public void deleteById(int idEmpleado) {
        empleadoRepository.deleteById(idEmpleado);
    }

}
