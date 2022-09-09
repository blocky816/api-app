package com.tutorial.crud.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.StoredProcedureQuery;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.annotations.Type;
import org.hibernate.hql.internal.ast.tree.SessionFactoryAwareNode;
import org.hibernate.query.Query;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tutorial.crud.CrudApplication;
import com.tutorial.crud.aopDao.endpoints;
import com.tutorial.crud.correo.Correo;
import com.tutorial.crud.dto.ActividadDTO;
import com.tutorial.crud.dto.ApartadosDTO;
import com.tutorial.crud.dto.ArrayPrueba;
import com.tutorial.crud.dto.ArrayPrueba2;
import com.tutorial.crud.dto.ClienteBasculaDTO;
import com.tutorial.crud.dto.ClienteBasculaVista;
import com.tutorial.crud.dto.ClienteDTO;
import com.tutorial.crud.dto.ClienteDTOApp;
import com.tutorial.crud.dto.ClienteDTOO;
import com.tutorial.crud.dto.EjercicioDTO;
import com.tutorial.crud.dto.HorarioDTO;
import com.tutorial.crud.dto.RutinaDTO;
import com.tutorial.crud.dto.RutinaEjercicioDTO;
import com.tutorial.crud.dto.RutinaSinEjercicios;
import com.tutorial.crud.dto.RutinaSinEjerciciosApp;
import com.tutorial.crud.dto.SalaDTO;
import com.tutorial.crud.dto.TecnicoDTO;
import com.tutorial.crud.dto.TipoActividadDTO;
import com.tutorial.crud.dto.VistaPlantilla;
import com.tutorial.crud.dto.VistaPlantilla2;
import com.tutorial.crud.dto.VistaRutinaCliente;
import com.tutorial.crud.entity.CAActividad;
import com.tutorial.crud.entity.CAApartados;
import com.tutorial.crud.entity.CAApartadosUsuario;
import com.tutorial.crud.entity.CAClase;
import com.tutorial.crud.entity.CAHorario;
import com.tutorial.crud.entity.CASala;
import com.tutorial.crud.entity.CATecnico;
import com.tutorial.crud.entity.CATipoActividad;
import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.ClienteBascula;
import com.tutorial.crud.entity.Club;
import com.tutorial.crud.entity.Ejercicio;
import com.tutorial.crud.entity.Foto;
import com.tutorial.crud.entity.PaseConsumido;
import com.tutorial.crud.entity.PaseUsuario;
import com.tutorial.crud.entity.Registro;
import com.tutorial.crud.entity.RegistroGimnasio;
import com.tutorial.crud.entity.ReportePromocion;
import com.tutorial.crud.entity.Rutina;
import com.tutorial.crud.entity.RutinaEjercicio;
import com.tutorial.crud.entity.TerminalRedencion;
import com.tutorial.crud.entity.AgendaHorario;
import com.tutorial.crud.entity.AgendaReservas;
import com.tutorial.crud.entity.AgendaReservasUsuario;
import com.tutorial.crud.entity.Banda;
import com.tutorial.crud.entity.Body;
import com.tutorial.crud.entity.configuracion;
import com.tutorial.crud.service.ActividadService;
import com.tutorial.crud.service.AgendaHorarioService;
import com.tutorial.crud.service.AgendaReservasService;
import com.tutorial.crud.service.AgendaReservasUsuarioService;
import com.tutorial.crud.service.ApartadosService;
import com.tutorial.crud.service.ApartadosUsuarioService;
import com.tutorial.crud.service.BandaService;
import com.tutorial.crud.service.CAHorarioService;
import com.tutorial.crud.service.ClienteBasculaService;
import com.tutorial.crud.service.ClienteService;
import com.tutorial.crud.service.ClubService;
import com.tutorial.crud.service.EjercicioService;
import com.tutorial.crud.service.FotoServiceImpl;
import com.tutorial.crud.service.PaseConsumidoService;
import com.tutorial.crud.service.PaseUsuarioService;
import com.tutorial.crud.service.RegistroGimnasioService;
import com.tutorial.crud.service.RegistroService;
import com.tutorial.crud.service.RutinaEjercicioService;
import com.tutorial.crud.service.RutinaService;
import com.tutorial.crud.service.SalaService;
import com.tutorial.crud.service.TecnicoService;
import com.tutorial.crud.service.TerminalRedencionService;
import com.tutorial.crud.service.TipoActividadService;
import com.tutorial.crud.service.configuracionService;



/**
 * 	Esta clase permite hacer uso de todos los service para crear, actualizar y obtener las entidades mapeadas
 * @author: Daniel García Velasco y Abimael Rueda Galindo
 * @version: 12/7/2021
 *
 */

@RestController
@RequestMapping("/rutina")
@CrossOrigin(origins = "*")
public class RutinaController 
{
	endpoints e = new endpoints();

    @Autowired
    configuracionService configuracionService;
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	EjercicioService ejercicioService;
	

	@Autowired
	ClienteService clienteService;
	
	@Autowired
	RutinaEjercicioService rutinaEjercicioService;
	

	@Autowired
	AgendaHorarioService agendaHorarioService;
	
	@Autowired
	RutinaService rutinaService;
	

	@Autowired
	AgendaReservasService agendaReservasService;
	
	@Autowired
	AgendaReservasUsuarioService agendaReservasUsuarioService;
	

	@Autowired
	ClienteBasculaService clienteBasculaService;

	@Autowired
	BandaService bandaService;
	
	@Value("${my.property.usuarioCorreo}")
	String usuarioCorreo;
	
	@Value("${my.property.contrasenaCorreo}")
	String contrasenaCorreo;
	
	@Value("${my.property.copiaOculta2}")
	String copiaOculta;
	
	//-------------------------------------- WEB SERVICE EJERCICIO------------------------------------------------------
	/**
	 * Metodo que muestra todos los Miembros almacenados en la base de datos
	 * @return lista de Miembro
	 */
	@GetMapping("/obtenerEjercicio/{idEjercicio}")
	public ResponseEntity<?> obtenerEjercicio(@PathVariable("idEjercicio") int idEjercicio)
	{
		try {
			Ejercicio ejercicio=ejercicioService.getOne(idEjercicio);
			
			return ResponseEntity.ok(ejercicio);
		}catch(Exception e) {			
			JSONObject json=new JSONObject();
			json.put("respuesta", "id incorrecto");
			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
		}
		
	}
	@GetMapping("/listaEjercicios")
	public ResponseEntity<?> listaEjercicios()
	{
		try {
			List<Ejercicio> ejercicios=ejercicioService.list();
			
			return ResponseEntity.ok(ejercicios);
		}catch(Exception e) {			
			JSONObject json=new JSONObject();
			json.put("respuesta", "error");
			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
		}
		
	}
	
	@GetMapping("/deleteEjercicio/{idEjercicio}")
	public ResponseEntity<?> deleteEjercicio(@PathVariable("idEjercicio") int idEjercicio)
	{
		JSONObject json=new JSONObject();
		try {
			Ejercicio ejercicio=ejercicioService.getOne(idEjercicio);
			if(!ejercicio.esActivo()) {
				throw new ParseException("Ejercicio ya eliminado", 0);
			}
			boolean delete=ejercicioService.delete(idEjercicio);
			if(!delete) {
				throw new ParseException("No se pudo eliminar el ejercicio", 0);
			}
			
			json.put("respuesta", "el ejercicio se elimino exitosamente");
			return ResponseEntity.ok(json.toString());
		}catch(Exception e) {			
			json.put("respuesta", "fallo al eliminar el ejercicio");
			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
		}
		
	}
	
	
	
	//Utiliza el idApartado
	@PostMapping("/crearEjercicio")
	public ResponseEntity<?> crearEjercicio(@RequestBody Ejercicio ejercicio) {
		ejercicio.setActivo(true);
		ejercicio.setCreated(new Date());
		ejercicio.setUpdated(new Date());
		ejercicioService.save(ejercicio);
		
		
		JSONObject json=new JSONObject();
		json.put("respuesta", "Ejercicio Creado Exitosamente");
		 return new ResponseEntity<>(json.toString(), HttpStatus.OK);
	}
	
	@PostMapping("/actualizarEjercicio")
	public ResponseEntity<?> actualizarEjercicio(@RequestBody Ejercicio ejercicioActualizado) {

		JSONObject json=new JSONObject();
		try {
			Ejercicio ejercicioDesactualizado=ejercicioService.getOne(ejercicioActualizado.getId());
			ejercicioActualizado.setActivo(ejercicioDesactualizado.esActivo());
			ejercicioActualizado.setCreated(ejercicioDesactualizado.obtenerCreated());
			ejercicioDesactualizado=(Ejercicio) ejercicioActualizado.clone();
			
			ejercicioDesactualizado.setUpdated(new Date());
			ejercicioService.save(ejercicioDesactualizado);
			
	
			json.put("respuesta", "Ejercicio Actualizado Exitosamente");
			 return new ResponseEntity<>(json.toString(), HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json.put("respuesta", "Error al actualizar la información");
			 return new ResponseEntity<>(json.toString(), HttpStatus.CONFLICT);
		}
	}

	
	//-------------------------------------Rutinas webservices-------------------------------------------------
	@PostMapping("/agregarRutinaCliente")
	 @ResponseBody
	@Transactional(rollbackFor = SQLException.class)
	public ResponseEntity<?> agregarRutinaCliente(@RequestBody ArrayPrueba body){
		JSONObject jsonRespuesta=new JSONObject();
		ArrayPrueba2 nuevaRutina=body.getBody().get(0);
		ArrayList<RutinaDTO>listaEjercicios=nuevaRutina.getRutina();
		int idCliente=nuevaRutina.getIdCliente();
		Cliente cliente=clienteService.findById(idCliente);
		Rutina rutina=cliente.obtenerRutina();
		if(rutina==null) {
			rutina=new Rutina();
			rutina.setActivo(false);
			rutina.setCreated(new Date());
			rutina.setNombreObjetivo(nuevaRutina.getNombreObjetivo());
			rutina.setNombreRutina(nuevaRutina.getNombreRutina());
			rutina.setSemanas(nuevaRutina.getSemanas());
			cliente.setSemanas(nuevaRutina.getSemanas());
			rutina.setUpdated(new Date());
			cliente.setDiaInicio(new Date());
			rutina.setCliente(cliente);
			cliente.setRutina(rutina);
			List<RutinaEjercicio> rutinaEjercicios=new ArrayList<RutinaEjercicio>();
			for(RutinaDTO r: listaEjercicios){
				RutinaEjercicio ejercicio=new RutinaEjercicio();
				String[] split=r.getId_ejercicio().split(",");
				ejercicio.setDia(Integer.parseInt(split[1]));
				Ejercicio ej=ejercicioService.getOne(Integer.parseInt(split[0]));
				ejercicio.setEjercicio(ej);
				ejercicio.setRutina(rutina);
				rutinaEjercicios.add(ejercicio);
				ej.setEjercicios(rutinaEjercicios);
			}
			rutina.setEjercicios(rutinaEjercicios);
		}else {
			java.sql.Date aux=cliente.obtenerDiaInicio();
			Date ultimaActualizacion=new Date(aux.getTime());
			ultimaActualizacion.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			LocalDateTime fechaRutina=ultimaActualizacion.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			fechaRutina=fechaRutina.plusWeeks(cliente.obtenerSemanas());
			
			LocalDateTime today=LocalDateTime.now();
			if(today.isAfter(fechaRutina) || !rutina.rutinaAsignada()) {
				rutina.setActivo(false);
				rutina.setNombreObjetivo(nuevaRutina.getNombreObjetivo());
				rutina.setNombreRutina(nuevaRutina.getNombreRutina());
				cliente.setSemanas(nuevaRutina.getSemanas());
				rutina.setSemanas(nuevaRutina.getSemanas());
				rutina.setUpdated(new Date());
				cliente.setDiaInicio(new Date());
				List<RutinaEjercicio> rutinaAnterior=rutina.getEjercicios();
				for(RutinaEjercicio rutinaEjercicio: rutinaAnterior) {
					rutinaEjercicioService.delete(rutinaEjercicio);
				}
				rutinaAnterior.clear();
				for(RutinaDTO ejercicioNuevo: listaEjercicios){
					RutinaEjercicio rutinaEjercicio=new RutinaEjercicio();
					String[] split=ejercicioNuevo.getId_ejercicio().split(",");
					rutinaEjercicio.setDia(Integer.parseInt(split[1]));
					Ejercicio ejercicio=ejercicioService.getOne(Integer.parseInt(split[0]));
					
					rutinaEjercicio.setEjercicio(ejercicio);
					rutinaEjercicio.setRutina(rutina);
					
					rutinaAnterior.add(rutinaEjercicio);
				}
			}else {
				jsonRespuesta.put("respuesta", "el usuario "+idCliente+" ya tiene asignada una rutina");
				return new ResponseEntity<>(jsonRespuesta.toString(), HttpStatus.CONFLICT);
			}
		}
		return new ResponseEntity<>(rutina, HttpStatus.OK);
	}
	
	@PostMapping("/crearPlantillaRutina")
	 @ResponseBody
	@Transactional(rollbackFor = SQLException.class)
	public ResponseEntity<?> crearPlantillaRutina(@RequestBody ArrayPrueba body){
		JSONObject respuesta=new JSONObject();
		try {
			ArrayPrueba2 nuevaRutina=body.getBody().get(0);
			ArrayList<RutinaDTO>listaEjercicios=nuevaRutina.getRutina();
			
			Rutina rutina=new Rutina();
			rutina=new Rutina();
			rutina.setActivo(true);
			rutina.setCreated(new Date());
			rutina.setNombreObjetivo(nuevaRutina.getNombreObjetivo());
			rutina.setNombreRutina(nuevaRutina.getNombreRutina());
			rutina.setUpdated(new Date());
			rutina.setPlantilla(true);
			rutina.setTipoPlantilla(nuevaRutina.getTipoPlantilla());
			rutina.setSemanas(nuevaRutina.getSemanas());
			List<RutinaEjercicio> rutinaEjercicios=new ArrayList<RutinaEjercicio>();
			for(RutinaDTO r: listaEjercicios){
				RutinaEjercicio ejercicio=new RutinaEjercicio();
				String[] split=r.getId_ejercicio().split(",");
				ejercicio.setDia(Integer.parseInt(split[1]));
				Ejercicio ej=ejercicioService.getOne(Integer.parseInt(split[0]));
				ejercicio.setEjercicio(ej);
				ejercicio.setRutina(rutina);
				rutinaEjercicios.add(ejercicio);
				ej.setEjercicios(rutinaEjercicios);
			}
			rutina.setEjercicios(rutinaEjercicios);
			rutinaService.save(rutina);
			respuesta.put("respuesta", "Plantilla almacenada exitosamente");
			return new ResponseEntity<>(respuesta.toString(), HttpStatus.OK);
		}catch(Exception e) {
			
		}
		respuesta.put("respuesta", "No se pudo almacenar la plantilla nueva");
		return new ResponseEntity<>(respuesta.toString(), HttpStatus.CONFLICT);
		
	}
	

	@GetMapping("/obtenerRutina/{idCliente}")
	public ResponseEntity<?> obtenerRutina(@PathVariable("idCliente") int idCliente)
	{
		JSONObject json=new JSONObject();
		Cliente cliente=clienteService.findById(idCliente);
		try {
			Rutina rutina=cliente.obtenerRutina();
			if(rutina==null) {
				return new ResponseEntity<String>("", HttpStatus.NOT_FOUND);
			}
			VistaRutinaCliente vista=new VistaRutinaCliente();
			vista.setDiaFinal(cliente.obtenerDiaFinal());
			vista.setDiaInicio(cliente.obtenerDiaInicio());
			vista.setNombreObjetivo(rutina.getNombreObjetivo());
			vista.setNombreRutina(rutina.getNombreRutina());
			vista.setSemanas(cliente.obtenerSemanas());
			vista.setEjercicios(rutina.getEjercicios());
			return ResponseEntity.ok(vista);
		}catch(Exception e) {			
			json.put("respuesta", "id incorrecto");
			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
		}
		
	}
	
	@GetMapping("/obtenerPlantilla/{idPlantilla}")
	public ResponseEntity<?> obtenerPlantilla(@PathVariable("idPlantilla") int idPlantilla)
	{
		JSONObject json=new JSONObject();
		try {
			Rutina rutina=rutinaService.getOne(idPlantilla).get();
			if(rutina.esPlantilla()) {
				VistaPlantilla vista=new VistaPlantilla();
				vista.setIdPlantilla(idPlantilla);
				vista.setNombreObjetivo(rutina.getNombreObjetivo());
				vista.setNombreRutina(rutina.getNombreRutina());
				vista.setSemanas(rutina.getSemanas());
				vista.setEjercicios(rutina.getEjercicios());
				vista.setTipoPlantilla(rutina.getTipoPlantilla());
				vista.setComentarios(rutina.getComentarios());
				return ResponseEntity.ok(vista);
			}else {
				json.put("respuesta", "el id no corresponde con el de una plantilla");
				return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
			}
				
		}catch(Exception e) {			
			json.put("respuesta", "id incorrecto");
			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
		}
		
	}
	@GetMapping("/obtenerPlantillasGenerales")
	public ResponseEntity<?> obtenerPlantillasGenerales()
	{
		JSONObject json=new JSONObject();
		try {
			Session currentSession = entityManager.unwrap(Session.class);
	        Query<Rutina> listaClases;
			listaClases = currentSession.createNativeQuery("select * from rutina  where plantilla is true and tipo_plantilla='General';",Rutina.class);
			List<Rutina> lista= listaClases.getResultList();
			List<VistaPlantilla> listaVista=new ArrayList<VistaPlantilla>();
			for(Rutina item: lista) {
				VistaPlantilla vista=new VistaPlantilla();
				vista.setIdPlantilla(item.obtenerId());
				vista.setNombreObjetivo(item.getNombreObjetivo());
				vista.setNombreRutina(item.getNombreRutina());
				vista.setSemanas(item.getSemanas());
				vista.setEjercicios(item.getEjercicios());
				vista.setTipoPlantilla(item.getTipoPlantilla());
				listaVista.add(vista);
			}
			return ResponseEntity.ok(listaVista);
			
				
			
				
		}catch(Exception e) {			
			json.put("respuesta", "Ocurrio un error obteniendo las plantillas ");
			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
		}
		
	}
	/*@GetMapping("/obtenerPlantillasGenerales2")
	public ResponseEntity<?> obtenerPlantillasGenerales2()
	{
		JSONObject json=new JSONObject();
		try {
			Session currentSession = entityManager.unwrap(Session.class);
	        Query<Rutina> listaClases;
			listaClases = currentSession.createNativeQuery("select * from rutina  where plantilla is true and tipo_plantilla='General';",Rutina.class);
			List<Rutina> lista= listaClases.getResultList();
			List<RutinaSinEjerciciosApp> listaVista=new ArrayList<RutinaSinEjerciciosApp>();
			for(Rutina item: lista) {
				RutinaSinEjerciciosApp aux=new RutinaSinEjerciciosApp();
				aux.setIdPlantilla(item.obtenerId());
				aux.setNombreObjetivo(item.getNombreObjetivo());
				aux.setNombreRutina(item.getNombreRutina());
				aux.setSemanas(item.getSemanas());
				listaVista.add(aux);
			}
			return ResponseEntity.ok(listaVista);
			
				
			
				
		}catch(Exception e) {			
			json.put("respuesta", "Ocurrio un error obteniendo las plantillas ");
			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
		}
		
	}*/
	
	@GetMapping("/obtenerRutinaApp/{idCliente}")
	public ResponseEntity<?> obtenerRutinaApp(@PathVariable("idCliente") int idCliente)
	{
		JSONObject json=new JSONObject();
		try {
			Cliente cliente=clienteService.findById(idCliente);
			Rutina rutina=cliente.obtenerRutina();
			if(rutina==null) {
				return new ResponseEntity<String>("", HttpStatus.NOT_FOUND);
			}
			RutinaSinEjercicios aux=new RutinaSinEjercicios();
			aux.setIdCliente(rutina.getIdCliente(idCliente));
			aux.setNombreObjetivo(rutina.getNombreObjetivo());
			aux.setNombreRutina(rutina.getNombreRutina());
			aux.setSemanas(cliente.obtenerSemanas());
			aux.setDiaInicio(cliente.obtenerDiaInicio());
			aux.setDiaFinal(cliente.obtenerDiaFinal());
			return ResponseEntity.ok(aux);
		}catch(Exception e) {			
			json.put("respuesta", "id incorrecto");
			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
		}
		
	}
	@GetMapping("/obtenerRutinaEjerciciosApp/{idCliente}")
	public ResponseEntity<?> obtenerRutinaEjerciciosApp(@PathVariable("idCliente") int idCliente)
	{
		JSONObject json=new JSONObject();
		try {
			Rutina rutina=clienteService.findById(idCliente).obtenerRutina();
			if(rutina==null) {
				return new ResponseEntity<String>("", HttpStatus.NOT_FOUND);
			}
			return ResponseEntity.ok(rutina.getEjercicios());
		}catch(Exception e) {			
			json.put("respuesta", "id incorrecto");
			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
		}
		
	}
	
	@PostMapping("/asignarRutina")
	@Transactional(rollbackFor = SQLException.class)
	public ResponseEntity<?> asignarRutina(@RequestBody ArrayPrueba body){
		ArrayPrueba2 nuevaRutina=body.getBody().get(0);
		ArrayList<RutinaDTO>listaEjercicios=nuevaRutina.getRutina();
		JSONObject json=new JSONObject();
		int idCliente=nuevaRutina.getIdCliente();
		Cliente cliente=clienteService.findById(idCliente);
		try {
			Rutina rutina=cliente.obtenerRutina();
			if(rutina.rutinaAsignada()) {
				throw new RuntimeException("ya tenia una  rutina asignada");
			}
			rutina.setActivo(true);
			rutina.setNombreObjetivo(nuevaRutina.getNombreObjetivo());
			rutina.setNombreRutina(nuevaRutina.getNombreRutina());
			rutina.setSemanas(nuevaRutina.getSemanas());
			cliente.setSemanas(nuevaRutina.getSemanas());
			rutina.setUpdated(new Date());
			cliente.setDiaInicio(new Date());
			List<RutinaEjercicio> rutinaAnterior=rutina.getEjercicios();
			for(RutinaEjercicio rutinaEjercicio: rutinaAnterior) {
				rutinaEjercicioService.delete(rutinaEjercicio);
			}
			rutinaAnterior.clear();
			for(RutinaDTO ejercicioNuevo: listaEjercicios){
				RutinaEjercicio rutinaEjercicio=new RutinaEjercicio();
				String[] split=ejercicioNuevo.getId_ejercicio().split(",");
				rutinaEjercicio.setDia(Integer.parseInt(split[1]));
				Ejercicio ejercicio=ejercicioService.getOne(Integer.parseInt(split[0]));
				rutinaEjercicio.setOrden(ejercicioNuevo.getOrden());
				rutinaEjercicio.setRepeticiones(ejercicioNuevo.getRepeticiones());
				rutinaEjercicio.setSeries(ejercicioNuevo.getSeries());
				rutinaEjercicio.setEjercicio(ejercicio);
				rutinaEjercicio.setRutina(rutina);
				
				rutinaAnterior.add(rutinaEjercicio);
			}
			//rutina.setEjercicios(rutinaActualizada.getEjercicios());
			this.enviarCorreo(idCliente);
			json.put("respuesta", "Rutina cargada exitosamente al cliente "+idCliente);
			return new ResponseEntity<String>(json.toString(), HttpStatus.OK); 
		}catch(RuntimeException e) {		
        	TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			json.put("respuesta", "El usuario ya tiene una rutina asignada");
			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
		}catch(Exception e) {		
			e.printStackTrace();
        	TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			json.put("respuesta", "id incorrecto");
			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
		}
		
	}
	
	@PostMapping("/actualizarPlantilla")
	@Transactional(rollbackFor = SQLException.class)
	public ResponseEntity<?> actualizarPlantilla(@RequestBody VistaPlantilla2 nuevaRutina){
		List<EjercicioDTO>listaEjercicios=nuevaRutina.getEjercicios();
		JSONObject json=new JSONObject();
		Rutina rutina=null;
		try {
			rutina=rutinaService.getOne(nuevaRutina.getIdPlantilla()).get();
			rutina.setActivo(true);
			rutina.setPlantilla(true);
			rutina.setNombreObjetivo(nuevaRutina.getNombreObjetivo());
			rutina.setNombreRutina(nuevaRutina.getNombreRutina());
			rutina.setSemanas(nuevaRutina.getSemanas());
			rutina.setComentarios(nuevaRutina.getComentarios());
			rutina.setUpdated(new Date());
			List<RutinaEjercicio> rutinaAnterior=rutina.getEjercicios();
			for(RutinaEjercicio rutinaEjercicio: rutinaAnterior) {
				rutinaEjercicioService.delete(rutinaEjercicio);
			}
			rutinaAnterior.clear();
			for(EjercicioDTO ejercicioNuevo: listaEjercicios){
				RutinaEjercicio rutinaEjercicio=new RutinaEjercicio();
				String[] split=ejercicioNuevo.getId_ejercicio().split(",");
				rutinaEjercicio.setDia(Integer.parseInt(split[1]));
				Ejercicio ejercicio=ejercicioService.getOne(Integer.parseInt(split[0]));
				rutinaEjercicio.setOrden(ejercicioNuevo.getOrden());
				rutinaEjercicio.setRepeticiones(ejercicioNuevo.getRepeticiones());
				rutinaEjercicio.setSeries(ejercicioNuevo.getSeries());
				rutinaEjercicio.setEjercicio(ejercicio);
				rutinaEjercicio.setRutina(rutina);
				
				rutinaAnterior.add(rutinaEjercicio);
			}
			//rutina.setEjercicios(rutinaActualizada.getEjercicios());
			json.put("respuesta", "Rutina actualizada exitosamente");
			return new ResponseEntity<String>(json.toString(), HttpStatus.OK); 
		}catch(IndexOutOfBoundsException e) {		
        	TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			json.put("respuesta", "ids de ejercicios no existen");
			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
		}catch(RuntimeException e) {
        	TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			json.put("respuesta", "Error durante la actualizacion");
			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
		}catch(Exception e) {		
			e.printStackTrace();
        	TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			json.put("respuesta", "id incorrecto");
			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
		}
		
	}

	//rutina.setCliente(cliente);
	//cliente.setRutina(rutina);
	@PostMapping("/asignarRutinaPlantilla")
	@Transactional(rollbackFor = SQLException.class)
	public ResponseEntity<?> asignarRutinaPlantilla(@RequestBody Body body){
		JSONObject json=new JSONObject();
		int idCliente=body.getUsuario();
		int idRutina=body.getIdRutina();
		try {
			Cliente cliente=clienteService.findById(idCliente);
			Rutina rutinaCliente=cliente.obtenerRutina();
			Rutina rutina=rutinaService.getOne(idRutina).get();
			
			if(rutinaCliente!=null) {
				SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd"); 
	       		Date fecha = formato.parse(cliente.obtenerDiaFinal());
	       		Date today =new Date(); 
				if(fecha.before(today)) {
					this.eliminarRutina(idCliente);
					rutina.setCliente(cliente);
					cliente.setRutina(rutina);
					cliente.setSemanas(rutina.getSemanas());
					cliente.setDiaInicio(new Date());
					this.enviarCorreo(idCliente);
					json.put("respuesta", "Rutina cargada exitosamente al cliente "+idCliente);
					return new ResponseEntity<String>(json.toString(), HttpStatus.OK); 
				}else {
					throw new RuntimeException("ya tenia una  rutina asignada");
				}
				
			}else {
				rutina.setCliente(cliente);
				cliente.setRutina(rutina);
				cliente.setSemanas(rutina.getSemanas());
				cliente.setDiaInicio(new Date()); 
				this.enviarCorreo(idCliente);
				json.put("respuesta", "Rutina cargada exitosamente al cliente "+idCliente);
				return new ResponseEntity<String>(json.toString(), HttpStatus.OK); 
				
			} 
		}catch(RuntimeException e) {	
			e.printStackTrace();	
        	TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			json.put("respuesta", "El usuario ya tiene una rutina asignada");
			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
		}catch(Exception e) {		
			e.printStackTrace();
        	TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			json.put("respuesta", "id incorrecto");
			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
		}
		
	}
	@GetMapping("/deleteRutina/{idCliente}")
	@Transactional(rollbackFor = SQLException.class)
	public ResponseEntity<?> eliminarRutina(@PathVariable("idCliente") int idCliente){
		JSONObject json=new JSONObject();
		Cliente cliente=clienteService.findById(idCliente);
		try {
			Rutina rutina=cliente.obtenerRutina();
			if(rutina.esPlantilla()) {
				List<Cliente>clientes=rutina.obtenerClientes();
				clientes.remove(cliente);
				rutina.colocarCliente(clientes);
				cliente.setRutina(null);
				
			}else {
				List<RutinaEjercicio> rutinaAnterior=rutina.getEjercicios();
				for(RutinaEjercicio rutinaEjercicio: rutinaAnterior) {
					rutinaEjercicioService.delete(rutinaEjercicio);
				}
				rutinaAnterior.clear();
				clienteService.findById(idCliente).setRutina(null);
				rutinaService.delete(rutina);
			}
			
			json.put("respuesta", "Rutina borrada exitosamente al cliente "+idCliente);
			return new ResponseEntity<String>(json.toString(), HttpStatus.OK); 
		}catch(RuntimeException e) {		
        	TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			json.put("respuesta", "El usuario no tiene una rutina asignada");
			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
		}catch(Exception e) {		
			e.printStackTrace();
        	TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			json.put("respuesta", "id incorrecto");
			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
		}
		
	}
	@GetMapping("/deletePlantilla/{idRutina}")
	@Transactional(rollbackFor = SQLException.class)
	public ResponseEntity<?> deletePlantilla(@PathVariable("idRutina") int idRutina){
		JSONObject json=new JSONObject();
		try {
			Rutina rutina=rutinaService.getOne(idRutina).get();
			if(rutina.clientesAsignados()>0) {
				throw new IOException("No se puede eliminar la plantilla porque un usuario tiene asignada la rutina");
			}
			List<RutinaEjercicio> rutinaAnterior=rutina.getEjercicios();
			for(RutinaEjercicio rutinaEjercicio: rutinaAnterior) {
				rutinaEjercicioService.delete(rutinaEjercicio);
			}
			rutinaAnterior.clear();
			rutinaService.delete(rutina);
			json.put("respuesta", "Plantilla Eliminada exitosamente ");
			return new ResponseEntity<String>(json.toString(), HttpStatus.OK); 
		}catch(RuntimeException e) {		
        	TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			json.put("respuesta", "El usuario no tiene una rutina asignada");
			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
		}catch(IOException e) {		
        	TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			json.put("respuesta", "No se puede eliminar la plantilla porque un usuario tiene asignada la rutina");
			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
		}catch(Exception e) {		
			e.printStackTrace();
        	TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			json.put("respuesta", "id incorrecto");
			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
		}
		
	}

	
	//--------------------------------------- WEB SERVICE AGENDA----------------------------------------------------
	@RequestMapping(value="crearHorarioAgenda", method=RequestMethod.POST)
   	public ResponseEntity<?> crearHorarioAgenda(@RequestBody HorarioDTO miHorario)
   	{
    	   //SELECT id FROM ca_sala ORDER BY RANDOM() limit 1
    	   
   		AgendaHorario nuevoHorario=new AgendaHorario();
   		nuevoHorario.setActivo(true);
   		nuevoHorario.setCreated(new Date());
   		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
   		String username;
   		if (principal instanceof UserDetails) {
   			username = ((UserDetails)principal).getUsername();
   		} else {
   		  	username = principal.toString();
   		}
   		nuevoHorario.setCreatedBy(username);
   		nuevoHorario.setDomingo(miHorario.isDomingo());
   		nuevoHorario.setHora(miHorario.getHora());
   		nuevoHorario.setJueves(miHorario.isJueves());
   		nuevoHorario.setLunes(miHorario.isLunes());
   		nuevoHorario.setMartes(miHorario.isMartes());
   		nuevoHorario.setMiercoles(miHorario.isMiercoles());
   		nuevoHorario.setPeriodoFinal(miHorario.getPeriodoFinal());
   		nuevoHorario.setPeriodoInicio(miHorario.getPeriodoInicio());
   		nuevoHorario.setSabado(miHorario.isSabado());
   		nuevoHorario.setRango(miHorario.getRango());
   		nuevoHorario.setViernes(miHorario.isViernes());
   		nuevoHorario.setClub(miHorario.getClub());
   		nuevoHorario.setCupoMaximo(miHorario.getCupoMaximo());
   		
   		nuevoHorario.setUpdated(new Date());
   		nuevoHorario.setUpdatedBy(username);
   		nuevoHorario = agendaHorarioService.save(nuevoHorario);
   		SimpleDateFormat print = new SimpleDateFormat("yyyy-MM-dd"); 
   		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date startDate = new Date(),endDate = new Date();
		try {
			startDate = formatter.parse(nuevoHorario.getPeriodoInicio());
				endDate = formatter.parse(nuevoHorario.getPeriodoFinal());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			Calendar start = Calendar.getInstance();
			start.setTime(startDate);
			Calendar end = Calendar.getInstance();
			end.setTime(endDate);

			for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
		        Calendar c = Calendar.getInstance();
				c.setTime(date);
		        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
					if(nuevoHorario.isLunes() && dayOfWeek==2) {
						AgendaReservas apartado=new AgendaReservas();
		   	   			apartado.setActivo(true);
		   				apartado.setConteo( 0);
		   				apartado.setDia(print.format(date));
		   				apartado.setCreated(new Date());
		   				apartado.setCreatedBy("admin");
		   				apartado.setHorario(nuevoHorario);
		   				apartado.setUpdated(new Date());
		   				apartado.setUpdatedBy("admin");
		   				agendaReservasService.save(apartado);
					}if(nuevoHorario.isMartes() && dayOfWeek==3) {
						AgendaReservas apartado=new AgendaReservas();
		   	   			apartado.setActivo(true);
		   				apartado.setConteo(0);
		   				apartado.setDia(print.format(date));
		   				apartado.setCreated(new Date());
		   				apartado.setCreatedBy("admin");
		   				apartado.setHorario(nuevoHorario);
		   				apartado.setUpdated(new Date());
		   				apartado.setUpdatedBy("admin");
		   				agendaReservasService.save(apartado);
					}if(nuevoHorario.isMiercoles() && dayOfWeek==4) {
						AgendaReservas apartado=new AgendaReservas();
		   	   			apartado.setActivo(true);
		   				apartado.setConteo(0);
		   				apartado.setDia(print.format(date));
		   				apartado.setCreated(new Date());
		   				apartado.setCreatedBy("admin");
		   				apartado.setHorario(nuevoHorario);
		   				apartado.setUpdated(new Date());
		   				apartado.setUpdatedBy("admin");
		   				agendaReservasService.save(apartado);
					}if(nuevoHorario.isJueves() && dayOfWeek==5) {
						AgendaReservas apartado=new AgendaReservas();
		   	   			apartado.setActivo(true);
		   				apartado.setConteo(0);
		   				apartado.setDia(print.format(date));
		   				apartado.setCreated(new Date());
		   				apartado.setCreatedBy("admin");
		   				apartado.setHorario(nuevoHorario);
		   				apartado.setUpdated(new Date());
		   				apartado.setUpdatedBy("admin");
		   				agendaReservasService.save(apartado);
					}if(nuevoHorario.isViernes() && dayOfWeek==6) {
						AgendaReservas apartado=new AgendaReservas();
		   	   			apartado.setActivo(true);
		   				apartado.setConteo(0);
		   				apartado.setDia(print.format(date));
		   				apartado.setCreated(new Date());
		   				apartado.setCreatedBy("admin");
		   				apartado.setHorario(nuevoHorario);
		   				apartado.setUpdated(new Date());
		   				apartado.setUpdatedBy("admin");
		   				agendaReservasService.save(apartado);
					}if(nuevoHorario.isSabado() && dayOfWeek==7) {
						AgendaReservas apartado=new AgendaReservas();
		   	   			apartado.setActivo(true);
		   				apartado.setConteo(0);
		   				apartado.setDia(print.format(date));
		   				apartado.setCreated(new Date());
		   				apartado.setCreatedBy("admin");
		   				apartado.setHorario(nuevoHorario);
		   				apartado.setUpdated(new Date());
		   				apartado.setUpdatedBy("admin");
		   				agendaReservasService.save(apartado);
					}if(nuevoHorario.isDomingo() && dayOfWeek==1) {
						AgendaReservas apartado=new AgendaReservas();
		   	   			apartado.setActivo(true);
		   				apartado.setConteo(0);
		   				apartado.setDia(print.format(date));
		   				apartado.setCreated(new Date());
		   				apartado.setCreatedBy("admin");
		   				apartado.setHorario(nuevoHorario);
		   				apartado.setUpdated(new Date());
		   				apartado.setUpdatedBy("admin");
		   				agendaReservasService.save(apartado);
					}
			}	
   		return new ResponseEntity<>("Horario creado Correctamente", HttpStatus.OK);
   	}
	
	@RequestMapping(value="crearReserva", method=RequestMethod.POST)
   	@Transactional(rollbackFor = SQLException.class)
	public ResponseEntity<String> crearReserva(@RequestBody Body body) {

		JSONObject json =new JSONObject();
		AgendaReservas apartado=agendaReservasService.getOne(body.getId());	
		apartado.setAsesor(body.getAsesor());
		AgendaHorario horario1=apartado.getHorario();        
   		AgendaReservasUsuario apartadosUsuario=new AgendaReservasUsuario();
		Cliente cliente=clienteService.findById(body.getUsuario());
		
		try {
			if(cliente.getEstatusCobranza().getIdEstatusCobranza()!=1) {
				throw new Exception("El usuario no esta activo y al corriente");
			}
			 Session currentSession = entityManager.unwrap(Session.class);
   	        Query<?>clasesDia = currentSession.createNativeQuery("select count(*) from agenda_reservas_usuario join agenda_reservas on "
   	        		+ "agenda_reservas.id_apartados=agenda_reservas_usuario.id_reservas join agenda_horario on "
   	        		+ "agenda_horario.id=agenda_reservas.id_horario_agenda where idcliente="+body.getUsuario()+" and "
   	        		+ "agenda_reservas_usuario.activo is true and "
   	        		+ "current_timestamp<TO_TIMESTAMP(agenda_reservas.dia||split_part(agenda_horario.rango, '-', 2),'YYYY-MM-DDHH24:MI');");
   	        BigInteger cantidad=(BigInteger)clasesDia.getSingleResult();
   	        try {
   	        	if(cantidad.intValue()>0 && cliente.obtenerRutina()==null) {
   	        		throw new ParseException("El cliente ya cuenta con una reserva para este día", 0);
   	        	}else if(cliente.obtenerRutina()!=null ){
   	        		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd"); 
   	        		Date fecha = formato.parse(cliente.obtenerDiaFinal());
   	        		Date diaSeleccionado = formato.parse(apartado.getDia());
   	        		if(fecha.after(diaSeleccionado)) {
   	        			throw new Exception("No se puede agendar cita si el usuario no ha completado su rutina");
   	        		}
   	        		
   	        		
   	        	}
   	        }catch(ParseException e) {
   	        	json.put("respuesta", "El cliente ya cuenta con una reserva para la fecha "+apartado.getDia());
   				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	
   				return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT);
   	        }catch(Exception e) {
   	        	json.put("respuesta", "No se puede agendar cita si el usuario no ha completado su rutina");
   				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	
   				return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT);
   	        }
			agendaReservasUsuarioService.crearReservacion(body, horario1, apartado, apartadosUsuario);
			json.put("respuesta", "Se agendo la cita correctamente");
			return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
			
		} catch (IOException  e) {
			json.put("respuesta", "Este usuario ya esta agendado");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
		} catch (RuntimeException  e) {
			json.put("respuesta", "No hay cupo disponible");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			
		
			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
		} catch (Exception e) {
			json.put("respuesta", "El usuario no esta activo y al corriente");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();			
		
			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
		} 
   		
	}
	
	@RequestMapping(value="cancelarReserva", method=RequestMethod.POST)
   	@Transactional(rollbackFor = SQLException.class)
	public ResponseEntity<String> cancelarReserva(@RequestBody Body body) {
		
		JSONObject json =new JSONObject();
		AgendaReservas apartado=agendaReservasService.getOne(body.getId());
		AgendaReservasUsuario reservasUsuario=agendaReservasUsuarioService.getOne(body.getUsuario(), body.getId());
		try {
			Cliente cliente=clienteService.findById(body.getUsuario());
			if(cliente==null) {
   				throw new FileNotFoundException("Este Cliente no tiene apartados ");
			}
			apartado.setConteo(apartado.getConteo()-1);
   			if(-1==apartado.getConteo()) {
   				throw new IOException("Sala Vacía");
   			}
   			boolean ban=agendaReservasUsuarioService.delete(cliente,apartado);
   			reservasUsuario.setCliente(null);
   			reservasUsuario.setReservas(null);
   			agendaReservasUsuarioService.save(reservasUsuario);
   			if(ban==false)
   				throw new FileNotFoundException("Error cancelando la clase ");
   			json.put("respuesta", "Se cancelo la reservacion Correctamente");
   			
   			return new ResponseEntity<String>(json.toString(), HttpStatus.OK); 
   		} catch (FileNotFoundException  e) {
   			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
   			
   			json.put("respuesta", "No ha agendado");
   			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
   		}catch(IOException e) {
   			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
   			json.put("respuesta", "Sala Vacía");
   			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
   		} 
	}

	//---------------------------------------------------------SERVICIOS MASA CORPORAL------------------------------------
	
	@RequestMapping(value="datosBascula", method=RequestMethod.POST)
	public ResponseEntity<String> datosBascula(@RequestBody ClienteBascula body) {
		
		JSONObject json =new JSONObject();
		try {
			body.fechaCaptura=new Date();
			clienteBasculaService.save(body);
			Cliente cliente=this.obtenerCliente(body.idUsuario);
			Correo correo=new Correo(usuarioCorreo,contrasenaCorreo,cliente.getEmail(),copiaOculta);
			String asunto="Analisis Corporal";
			LocalDateTime fechaActual= LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd 'de' MMMM 'de' yyyy");
			String formattedLocalDate = fechaActual.format(formatter);
			formattedLocalDate=formattedLocalDate.toUpperCase();
			correo.enviar_pesaje(body.agua, body.masaOsea, body.adiposidad, body.masaMagra, body.masaGrasa, body.peso, body.caloriasDiarias, body.tMB, body.edadMetabolica, body.iMC, formattedLocalDate, body.idUsuario, cliente.getNombre(), Base64.getEncoder().encodeToString(cliente.getURLFoto().getImagen()), cliente.getClub().getNombre(), asunto);
   			json.put("respuesta", "Se almaceno la informacion del cliente correctamente");
   			
   			return new ResponseEntity<String>(json.toString(), HttpStatus.OK); 
   		} catch (Exception  e) {
   			
   			json.put("respuesta", "Ocurrio un error, no se ha podido guardar la informacion");
   			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
   		}
	}

	@RequestMapping(value="ultimoPesaje", method=RequestMethod.POST)
	@CrossOrigin(origins = "*")
	public ResponseEntity<?> ultimoPesaje(@RequestBody ClienteBascula body) {
		
		try {
			ClienteBascula ultimoPesaje=clienteBasculaService.getUltimoPesaje(body.idUsuario);
			ClienteBasculaVista upv=new ClienteBasculaVista();
			Cliente cliente=this.obtenerCliente(body.idUsuario);
			long nacimiento=new Date().getTime()-cliente.getFechaNacimiento().getTime();
			int yearsOld=(int) (nacimiento/(365*24 * 60 * 60 * 1000L));
			upv.adiposidad=ultimoPesaje.adiposidad;
			upv.agua=ultimoPesaje.agua;
			upv.altura=ultimoPesaje.altura;
			upv.atleta=ultimoPesaje.atleta;
			upv.caloriasDiarias=ultimoPesaje.caloriasDiarias;
			upv.edadMetabolica=ultimoPesaje.edadMetabolica;
			upv.edadUsuario=yearsOld;

			SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd HH:mm");
			String fechaTexto = formatter.format(ultimoPesaje.fechaCaptura);
			upv.fechaCaptura=fechaTexto;
			upv.foto=cliente.getURLFoto().getImagen();
			upv.id=ultimoPesaje.id;
			upv.idTerminal=ultimoPesaje.idTerminal;
			upv.idUsuario=ultimoPesaje.idUsuario;
			upv.iMC=ultimoPesaje.iMC;
			upv.masaGrasa=ultimoPesaje.masaGrasa;
			upv.masaMagra=ultimoPesaje.masaMagra;
			upv.masaOsea=ultimoPesaje.masaOsea;
			upv.nivelActividad=ultimoPesaje.nivelActividad;
			upv.nombre=cliente.getNombre()+" "+cliente.getApellidoPaterno()+" "+cliente.getApellidoMaterno();
			upv.peso=ultimoPesaje.peso;
			upv.sexo=ultimoPesaje.sexo;
			upv.tMB=ultimoPesaje.tMB;
			upv.valoracionFisica=ultimoPesaje.valoracionFisica;
			
			clases.ClienteBasculaVista pesaje=new clases.ClienteBasculaVista();
			pesaje.adiposidad=upv.adiposidad;
			pesaje.agua=upv.agua;
			pesaje.altura=upv.altura;
			pesaje.atleta=upv.atleta;
			pesaje.caloriasDiarias=upv.caloriasDiarias;
			pesaje.edadMetabolica=upv.edadMetabolica;
			pesaje.edadUsuario=upv.edadUsuario;

			pesaje.fechaCaptura=upv.fechaCaptura;
			pesaje.foto=upv.foto;
			pesaje.id=upv.id;
			pesaje.idTerminal=body.idTerminal;
			pesaje.idUsuario=upv.idUsuario;
			pesaje.iMC=upv.iMC;
			pesaje.masaGrasa=upv.masaGrasa;
			pesaje.masaMagra=upv.masaMagra;
			pesaje.masaOsea=upv.masaOsea;
			pesaje.nivelActividad=upv.nivelActividad;
			pesaje.nombre=upv.nombre;
			pesaje.peso=upv.peso;
			pesaje.sexo=upv.sexo;
			pesaje.tMB=upv.tMB;
			pesaje.valoracionFisica=upv.valoracionFisica;
			
            CrudApplication.cl.enviarPesaje(pesaje);
			return new ResponseEntity<>(upv, HttpStatus.OK);
			
		}catch(Exception e) {			
			ClienteBasculaVista upv = new ClienteBasculaVista();
			Cliente cliente=this.obtenerCliente(body.idUsuario);
			upv.idUsuario=body.idUsuario;
			upv.idTerminal=body.idTerminal;
			upv.sexo=cliente.getSexo();
			long nacimiento=new Date().getTime()-cliente.getFechaNacimiento().getTime();
			int yearsOld=(int) (nacimiento/(365*24 * 60 * 60 * 1000L));
			upv.edadUsuario=yearsOld;
			upv.foto=cliente.getURLFoto().getImagen();
			upv.nombre=cliente.getNombre()+" "+cliente.getApellidoPaterno()+" "+cliente.getApellidoMaterno();
            CrudApplication.cl.enviarPesaje(upv);
			return new ResponseEntity<>(upv, HttpStatus.CONFLICT); 
		}
	}

	@GetMapping("/ultimoPesaje/{idCliente}")
	@CrossOrigin(origins = "*")
	public ResponseEntity<?> ultimoPesaje(@PathVariable("idCliente") int idCliente)
	{
		try {
			ClienteBascula ultimoPesaje=clienteBasculaService.getUltimoPesaje(idCliente);
			ClienteBasculaVista upv=new ClienteBasculaVista();
			Cliente cliente=this.obtenerCliente(idCliente);
			long nacimiento=new Date().getTime()-cliente.getFechaNacimiento().getTime();
			int yearsOld=(int) (nacimiento/(365*24 * 60 * 60 * 1000L));
			upv.adiposidad=ultimoPesaje.adiposidad;
			upv.agua=ultimoPesaje.agua;
			upv.altura=ultimoPesaje.altura;
			upv.atleta=ultimoPesaje.atleta;
			upv.caloriasDiarias=ultimoPesaje.caloriasDiarias;
			upv.edadMetabolica=ultimoPesaje.edadMetabolica;
			upv.edadUsuario=yearsOld;

			SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd HH:mm");
			String fechaTexto = formatter.format(ultimoPesaje.fechaCaptura);
			upv.fechaCaptura=fechaTexto;
			upv.foto=cliente.getURLFoto().getImagen();
			upv.id=ultimoPesaje.id;
			upv.idTerminal=ultimoPesaje.idTerminal;
			upv.idUsuario=ultimoPesaje.idUsuario;
			upv.iMC=ultimoPesaje.iMC;
			upv.masaGrasa=ultimoPesaje.masaGrasa;
			upv.masaMagra=ultimoPesaje.masaMagra;
			upv.masaOsea=ultimoPesaje.masaOsea;
			upv.nivelActividad=ultimoPesaje.nivelActividad;
			upv.nombre=cliente.getNombre()+" "+cliente.getApellidoPaterno()+" "+cliente.getApellidoMaterno();
			upv.peso=ultimoPesaje.peso;
			upv.sexo=ultimoPesaje.sexo;
			upv.tMB=ultimoPesaje.tMB;
			upv.valoracionFisica=ultimoPesaje.valoracionFisica;
			return new ResponseEntity<>(upv, HttpStatus.OK);
			
		}catch(Exception e) {			
			ClienteBasculaVista upv = new ClienteBasculaVista();
			return new ResponseEntity<>(upv, HttpStatus.CONFLICT); 
		}
		
	}
	@GetMapping("/ultimoPesajeGeneral/{idCliente}")
	public ResponseEntity<?> ultimoPesajeGeneral(@PathVariable("idCliente") int idCliente)
	{
		try {
			ClienteBascula ultimoPesaje=clienteBasculaService.getUltimoPesaje(idCliente);

			Cliente cliente=clienteService.findById(idCliente);
			long nacimiento=new Date().getTime()-cliente.getFechaNacimiento().getTime();
			int yearsOld=(int) (nacimiento/(365*24 * 60 * 60 * 1000L));
			ClienteBasculaDTO aux=new ClienteBasculaDTO();
			aux.altura=ultimoPesaje.altura;
			aux.atleta=ultimoPesaje.atleta;
			aux.nivelActividad=ultimoPesaje.nivelActividad;
			aux.peso=ultimoPesaje.peso;
			aux.edad=yearsOld;
			return new ResponseEntity<>(aux, HttpStatus.OK);
			
		}catch(Exception e) {			
			ClienteBasculaDTO ultimoPesaje = new ClienteBasculaDTO();
			return new ResponseEntity<>(ultimoPesaje, HttpStatus.CONFLICT); 
		}
		
	}
	
	//--------------------------------------------Banda Services----------------------------------------------------------------
	
	
	
	
	@RequestMapping(value="registrarBanda", method=RequestMethod.POST)
	public ResponseEntity<String> registrarBanda(@RequestBody Banda body) {
		
		JSONObject json =new JSONObject();
		try {
			body.setFechaRegistro(new Date());
			bandaService.save(body);
			json.put("respuesta", "Se registro la banda correctamente");
   			
   			return new ResponseEntity<String>(json.toString(), HttpStatus.OK); 
   		} catch (Exception  e) {
   			
   			json.put("respuesta", "Ocurrio un error, no se ha podido guardar la informacion");
   			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
   		}
	}
	
	
	
	//---------------------------------------------------------Funciones----------------------------------------------------
	public boolean enviarCorreo(int idCliente){
		try {
			Cliente cliente=this.obtenerCliente(idCliente);
			Correo correo=new Correo(usuarioCorreo,contrasenaCorreo,cliente.getEmail(),copiaOculta);
			//Correo correo=new Correo(usuarioCorreo,contrasenaCorreo,"bloodgigametal@gmail.com",copiaOculta);
			Rutina rutina=cliente.obtenerRutina();
			List<RutinaEjercicio> ejercicios=rutina.getEjercicios();
			Collections.sort(ejercicios);
			String lista="";
			String lista2="";
			for(int i=0;i<ejercicios.size();i++) {
				lista=lista+"                <div class=\"card\">\r\n"
			    		+ "                    <div>\r\n"
			    		+ "                        <h5 align=\"center\"> dia: "+ejercicios.get(i).getDia()+" <h6>"+ejercicios.get(i).getMaquina()+"</h6></h5>\r\n"
			    		+ "                        <table>\r\n"
			    		+ "                            <tr>\r\n"
			    		+ "                                <td width=\"125\" style=\" border-right: 0\">\r\n"
			    		+ "                                    <img width=\"125\" height=\"125\" src=\"https://trainingh.mx/wp-content/uploads/ejercicios/"+ejercicios.get(i).getGrupoMuscular()+"/"+ejercicios.get(i).getRutaImagen()+".gif\"/>\r\n"
			    		+ "                                </td>\r\n"
			    		+ "                                <td style=\" border-right: 0\">\r\n"
			    		+ "                                    <h3 align=\"left\" style=\"color: gray;\"><i>"+ejercicios.get(i).getGrupoMuscular()+"</i></h3>\r\n"
			    		+ "                                    <h3 align=\"left\" style=\"color: brown;\">"+ejercicios.get(i).getNombre()+"</h3>\r\n"
			    		+ "                                    <table>\r\n"
			    		+ "                                        <tr >\r\n"
			    		+ "                                            <td style=\" border-right: 0\"><b>Series</b></td>\r\n"
			    		+ "                                            <td style=\" border-right: 0\"><b>Repeticiones</b> </td>\r\n"
			    		+ "                                        </tr>\r\n"
			    		+ "                                        <tr >\r\n"
			    		+ "                                            <td style=\" border-right: 0\"> "+ejercicios.get(i).getSeries()+"</td>\r\n"
			    		+ "                                            <td style=\" border-right: 0\"> "+ejercicios.get(i).getRepeticiones()+"</td>\r\n"
			    		+ "                                        </tr>\r\n"
			    		+ "                                    </table>\r\n"
			    		+ "                                </td>\r\n"
			    		+ "                            </tr>\r\n"
			    		+ "                        </table>\r\n"
			    		+ "                    </div>\r\n"
			    		+ "                </div>\r\n";
			}
			for(int i=0;i<ejercicios.size();i++) {
				lista2=lista2+"                <div class=\"card\">\r\n"
			    		+ "                    <div>\r\n"
			    		+ "                        <h5 align=\"center\">"+ejercicios.get(i).getOrden()+" <h6>"+ejercicios.get(i).getMaquina()+"</h6></h5>\r\n"
			    		+ "                        <table>\r\n"
			    		+ "                            <tr>\r\n"
			    		+ "                                <td  style=\" border-right: 0\">\r\n"
			    		+ "                                    <img width=\"125\" height=\"125\" src=\"https://trainingh.mx/wp-content/uploads/ejercicios/"+ejercicios.get(i).getGrupoMuscular()+"/"+ejercicios.get(i).getRutaImagen()+".gif\"/>\r\n"
			    		+ "                                </td>\r\n"
			    		+ "                                <td style=\" border-right: 0\">\r\n"
			    		+ "                                    <h3 align=\"center\" style=\"color: gray;\"><i>"+ejercicios.get(i).getGrupoMuscular()+"</i></h3>\r\n"
			    		+ "                                    <h3 align=\"center\" style=\"color: brown;\">"+ejercicios.get(i).getNombre()+"</h3>\r\n"
			    		+ "                                    <table>\r\n"
			    		+ "                                        <tr >\r\n"
			    		+ "                                            <td style=\" border-right: 0\"><b>Series</b></td>\r\n"
			    		+ "                                            <td style=\" border-right: 0\"><b>Repeticiones</b> </td>\r\n"
			    		+ "                                        </tr>\r\n"
			    		+ "                                        <tr >\r\n"
			    		+ "                                            <td style=\" border-right: 0\"> "+ejercicios.get(i).getSeries()+"</td>\r\n"
			    		+ "                                            <td style=\" border-right: 0\"> "+ejercicios.get(i).getRepeticiones()+"</td>\r\n"
			    		+ "                                        </tr>\r\n"
			    		+ "                                    </table>\r\n"
			    		+ "                                </td>\r\n"
			    		+ "                            </tr>\r\n"
			    		+ "                        </table>\r\n"
			    		+ "                    </div>\r\n"
			    		+ "                </div>\r\n";
			}
			String asunto="Rutina nueva";
			LocalDateTime fechaRutina= new Date(cliente.obtenerDiaInicio().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String formattedLocalDate = fechaRutina.format(formatter);
			correo.enviar_rutina(asunto, String.valueOf(cliente.getIdCliente()),
					Base64.getEncoder().encodeToString(cliente.getURLFoto().getImagen()), cliente.getNombre(),
					cliente.getClub().getNombre(), formattedLocalDate, cliente.obtenerDiaFinal(),rutina.getNombreObjetivo(),
					rutina.getNombreRutina(), lista,lista2,rutina.getComentarios());
			return true;
		}catch(Exception e) {
			return false;
		}
		
	}
	
	
	@GetMapping("/obtenerCliente/{horarioId}")
	@CrossOrigin(origins = "*")
	@ResponseBody
    public Cliente obtenerCliente(@PathVariable("horarioId") int horarioId){
        Cliente cliente = clienteService.findById(horarioId);
        if(cliente == null) {
            throw new RuntimeException("cliente id not found -"+horarioId);
        }
        //retornará al usuario con id pasado en la url
        if(cliente.getURLFoto()==null) {
        	BufferedImage bImage;
			try {
				bImage = ImageIO.read(new File("blank-profile.png"));
	            ByteArrayOutputStream bos = new ByteArrayOutputStream();
	            ImageIO.write(bImage, "png", bos );
	            byte [] data = bos.toByteArray();
	            cliente.setURLFoto(new Foto(data));
			} catch (IOException e) {
				e.printStackTrace();
			}
        }		       
        return cliente;
    }//Fin del metodo
	
	
	
	
}//fin de la clase
