package com.tutorial.crud.controller;

import com.tutorial.crud.correo.OpenPayEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/openpayC")
@CrossOrigin(origins = "*")
public class CorreoController {
    @Autowired
    private OpenPayEmailService openPayEmailService;

    @GetMapping("/enviar-correo")
    public String enviarCorreo(@RequestParam String correoCliente, @RequestParam String nombreCliente, @RequestParam String numeroReferencia) {
        try {
            // Enviar el correo con la referencia de pago
            openPayEmailService.enviarCorreo(correoCliente, nombreCliente, numeroReferencia);
            return "Correo enviado exitosamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al enviar el correo: " + e.getMessage();
        }
    }
}
