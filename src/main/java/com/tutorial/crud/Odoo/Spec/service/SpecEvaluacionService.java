package com.tutorial.crud.Odoo.Spec.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.Normalizer;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @Value("${odoo.api.url}")
    String odooApiUrl;

    public ResponseEntity<?> obtenerUsuarioSpec(Integer idCliente){
        try {
            var body  = " {\"id_cliente\": " + idCliente + "}";
            var client = HttpClient.newHttpClient();

            var request = HttpRequest.newBuilder(
                            URI.create(odooApiUrl + "/ServiciosClubAlpha/api/Spec/Deportista"))
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.body().contains("Internal Server Error")){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("msg", "Ocurrio un error en Odoo"));
            }

            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response.body());
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("msg", "Error al obtener datos de usuario", "error", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
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
                
        Optional<Double> masaMuscular = evaluacion.getLine_evaluation().stream()
            .filter(r -> normalize(r.getId_parametro().get(0).getName()).equalsIgnoreCase("Musculo"))
            .map(r -> r.getValor())
            .findFirst();

        info.setMasaMuscular(masaMuscular.orElse(0.0));

        var masaAdiposa = evaluacion.getLine_evaluation().stream()
            .filter( r -> normalize(r.getId_parametro().get(0).getName()).equalsIgnoreCase("Grasa"))
            .map(r -> r.getValor())
            .findFirst();

        info.setMasaAdiposa(masaAdiposa.orElse(0.0));

        var talla = evaluacion.getLine_evaluation().stream()
            .filter( r -> normalize(r.getId_parametro().get(0).getName()).equals("Estatura"))
            .map(r -> r.getValor())
            .findFirst();

        info.setTalla(talla.orElse(0.0));

        var peso = evaluacion.getLine_evaluation().stream().filter(
            r -> normalize(r.getId_parametro().get(0).getName()).equals("Peso"))
            .map(r -> r.getValor())
            .findFirst();

        info.setPeso(peso.orElse(0.0));

        List<ResultadosPruebasSpec> resultados = new ArrayList<>();
        for (var item : evaluacion.getLine_evaluation()) {
            ResultadosPruebasSpec resultado = new ResultadosPruebasSpec();
            resultado.setNombre(item.getId_parametro().get(0).getName());
            resultado.setValor(item.getValor());
            resultado.setLimiteInferior(item.getLimite_inferior());
            resultado.setLimiteSuperior(item.getLimite_superior());
            resultado.setUnidad(item.getId_parametro().get(0).getUnidad().get(1).toString());
            resultado.setMostrarEnApp(item.getVisibility_report_app());

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
    private static String normalize(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
    }
    
}
