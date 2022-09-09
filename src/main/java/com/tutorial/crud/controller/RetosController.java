package com.tutorial.crud.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tutorial.crud.dto.CaloriasQuemadas;
import com.tutorial.crud.dto.PasosDados;
import com.tutorial.crud.dto.RetoDTO;
import com.tutorial.crud.dto.SalaDTO;
import com.tutorial.crud.dto.TecnicoDTO;
import com.tutorial.crud.dto.TipoActividadDTO;
import com.tutorial.crud.dto.TipoDTO;
import com.tutorial.crud.entity.CAClase;
import com.tutorial.crud.entity.CASala;
import com.tutorial.crud.entity.CATecnico;
import com.tutorial.crud.entity.CATipoActividad;
import com.tutorial.crud.entity.EntrenamientoUsuario;
import com.tutorial.crud.entity.PasosCalorias;
import com.tutorial.crud.entity.Reto;
import com.tutorial.crud.entity.Tipo;
import com.tutorial.crud.service.EntrenamientoUsuarioService;
import com.tutorial.crud.service.RetoService;
import com.tutorial.crud.service.TipoService;

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
	//-------------------------------------- WEB SERVICE TIPO------------------------------------------------------
		/**
		 * Metodo que muestra todos los Miembros almacenados en la base de datos
		 * @return lista de Miembro
		 */
		@GetMapping({"/obtenerTipo","/obtenerTipo{activo}"})
		public ResponseEntity<?> obtenerTipo(@RequestParam(required = false) String activo)
		{
			List<Tipo> tipo;
			if(activo!=null) {
				try {
					tipo = tipoService.getByActivo(Boolean.parseBoolean(activo));
				}catch(NoSuchElementException e) {
					return new ResponseEntity<>("No se encontraron resultados", HttpStatus.CONFLICT);
				}
			}else {
				tipo = tipoService.list();
			}
			List<TipoDTO> tipoDTO = new ArrayList<TipoDTO>();
			for(int i=0;i<tipo.size();i++) {
				TipoDTO tipoaux=new TipoDTO();
				tipoaux.setNombre(tipo.get(i).getNombre());			
				tipoDTO.add(tipoaux);
			}
			return ResponseEntity.ok(tipo);
		}
		
		/**
		 * Metodo que muestra solo un miembro
		 * @param Id es el id del miembro que se quiere mostrar, en caso de no encontrarlo genera un RuntimeException
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
		public ResponseEntity<?> crearTecnico(@RequestBody Tipo tipo)
		{
	    	tipo.setActivo(true);
	    	tipo.setCreated(new Date());
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String username;
			if (principal instanceof UserDetails) {
				username = ((UserDetails)principal).getUsername();
			} else {
			  	username = principal.toString();
			}
			tipo.setCreatedBy(username);
			tipo.setUpdated(new Date());
			tipo.setUpdatedBy(username);
			tipoService.save(tipo);
			return ResponseEntity.ok("Tipo "+tipo.getNombre()+" creado correctamente");
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
				retoAUX.setDescripcion(reto.get(i).getDescripcion());
				retoAUX.setFechaFin(reto.get(i).getFechafin());		
				retoAUX.setFechaInicio(reto.get(i).getFechaInicio());
				retoAUX.setTipo(reto.get(i).getTipo().getNombre());
				retoAUX.setNombre(reto.get(i).getNombre());
				retoDTO.add(retoAUX);
			}
			return ResponseEntity.ok(reto);
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
		public ResponseEntity<?> crearTecnico(@RequestBody Reto miReto)
		{
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
}
