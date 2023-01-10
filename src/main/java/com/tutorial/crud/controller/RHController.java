package com.tutorial.crud.controller;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tutorial.crud.aopDao.endpoints;
import com.tutorial.crud.correo.Correo;
import com.tutorial.crud.entity.*;
import com.tutorial.crud.security.service.RolService;
import com.tutorial.crud.security.service.UsuarioService;
import com.tutorial.crud.service.*;

import javax.persistence.Tuple;


/**
 * 	Esta clase permite hacer uso de todos los service para crear, actualizar y obtener las entidades mapeadas
 * @author: Daniel García Velasco y Abimael Rueda Galindo
 * @version: 12/7/2021
 *
 */

@RestController
@RequestMapping("/rh")
@CrossOrigin(origins = "*")
public class RHController 
{

	endpoints e = new endpoints();
	
    @Autowired
    configuracionService configuracionService;
    
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;
    
	@Autowired
	RHEmpleadoService rhempleadoService;
	
    @Autowired
    PasswordEncoder passwordEncoder;
    
	@Autowired
	RHSolicitudService rhsolicitudService;	

	@Autowired
	RHAprovadosService rhaprovadoService;	

	@Autowired
	RHFirmaService rhfirmaService;

	@Autowired
	EmpleadoService empleadoService;

	@Value("${my.property.usuarioCorreo}")
	String usuarioCorreo;

	@Value("${my.property.contrasenaCorreo}")
	String contrasenaCorreo;
	
	
	@Value("${my.property.nombre}")
	String nombre;
	
	@Value("${my.property.nombreUsuario}")
	String nombreUsuario;
	
	@Value("${my.property.password}")
	String password;
	
	@Value("${my.property.login}")
	String login;
	
	@Value("${my.property.Token}")
	String Token;
	
	@Value("${my.property.getUsuarios}")
	String getUsuarios;			

	@Value("${my.property.GetMovimientosbyId}")
	String GetMovimientosbyId;
	
	@Value("${my.property.usuarioData}")
	String user;
	
	@Value("${my.property.passwordData}")
    String pass;
	
	@Value("${my.property.urlData}")
    String url;

	@Value("${my.property.data}")
	String dbURL;

	@Value("${my.property.userData}")
	String userData;

	@Value("${my.property.passData}")
    String passData;
	
	@Value("${my.property.receptor}")
	String receptor;
	
	@Value("${my.property.copiaOculta}")
	String copiaOculta;
	
		
		@GetMapping("/getEmpleados")
		@ResponseBody
		public ResponseEntity<?> getEmpleados(){
			Connection conn = null;
          	ArrayList<RHEmpleado> listaReporte = new ArrayList<RHEmpleado>();
	        try {
	            // Carga el driver de oracle
	        	DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
	            conn = DriverManager.getConnection(dbURL, userData, passData);
	            
	            PreparedStatement ps=conn.prepareStatement("EXEC DataFlowAlpha.dbo.sp_Consulta_RH_Empleado");
              	ResultSet rs =ps.executeQuery();
                while (rs.next()) {
                	RHEmpleado to = new RHEmpleado();

                	to.setId(rs.getInt(1));
                	to.setEmpleado(rs.getString(2));
                	to.setIniciales(rs.getString(3));
                	to.setActivo(rs.getString(4));
                	to.setClub(rs.getString(5));
                	to.setDepartamento(rs.getString(6));
                	to.setPuesto(rs.getString(7));
                	to.setClaveExterna(rs.getString(8));
                	to.setIdEmpleado(rs.getInt(9));
                	to.setRfc(rs.getString(10));
                	to.setCurp(rs.getString(11));
                	to.setImss(rs.getString(12));
                	to.setEmpleadoTipo(rs.getString(13));
                	to.setFechaAlta(rs.getDate(14));
                    to.setFechaNacimiento(rs.getDate(15));
					to=rhempleadoService.save(to);
                    //listaReporte.add(to);
                }
              
            	conn.close();
	        } catch (SQLException ex) {
	            System.out.println("Error: " + ex.getMessage());
	            ex.printStackTrace();
	        } finally {
	            try {
	            	conn.close();
	            } catch (SQLException ex) {
	                System.out.println("Error: " + ex.getMessage());
	            }
	        }
			listaReporte = new ArrayList<RHEmpleado>(rhempleadoService.list());
			
			ArrayList<RHEmpleado> empleadosActivos = new ArrayList<>();

			for (RHEmpleado rhEmpleado:listaReporte){
				if (rhEmpleado.getActivo().equals("SI")){
					empleadosActivos.add(rhEmpleado);
					if(empleadoService.existsById(rhEmpleado.getIdEmpleado())){
						//System.out.println("Empleado: " + rhEmpleado.getIdEmpleado() + " ya existe en empleados.");
						continue;
					} else {
						System.out.println("Agregando nuevo empleado: " + rhEmpleado.getIdEmpleado());
						Empleado empleado = new Empleado();
						empleado.setIdEmpleado(rhEmpleado.getIdEmpleado());
						empleadoService.save(empleado);
					}
				}else if(rhEmpleado.getActivo().equals("NO")) {
					if(empleadoService.existsById(rhEmpleado.getIdEmpleado())){
						//System.out.println("Empleado inactivo detectado: " + rhEmpleado.getIdEmpleado());
						empleadoService.deleteById(rhEmpleado.getIdEmpleado());
						//System.out.println("Borrado exitosamente");
					}
				}
			}
	        
			return new ResponseEntity<>(empleadosActivos, HttpStatus.OK);
		}
		
		@GetMapping("/historicoEmpleado/{horarioId}")
		@ResponseBody
		public ResponseEntity<?> registroVehicular(@PathVariable("horarioId") int horarioId){
			RHEmpleado empleado=rhempleadoService.findByIdEmpleado(horarioId);
			List<RHSolicitud> solicitudes=rhsolicitudService.getByEmpleado(empleado);
			
			
	   		return new ResponseEntity<>(solicitudes, HttpStatus.OK);
		}
		
		
		@PostMapping("/solicitudesPorEmpleado")
		@ResponseBody
		public ResponseEntity<?> solicitudesPorEmpleado(@RequestBody Body body){
			RHEmpleado empleado=rhempleadoService.findByIdEmpleado(body.getEmpleado());
			List<RHSolicitud> solicitudes=rhsolicitudService.getByEmpleado(empleado);
			
			Date fechaAlta=empleado.getFechaAlta();
			SimpleDateFormat getDiaMes = new SimpleDateFormat("dd-MM");
	        String diaMes = getDiaMes.format(fechaAlta);
	        
			Date date = new Date();
			ZoneId timeZone = ZoneId.systemDefault();
	        LocalDate getLocalDate = date.toInstant().atZone(timeZone).toLocalDate();
	        
			int anioPeriodo;
			anioPeriodo=getLocalDate.getYear();
	        String periodoActual=diaMes+"-"+anioPeriodo;
	        
	        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
	        Date fecha = null;
	        try {
				fecha = formato.parse(periodoActual);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        if(fecha.after(date)) {
	        	anioPeriodo=getLocalDate.getYear()-1;
		         periodoActual=diaMes+"-"+anioPeriodo;
		        
		         formato = new SimpleDateFormat("dd-MM-yyyy");
		         fecha = null;
		        try {
					fecha = formato.parse(periodoActual);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        List<RHSolicitud> solicitudesPeriodoActual=new ArrayList<RHSolicitud>();
	        for(int i=0;i<solicitudes.size();i++) {
	        	Date fechaInicio=solicitudes.get(i).obtenerFechaInicio();
	        	if(fechaInicio.after(fecha) || fechaInicio.getTime()-fecha.getTime()==0) {
	        		solicitudesPeriodoActual.add(solicitudes.get(i));
	        	}
	        }
	        
			
	   		return new ResponseEntity<>(solicitudesPeriodoActual, HttpStatus.OK);
		}
		@PostMapping("/crearSolicitud")
		@ResponseBody
		public ResponseEntity<?> crearSolicitud(@RequestBody Body body){
			RHSolicitud nuevaSolicitud=new RHSolicitud();
			RHEmpleado rhempleado=rhempleadoService.findByIdEmpleado(body.getEmpleado());
			nuevaSolicitud.setEmpleado(rhempleado);
			nuevaSolicitud.setFechaFin(body.getFechaFin());
			nuevaSolicitud.setFechaInicio(body.getFechaInicio());
			nuevaSolicitud.setFechaSolicitud(new Date());
			nuevaSolicitud.setSolicita(body.getSolicita());
			nuevaSolicitud.setDiasMenos(body.getDiasMenos());
			
			RHAprovados aprovado=new RHAprovados();
			RHFirma firma=new RHFirma();
			
			aprovado.setSolicitud(nuevaSolicitud);
			nuevaSolicitud.setAprovados(aprovado);
			
			firma.setSolicitud(nuevaSolicitud);
			nuevaSolicitud.setFirma(firma);
			
			rhempleado.obtenerSolicitudes().add(nuevaSolicitud);
			rhempleadoService.save(rhempleado);
			
			Correo correo=new Correo(usuarioCorreo,contrasenaCorreo,receptor,copiaOculta);
			correo.enviar_correo(rhempleado.getEmpleado(), rhempleado.getIdEmpleado());
			
	   		JSONObject json=new JSONObject();
	   		json.put("respuesta", "solicitud enviada correctamente");
	   	
	   		return new ResponseEntity<>(json.toString(), HttpStatus.OK);
	       
			
		}
		
		@PostMapping("/setAprovado")
		@ResponseBody
		public ResponseEntity<?> setAprovado(@RequestBody Body body){
			RHAprovados aprovado=rhaprovadoService.findByIdSolicitud(rhsolicitudService.getOne(body.getIdSolicitud()).get());
			aprovado.setAprovado(body.getAprovado());
			aprovado.setFechaAprovacion(new Date());
			rhaprovadoService.save(aprovado);
			
	   		JSONObject json=new JSONObject();
	   		json.put("respuesta", "informacion guardada correctamente");
	   	
	   		return new ResponseEntity<>(json.toString(), HttpStatus.OK);
	       
			
		}
		
		@PostMapping("/firmaEmpleado")
		@ResponseBody
		public ResponseEntity<?> firmaEmpleado(@RequestBody Body body){
			RHFirma firma=rhfirmaService.findByIdSolicitud(rhsolicitudService.getOne(body.getIdSolicitud()).get());
			firma.setEntregaDocumento(body.getEntragaDocumento());
			firma.setValidacion(body.getValidacion());
			firma.setFechaValidacion(new Date());
			rhfirmaService.save(firma);
			
			
	   		JSONObject json=new JSONObject();
	   		json.put("respuesta", "informacion guardada correctamente");
	   	
	   		return new ResponseEntity<>(json.toString(), HttpStatus.OK);
	       
			
		}


	/* ----------------------- Asignar Correo Electronico ------------------------------*/
	@PreAuthorize("hasRole('ADMIN')") //Con esto le exigimos un token en el Request
	@PostMapping("/asignarCorreo")
	@ResponseBody
	public ResponseEntity<?> asignarCorreo(@RequestBody ObjectNode objectNode){
		String idEmpleado = objectNode.get("idEmpleado").asText();
		String correo = objectNode.get("correo").asText();
		Empleado empleado = empleadoService.findByIdEmpleado(Integer.parseInt(idEmpleado));

		JSONObject response=new JSONObject();

		if(empleado == null) {
			response.put("respuesta", "Empleado no existe.");
			return new ResponseEntity<>(response.toString(), HttpStatus.NOT_FOUND);
		}
		try {
			empleado.setCorreo(correo);
			empleadoService.save(empleado);

		} catch (DataIntegrityViolationException e) {
			response.put("respuesta", "CORREO  ya fue asignado.");
			return new ResponseEntity<>(response.toString(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.put("respuesta", "CORREO  asignado correctamente.");
		return new ResponseEntity<>(response.toString(), HttpStatus.OK);
	}

	//--------------   Service para enviar un correo de cumpleaños a las 9:00 a.m ----------------------->>>>>>

	@Scheduled(cron = "0 0 9 * * *")
	public void verificaCumpleaños() {
			
			try {
				Date today = new Date();
				int day = today.getDate();
				int month = today.getMonth() + 1;

				String dayStr = "";
				String monthStr = "";
				if(day < 10) {
					dayStr += "0" + String.valueOf(day);
				} else {
					dayStr += String.valueOf(day);
				}

				if(month < 10) {
					monthStr += "0" + String.valueOf(month);
				} else {
					monthStr += String.valueOf(month);
				}
				List<Tuple> t = rhempleadoService.getEmpleadosCumpleaños(dayStr, monthStr);

				for (Tuple tuple:t){
					String correoEmpleado = "";
					String club = "";
					int idEmpleado;

					idEmpleado = tuple.get(0, Integer.class);
					club = tuple.get(2, String.class);

					Empleado empleado = empleadoService.findByIdEmpleado(idEmpleado);
					//correoEmpleado = tuple.get(4, String.class);

					if (!club.equals("") && empleado != null){
						if(empleado.getCorreo() == null) {
							continue;
						} else {
							try {
								correoEmpleado = empleado.getCorreo();
								Correo correo = new Correo(usuarioCorreo, contrasenaCorreo, correoEmpleado);
								correo.sendBirthdayEmail("¡Feliz Cumpleaños!", club);
							}catch(Exception e) {
								continue;
							}
							
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				//System.out.println("Error en la tarea envio de correos de cumpleaños...");
			}

	}
}//fin de la clase

