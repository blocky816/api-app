package com.tutorial.crud.service;

import com.tutorial.crud.entity.Toalla;
import com.tutorial.crud.repository.ToallaRepository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ToallaService {
    
    @Autowired
    ToallaRepository toallaRepository;

    public void save(Toalla toalla) {
        toallaRepository.save(toalla);
    }

    public List<Toalla> findAll() {
        return toallaRepository.findAll();
    }

    public boolean existsByIdClienteAndFechaInicio(int idCliente, Date fechaInicio) {
        List<Toalla> toallasList = this.findAll(); 
        int day = fechaInicio.getDate();
        int month = fechaInicio.getMonth() + 1;
        int year = fechaInicio.getYear();


        boolean bandera = false;
        for(Toalla toalla: toallasList){
            if(day == toalla.getFechaInicio().getDate() && month == toalla.getFechaInicio().getMonth() + 1 && year == toalla.getFechaInicio().getYear() && idCliente == toalla.getIdCliente()){
              bandera = true;  
              break;
            }
        }
        return bandera;
    }

    public Toalla getByIdClienteAndFechaInicio(int idCliente, Date fechaInicio) {
        List<Toalla> toallasList = this.findAll(); 
        int day = fechaInicio.getDate();
        int month = fechaInicio.getMonth() + 1;
        int year = fechaInicio.getYear();

        Toalla toallaLiberar = null;
        boolean bandera = false;
        for(Toalla toalla: toallasList){
            if(day == toalla.getFechaInicio().getDate() && month == toalla.getFechaInicio().getMonth() + 1 && year == toalla.getFechaInicio().getYear() && idCliente == toalla.getIdCliente()){
              toallaLiberar = toalla;
              break;
            }
        }
        //return toallaRepository.getByIdClienteAndFechaInicio(idCliente, fechaInicio);
        return toallaLiberar;
    }


}
