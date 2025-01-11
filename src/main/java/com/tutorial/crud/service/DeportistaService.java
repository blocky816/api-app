package com.tutorial.crud.service;

import com.tutorial.crud.dto.AsistenciaDTO;
import com.tutorial.crud.dto.DeportistaDTO;
import com.tutorial.crud.dto.EvaluacionRequest;
import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DeportistaService {
    private final RestTemplate restTemplate;
    private final String baseUrl;

    private ClienteService clienteService;

    @Autowired
    public DeportistaService(RestTemplate restTemplate, @Value("${odoo.api.url}") String baseUrl, ClienteService clienteService) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.clienteService = clienteService;
    }

    public List<DeportistaDTO> obtenerDeportistas(int idCliente) throws ResourceNotFoundException {
        String url = String.format("%s/ServiciosClubAlpha/api/historico/deportista/%s", baseUrl, idCliente);
        try {
            ResponseEntity<DeportistaDTO[]> response = restTemplate.postForEntity(url, null, DeportistaDTO[].class);

            if (response.getBody() == null || response.getBody().length == 0) {
                throw new ResourceNotFoundException("No se encontró un historico para el deportista");
            }

            List<DeportistaDTO> deportistas = List.of(response.getBody());

            deportistas.forEach(this::procesarDeportista);
            return deportistas;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResourceNotFoundException("No se encontró un historico para el deportista");
        }
    }

    public ResponseEntity<String> getEvaluacionesDeportista(EvaluacionRequest request) {
        String url = String.format("%s/ServiciosClubAlpha/api/list/evaluaciones/deportista", baseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<EvaluacionRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {}
        );
        return response;
    }

    public ResponseEntity<List<AsistenciaDTO>> getAsistenciasDeportista(EvaluacionRequest request) {
        String url = String.format("%s/ServiciosClubAlpha/api/list/asistencias/deportista", baseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EvaluacionRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<List<AsistenciaDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<List<AsistenciaDTO>>() {}
        );
        return response;
    }

    public Cliente darConsentimiento(int idCliente) {
        Cliente cliente = clienteService.findById(idCliente);
        if (cliente == null) {
            return null;
        }

        boolean consentimiento = cliente.getConsentimiento() != null ? cliente.getConsentimiento() : false;
        cliente.setProfesionalizacion(!consentimiento);
        cliente.setConsentimiento(!consentimiento);
        clienteService.save(cliente); return cliente;
    }

    private void procesarDeportista(DeportistaDTO deportista) {
        Cliente cliente = clienteService.findById(Integer.parseInt(deportista.getId_cliente_deportista()));

        if (cliente != null) {
            actualizarProfesionalizacion(deportista, cliente);
            establecerImagen(deportista, cliente);
            clienteService.save(cliente);
        }
    }

    private void actualizarProfesionalizacion(DeportistaDTO deportista, Cliente cliente) {
        boolean esMenorDeEdad = clienteService.esMenorDeEdad(cliente.getFechaNacimiento());
        if (esMenorDeEdad) {
            deportista.setProfesionalizacion(true);
            cliente.setProfesionalizacion(true);
        } else {
            boolean consentimiento = Boolean.TRUE.equals(cliente.getConsentimiento());
            deportista.setProfesionalizacion(consentimiento);
            cliente.setProfesionalizacion(consentimiento);
        }
        deportista.setEdad(clienteService.calcularEdad(cliente.getFechaNacimiento()));
    }

    private void establecerImagen(DeportistaDTO deportista, Cliente cliente) {
        if (cliente.getURLFoto() != null && cliente.getURLFoto().getImagen() != null) {
            deportista.setImagen(cliente.getURLFoto().getImagen());
        } else {
            deportista.setImagen(null);
        }
    }
}
