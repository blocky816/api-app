package com.tutorial.crud.service;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutorial.crud.dto.InvoiceRequest;
import com.tutorial.crud.dto.InvoiceResponse;
import com.tutorial.crud.dto.ProductResponse;
import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.PaseUsuario;
import org.aspectj.apache.bcel.generic.InstructionConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.*;
import java.util.logging.Logger;

@Service
public class OdooInvoiceService {
    private static final Logger logger = Logger.getLogger(ProductoService.class.getName());

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String odooApiUrl;

    private final PaseUsuarioService paseUsuarioService;
    private final ClienteService clienteService;

    public OdooInvoiceService(
            HttpClient httpClient,
            ObjectMapper objectMapper,
            @Value("${odoo.api.url}") String odooApiUrl,
            PaseUsuarioService paseUsuarioService,
            ClienteService clienteService) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.odooApiUrl = odooApiUrl;
        this.paseUsuarioService = paseUsuarioService;
        this.clienteService = clienteService;
    }

    public String sendInvoiceToOdoo(InvoiceRequest invoiceRequest) throws Exception {
        logger.info("Shopping Cart request: " + invoiceRequest);
        String url = odooApiUrl + "/ServiciosClubAlpha/api/factura/car/shop";

        int odooId = getOdooId(invoiceRequest.getClienteIdOdoo());
        invoiceRequest.setClienteIdOdoo(odooId);
        String requestBody = buildRequestBody(invoiceRequest);

        HttpRequest request = createHttpRequest(url, requestBody);
        logger.info("Enviando solicitud a Odoo");
        //return requestBody;
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("Respuesta Status code : " + response.statusCode());

            if (response.statusCode() >= 200 && response.statusCode() < 300){
                List<InvoiceResponse> invoiceResponse = parseInvoiceResponse(response.body());
                return processInvoiceResponses(invoiceResponse);
            }
        } catch (IOException | InterruptedException e) {
            logger.severe("Error mientras se cargaba la factura en Odoo: " + e.getMessage());
            throw new RuntimeException("Error en la comunicación con Odoo", e);
        }

        return "Error en la carga de pases";
    }

    private int getOdooId(int odooId) {
        Cliente cliente = clienteService.findByIdOdoo(odooId);

        // Verificar si el cliente existe
        if (cliente == null) {
            throw new NoSuchElementException("Cliente no encontrado con ID de Odoo: " + odooId);
        }
        // Si el cliente es titular, devolver su ID de Odoo
        if (cliente.getIdTitular() == cliente.getIdCliente()) {
            return cliente.getIdOdoo();
        }

        // Buscar al titular si es dependiente
        Cliente titular = clienteService.findById(cliente.getIdTitular());

        // Verificar si se encontró el titular
        if (titular == null) {
            throw new NoSuchElementException("Titular no encontrado con ID: " + cliente.getIdTitular());
        }

        return titular.getIdOdoo();
    }

    private String buildRequestBody(InvoiceRequest invoiceRequest) throws JsonProcessingException {
        return objectMapper.writeValueAsString(invoiceRequest);
    }
    private HttpRequest createHttpRequest(String url, String requestBody) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
    }
    private List<InvoiceResponse> parseInvoiceResponse(String jsonResponse) throws Exception {
        return objectMapper.readValue(jsonResponse, new TypeReference<List<InvoiceResponse>>() {});
    }

    private String processInvoiceResponses(List<InvoiceResponse> invoiceResponses) {
        String clientId = invoiceResponses.get(0).getIdCliente();
        Cliente cliente = clienteService.findById(Integer.parseInt(clientId));

        if (cliente != null) {
            List<PaseUsuario> paseUsuarios = new ArrayList<>();

            for (InvoiceResponse invoiceResponse : invoiceResponses) {
                paseUsuarios.addAll(createPaseUsuarios(invoiceResponse, cliente));
            }

            paseUsuarioService.saveAll(paseUsuarios);
            return "Pases cargados correctamente";
        }

        return "Cliente no encontrado";
    }

    private List<PaseUsuario> createPaseUsuarios(InvoiceResponse invoiceResponse, Cliente cliente) {
        List<PaseUsuario> paseUsuarios = new ArrayList<>();

        for (ProductResponse product : invoiceResponse.getProductos()) {
            if (product.getProduct_id().size() < 2) continue;

            PaseUsuario paseUsuario = new PaseUsuario();
            paseUsuario.setIdVentaDetalle(product.getId());
            paseUsuario.setCantidad((int) product.getQuantity());
            paseUsuario.setIdProd((int) product.getProduct_id().get(0)); // ID Producto
            paseUsuario.setCliente(cliente);
            paseUsuario.setConcepto((String) product.getProduct_id().get(1)); // Concepto
            paseUsuario.setDisponibles((int) product.getQuantity());
            paseUsuario.setConsumido(0);
            paseUsuario.setF_compra(new Date());
            paseUsuario.setCreatedBy(invoiceResponse.getIdCliente());
            paseUsuario.setUpdatedBy(invoiceResponse.getIdCliente());
            paseUsuario.setSubgrupo(null);
            paseUsuario.setActivo(false);

            paseUsuarios.add(paseUsuario);
        }

        return paseUsuarios;
    }
}
