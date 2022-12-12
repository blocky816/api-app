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

        System.out.println("Toallas list is Empty?: " + toallasList.isEmpty());
        System.out.println("Toallas list size: " + toallasList.size());

        System.out.println("IDCliente" + idCliente);
        System.out.println("DAY fecha inicio: " + day);
        System.out.println("Month fecha inicio: " + month);
        System.out.println("YEAR fecha inicio: " + year);

        boolean bandera = false;
        for(Toalla toalla: toallasList){
            System.out.println("toalla cliente: " + toalla.getIdCliente());
            System.out.println("toalla DAY fecha inicio: " + toalla.getFechaInicio().getDate());
            System.out.println("toalla MONTH fecha inicio: " + toalla.getFechaInicio().getMonth());
            System.out.println("toalla YEAR fecha inicio: " + toalla.getFechaInicio().getYear());
            System.out.println("Bandera: " + bandera);
            System.out.println("Month = toalla month ? ");
            System.out.println(month == toalla.getFechaInicio().getMonth() + 1);
            System.out.println("day = toalla day ? ");
            System.out.println(day == toalla.getFechaInicio().getDay());
            System.out.println("year = toalla year ? ");
            System.out.println(year == toalla.getFechaInicio().getYear());
            System.out.println("cliente = toalla cliente ? ");
            System.out.println(idCliente == toalla.getIdCliente());

            System.out.println(day == toalla.getFechaInicio().getDate() && month == toalla.getFechaInicio().getMonth() + 1 && year == toalla.getFechaInicio().getYear() && idCliente == toalla.getIdCliente());
            if(day == toalla.getFechaInicio().getDate() && month == toalla.getFechaInicio().getMonth() + 1 && year == toalla.getFechaInicio().getYear() && idCliente == toalla.getIdCliente()){
              System.out.println("registro duplicado en toalla service");
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
              System.out.println("registro encontrado en toalla service");
              toallaLiberar = toalla;
              break;
            }
        }
        //return toallaRepository.getByIdClienteAndFechaInicio(idCliente, fechaInicio);
        return toallaLiberar;
    }


}
