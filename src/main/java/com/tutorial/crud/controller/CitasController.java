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
import java.util.ArrayList;
import java.util.Calendar;
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

import com.tutorial.crud.aopDao.endpoints;
import com.tutorial.crud.dto.ActividadDTO;
import com.tutorial.crud.dto.ApartadosDTO;
import com.tutorial.crud.dto.ClienteDTO;
import com.tutorial.crud.dto.ClienteDTOApp;
import com.tutorial.crud.dto.ClienteDTOO;
import com.tutorial.crud.dto.HorarioDTO;
import com.tutorial.crud.dto.HorarioVista;
import com.tutorial.crud.dto.SalaDTO;
import com.tutorial.crud.dto.TecnicoDTO;
import com.tutorial.crud.dto.TipoActividadDTO;
import com.tutorial.crud.entity.CAActividad;
import com.tutorial.crud.entity.CAApartados;
import com.tutorial.crud.entity.CAApartadosUsuario;
import com.tutorial.crud.entity.CAClase;
import com.tutorial.crud.entity.CAHorario;
import com.tutorial.crud.entity.CASala;
import com.tutorial.crud.entity.CATecnico;
import com.tutorial.crud.entity.CATipoActividad;
import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.Club;
import com.tutorial.crud.entity.PaseConsumido;
import com.tutorial.crud.entity.PaseUsuario;
import com.tutorial.crud.entity.Registro;
import com.tutorial.crud.entity.RegistroGimnasio;
import com.tutorial.crud.entity.ReportePromocion;
import com.tutorial.crud.entity.TerminalRedencion;
import com.tutorial.crud.entity.Body;
import com.tutorial.crud.entity.configuracion;
import com.tutorial.crud.service.ActividadService;
import com.tutorial.crud.service.ApartadosService;
import com.tutorial.crud.service.ApartadosUsuarioService;
import com.tutorial.crud.service.CAHorarioService;
import com.tutorial.crud.service.ClienteService;
import com.tutorial.crud.service.ClubService;
import com.tutorial.crud.service.FotoServiceImpl;
import com.tutorial.crud.service.PaseConsumidoService;
import com.tutorial.crud.service.PaseUsuarioService;
import com.tutorial.crud.service.RegistroGimnasioService;
import com.tutorial.crud.service.RegistroService;
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
@RequestMapping("/citas")
@CrossOrigin(origins = "*")
public class CitasController 
{
endpoints e = new endpoints();
    
	private static Logger logJava = Logger.getLogger(CitasController.class);

    @Autowired
    configuracionService configuracionService;
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	SalaService salaService;
	
	@Autowired
	ClubService clubService;	

	@Autowired
	ApartadosService apartadosService;
	
	@Autowired
	CAHorarioService horarioService;

	@Autowired
	TipoActividadService tipoActividadService;
	
	@Autowired
	TecnicoService tecnicoService;
	
	@Autowired
	ActividadService actividadService;
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	ApartadosUsuarioService apartadosUsuarioService;
	
	@Autowired
	PaseUsuarioService paseUsuarioService;
	
	@Autowired
	PaseConsumidoService paseConsumidoService;
	
	@Autowired
	TerminalRedencionService terminalRedencionService;	

	@Autowired
	RegistroService registroService;
	 
	@Autowired
	RegistroGimnasioService registroGimnasioService;
	
	@Autowired
	FotoServiceImpl fotoService;
	

	
	@Value("${my.property.data}")
	String dbURL;

	@Value("${my.property.userData}")
	String userData;

	@Value("${my.property.passData}")
    String passData;
	
	//-------------------------------------- WEB SERVICE SALAS------------------------------------------------------
	/**
	 * Metodo que muestra todos los Miembros almacenados en la base de datos
	 * @return lista de Miembro
	 */
	@GetMapping({"/obtenerSala","/obtenerSala{activo}"})
	public ResponseEntity<?> obtenerSala(@RequestParam(required = false) String activo)
	{
		List<CASala> salas;
		if(activo!=null) {
			try {
				salas = salaService.getByActivo(Boolean.parseBoolean(activo));
			}catch(NoSuchElementException e) {
				return new ResponseEntity<>("No se encontraron resultados", HttpStatus.CONFLICT);
			}
		}else {
			salas = salaService.list();
		}
		return ResponseEntity.ok(salas);
	}
	
	/**
	 * Metodo que muestra solo un miembro
	 * @param Id es el id del miembro que se quiere mostrar, en caso de no encontrarlo genera un RuntimeException
	 * @return 
	 * @return El miembro con el id 
	 */
	@GetMapping({"/obtenerSala","/obtenerSala/{nombre}{club}"})
	@ResponseBody
    public ResponseEntity<?> getSala(@RequestParam String nombre,@RequestParam String club){
		Session currentSession = entityManager.unwrap(Session.class);
		Query<CASala> listaClases = currentSession.createQuery("FROM CASala s where s.nombre=:o and s.club.IdClub=:u", CASala.class);
		listaClases.setParameter("o",nombre);
		listaClases.setParameter("u",Integer.parseInt(club));
		try {
			CASala salas=listaClases.getSingleResult();
			SalaDTO salaaux=new SalaDTO();
			salaaux.setClub(salas.getClub().getIdClub());
			salaaux.setNombre(salas.getNombre());
			return ResponseEntity.ok(salaaux);
		}catch(NoResultException e) {
				return new ResponseEntity<>("No se encontraron resultados", HttpStatus.CONFLICT);
		}		
    }//fin del metodo
	
	/**
	 * Metodo que añade a la base de datos un nuevo miembro
	 * @param miembro es el objeto miembro que se desea añadir, en caso de contar con el mismo id, actualiza los valores solamente
	 * @return el objeto miembro que fue almacenado
	 */

	@RequestMapping(value="crearSala", method=RequestMethod.POST)
	public ResponseEntity<?> crearSala(@RequestBody SalaDTO miSala)
	{
    	Session currentSession = entityManager.unwrap(Session.class);
		Query<CASala> listaClases = currentSession.createQuery("FROM CASala s where s.nombre=:o and s.club.IdClub=:u", CASala.class);
		listaClases.setParameter("o",miSala.getNombre());
		listaClases.setParameter("u",miSala.getClub());
		if(!listaClases.list().isEmpty()) {
			return new ResponseEntity<>("Sala ya existe", HttpStatus.CONFLICT);}
		CASala nuevaSala=new CASala();
		nuevaSala.setActivo(true);
		nuevaSala.setClub(clubService.findById(miSala.getClub()));
		nuevaSala.setCreated(new Date());
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
		  	username = principal.toString();
		}
		nuevaSala.setCreatedBy(username);
		nuevaSala.setNombre(miSala.getNombre());
		nuevaSala.setUpdated(new Date());
		nuevaSala.setUpdatedBy(username);
		nuevaSala = salaService.save(nuevaSala);
		return new ResponseEntity<>(nuevaSala, HttpStatus.OK);
	}
	
	/**
	 * Metodo que modifica un miembro ya existente en la base de datos (el miembro debe existir sino sera creado uno nuevo)
	 * @param miembro es el objecto miembro que se quiere modificar
	 * @return objeto miembro ya modificado
	 */

	@RequestMapping(value="actualizarSala", method=RequestMethod.POST)
	public ResponseEntity<?> actualizarSala(@RequestBody Body miSala)
	{
		JSONObject json=new JSONObject();
		try{
			Optional<CASala> OpcionalSala = salaService.getOne((UUID) miSala.getId());
			if(OpcionalSala.isPresent())
			{
				CASala actualizarSala = OpcionalSala.get();
				actualizarSala.setNombre(miSala.getNombre());
				Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				String username;
				if (principal instanceof UserDetails) {
					username = ((UserDetails)principal).getUsername();
				} else {
				  	username = principal.toString();
				}
				actualizarSala.setUpdatedBy(username);
				actualizarSala.setUpdated(new Date());
				Club nuevoClub=clubService.findById(miSala.getIdClub());
				actualizarSala.setClub(nuevoClub);
				actualizarSala=salaService.save(actualizarSala);
				return ResponseEntity.ok(actualizarSala);
			}else {
				json.put("msg", "No se encontro la Sala con ese id");
				return new ResponseEntity<>(json.toString(), HttpStatus.BAD_REQUEST); 
			}
		}catch(Exception e) {
			e.printStackTrace();
			json.put("msg", "Ocurrio un error desconocido");
			return new ResponseEntity<>(json.toString(), HttpStatus.INTERNAL_SERVER_ERROR); 
		}

		
	}
	@RequestMapping(value="deleteSala", method=RequestMethod.POST)
	public ResponseEntity<?> deleteSala(@RequestBody Body miSala)
	{
		JSONObject resp=new JSONObject();
		try{
			Optional<CASala> OpcionalSala = salaService.getOne((UUID) miSala.getId());
			if(OpcionalSala.isPresent())
			{
				CASala actualizarSala = OpcionalSala.get();
				actualizarSala.setActivo(false);
				Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				String username;
				if (principal instanceof UserDetails) {
					username = ((UserDetails)principal).getUsername();
				} else {
				  	username = principal.toString();
				}
				actualizarSala.setUpdatedBy(username);
				actualizarSala.setUpdated(new Date());
				
				salaService.save(actualizarSala);
				resp.put("msg", "la sala se ha eliminado");
				return ResponseEntity.ok(resp.toString());
			}else {
				resp.put("msg", "No se encontro la Sala con ese id");
				return new ResponseEntity<>(resp.toString(), HttpStatus.BAD_REQUEST); 
			}
		}catch(Exception e) {
			e.printStackTrace();
			resp.put("msg", "Ocurrio un error desconocido");
			return new ResponseEntity<>(resp.toString(), HttpStatus.INTERNAL_SERVER_ERROR); 
		}

		
	}
    
    
    //------------------------------------- WEB SERVICE CLASES-------------------------------------------------


   	@GetMapping({"/obtenerClases{club}{dia}{admin}","/obtenerClases{club}{dia}{usuario}{admin}"})
	public ResponseEntity<List<CAClase>> obtenerClasesPOST(@RequestParam String club,@RequestParam String dia,@RequestParam(required = false) Integer usuario,@RequestParam(required = false) boolean admin)
	{
   		if(club.equals("Sports_Plaza")) {
   			club="Sports Plaza";
   		}else if(club.equals("Club_Alpha_3")) {
   			club="Club Alpha 3";
   		}else if(club.equals("Club_Alpha_2")) {
   			club="Club Alpha 2";
   		}
   		String[] dd=dia.split("-");
   		if(dd[2].length()==1) {
   			dd[2]="0"+dd[2];
   		}
   		dia=dd[0]+"-"+dd[1]+"-"+dd[2];
		Session currentSession = entityManager.unwrap(Session.class);
        Query<CAClase> listaClases;
		if(club.equals("Club Alpha 3") || club.equals("Club Alpha 2") ||club.equals("CIMERA")){
			listaClases = currentSession.createNativeQuery("select id,nombre,clases.tecnico,tipo_actividad,color, lugar,"
        			+ "clases.duracion,nivel,clases.hora,cupo_actual,cupo_maximo,clases.rango,clases.disponible,clases.dia,"
        			+ " clases.paga, clases.id_apartados from clases  where dia='"+dia+"'  and club='"+club+"' and"
        			+ " disponible=true  and   TO_TIMESTAMP(clases.dia||split_part(clases.rango, '-', 2),'YYYY-MM-DDHH24:MI')>CURRENT_TIMESTAMP "
        			+ "order by to_timestamp(split_part(clases.rango, '-', 1),'HH24:MI');",CAClase.class);
		}else {
			listaClases = currentSession.createNativeQuery("select id,nombre,clases.tecnico,tipo_actividad,color, lugar,"
        			+ "clases.duracion,nivel,clases.hora,cupo_actual,cupo_maximo,clases.rango,clases.disponible,clases.dia,"
        			+ " clases.paga, clases.id_apartados from clases  where dia='"+dia+"'  and club='"+club+"' and"
        			+ " disponible=true and segmentacion="+admin+" and  "
        			+ " TO_TIMESTAMP(clases.dia||split_part(clases.rango, '-', 2),'YYYY-MM-DDHH24:MI')>CURRENT_TIMESTAMP "
        			+ "order by to_timestamp(split_part(clases.rango, '-', 1),'HH24:MI');",CAClase.class);
		}
        	

        //FROM CAClase WHERE id IN (select h.id FROM CAHorario h WHERE h.lunes = true) and clases.tecnico='CLUB ALPHA 2'
		
		List<CAClase> lista= listaClases.getResultList();
		
		return ResponseEntity.ok(lista);
	}
   	
   	@GetMapping({"/obtenerClasesFitness{club}{dia}","/obtenerClasesFitness{club}{dia}"})
	public ResponseEntity<List<CAClase>> obtenerClasesFitness(@RequestParam String club,@RequestParam String dia)
	{   		
   		String[] dd=dia.split("-");
   		if(dd[2].length()==1) {
   			dd[2]="0"+dd[2];
   		}
   		dia=dd[0]+"-"+dd[1]+"-"+dd[2];
		Session currentSession = entityManager.unwrap(Session.class);
        Query<CAClase> listaClases;
			listaClases = currentSession.createNativeQuery("select id,nombre,clases.tecnico,tipo_actividad,color, lugar,"
        			+ "clases.duracion,nivel,clases.hora,cupo_actual,cupo_maximo,clases.rango,clases.disponible,clases.dia,"
        			+ " clases.paga, clases.id_apartados from clases  where dia='"+dia+"'  and fitness is true and club='"+club+"' and"
        			+ " disponible=true  order by to_timestamp(split_part(clases.rango, '-', 1),'HH24:MI');",CAClase.class); 	

		
		List<CAClase> lista= listaClases.getResultList();
		
		return ResponseEntity.ok(lista);
	}
   	@PostMapping("/obtenerClases")
	public ResponseEntity<List<CAClase>> obtenerClases(@RequestBody Body body)
	{	
   		body.setFechaInicio(new java.sql.Date(body.getFechaInicio().getTime()+(6 * 60 * 60 * 1000)));
   		body.setFechaFin(new java.sql.Date(body.getFechaFin().getTime()+(30 * 60 * 60 * 1000)));
		Session currentSession = entityManager.unwrap(Session.class);
        Query<CAClase> listaClases;
        //listaClases = currentSession.createNativeQuery("select id,nombre,clases.tecnico,tipo_actividad,color, lugar,clases.duracion,nivel,"
		//		+ "clases.hora,cupo_actual,cupo_maximo,clases.rango,clases.disponible,clases.dia, clases.paga, clases.id_apartados from "
		//		+ "clases  where nombre='"+body.getActividad()+"' AND "
		//				+ "TO_TIMESTAMP(clases.dia|| ' ' ||split_part(clases.rango, '-', 1),'YYYY-MM-DD HH24:MI') between "
		//				+ "cast('"+body.getFechaInicio()+" 00:00:00' as timestamp) and cast('"+body.getFechaFin()+" 23:59:59' as timestamp)"
		//						+ " and club='"+body.getClub()+"' and disponible is true  and segmentacion is false order by "
		//								+ "TO_TIMESTAMP(clases.dia|| ' ' ||split_part(clases.rango, '-', 1),'YYYY-MM-DD HH24:MI');",CAClase.class);	
       //listaClases = currentSession.createNativeQuery("select id,nombre,clases.tecnico,tipo_actividad,color, lugar,"
    	//		+ "clases.duracion,nivel,clases.hora,cupo_actual,cupo_maximo,clases.rango,clases.disponible,clases.dia,"
    	//		+ " clases.paga, clases.id_apartados from clases  where  dia between '"+body.getFechaInicio()+"' and '"+body.getFechaFin()+"'"
    	//				+ "   and club='"+body.getClub()+"' and disponible=true order by dia,to_timestamp(split_part(clases.rango, '-', 1),'HH24:MI');",CAClase.class);	
       listaClases = currentSession.createNativeQuery("select * from clases  where  dia "
       		+ "between '"+body.getFechaInicio()+"' and '"+body.getFechaFin()+"'   and club='"+body.getClub()+"' and disponible=true "
       				+ "order by hora;",CAClase.class);	
		
       //FROM CAClase WHERE id IN (select h.id FROM CAHorario h WHERE h.lunes = true) and clases.tecnico='CLUB ALPHA 2'
		
		List<CAClase> lista= listaClases.getResultList();
		
		return ResponseEntity.ok(lista);
	}
   	@GetMapping("/obtenerClasesCanceladas{club}{dia}")
	public ResponseEntity<List<CAClase>> obtenerClasesCanceladas(@RequestParam String club,@RequestParam String dia){
		Session currentSession = entityManager.unwrap(Session.class);
		Query<CAClase> listaClases;
        	
        	listaClases = currentSession.createNativeQuery("select id,nombre,clases.tecnico,tipo_actividad,color,"
    				+ "lugar,clases.duracion,nivel,clases.hora,cupo_actual,cupo_maximo,clases.rango,clases.disponible,clases.dia,"
    				+ "clases.paga, clases.id_apartados from clases  where dia='"+dia+"'  and club='"+club+"'and disponible=false"
    				+ " order by to_timestamp(clases.hora,'HH24:MI')",CAClase.class);	

        //FROM CAClase WHERE id IN (select h.id FROM CAHorario h WHERE h.lunes = true) and clases.tecnico='CLUB ALPHA 2'
		
		List<CAClase> lista= listaClases.getResultList();
		
		return ResponseEntity.ok(lista);
	}
   	
   	@GetMapping({"/obtenerApartadosUser","/obtenerApartadosUser{usuario}"})
	public ResponseEntity<List<CAClase>> obtenerApartadosUsuario(@RequestParam Integer usuario)
	{
   		Cliente cliente = clienteService.findById(usuario);
   		Session currentSession = entityManager.unwrap(Session.class);
   		/*Query<CAClase> listaClases = currentSession.createNativeQuery("select clases.id,nombre,clases.tecnico,tipo_actividad,color,"
   				+ "lugar,clases.duracion,nivel,clases.hora,cupo_actual,cupo_maximo,clases.rango,clases.disponible, dia, paga, clases.id_apartados "
   				+ "from  ca_apartados_usuario join clases on ca_apartados_usuario.id_apartados=clases.id_apartados "
   				+ "full join pase_consumido on pase_consumido.apartado_usuario=ca_apartados_usuario.id where "
   				+ " idcliente="+cliente.getIdCliente()+" and ca_apartados_usuario.activo=true and fecha_redencion is null order by to_timestamp(clases.hora,'HH24:MI');",CAClase.class);*/
   		Query<CAClase> listaClases = currentSession.createNativeQuery("select clases.id,nombre,clases.tecnico,tipo_actividad,color,"
   				+ "lugar,clases.duracion,nivel,clases.hora, cupo_actual,cupo_maximo,clases.rango,clases.disponible, dia, paga,"
   				+ " clases.id_apartados from  ca_apartados_usuario  join clases on "
   				+ "ca_apartados_usuario.id_apartados=clases.id_apartados left join registro_gimnasio on"
   				+ " ca_apartados_usuario.id_apartados=registro_gimnasio.id_apartados full join pase_consumido on "
   				+ "pase_consumido.apartado_usuario=ca_apartados_usuario.id where idcliente="+cliente.getIdCliente()+" and"
   				+ " ca_apartados_usuario.activo=true and terminal_redencion_id is null and "
   				+ " TO_TIMESTAMP(clases.dia||split_part(clases.rango, '-', 2),'YYYY-MM-DDHH24:MI')>CURRENT_TIMESTAMP  "
   				+ "and registro_gimnasio.id_apartados is null order by to_timestamp(clases.hora,'HH24:MI');",CAClase.class);
   		List<CAClase> lista= listaClases.list();
		/*Date fechaActual=new Date();
		
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List<CAClase> lista2=new ArrayList<CAClase>();
		for(int i=0;i<lista.size();i++) {
			String rango =lista.get(i).getRango();
			String[] hora=rango.split("-");
			try {
				Date fecha = formato.parse(lista.get(i).getDia()+" "+hora[1]);
				if(fecha.after(fechaActual)) {
					lista2.add(lista.get(i));
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}*/
		/*
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List<CAClase> lista2=new ArrayList<CAClase>();
		for(int i=0;i<lista.size();i++) {
			Date fecha;
			try {				
				fecha = formato.parse(lista.get(i).getDia()+" 00:00");
				LocalDate localDate = fechaActual.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				fechaActual=formato.parse(localDate.getYear()+"-"+localDate.getMonthValue()+"-"+localDate.getDayOfMonth()+" 00:00");
				if(fecha.after(fechaActual) || fecha.equals(fechaActual)) {
					lista2.add(lista.get(i));
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
   		
   	
   		
		return ResponseEntity.ok(lista);
	}
   	
   	@GetMapping("/obtenerApartadosUserSemana{usuario}")
	public ResponseEntity<List<CAClase>> obtenerApartadosUserSemana(@RequestParam Integer usuario)
	{
   		Cliente cliente = clienteService.findById(usuario);
   		Session currentSession = entityManager.unwrap(Session.class);
   		Query<?> inicioSemana=currentSession.createNativeQuery("select FECHA from dias_semana where nombre='LUNES'");
   		String lunes=(String)inicioSemana.getSingleResult();
   		Query<CAClase> listaClases = currentSession.createNativeQuery("select clases.id,nombre,clases.tecnico,tipo_actividad,color,lugar,"
   				+ "clases.duracion,nivel,clases.hora, cupo_actual,cupo_maximo,clases.rango,clases.disponible, dia, paga,clases.id_apartados"
   				+ " from  ca_apartados_usuario  join clases on ca_apartados_usuario.id_apartados=clases.id_apartados where idcliente="+usuario+" "
   				+ "and ca_apartados_usuario.activo=true and "
   				+ "TO_TIMESTAMP(clases.dia||split_part(clases.rango, '-', 2),'YYYY-MM-DDHH24:MI')>='"+lunes+"'  order by "
   				+ "to_timestamp(clases.dia|| clases.hora,'YYYY-MM-DDHH24:MI') desc;",CAClase.class);
   		List<CAClase> lista= listaClases.list();
   		
		return ResponseEntity.ok(lista);
	}
    /*@GetMapping({"/obtenerUsuariosByClase","/obtenerUsuariosByClase{clase}"})
	public ResponseEntity<?> obtenerClasesUsuario(@RequestBody Body body)
	{
   		CAApartados apartado = apartadosService.getOne(body.getId());
   		Session currentSession = entityManager.unwrap(Session.class);
   		Query<Cliente> listaClases = currentSession.createQuery("SELECT cliente FROM CAApartadosUsuario c where c.apartados='"+apartado.getId()+"' and c.activo=true", Cliente.class);
   		List<Cliente> lista=listaClases.getResultList();
   		List<ClienteDTO> listaDTO=new ArrayList<ClienteDTO>();
   		for(int i=0;i<lista.size();i++) {
   			ClienteDTO clienteDTO=new ClienteDTO();
   			clienteDTO.setEmail(lista.get(i).getEmail());
   			clienteDTO.setIdCliente(lista.get(i).getIdCliente());
   			if(lista.get(i).getURLFoto()==null) {
   				BufferedImage bImage;
   				try {
   					bImage = ImageIO.read(new File("blank-profile.png"));
   		            ByteArrayOutputStream bos = new ByteArrayOutputStream();
   		            ImageIO.write(bImage, "png", bos );
   		            byte [] data = bos.toByteArray();
   		            clienteDTO.setImagen(data);
   				} catch (IOException e) {
   					e.printStackTrace();
   				}
   			}
   			else
   				clienteDTO.setImagen(lista.get(i).getURLFoto().getImagen());
   			clienteDTO.setNombre(lista.get(i).getNombre()+" "+lista.get(i).getApellidoPaterno()+" "+lista.get(i).getApellidoMaterno());
   			listaDTO.add(clienteDTO);
   		}
		return ResponseEntity.ok(listaDTO);
		
	}*/
   	@GetMapping({"/obtenerUsuariosByClase","/obtenerUsuariosByClase{clase}"})
   	@CrossOrigin(origins = "*")
	public ResponseEntity<?> obtenerClasesUsuario(@RequestBody Body body)
	{
   		CAApartados apartado;
   		try {
   	   		 apartado = apartadosService.getOne(body.getId());
   		}catch(NoSuchElementException e) {
   			apartado=apartadosService.getHorario(body.getId(),body.getDia());
   		}
   		Session currentSession = entityManager.unwrap(Session.class);
   		Query listaClientes = currentSession.createNativeQuery("select (cliente.nombre ||' ' || cliente.apellidopaterno || ' ' ||"
   				+ " cliente.apellidomaterno) AS Nombre, email, cliente.id_foto, cliente.idcliente,clases.lugar,dia"
   				+ " from  ca_apartados_usuario join clases on ca_apartados_usuario.id_apartados=clases.id_apartados join"
   				+ " cliente on ca_apartados_usuario.idcliente=cliente.idcliente where "
   				+ "ca_apartados_usuario.id_apartados='"+apartado.getId()+"' and ca_apartados_usuario.activo=true");
   		Query<CAClase> paga=currentSession.createQuery(" from CAClase c where c.idApartados='"+apartado.getId()+"'",CAClase.class);
   		List<CAClase>pagaa=paga.getResultList();
   		List<Object[]> listResults = listaClientes.getResultList();
		List<ClienteDTO> listaDTO= new ArrayList<ClienteDTO>();
		for (Object[] record : listResults) {
			ClienteDTO cliente=new ClienteDTO();
			for(int i=0;i<record.length;i++) {
				cliente.setNombre((String) record[0]);
				cliente.setEmail((String)record[1]);
				try {
					cliente.setImagen(fotoService.findById((int) record[2]).getImagen());					
				}catch(NullPointerException e) {
					BufferedImage bImage;
	   				try {
	   					bImage = ImageIO.read(new File("blank-profile.png"));
	   		            ByteArrayOutputStream bos = new ByteArrayOutputStream();
	   		            ImageIO.write(bImage, "png", bos );
	   		            byte [] data = bos.toByteArray();
	   		            cliente.setImagen(data);
	   				} catch (IOException ex) {
	   					ex.printStackTrace();
	   				}
				}
				cliente.setIdCliente((int) record[3]);
				cliente.setNombreSala((String) record[4]);
				cliente.setDia((String)record[5]);
			}
			Query redimidos;
			if(pagaa.get(0).getPaga()>0) {
				CAApartadosUsuario apartadoUsuario=apartadosUsuarioService.getOne(cliente.getIdCliente(), apartado.getId());
				 redimidos = currentSession.createNativeQuery("select fecha_redencion,pase_usuario,terminal_redencion_id from pase_consumido where apartado_usuario='"+apartadoUsuario.getId()+"' and terminal_redencion_id is not null;");

			}else {
				redimidos = currentSession.createNativeQuery("select id_cliente,id_terminal,registro_acceso from registro_gimnasio where id_cliente="+cliente.getIdCliente()+" and id_apartados='"+apartado.getId()+"';");
				
			}
			 List lista=redimidos.getResultList(); 

			 if(lista.size()>0) {
				 cliente.setCheck("checked");
			 }else {
				 cliente.setCheck("");
			 }
			listaDTO.add(cliente);

		}
		
		return ResponseEntity.ok(listaDTO);
		
	}
   	@PostMapping({"/obtenerUsuariosByClaseApp","/obtenerUsuariosByClaseApp{clase}"})
	public ResponseEntity<?> obtenerClasesUsuarioApp(@RequestBody Body body)
	{
   		CAApartados apartado = apartadosService.getOne(body.getId());
   		Session currentSession = entityManager.unwrap(Session.class);
   		Query listaClientes = currentSession.createNativeQuery("select (cliente.nombre ||' ' || cliente.apellidopaterno || ' ' ||"
   				+ " cliente.apellidomaterno) AS Nombre, email, cliente.id_foto, cliente.idcliente,clases.lugar,dia"
   				+ " from  ca_apartados_usuario join clases on ca_apartados_usuario.id_apartados=clases.id_apartados join"
   				+ " cliente on ca_apartados_usuario.idcliente=cliente.idcliente where "
   				+ "ca_apartados_usuario.id_apartados='"+apartado.getId()+"' and ca_apartados_usuario.activo=true");
   		Query<CAClase> paga=currentSession.createQuery(" from CAClase c where c.idApartados='"+apartado.getId()+"'",CAClase.class);
   		List<CAClase>pagaa=paga.getResultList();
   		List<Object[]> listResults = listaClientes.getResultList();
		List<ClienteDTOApp> listaDTO= new ArrayList<ClienteDTOApp>();
		for (Object[] record : listResults) {
			ClienteDTOApp cliente=new ClienteDTOApp();
			for(int i=0;i<record.length;i++) {
				cliente.setNombre((String) record[0]);
				cliente.setEmail((String)record[1]);
				try {
					cliente.setImagen(fotoService.findById((int) record[2]).getImagen());					
				}catch(NullPointerException e) {
					BufferedImage bImage;
	   				try {
	   					bImage = ImageIO.read(new File("blank-profile.png"));
	   		            ByteArrayOutputStream bos = new ByteArrayOutputStream();
	   		            ImageIO.write(bImage, "png", bos );
	   		            byte [] data = bos.toByteArray();
	   		            cliente.setImagen(data);
	   				} catch (IOException ex) {
	   					ex.printStackTrace();
	   				}
				}
				cliente.setIdCliente((int) record[3]);
				cliente.setNombreSala((String) record[4]);
				cliente.setDia((String)record[5]);
			}
			Query redimidos;
			if(pagaa.get(0).getPaga()!=0) {
				CAApartadosUsuario apartadoUsuario=apartadosUsuarioService.getOne(cliente.getIdCliente(), apartado.getId());
				 redimidos = currentSession.createNativeQuery("select fecha_redencion,pase_usuario,terminal_redencion_id from pase_consumido where apartado_usuario='"+apartadoUsuario.getId()+"' and terminal_redencion_id is not null;");

			}else {
				redimidos = currentSession.createNativeQuery("select id_cliente,id_terminal,registro_acceso from registro_gimnasio where id_cliente="+cliente.getIdCliente()+" and id_apartados='"+apartado.getId()+"';");
				
			}
			 List lista=redimidos.getResultList(); 

			 if(lista.size()>0) {
				 cliente.setCheck(true);
			 }else {
				 cliente.setCheck(false);
			 }
			listaDTO.add(cliente);

		}
		
		return ResponseEntity.ok(listaDTO);
		
	}
    
    @RequestMapping(value="cancelarClase", method=RequestMethod.POST)
   	public ResponseEntity<?> cancelarClase(@RequestBody Body body){
		JSONObject json=new JSONObject();
    	try {
        	CAApartados apartado=apartadosService.getHorario(body.getId(), body.getDia());
        	apartado.setActivo(false);
        	apartado.setConteo(0);
        	apartadosUsuarioService.delete(apartado);
        	apartadosService.save(apartado);
	   		json.put("Respuesta", "Clase cancelada exitosamente");
	   		
        	return new ResponseEntity<>(json.toString(), HttpStatus.OK); 
    		
    	}catch(Exception e) {
    		e.printStackTrace();
       		json.put("Respuesta", "Ha ocurrido un error desconocido");
       		
       		
        	return new ResponseEntity<>(json.toString(), HttpStatus.CONFLICT); 
    	}
    	
    }
    
    @RequestMapping(value="restablecerClase", method=RequestMethod.POST)
   	public ResponseEntity<?> restablecerClase(@RequestBody Body body){
		JSONObject json=new JSONObject();
    	try {
        	CAApartados apartado=apartadosService.getHorario(body.getId(), body.getDia());
        	apartado.setActivo(true);
        	apartadosService.save(apartado);
	   		json.put("Respuesta", "Clase restablecida exitosamente");
	   		
	   		
        	return new ResponseEntity<>(json.toString(), HttpStatus.OK); 
    		
    	}catch(Exception e) {
    		e.printStackTrace();
       		json.put("Respuesta", "Ha ocurrido un error desconocido");
       	
        	return new ResponseEntity<>(json.toString(), HttpStatus.CONFLICT); 
    	}
    	
    }
	
	
	//--------------------------------------------WEB SERVICE APARTADOS---------------------------------------------------
/**
   	 * Metodo que muestra todos los Miembros almacenados en la base de datos
   	 * @return lista de Miembro
   	 */
   	@GetMapping({"/obtenerApartados","/obtenerApartados{activo}"})
   	public ResponseEntity<?> obtenerApartados(@RequestParam(required = false) String activo)
   	{
   		List<CAApartados> apartados;
   		if(activo!=null) {
   			try {
   				 apartados = apartadosService.getByActivo(Boolean.parseBoolean(activo));
   			}catch(NoSuchElementException e) {
   				return new ResponseEntity<>("No se encontraron resultados", HttpStatus.CONFLICT);
   			}
   			
   		}else {
   			apartados = apartadosService.list();			
   		}
   		List<ApartadosDTO> apartadosDTO = new ArrayList<ApartadosDTO>();
   		for(int i=0;i<apartados.size();i++) {
   			ApartadosDTO apartadosAUX=new ApartadosDTO();
   			apartadosAUX.setConteo(apartados.get(i).getConteo());
   			apartadosAUX.setId(apartados.get(i).getId());
   			/*List<String> texto=new ArrayList<String>();
   			for(int j=0;j<apartados.get(i).getHorario().size();j++) {
   				String actividad=apartados.get(i).getHorario().get(j).getActividad().getNombre();
   				String sala=apartados.get(i).getHorario().get(j).getSala().getNombre();
   				String nombre=apartados.get(i).getHorario().get(j).getTecnico().getNombre();
   				String hora=apartados.get(i).getHorario().get(j).getHora();
   				texto.add(actividad+" "+sala+" "+nombre+" "+hora);	
   			}*/
   			String actividad=apartados.get(i).getHorario().getActividad().getNombre();
			String sala=apartados.get(i).getHorario().getSala().getNombre();
			String nombre=apartados.get(i).getHorario().getTecnico().getNombre();
			String hora=apartados.get(i).getHorario().getHora();
	   		apartadosAUX.setHorario(actividad+" "+sala+" "+nombre+" "+hora);   			
   			apartadosDTO.add(apartadosAUX);
   		}
   		return ResponseEntity.ok(apartadosDTO);
   	}
   	
   	/**
   	 * Metodo que muestra solo un miembro
   	 * @param Id es el id del miembro que se quiere mostrar, en caso de no encontrarlo genera un RuntimeException
   	 * @return El miembro con el id 
   	 */
   	@GetMapping({"/obtenerAPA","/obtenerAPA{id}"})
       public ResponseEntity<?> getHorario(@RequestParam UUID id){
   			try {
   	   			CAApartados apartados=apartadosService.getOne(id);
				ApartadosDTO apartadosAUX=new ApartadosDTO();
	   			apartadosAUX.setConteo(apartados.getConteo());
	   			
	   			/*List<String> texto=new ArrayList<String>();
	   			for(int i=0;i<apartados.getHorario().size();i++) {
	   				String actividad=apartados.getHorario().get(i).getActividad().getNombre();
	   				String sala=apartados.getHorario().get(i).getSala().getNombre();
	   				String nombre=apartados.getHorario().get(i).getTecnico().getNombre();
	   				String hora=apartados.getHorario().get(i).getHora();
	   				texto.add(actividad+" "+sala+" "+nombre+" "+hora);	
	   			}*/
	   			String actividad=apartados.getHorario().getActividad().getNombre();
   				String sala=apartados.getHorario().getSala().getNombre();
   				String nombre=apartados.getHorario().getTecnico().getNombre();
   				String hora=apartados.getHorario().getHora();
	   			apartadosAUX.setHorario(actividad+" "+sala+" "+nombre+" "+hora);
	   			apartadosAUX.setId(apartados.getId());
				return ResponseEntity.ok(apartadosAUX);
			}catch(NoResultException e) {
					return new ResponseEntity<>("No se encontraron resultados", HttpStatus.NOT_FOUND);
			}	
       }//fin del metodo

   	
   	/**
   	 * Metodo que modifica un miembro ya existente en la base de datos (el miembro debe existir sino sera creado uno nuevo)
   	 * @param miembro es el objecto miembro que se quiere modificar
   	 * @return objeto miembro ya modificado
   	 */


    @PreAuthorize("hasRole('ADMIN')")
   	@RequestMapping(value="actualizarAparato", method=RequestMethod.PATCH)
   	public ResponseEntity<String> actualizarApartado(@RequestBody CAApartados miApartado){
    	CAApartados actualizarApartado = apartadosService.getOne(miApartado.getId());
   		if(actualizarApartado!=null){   			
   			actualizarApartado.setActivo(miApartado.isActivo());
   			actualizarApartado.setConteo(miApartado.getConteo());
   			actualizarApartado.setHorario(horarioService.getOne(miApartado.getHorario().getId()).get());
   			/*List<CAHorario> horarios=new ArrayList<CAHorario>();
   			for (int i=0;i<miApartado.getHorario().size();i++) {
   				CAHorario horario=horarioService.getOne(miApartado.getId()).get();
   				horarios.add(horario);
   	   		}
   			actualizarApartado.setHorario(horarios);*/   			
   			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
   			String username;
   			if (principal instanceof UserDetails) {
   				username = ((UserDetails)principal).getUsername();
   			} else {
   			  	username = principal.toString();
   			}
   			actualizarApartado.setUpdatedBy(username);
   			
   			apartadosService.save(actualizarApartado);
   			return ResponseEntity.ok("Apartado  actualizado correctamente");
   		}
   		else
   		{
   			return ResponseEntity.notFound().build();
   		}
   	}
   
	@RequestMapping(value="crearApartados", method=RequestMethod.POST)
   	@Transactional(rollbackFor = SQLException.class)
	public ResponseEntity<String> crearApartados(@RequestBody(required = false) Body body) {
		
   		if(body==null) {
   			SimpleDateFormat print = new SimpleDateFormat("yyyy-MM-dd");
			        
   			List<CAHorario> horarios=horarioService.list();
   			for(int i=0;i<horarios.size();i++) {
   				
   				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
   				Date startDate = new Date(),endDate = new Date();
				try {
					startDate = formatter.parse(horarios.get(i).getPeriodoInicio());
	   				endDate = formatter.parse(horarios.get(i).getPeriodoFinal());
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
   					if(horarios.get(i).isLunes() && dayOfWeek==2) {
	   					CAApartados apartado=new CAApartados(); 
	   	   	   			apartado.setActivo(true);
	   	   				apartado.setConteo(0);
	   	   				apartado.setDia(print.format(date));
	   	   				apartado.setCreated(new Date());
	   	   				apartado.setCreatedBy("admin");
	   	   				apartado.setHorario(horarios.get(i));
	   	   				apartado.setUpdated(new Date());
	   	   				apartado.setUpdatedBy("admin");
	   	   				apartadosService.save(apartado);
	   				}if(horarios.get(i).isMartes() && dayOfWeek==3) {
	   					CAApartados apartado=new CAApartados();
	   	   	   			apartado.setActivo(true);
	   	   				apartado.setConteo(0);
	   	   				apartado.setDia(print.format(date));
	   	   				apartado.setCreated(new Date());
	   	   				apartado.setCreatedBy("admin");
	   	   				apartado.setHorario(horarios.get(i));
	   	   				apartado.setUpdated(new Date());
	   	   				apartado.setUpdatedBy("admin");
	   	   				apartadosService.save(apartado);
	   				}if(horarios.get(i).isMiercoles() && dayOfWeek==4) {
	   					CAApartados apartado=new CAApartados();
	   	   	   			apartado.setActivo(true);
	   	   				apartado.setConteo(0);
	   	   				apartado.setDia(print.format(date));
	   	   				apartado.setCreated(new Date());
	   	   				apartado.setCreatedBy("admin");
	   	   				apartado.setHorario(horarios.get(i));
	   	   				apartado.setUpdated(new Date());
	   	   				apartado.setUpdatedBy("admin");
	   	   				apartadosService.save(apartado);
	   				}if(horarios.get(i).isJueves() && dayOfWeek==5) {
	   					CAApartados apartado=new CAApartados();
	   	   	   			apartado.setActivo(true);
	   	   				apartado.setConteo(0);
	   	   				apartado.setDia(print.format(date));
	   	   				apartado.setCreated(new Date());
	   	   				apartado.setCreatedBy("admin");
	   	   				apartado.setHorario(horarios.get(i));
	   	   				apartado.setUpdated(new Date());
	   	   				apartado.setUpdatedBy("admin");
	   	   				apartadosService.save(apartado);
	   				}if(horarios.get(i).isViernes() && dayOfWeek==6) {
	   					CAApartados apartado=new CAApartados();
	   	   	   			apartado.setActivo(true);
	   	   				apartado.setConteo(0);
	   	   				apartado.setDia(print.format(date));
	   	   				apartado.setCreated(new Date());
	   	   				apartado.setCreatedBy("admin");
	   	   				apartado.setHorario(horarios.get(i));
	   	   				apartado.setUpdated(new Date());
	   	   				apartado.setUpdatedBy("admin");
	   	   				apartadosService.save(apartado);
	   				}if(horarios.get(i).isSabado() && dayOfWeek==7) {
	   					CAApartados apartado=new CAApartados();
	   	   	   			apartado.setActivo(true);
	   	   				apartado.setConteo(0);
	   	   				apartado.setDia(print.format(date));
	   	   				apartado.setCreated(new Date());
	   	   				apartado.setCreatedBy("admin");
	   	   				apartado.setHorario(horarios.get(i));
	   	   				apartado.setUpdated(new Date());
	   	   				apartado.setUpdatedBy("admin");
	   	   				apartadosService.save(apartado);
	   				}if(horarios.get(i).isDomingo() && dayOfWeek==1) {
	   					CAApartados apartado=new CAApartados();
	   	   	   			apartado.setActivo(true);
	   	   				apartado.setConteo(0);
	   	   				apartado.setDia(print.format(date));
	   	   				apartado.setCreated(new Date());
	   	   				apartado.setCreatedBy("admin");
	   	   				apartado.setHorario(horarios.get(i));
	   	   				apartado.setUpdated(new Date());
	   	   				apartado.setUpdatedBy("admin");
	   	   				apartadosService.save(apartado);
	   				}
   				}
   			}
				return new ResponseEntity<String>("Ok", HttpStatus.OK);  
   			
   		}else {
   			
   			JSONObject json =new JSONObject();
   			CAHorario horario1=horarioService.getOne(body.getId()).get();		
   			CAApartados apartado=apartadosService.getHorario(body.getId(),body.getDia());
   	        
   	        Session currentSession = entityManager.unwrap(Session.class);
   	        

	   		CAApartadosUsuario apartadosUsuario=new CAApartadosUsuario();
	   		int paga=apartado.getHorario().getActividad().getPaga();
   	        if(paga>0) {
		   		List<PaseUsuario> paseUsuario=this.getPase(body.getUsuario());
		   		boolean ban=false;
		   		int k = 0;
		   		for(int i=0;i<paseUsuario.size();i++) {
		   			if(paseUsuario.get(i).getIdProd()==paga) {
		   				if(paseUsuario.get(i).getDisponibles()>0) {
		   					paseUsuario.get(i).setDisponibles(paseUsuario.get(i).getDisponibles()-1);
		   					paseUsuarioService.save(paseUsuario.get(i));
		   					k=i;
		   					ban=true;
		   					break;
		   				}
		   			}
		   		}try {
		   			
			   		if(ban==true) {
			   			/*try {
				   			tiempoMenorHora(apartado);			   				
			   			}catch(ParseException e) {
			   				json.put("Respuesta", "No se puede resevar, falta menos de una hora para iniciar");
			   				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	
			   				return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
			   			}*/
			   			
			   			apartadosUsuario=apartadosUsuarioService.crearApartado(body, horario1, apartado, apartadosUsuario);
			   			PaseConsumido paseConsumido=new PaseConsumido();
			   			paseConsumido.setApartadosUsuario(apartadosUsuario);
			   			paseConsumido.setPaseUsuario(paseUsuario.get(k));
				   		paseConsumidoService.save(paseConsumido);			
		   				json.put("Respuesta", "Se aparto la Clase Correctamente");
		   			
		   				return new ResponseEntity<String>(json.toString(), HttpStatus.OK); 			   			
			   		}else {
						throw new Exception("El usuario no tiene pases para esta clase");
			   		}
		   		} catch (IOException  e) {
	   				json.put("Respuesta", "Ya tiene apartada esta clase");
	   				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	   				
	   			
	   				return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
	   			} catch (RuntimeException  e) {
	   				json.put("Respuesta", "No hay cupo disponible");
	   				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	   			
	   				return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
	   			} catch (Exception e) {
	   				json.put("Respuesta", "No Tiene Pases para Apartar esta cita");
	   				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	   				
	   				return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
				}
	   		}else {
	   			Cliente cliente=clienteService.findById(body.getUsuario());
	   			if(cliente.getClub().getIdClub()==4) {
	   				String actividad=horario1.getActividad().getNombre();
	   				if(actividad.equals("GIMNASIO") || actividad.equals("CROSSFIT")) {
				   		paseUsuarioService.cancelarPasesVencidos(body.getUsuario());
				   		paseUsuarioService.activarPases(body.getUsuario());
		   				List<PaseUsuario> paseUsuario=this.getPase(body.getUsuario());
		   				paseUsuario=paseUsuarioService.getPasesGimnasio(body.getUsuario());
				   		
		   				try {
		   					if(paseUsuario.isEmpty()) {
		   						throw new Exception("El usuario no tiene pases para GIMNASIO");
		   					}
		   				}catch(Exception e){
			   				json.put("Respuesta", "El usuario no tiene pases para GIMNASIO");
			   				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			   			
			   				return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
		   				}
		   				
		   			}else if(actividad.equals("NADO CARRIL 1") || actividad.equals("NADO CARRIL 2") || actividad.equals("NADO CARRIL 3") ) {
				   		paseUsuarioService.cancelarPasesVencidos(body.getUsuario());
				   		paseUsuarioService.activarPases(body.getUsuario());
		   				List<PaseUsuario> paseUsuario=this.getPase(body.getUsuario());
		   				paseUsuario=paseUsuarioService.getPasesAlberca(body.getUsuario());
		   				
		   				try {
		   					if(paseUsuario.isEmpty()) {
		   						throw new Exception("El usuario no tiene pases para ALBERCA");
		   					}
		   				}catch(Exception e){
			   				json.put("Respuesta", "El usuario no tiene pases para ALBERCA");
			   				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			   			
			   				return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
		   				}
		   				int idVenta=paseUsuario.get(0).getIdVentaDetalle();
		   				
		   				Query<?> clasesDia = currentSession.createNativeQuery("select case  WHEN pase_usuario.id_prod=1847 then 2 WHEN "
		   						+ "pase_usuario.id_prod=1848 then 3 WHEN pase_usuario.id_prod=1849 then 5 end-(select "
		   						+ "count(ca_apartados_usuario.id) from ca_apartados_usuario join clases on "
		   						+ "ca_apartados_usuario.id_apartados=clases.id_apartados where idcliente="+body.getUsuario()+" and activo "
		   						+ "is true and nombre like '%NADO%' and dia between (select fecha from dias_semana where nombre='LUNES')AND"
		   						+ " (select fecha from dias_semana where nombre='DOMINGO')) from pase_usuario where "
		   						+ "id_venta_Detalle="+idVenta+";");
		   		        int limiteReservas=((BigInteger)clasesDia.getSingleResult()).intValue();
			   		     try {
			   					if(limiteReservas==0) {
			   						throw new Exception("Ha alcanzado su limite de reservas por semana");
			   					}
			   				}catch(Exception e){
				   				json.put("Respuesta", "El usuario ha alcanzado su limite de reservas por semana");
				   				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				   			
				   				return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
			   				}
		   			}else if(actividad.equals("TROTE/RUNNING")) {
				   		paseUsuarioService.cancelarPasesVencidos(body.getUsuario());
				   		paseUsuarioService.activarPases(body.getUsuario());
		   				List<PaseUsuario> paseUsuario=this.getPase(body.getUsuario());
		   				paseUsuario=paseUsuarioService.getPasesTrote(body.getUsuario());
				   		
		   				try {
		   					if(paseUsuario.isEmpty()) {
		   						throw new Exception("El usuario no tiene pases para la pista de trote");
		   					}
		   				}catch(Exception e){
			   				json.put("Respuesta", "El usuario no tiene pases para la pista de trote");
			   				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			   			
			   				return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
		   				}
		   				
		   			}
	   			}
	   			
	   			try {
	   				/*try {
			   			tiempoMenorHora(apartado);			   				
		   			}catch(ParseException e) {
		   				json.put("Respuesta", "No se puede resevar, falta menos de una hora para iniciar");
		   				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	
		   				return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
		   			}*/
	   				
	   				if(cliente.getClub().getIdClub()!=4) {
	   					currentSession = entityManager.unwrap(Session.class);
			   	        Query<?>clasesDia = currentSession.createNativeQuery("select count(*) from ca_apartados_usuario join ca_apartados on"
			   	        		+ " ca_apartados.id_apartados=ca_apartados_usuario.id_apartados where idcliente="+body.getUsuario()+""
			   	        				+ " and cast(dia as date)='"+apartado.getDia()+"' and ca_apartados_usuario.activo is true;");
			   	        BigInteger cantidad=(BigInteger)clasesDia.getSingleResult();
			   	        try {
			   	        	if(cantidad.intValue()+1>4) {
			   	        		throw new ParseException("No se pueden reservar mas de 4 clases por día", 0);
			   	        	}
			   	        }catch(ParseException e) {
			   	        	json.put("Respuesta", "No se pueden reservar mas de 4 clases por día");
			   				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	
			   				return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT);
			   	        }
	   				}
	   				apartadosUsuarioService.crearApartado(body, horario1, apartado, apartadosUsuario);
	   				json.put("Respuesta", "Se aparto la Clase Correctamente");
	   			
	   				return new ResponseEntity<String>(json.toString(), HttpStatus.OK); 
	   			} catch (IOException  e) {
	   				json.put("Respuesta", "Este usuario ya aparto esta clase");
	   				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	   			
	   				return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
	   			} catch (RuntimeException  e) {
	   				json.put("Respuesta", "No hay cupo disponible");
	   				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	   				
	   			
	   				return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
	   			} 
	   		}
   		}
	}

	
	//Utiliza el idApartado
	@RequestMapping(value="crearReserva", method=RequestMethod.POST)
   	@Transactional(rollbackFor = SQLException.class)
	public ResponseEntity<String> crearReserva(@RequestBody(required = false) Body body) {
		
		JSONObject json =new JSONObject();
		CAApartados apartado=apartadosService.getOne(body.getId());
		
		CAHorario horario1=apartado.getHorario();
        
        Session currentSession = entityManager.unwrap(Session.class);
        

   		CAApartadosUsuario apartadosUsuario=new CAApartadosUsuario();
   		int paga=apartado.getHorario().getActividad().getPaga();
        if(paga>0) {
	   		List<PaseUsuario> paseUsuario=this.getPase(body.getUsuario());
	   		boolean ban=false;
	   		int k = 0;
	   		for(int i=0;i<paseUsuario.size();i++) {
	   			if(paseUsuario.get(i).getIdProd()==paga) {
	   				if(paseUsuario.get(i).getDisponibles()>0) {
	   					paseUsuario.get(i).setDisponibles(paseUsuario.get(i).getDisponibles()-1);
	   					paseUsuarioService.save(paseUsuario.get(i));
	   					k=i;
	   					ban=true;
	   					break;
	   				}
	   			}
	   		}try {
	   			
		   		if(ban==true) {
		   			
		   			apartadosUsuario=apartadosUsuarioService.crearApartado(body, horario1, apartado, apartadosUsuario);
		   			PaseConsumido paseConsumido=new PaseConsumido();
		   			paseConsumido.setApartadosUsuario(apartadosUsuario);
		   			paseConsumido.setPaseUsuario(paseUsuario.get(k));
			   		paseConsumidoService.save(paseConsumido);			
	   				json.put("Respuesta", "Se aparto la Clase Correctamente");
	   			
	   				confirmarAsistencia(body);
	   				return new ResponseEntity<String>(json.toString(), HttpStatus.OK); 			   			
		   		}else {
					throw new Exception("El usuario no tiene pases para esta clase");
		   		}
	   		} catch (IOException  e) {
   				json.put("Respuesta", "Ya tiene apartada esta clase");
   				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
   				
   			
   				return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
   			} catch (RuntimeException  e) {
   				json.put("Respuesta", "No hay cupo disponible");
   				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
   			
   				return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
   			} catch (Exception e) {
   				json.put("Respuesta", "No Tiene Pases para Apartar esta cita");
   				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
   				
   			
   				
   				return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
			}
   		}else {
   			Cliente cliente=clienteService.findById(body.getUsuario());
   			if(cliente.getClub().getIdClub()==4) {
   				String actividad=horario1.getActividad().getNombre();
   				if(actividad.equals("GIMNASIO")  || actividad.equals("CROSSFIT")) {
			   		paseUsuarioService.cancelarPasesVencidos(body.getUsuario());
			   		paseUsuarioService.activarPases(body.getUsuario());
	   				List<PaseUsuario> paseUsuario=this.getPase(body.getUsuario());
	   				paseUsuario=paseUsuarioService.getPasesGimnasio(body.getUsuario());
			   		
	   				try {
	   					if(paseUsuario.isEmpty()) {
	   						throw new Exception("El usuario no tiene pases para GIMNASIO");
	   					}
	   				}catch(Exception e){
		   				json.put("Respuesta", "El usuario no tiene pases para GIMNASIO");
		   				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		   			
		   				return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
	   				}
	   				
	   			}else if(actividad.equals("NADO CARRIL 1") || actividad.equals("NADO CARRIL 2") || actividad.equals("NADO CARRIL 3") ) {
			   		paseUsuarioService.cancelarPasesVencidos(body.getUsuario());
			   		paseUsuarioService.activarPases(body.getUsuario());
	   				List<PaseUsuario> paseUsuario=this.getPase(body.getUsuario());
	   				paseUsuario=paseUsuarioService.getPasesAlberca(body.getUsuario());
			   		
	   				try {
	   					if(paseUsuario.isEmpty()) {
	   						throw new Exception("El usuario no tiene pases para ALBERCA");
	   					}
	   				}catch(Exception e){
		   				json.put("Respuesta", "El usuario no tiene pases para ALBERCA");
		   				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		   			
		   				return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
	   				}
	   				int idVenta=paseUsuario.get(0).getIdVentaDetalle();
	   				
	   				Query<?> clasesDia = currentSession.createNativeQuery("select case  WHEN pase_usuario.id_prod=1847 then 2 WHEN "
	   						+ "pase_usuario.id_prod=1848 then 3 WHEN pase_usuario.id_prod=1849 then 5 end-(select "
	   						+ "count(ca_apartados_usuario.id) from ca_apartados_usuario join clases on "
	   						+ "ca_apartados_usuario.id_apartados=clases.id_apartados where idcliente="+body.getUsuario()+" and activo "
	   						+ "is true and nombre like '%NADO%' and dia between (select fecha from dias_semana where nombre='LUNES')AND"
	   						+ " (select fecha from dias_semana where nombre='DOMINGO')) from pase_usuario where "
	   						+ "id_venta_Detalle="+idVenta+";");
	   		        int limiteReservas=((BigInteger)clasesDia.getSingleResult()).intValue();
	   		        try {
	   					if(limiteReservas==0) {
	   						throw new Exception("Ha alcanzado su limite de reservas por semana");
	   					}
	   				}catch(Exception e){
		   				json.put("Respuesta", "El usuario ha alcanzado su limite de reservas por semana");
		   				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		   			
		   				return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
	   				}
	   				
	   			}else if(actividad.equals("TROTE/RUNNING")) {
			   		paseUsuarioService.cancelarPasesVencidos(body.getUsuario());
			   		paseUsuarioService.activarPases(body.getUsuario());
	   				List<PaseUsuario> paseUsuario=this.getPase(body.getUsuario());
	   				paseUsuario=paseUsuarioService.getPasesTrote(body.getUsuario());
			   		
	   				try {
	   					if(paseUsuario.isEmpty()) {
	   						throw new Exception("El usuario no tiene pases para la pista de trote");
	   					}
	   				}catch(Exception e){
		   				json.put("Respuesta", "El usuario no tiene pases para la pista de trote");
		   				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		   			
		   				return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
	   				}
	   				
	   			}
   			}
   			
   			try {
   				
   				if(cliente.getClub().getIdClub()!=4) {
   					currentSession = entityManager.unwrap(Session.class);
		   	        Query<?>clasesDia = currentSession.createNativeQuery("select count(*) from ca_apartados_usuario join ca_apartados on"
		   	        		+ " ca_apartados.id_apartados=ca_apartados_usuario.id_apartados where idcliente="+body.getUsuario()+""
		   	        				+ " and cast(dia as date)='"+apartado.getDia()+"' and ca_apartados_usuario.activo is true;");
		   	        BigInteger cantidad=(BigInteger)clasesDia.getSingleResult();
		   	        try {
		   	        	if(cantidad.intValue()+1>4) {
		   	        		throw new ParseException("No se pueden reservar mas de 4 clases por día", 0);
		   	        	}
		   	        }catch(ParseException e) {
		   	        	json.put("Respuesta", "No se pueden reservar mas de 4 clases por día");
		   				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	
		   				return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT);
		   	        }
   				}
   				apartadosUsuarioService.crearApartado(body, horario1, apartado, apartadosUsuario);
   				json.put("Respuesta", "Se aparto la Clase Correctamente");
   			

   				confirmarAsistencia(body);
   				return new ResponseEntity<String>(json.toString(), HttpStatus.OK); 
   			} catch (IOException  e) {
   				json.put("Respuesta", "Este usuario ya aparto esta clase");
   				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
   			
   				return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
   			} catch (RuntimeException  e) {
   				json.put("Respuesta", "No hay cupo disponible");
   				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
   				
   			
   				return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
   			} 
   		}
	}
	
	@RequestMapping(value="cancelarApartado", method=RequestMethod.POST)
   	@Transactional(rollbackFor = SQLException.class)
	public ResponseEntity<String> cancelarApartados(@RequestBody Body body) {
		JSONObject json =new JSONObject();
		CAApartados apartado=apartadosService.getHorario(body.getId(),body.getDia());
		CAApartadosUsuario apartadosUsuario=apartadosUsuarioService.getOne(body.getUsuario(),apartado.getId());
		CAApartados apartados=apartadosService.getOne(apartadosUsuario.getApartados());
		try {
			tiempoMenorHora(apartados);
		} catch (ParseException e) {
   			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
   			json.put("Respuesta", "Ya no se puede cancelar esta cita");
   			
   			
   			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
		}
		int paga=apartado.getHorario().getActividad().getPaga();
		if(paga!=0) {
	   		List<PaseUsuario> paseUsuario=paseUsuarioService.getByIdCliente(body.getUsuario());
	   	  
	   		boolean ban=false;
	   		int k = 0;
	   		for(int i=0;i<paseUsuario.size();i++) {
	   			if(paseUsuario.get(i).getIdProd()==paga) {
   					paseUsuario.get(i).setDisponibles(paseUsuario.get(i).getDisponibles()+1);
   					paseUsuarioService.save(paseUsuario.get(i));
   					k=i;
   					ban=true;
   					break;
	   			}
	   		}
	   		
	   		if(ban==true) {	  
	   			try {
		   			PaseConsumido paseConsumido=paseConsumidoService.getOne(apartadosUsuario, paseUsuario.get(k));
		   			if(paseConsumido.getFechaRedencion()!=null) {
						throw new ParseException("El cliente ya asistio a esta clase", 0);
		   			}else {
		   				paseConsumido.setFechaRedencion(new Date(0));
		   				paseConsumidoService.save(paseConsumido);
		   			}
	   			}	 catch(ParseException e) {
	   	   			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	   	   			json.put("Respuesta", "El cliente ya redimio su pase para esta clase");
	   	   			
	   	   			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
	   			}
	   		}
   		}
		try {
			Cliente cliente=apartadosUsuarioService.getByIdCliente(body.getUsuario());
			if(cliente==null) {
   				throw new FileNotFoundException("Este Cliente no tiene apartados ");
			}
			apartado.setConteo(apartado.getConteo()-1);
   			if(-1==apartado.getConteo()) {
   				throw new IOException("Sala Vacía");
   			}
   			boolean ban=apartadosUsuarioService.delete(cliente,apartado);
   			if(ban==false)
   				throw new FileNotFoundException("Error cancelando la clase ");
   			json.put("Respuesta", "Se cancelo la Clase Correctamente");
   			
   			return new ResponseEntity<String>(json.toString(), HttpStatus.OK); 
   		} catch (FileNotFoundException  e) {
   			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
   			
   			json.put("Respuesta", "No tiene apartados");
   			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
   		}catch(IOException e) {
   			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
   			json.put("Respuesta", "Sala Vacía");
   			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
   		} 
	}

	@RequestMapping(value="cancelarReserva", method=RequestMethod.POST)
   	@Transactional(rollbackFor = SQLException.class)
	public ResponseEntity<String> cancelarReserva(@RequestBody Body body) {
		
		JSONObject json =new JSONObject();
		CAApartados apartado=apartadosService.getOne(body.getId());
		CAApartadosUsuario apartadosUsuario=apartadosUsuarioService.getOne(body.getUsuario(),apartado.getId());
		int paga=apartado.getHorario().getActividad().getPaga();
		
		if(paga!=0) {
	   		List<PaseUsuario> paseUsuario=paseUsuarioService.getByIdCliente(body.getUsuario());
	   	  
	   		boolean ban=false;
	   		int k = 0;
	   		for(int i=0;i<paseUsuario.size();i++) {
	   			if(paseUsuario.get(i).getIdProd()==paga) {
   					paseUsuario.get(i).setDisponibles(paseUsuario.get(i).getDisponibles()+1);
   					paseUsuarioService.save(paseUsuario.get(i));
   					k=i;
   					ban=true;
   					break;
	   			}
	   		}
	   		
	   		if(ban==true) {	  
	   			try {
		   			PaseConsumido paseConsumido=paseConsumidoService.getOne(apartadosUsuario, paseUsuario.get(k));
		   			if(paseConsumido.getFechaRedencion()!=null) {
						throw new ParseException("El cliente ya asistio a esta clase", 0);
		   			}else {
		   				paseConsumido.setFechaRedencion(new Date(0));
		   				paseConsumidoService.save(paseConsumido);
		   			}
	   			}	 catch(ParseException e) {
	   	   			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	   	   			json.put("Respuesta", "El cliente ya redimio su pase para esta clase");
	   	   			
	   	   			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
	   			}
	   		}
   		}
		try {
			Cliente cliente=apartadosUsuarioService.getByIdCliente(body.getUsuario());
			if(cliente==null) {
   				throw new FileNotFoundException("Este Cliente no tiene apartados ");
			}
			apartado.setConteo(apartado.getConteo()-1);
   			if(-1==apartado.getConteo()) {
   				throw new IOException("Sala Vacía");
   			}
   			boolean ban=apartadosUsuarioService.delete(cliente,apartado);
   			if(ban==false)
   				throw new FileNotFoundException("Error cancelando la clase ");
   			json.put("Respuesta", "Se cancelo la Clase Correctamente");
   			
   			return new ResponseEntity<String>(json.toString(), HttpStatus.OK); 
   		} catch (FileNotFoundException  e) {
   			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
   			
   			json.put("Respuesta", "No tiene apartados");
   			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
   		}catch(IOException e) {
   			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
   			json.put("Respuesta", "Sala Vacía");
   			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
   		} 
	}

	
	public void tiempoMenorHora(CAApartados apartados) throws ParseException {
		Date fechaActual=new Date();
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String rango =apartados.getHorario().getRango();
		String[] hora=rango.split("-"); 
		Date fecha = formato.parse(apartados.getDia()+" "+hora[0]);
		long diff=fecha.getTime()-fechaActual.getTime();
		if(TimeUnit.MILLISECONDS.toHours(diff)<=0) {
			throw new ParseException("No se puede cancelar", 0);
		}
	}
   	
	@RequestMapping(value="cancelarApartados", method=RequestMethod.POST)
   	@Transactional(rollbackFor = SQLException.class)
	public ResponseEntity<String> cancelarApartadoss(@RequestBody Body body) {
		JSONObject json =new JSONObject();
		CAApartados apartado=apartadosService.getOne(body.getId());

		CAApartadosUsuario apartadosUsuario=apartadosUsuarioService.getOne(body.getUsuario(),apartado.getId());
		CAApartados apartados=apartadosService.getOne(apartadosUsuario.getApartados());
		try {
			tiempoMenorHora(apartados);
		} catch (ParseException e) {
   			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
   			json.put("Respuesta", "Ya no se puede cancelar esta cita");
   			
   			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
		}
		int paga=apartado.getHorario().getActividad().getPaga();
		
		if(paga!=0) {
	   		List<PaseUsuario> paseUsuario=paseUsuarioService.getByIdCliente(body.getUsuario());
	   	  
	   		boolean ban=false;
	   		int k = 0;
	   		for(int i=0;i<paseUsuario.size();i++) {
	   			if(paseUsuario.get(i).getIdProd()==paga) {
   					paseUsuario.get(i).setDisponibles(paseUsuario.get(i).getDisponibles()+1);
   					paseUsuario.set(i, paseUsuarioService.save(paseUsuario.get(i)));
   					
   					k=i;
   					ban=true;
   					break;
	   			}
	   		}
	   		
	   		if(ban==true) {	   			
	   			try {
		   			PaseConsumido paseConsumido=paseConsumidoService.getOne(apartadosUsuario, paseUsuario.get(k));
		   			if(paseConsumido.getFechaRedencion()!=null) {
						throw new ParseException("El cliente ya asistio a esta clase", 0);
		   			}else {
		   				paseConsumido.setFechaRedencion(new Date(0));
		   				paseConsumidoService.save(paseConsumido);
		   			}
	   			}	 catch(ParseException e) {
	   	   			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	   	   			json.put("Respuesta", "El cliente ya redimio su pase para esta clase");
	   	   			
	   	   	
	   	   			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
	   			}								   			
	   		}
   		}
		try {
			Cliente cliente=apartadosUsuarioService.getByIdCliente(body.getUsuario());
			if(cliente==null) {
   				throw new FileNotFoundException("Este Cliente no tiene apartados ");
			}
			apartado.setConteo(apartado.getConteo()-1);
   			if(-1==apartado.getConteo()) {
   				throw new IOException("Sala Vacía");
   			}
   			boolean ban=apartadosUsuarioService.delete(cliente,apartado);
   			if(ban==false)
   				throw new FileNotFoundException("Error cancelando la clase ");
   			json.put("Respuesta", "Se cancelo la Clase Correctamente");
   			
   			return new ResponseEntity<String>(json.toString(), HttpStatus.OK); 
   		} catch (FileNotFoundException  e) {
   			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
   			json.put("Respuesta", "No tiene apartados");
   			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
   		}catch(IOException e) {
   			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
   			json.put("Respuesta", "Sala Vacía");
   			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
   		} 
	}
	
	//_---------------------------------------------------WEB SERVICE TIPO ACTIVIDAD-------------------------------------------

	/**
	 * Metodo que muestra todos los Miembros almacenados en la base de datos
	 * @return lista de Miembro
	 */
	@GetMapping({"/obtenerTipoActividad","/obtenerTipoActividad{activo}"})
	public ResponseEntity<?> obtenerTipoActividad(@RequestParam(required = false) String activo)
	{
		List<CATipoActividad> tipoActividad;
		if(activo!=null) {
			try {
				 tipoActividad = tipoActividadService.getByActivo(Boolean.parseBoolean(activo));
			}catch(NoSuchElementException e) {
				return new ResponseEntity<>("No se encontraron resultados", HttpStatus.CONFLICT);
			}
			
		}else {
			tipoActividad = tipoActividadService.list();			
		}
		List<TipoActividadDTO> tipoActividadDTO = new ArrayList<TipoActividadDTO>();
		for(int i=0;i<tipoActividad.size();i++) {
			TipoActividadDTO tipoactividadaux=new TipoActividadDTO();
			tipoactividadaux.setColor(tipoActividad.get(i).getColor());
			tipoactividadaux.setNombre(tipoActividad.get(i).getNombre());		
			tipoActividadDTO.add(tipoactividadaux);
		}
		return ResponseEntity.ok(tipoActividadDTO);
	}
	
	/**
	 * Metodo que muestra solo un miembro
	 * @param Id es el id del miembro que se quiere mostrar, en caso de no encontrarlo genera un RuntimeException
	 * @return El miembro con el id 
	 */
	@GetMapping({"/obtenerTA","/obtenerTA{nombre}"})
    public ResponseEntity<?> getTipoActividad(@RequestParam String nombre){
		Optional<CATipoActividad> OpcionalTipoActividad = tipoActividadService.getByNombre(nombre);
		if(OpcionalTipoActividad.isPresent())
		{
			CATipoActividad tipoActividad=OpcionalTipoActividad.get();
			TipoActividadDTO tipoActividadDTO=new TipoActividadDTO();
			tipoActividadDTO.setColor(tipoActividad.getColor());
			tipoActividadDTO.setNombre(tipoActividad.getNombre());
			return ResponseEntity.ok(tipoActividadDTO);
        }
		return new ResponseEntity<>("No se encontraron resultados", HttpStatus.NOT_FOUND);
    }//fin del metodo
	
	/**
	 * Metodo que añade a la base de datos un nuevo miembro
	 * @param miembro es el objeto miembro que se desea añadir, en caso de contar con el mismo id, actualiza los valores solamente
	 * @return el objeto miembro que fue almacenado
	 */

	@RequestMapping(value="crearTipoActividad", method=RequestMethod.POST)
	public ResponseEntity<?> crearTipoActividad(@RequestBody CATipoActividad miTipoActividad){
    	Session currentSession = entityManager.unwrap(Session.class);
		Query<CATipoActividad> listaClases = currentSession.createQuery("FROM CATipoActividad s where s.nombre=:o ", CATipoActividad.class);
		listaClases.setParameter("o",miTipoActividad.getNombre());

		if(!listaClases.list().isEmpty())
			return new ResponseEntity<>("Sala ya existe", HttpStatus.CONFLICT);
    	miTipoActividad.setActivo(true);
    	miTipoActividad.setCreated(new Date());
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
		  	username = principal.toString();
		}
		miTipoActividad.setCreatedBy(username);
		miTipoActividad.setUpdated(new Date());
		miTipoActividad.setUpdatedBy(username);
		tipoActividadService.save(miTipoActividad);
		return ResponseEntity.ok(miTipoActividad);
	}
	
	/**
	 * Metodo que modifica un miembro ya existente en la base de datos (el miembro debe existir sino sera creado uno nuevo)
	 * @param miembro es el objecto miembro que se quiere modificar
	 * @return objeto miembro ya modificado
	 */

    @PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="actualizarTipoActividad", method=RequestMethod.PATCH)
	public ResponseEntity<String> actualizarTipoActividad(@RequestBody CATipoActividad miTipoActividad)
	{
		Optional<CATipoActividad> OpcionalTipoActividad = tipoActividadService.getOne((UUID) miTipoActividad.getId());
		if(OpcionalTipoActividad.isPresent())
		{
			CATipoActividad actualizarTipoActividad = OpcionalTipoActividad.get();
			actualizarTipoActividad.setColor(miTipoActividad.getColor());
			actualizarTipoActividad.setActivo(miTipoActividad.isActivo());
			actualizarTipoActividad.setNombre(miTipoActividad.getNombre());
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String username;
			if (principal instanceof UserDetails) {
				username = ((UserDetails)principal).getUsername();
			} else {
			  	username = principal.toString();
			}
			actualizarTipoActividad.setUpdatedBy(username);
			
			tipoActividadService.save(actualizarTipoActividad);
			return ResponseEntity.ok("Sala actualizada correctamente");
		}
		else
		{
			return ResponseEntity.notFound().build();
		}
	}
    
    //------------------------------------------------------WEB SERVICE TECNICO---------------------------------------------

	/**
	 * Metodo que muestra todos los Miembros almacenados en la base de datos
	 * @return lista de Miembro
	 */
	@GetMapping({"/obtenerTecnico","/obtenerTecnico{activo}"})
	public ResponseEntity<?> obtenerTecnico(@RequestParam(required = false) String activo)
	{
		List<CATecnico> tecnico;
		if(activo!=null) {
			try {
				 tecnico = tecnicoService.getByActivo(Boolean.parseBoolean(activo));
			}catch(NoSuchElementException e) {
				return new ResponseEntity<>("No se encontraron resultados", HttpStatus.CONFLICT);
			}
			
		}else {
			tecnico = tecnicoService.list();			
		}
		return ResponseEntity.ok(tecnico);
	}
	
	/**
	 * Metodo que muestra solo un miembro
	 * @param Id es el id del miembro que se quiere mostrar, en caso de no encontrarlo genera un RuntimeException
	 * @return El miembro con el id 
	 */
	@GetMapping({"/obtenerTEC","/obtenerTEC{nombre}"})
    public ResponseEntity<?> getTecnico(@RequestParam String nombre){
		Optional<CATecnico> optionalTecnico = tecnicoService.getByNombre(nombre);
		if(optionalTecnico.isPresent())
		{
			CATecnico tecnico=optionalTecnico.get();
			TecnicoDTO tipoActividadDTO=new TecnicoDTO();
			tipoActividadDTO.setNombre(tecnico.getNombre());
			return ResponseEntity.ok(tipoActividadDTO);
        }
		return new ResponseEntity<>("No se encontraron resultados", HttpStatus.NOT_FOUND);
    }//fin del metodo
	
	/**
	 * Metodo que añade a la base de datos un nuevo miembro
	 * @param miembro es el objeto miembro que se desea añadir, en caso de contar con el mismo id, actualiza los valores solamente
	 * @return el objeto miembro que fue almacenado
	 */

	@RequestMapping(value="crearTecnico", method=RequestMethod.POST)
	public ResponseEntity<?> crearTecnico(@RequestBody CATecnico miTecnico)
	{
		JSONObject json=new JSONObject();
		try {
			miTecnico.setActivo(true);
	    	miTecnico.setCreated(new Date());
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String username;
			if (principal instanceof UserDetails) {
				username = ((UserDetails)principal).getUsername();
			} else {
			  	username = principal.toString();
			}
			miTecnico.setCreatedBy(username);
			miTecnico.setUpdated(new Date());
			miTecnico.setUpdatedBy(username);
			miTecnico=tecnicoService.save(miTecnico);
			return ResponseEntity.ok(miTecnico);
		}catch(Exception e) {
			e.printStackTrace();
			json.put("msg", "Ocurrio un error desconocido");
			return new ResponseEntity<>(json.toString(), HttpStatus.INTERNAL_SERVER_ERROR); 
		}
    	
	}
	
	/**
	 * Metodo que modifica un miembro ya existente en la base de datos (el miembro debe existir sino sera creado uno nuevo)
	 * @param miembro es el objecto miembro que se quiere modificar
	 * @return objeto miembro ya modificado
	 */

	@RequestMapping(value="actualizarTecnico", method=RequestMethod.PUT)
	public ResponseEntity<?> actualizarTecnico(@RequestBody CATecnico miTecnico)
	{
		JSONObject json=new JSONObject();
		try {
			Optional<CATecnico> optionalTecnico = tecnicoService.getOne((UUID) miTecnico.getId());
			if(optionalTecnico.isPresent())
			{
				CATecnico actualizarTecnico = optionalTecnico.get();
				actualizarTecnico.setNombre(miTecnico.getNombre());
				Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				String username;
				if (principal instanceof UserDetails) {
					username = ((UserDetails)principal).getUsername();
				} else {
				  	username = principal.toString();
				}
				actualizarTecnico.setUpdatedBy(username);
				
				actualizarTecnico=tecnicoService.save(actualizarTecnico);
				return ResponseEntity.ok(actualizarTecnico);
			}else {
				json.put("msg", "No se encontro el tecnico con ese id");
				return new ResponseEntity<>(json.toString(), HttpStatus.BAD_REQUEST); 
			}
		}catch(Exception e) {
			e.printStackTrace();
			json.put("msg", "Ocurrio un error desconocido");
			return new ResponseEntity<>(json.toString(), HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
	}
	@RequestMapping(value="deleteTecnico", method=RequestMethod.POST)
	public ResponseEntity<?> deleteTecnico(@RequestBody Body miTecnico)
	{
		JSONObject resp=new JSONObject();
		try{
			Optional<CATecnico> tecnicoOpcional = tecnicoService.getOne((UUID) miTecnico.getId());
			if(tecnicoOpcional.isPresent())
			{
				CATecnico actualizarTecnico = tecnicoOpcional.get();
				actualizarTecnico.setActivo(false);
				Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				String username;
				if (principal instanceof UserDetails) {
					username = ((UserDetails)principal).getUsername();
				} else {
				  	username = principal.toString();
				}
				actualizarTecnico.setUpdatedBy(username);
				actualizarTecnico.setUpdated(new Date());
				
				tecnicoService.save(actualizarTecnico);
				resp.put("msg", "el tecnico se ha eliminado");
				return ResponseEntity.ok(resp.toString());
			}else {
				resp.put("msg", "No se encontro el tecnico con ese id");
				return new ResponseEntity<>(resp.toString(), HttpStatus.BAD_REQUEST); 
			}
		}catch(Exception e) {
			e.printStackTrace();
			resp.put("msg", "Ocurrio un error desconocido");
			return new ResponseEntity<>(resp.toString(), HttpStatus.INTERNAL_SERVER_ERROR); 
		}

		
	}
    
    //---------------------------------------------------WEB SERVICE ACTIVIDAD--------------------------------------------
    
    /**
	 * Metodo que muestra todos los Miembros almacenados en la base de datos
	 * @return lista de Miembro
	 */
	@GetMapping({"/obtenerActividad","/obtenerActividad{activo}"})
	public ResponseEntity<?> obtenerActividad(@RequestParam(required = false) String activo)
	{
		List<CAActividad> actividad;
		if(activo!=null) {
			try {
				 actividad = actividadService.getByActivo(Boolean.parseBoolean(activo));
			}catch(NoSuchElementException e) {
				return new ResponseEntity<>("No se encontraron resultados", HttpStatus.CONFLICT);
			}
			
		}else {
			actividad = actividadService.list();			
		}
		
		return ResponseEntity.ok(actividad);
	}
	
	
	/**
	 * Metodo que añade a la base de datos un nuevo miembro
	 * @param miembro es el objeto miembro que se desea añadir, en caso de contar con el mismo id, actualiza los valores solamente
	 * @return el objeto miembro que fue almacenado
	 */

	@RequestMapping(value="crearActividad", method=RequestMethod.POST)
	public ResponseEntity<?> crearActividad(@RequestBody ActividadDTO miActividad)
	{
		CAActividad nuevaActividad=new CAActividad();
		nuevaActividad.setActivo(true);
		nuevaActividad.setCreated(new Date());
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
		  	username = principal.toString();
		}
		nuevaActividad.setCreatedBy(username);
		
		nuevaActividad.setDificultad(miActividad.getDificultad());
		nuevaActividad.setPaga(miActividad.getPaga());
		nuevaActividad.setSegmentacion(miActividad.isSegmentacion());
		nuevaActividad.setSobreescribir(miActividad.isSobreescribir());
		nuevaActividad.setTipoActividad(tipoActividadService.getOne(miActividad.getTipoActividad()).get());
		nuevaActividad.setCupo(miActividad.getCupo());
		nuevaActividad.setMax(miActividad.getMax());
		
		nuevaActividad.setNombre(miActividad.getNombre());
		nuevaActividad.setUpdated(new Date());
		nuevaActividad.setUpdatedBy(username);
		nuevaActividad = actividadService.save(nuevaActividad);
		return new ResponseEntity<>(nuevaActividad, HttpStatus.OK);
	}
	
	/**
	 * Metodo que modifica un miembro ya existente en la base de datos (el miembro debe existir sino sera creado uno nuevo)
	 * @param miembro es el objecto miembro que se quiere modificar
	 * @return objeto miembro ya modificado
	 */

	@RequestMapping(value="actualizarActividad", method=RequestMethod.PUT)
	public ResponseEntity<?> actualizarActividad(@RequestBody CAActividad miActividad)
	{
		JSONObject json=new JSONObject();
		try {
			Optional<CAActividad> optionalActividad = actividadService.getOne(miActividad.getId());
			if(optionalActividad.isPresent())
			{
				CAActividad actualizarActividad = optionalActividad.get();
				System.out.println(actualizarActividad);
				actualizarActividad.setDificultad(miActividad.getDificultad());
				actualizarActividad.setPaga(miActividad.getPaga());
				actualizarActividad.setSegmentacion(miActividad.isSegmentacion());
				actualizarActividad.setSobreescribir(miActividad.isSobreescribir());
				actualizarActividad.setNombre(miActividad.getNombre());
				actualizarActividad.setMax(miActividad.getMax());
				CATipoActividad tipoActividad=actualizarActividad.getTipoActividad();
				tipoActividad.setColor(miActividad.getTipoActividad().getColor());
				actualizarActividad.setTipoActividad(tipoActividad);
				Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				String username;
				if (principal instanceof UserDetails) {
					username = ((UserDetails)principal).getUsername();
				} else {
				  	username = principal.toString();
				}
				actualizarActividad.setUpdatedBy(username);
				actualizarActividad.setUpdated(new Date());
				actualizarActividad=actividadService.save(actualizarActividad);
				return ResponseEntity.ok(actualizarActividad);
			}else {
				json.put("msg", "No se encontro el tecnico con ese id");
				return new ResponseEntity<>(json.toString(), HttpStatus.NOT_FOUND); 
			}
		}catch(Exception e) {
			e.printStackTrace();
			json.put("msg", "Ocurrio un error desconocido");
			return new ResponseEntity<>(json.toString(), HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		
	}
	
	@RequestMapping(value="deleteActividad", method=RequestMethod.POST)
	public ResponseEntity<?> deleteActividad(@RequestBody Body miActividad)
	{
		JSONObject resp=new JSONObject();
		try{
			Optional<CAActividad> actividadOpcional = actividadService.getOne((UUID) miActividad.getId());
			if(actividadOpcional.isPresent())
			{
				CAActividad actualizarActividad = actividadOpcional.get();
				actualizarActividad.setActivo(false);
				Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				String username;
				if (principal instanceof UserDetails) {
					username = ((UserDetails)principal).getUsername();
				} else {
				  	username = principal.toString();
				}
				actualizarActividad.setUpdatedBy(username);
				actualizarActividad.setUpdated(new Date());
				
				actividadService.save(actualizarActividad);
				resp.put("msg", "la actividad se ha eliminado");
				return ResponseEntity.ok(resp.toString());
			}else {
				resp.put("msg", "No se encontro la actividad con ese id");
				return new ResponseEntity<>(resp.toString(), HttpStatus.BAD_REQUEST); 
			}
		}catch(Exception e) {
			e.printStackTrace();
			resp.put("msg", "Ocurrio un error desconocido");
			return new ResponseEntity<>(resp.toString(), HttpStatus.INTERNAL_SERVER_ERROR); 
		}

		
	}

    //-------------------------------------------------WEB SERVICE CA_HORARIO-----------------------------------------------
    
    /**
   	 * Metodo que muestra todos los Miembros almacenados en la base de datos
   	 * @return lista de Miembro
   	 */
   	@GetMapping({"/obtenerHorario","/obtenerHorario{activo}"})
   	public ResponseEntity<?> obtenerHorario(@RequestParam(required = false) String activo)
   	{
   		List<CAHorario> horario;
   		if(activo!=null) {
   			try {
   				 horario = horarioService.getByActivo(Boolean.parseBoolean(activo));
   			}catch(NoSuchElementException e) {
   				return new ResponseEntity<>("No se encontraron resultados", HttpStatus.CONFLICT);
   			}
   			
   		}else {
   			horario = horarioService.list();			
   		}
   		List<HorarioDTO> horarioDTO = new ArrayList<HorarioDTO>();
   		for(int i=0;i<horario.size();i++) {
   			HorarioDTO horarioAUX=new HorarioDTO();
   			horarioAUX.setActividad(horario.get(i).getActividad().getNombre());
   			horarioAUX.setDomingo(horario.get(i).isDomingo());
   			horarioAUX.setDuracion(horario.get(i).getDuracion());
   			horarioAUX.setHora(horario.get(i).getHora());
   			horarioAUX.setJueves(horario.get(i).isJueves());
   			horarioAUX.setLunes(horario.get(i).isLunes());
   			horarioAUX.setMartes(horario.get(i).isMartes());
   			horarioAUX.setMiercoles(horario.get(i).isMiercoles());
   			horarioAUX.setPeriodoFinal(horario.get(i).getPeriodoFinal());
   			horarioAUX.setPeriodoInicio(horario.get(i).getPeriodoInicio());
   			horarioAUX.setSabado(horario.get(i).isSabado());
   			horarioAUX.setSala(horario.get(i).getSala().getNombre());
   			horarioAUX.setTecnico(horario.get(i).getTecnico().getNombre());
   			horarioAUX.setViernes(horario.get(i).isViernes());
   			horarioAUX.setRango(horario.get(i).getRango());
   			horarioAUX.setDisponible(horario.get(i).isDisponible());
   			horarioDTO.add(horarioAUX);
   		}
   		return ResponseEntity.ok(horario);
   	}
   	
   	/**
   	 * Metodo que muestra solo un miembro
   	 * @param Id es el id del miembro que se quiere mostrar, en caso de no encontrarlo genera un RuntimeException
   	 * @return El miembro con el id 
   	 */
   	@GetMapping({"/obtenerHOR","/obtenerHOR{actividad}{sala}{tecnico}{hora}"})
       public ResponseEntity<?> getHorario(@RequestParam String actividad,@RequestParam String sala,@RequestParam String tecnico,@RequestParam String hora){
   		Session currentSession = entityManager.unwrap(Session.class);
		Query<CAHorario> listaHorario = currentSession.createQuery("FROM CAHorario h where h.actividad.nombre=:o and h.sala.nombre=:u and h.tecnico.nombre=:t and h.hora=:r", CAHorario.class);
		listaHorario.setParameter("o",actividad);
		listaHorario.setParameter("u",sala);
		listaHorario.setParameter("t",tecnico);
		listaHorario.setParameter("r",hora);
		try {
			CAHorario horario=listaHorario.getSingleResult();
			HorarioDTO horarioAUX=new HorarioDTO();
   			horarioAUX.setActividad(horario.getActividad().getNombre());
   			horarioAUX.setDomingo(horario.isDomingo());
   			horarioAUX.setDuracion(horario.getDuracion());
   			horarioAUX.setHora(horario.getHora());
   			horarioAUX.setJueves(horario.isJueves());
   			horarioAUX.setLunes(horario.isLunes());
   			horarioAUX.setMartes(horario.isMartes());
   			horarioAUX.setMiercoles(horario.isMiercoles());
   			horarioAUX.setPeriodoFinal(horario.getPeriodoFinal());
   			horarioAUX.setPeriodoInicio(horario.getPeriodoInicio());
   			horarioAUX.setSabado(horario.isSabado());
   			horarioAUX.setSala(horario.getSala().getNombre());
   			horarioAUX.setTecnico(horario.getTecnico().getNombre());
   			horarioAUX.setViernes(horario.isViernes());
   			horarioAUX.setRango(horario.getRango());
   			horarioAUX.setDisponible(horario.isDisponible());
			return ResponseEntity.ok(horarioAUX);
		}catch(NoResultException e) {
				return new ResponseEntity<>("No se encontraron resultados", HttpStatus.NOT_FOUND);
		}	
       }//fin del metodo
   	
   	/**
   	 * Metodo que añade a la base de datos un nuevo miembro
   	 * @param miembro es el objeto miembro que se desea añadir, en caso de contar con el mismo id, actualiza los valores solamente
   	 * @return el objeto miembro que fue almacenado
   	 */

   	@RequestMapping(value="crearHorario", method=RequestMethod.POST)
   	public ResponseEntity<?> crearHorario(@RequestBody HorarioDTO miHorario)
   	{
    	   //SELECT id FROM ca_sala ORDER BY RANDOM() limit 1
    	   
   		CAHorario nuevoHorario=new CAHorario();
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
   		
   		nuevoHorario.setActividad(actividadService.getByNombre(miHorario.getActividad()).get());
   		nuevoHorario.setDomingo(miHorario.isDomingo());
   		nuevoHorario.setDuracion(miHorario.getDuracion());
   		nuevoHorario.setHora(miHorario.getHora());
   		nuevoHorario.setJueves(miHorario.isJueves());
   		nuevoHorario.setLunes(miHorario.isLunes());
   		nuevoHorario.setMartes(miHorario.isMartes());
   		nuevoHorario.setMiercoles(miHorario.isMiercoles());
   		nuevoHorario.setPeriodoFinal(miHorario.getPeriodoFinal());
   		nuevoHorario.setPeriodoInicio(miHorario.getPeriodoInicio());
   		nuevoHorario.setSabado(miHorario.isSabado());
   		nuevoHorario.setRango(miHorario.getRango());
   		nuevoHorario.setDisponible(miHorario.isDisponible());
   		nuevoHorario.setSala(salaService.getSalaClub(miHorario.getSala(),miHorario.getClub()));
   		nuevoHorario.setTecnico(tecnicoService.getByNombre(miHorario.getTecnico()).get());
   		nuevoHorario.setViernes(miHorario.isViernes());
   		
   		nuevoHorario.setUpdated(new Date());
   		nuevoHorario.setUpdatedBy(username);
   		nuevoHorario = horarioService.save(nuevoHorario);
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
						CAApartados apartado=new CAApartados();
		   	   			apartado.setActivo(true);
		   				apartado.setConteo( 0);
		   				apartado.setDia(print.format(date));
		   				apartado.setCreated(new Date());
		   				apartado.setCreatedBy("admin");
		   				apartado.setHorario(nuevoHorario);
		   				apartado.setUpdated(new Date());
		   				apartado.setUpdatedBy("admin");
		   				apartadosService.save(apartado);
					}if(nuevoHorario.isMartes() && dayOfWeek==3) {
						CAApartados apartado=new CAApartados();
		   	   			apartado.setActivo(true);
		   				apartado.setConteo(0);
		   				apartado.setDia(print.format(date));
		   				apartado.setCreated(new Date());
		   				apartado.setCreatedBy("admin");
		   				apartado.setHorario(nuevoHorario);
		   				apartado.setUpdated(new Date());
		   				apartado.setUpdatedBy("admin");
		   				apartadosService.save(apartado);
					}if(nuevoHorario.isMiercoles() && dayOfWeek==4) {
						CAApartados apartado=new CAApartados();
		   	   			apartado.setActivo(true);
		   				apartado.setConteo(0);
		   				apartado.setDia(print.format(date));
		   				apartado.setCreated(new Date());
		   				apartado.setCreatedBy("admin");
		   				apartado.setHorario(nuevoHorario);
		   				apartado.setUpdated(new Date());
		   				apartado.setUpdatedBy("admin");
		   				apartadosService.save(apartado);
					}if(nuevoHorario.isJueves() && dayOfWeek==5) {
						CAApartados apartado=new CAApartados();
		   	   			apartado.setActivo(true);
		   				apartado.setConteo(0);
		   				apartado.setDia(print.format(date));
		   				apartado.setCreated(new Date());
		   				apartado.setCreatedBy("admin");
		   				apartado.setHorario(nuevoHorario);
		   				apartado.setUpdated(new Date());
		   				apartado.setUpdatedBy("admin");
		   				apartadosService.save(apartado);
					}if(nuevoHorario.isViernes() && dayOfWeek==6) {
						CAApartados apartado=new CAApartados();
		   	   			apartado.setActivo(true);
		   				apartado.setConteo(0);
		   				apartado.setDia(print.format(date));
		   				apartado.setCreated(new Date());
		   				apartado.setCreatedBy("admin");
		   				apartado.setHorario(nuevoHorario);
		   				apartado.setUpdated(new Date());
		   				apartado.setUpdatedBy("admin");
		   				apartadosService.save(apartado);
					}if(nuevoHorario.isSabado() && dayOfWeek==7) {
						CAApartados apartado=new CAApartados();
		   	   			apartado.setActivo(true);
		   				apartado.setConteo(0);
		   				apartado.setDia(print.format(date));
		   				apartado.setCreated(new Date());
		   				apartado.setCreatedBy("admin");
		   				apartado.setHorario(nuevoHorario);
		   				apartado.setUpdated(new Date());
		   				apartado.setUpdatedBy("admin");
		   				apartadosService.save(apartado);
					}if(nuevoHorario.isDomingo() && dayOfWeek==1) {
						CAApartados apartado=new CAApartados();
		   	   			apartado.setActivo(true);
		   				apartado.setConteo(0);
		   				apartado.setDia(print.format(date));
		   				apartado.setCreated(new Date());
		   				apartado.setCreatedBy("admin");
		   				apartado.setHorario(nuevoHorario);
		   				apartado.setUpdated(new Date());
		   				apartado.setUpdatedBy("admin");
		   				apartadosService.save(apartado);
					}
			}	
   		return new ResponseEntity<>("Horario creado Correctamente", HttpStatus.OK);
   	}
   	@RequestMapping(value="addHorario", method=RequestMethod.POST)
   	public ResponseEntity<?> addHorario(@RequestBody HorarioVista miHorario)
   	{
    	   //SELECT id FROM ca_sala ORDER BY RANDOM() limit 1
    	   
   		CAHorario nuevoHorario=new CAHorario();
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
   		
   		nuevoHorario.setActividad(actividadService.getOne(miHorario.getActividad()).get());
   		nuevoHorario.setDomingo(miHorario.isDomingo());
   		nuevoHorario.setDuracion(miHorario.getDuracion());
   		nuevoHorario.setHora(miHorario.getHora());
   		nuevoHorario.setJueves(miHorario.isJueves());
   		nuevoHorario.setLunes(miHorario.isLunes());
   		nuevoHorario.setMartes(miHorario.isMartes());
   		nuevoHorario.setMiercoles(miHorario.isMiercoles());
   		nuevoHorario.setPeriodoFinal(miHorario.getPeriodoFinal());
   		nuevoHorario.setPeriodoInicio(miHorario.getPeriodoInicio());
   		nuevoHorario.setSabado(miHorario.isSabado());
   		nuevoHorario.setRango(miHorario.getRango());
   		nuevoHorario.setDisponible(miHorario.isDisponible());
   		nuevoHorario.setSala(salaService.getOne(miHorario.getSala()).get());
   		nuevoHorario.setTecnico(tecnicoService.getOne(miHorario.getTecnico()).get());
   		nuevoHorario.setViernes(miHorario.isViernes());
   		
   		nuevoHorario.setUpdated(new Date());
   		nuevoHorario.setUpdatedBy(username);
   		nuevoHorario = horarioService.save(nuevoHorario);
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
						CAApartados apartado=new CAApartados();
		   	   			apartado.setActivo(true);
		   				apartado.setConteo( 0);
		   				apartado.setDia(print.format(date));
		   				apartado.setCreated(new Date());
		   				apartado.setCreatedBy("admin");
		   				apartado.setHorario(nuevoHorario);
		   				apartado.setUpdated(new Date());
		   				apartado.setUpdatedBy("admin");
		   				apartadosService.save(apartado);
					}if(nuevoHorario.isMartes() && dayOfWeek==3) {
						CAApartados apartado=new CAApartados();
		   	   			apartado.setActivo(true);
		   				apartado.setConteo(0);
		   				apartado.setDia(print.format(date));
		   				apartado.setCreated(new Date());
		   				apartado.setCreatedBy("admin");
		   				apartado.setHorario(nuevoHorario);
		   				apartado.setUpdated(new Date());
		   				apartado.setUpdatedBy("admin");
		   				apartadosService.save(apartado);
					}if(nuevoHorario.isMiercoles() && dayOfWeek==4) {
						CAApartados apartado=new CAApartados();
		   	   			apartado.setActivo(true);
		   				apartado.setConteo(0);
		   				apartado.setDia(print.format(date));
		   				apartado.setCreated(new Date());
		   				apartado.setCreatedBy("admin");
		   				apartado.setHorario(nuevoHorario);
		   				apartado.setUpdated(new Date());
		   				apartado.setUpdatedBy("admin");
		   				apartadosService.save(apartado);
					}if(nuevoHorario.isJueves() && dayOfWeek==5) {
						CAApartados apartado=new CAApartados();
		   	   			apartado.setActivo(true);
		   				apartado.setConteo(0);
		   				apartado.setDia(print.format(date));
		   				apartado.setCreated(new Date());
		   				apartado.setCreatedBy("admin");
		   				apartado.setHorario(nuevoHorario);
		   				apartado.setUpdated(new Date());
		   				apartado.setUpdatedBy("admin");
		   				apartadosService.save(apartado);
					}if(nuevoHorario.isViernes() && dayOfWeek==6) {
						CAApartados apartado=new CAApartados();
		   	   			apartado.setActivo(true);
		   				apartado.setConteo(0);
		   				apartado.setDia(print.format(date));
		   				apartado.setCreated(new Date());
		   				apartado.setCreatedBy("admin");
		   				apartado.setHorario(nuevoHorario);
		   				apartado.setUpdated(new Date());
		   				apartado.setUpdatedBy("admin");
		   				apartadosService.save(apartado);
					}if(nuevoHorario.isSabado() && dayOfWeek==7) {
						CAApartados apartado=new CAApartados();
		   	   			apartado.setActivo(true);
		   				apartado.setConteo(0);
		   				apartado.setDia(print.format(date));
		   				apartado.setCreated(new Date());
		   				apartado.setCreatedBy("admin");
		   				apartado.setHorario(nuevoHorario);
		   				apartado.setUpdated(new Date());
		   				apartado.setUpdatedBy("admin");
		   				apartadosService.save(apartado);
					}if(nuevoHorario.isDomingo() && dayOfWeek==1) {
						CAApartados apartado=new CAApartados();
		   	   			apartado.setActivo(true);
		   				apartado.setConteo(0);
		   				apartado.setDia(print.format(date));
		   				apartado.setCreated(new Date());
		   				apartado.setCreatedBy("admin");
		   				apartado.setHorario(nuevoHorario);
		   				apartado.setUpdated(new Date());
		   				apartado.setUpdatedBy("admin");
		   				apartadosService.save(apartado);
					}
			}	
   		return new ResponseEntity<>("Horario creado Correctamente", HttpStatus.OK);
   	}
   	
   	/**
   	 * Metodo que modifica un miembro ya existente en la base de datos (el miembro debe existir sino sera creado uno nuevo)
   	 * @param miembro es el objecto miembro que se quiere modificar
   	 * @return objeto miembro ya modificado
   	 */

   	@RequestMapping(value="actualizarHorario", method=RequestMethod.POST)
   	@Transactional
   	public ResponseEntity<?>  actualizarHorario(@RequestBody Body body){
   		Session currentSession = entityManager.unwrap(Session.class);
   		int countUpdate = currentSession.createNativeQuery("update ca_horario set PERIODO_FINAL = replace(PERIODO_FINAL, PERIODO_FINAL,"
   				+ " '"+body.getPeriodoFinal()+"') where id_horario in(select distinct(id) from clases where dia between '2022-04-18' and "
   						+ "'2022-04-24' and club='"+body.getClub()+"' and nombre='"+body.getName()+"') and activo=true;").executeUpdate();
   		
   		int countUpdate2 = currentSession.createNativeQuery("update ca_horario set PERIODO_INICIO = replace(PERIODO_INICIO, PERIODO_INICIO,"
   				+ " '"+body.getPeriodoInicio()+"') where id_horario in(select distinct(id) from clases where dia between '2022-04-18' and "
   						+ "'2022-04-24' and club='"+body.getClub()+"' and nombre='"+body.getName()+"') and activo=true;").executeUpdate();
   		
   		Query<CAHorario> listaHorario = currentSession.createNativeQuery("select * from ca_horario where "
   				+ "PERIODO_INICIO='"+body.getPeriodoInicio()+"' and activo=true and sala in(select id from ca_sala join club on"
   						+ " club_idclub=idclub where club.nombre='"+body.getClub()+"') and actividad in(select id from ca_actividad where nombre='"+body.getName()+"');",CAHorario.class);
		List<CAHorario> horarios= listaHorario.list();
		SimpleDateFormat print = new SimpleDateFormat("yyyy-MM-dd");
        
			for(int i=0;i<horarios.size();i++) {
				
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date startDate = new Date(),endDate = new Date();
			try {
				startDate = formatter.parse(horarios.get(i).getPeriodoInicio());
   				endDate = formatter.parse(horarios.get(i).getPeriodoFinal());
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
					if(horarios.get(i).isLunes() && dayOfWeek==2) {
   					CAApartados apartado=new CAApartados(); 
   	   	   			apartado.setActivo(true);
   	   				apartado.setConteo(0);
   	   				apartado.setDia(print.format(date));
   	   				apartado.setCreated(new Date());
   	   				apartado.setCreatedBy("admin");
   	   				apartado.setHorario(horarios.get(i));
   	   				apartado.setUpdated(new Date());
   	   				apartado.setUpdatedBy("admin");
   	   				apartadosService.save(apartado);
   				}if(horarios.get(i).isMartes() && dayOfWeek==3) {
   					CAApartados apartado=new CAApartados();
   	   	   			apartado.setActivo(true);
   	   				apartado.setConteo(0);
   	   				apartado.setDia(print.format(date));
   	   				apartado.setCreated(new Date());
   	   				apartado.setCreatedBy("admin");
   	   				apartado.setHorario(horarios.get(i));
   	   				apartado.setUpdated(new Date());
   	   				apartado.setUpdatedBy("admin");
   	   				apartadosService.save(apartado);
   				}if(horarios.get(i).isMiercoles() && dayOfWeek==4) {
   					CAApartados apartado=new CAApartados();
   	   	   			apartado.setActivo(true);
   	   				apartado.setConteo(0);
   	   				apartado.setDia(print.format(date));
   	   				apartado.setCreated(new Date());
   	   				apartado.setCreatedBy("admin");
   	   				apartado.setHorario(horarios.get(i));
   	   				apartado.setUpdated(new Date());
   	   				apartado.setUpdatedBy("admin");
   	   				apartadosService.save(apartado);
   				}if(horarios.get(i).isJueves() && dayOfWeek==5) {
   					CAApartados apartado=new CAApartados();
   	   	   			apartado.setActivo(true);
   	   				apartado.setConteo(0);
   	   				apartado.setDia(print.format(date));
   	   				apartado.setCreated(new Date());
   	   				apartado.setCreatedBy("admin");
   	   				apartado.setHorario(horarios.get(i));
   	   				apartado.setUpdated(new Date());
   	   				apartado.setUpdatedBy("admin");
   	   				apartadosService.save(apartado);
   				}if(horarios.get(i).isViernes() && dayOfWeek==6) {
   					CAApartados apartado=new CAApartados();
   	   	   			apartado.setActivo(true);
   	   				apartado.setConteo(0);
   	   				apartado.setDia(print.format(date));
   	   				apartado.setCreated(new Date());
   	   				apartado.setCreatedBy("admin");
   	   				apartado.setHorario(horarios.get(i));
   	   				apartado.setUpdated(new Date());
   	   				apartado.setUpdatedBy("admin");
   	   				apartadosService.save(apartado);
   				}if(horarios.get(i).isSabado() && dayOfWeek==7) {
   					CAApartados apartado=new CAApartados();
   	   	   			apartado.setActivo(true);
   	   				apartado.setConteo(0);
   	   				apartado.setDia(print.format(date));
   	   				apartado.setCreated(new Date());
   	   				apartado.setCreatedBy("admin");
   	   				apartado.setHorario(horarios.get(i));
   	   				apartado.setUpdated(new Date());
   	   				apartado.setUpdatedBy("admin");
   	   				apartadosService.save(apartado);
   				}if(horarios.get(i).isDomingo() && dayOfWeek==1) {
   					CAApartados apartado=new CAApartados();
   	   	   			apartado.setActivo(true);
   	   				apartado.setConteo(0);
   	   				apartado.setDia(print.format(date));
   	   				apartado.setCreated(new Date());
   	   				apartado.setCreatedBy("admin");
   	   				apartado.setHorario(horarios.get(i));
   	   				apartado.setUpdated(new Date());
   	   				apartado.setUpdatedBy("admin");
   	   				apartadosService.save(apartado);
   				}
				}
			}
		
		  return new ResponseEntity<>(countUpdate, HttpStatus.OK);	
   	}
   	
   	@RequestMapping(value="modificarHorario", method=RequestMethod.POST)
   	public ResponseEntity<?>  modificarHorario(@RequestBody CAHorario body){
   		CAHorario horario=horarioService.getOne(body.getId()).get();
   		body.setActividad(actividadService.getOne(body.getActividad().getId()).get());
   		body.setSala(salaService.getOne(body.getSala().getId()).get());
   		body.setTecnico(tecnicoService.getOne(body.getTecnico().getId()).get());
   		String username="";
   		if(!horario.getHora().equals(body.getHora())) {
   			horario.setHora(body.getHora());
   		}
   		if(horario.getDuracion()!=body.getDuracion()) {
   			horario.setDuracion(body.getDuracion());
   		}
   		if(!horario.getActividad().equals(body.getActividad())) {
   			horario.setActividad(body.getActividad());
   		}
   		if(!horario.getTecnico().equals(body.getTecnico())) {
   			horario.setTecnico(body.getTecnico());
   		}
   		if(!horario.getSala().equals(body.getSala())) {
   			horario.setSala(body.getSala());
   		}
   		if(!horario.getRango().equals(body.getRango())) {
   			horario.setRango(body.getRango());
   		}
   		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	   		
   		if (principal instanceof UserDetails) {
   			username = ((UserDetails)principal).getUsername();
   		} else {
   		  	username = principal.toString();
   		}
   		horario.setUpdated(new Date());
   		horario.setUpdatedBy(username);
   		horarioService.save(horario);
		return new ResponseEntity<>(horario, HttpStatus.OK);	
   	}
   	@RequestMapping(value="actualizarHorarioDuplicacion", method=RequestMethod.POST)
   	@Transactional
   	public ResponseEntity<?>  actualizarHorarioDuplicacion(@RequestBody Body body){
   		Session currentSession = entityManager.unwrap(Session.class);
   		
   		Query<CAHorario> horarioActual=currentSession.createQuery("FROM CAHorario h where "
   				+ "date(to_date(h.periodoFinal,'DD-MM-YYYY HH:MI:SS'))>current_timestamp() and h.sala.club.Nombre='"+body.getClub()+"' and h.actividad.nombre='"+body.getName()+"'",CAHorario.class);
   		
   		List<CAHorario> horarios2= horarioActual.list();
   		List<CAHorario> horariosNuevos=new ArrayList<CAHorario>();
   		String username="";
   		for(CAHorario horario: horarios2) {
   			CAHorario horarioNuevo=new CAHorario();
   			horarioNuevo.setActividad(horario.getActividad());
   			horarioNuevo.setActivo(true);
   			horarioNuevo.setCreated(new Date());
   			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
   	   		
   	   		if (principal instanceof UserDetails) {
   	   			username = ((UserDetails)principal).getUsername();
   	   		} else {
   	   		  	username = principal.toString();
   	   		}
   			horarioNuevo.setCreatedBy(username);
   			horarioNuevo.setDisponible(true);
   			horarioNuevo.setDomingo(horario.isDomingo());
   			horarioNuevo.setDuracion(horario.getDuracion());
   			horarioNuevo.setHora(horario.getHora());
   			horarioNuevo.setJueves(horario.isJueves());
   			horarioNuevo.setLunes(horario.isLunes());
   			horarioNuevo.setMartes(horario.isMartes());
   			horarioNuevo.setMiercoles(horario.isMiercoles());
   			horarioNuevo.setPeriodoFinal(body.getPeriodoFinal());
   			horarioNuevo.setPeriodoInicio(body.getPeriodoInicio());
   			horarioNuevo.setRango(horario.getRango());
   			horarioNuevo.setSabado(horario.isSabado());
   			horarioNuevo.setSala(horario.getSala());
   			horarioNuevo.setTecnico(horario.getTecnico());
   			horarioNuevo.setUpdated(new Date());
   			horarioNuevo.setUpdatedBy(username);
   			horarioNuevo.setViernes(horario.isViernes());
   			horarioService.save(horarioNuevo);
   			horariosNuevos.add(horarioNuevo);
   		}
   		
		SimpleDateFormat print = new SimpleDateFormat("yyyy-MM-dd");
    
		for(int i=0;i<horarios2.size();i++) {
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date startDate = new Date(),endDate = new Date();
			try {
				startDate = formatter.parse(horarios2.get(i).getPeriodoInicio());
				endDate = formatter.parse(horarios2.get(i).getPeriodoFinal());
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
				if(horarios2.get(i).isLunes() && dayOfWeek==2) {
					CAApartados apartado=new CAApartados(); 
		   			apartado.setActivo(true);
					apartado.setConteo(0);
					apartado.setDia(print.format(date));
					apartado.setCreated(new Date());
					apartado.setCreatedBy(username);
					apartado.setHorario(horarios2.get(i));
					apartado.setUpdated(new Date());
					apartado.setUpdatedBy(username);
					apartadosService.save(apartado);
				}if(horarios2.get(i).isMartes() && dayOfWeek==3) {
					CAApartados apartado=new CAApartados();
		   			apartado.setActivo(true);
					apartado.setConteo(0);
					apartado.setDia(print.format(date));
					apartado.setCreated(new Date());
					apartado.setCreatedBy(username);
					apartado.setHorario(horarios2.get(i));
					apartado.setUpdated(new Date());
					apartado.setUpdatedBy(username);
					apartadosService.save(apartado);
				}if(horarios2.get(i).isMiercoles() && dayOfWeek==4) {
					CAApartados apartado=new CAApartados();
		   			apartado.setActivo(true);
					apartado.setConteo(0);
					apartado.setDia(print.format(date));
					apartado.setCreated(new Date());
					apartado.setCreatedBy(username);
					apartado.setHorario(horarios2.get(i));
					apartado.setUpdated(new Date());
					apartado.setUpdatedBy(username);
					apartadosService.save(apartado);
				}if(horarios2.get(i).isJueves() && dayOfWeek==5) {
					CAApartados apartado=new CAApartados();
		   			apartado.setActivo(true);
					apartado.setConteo(0);
					apartado.setDia(print.format(date));
					apartado.setCreated(new Date());
					apartado.setCreatedBy(username);
					apartado.setHorario(horarios2.get(i));
					apartado.setUpdated(new Date());
					apartado.setUpdatedBy(username);
					apartadosService.save(apartado);
				}if(horarios2.get(i).isViernes() && dayOfWeek==6) {
					CAApartados apartado=new CAApartados();
		   			apartado.setActivo(true);
					apartado.setConteo(0);
					apartado.setDia(print.format(date));
					apartado.setCreated(new Date());
					apartado.setCreatedBy(username);
					apartado.setHorario(horarios2.get(i));
					apartado.setUpdated(new Date());
					apartado.setUpdatedBy(username);
					apartadosService.save(apartado);
				}if(horarios2.get(i).isSabado() && dayOfWeek==7) {
					CAApartados apartado=new CAApartados();
		   			apartado.setActivo(true);
					apartado.setConteo(0);
					apartado.setDia(print.format(date));
					apartado.setCreated(new Date());
					apartado.setCreatedBy(username);
					apartado.setHorario(horarios2.get(i));
					apartado.setUpdated(new Date());
					apartado.setUpdatedBy(username);
					apartadosService.save(apartado);
				}if(horarios2.get(i).isDomingo() && dayOfWeek==1) {
					CAApartados apartado=new CAApartados();
		   			apartado.setActivo(true);
					apartado.setConteo(0);
					apartado.setDia(print.format(date));
					apartado.setCreated(new Date());
					apartado.setCreatedBy(username);
					apartado.setHorario(horarios2.get(i));
					apartado.setUpdated(new Date());
					apartado.setUpdatedBy(username);
					apartadosService.save(apartado);
				}
			}
		}
		
	return new ResponseEntity<>(horariosNuevos, HttpStatus.OK);	
   	}
   	
   	@RequestMapping(value="actualizarHorarioFinSemanaSports", method=RequestMethod.POST)
   	@Transactional
   	public ResponseEntity<?>  actualizarHorarioFinSemanaSports(@RequestBody Body body){
   		Session currentSession = entityManager.unwrap(Session.class);
   		int countUpdate = currentSession.createNativeQuery("update ca_horario set PERIODO_FINAL = replace(PERIODO_FINAL, PERIODO_FINAL,"
   				+ " '"+body.getPeriodoFinal()+"') where id_horario in(select distinct(id) from clases where dia between '2021-11-01' and "
   						+ "'2021-11-31' and club='Sports Plaza' and nombre='GIMNASIO');").executeUpdate();
   		
   		int countUpdate2 = currentSession.createNativeQuery("update ca_horario set PERIODO_INICIO = replace(PERIODO_INICIO, PERIODO_INICIO,"
   				+ " '"+body.getPeriodoInicio()+"') where id_horario in(select distinct(id) from clases where dia between '2021-11-01' and"
   						+ " '2021-11-31' and club='Sports Plaza' and nombre='GIMNASIO');").executeUpdate();
   		
   		Query<CAHorario> listaHorario = currentSession.createNativeQuery("select * from ca_horario where id_horario in(select distinct(id)"
   				+ " from clases where dia between '2021-11-01' and '2021-11-31' and club='Sports Plaza' and nombre='GIMNASIO') and "
   				+ "activo=true AND (DOMINGO=TRUE OR SABADO=TRUE);",CAHorario.class);
		List<CAHorario> horarios= listaHorario.list();
		SimpleDateFormat print = new SimpleDateFormat("yyyy-MM-dd");
        
			for(int i=0;i<horarios.size();i++) {
				
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date startDate = new Date(),endDate = new Date();
			try {
				startDate = formatter.parse(horarios.get(i).getPeriodoInicio());
   				endDate = formatter.parse(horarios.get(i).getPeriodoFinal());
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
					if(horarios.get(i).isLunes() && dayOfWeek==2) {
   					CAApartados apartado=new CAApartados(); 
   	   	   			apartado.setActivo(true);
   	   				apartado.setConteo(0);
   	   				apartado.setDia(print.format(date));
   	   				apartado.setCreated(new Date());
   	   				apartado.setCreatedBy("admin");
   	   				apartado.setHorario(horarios.get(i));
   	   				apartado.setUpdated(new Date());
   	   				apartado.setUpdatedBy("admin");
   	   				apartadosService.save(apartado);
   				}if(horarios.get(i).isMartes() && dayOfWeek==3) {
   					CAApartados apartado=new CAApartados();
   	   	   			apartado.setActivo(true);
   	   				apartado.setConteo(0);
   	   				apartado.setDia(print.format(date));
   	   				apartado.setCreated(new Date());
   	   				apartado.setCreatedBy("admin");
   	   				apartado.setHorario(horarios.get(i));
   	   				apartado.setUpdated(new Date());
   	   				apartado.setUpdatedBy("admin");
   	   				apartadosService.save(apartado);
   				}if(horarios.get(i).isMiercoles() && dayOfWeek==4) {
   					CAApartados apartado=new CAApartados();
   	   	   			apartado.setActivo(true);
   	   				apartado.setConteo(0);
   	   				apartado.setDia(print.format(date));
   	   				apartado.setCreated(new Date());
   	   				apartado.setCreatedBy("admin");
   	   				apartado.setHorario(horarios.get(i));
   	   				apartado.setUpdated(new Date());
   	   				apartado.setUpdatedBy("admin");
   	   				apartadosService.save(apartado);
   				}if(horarios.get(i).isJueves() && dayOfWeek==5) {
   					CAApartados apartado=new CAApartados();
   	   	   			apartado.setActivo(true);
   	   				apartado.setConteo(0);
   	   				apartado.setDia(print.format(date));
   	   				apartado.setCreated(new Date());
   	   				apartado.setCreatedBy("admin");
   	   				apartado.setHorario(horarios.get(i));
   	   				apartado.setUpdated(new Date());
   	   				apartado.setUpdatedBy("admin");
   	   				apartadosService.save(apartado);
   				}if(horarios.get(i).isViernes() && dayOfWeek==6) {
   					CAApartados apartado=new CAApartados();
   	   	   			apartado.setActivo(true);
   	   				apartado.setConteo(0);
   	   				apartado.setDia(print.format(date));
   	   				apartado.setCreated(new Date());
   	   				apartado.setCreatedBy("admin");
   	   				apartado.setHorario(horarios.get(i));
   	   				apartado.setUpdated(new Date());
   	   				apartado.setUpdatedBy("admin");
   	   				apartadosService.save(apartado);
   				}if(horarios.get(i).isSabado() && dayOfWeek==7) {
   					CAApartados apartado=new CAApartados();
   	   	   			apartado.setActivo(true);
   	   				apartado.setConteo(0);
   	   				apartado.setDia(print.format(date));
   	   				apartado.setCreated(new Date());
   	   				apartado.setCreatedBy("admin");
   	   				apartado.setHorario(horarios.get(i));
   	   				apartado.setUpdated(new Date());
   	   				apartado.setUpdatedBy("admin");
   	   				apartadosService.save(apartado);
   				}if(horarios.get(i).isDomingo() && dayOfWeek==1) {
   					CAApartados apartado=new CAApartados();
   	   	   			apartado.setActivo(true);
   	   				apartado.setConteo(0);
   	   				apartado.setDia(print.format(date));
   	   				apartado.setCreated(new Date());
   	   				apartado.setCreatedBy("admin");
   	   				apartado.setHorario(horarios.get(i));
   	   				apartado.setUpdated(new Date());
   	   				apartado.setUpdatedBy("admin");
   	   				apartadosService.save(apartado);
   				}
				}
			}
		
		  return new ResponseEntity<>(countUpdate, HttpStatus.OK);	
   	}
       
       //---------------------------------------------------WEB SERVICE PASE------------------------------------------------------
       
       @GetMapping("/obtenerPase/{idCliente}")
		@ResponseBody
		public List<PaseUsuario> getPase(@PathVariable("idCliente") int idCliente)
		{
    	   Connection conn = null;
         	ArrayList<PaseUsuario> listaReporte = new ArrayList<PaseUsuario>();
	        try {
	            // Carga el driver de oracle
	        	DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
	            

	        	
	            conn = DriverManager.getConnection(dbURL, userData, passData);
	            
	            PreparedStatement ps=conn.prepareStatement("EXEC DataFlowAlpha.dbo.sp_Consulta_Pases_Sports_Plaza ?");
	            ps.setInt(1, idCliente);
             	ResultSet rs =ps.executeQuery();
               while (rs.next()) {
               	
                   PaseUsuario to=new PaseUsuario();
                   to.setActivo(true);
                   to.setIdProd(rs.getInt(1));
                   to.setF_compra(new Date(rs.getLong(2)));
                   to.setConcepto(rs.getString(3));
                   to.setIdVentaDetalle(rs.getInt(4));
                   to.setCliente(clienteService.findById(idCliente));
                   to.setCantidad(rs.getInt(11));
                   to.setConsumido(0);
                   to.setCreated(new Date());
                   if(to.getConcepto().equals("SP Mensualidad Gym") || to.getConcepto().equals("SP Mensualidad Gym Estudiante")) {
                       to.setDisponibles(0);
                       to.setCantidad(0);
                       to.setIdProd(1746);
                   }else {
                       to.setDisponibles(rs.getInt(11));
                       to.setCantidad(rs.getInt(11));
                   }
                   if(to.getIdProd()==1896 || to.getIdProd()==1897 ||to.getIdProd()==1898 ) {
                	   to.setIdProd(1895);
                   }
                   to.setUpdated(new Date());
                   Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	       			String username;
	       			if (principal instanceof UserDetails) {
	       				username = ((UserDetails)principal).getUsername();
	       			} else {
	       			  	username = principal.toString();
	       			}
	       			to.setUpdatedBy(username);
	       			to.setCreatedBy(username);
                   listaReporte.add(to);
               }
                ps=conn.prepareStatement("EXEC DataFlowAlpha.dbo.sp_Consulta_Paquetes_Sports_Plaza ?");
	            ps.setInt(1, idCliente);
            	rs =ps.executeQuery();
              while (rs.next()) {
              	
                  PaseUsuario to=new PaseUsuario();
                  to.setActivo(true);
                  to.setIdProd(rs.getInt(1));
                  to.setF_compra(new Date(rs.getDate(2).getTime()));
                  to.setConcepto(rs.getString(3));
                  to.setIdVentaDetalle(rs.getInt(4));
                  to.setCliente(clienteService.findById(idCliente));
                  to.setConsumido(0);
                  to.setCreated(new Date());
                  if(to.getConcepto().equals("SP Mensualidad Gym") || to.getConcepto().equals("SP Mensualidad Gym Estudiante")) {
                      to.setDisponibles(0);
                      to.setCantidad(0);
                      to.setIdProd(1746);
                  }else {
                      to.setDisponibles(rs.getInt(11));
                      to.setCantidad(rs.getInt(11));
                  }
                  to.setUpdated(new Date());
                  Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	       			String username;
	       			if (principal instanceof UserDetails) {
	       				username = ((UserDetails)principal).getUsername();
	       			} else {
	       			  	username = principal.toString();
	       			}
	       			to.setUpdatedBy(username);
	       			to.setCreatedBy(username);
                  listaReporte.add(to);
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
	    	for(int i=0;i<listaReporte.size();i++) {
		    	int idVenta=listaReporte.get(i).getIdVentaDetalle();
		    	Optional<PaseUsuario> pase=paseUsuarioService.getOne(idVenta);
		    	
		    	if(pase.isEmpty()) {
			    	PaseUsuario paseUsuario=new PaseUsuario();
		    		paseUsuario.setIdVentaDetalle(idVenta);		    	
			    	paseUsuario.setCliente(clienteService.findById(idCliente));	
			    	paseUsuario.setDisponibles(listaReporte.get(i).getCantidad());
			    	paseUsuario.setCantidad(listaReporte.get(i).getCantidad());
			    	paseUsuario.setConcepto(listaReporte.get(i).getConcepto());
			    	paseUsuario.setConsumido(0);
			    	paseUsuario.setF_compra(new Date(listaReporte.get(i).getF_compra()));
			    	paseUsuario.setIdProd(listaReporte.get(i).getIdProd());
			    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					String username;
					if (principal instanceof UserDetails) {
						username = ((UserDetails)principal).getUsername();
					} else {
					  	username = principal.toString();
					}
					paseUsuario.setCreatedBy(username);
					paseUsuario.setUpdatedBy(username);
			    	paseUsuarioService.save(paseUsuario);
		    	}
	    	}
	    	
	    	return paseUsuarioService.getByIdCliente(idCliente);
		}
       
       @RequestMapping(value="confirmarAsistencia", method=RequestMethod.POST)
      	public ResponseEntity<?> confirmarAsistencia(@RequestBody Body body)
      	{
    	   Session currentSession = entityManager.unwrap(Session.class);
      		this.actualizarPasesRedimidos();
      		JSONObject json=new JSONObject();
      		try {
   				if(clienteService.findCitas(body.getId(),body.getUsuario())) {
   					Query<CAClase> listaClases;
   		        	listaClases = currentSession.createNativeQuery("select id,nombre,clases.tecnico,tipo_actividad,color, lugar,"
   		        			+ "clases.duracion,nivel,clases.hora,cupo_actual,cupo_maximo,clases.rango,clases.disponible,clases.dia,"
   		        			+ " clases.paga, clases.id_apartados from clases  where id_apartados='"+body.getId()+"'  "
   		        			+ "order by to_timestamp(clases.hora,'HH24:MI');",CAClase.class);   				
   		        	List<CAClase> lista= listaClases.getResultList();
   					if(lista.get(0).getPaga()==0) {   						
   						if(registroGimnasioService.accedio(body.getUsuario(), body.getId())) {
   							json.put("Respuesta", "Este usuario ya había accedido a la clase");
   		   	   	   			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 	
   						}
   						RegistroGimnasio registroGimnasio=new RegistroGimnasio();
   						registroGimnasio.setIdCliente(clienteService.findById(body.getUsuario()));
   						registroGimnasio.setRegistroAcceso(new Date());
   						registroGimnasio.setIdApartados(apartadosService.getOne(body.getId()));
   						registroGimnasioService.save(registroGimnasio);

   		   	   			json.put("Respuesta", "Acceso correcto");
   				   	   	
   		   	   			return new ResponseEntity<String>(json.toString(), HttpStatus.OK);    						
   					}
   					PaseConsumido pase=paseConsumidoService.getOne(body.getUsuario(), body.getIdVentaDetalle());
   					if(pase.getPaseUsuario().getIdProd()!=0) {
   	   	   				clienteService.findById(body.getUsuario());
   		   	   	       	if(pase.getFechaRedencion()!=null) {
   		   	   	   			json.put("Respuesta", "Este pase ya ha sido consumido antes");
   		   	   	   			
   		   	   	   			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 		   	   	       		
   		   	   	       	}
   		   	   	       	pase.setFechaRedencion(new Date());
   		   	   	       	pase.setActivo(true);
   		   	   	       	pase.setCreated(new Date());
   		   	   	       	PaseUsuario paseUsuario=paseUsuarioService.getOne(pase.getPaseUsuario().getIdVentaDetalle()).get();
   		   	   	       	paseUsuario.setConsumido(paseUsuario.getConsumido()+1);
   		   	   	       	paseUsuarioService.save(paseUsuario);
   				   	   	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
   				  		String username;
   				  		if (principal instanceof UserDetails) {
   				  			username = ((UserDetails)principal).getUsername();
   				  		} else {
   				  		  	username = principal.toString();
   				  		}
   				  		pase.setCreatedBy(username);
   				  		pase.setUpdatedBy(username);
   				  		pase.setUpdated(new Date());
   		   	   	       	paseConsumidoService.save(pase);
   	   	   			}
   	   	   			json.put("Respuesta", "Acceso permitido");
   	   	   			
   	   	   			return new ResponseEntity<String>(json.toString(), HttpStatus.OK); 
   				}   				
  	   			json.put("Respuesta", "No tiene permitido acceder a esta sala");
  	   			
      	   		return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
      		}catch(IndexOutOfBoundsException e) {
     	   		json.put("Respuesta", "No tiene pases para esta clase");
     	   		
      			return new ResponseEntity<>(json.toString(), HttpStatus.CONFLICT);		
      		}catch(Exception e) {
      			e.printStackTrace();
     	   		json.put("Error", "Ha sucedido un error desconocido");
     	   		
      			return new ResponseEntity<>(json.toString(), HttpStatus.CONFLICT);	
      			
      		}
      	}
       
   	@RequestMapping(value="redimirPase", method=RequestMethod.POST)
   	public ResponseEntity<?> redimirPase(@RequestBody Body body)
   	{
   		this.actualizarPasesRedimidos();
		JSONObject json=new JSONObject();
   		try {
   	   		if(body.isSuper()) {
   	   			Registro nuevoRegistroAcceso=new Registro(body.getUsuario(),body.getTerminal());
   	   			registroService.save(nuevoRegistroAcceso);
   	   			json.put("Respuesta", "Acceso permitido");
   	   			return new ResponseEntity<String>(json.toString(), HttpStatus.OK); 
   	   		}else {
   	   			TerminalRedencion terminal=terminalRedencionService.getOne(body.getTerminal()).get();
   	   			if((terminal.getId()==5 && body.getIdVentaDetalle()==-1) || (terminal.getId()==6  && body.getIdVentaDetalle()==-1)) {
   	   				this.getPaseAlberca(body.getUsuario());
   	   				List<PaseUsuario> paseUsuario=paseUsuarioService.getPasesClasesNado(body.getUsuario());
   	   				if(paseUsuario.size()>0) {
	   	   				json.put("Respuesta", "Acceso correcto");
		   	   			RegistroGimnasio registroGimnasio=new RegistroGimnasio();
						registroGimnasio.setIdCliente(clienteService.findById(body.getUsuario()));
						registroGimnasio.setIdTerminal(terminalRedencionService.getOne(body.getTerminal()).get());
						registroGimnasio.setRegistroAcceso(new Date());
						registroGimnasioService.save(registroGimnasio);
		   	   			return new ResponseEntity<String>(json.toString(), HttpStatus.OK); 
   	   				}
   	   			}
				if(clienteService.findCitas(new Date(),terminal.obtenerSala(),body.getUsuario())) {
					CAApartados apartado=clienteService.findApartados(new Date(), terminal.obtenerSala(), body.getUsuario());
					if(body.getIdVentaDetalle()==0) {
						
						if(registroGimnasioService.accedio(body.getUsuario(), apartado.getId())) {
							json.put("Respuesta", "Este usuario ya había accedido a la clase");
		   	   	   			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 	
						}
						RegistroGimnasio registroGimnasio=new RegistroGimnasio();
						registroGimnasio.setIdCliente(clienteService.findById(body.getUsuario()));
						registroGimnasio.setIdTerminal(terminalRedencionService.getOne(body.getTerminal()).get());
						registroGimnasio.setRegistroAcceso(new Date());
						registroGimnasio.setIdApartados(apartado);
						registroGimnasioService.save(registroGimnasio);

		   	   			json.put("Respuesta", "Acceso correcto");
				   	   	
		   	   			return new ResponseEntity<String>(json.toString(), HttpStatus.OK); 
						
					}
					if(body.getIdVentaDetalle()==-1) {
						
						if(registroGimnasioService.accedio(body.getUsuario(), apartado.getId())) {
							json.put("Respuesta", "Este usuario ya había accedido a la clase");
		   	   	   			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 	
						}
						RegistroGimnasio registroGimnasio=new RegistroGimnasio();
						registroGimnasio.setIdCliente(clienteService.findById(body.getUsuario()));
						registroGimnasio.setIdTerminal(terminalRedencionService.getOne(body.getTerminal()).get());
						registroGimnasio.setRegistroAcceso(new Date());
						registroGimnasio.setIdApartados(apartado);
						registroGimnasioService.save(registroGimnasio);

		   	   			json.put("Respuesta", "Acceso correcto");
				   	   	
		   	   			return new ResponseEntity<String>(json.toString(), HttpStatus.OK); 
						
					}
					PaseConsumido pase=paseConsumidoService.getOne(body.getUsuario(), body.getIdVentaDetalle());
					if(pase.getPaseUsuario().getIdProd()!=0) {
	   	   				clienteService.findById(body.getUsuario());
		   	   	       	if(pase.getFechaRedencion()!=null) {
		   	   	   			json.put("Respuesta", "Este pase ya ha sido consumido antes");
		   	   	   			
		   	   	   			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 		   	   	       		
		   	   	       	}
	   	   	       		pase.setTerminalRedencion(terminal);
		   	   	       	pase.setFechaRedencion(new Date());
		   	   	       	pase.setActivo(true);
		   	   	       	pase.setCreated(new Date());
		   	   	       	PaseUsuario paseUsuario=paseUsuarioService.getOne(pase.getPaseUsuario().getIdVentaDetalle()).get();
		   	   	       	paseUsuario.setConsumido(paseUsuario.getConsumido()+1);
		   	   	       	paseUsuarioService.save(paseUsuario);
				   	   	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				  		String username;
				  		if (principal instanceof UserDetails) {
				  			username = ((UserDetails)principal).getUsername();
				  		} else {
				  		  	username = principal.toString();
				  		}
				  		pase.setCreatedBy(username);
				  		pase.setUpdatedBy(username);
				  		pase.setUpdated(new Date());
		   	   	       	paseConsumidoService.save(pase);
	   	   			}
	   	   			json.put("Respuesta", "Acceso permitido");
	   	   			
	   	   			return new ResponseEntity<String>(json.toString(), HttpStatus.OK); 
				}   				
   	   			json.put("Respuesta", "No tiene permitido acceder a esta sala");
   	   			
   	   			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
   	       	
   	   			
   	   		}
   		}catch(IndexOutOfBoundsException e) {
  	   		json.put("Respuesta", "No tiene pases para esta clase");
  	   		
   			return new ResponseEntity<>(json.toString(), HttpStatus.CONFLICT);		
   		}catch(Exception e) {
   			e.printStackTrace();
  	   		json.put("Error", "Ha sucedido un error desconocido");
  	   	
   			return new ResponseEntity<>(json.toString(), HttpStatus.CONFLICT);	
   			
   		}
   	}

	@GetMapping("/actualizarPasesRedimidos")
		public String actualizarPasesRedimidos() {
		
		Connection con = null;
		try {
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/app","postgres","lalo");
			PreparedStatement pstmt=con.prepareStatement("call actualizar_pase_consumido()");
			pstmt.execute();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   		return "Proceso ejecutado exitosamente";
		}
	
	@PostMapping("/pasesConsumidos")
	public ResponseEntity<?> pasesConsumidos(@RequestBody Body body){
   		Session currentSession = entityManager.unwrap(Session.class);
   		Query<CAClase> listaClases = currentSession.createNativeQuery("select clases.id,nombre,clases.tecnico,tipo_actividad,color,"
   				+ "lugar,clases.duracion,nivel,clases.hora,cupo_actual,cupo_maximo,clases.rango,clases.disponible,clases.dia,"
   				+ "clases.paga, ca_apartados_usuario.activo from pase_consumido join ca_apartados_usuario on "
   				+ "pase_consumido.apartado_usuario=ca_apartados_usuario.id join clases on "
   				+ "clases.id_apartados=ca_apartados_usuario.id_apartados where pase_usuario="+body.getIdVentaDetalle()+""
   				+ " and terminal_Redencion_id is not null;",CAClase.class);
		List<CAClase> lista= listaClases.list();
		
		return new ResponseEntity<>(lista,HttpStatus.OK);
	}
	@PostMapping("/asistenciaUsuarios")
	public ResponseEntity<?> asistenciaUsuarios(@RequestBody Body body){
		List<ClienteDTOO> listaClientes;
		listaClientes=clienteService.asistenciaClientes(body.getId());
		if(listaClientes.isEmpty()) {
			listaClientes=clienteService.asistenciaGimnasioClientes(body.getId());
		}
		
		return new ResponseEntity<>(listaClientes,HttpStatus.OK);
	}
	@PostMapping("/prueba")
	public ResponseEntity<?> prueba(@RequestBody Body body){
		String archivo=body.getClub();
    	String cadena;
        FileReader f;
		try {
			f = new FileReader(archivo);
			BufferedReader b = new BufferedReader(f); 
	        while((cadena = b.readLine())!=null) { 
	        	fotoService.delete(fotoService.findById(Integer.parseInt(cadena)));
	        } 
	        b.close(); 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return new ResponseEntity<>("listo",HttpStatus.OK);
		
	}
	public String getPaseAlberca(int paseid)
	{
		String body2 = "{\n"
    			+ "\"IdCliente\":"+paseid+",\n"
    			+ "\"Token\":\"77D5BDD4-1FEE-4A47-86A0-1E7D19EE1C74\"\n"
    			+ "}";
    	configuracion o = configuracionService.findByServiceName("getPasesById").get(); 
    	JSONArray json=new JSONArray(e.conectaApiClubPOST(body2,o.getEndpointAlpha()));
    	for(int i=0;i<json.length();i++) {
    		JSONObject obj=json.getJSONObject(i);
    		int concepto=obj.getInt("IDProdServ");
	    	PaseUsuario pase=new PaseUsuario();
	    	pase.setIdProd(concepto);
	    	if((concepto>=1834 && concepto<=1846) || (concepto==1746 || concepto==1747)) {
	    		pase.setCantidad(0);
	    		pase.setDisponibles(0);
	    		pase.setConsumido(0);
	    		if(concepto==1747) {
	    			pase.setIdProd(1746);
	    		}
	    	}
	    	pase.setCantidad(obj.getInt("Cantidad"));
	    	pase.setIdVentaDetalle(obj.getInt("VentaDetalle"));
	    	pase.setConcepto(obj.getString("Concepto"));
	    	pase.setF_compra(new Date(obj.getLong("FechaCaptura")));
	    	pase.setActivo(true);
	    	pase.setCliente(clienteService.findById(paseid));
	    	pase.setCreated(new Date());
	    	pase.setUpdated(new Date());
	    	paseUsuarioService.save(pase);
	    	pase.setUpdatedBy(String.valueOf(paseid));
	    	pase.setCreatedBy(String.valueOf(paseid));
    	}
    	return json.toString();
	}

}//fin de la clase
