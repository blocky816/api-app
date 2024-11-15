package com.tutorial.crud.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface IOdooPedidoService {
    Map<String, Object> obtenerPedido(int idCliente);
    ResponseEntity<?> generarReferenciaPago(int idCliente, int vigencia);
}
