package com.tutorial.crud.Odoo.Spec.service;

import com.tutorial.crud.Odoo.Spec.dto.RespuestaDTO;
import com.tutorial.crud.Odoo.Spec.entity.RespuestaFormulario;
import com.tutorial.crud.service.FormularioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecFormsService {
    @Autowired
    private FormularioService formularioService;


    public List<RespuestaFormulario> responderFormularioMultiple(int idCliente, List<RespuestaDTO> respuestas) {
        List<RespuestaFormulario> respuestasGuardadas = formularioService.responderFormularioMultiple(idCliente, respuestas);
        return respuestasGuardadas;
    }

    public List<RespuestaDTO> obtenerRespuestasFormulario(Integer idCliente) {
        return formularioService.obtenerRespuestasPorCliente(idCliente);
    }
}
