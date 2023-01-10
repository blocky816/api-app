package com.tutorial.crud.controller;

import java.math.BigInteger;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Tuple;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tutorial.crud.dto.*;
import com.tutorial.crud.entity.*;
import com.tutorial.crud.service.*;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/retos")
@CrossOrigin(origins = "*")
public class RetosController {
	@Autowired
	private TipoService tipoService;	

	@Autowired
	private RetoService retoService;

	@Autowired
	private EntityManager entityManager;
	@Autowired
	private EntrenamientoUsuarioService entrenamientoUsuarioService;

	@Autowired
	private TipoRetoService tipoRetoService;

	@Autowired
	private RetoUsuarioService retoUsuarioService;

	@Autowired
	private ClienteService clienteService;

	@Autowired RetoAcumulableService retoAcumulableService;
	@Autowired RetoEstadisticaService retoEstadisticaService;
	//-------------------------------------- WEB SERVICE TIPO RETO------------------------------------------------------
		/**
		 * Metodo que muestra todos los Miembros almacenados en la base de datos
		 * @return lista de Miembro
		 */
		@GetMapping({"/obtenerTipo","/obtenerTipo{activo}"})
		public ResponseEntity<?> obtenerTipo(@RequestParam(required = false) String activo)
		{
			List<TipoReto> tipoRetos;
			if(activo!=null) {
				try {
					tipoRetos = tipoRetoService.getByActivo(Boolean.parseBoolean(activo));
				}catch(NoSuchElementException e) {
					return new ResponseEntity<>("No se encontraron resultados", HttpStatus.CONFLICT);
				}
			}else {
				tipoRetos = tipoRetoService.list();
			}
			List<TipoRetoDTO> tipoRetoDTOS = new ArrayList<TipoRetoDTO>();
			for(int i=0;i<tipoRetos.size();i++) {
				TipoRetoDTO tipoaux=new TipoRetoDTO();
				tipoaux.setNombre(tipoRetos.get(i).getNombre());
				tipoaux.setId(tipoRetos.get(i).getId());
				tipoaux.setDificultad(tipoRetos.get(i).getDificultad());
				tipoaux.setTime(tipoRetos.get(i).getTime());
				tipoaux.setMax(tipoRetos.get(i).getMax());
				tipoRetoDTOS.add(tipoaux);
			}
			return ResponseEntity.ok(tipoRetoDTOS);
		}
		
		/**
		 * Metodo que muestra solo un miembro
		 * @param ID es el id del miembro que se quiere mostrar, en caso de no encontrarlo genera un RuntimeException
		 * @return 
		 * @return El miembro con el id 
		 */
		@GetMapping({"/obtenerTR","/obtenerTR{nombre}"})
	    public ResponseEntity<?> getTipo(@RequestParam String nombre){
			Optional<Tipo> OpcionalTipo = tipoService.getByNombre(nombre);
			if(OpcionalTipo.isPresent())
			{
				Tipo tipo=OpcionalTipo.get();
				TipoDTO tipoDTO=new TipoDTO();
				tipoDTO.setNombre(tipo.getNombre());
				return ResponseEntity.ok(tipoDTO);
	        }
			return new ResponseEntity<>("No se encontraron resultados", HttpStatus.NOT_FOUND);
		
		}
		
		@PreAuthorize("hasRole('ADMIN')")
		@RequestMapping(value="crearTipo", method=RequestMethod.POST)
		public ResponseEntity<?> crearTipoReto(@RequestBody TipoReto tipoReto)
		{
			tipoReto.setActivo(true);
			tipoReto.setCreated(new Date());
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String username;
			if (principal instanceof UserDetails) {
				username = ((UserDetails)principal).getUsername();
				System.out.println("if principal instanceof UserDetails: " + username);
			} else {
			  	username = principal.toString();
				System.out.println("else principal.toString(): " + username);
			}
			tipoReto.setCreatedBy(username);
			tipoReto.setUpdated(new Date());
			tipoReto.setUpdatedBy(username);
			tipoReto.setMax(tipoReto.getMax());
			tipoReto.setTime(tipoReto.getTime());

			tipoRetoService.save(tipoReto);
			return ResponseEntity.ok("Tipo "+tipoReto.getNombre()+" creado correctamente");
		}
		
	    @PreAuthorize("hasRole('ADMIN')")
		@RequestMapping(value="actualizarTipo", method=RequestMethod.PATCH)
		public ResponseEntity<String> actualizarTipo(@RequestBody Tipo miTipo)
		{
			Optional<Tipo> optionalTipo = tipoService.getOne((UUID) miTipo.getId());
			if(optionalTipo.isPresent())
			{
				Tipo actualizarTipo = optionalTipo.get();
				actualizarTipo.setActivo(miTipo.isActivo());
				actualizarTipo.setNombre(miTipo.getNombre());
				Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				String username;
				if (principal instanceof UserDetails) {
					username = ((UserDetails)principal).getUsername();
				} else {
				  	username = principal.toString();
				}
				actualizarTipo.setUpdatedBy(username);
				
				tipoService.save(actualizarTipo);
				return ResponseEntity.ok("Tecnico actualizado correctamente");
			}
			else
			{
				return ResponseEntity.notFound().build();
			}
		}
	    
	  //-------------------------------------- WEB SERVICE RETO------------------------------------------------------
	    /**
		 * Metodo que muestra todos los Miembros almacenados en la base de datos
		 * @return lista de Miembro
		 */
		@GetMapping({"/obtenerReto","/obtenerReto{activo}"})
		public ResponseEntity<?> obtenerReto(@RequestParam(required = false) String activo)
		{
			List<Reto> reto;
			if(activo!=null) {
				try {
					reto = retoService.getByActivo(Boolean.parseBoolean(activo));
				}catch(NoSuchElementException e) {
					return new ResponseEntity<>("No se encontraron resultados", HttpStatus.CONFLICT);
				}
			}else {
				reto = retoService.list();
			}
			List<RetoDTO> retoDTO = new ArrayList<RetoDTO>();
			for(int i=0;i<reto.size();i++) {
				RetoDTO retoAUX=new RetoDTO();
				retoAUX.setId(reto.get(i).getId());
				retoAUX.setNombre(reto.get(i).getNombre());
				retoAUX.setDescripcion(reto.get(i).getDescripcion());
				retoAUX.setFechaFin(reto.get(i).getFechaFin());
				retoAUX.setFechaInicio(reto.get(i).getFechaInicio());
				retoAUX.setTipo(reto.get(i).getTipoReto().getNombre());
				retoDTO.add(retoAUX);
			}
			//return ResponseEntity.ok(reto);
			return ResponseEntity.ok(retoDTO);
		}
		@GetMapping({"/obtenerRET","/obtenerRET{nombre}"})
	    public ResponseEntity<?> getReto(@RequestParam String nombre){
			Optional<Reto> optionalReto = retoService.getByNombre(nombre);
			if(optionalReto.isPresent())
			{
				Reto reto=optionalReto.get();
				RetoDTO retoDTO=new RetoDTO();
				retoDTO.setNombre(reto.getNombre());
				return ResponseEntity.ok(retoDTO);
	        }
			return new ResponseEntity<>("No se encontraron resultados", HttpStatus.NOT_FOUND);
	    }//fin del metodo
		
		@PreAuthorize("hasRole('ADMIN')")
		@RequestMapping(value="crearReto", method=RequestMethod.POST)
		public ResponseEntity<?> crearReto(@RequestBody Reto miReto)
		{
			miReto.setNombre(miReto.getNombre());
			miReto.setDescripcion(miReto.getDescripcion());
			miReto.setFechaInicio(miReto.getFechaInicio());
			System.out.println("miReto.getFechafin(): "+miReto.getFechaFin());
			miReto.setFechaFin(miReto.getFechaFin());
			miReto.setTipoReto(miReto.getTipoReto());
	    	miReto.setActivo(true);
	    	miReto.setCreated(new Date());
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String username;
			if (principal instanceof UserDetails) {
				username = ((UserDetails)principal).getUsername();
			} else {
			  	username = principal.toString();
			}
			miReto.setCreatedBy(username);
			miReto.setUpdated(new Date());
			miReto.setUpdatedBy(username);
			miReto.setDispositivo(miReto.getDispositivo());
			miReto.setCupoMaximo(miReto.getCupoMaximo());
			miReto.setBanner(miReto.getBanner());
			miReto.setIcono(miReto.getIcono());
			miReto.setClub(miReto.getClub());

			System.out.println("Mi Reto: " + miReto);
			retoService.save(miReto);
			return ResponseEntity.ok("Reto "+miReto.getNombre()+" creado correctamente");
		}
		
		@PreAuthorize("hasRole('ADMIN')")
		@RequestMapping(value="actualizarReto", method=RequestMethod.PATCH)
		public ResponseEntity<String> actualizarReto(@RequestBody Reto miReto)
		{
			Optional<Reto> optionalReto = retoService.getOne((UUID) miReto.getId());
			if(optionalReto.isPresent())
			{
				Reto actualizarReto = optionalReto.get();
				actualizarReto.setActivo(miReto.isActivo());
				actualizarReto.setNombre(miReto.getNombre());
				Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				String username;
				if (principal instanceof UserDetails) {
					username = ((UserDetails)principal).getUsername();
				} else {
				  	username = principal.toString();
				}
				actualizarReto.setUpdatedBy(username);
				
				retoService.save(actualizarReto);
				return ResponseEntity.ok("Tecnico actualizado correctamente");
			}
			else
			{
				return ResponseEntity.notFound().build();
			}
		}
		@RequestMapping(value="registrarDatos", method=RequestMethod.POST)
		public ResponseEntity<?> registrarDatos(@RequestBody EntrenamientoUsuario datos)
		{
			JSONObject resp=new JSONObject();
			try {
		    	datos.setFechaRegistro(new Date());
				entrenamientoUsuarioService.save(datos);	
				resp.put("msg", "Datos guardados correctamente");
				return new ResponseEntity<>(resp.toString(), HttpStatus.OK); 			
			}catch(Exception e) {

				e.printStackTrace();
				resp.put("msg", "Ocurrio un error desconocido");
				return new ResponseEntity<>(resp.toString(), HttpStatus.INTERNAL_SERVER_ERROR); 
			}
			
		}
		@RequestMapping(value="pasosDados/{idCliente}", method=RequestMethod.GET)
		public ResponseEntity<?> pasosDados(@PathVariable("idCliente") int idCliente)
		{
			JSONObject resp=new JSONObject();
			try {
				 Query<PasosCalorias> listaClases;
					Session currentSession = entityManager.unwrap(Session.class);
				listaClases = currentSession.createNativeQuery("select * from pasos_calorias",PasosCalorias.class);
				List<PasosCalorias> listaUsuarios=listaClases.getResultList();
				 Query<?> usuarioEntrenamiento;
				 usuarioEntrenamiento = currentSession.createNativeQuery("select coalesce(sum(pasos),0) from entrenamiento_usuario where id_cliente="+idCliente+""
				 		+ " and fecha_registro between (select date_trunc('month',current_date))  and "
				 		+ " (select date_trunc('month',current_date) +CAST ( '1month' AS interval )-CAST ( '1sec' AS interval ))");
				 BigInteger lista= (BigInteger) usuarioEntrenamiento.getSingleResult();
				PasosDados pasosDados=new PasosDados();
				pasosDados.setLista(listaUsuarios);
				pasosDados.setPasos(lista.intValue());
				return new ResponseEntity<>(pasosDados, HttpStatus.OK); 			
			}catch(Exception e) {

				e.printStackTrace();
				resp.put("msg", "Ocurrio un error desconocido");
				return new ResponseEntity<>(resp.toString(), HttpStatus.INTERNAL_SERVER_ERROR); 
			}
		
		}
		@RequestMapping(value="caloriasQuemadas/{idCliente}", method=RequestMethod.GET)
		public ResponseEntity<?> caloriasQuemadas(@PathVariable("idCliente") int idCliente)
		{
			JSONObject resp=new JSONObject();
			try {
				Query<PasosCalorias> listaClases;
				Session currentSession = entityManager.unwrap(Session.class);
			listaClases = currentSession.createNativeQuery("select * from pasos_calorias",PasosCalorias.class);
			List<PasosCalorias> listaUsuarios=listaClases.getResultList();
			 Query<?> usuarioEntrenamiento;
			 usuarioEntrenamiento = currentSession.createNativeQuery("select coalesce(sum(calorias),0) from entrenamiento_usuario where id_cliente="+idCliente+""
					 + " and fecha_registro between (select date_trunc('month',current_date))  and "
				 		+ " (select date_trunc('month',current_date) +CAST ( '1month' AS interval )-CAST ( '1sec' AS interval ))");
			 Float lista= (Float) usuarioEntrenamiento.getSingleResult();
				CaloriasQuemadas pasosDados=new CaloriasQuemadas();
			pasosDados.setLista(listaUsuarios);
			pasosDados.setCalorias(lista.floatValue());
			return new ResponseEntity<>(pasosDados, HttpStatus.OK); 				
			}catch(Exception e) {

				e.printStackTrace();
				resp.put("msg", "Ocurrio un error desconocido");
				return new ResponseEntity<>(resp.toString(), HttpStatus.INTERNAL_SERVER_ERROR); 
			}
			
		}

		//OCTUBRE

		//--------------   Asignar un cliente    ----------------------->>>>>>
		@RequestMapping(value = "asignarCliente", method = RequestMethod.POST)
		@Transactional(rollbackFor = SQLException.class)
		public ResponseEntity<?> asignarCliente(@RequestBody ObjectNode objectNode) {
			JSONObject response = new JSONObject();
			int idCliente = objectNode.get("idCliente").asInt();
			String idReto = objectNode.get("idReto").asText();

			try {
				Cliente cliente = clienteService.findById(idCliente);
				Optional<Reto> retoOptional = retoService.getOne(UUID.fromString(idReto));

				if (!retoOptional.isPresent() || retoOptional.get().isActivo() == false) {
					return new ResponseEntity<>("RETO no existe.", HttpStatus.NOT_FOUND);
				}
				Reto reto = retoOptional.get();

				RetoUsuario retoUsuario = retoUsuarioService.existeCliente(reto, cliente);
				if (retoUsuario != null) {
					return new ResponseEntity<>("CLIENTE inscrito anteriormente.", HttpStatus.CONFLICT);
				}

				if (reto.getCupoMaximo() > reto.getNoInscritos()) {
					retoUsuario = new RetoUsuario();
					retoUsuario.setCliente(cliente);
					retoUsuario.setReto(reto);
					retoUsuario.setClub(cliente.getClub().getIdClub());
					retoUsuario.setMembresia(cliente.getTipoMembresia().getIdTipoMembresia());
					retoUsuario.setEstatusCobranza(cliente.getEstatusCobranza().getIdEstatusCobranza());

					//Incrementar numero de inscritos
					reto.setNoInscritos(reto.getNoInscritos() + 1);
					retoUsuarioService.save(retoUsuario);
					return new ResponseEntity<>("CLIENTE inscrito correctamente. ", HttpStatus.OK);
				}else {
					return new ResponseEntity<>("CUPO máximo alcanzado.", HttpStatus.OK);
				}
			}catch (Exception e){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				response.put("respuesta", "El usuario ya tiene una rutina asignada");
				return new ResponseEntity<String>(response.toString(), HttpStatus.CONFLICT);
			}
	}


		//--------------   Lista de retos disponibles por cliente y estado de inscripción    ----------------------->>>>>>
		@PreAuthorize("hasRole('ADMIN')")
		@GetMapping(path="/disponiblesByClub")
		public ResponseEntity<?> listaRetosByCLub(@RequestParam int idCliente)
		 {
			JSONObject response = new JSONObject();

			try {
				//Consultar si cliente existe
				Cliente cliente = clienteService.findById(idCliente);
				if (cliente == null) {
					response.put("respuesta", "CLIENTE no encontrado.");
					return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
				}
				//Obtener datos recibidos del Join
				List<Tuple> t = retoUsuarioService.getRetoUsuarioDTO(cliente.getIdCliente());

				List<RetosByClienteDTO> retosByClienteDTOList = new ArrayList<RetosByClienteDTO>();

				for (Tuple tuple:t){
					Optional<TipoReto> tipoRetoOptional = tipoRetoService.getOne(UUID.fromString(tuple.get(3, String.class)));
					TipoRetoDTO tipoRetoDTO = new TipoRetoDTO();
					if(tipoRetoOptional.isPresent()) {
						tipoRetoDTO.setId(tipoRetoOptional.get().getId());
						tipoRetoDTO.setNombre(tipoRetoOptional.get().getNombre());
						tipoRetoDTO.setDificultad(tipoRetoOptional.get().getDificultad());
						tipoRetoDTO.setTime(tipoRetoOptional.get().getTime());
						tipoRetoDTO.setMax(tipoRetoOptional.get().getMax());
					}
					SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
					Date fechaInicio = formato.parse(tuple.get(6, String.class));
					Date fechaFin = formato.parse(tuple.get(7, String.class));
					RetosByClienteDTO reto = new RetosByClienteDTO(UUID.fromString(tuple.get(0, String.class)),
							tuple.get(1, String.class),
							tuple.get(2, String.class),
							tipoRetoDTO,
							tuple.get(4, Integer.class),
							tuple.get(5, Integer.class),
							fechaInicio,
							fechaFin,
							tuple.get(8, Boolean.class),
							tuple.get(9, String.class),
							tuple.get(10, Integer.class),
							tuple.get(11, String.class),
							tuple.get(12, String.class),
							tuple.get(13, Integer.class)
					);
					retosByClienteDTOList.add(reto);
				}
				return new ResponseEntity<>(retosByClienteDTOList, HttpStatus.OK);

			}catch(NoSuchElementException e) {
				response.put("respuesta", "No hay RETOS disponibles.");
				return new ResponseEntity<>(response.toString(), HttpStatus.NOT_FOUND);
			} catch(Exception e) {
				response.put("respuesta", "ERROR interno.");
				return new ResponseEntity<>(response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
			}

		}


		//--------------   Agregar valores acumulables   ----------------------->>>>>>
		@PreAuthorize("hasRole('ADMIN')")
		@PostMapping(path="/setAcumulables")
		@Transactional(rollbackFor = SQLException.class)
		public ResponseEntity<?> setValoresAcumulables(@RequestBody ObjectNode objectNode)
		{
			JSONObject response = new JSONObject();

			if (objectNode.get("idCliente") == null || objectNode.get("idReto") == null) {
				response.put("respuesta","CLIENTE Y RETO obligatorios.");
				return new ResponseEntity<>(response.toMap(), HttpStatus.CONFLICT);
			}

			int idCliente = objectNode.get("idCliente").asInt();
			String idReto = objectNode.get("idReto").asText();

			int pasos = objectNode.get("pasos") != null ? objectNode.get("pasos").asInt() : 0;
			Double calorias = objectNode.get("calorias") != null ? objectNode.get("calorias").asDouble() : 0;
			Double distancia = objectNode.get("distancia") != null ? objectNode.get("distancia").asDouble() : 0;
			Boolean isSummable = objectNode.get("isSummable").asBoolean();

			try {
				Cliente cliente = clienteService.findById(idCliente);
				Optional<Reto> retoOptional = retoService.getOne(UUID.fromString(idReto));

				if(cliente == null) {
					response.put("respuesta", "CLIENTE no existe.");
					return new ResponseEntity<String>(response.toString(), HttpStatus.NOT_FOUND);
				}
				if (!retoOptional.isPresent()){
					response.put("respuesta", "RETO no existe.");
					return new ResponseEntity<String>(response.toString(), HttpStatus.NOT_FOUND);
				}

				List<RetoUsuario> retoUsuarios = retoUsuarioService.list();

				//buscamos y guardamos el reto_usuario que corresponde al id_reto
				RetoUsuario retoAux = null;
				for (RetoUsuario retoUsuario:retoUsuarios) {
					if (retoUsuario.getReto().getId().equals(retoOptional.get().getId()) &&
							retoUsuario.getCliente().getIdCliente() == cliente.getIdCliente()){
						retoAux = retoUsuario;
						break;
					}
				}

				if (retoAux == null) {
					response.put("respuesta", "Registro inexistente para guardar los datos");
					return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
				}

				List<RetoAcumulable> retoAcumulableList =  retoAcumulableService.list();

				RetoAcumulable retoAcumulable = null;

				//verificamos que el registro ya existe en tabla si es asi y es sumable se suman los nuevos valores que lleguen
				if (!retoAcumulableList.isEmpty()){
					//System.out.println("Lista de retos acumulables no vacia: " + retoAcumulableList);

					for (RetoAcumulable retoAcu: retoAcumulableList){
						if (retoAcu.getRetoUsuario().getReto().getId().equals(retoOptional.get().getId()) &&
								retoAcu.getRetoUsuario().getCliente().getIdCliente() == cliente.getIdCliente()){
							retoAcumulable = retoAcu;
						}
					}

					if (retoAcumulable != null && isSummable){
						retoAcumulable.setCalorias(retoAcumulable.getCalorias() + calorias);
						retoAcumulable.setDistancia(retoAcumulable.getDistancia() + distancia);
						retoAcumulable.setPasos(retoAcumulable.getPasos() + pasos);
						retoAcumulable.setSummable(isSummable);
						retoAcumulable.setRetoUsuario(retoAux);
					}
				} else {
					//en caso contario solo se crea un nuevo registro con los valores de entrada
					retoAcumulable = new RetoAcumulable();
					retoAcumulable.setCalorias(calorias);
					retoAcumulable.setDistancia(distancia);
					retoAcumulable.setPasos(pasos);
					retoAcumulable.setRetoUsuario(retoAux);
					retoAcumulable.setSummable(isSummable);
				}

				retoAcumulableService.save(retoAcumulable);
				response.put("respuesta","Datos registrados correctamente.");
				return new ResponseEntity<>(response.toMap(), HttpStatus.OK);
				//return new ResponseEntity<>(retoAcumulable, HttpStatus.OK);
			}catch(Exception e) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				response.put("respuesta", "CLIENTE ID incorrecto");
				return new ResponseEntity<String>(response.toString(), HttpStatus.CONFLICT);
			}
		}

		//--------------   Agregar valores estadisticos   ----------------------->>>>>>
		@PreAuthorize("hasRole('ADMIN')")
		@PostMapping(path="/setEstadisticos")
		@Transactional(rollbackFor = SQLException.class)
		public ResponseEntity<?> setValoresEstadisticos(@RequestBody ObjectNode objectNode)
		{
			JSONObject response = new JSONObject();

			if (objectNode.get("idCliente") == null || objectNode.get("idReto") == null) {
				response.put("respuesta","CLIENTE Y RETO obligatorios.");
				return new ResponseEntity<>(response.toMap(), HttpStatus.CONFLICT);
			}

			int idCliente = objectNode.get("idCliente").asInt();
			String idReto = objectNode.get("idReto").asText();
			int frecuenciaCardiaca = objectNode.get("frecuenciaCardiaca").asInt();
			int frecuenciaRespiratoria = objectNode.get("frecuenciaRespiratoria").asInt();
			int pasos = objectNode.get("pasos").asInt();
			Double calorias = objectNode.get("calorias").asDouble();
			Double distancia = objectNode.get("distancia").asDouble();
			String updatedAt = objectNode.get("updatedAt").asText();

			try {
				Cliente cliente = clienteService.findById(idCliente);
				Optional<Reto> retoOptional = retoService.getOne(UUID.fromString(idReto));

				if(cliente == null) {
					response.put("respuesta", "CLIENTE no existe.");
					return new ResponseEntity<String>(response.toString(), HttpStatus.NOT_FOUND);
				}
				if (!retoOptional.isPresent()){
					response.put("respuesta", "RETO no existe.");
					return new ResponseEntity<String>(response.toString(), HttpStatus.NOT_FOUND);
				}

				List<RetoUsuario> retoUsuarios = retoUsuarioService.list();

				RetoUsuario retoAux = null;
				for (RetoUsuario retoUsuario:retoUsuarios) {
					if (retoUsuario.getReto().getId().equals(retoOptional.get().getId()) &&
							retoUsuario.getCliente().getIdCliente() == cliente.getIdCliente()){
						retoAux = retoUsuario;
						break;
					}
				}
				if (retoAux == null) {
					response.put("respuesta", "Registro inexistente para guardar los datos");
					return new ResponseEntity<>(response.toMap(), HttpStatus.NOT_FOUND);
				}

				RetoEstadistica retoEstadistica = new RetoEstadistica();

				retoEstadistica.setFrecuenciaCardiaca(frecuenciaCardiaca);
				retoEstadistica.setFrecuenciaRespiratoria(frecuenciaRespiratoria);
				retoEstadistica.setRetoUsuario(retoAux);
				retoEstadistica.setPasos(pasos);
				retoEstadistica.setCalorias(calorias);
				retoEstadistica.setDistancia(distancia);

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
				LocalDateTime dateTime = LocalDateTime.parse(updatedAt, formatter);

				retoEstadistica.setUpdatedAt(dateTime);
				retoEstadisticaService.save(retoEstadistica);

				//return new ResponseEntity<>(retoEstadistica, HttpStatus.OK);
				response.put("respuesta","Datos registrados correctamente.");
				return new ResponseEntity<>(response.toMap(), HttpStatus.OK);

			} catch (DateTimeParseException e) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				response.put("respuesta", "FECHA formato incorrecto");
				return new ResponseEntity<String>(response.toString(), HttpStatus.CONFLICT);
			} catch(Exception e) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				response.put("respuesta", "CLIENTE ID incorrecto");
				return new ResponseEntity<String>(response.toString(), HttpStatus.CONFLICT);
			}
		}

}
