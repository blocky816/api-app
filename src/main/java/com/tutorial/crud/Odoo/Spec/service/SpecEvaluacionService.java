package com.tutorial.crud.Odoo.Spec.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tutorial.crud.Odoo.Spec.dto.EvaluacionSpec.InformacionDeportistaSpec;
import com.tutorial.crud.Odoo.Spec.dto.EvaluacionSpec.ReporteEvaluacion;
import com.tutorial.crud.Odoo.Spec.dto.EvaluacionSpec.ResultadosPruebasSpec;
import com.tutorial.crud.Odoo.Spec.dto.EvaluacionSpec.SpecEvaluacionDto;
import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.repository.ClienteRepository;

@Service
public class SpecEvaluacionService {
    @Autowired
    ClienteRepository clienteRepository;
    
    public List<SpecEvaluacionDto> obtenerEvaluacion(Integer idCliente) throws IOException, InterruptedException {
        var body  = " {\"id_cliente\": " + idCliente + "}";
        var client = HttpClient.newHttpClient();
        
        var request = HttpRequest.newBuilder(
                        URI.create("http://192.168.20.104:8000/ServiciosClubAlpha/api/Spec/Descarga/Evaluaciones"))
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

                
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        var specEvaluacionDto = SpecEvaluacionDto.getList(response.body());
        return specEvaluacionDto;
    }

    public ReporteEvaluacion obtenerReporte(SpecEvaluacionDto evaluacion) {
        ReporteEvaluacion reporte = new ReporteEvaluacion();

        InformacionDeportistaSpec info = new InformacionDeportistaSpec();

        info.setNombre(evaluacion.getPartner_id().get(1).toString());

        info.setEdad(obtenerEdad(Integer.parseInt(evaluacion.getPartner_id().get(0).toString())));
                
        var masaMuscular = evaluacion.getLine_evaluation().stream().filter(
            r -> r.getId_parametro().get(0).getName().equals("Músculo")
            ).findFirst().get().getValor();

        info.setMasaMuscular(masaMuscular);

        var masaAdiposa = evaluacion.getLine_evaluation().stream().filter(
            r -> r.getId_parametro().get(0).getName().equals("Grasa")
            ).findFirst().get().getValor();

        info.setMasaAdiposa(masaAdiposa);

        var talla = evaluacion.getLine_evaluation().stream().filter(
            r -> r.getId_parametro().get(0).getName().equals("Estatura")
            ).findFirst().get().getValor();
        info.setTalla(talla);

        var peso = evaluacion.getLine_evaluation().stream().filter(
            r -> r.getId_parametro().get(0).getName().equals("Peso")
            ).findFirst().get().getValor();
        info.setPeso(peso);

        List<ResultadosPruebasSpec> resultados = new ArrayList<>();
        for (var item : evaluacion.getLine_evaluation()) {
            ResultadosPruebasSpec resultado = new ResultadosPruebasSpec();
            resultado.setNombre(item.getId_parametro().get(0).getName());
            resultado.setValor(item.getValor());
            resultado.setLimiteInferior(item.getLimite_inferior());
            resultado.setLimiteSuperior(item.getLimite_superior());

            resultados.add(resultado);

        }
        reporte.setPruebas(resultados);
        reporte.setInfo(info);

        return reporte;
    }
        
    private Integer obtenerEdad(Integer id_cliente) {
        Cliente cliente = clienteRepository.findByIdOdoo(id_cliente);
        if ( cliente == null) {
            return 0;
        }
        Date fechaNac = cliente.getFechaNacimiento();
        var fechaActual = new java.util.Date();
        var edad = fechaActual.getYear() - fechaNac.getYear();
        if (fechaNac.getMonth() > fechaActual.getMonth()) {
            edad--;
        } else if (fechaNac.getMonth() == fechaActual.getMonth() && fechaNac.getDate() > fechaActual.getDate()) {
            edad--;
        }

        return edad;
    }
}
