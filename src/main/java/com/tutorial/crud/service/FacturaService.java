package com.tutorial.crud.service;

import com.tutorial.crud.entity.Factura;
import com.tutorial.crud.repository.FacturaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FacturaService {

    @Autowired
    FacturaRepository facturaRepository;

    public List<Factura> list(){
        return facturaRepository.findAll();
    }

    public Optional<Factura> getOne(int id){
        return facturaRepository.findById(id);
    }

    public Factura  save(Factura factura){
    	return facturaRepository.save(factura);
    }

	public Factura getByRecibo(String recibo) {
    	return facturaRepository.findByRecibo(recibo).get();
	}
	
	public List<Factura> getByCliente(int cliente) {
    	return facturaRepository.findByIdCliente(cliente);
	}

}
