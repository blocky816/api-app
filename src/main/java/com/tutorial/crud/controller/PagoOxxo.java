package com.tutorial.crud.controller;


import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

import io.conekta.Charge;
import io.conekta.Conekta;
import io.conekta.Error;
import io.conekta.ErrorList;
import io.conekta.Order;
import io.conekta.OxxoPayment;

@RestController
@RequestMapping("/pagos")
@CrossOrigin(origins = "*")
public class PagoOxxo {
	
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
		String body2 = "{\n"
    			+ "\"IdCliente\":"+body.getIDCliente()+",\n"
    			+ "\"Token\":\"77D5BDD4-1FEE-4A47-86A0-1E7D19EE1C74\"\n"
    			+ "}";
		try{
	    	configuracion o = configuracionService.findByServiceName("getPedido").get(); 
	    	JSONObject json=new JSONObject(e.conectaApiClubPOST(body2,o.getEndpointAlpha()));
	    	Cliente cliente=clienteService.findById(body.getIDCliente());
			Conekta.setApiKey(apikey);
			Conekta.apiVersion = "2.0.0";
			OrdenAlpha oa=ordenAlphaService.getByIdCliente(body.getIDCliente());
			
			if(oa==null) {
				Long nowUnixTimestamp = System.currentTimeMillis();
				Long thirtyDaysFromNowUnixTimestamp =  (nowUnixTimestamp + 2L * 24 * 60 * 60 * 1000) / 1000L;
				String thirtyDaysFromNow = thirtyDaysFromNowUnixTimestamp.toString(); 
				Order order = Order.create(
					new JSONObject("{"
					  + "'line_items': [{"
						  + "'name': 'Pago OXXO Club Alpha',"
						  + "'unit_price': "+(int)body.getMonto()*100+","
						  + "'quantity': 1"
					  + "}],"
					  + "'currency': 'MXN',"
					  + "'customer_info': {"
						+ "'name': '"+cliente.getNombre()+"',"
						+ "'email': '"+cliente.getEmail()+"',"
						+ "'phone': '+5218181818181'"
					  + "},"
					  +  "'charges':[{"
						+ "'payment_method': {"
						  + "'type': 'oxxo_cash',"
						  + "'expires_at': " + thirtyDaysFromNow
						+ "}"
					  + "}],"
					  + "'metadata':{"
					  	+ "'NoPedido':'"+json.get("NoPedido")+"',"
					  	+ "'TitularCuenta':'"+cliente.getNombre()+"',"
					  	+ "'IDCliente':'"+cliente.getIdCliente()+"',"
						+ "'Membresia':'"+cliente.getNoMembresia()+"'"
					  + "}"
					+ "}"
					)
				  );
				
				  Charge charge = (Charge) order.charges.get(0);
				  OxxoPayment oxxoPayment = (OxxoPayment) charge.payment_method;
				  OrdenAlpha ordenAlpha=new OrdenAlpha();
				  ordenAlpha.setFechaCreacion(new Date());
				  ordenAlpha.setJson(order.toString());
				  ordenAlpha.setMonto(body.getMonto());
				  ordenAlpha.setNotarjeta(oxxoPayment.reference);
				  ordenAlpha.setNoAutorizacion(order.id);
				  ordenAlpha.setNoPedido(json.getInt("NoPedido"));
				  ordenAlpha.setTitularCuenta(cliente.getNombre());
				  ordenAlpha.setIdCliente(body.getIDCliente());
				  ordenAlphaService.save(ordenAlpha);
				  JSONObject json2=new JSONObject();
				  json2.put("totalPago", body.getMonto());
				  Timestamp ts=new Timestamp(new Date(Long.parseLong(thirtyDaysFromNow)*1000).getTime());  
				  json2.put("fechaExpiracion", ts);
				  json2.put("numeroReferencia", oxxoPayment.reference);
			      return json2.toString();
			}else {
				LocalDate expiracion=oa.getFechaCreacion().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(2);
				Date horaActual=new Date();
				Date fechaexpiracion=java.util.Date.from(expiracion.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
				if(horaActual.after(fechaexpiracion)) {
					Long nowUnixTimestamp = System.currentTimeMillis();
					Long thirtyDaysFromNowUnixTimestamp =  (nowUnixTimestamp + 2L * 24 * 60 * 60 * 1000) / 1000L;
					String thirtyDaysFromNow = thirtyDaysFromNowUnixTimestamp.toString(); 
					Order order = Order.create(
						new JSONObject("{"
						  + "'line_items': [{"
							  + "'name': 'Pago OXXO Club Alpha',"
							  + "'unit_price': "+(int)body.getMonto()*100+","
							  + "'quantity': 1"
						  + "}],"
						  + "'currency': 'MXN',"
						  + "'customer_info': {"
							+ "'name': '"+cliente.getNombre()+"',"
							+ "'email': '"+cliente.getEmail()+"',"
							+ "'phone': '+5218181818181'"
						  + "},"
						  +  "'charges':[{"
							+ "'payment_method': {"
							  + "'type': 'oxxo_cash',"
							  + "'expires_at': " + thirtyDaysFromNow
							+ "}"
						  + "}],"
						  + "'metadata':{"
						  	+ "'NoPedido':'"+json.get("NoPedido")+"',"
						  	+ "'TitularCuenta':'"+cliente.getNombre()+"',"
						  	+ "'IDCliente':'"+cliente.getIdCliente()+"',"
							+ "'Membresia':'"+cliente.getNoMembresia()+"'"
						  + "}"
						+ "}"
						)
					  );
					
					  Charge charge = (Charge) order.charges.get(0);
					  OxxoPayment oxxoPayment = (OxxoPayment) charge.payment_method;
					  OrdenAlpha ordenAlpha=new OrdenAlpha();
					  ordenAlpha.setFechaCreacion(new Date());
					  ordenAlpha.setJson(order.toString());
					  ordenAlpha.setMonto(body.getMonto());
					  ordenAlpha.setNotarjeta(oxxoPayment.reference);
					  ordenAlpha.setNoAutorizacion(order.id);
					  ordenAlpha.setNoPedido(json.getInt("NoPedido"));
					  ordenAlpha.setTitularCuenta(cliente.getNombre());
					  ordenAlpha.setIdCliente(body.getIDCliente());
					  ordenAlphaService.save(ordenAlpha);
					  JSONObject json2=new JSONObject();
					  json2.put("totalPago", body.getMonto());
					  Timestamp ts=new Timestamp(new Date(Long.parseLong(thirtyDaysFromNow)*1000).getTime());  
					  json2.put("fechaExpiracion", ts);
					  json2.put("numeroReferencia", oxxoPayment.reference);
				      return json2.toString();
				}else {
					JSONObject json2=new JSONObject();
					json2.put("totalPago", oa.getMonto()); 
					json2.put("fechaExpiracion", oa.getFechaCreacion().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(2));
					json2.put("numeroReferencia", oa.getNotarjeta());
					
					
				  	return json2.toString();					
				}
				
			}
			
			}catch (JSONException | ErrorList | Error e) {
				JSONObject json2=new JSONObject();
				json2.put("respuesta", "No se encontraron pedidos");
				e.printStackTrace();
				
				return json2.toString();
			}
    }//fin del metodo
	
	@PostMapping("/status")
    public String consultarPago(@RequestBody body3 body) {
		Conekta.setApiKey(apikey);
		Conekta.setApiVerion("2.0.0");
		try{
			Order order = Order.find(body.getIdOrden());
			JSONObject json=new JSONObject();
			
			
			
			//pending_payment, declined, expired, paid, refunded, partially_refunded, charged_back, pre_authorized y voided.
			if(order.payment_status.equals("pending_payment")) {
				return json.put("Respuesta", "Pago pendiente").toString();				
			}
				
			if(order.payment_status.equals("declined")) {
				return json.put("Respuesta", "Declinado").toString();	
			}
			if(order.payment_status.equals("expired")) {
				return json.put("Respuesta", "Expirado").toString();
			}
			if(order.payment_status.equals("paid")) {
				return json.put("Respuesta", "Pagado").toString();
			}
			
			if(order.payment_status.equals("refunded")) {
				return json.put("Respuesta", "Reintegrado").toString();
			}
			if(order.payment_status.equals("partially_refunded")) {
				return json.put("Respuesta", "Parcialmente reintegrado").toString();
			}
			if(order.payment_status.equals("charged_back")) {
				return json.put("Respuesta", "Contracargo").toString();
			}
			if(order.payment_status.equals("pre_authorized")) {
				return json.put("Respuesta", "Preautorizado").toString();
			}
			if(order.payment_status.equals("voided")) {
				return json.put("Respuesta", "Vacío").toString();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		

    }//fin del metodo
	@PostMapping("/cancelar")
    public String cancelarPago(@RequestBody body3 body) {
		Long nowUnixTimestamp = System.currentTimeMillis();
		Long thirtyDaysFromNowUnixTimestamp =  (nowUnixTimestamp + 1L * 24 * 60 * 60 * 1000) / 1000L;
		String thirtyDaysFromNow = thirtyDaysFromNowUnixTimestamp.toString(); 

		Conekta.setApiKey(apikey);
		Conekta.apiVersion = "2.0.0";
		try{
			JSONObject data = new JSONObject("{"
					  + "'payment_method': {"
					    + "'type': 'oxxo_cash',"
					    + "'expires_at': "+thirtyDaysFromNow
					  + "}"
					+"}");

					Order order = Order.find(body.getIdOrden());
					Charge charge = order.createCharge(data);
			return "list";
		}catch (ErrorList e) {
			e.printStackTrace(); 
		} catch (Error e) {
			e.printStackTrace(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "La orden no ha sido Cancelada";

    }//fin del metodo
}
