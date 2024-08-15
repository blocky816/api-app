package com.tutorial.crud.controller;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorial.crud.aopDao.endpoints;
import com.tutorial.crud.dto.body3;
import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.OrdenAlpha;
import com.tutorial.crud.entity.configuracion;
import com.tutorial.crud.service.ClienteService;
import com.tutorial.crud.service.OrdenAlphaService;
import com.tutorial.crud.service.configuracionService;

@RestController
@RequestMapping("/pagos")
@CrossOrigin(origins = "*")
public class PagoOxxo {
	private static final Logger log = LoggerFactory.getLogger(PagoOxxo.class);
	@Value("${my.property.apikey}")
	String apikey;
	
	endpoints e = new endpoints();
    
    @Autowired
    configuracionService configuracionService;
    
    @Autowired
    ClienteService clienteService;
    
    @Autowired
    OrdenAlphaService ordenAlphaService;

	@PostMapping("/conekta")
	public String crearPago(@RequestBody body3 body) {
		try{
			log.info("Solicitud de referencia Oxxo: cliente = {} monto = {}", body.getIDCliente(), body.getMonto());
			String body2 = createBodyJson(body);
			configuracion configuration = getConfiguracion();

			String pedidoResponse = e.conectaApiClubPOST(body2,configuration.getEndpointAlpha());
			if (pedidoResponse == null || pedidoResponse.isEmpty() || pedidoResponse.equals("[]")) {
				throw new RuntimeException("No se encontraron pedidos para el cliente: " + body.getIDCliente());
			}

			JSONObject jsonResponse = new JSONObject(pedidoResponse);

			Cliente cliente = clienteService.findById(body.getIDCliente());
			OrdenAlpha existingOrder  = ordenAlphaService.getByIdCliente(body.getIDCliente());

			if (existingOrder == null || isOrderExpired(existingOrder)) {
				return processNewOrder(body, cliente, jsonResponse);
			} else {
				return getExistingOrderResponse(existingOrder);
			}
		} catch (Exception e) {
			return handleException(e);
		}
	}//fin del metodo
	
//	@PostMapping("/status")
//    public String consultarPago(@RequestBody body3 body) {
//		Conekta.setApiKey(apikey);
//		Conekta.setApiVerion("2.0.0");
//		try{
//			Order order = Order.find(body.getIdOrden());
//			JSONObject json=new JSONObject();
//
//
//
//			//pending_payment, declined, expired, paid, refunded, partially_refunded, charged_back, pre_authorized y voided.
//			if(order.payment_status.equals("pending_payment")) {
//				return json.put("Respuesta", "Pago pendiente").toString();
//			}
//
//			if(order.payment_status.equals("declined")) {
//				return json.put("Respuesta", "Declinado").toString();
//			}
//			if(order.payment_status.equals("expired")) {
//				return json.put("Respuesta", "Expirado").toString();
//			}
//			if(order.payment_status.equals("paid")) {
//				return json.put("Respuesta", "Pagado").toString();
//			}
//
//			if(order.payment_status.equals("refunded")) {
//				return json.put("Respuesta", "Reintegrado").toString();
//			}
//			if(order.payment_status.equals("partially_refunded")) {
//				return json.put("Respuesta", "Parcialmente reintegrado").toString();
//			}
//			if(order.payment_status.equals("charged_back")) {
//				return json.put("Respuesta", "Contracargo").toString();
//			}
//			if(order.payment_status.equals("pre_authorized")) {
//				return json.put("Respuesta", "Preautorizado").toString();
//			}
//			if(order.payment_status.equals("voided")) {
//				return json.put("Respuesta", "Vacío").toString();
//			}
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//
//
//    }//fin del metodo
//	@PostMapping("/cancelar")
//    public String cancelarPago(@RequestBody body3 body) {
//		Long nowUnixTimestamp = System.currentTimeMillis();
//		Long thirtyDaysFromNowUnixTimestamp =  (nowUnixTimestamp + 1L * 24 * 60 * 60 * 1000) / 1000L;
//		String thirtyDaysFromNow = thirtyDaysFromNowUnixTimestamp.toString();
//
//		Conekta.setApiKey(apikey);
//		Conekta.apiVersion = "2.0.0";
//		try{
//			JSONObject data = new JSONObject("{"
//					  + "'payment_method': {"
//					    + "'type': 'oxxo_cash',"
//					    + "'expires_at': "+thirtyDaysFromNow
//					  + "}"
//					+"}");
//
//					Order order = Order.find(body.getIdOrden());
//					Charge charge = order.createCharge(data);
//			return "list";
//		}catch (ErrorList e) {
//			e.printStackTrace();
//		} catch (Error e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "La orden no ha sido Cancelada";
//
//    }//fin del metodo

	// HELPERS
	private String createBodyJson(body3 body) {
		return new JSONObject()
				.put("IdCliente", body.getIDCliente())
				.put("Token", "77D5BDD4-1FEE-4A47-86A0-1E7D19EE1C74")
				.toString();
	}

	private configuracion getConfiguracion() {
		return configuracionService.findByServiceName("getPedido")
				.orElseThrow(() -> new RuntimeException("Configuration not found"));
	}

	private boolean isOrderExpired(OrdenAlpha existingOrder) {
		LocalDate expirationDate = existingOrder.getFechaCreacion().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(2);
		return LocalDate.now().isAfter(expirationDate);
	}

	private String processNewOrder(body3 body, Cliente cliente, JSONObject jsonResponse) throws IOException, InterruptedException {
		Long expirationTimestamp = getExpirationTimestamp();
		String order = createOrder(body, cliente, jsonResponse, expirationTimestamp);
		OrdenAlpha newOrder = saveOrder(order, cliente, jsonResponse, body);
		return createSuccessResponse(order, expirationTimestamp);
	}

	private Long getExpirationTimestamp() {
		return (System.currentTimeMillis() + 2L * 24 * 60 * 60 * 1000) / 1000L;
	}

	private String createOrder(body3 body, Cliente cliente, JSONObject jsonResponse, Long expirationTimestamp) throws IOException, InterruptedException {
		JSONObject orderJson = new JSONObject()
				.put("line_items", new JSONObject[] {
						new JSONObject()
								.put("name", "Pago OXXO Club Alpha")
								.put("unit_price", body.getMonto() * 100)
								.put("quantity", 1)
				})
				.put("currency", "MXN")
				.put("customer_info", new JSONObject()
						.put("name", cliente.getNombre())
						.put("email", cliente.getEmail())
						.put("phone", "+5218181818181"))
				.put("charges", new JSONObject[] {
						new JSONObject()
								.put("payment_method", new JSONObject()
								.put("type", "oxxo_cash")
								.put("expires_at", expirationTimestamp))
				})
				.put("metadata", new JSONObject()
						.put("NoPedido", jsonResponse.get("NoPedido"))
						.put("TitularCuenta", cliente.getNombre())
						.put("IDCliente", cliente.getIdCliente())
						.put("Membresia", cliente.getNoMembresia()));


		//System.out.println("JSON REQUEST: " + orderJson);
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://api.digitalfemsa.io/orders"))
				.header("Accept", "application/vnd.conekta-v2.1.0+json")
				.header("Content-Type", "application/json")
				.header("Accept-Language", "es")
				.header("Authorization", apikey)
				.method("POST", HttpRequest.BodyPublishers.ofString(orderJson.toString()))
				.build();
		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		//System.out.println("RESPONSE CONEKTA: " + response.body());
		if (response.statusCode() != 200) {
			log.info("Error al generar referencia Oxxo: statuscode = {} message = {}", response.statusCode(), response.body());
			throw new RuntimeException("Error al crear la referencia Oxxo. Código de estado: " + response.statusCode());
		}
		return response.body();
	}

	private OrdenAlpha saveOrder(String order, Cliente cliente, JSONObject jsonResponse, body3 body) {
		try {
			JSONObject orderJson = new JSONObject(order);
			OrdenAlpha ordenAlpha = new OrdenAlpha();
			ordenAlpha.setFechaCreacion(new Date());
			ordenAlpha.setJson(order);
			ordenAlpha.setMonto(body.getMonto());
			ordenAlpha.setNotarjeta(orderJson.getJSONObject("charges").getJSONArray("data").getJSONObject(0).getJSONObject("payment_method").getString("reference"));
			ordenAlpha.setNoAutorizacion(orderJson.getString("id"));
			ordenAlpha.setNoPedido(jsonResponse.getInt("NoPedido"));
			ordenAlpha.setTitularCuenta(cliente.getNombre());
			ordenAlpha.setIdCliente(cliente.getIdCliente());
			ordenAlphaService.save(ordenAlpha);

			return ordenAlpha;
		} catch (JSONException e) {
			throw new JSONException("No se pudo obtener el valor de la referencia: " + e.getMessage());
		} catch (Exception e) {
			throw new RuntimeException("Error inesperado al guardar la orden: " + e.getMessage());
		}
	}

	private String createSuccessResponse(String order, Long expirationTimestamp) {
		JSONObject orderJson = new JSONObject(order);

		JSONObject responseJson = new JSONObject()
				.put("totalPago", orderJson.getJSONObject("line_items").getJSONArray("data").getJSONObject(0).getInt("unit_price") / 100)
				.put("fechaExpiracion", new Timestamp(expirationTimestamp * 1000).toString())
				.put("numeroReferencia", orderJson.getJSONObject("charges").getJSONArray("data").getJSONObject(0).getJSONObject("payment_method").getString("reference"));

		return responseJson.toString();
	}

	private String getExistingOrderResponse(OrdenAlpha existingOrder) {
		JSONObject responseJson = new JSONObject()
				.put("totalPago", existingOrder.getMonto())
				.put("fechaExpiracion", existingOrder.getFechaCreacion().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(2).toString())
				.put("numeroReferencia", existingOrder.getNotarjeta());

		return responseJson.toString();
	}

	private String handleException(Exception e) {
		JSONObject errorJson = new JSONObject()
				.put("respuesta", e.getMessage());
		//e.printStackTrace();
		return errorJson.toString();
	}
}
