package com.tutorial.crud.service;

import com.tutorial.crud.correo.OpenPayEmailService;
import com.tutorial.crud.dto.PedidoResponseDTO;
import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.exception.PaymentReferenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class OdooPedidoService implements IOdooPedidoService {

    private static final Logger logger = LoggerFactory.getLogger(OdooPedidoService.class);
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private OpenPayEmailService openPayEmailService;

    @Value("${odoo.api.url}")
    private String odooUrl;

    @Value("${openpay.url}")
    private String openPayUrl;
    @Value("${openpay.merchant-id}")
    private String merchantId;

    @Value("${openpay.api-key}")
    private String apiKey;

    @Value("${openpay.webhook.urlRegistraCargo}")
    private String registrarCargoUrl;

    @Value("${openpay.webhook.urlVerificaRef}")
    private String urlVerificaRef;

    @Value("${openpay.urlGenerateBarCode}")
    private String urlGenerateBarCode;
    @Value("${openpay.webhook.user}")
    private String user;

    @Value("${openpay.webhook.password}")
    private String password;

    @Override
    public Map<String, Object> obtenerPedido(int idCliente) {
        logger.info("Iniciando obtención de pedido para el cliente ID: {}", idCliente);
        HttpEntity<String> entity = crearHttpEntityParaPedido(idCliente);
        Map<String, Object> resultado = new HashMap<>();
        String getPedidoUrl = String.format("%s/ServiciosClubAlpha/api/Pagos/GetPedidoByCliente", odooUrl);

        try {
            PedidoResponseDTO response = restTemplate.postForObject(getPedidoUrl, entity, PedidoResponseDTO.class);
            double monto = calcularMontoTotal(response);
            int noPedido = Optional.ofNullable(response).map(PedidoResponseDTO::getNoPedido).orElse(0);

            resultado.put("montoTotal", monto);
            resultado.put("noPedido", noPedido);
            logger.info("Pedido obtenido con éxito: noPedido={}, montoTotal={}", noPedido, monto);
        } catch (Exception e) {
            logger.error("Error al obtener el pedido para el cliente ID {}: {}", idCliente, e.getMessage());
            resultado.put("montoTotal", 0.0);
            resultado.put("noPedido", 0);
            // Manejo de errores específicos de RestTemplate
        }
        return resultado;
    }

    private HttpEntity<String> crearHttpEntityParaPedido(int idCliente) {
        String requestBody = String.format("{ \"IdCliente\": %d }", idCliente);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(requestBody, headers);
    }

    private double calcularMontoTotal(PedidoResponseDTO response) {
        return Optional.ofNullable(response)
                .map(PedidoResponseDTO::getDetalle)
                .map(detalle -> detalle.stream().mapToDouble(PedidoResponseDTO.Detalle::getImporte).sum())
                .orElse(0.0);
    }

    public ResponseEntity<?> generarReferenciaPago(int idCliente, int vigencia) {
        logger.info("Iniciando generación de referencia de pago para el cliente ID: {}", idCliente);

        Map<String, Object> referenciasActivasInfo = tieneReferenciasActivas(idCliente);
        int numeroReferencias = (int) referenciasActivasInfo.get("numeroReferenciasActivas");

        if (numeroReferencias > 0) {
            return manejarReferenciasActivas(idCliente, referenciasActivasInfo);
        }

        return manejarReferenciasInactivas(idCliente, vigencia);
    }

    private ResponseEntity<?> manejarReferenciasActivas(int idCliente, Map<String, Object> referenciasActivasInfo) {
        logger.warn("El cliente ID {} tiene referencias activas. Retornando URL de referencia activa.", idCliente);

        List<Map<String, Object>> referencias = (List<Map<String, Object>>) referenciasActivasInfo.get("referenciasActivas");
        Map<String, Object> referenciaActiva = referencias.get(0);

        String referencia = referenciaActiva.get("referencia").toString();
        double monto = (double) referenciaActiva.get("cantidad");

        String urlPago = String.format("%s/%s", urlGenerateBarCode, referencia);

        Map<String, Object> response = new HashMap<>();
        response.put("urlPago", urlPago);
        response.put("monto", monto);

        //sendReferenceEmail(idCliente, referencia);
        return ResponseEntity.ok(response);
    }

    private ResponseEntity<?> manejarReferenciasInactivas(int idCliente, int vigencia) {
        Map<String, Object> pedidoCliente = obtenerPedido(idCliente);
        double montoTotal = (double) pedidoCliente.get("montoTotal");
        int noPedido = (int) pedidoCliente.get("noPedido");

        if (montoTotal <= 0) {
            logger.warn("El monto total es cero o negativo para el cliente ID {}: {}", idCliente, montoTotal);
            return ResponseEntity.badRequest().body("El monto total debe ser mayor a cero.");
        }

        Cliente cliente = clienteService.findById(idCliente);
        if (cliente == null) {
            logger.warn("Cliente no encontrado para ID: {}", idCliente);
            return ResponseEntity.badRequest().body("Cliente no encontrado.");
        }

        return crearReferenciaPago(montoTotal, noPedido, cliente, vigencia, idCliente);
    }

    private ResponseEntity<?> crearReferenciaPago(double montoTotal, int noPedido, Cliente cliente, int vigencia, int idCliente) {
        String urlOpenPay = openPayUrl.replace("{merchantId}", merchantId);
        HttpEntity<Map<String, Object>> entity = crearHttpEntityOpenPayRef(montoTotal, noPedido, cliente, vigencia);

        try {
            ResponseEntity<?> openPayResponse = crearOpenPayRef(urlOpenPay, entity, idCliente);
            logger.info("Referencia de OpenPay creada exitosamente para el cliente ID: {}", idCliente);

            Map<String, Object> responseBody = (Map<String, Object>) openPayResponse.getBody();
            String referencia = ((Map<String, Object>) responseBody.get("payment_method")).get("reference").toString();
            double monto = (double) responseBody.get("amount");

            return registrarCargoYDevolverUrl(openPayResponse, idCliente, referencia, monto);
        } catch (Exception e) {
            logger.error("Error al generar la referencia de pago para el cliente ID {}: {}", idCliente, e.getMessage());
            throw new PaymentReferenceException("Error al generar la referencia de pago: " + e.getMessage());
        }
    }

    private ResponseEntity<?> registrarCargoYDevolverUrl(ResponseEntity<?> openPayResponse, int idCliente, String referencia, double monto) {
        ResponseEntity<?> registrarResponse = registrarCargo(openPayResponse, idCliente);

        if (registrarResponse.getStatusCode() == HttpStatus.CREATED) {
            logger.info("Cargo registrado exitosamente para el cliente ID: {}", idCliente);
            String urlPago = String.format("%s/%s", urlGenerateBarCode, referencia);

            Map<String, Object> response = new HashMap<>();
            response.put("urlPago", urlPago);
            response.put("monto", monto);

            //sendReferenceEmail(idCliente, referencia);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            logger.error("Error al registrar el cargo para el cliente ID {}: {}", idCliente, registrarResponse.getBody());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar el cargo.");
        }
    }


    private HttpEntity<Map<String, Object>> crearHttpEntityOpenPayRef(double montoTotal, int noPedido, Cliente cliente, int vigencia) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("method", "store");
        requestBody.put("amount", montoTotal);
        requestBody.put("description", "Cargo con tienda");
        requestBody.put("order_id", "oid-" + noPedido);
        requestBody.put("due_date", crearFechaLimite(vigencia));
        // Agregar datos del cliente
        requestBody.put("customer", Map.of(
                "name", cliente.getNombre(),
                "lastname", cliente.getApellidoMaterno(),
                "email", cliente.getEmail(),
                "requires_account", false
        ));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(apiKey, "");

        return new HttpEntity<>(requestBody, headers);
    }

    private String crearFechaLimite(int vigencia) {
        vigencia = (vigencia < 2 || vigencia > 30) ? 2 : vigencia;
        LocalDateTime dueDate = LocalDateTime.now().plusDays(vigencia);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return dueDate.format(formatter);
    }

    private ResponseEntity<?> crearOpenPayRef(String urlOpenPay, HttpEntity<Map<String, Object>> entity, int idCliente) {
        ResponseEntity<?> response = restTemplate.postForEntity(urlOpenPay, entity, Object.class);
        Object responseBodyObj = response.getBody();

        if (responseBodyObj instanceof Map<?, ?>) {
            Map<String, Object> responseBody = (Map<String, Object>) responseBodyObj;
            responseBody.put("customerID", idCliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en la respuesta de OpenPay.");
    }

    private ResponseEntity<?> registrarCargo(ResponseEntity<?> openPayResponse, int idCliente) {
        if (!(openPayResponse.getBody() instanceof Map<?, ?>)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en la respuesta de OpenPay.");
        }

        Map<String, Object> openPayResponseBody = (Map<String, Object>) openPayResponse.getBody();
        HttpEntity<Map<String, Object>> registrarCargoEntity = new HttpEntity<>(openPayResponseBody, crearHeaders(user, password));

        ResponseEntity<?> registrarResponse = restTemplate.postForEntity(registrarCargoUrl, registrarCargoEntity, Object.class);

        return registrarResponse.getStatusCode() == HttpStatus.CREATED
                ? ResponseEntity.status(HttpStatus.CREATED).body("Cargo OpenPAY registrado exitosamente: " + idCliente)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar el cargo OpenPay: " + registrarResponse.getBody());
    }


    private HttpHeaders crearHeaders(String authUser, String authPassword) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(authUser, authPassword);
        return headers;
    }

    private Map<String, Object> tieneReferenciasActivas(int idCliente) {
        logger.info("Consultando referencias activas para el cliente ID: {}", idCliente);

        String url = construirUrl(idCliente);
        HttpEntity<Void> entity = new HttpEntity<>(crearHeaders(user, password));

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        return procesarResponse(response, idCliente);
    }

    private String construirUrl(int idCliente) {
        return String.format("%s/%d", urlVerificaRef, idCliente);
    }

    private Map<String, Object> procesarResponse(ResponseEntity<Map<String, Object>> response, int idCliente) {
        Map<String, Object> result = inicializarResultado();

        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> body = response.getBody();
            if (body != null) {
                actualizarResultado(result, body);
                logger.info("Cliente ID {} tiene {} referencias activas.", idCliente, result.get("numeroReferenciasActivas"));
            }
        } else {
            logger.warn("No se pudo determinar el estado de referencias activas para el cliente ID: {}", idCliente);
        }

        return result;
    }

    private Map<String, Object> inicializarResultado() {
        Map<String, Object> result = new HashMap<>();
        result.put("numeroReferenciasActivas", 0);
        result.put("referenciasActivas", new ArrayList<>());
        return result;
    }

    private void actualizarResultado(Map<String, Object> result, Map<String, Object> body) {
        if (body.containsKey("numeroReferenciasActivas")) {
            int numeroReferencias = (int) body.get("numeroReferenciasActivas");
            result.put("numeroReferenciasActivas", numeroReferencias);

            if (numeroReferencias > 0 && body.containsKey("referenciasActivas")) {
                result.put("referenciasActivas", body.get("referenciasActivas"));
            }
        }
    }

    private void sendReferenceEmail(int idCliente, String referencia) {
        try {
            logger.info("Enviar referencia Open Pay: " + idCliente + " - no: " + referencia);
            Cliente cliente = clienteService.findById(idCliente);
            if (cliente == null) {
                logger.warn("Cliente no encontrado para ID: {}", idCliente);
            }
            String correoCliente = cliente.getEmail();
            String nombreCliente = cliente.getNombre();
            // Enviar el correo con la referencia de pago
            openPayEmailService.enviarCorreo(correoCliente, nombreCliente, referencia);
            logger.info("Correo enviado exitosamente: " + idCliente + " - no: " + referencia);
        } catch (Exception e) {
            logger.error("Error al enviar el correo de referencia: " + idCliente + " - no: " + referencia + " mensaje: " + e.getMessage());
        }
    }
}
