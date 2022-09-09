package com.tutorial.crud.controller;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
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
import com.tutorial.crud.service.EnlaceService;
import com.tutorial.crud.service.OrdenAlphaService;
import com.tutorial.crud.service.configuracionService;

import mx.openpay.client.Charge;
import mx.openpay.client.core.OpenpayAPI;
import mx.openpay.client.core.requests.transactions.CancelParams;

@RestController
@RequestMapping("/pagos")
@CrossOrigin(origins = "*")
public class PagoBBVA {

	@Value("${my.property.apikeyOpenpay}")
	String privateKey;
	
	@Value("${my.property.idOpenpay}")
	String merchantId;
	
	@Value("${my.property.basic}")
	String token;
	
	endpoints e = new endpoints();
    
    @Autowired
    configuracionService configuracionService;
    
    @Autowired
    ClienteService clienteService;
    
    @Autowired
    OrdenAlphaService ordenAlphaService;
    
    @Autowired
    EnlaceService enlaceService;
    
	@PostMapping("/openpay")
    public String crearPago(@RequestBody body3 body) {
	//paso 1 obtener datos del cliente
		String body2 = "{\n"
    			+ "\"IdCliente\":"+body.getIDCliente()+",\n"
    			+ "\"Token\":\"77D5BDD4-1FEE-4A47-86A0-1E7D19EE1C74\"\n"
    			+ "}";
		try{
			configuracion o = configuracionService.findByServiceName("getPedido").get(); 
	    	JSONObject json=new JSONObject(e.conectaApiClubPOST(body2,o.getEndpointAlpha()));
	    	Cliente cliente=clienteService.findById(body.getIDCliente());
	    	//paso 2 colocar apikey
	    
			//crear cargo verificar si existe el cargo si no existe crear uno nuevo
			OrdenAlpha oa=ordenAlphaService.getByIdCliente(body.getIDCliente());
			if(oa==null) {				
				Date dueDate=new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(dueDate);
				calendar.add(Calendar.DAY_OF_YEAR, 2);
				String query="https://sandbox-api.openpay.mx/v1/"+merchantId+"/charges";
				 SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				String body3 = "{\r\n"
						+ "    \"method\" : \"store\",\r\n"
						+ "   \"amount\" : "+body.getMonto()+",\r\n"
						+ "   \"description\" : \""+json.getString("NoPedido")+"\",\r\n"
						+ "   \"due_date\" : \""+sf.format(calendar.getTime())+"\",\r\n" 
						+ "   \"customer\":{\r\n"
						+ "       \"name\":\""+cliente.getNombre()+" "+cliente.getApellidoPaterno()+" "+cliente.getApellidoMaterno()+"\",\r\n"
						+ "       \"email\":\""+cliente.getEmail()+"\"\r\n"
						+ "   }   \r\n"
						+ "}";
	            URL url = new URL(query);
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            String basicAuth = "Basic "+token;
	
	            conn.setRequestProperty ("Authorization", basicAuth);
	
	            conn.setConnectTimeout(5000);
	            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            conn.setRequestMethod("POST");
	            OutputStream os = conn.getOutputStream();
	            os.write(body3.toString().getBytes("UTF-8"));
	            os.close();
	
	            InputStream in = new BufferedInputStream(conn.getInputStream());
	            String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
	            JSONObject respuesta = new JSONObject(result);
	            in.close();
	            conn.disconnect();
				OrdenAlpha ordenAlpha=new OrdenAlpha();
				ordenAlpha.setFechaCreacion(new Date());
				ordenAlpha.setJson(respuesta.toString());
				ordenAlpha.setMonto(body.getMonto());
				ordenAlpha.setNotarjeta(respuesta.getJSONObject("payment_method").getString("reference"));
				ordenAlpha.setNoAutorizacion(respuesta.getString("id"));
				ordenAlpha.setTitularCuenta(cliente.getNombre());
				ordenAlpha.setIdCliente(body.getIDCliente());
				ordenAlpha.setNoPedido(json.getInt("NoPedido"));
				ordenAlphaService.save(ordenAlpha);
				JSONObject json2=new JSONObject();
				json2.put("totalPago", body.getMonto());
				  Timestamp ts=new Timestamp(calendar.getTime().getTime());  
				  json2.put("fechaExpiracion", ts);
				  json2.put("numeroReferencia", respuesta.getJSONObject("payment_method").getString("reference"));
			    return json2.toString();
			}else {
				LocalDate expiracion=oa.getFechaCreacion().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(2);
				Date horaActual=new Date();
				Date fechaexpiracion=java.util.Date.from(expiracion.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
				if(horaActual.after(fechaexpiracion)) {
					Date dueDate=new Date();
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(dueDate);
					calendar.add(Calendar.DAY_OF_YEAR, 2);
					String query="https://sandbox-api.openpay.mx/v1/"+merchantId+"/charges";
					 SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					String body3 = "{\r\n"
							+ "    \"method\" : \"store\",\r\n"
							+ "   \"amount\" : "+body.getMonto()+",\r\n"
							+ "   \"description\" : \""+json.getInt("NoPedido")+"\",\r\n"
							+ "   \"due_date\" : \""+sf.format(calendar.getTime())+"\",\r\n" 
							+ "   \"customer\":{\r\n"
							+ "       \"name\":\""+cliente.getNombre()+"\",\r\n"
							+ "       \"email\":\""+cliente.getEmail()+"\"\r\n"
							+ "   }   \r\n"
							+ "}";
		            URL url = new URL(query);
		            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		            String basicAuth = "Basic "+token;
		
		            conn.setRequestProperty ("Authorization", basicAuth);
		
		            conn.setConnectTimeout(5000);
		            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		            conn.setDoOutput(true);
		            conn.setDoInput(true);
		            conn.setRequestMethod("POST");
		            OutputStream os = conn.getOutputStream();
		            os.write(body3.toString().getBytes("UTF-8"));
		            os.close();
		
		            InputStream in = new BufferedInputStream(conn.getInputStream());
		            String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
		            JSONObject respuesta = new JSONObject(result);
		            in.close();
		            conn.disconnect();
					OrdenAlpha ordenAlpha=new OrdenAlpha();
					ordenAlpha.setFechaCreacion(new Date());
					ordenAlpha.setJson(respuesta.toString());
					ordenAlpha.setMonto(body.getMonto());
					ordenAlpha.setNotarjeta(respuesta.getJSONObject("payment_method").getString("reference"));
					ordenAlpha.setNoAutorizacion(respuesta.getString("id"));
					ordenAlpha.setTitularCuenta(cliente.getNombre());
					ordenAlpha.setIdCliente(body.getIDCliente());
					ordenAlpha.setNoPedido(json.getInt("NoPedido"));
					ordenAlphaService.save(ordenAlpha);
					JSONObject json2=new JSONObject();
					json2.put("totalPago", body.getMonto());
					  Timestamp ts=new Timestamp(calendar.getTime().getTime());  
					  json2.put("fechaExpiracion", ts);
					  json2.put("numeroReferencia", respuesta.getJSONObject("payment_method").getString("reference"));
				    return json2.toString();
				}else {
					JSONObject json2=new JSONObject();
					json2.put("totalPago", oa.getMonto()); 
					json2.put("fechaExpiracion", oa.getFechaCreacion().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(2));
					json2.put("numeroReferencia", oa.getNotarjeta());
				  	return json2.toString();					
				}
				
			}
			
			}catch (JSONException | IOException e) {
				JSONObject json2=new JSONObject();
				json2.put("respuesta", "No se encontraron pedidos");
				e.printStackTrace();
				return json2.toString();
			}
    }//fin del metodo
	
	@PostMapping("/statusOpenpay")
    public String consultarPago(@RequestBody body3 body) {
		OpenpayAPI api = new OpenpayAPI("https://sandbox-api.openpay.mx", privateKey, merchantId);
		try{
			Charge order= api.charges().get(body.getIdOrden());
			JSONObject json=new JSONObject();
			//pending_payment, declined, expired, paid, refunded, partially_refunded, charged_back, pre_authorized y voided.
			return json.put("Respuesta", order.getStatus()).toString();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		

    }//fin del metodo
	
	@PostMapping("/cancelarOpenpay")
    public String cancelarPago(@RequestBody body3 body) {
		OpenpayAPI api = new OpenpayAPI("https://sandbox-api.openpay.mx", privateKey, merchantId);
		JSONObject json=new JSONObject();
		CancelParams cancelParams=new CancelParams();
		try{
			api.charges().cancel(cancelParams.chargeId(body.getIdOrden()));
			return json.put("Respuesta", "orden cancelada").toString();
		}catch (Exception e) {
			json.put("Respuesta", "error cancelando la orden").toString();
			e.printStackTrace();
		}
		return json.put("Respuesta", "no se puede cancelar la orden").toString();

    }//fin del metodo
}
