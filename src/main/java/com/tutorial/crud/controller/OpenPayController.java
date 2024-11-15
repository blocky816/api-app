package com.tutorial.crud.controller;

import com.tutorial.crud.service.OdooPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/openpay")
@CrossOrigin(origins = "*")
public class OpenPayController {
    @Autowired
    OdooPedidoService odooPedidoService;

    @GetMapping("/getPedido/{idCliente}")
    public ResponseEntity<?> getPedido(@PathVariable int idCliente) {
        Map<String, Object> pedidoCliente = odooPedidoService.obtenerPedido(idCliente);
        return new ResponseEntity<>(pedidoCliente, HttpStatus.OK);
    }

    @PostMapping("/generar-referencia/{idCliente}/{vigenciaDias}")
    public ResponseEntity<?> generarReferencia(@PathVariable int idCliente, @PathVariable int vigenciaDias) {
        return odooPedidoService.generarReferenciaPago(idCliente, vigenciaDias);
    }
}
