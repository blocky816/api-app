package com.tutorial.crud.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutorial.crud.dto.ListaTorniquetes;
import com.tutorial.crud.entity.Club;
import com.tutorial.crud.entity.Torniquete;
import com.tutorial.crud.exception.ResourceNotFoundException;
import com.tutorial.crud.repository.TorniqueteRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class OdooTorniqueteService {

    private static final Logger logger = Logger.getLogger(OdooTorniqueteService.class.getName());

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String odooApiUrl;
    private final TorniqueteRepository torniqueteRepository;
    private final ClubService clubService;

    public OdooTorniqueteService(
            HttpClient httpClient,
            ObjectMapper objectMapper,
            @Value("${odoo.api.url}") String odooApiUrl,
            TorniqueteRepository torniqueteRepository,
            ClubService clubService) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.odooApiUrl = odooApiUrl;
        this.torniqueteRepository = torniqueteRepository;
        this.clubService = clubService;
    }

    public ListaTorniquetes getTorniquetes(int branchId) throws ResourceNotFoundException {
        String url = odooApiUrl + "/ServiciosClubAlpha/api/all/torniquetes";
        String requestBody = buildRequestBody(branchId);

        HttpRequest request = createHttpRequest(url, requestBody);

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            logger.info("Consulta de torniquetes de Odoo para branch " + branchId + ": " + response.body());

            if (response.statusCode() != 201) {  // Status code 200 is OK
                throw new ResourceNotFoundException("Torniquetes no encontrados branch: " + branchId);
            } else {
                return parseResponse(response.body());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error en la comunicación con Odoo", e);
        }
    }

    private String buildRequestBody(int branchId) {
        return "{\"branch_id\": " + branchId + "}";
    }

    private HttpRequest createHttpRequest(String url, String requestBody) {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json");

        if (requestBody != null && !requestBody.isEmpty())
            requestBuilder.method("GET", HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8));
        else
             requestBuilder.method("GET", HttpRequest.BodyPublishers.noBody());

        return requestBuilder.build();
    }

    private ListaTorniquetes parseResponse(String responseBody) throws ResourceNotFoundException {
        try {
            return objectMapper.readValue(responseBody, ListaTorniquetes.class);
        } catch (JsonProcessingException e) {
            throw new ResourceNotFoundException("Error al procesar la respuesta de torniquetes: " + e.getMessage());
        }
    }

    public String abrirTorniquete(int idTorniquete) {
        //Club club = clubService.findById(idclub);
        Optional<Torniquete> torniqueteOpt = torniqueteRepository.findById(idTorniquete);

        if (torniqueteOpt.isPresent()){
            Torniquete torniquete = torniqueteOpt.get();
            String urlTorniquete = torniquete.getIp();
            String url = String.format("%s:8000/abrir?1", urlTorniquete);

            return sendOpenRequest(url);
        }
        return "Torniquete no encontrado";
    }

    private String sendOpenRequest(String url) {
        HttpRequest request = createHttpRequest(url, null);

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("Abriendo torniquete " + url + " status code:  " + response.statusCode() + " respuesta: " + response);
            return response.body();
        } catch (Exception e){
            return "Error al abrir el torniquete: " + e.getMessage();
        }
    }
}
