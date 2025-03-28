package com.tutorial.crud.Odoo.Spec.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutorial.crud.Odoo.Spec.dto.ClienteRequest;
import com.tutorial.crud.Odoo.Spec.dto.SpecFacturaLinea;
import com.tutorial.crud.Odoo.Spec.dto.SpecFacturaResponse;
import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.PaseUsuario;
import com.tutorial.crud.service.ClienteService;
import com.tutorial.crud.service.PaseUsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class SpecFacturaService {

    private static final Logger logger = LoggerFactory.getLogger(SpecFacturaService.class);
    private static final int CANTIDAD = 6;
    private static final int DISPONIBLES = 6;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PaseUsuarioService paseUsuarioService;

    @Autowired
    ClienteService clienteService;

    @Value("${odoo.api.url}")
    private String ODOO_API_URL;

    private String FACTURA_API_URL;

    @PostConstruct
    public void init() {
        this.FACTURA_API_URL = ODOO_API_URL + "/ServiciosClubAlpha/api/Spec/Descarga/Facturas";
    }

    /**
     * Obtiene una lista de facturas para un cliente dado.
     *
     * @param idCliente El identificador del cliente
     * @return Lista de facturas del cliente
     */
    public List<SpecFacturaResponse> obtenerFacturas(String idCliente) {
        ClienteRequest clienteRequest = createClienteRequest(idCliente);
        HttpEntity<ClienteRequest> entity = createHttpEntity(clienteRequest);

        try {
            ResponseEntity<String> responseEntity = makeRequest(entity);
            return processResponse(responseEntity.getBody());
        } catch (HttpClientException | JsonProcessingException e) {
            logger.error("Error al obtener las facturas: {}", e.getMessage(), e);
            return Collections.emptyList();  // Retorna una lista vacía en caso de error
        }
    }

    /**
     * Crea un objeto ClienteRequest con el idCliente proporcionado.
     *
     * @param idCliente El identificador del cliente
     * @return El objeto ClienteRequest
     */
    private ClienteRequest createClienteRequest(String idCliente) {
        return new ClienteRequest(idCliente);
    }

    /**
     * Crea una entidad HTTP con las cabeceras necesarias.
     *
     * @param clienteRequest El cuerpo de la solicitud (ClienteRequest)
     * @return La entidad HTTP con el cuerpo y las cabeceras
     */
    private HttpEntity<ClienteRequest> createHttpEntity(ClienteRequest clienteRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(clienteRequest, headers);
    }

    /**
     * Realiza la solicitud HTTP utilizando el RestTemplate.
     *
     * @param entity La entidad HTTP que contiene los datos de la solicitud
     * @return La respuesta de la API como una cadena de texto
     * @throws HttpClientException Si hay un error en la solicitud HTTP
     */
    private ResponseEntity<String> makeRequest(HttpEntity<ClienteRequest> entity) throws HttpClientException {
        System.out.println("FACTURA_API_URL" + FACTURA_API_URL);
        return restTemplate.exchange(
                FACTURA_API_URL,
                HttpMethod.POST,
                entity,
                String.class
        );
    }

    /**
     * Procesa la respuesta de la API, intentando deserializarla en una lista de facturas.
     *
     * @param responseBody El cuerpo de la respuesta de la API
     * @return La lista de facturas obtenidas, o una lista vacía si hay un error
     * @throws JsonProcessingException Si hay un error al deserializar la respuesta
     */
    private List<SpecFacturaResponse> processResponse(String responseBody) throws JsonProcessingException {
        List<SpecFacturaResponse> facturas = tryParseFacturas(responseBody);

        if (facturas == null || facturas.isEmpty()) {
            logger.warn("No hay facturas disponibles para procesar.");
            return Collections.emptyList();  // Retorna lista vacía si no hay facturas
        }

        return facturas;
    }

    /**
     * Intenta deserializar la respuesta JSON en una lista de facturas.
     *
     * @param responseBody El cuerpo de la respuesta en formato JSON
     * @return Lista de facturas deserializada, o null si la deserialización falla
     * @throws JsonProcessingException Si ocurre un error al intentar deserializar
     */
    private List<SpecFacturaResponse> tryParseFacturas(String responseBody) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(responseBody, new TypeReference<List<SpecFacturaResponse>>() {});
    }

    // Excepción personalizada para manejar errores HTTP
    public static class HttpClientException extends RuntimeException {
        public HttpClientException(String message, Throwable cause) {
            super(message, cause);
        }
    }



    /**
     * Procesa las facturas obtenidas.
     *
     * @param facturas Lista de facturas a procesar
     */
    public void procesarFacturas(List<SpecFacturaResponse> facturas) {
        if (facturas == null || facturas.isEmpty()) {
            logger.warn("No hay facturas disponibles para procesar.");
            return;
        }

        logger.info("Procesando las facturas: {}", facturas.size());

        facturas.stream()
                .filter(factura -> "posted".equals(factura.getState())) // Filtramos facturas con estado 'posted'
                .forEach(this::procesarFactura); // Procesamos cada factura
    }

    /**
     * Procesa una factura específica.
     *
     * @param factura La factura a procesar
     */
    private void procesarFactura(SpecFacturaResponse factura) {
        /*SpecFacturaLinea linea = factura.getInvoiceLine().get(0);
        int idVentaDetalle = linea.getIdLine();
        String concepto = (String) linea.getProductId().get(1);
        int idProd = (int) linea.getProductId().get(0);

        boolean esPagado = factura.getPaymentState().equals("paid");

        // Calculamos la vigencia para 3 meses a partir del día actual
        LocalDateTime fechaVigencia = calcularFechaVigencia();

        // Obtenemos el cliente y verificamos si existe un PaseUsuario
        Cliente cliente = obtenerCliente(factura);
        PaseUsuario pase = paseUsuarioService.findByIdVentaDetalle(idVentaDetalle);

        if (cliente != null && pase == null) {
            // Si no existe, creamos un nuevo PaseUsuario
            crearPaseUsuario(idVentaDetalle, concepto, idProd, cliente, fechaVigencia, esPagado);
        } else if (cliente != null && !pase.getActivo() && esPagado) {
            actualizarPasePago(pase);
        }*/

        List<SpecFacturaLinea> specFacturaLineas = factura.getInvoiceLine();
        for(SpecFacturaLinea specFacturaLinea: specFacturaLineas){
            int idVentaDetalle = specFacturaLinea.getIdLine();
            String concepto = (String) specFacturaLinea.getProductId().get(1);
            int idProd = (int) specFacturaLinea.getProductId().get(0);
            int cantidad = (int) specFacturaLinea.getQuantity();

            boolean esPagado = factura.getPaymentState().equals("paid");

            // Calculamos la vigencia para 3 meses a partir del día actual
            LocalDateTime fechaVigencia = calcularFechaVigencia(specFacturaLinea.getVigenciaQr());

            // Obtenemos el cliente y verificamos si existe un PaseUsuario
            Cliente cliente = obtenerCliente(factura);
            PaseUsuario pase = paseUsuarioService.findByIdVentaDetalle(idVentaDetalle);

            if (cliente != null && pase == null) {
                // Si no existe, creamos un nuevo PaseUsuario
                crearPaseUsuario(idVentaDetalle, concepto, idProd, cliente, fechaVigencia, esPagado, cantidad);
            } else if (cliente != null && !pase.getActivo() && esPagado && !pase.isPagado()) {
                actualizarPasePago(pase);
            }
        }
    }

    /**
     * Calcula la fecha de vigencia (último día del mes dentro de 3 meses).
     *
     * @return La fecha de vigencia calculada
     */
    private LocalDateTime calcularFechaVigencia(String vigenciaQR) {
        /*return LocalDateTime.now()
                .plusMonths(3)
                .with(TemporalAdjusters.lastDayOfMonth())
                .withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(0);*/
        System.out.println("Vigencia recibida de odoo: " + vigenciaQR);
        LocalDate vigenciaFecha = LocalDate.parse(vigenciaQR); // Parseamos la fecha sin hora
        LocalDateTime vigenciaQRFOrmateada = vigenciaFecha.atTime(23, 59, 59); // Añadimos la hora
        return vigenciaQRFOrmateada;

    }

    /**
     * Obtiene el cliente asociado a la factura.
     *
     * @param factura La factura de la que obtener el cliente
     * @return El cliente correspondiente, o null si no se encuentra
     */
    private Cliente obtenerCliente(SpecFacturaResponse factura) {
        try {
            return clienteService.findByIdOdoo(Integer.parseInt((String) factura.getPartnerId().get(0)));
        } catch (NumberFormatException e) {
            logger.error("Error al obtener cliente para la factura con partnerId: {}", factura.getPartnerId(), e);
            return null;
        }
    }

    /**
     * Crea y guarda un nuevo PaseUsuario.
     *
     * @param idVentaDetalle El ID de la venta
     * @param concepto El concepto de la factura
     * @param idProd El ID del producto
     * @param cliente El cliente asociado
     * @param fechaVigencia La fecha de vigencia del pase
     */
    private void crearPaseUsuario(int idVentaDetalle, String concepto, int idProd, Cliente cliente, LocalDateTime fechaVigencia, boolean esPagado, int cantidad) {
        Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        LocalDateTime ldnow = LocalDateTime.now();

        PaseUsuario paseUsuario = new PaseUsuario();
        paseUsuario.setIdVentaDetalle(idVentaDetalle);
        paseUsuario.setConcepto(concepto);
        paseUsuario.setIdProd(idProd);
        paseUsuario.setCantidad(cantidad);
        paseUsuario.setDisponibles(cantidad);
        paseUsuario.setConsumido(0);
        paseUsuario.setFechaVigencia(fechaVigencia);
        if(esPagado) paseUsuario.setFechaPago(ldnow);
        paseUsuario.setF_compra(now);
        paseUsuario.setCliente(cliente);
        paseUsuario.setCreated(now);
        paseUsuario.setUpdated(now);
        paseUsuario.setActivo(esPagado); //activado cuando es pagado
        paseUsuario.setPagado(esPagado);
        paseUsuario.setCreatedBy(String.valueOf(cliente.getIdCliente()));
        paseUsuario.setUpdatedBy(String.valueOf(cliente.getIdCliente()));
        paseUsuario.setSubgrupo("QR");

        paseUsuarioService.save(paseUsuario);

        logger.info("Pase usuario creado: {}", paseUsuario.getConcepto());
    }

    /**
     * Actualiza el pase encontrado a pagado y activo
     *
     * @param pase El pase que se actualizará
     */
    private void actualizarPasePago(PaseUsuario pase){
        pase.setActivo(true);
        pase.setPagado(true);
        pase.setFechaPago(LocalDateTime.now());
    }
}
