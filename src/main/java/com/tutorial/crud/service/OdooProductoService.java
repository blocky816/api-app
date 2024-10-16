package com.tutorial.crud.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutorial.crud.dto.ProductoDTO;
import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class OdooProductoService {

    private static final Logger logger = Logger.getLogger(ProductoService.class.getName());

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String odooApiUrl;
    private final ClienteService clienteService;

    public OdooProductoService(HttpClient httpClient, ObjectMapper objectMapper, @Value("${odoo.api.url}") String odooApiUrl, ClienteService clienteService) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.odooApiUrl = odooApiUrl;
        this.clienteService = clienteService;
    }

   public List<ProductoDTO> getProductos(int idClienteOdoo) throws ResourceNotFoundException {
       String url = odooApiUrl + "/ServiciosClubAlpha/api/car/shop/productos";
       String requestBody = buildRequestBody(idClienteOdoo);

       HttpRequest request = createHttpRequest(url, requestBody);

       try {
           HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

           logger.info("Consulta de productos Odoo para " + idClienteOdoo + ": " + response.statusCode() + " status code");

           if (response.statusCode() != 201) {  // Status code 200 is OK
               throw new ResourceNotFoundException("Productos no encontrados: " + idClienteOdoo);
           } else {
               //return parseResponse(response.body());
               return filtrarProductos(parseResponse(response.body()), idClienteOdoo);
           }
       } catch (IOException | InterruptedException e) {
           throw new RuntimeException("Error en la comunicación con Odoo", e);
       }
   }

    private String buildRequestBody(int idClienteOdoo) {
        return "{\"id_cliente_odoo\": " + idClienteOdoo + "}";
    }

    private HttpRequest createHttpRequest(String url, String requestBody) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .method("GET", HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                .build();
    }

    private List<ProductoDTO> parseResponse(String responseBody) throws ResourceNotFoundException {
        try {
            ProductoDTO[] productos = objectMapper.readValue(responseBody, ProductoDTO[].class);
            return Arrays.asList(productos);
        } catch (JsonProcessingException e) {
            throw new ResourceNotFoundException("Error al procesar la respuesta: " + e.getMessage());
        }
    }

    private List<ProductoDTO> filtrarProductos(List<ProductoDTO> productos, int idClienteOdoo) throws ResourceNotFoundException {
        try {
            Cliente cliente = clienteService.findByIdOdoo(idClienteOdoo);
            String tipoCliente = cliente.getTipoCliente().getNombre().trim().toLowerCase();
            String categoria = cliente.getCategoria().getNombre().trim().toLowerCase();

            if (categoria.contains("especial")){
                return new ArrayList<>();
            }
            List<ProductoDTO> productosFiltrados = productos.stream()
                    .filter(producto -> {
                        // Siempre incluir "Plus Alpha to Aquadome"
                        if (producto.getNameProductTmpl().trim().toLowerCase().contains("aquadome")) {
                            return true;
                        }
                        if (tipoCliente.contains("platinum")) {
                            return producto.getNameProductTmpl().contains("Platinum");
                        } else {
                            return !producto.getNameProductTmpl().contains("Platinum");
                        }
                    })
                    .collect(Collectors.toList());
            return productosFiltrados;
        } catch (Exception e) {
            throw new ResourceNotFoundException("Error al filtrar productos normal/platinum para: " + idClienteOdoo);
        }
    }
}
