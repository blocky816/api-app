package com.tutorial.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tutorial.crud.dto.ClientePesaje;

@SpringBootApplication
public class CrudApplication {
	public static ClientePesaje cl;

	public static void main(String[] args) {
		SpringApplication.run(CrudApplication.class, args);
        try {
        	cl=new ClientePesaje();
			cl.initCliente();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
