package com.tutorial.crud.controller;



import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.tutorial.crud.aopDao.endpoints;
import com.tutorial.crud.entity.configuracion;
import org.hibernate.PropertyAccessException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tutorial.crud.entity.Toalla;
import com.tutorial.crud.security.entity.Usuario;
import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.RHEmpleado;
import com.tutorial.crud.service.ClienteService;
import com.tutorial.crud.service.RHEmpleadoService;
import com.tutorial.crud.service.ToallaService;

import com.tutorial.crud.service.configuracionService;


@RestController
@RequestMapping("/toalla")
@CrossOrigin(origins = "*")
public class ToallaController
{
    @Autowired
    ToallaService toallaService;

    @Autowired
    ClienteService clienteService;

    @Autowired
    RHEmpleadoService rHEmpleadoService;

    @Autowired
    configuracionService configuracionService;

    endpoints e = new endpoints();

    @GetMapping("/obtenerRegistro")
    @ResponseBody
    public ResponseEntity<?> obtenerRegistroToallas(){
        ArrayList<Toalla> registroToallas = new ArrayList<Toalla>(toallaService.findAll());

        return new ResponseEntity<>(registroToallas, HttpStatus.OK);
    }

    /**
     * Asignar una o varias toallas a un cliente activo
     * 
     */
    @PostMapping("/asignarCliente")
    @ResponseBody
    public ResponseEntity<?> asignarToalla(@RequestBody ObjectNode toallaBodyRequest){
        
        JSONObject response = new JSONObject();
        
        if(toallaBodyRequest.get("idCliente") == null || toallaBodyRequest.get("club") == null || toallaBodyRequest.get("toallaCH") == null
        || toallaBodyRequest.get("toallaG") == null || toallaBodyRequest.get("fechaInicio") == null || toallaBodyRequest.get("asignacion") == null){
            response.put("respuesta", "Todos los campos son obligatorios");
            return new ResponseEntity<>(response.toMap(), HttpStatus.CONFLICT);
        }
        int idCliente = toallaBodyRequest.get("idCliente").asInt();
		String club = toallaBodyRequest.get("club").asText();
		int toallaCH = toallaBodyRequest.get("toallaCH").asInt();
		int toallaG = toallaBodyRequest.get("toallaG").asInt();
		String dateStr = toallaBodyRequest.get("fechaInicio").asText().replace("T", " ");
        Boolean asignacion = (toallaBodyRequest.get("asignacion").asText().equals("0")) ? false : true;


        try {
            Date fechaInicio = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(dateStr);

            try {
                boolean existe = toallaService.existsByIdClienteAndFechaInicio(idCliente, fechaInicio);

                //System.out.println("Existe el registro en toallas: " + existe);
                Cliente cliente = clienteService.findById(idCliente);
                if(cliente == null) {
                    response.put("respuesta", "CLIENTE no encontrado");
                    return new ResponseEntity<>(response.toMap(), HttpStatus.NOT_FOUND);
                } else if(!cliente.isActivo()) {
                    response.put("respuesta", "CLIENTE no activo");
                    return new ResponseEntity<>(response.toMap(), HttpStatus.OK);
                } else if(toallaService.existsByIdClienteAndFechaInicio(cliente.getIdCliente(), fechaInicio)) {
                    response.put("respuesta", "CLIENTE no tiene permitido más reservas por el resto del día ");
                    return new ResponseEntity<>(response.toMap(), HttpStatus.CONFLICT);
                } else if(toallaCH < 0 || toallaG < 0) {
                    response.put("respuesta", "Valor no válido para TOALLAS");
                    return new ResponseEntity<>(response.toMap(), HttpStatus.CONFLICT);
                } else if (asignacion != false) {
                    response.put("respuesta", "Asignacion no puede ser diferente de '0");
                    return new ResponseEntity<>(response.toMap(), HttpStatus.CONFLICT);
                } else {
                    Toalla toalla = new Toalla();
                    toalla.setIdCliente(cliente.getIdCliente());
                    toalla.setClub(cliente.getClub().getNombre());
                    toalla.setToallaCH(toallaCH);
                    toalla.setToallaG(toallaG);
                    toalla.setFechaInicio(fechaInicio);
                    toalla.setAsignacion(asignacion);
                    toalla.setCliente(cliente.getNombre() + " " + cliente.getApellidoPaterno() + " " + cliente.getApellidoMaterno());

                    toallaService.save(toalla);
                    response.put("respuesta", "TOALLA asignada correctamente");
                    return new ResponseEntity<>(response.toMap(), HttpStatus.OK);
                }
            }catch (Exception e) {
                e.printStackTrace();
                response.put("respuesta", "Error interno");
                return new ResponseEntity<>(response.toMap(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (ParseException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            response.put("respuesta", "No se pudo convertir la fecha correctamente");
            return new ResponseEntity<>(response.toMap(), HttpStatus.CONFLICT);
        }  
    }

    @PostMapping("/liberarToalla")
    @ResponseBody
    public ResponseEntity<?> liberarToalla(@RequestBody ObjectNode toallaBodyRequest){
        
        JSONObject response = new JSONObject();

        if(toallaBodyRequest.get("idCliente") == null || toallaBodyRequest.get("fechaFin") == null || toallaBodyRequest.get("asignacion") == null
        || toallaBodyRequest.get("usuario") == null){
            response.put("respuesta", "Todos los campos son obligatorios");
            return new ResponseEntity<>(response.toMap(), HttpStatus.CONFLICT);
        }
        
        int idCliente = toallaBodyRequest.get("idCliente").asInt();
        String dateStr = toallaBodyRequest.get("fechaFin").asText().replace("T", " ");
        Boolean asignacion = (toallaBodyRequest.get("asignacion").asText().equals("0")) ? false : true;
        String empleado = toallaBodyRequest.get("usuario").asText();
                    
        try {
            Date fechaFin = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(dateStr);

            try {
                Cliente cliente = clienteService.findById(idCliente);
                if(cliente == null) {
                    response.put("respuesta", "CLIENTE no encontrado");
                    return new ResponseEntity<>(response.toMap(), HttpStatus.NOT_FOUND);
                } else if(!cliente.isActivo()) {
                    response.put("respuesta", "CLIENTE no activo");
                    return new ResponseEntity<>(response.toMap(), HttpStatus.CONFLICT);
                } else if(empleado == null) {
                    response.put("respuesta", "EMPLEADO no encontrado");
                    return new ResponseEntity<>(response.toMap(), HttpStatus.NOT_FOUND);
                } else if (asignacion != true) {
                    response.put("respuesta", "Asignacion no puede ser diferente de '1");
                    return new ResponseEntity<>(response.toMap(), HttpStatus.CONFLICT);
                } else if(toallaService.existsByIdClienteAndFechaInicio(cliente.getIdCliente(), fechaFin)) {
                    Toalla toalla = toallaService.getByIdClienteAndFechaInicio(cliente.getIdCliente(), fechaFin);
                    //System.out.println("toalla registro encontrado: " + toalla.getIdCliente() + " y fecha: " + toalla.getFechaFin());
    
                    if(toalla.getFechaFin() != null && toalla.getAsignacion()) {
                        response.put("respuesta", "Registro cerrado");
                        return new ResponseEntity<>(response.toMap(), HttpStatus.CONFLICT); 
                    } 
                    if(fechaFin != null && asignacion && !empleado.equals("") && !empleado.equals("null")) {
                        long time = toalla.getFechaInicio().getTime();
                        //Calcular el tiempo usado de las toallas

                        long difference_In_Time = fechaFin.getTime() - toalla.getFechaInicio().getTime();
                        long difference_In_Seconds = TimeUnit.MILLISECONDS.toSeconds(difference_In_Time) % 60;
                        long difference_In_Minutes = TimeUnit.MILLISECONDS.toMinutes(difference_In_Time) % 60;
                        long difference_In_Hours = TimeUnit.MILLISECONDS.toHours(difference_In_Time) % 24;

                       // Date tiempoTotal = new Date(difference_In_Time);
                        Date tiempoTotal = new Date(toalla.getFechaInicio().getYear(), toalla.getFechaInicio().getMonth(), toalla.getFechaInicio().getDate(),
                                (int) difference_In_Hours, (int) difference_In_Minutes, (int) difference_In_Seconds);
                        System.out.println("Tiempo total: " + tiempoTotal);
                        toalla.setFechaFin(fechaFin);
                        toalla.setAsignacion(asignacion);
                        toalla.setEmpleado(empleado);
                        toalla.setTiempoTotal(tiempoTotal);
                        toallaService.save(toalla);
                        response.put("respuesta", "Retorno de toallas correcto");
                        return new ResponseEntity<>(response.toMap(), HttpStatus.OK);
                    } else {
                        response.put("respuesta", "Todos los campos son obligatorios");
                        return new ResponseEntity<>(response.toMap(), HttpStatus.CONFLICT);
                    }
                } else {
                    response.put("respuesta", "No existe el registro para liberar TOALLAS");
                    return new ResponseEntity<>(response.toMap(), HttpStatus.CONFLICT);
                }
            }catch (Exception e) {
                e.printStackTrace();
                response.put("respuesta", "Error interno");
                return new ResponseEntity<>(response.toMap(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch(ParseException e1){
            e1.printStackTrace();
            response.put("respuesta", "No se pudo convertir la fecha correctamente");
            return new ResponseEntity<>(response.toMap(), HttpStatus.CONFLICT);
        }
        //return null;
    }


    /*------------------- Task para enviar sanciones al no devolver una toalla el dia que te la asignaron --------------------*/

    //@Scheduled(cron = "0 30 10 * * *")
    @GetMapping("/getToallasNoRegresadas")
    public ResponseEntity<?> enviaSanciones() {
		System.out.println("Leyendo tabla toallas...");

        List<Toalla> toallaList = toallaService.findAll();
        List<Toalla> toallasNoDevueltas = new ArrayList<>();

        for (Toalla toalla: toallaList){
            if (toalla.getFechaFin() == null && toalla.getAsignacion() == false){
                int idProd = 0;
                Date fecha = toalla.getFechaInicio();

                if (toalla.getIdCliente() == 0) {
                    System.out.println("CLiente no existe en el registro");
                }
                Cliente cliente = clienteService.findById(toalla.getIdCliente());
                if (cliente != null) {
                    System.out.println("CLiente encontrado: " + cliente.getNombre());
                    System.out.println("email: " + cliente.getEmail());
                }
                //60887 cargar a pascual
                // No existe retorno de toalla del año-mes-dia

                String body2 = "{\r\n"
                        + "\"IDCliente\":"+cliente.getIdCliente()+",  \r\n"
                        + "\"IDClub\":"+cliente.getClub().getIdClub()+",   \r\n"
                        + "\"Cantidad\":1, \r\n"
                        + "\"IDProductoServicio\":"+idProd+",  \r\n"
                        + "\"Observaciones\":\"No existe retorno de toalla del " + toalla.getFechaInicio() +"\",\r\n"
                        + "\"DescuentoPorciento\":0,  \r\n"
                        + "\"FechaInicio\":\""+new java.sql.Date(new Date().getTime())+" 00:00:00\", \r\n"
                        + "\"FechaFin\":\""+new java.sql.Date(new Date().getTime())+" 00:00:00\",  \r\n"
                        + "\"CobroProporcional\":0, \r\n"
                        + "\"Token\":\"77D5BDD4-1FEE-4A47-86A0-1E7D19EE1C74\"  \r\n"
                        + "}";
                System.out.println("Body2:\n" + body2);

                try {
                    URL url = new URL("http://192.168.20.26/ServiciosClubAlpha/api/OrdenDeVenta/Registra");
                    //String postData = "foo1=bar1&foo2=bar2";
                    String postData = body2;
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

                    try (DataOutputStream dos = new DataOutputStream(conn.getOutputStream())) {
                        dos.writeBytes(postData);
                    }
                    int statusCode = conn.getResponseCode();
                    System.out.println("Statuscode: " + statusCode);
                    if (statusCode == 200) {
                        toalla.setSancion("SI");
                    }else {
                        toalla.setSancion("NO");
                    }
                    toallaService.save(toalla);
                    conn.disconnect();

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("e.getMessage(): " + e.getMessage());
                    System.out.println("e.getMessage(): " + e.getLocalizedMessage());
                    System.out.println("e.getMessage(): " + e.getCause());
                }
                toallasNoDevueltas.add(toalla);
            }
        }//fin del for
        return new ResponseEntity<>(toallasNoDevueltas, HttpStatus.OK);
	}

}


