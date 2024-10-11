package com.tutorial.crud.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutorial.crud.dto.AccesoTorniqueteDTO;
import com.tutorial.crud.entity.Cliente;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

@Service
public class OdooAccesoService {
    private static final Logger logger = Logger.getLogger(OdooAccesoService.class.getName());

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String odooApiUrl;
    private final ClienteService clienteService;

    public OdooAccesoService(
            HttpClient httpClient,
            ObjectMapper objectMapper,
            @Value("${odoo.api.url}") String odooApiUrl,
            ClienteService clienteService) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.odooApiUrl = odooApiUrl;
        this.clienteService = clienteService;
    }

    public String sendAccesoToOdoo(AccesoTorniqueteDTO accesoTorniqueteDTO) throws Exception {
        logger.info("Acceso torniquete solicitud: " + accesoTorniqueteDTO);
        String url = odooApiUrl + "/ServiciosClubAlpha/api/entrance/qr";

        int isTitular = isTitular(accesoTorniqueteDTO.getIdClienteOdoo());
        accesoTorniqueteDTO.setTitular(isTitular);
        String requestBody = buildRequestBody(accesoTorniqueteDTO);

        HttpRequest request = createHttpRequest(url, requestBody);
        logger.info("Enviando acceso a Odoo");

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("Status code : " + response.statusCode() + " Response: " + response.body());

            if (response.statusCode() == 201){
                return response.body();
            }
        } catch (IOException | InterruptedException e) {
            logger.severe("Error mientras se registraba el acceso a Odoo: " + e.getMessage());
            throw new RuntimeException("Error en la comunicación con Odoo", e);
        }

       return "";
    }

    private int isTitular(int odooId) {
        Cliente cliente = clienteService.findByIdOdoo(odooId);

        // Verificar si el cliente existe
        if (cliente == null) {
            throw new NoSuchElementException("Cliente no encontrado con ID de Odoo: " + odooId);
        }
        return cliente.getIdTitular() == cliente.getIdCliente() ? 1 : 0;
    }

    private String buildRequestBody(AccesoTorniqueteDTO accesoTorniqueteDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(accesoTorniqueteDTO);
    }
    private HttpRequest createHttpRequest(String url, String requestBody) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
    }
}
