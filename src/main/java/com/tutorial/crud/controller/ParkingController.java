package com.tutorial.crud.controller;

import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import com.tutorial.crud.dto.*;
import com.tutorial.crud.exception.ResourceNotFoundException;
import com.tutorial.crud.repository.QREstacionamientoCostoRepository;
import com.tutorial.crud.entity.body2;
import org.apache.commons.io.IOUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tutorial.crud.aopDao.endpoints;
import com.tutorial.crud.correo.Correo;
import com.tutorial.crud.entity.*;
import com.tutorial.crud.security.dto.NuevoUsuario;
import com.tutorial.crud.security.entity.Rol;
import com.tutorial.crud.security.entity.Usuario;
import com.tutorial.crud.security.enums.RolNombre;
import com.tutorial.crud.security.service.RolService;
import com.tutorial.crud.security.service.UsuarioService;
import com.tutorial.crud.service.*;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;




/**
 * 	Esta clase permite hacer uso de todos los service para crear, actualizar y obtener las entidades mapeadas
 * @author: Daniel García Velasco y Abimael Rueda Galindo
 * @version: 12/7/2021
 *
 */


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/parking")
public class ParkingController
{
	endpoints e = new endpoints();

	@Autowired
	private EstatusCobranzaService estatusCobranzaService;
	
	//service para estatus membresia
	@Autowired
	private EstatusMembresiaService estatusMembresiaService;
	
	//service para tipo cliente
	@Autowired
	private TipoClienteService tipoClienteService;
	
	@Autowired
	private CategoriaService categoriaService;
	
	//service para estatus cliente
	@Autowired
	private EstatusClienteService estatusClienteService;
	
	//service para tipo membresia
	@Autowired
	private TipoMembresiaService tipoMembresiaService;
	
    @Autowired
    PasswordEncoder passwordEncoder;
	
	@Autowired
	ClubService clubService;
	
	@Autowired
	EstacionamientoExternoService estacionamientoExternoService;
	
    @Autowired
    configuracionService configuracionService;
    
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private PlumaService plumaService;

	@Autowired
	private RegistroTagService registroTagService;
	
	@Autowired
	private EntityManager entityManager;

	@Autowired
	private ParkingUsuarioService parkingUsuarioService;	
	
	@Autowired
	private RHEmpleadoService rhempleadoService;

	@Autowired
	private AntenaService antenaService;	

	@Autowired
	private CasetaService casetaService;	

	@Autowired
	private RegistroParkingService registroParkingService;	
	
	@Autowired
	private CarroService carroService;
	
	@Autowired
	private TipoAccesoService tipoAccesoService;
	
	@Autowired
	private AmonestacionesService amonestacionesService;
	
	@Autowired
	private QRCortesiaService qrCortesiaService;
	
	@Autowired
	private ClienteExternoService clienteExternoService;

	@Autowired
	private TerminalRedencionService terminalRedencionService;	

	@Autowired
	private RegistroService registroService;

	@Autowired
	private PaseConsumidoService paseConsumidoService;	

	@Autowired
	private PaseUsuarioService paseUsuarioService;	

	@Autowired
	private RegistroCortesService registroCortesService;	

	@Autowired
	private HorarioAccesoService horarioAccesoService;	

	@Autowired
	private FotoServiceImpl fotoService;
	
	@Autowired
	private RegistroSalidaSPService registroSalidaService;

	@Autowired
	private QRParkingService qrParkingService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	Servicios servicios;
	
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
	
	@Value("${my.property.usuarioCorreo}")
	String usuarioCorreo;
	
	@Value("${my.property.contrasenaCorreo}")
	String contrasenaCorreo;
	
	@Value("${my.property.copiaOculta2}")
	String copiaOculta;

	@Autowired
	ConfiguracionSancionService configuracionSancionService;

	@Autowired
	QREstacionamientoCostoRepository qrEstacionamientoCostoRepository;

	private static LocalDateTime timeBefore = LocalDateTime.now().withNano(0);
	private static LocalDateTime timeNow;

	@GetMapping("/nuevo/{idUsuario}")
	@ResponseBody
	public ResponseEntity<?> nuevo(@PathVariable("idUsuario") int idUsuario) {
		try {
			List<ParkingUsuario> lista = parkingUsuarioService.obtenerRecienteParkingUsuarios(idUsuario);

			ParkingUsuario usuario = parkingUsuarioService.obtenerParkingUsuarioNoCapturado(idUsuario);

			if (usuario == null) {
				return new ResponseEntity<>(crearRespuestaError("El usuario: " + idUsuario + " no tiene un chip disponible para activación.").toMap(), HttpStatus.NOT_FOUND);
			}

			System.out.println("usuario: " + usuario);

			Optional<Long> idChip = parkingUsuarioService.obtenerIdChip(usuario);
			if (idChip.isEmpty() || parkingUsuarioService.validarChipActivo(idChip.get())) {
				return new ResponseEntity<>(crearRespuestaError("ID de chip: " + idChip + " ya esta asignado o no existe en el sistema").toMap(), HttpStatus.BAD_REQUEST);
			}

			return parkingUsuarioService.generarRespuestaFinal(usuario, idChip.get());
		} catch (Exception e) {
			System.out.println("message: " + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(crearRespuestaError("Error inesperado: " + e.getMessage()).toMap(), HttpStatus.BAD_REQUEST);
		}
	}

	private JSONObject crearRespuestaError(String mensaje) {
		JSONObject json = new JSONObject();
		json.put("respuesta", mensaje);
		return json;
	}

	/*@GetMapping("/nuevo_old/{horarioId}")
	@ResponseBody
	public ResponseEntity<?> nuevo_old(@PathVariable("horarioId") int horarioId){
		ArrayList<ParkingUsuario> lista = new ArrayList<ParkingUsuario>();

		try {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create("http://192.168.20.104:8000/parking/nuevo/" + horarioId))
					.GET()
					.build();

			HttpResponse<String> respuesta = client.send(request, HttpResponse.BodyHandlers.ofString());

			JSONArray jarray = new JSONArray(respuesta.body());

			for(int i = 0;i < jarray.length(); i++ ) {
				ParkingUsuario to = new ParkingUsuario();

				try {
					SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
					Date fechaCaptura = formato.parse(jarray.getJSONObject(i).getString("fechaCaptura"));
					to.setFechaCaptura(fechaCaptura);
				} catch (ParseException parseException) {
					System.out.println(parseException.getMessage());
				}

				to.setIdProd(jarray.getJSONObject(i).getInt("idProd"));
				to.setConcepto(jarray.getJSONObject(i).getString("concepto"));
				to.setIdVentaDetalle(jarray.getJSONObject(i).getInt("idVentaDetalle"));
				to.setObservaciones(jarray.getJSONObject(i).getString("observaciones"));
				to.setEstadoCobranza(jarray.getJSONObject(i).getString("estadoCobranza"));
				to.setCorreo(jarray.getJSONObject(i).getString("correo"));
				to.setClub(jarray.getJSONObject(i).getString("club"));
				to.setCantidad(Math.round(jarray.getJSONObject(i).getFloat("cantidad")));

				//this.update(horarioId);
				servicios.update(horarioId);
				to.setCliente(clienteService.findById(horarioId));
				to.setPk(true);
				lista.add(to);

			}
			ParkingUsuario usuario = null;
			try {
				//usuario=lista.get(lista.size()-1);
				usuario = lista.get(0);
			}catch(IndexOutOfBoundsException e) {
				List<ParkingUsuario> parkingUsuario = parkingUsuarioService.findByIdCliente(clienteService.findById(horarioId));
				for(int i=0;i<parkingUsuario.size();i++) {
					if(!parkingUsuario.get(i).isCapturado()) {
						usuario=parkingUsuario.get(i);
						break;
					}
				}

			}
			JSONObject json=new JSONObject();
			int idVentaDetalle = 0;
			try {
				idVentaDetalle=usuario.getIdVentaDetalle();
			}catch(NullPointerException e) {
				System.out.println("\u001B[31m"+"Error en la linea 293 Parking Controller no se encontro ningun id de venta a este usuario en el sp "+"\u001B[0m");
				json.put("respuesta", "el usuario no tiene un chip pagado");
				return new ResponseEntity<>(json.toString(), HttpStatus.BAD_REQUEST);
			}
			if(parkingUsuarioService.getOne(usuario.getIdVentaDetalle()).isPresent() && parkingUsuarioService.getOne(idVentaDetalle).get().isCapturado()) {
				//conn.close();
				//json.put("respuesta", "el usuario no tiene un chip pagado");
				json.put("respuesta", "el chip tag ya fue asignado");
				return new ResponseEntity<>(json.toString(), HttpStatus.BAD_REQUEST);
			}else {
				Long idChip = 0L;
				try {
					idChip = Long.parseLong(usuario.getObservaciones().trim());
				} catch(NumberFormatException e) {
					json.put("respuesta", "error en el id del chip");
					return new ResponseEntity<>(json.toString(), HttpStatus.BAD_REQUEST);
				}

				//RegistroTag registroTag= registroTagService.findByIdChip(Long.parseLong(usuario.getObservaciones()));
				RegistroTag registroTag = registroTagService.findByIdChip(idChip);
				try {
					if(registroTag.isActivo()) {
						json.put("respuesta", "El chip ingresado ya se encuentra activo");
						return new ResponseEntity<>(json.toString(), HttpStatus.BAD_REQUEST);
					}
				} catch(NullPointerException e) {
					System.out.println("\u001B[31m"+"Error en la linea 310 Parking Controller ingresaron un id de chip que no existe en una orden de venta de dataflow: id_chip="+usuario.getObservaciones()+"\u001B[0m");
					json.put("respuesta", "el id de chip "+usuario.getObservaciones()+" no existe");
					return new ResponseEntity<>(json.toString(), HttpStatus.BAD_REQUEST);
				}
				System.out.println(registroTag);
				usuario.setRegistroTag(registroTag);
				registroTag.setParking(usuario);
				//registroTagService.save(registroTag);

				ParkingUsuarioDTO vista=new ParkingUsuarioDTO();

				ClienteVista clienteVista=new ClienteVista();
				clienteVista.setClienteTipo(usuario.getCliente().getTipoCliente().getNombre());
				clienteVista.setClub(usuario.getCliente().getClub().getNombre());
				clienteVista.setIdCLiente(usuario.getCliente().getIdCliente());
				clienteVista.setMembresia(usuario.getCliente().getNoMembresia());
				clienteVista.setNombre(usuario.getCliente().getNombre()+" "+usuario.getCliente().getApellidoPaterno()+" "+usuario.getCliente().getApellidoMaterno());
				usuario.setActivo(true);
				usuario.setCapturado(false);
				vista.setCliente(clienteVista);
				vista.setCantidad(usuario.getCantidad());
				vista.setConcepto(usuario.getConcepto());
				vista.setFechaCaptura(usuario.getFechaCaptura());
				vista.setIdProd(usuario.getIdProd());
				vista.setIdVentaDetalle(usuario.getIdVentaDetalle());
				vista.setPk(usuario.isPk());
				vista.setObservaciones(usuario.getObservaciones());
				Calendar calendar =Calendar.getInstance();
				calendar.setTime(usuario.getFechaCaptura());
				calendar.add(Calendar.YEAR, 1);
				vista.setVigencia(calendar.getTime());

				//registroTag.setActivo(true);
				//registroTag.setFechaFin(calendar.getTime());
				//registroTagService.save(registroTag);
				parkingUsuarioService.save(usuario);
				//conn.close();


				return new ResponseEntity<>(vista, HttpStatus.OK);
			}
			//return new ResponseEntity<>(lista, HttpStatus.OK);

		} catch (JSONException e) {
			System.out.println("Error de json: " + e.getMessage());
		} catch(IOException | InterruptedException ex) {
			System.out.println("Error de IO exception: " + ex.getMessage());
		} catch (Exception e) {
			System.out.println("No pude activar el chip de: " + horarioId + " => " + e.getMessage());
			//e.printStackTrace();
		}
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}*/

	
	@GetMapping("/consultarCliente/{horarioId}")
	@ResponseBody
	public ResponseEntity<?> consultarCliente(@PathVariable("horarioId") int horarioId){

		JSONObject resp=new JSONObject();
		Cliente cliente=clienteService.findById(horarioId);
		List<ParkingUsuario> parkingUsuario;
		List<CarroDTO> vistaLista=new ArrayList<CarroDTO>();
		String nombreCompleto="";
		String club="";
		long membresia=0;
		int idCliente=0;
		String tipoCliente="";
		if(cliente==null) {
			RHEmpleado empleado =rhempleadoService.getOne(horarioId-90000).get();
			if(empleado==null) {
				resp.put("respuesta", "El id del cliente no existe o no tiene ningun chip registrado");
				return new ResponseEntity<>(resp.toString(), HttpStatus.NOT_FOUND);
			}
			parkingUsuario=parkingUsuarioService.findByRhEmpleado(empleado);
			nombreCompleto=parkingUsuario.get(0).getRhEmpleado().getEmpleado();
			club=parkingUsuario.get(0).getRhEmpleado().getClub();
			idCliente=parkingUsuario.get(0).getRhEmpleado().getIdEmpleado();
			tipoCliente="EMPLEADO";
		}else {
			parkingUsuario=parkingUsuarioService.findByIdCliente(cliente);
			if(parkingUsuario.size()==0) {
				resp.put("respuesta", "El usuario no tiene ningun chip registrado");
				return new ResponseEntity<>(resp.toString(), HttpStatus.NOT_FOUND);
			}
			nombreCompleto=parkingUsuario.get(0).getCliente().getNombre()+" "+parkingUsuario.get(0).getCliente().getApellidoPaterno()+" "+parkingUsuario.get(0).getCliente().getApellidoMaterno();
			club=parkingUsuario.get(0).getCliente().getClub().getNombre();
			 membresia=parkingUsuario.get(0).getCliente().getNoMembresia();
			idCliente=parkingUsuario.get(0).getCliente().getIdCliente();
			tipoCliente=parkingUsuario.get(0).getCliente().getTipoCliente().getNombre();
		}
		for(int i=0;i<parkingUsuario.size();i++) {
			CarroDTO vista=new CarroDTO();
			Carro carro=parkingUsuario.get(i).getCarro().get(0);
			if(carro.isActivo()) {
				vista.setClub(club);
				vista.setIdCliente(idCliente);
				vista.setIdVentaDetalle(parkingUsuario.get(i).getIdVentaDetalle());
				vista.setMembresia(membresia);
				vista.setNombreCompleto(nombreCompleto);
				vista.setTipoCliente(tipoCliente);
				vista.setAnio(carro.getAnio());
				vista.setColor(carro.getColor());
				vista.setId(carro.getId());
				vista.setMarca(carro.getMarca());
				vista.setModelo(carro.getModelo());
				vista.setNoSerie(carro.getNoSerie());
				vista.setPlaca(carro.getPlaca());
				vista.setVigencia(carro.getVigencia());
				RegistroTag chip=registroTagService.findByParking(parkingUsuario.get(i));
				vista.setIdChip(chip.obtenerIdChip());
				vistaLista.add(vista);
			}
			
		}
		return new ResponseEntity<>(vistaLista, HttpStatus.OK);
	
}
	
	@PostMapping("/actualizarCarro")
	@ResponseBody
	public ResponseEntity<?> actualizarCarro(@RequestBody CarroDTO carrodto){
		ParkingUsuario parkingUsuario=parkingUsuarioService.getOne(carrodto.getIdVentaDetalle()).get();
		Carro carroViejo=parkingUsuario.getCarro().get(0);
		carroViejo.setAnio(carrodto.getAnio());
		carroViejo.setColor(carrodto.getColor());
		carroViejo.setMarca(carrodto.getMarca());
		carroViejo.setModelo(carrodto.getModelo());
		carroViejo.setNoSerie(carrodto.getNoSerie());
		carroViejo.setParkingUsuario(parkingUsuario);
		carroViejo.setPlaca(carrodto.getPlaca());
		carroService.save(carroViejo);
   		JSONObject json=new JSONObject();
		json.put("respuesta", "informacion guardada correctamente");
	   	
	   		return new ResponseEntity<>(json.toString(), HttpStatus.OK);
	}
	
	
	@PostMapping("/carroNuevo")
	@ResponseBody
	public ResponseEntity<?> crearCarro(@RequestBody CarroDTO carrodto){
		Session currentSession = entityManager.unwrap(Session.class);
		Query<ParkingUsuario> listaUsuarios;
        	
        	listaUsuarios = currentSession.createNativeQuery("Select * from parking_usuario WHERE"
        			+ " ID_VENTA_DETALLE = (SELECT ID_VENTA_DETALLE FROM parking_usuario where idcliente = "+carrodto.getIdCliente() +
					" and capturado = false and extract(MONTH from age(CURRENT_DATE, f_compra)) < 1)"
        			+ " and capturado=false;",ParkingUsuario.class);
       try {
    	   List<ParkingUsuario> lista= listaUsuarios.getResultList();
	   		Carro carro=new Carro();
	   		carro.setAnio(carrodto.getAnio());
	   		carro.setColor(carrodto.getColor());
	   		carro.setMarca(carrodto.getMarca());
	   		carro.setModelo(carrodto.getModelo());
	   		carro.setNoSerie(carrodto.getNoSerie());
	   		carro.setPlaca(carrodto.getPlaca());
	   		carro.setVigencia(carrodto.getVigencia());
	   		carro.setParkingUsuario(lista.get(0));
	   		carro.setActivo(true);
	   		carro=carroService.save(carro);
	   		lista.get(0).getCarro().add(carro);
	   		lista.get(0).setCapturado(true);

			Cliente customer = clienteService.findById(carrodto.getIdCliente());
			if (customer.getTipoCliente().getNombre().toLowerCase().contains("empleado")) {
				// Vigencia al ultimo dia del año en chips de empleados
				Calendar calendar = Calendar.getInstance();
				int year = calendar.get(Calendar.YEAR);
				calendar.set(Calendar.YEAR, year);
				calendar.set(Calendar.MONTH, Calendar.DECEMBER); // Diciembre es el mes 11 (0-indexado)
				calendar.set(Calendar.DAY_OF_MONTH, 31);

				calendar.set(Calendar.HOUR_OF_DAY, 23);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				calendar.set(Calendar.MILLISECOND, 0);

				Date lastDayOfYear = calendar.getTime();
				lista.get(0).obtenerRegistroTag().setFechaFin(lastDayOfYear);
			} else if (customer.getTipoCliente().getNombre().toLowerCase().contains("temporal")) {
			   // Convertir Date a LocalDateTime y sumar 6 meses en una sola linea
			   LocalDateTime expirationDate = LocalDateTime.ofInstant(lista.get(0).getFechaCaptura().toInstant(), ZoneId.systemDefault()).plusMonths(6).with(LocalTime.MAX.withNano(0));
			   // Convertir LocalDateTime a Instant usando la zona horaria del sistema
			   Instant instant = expirationDate.atZone(ZoneId.systemDefault()).toInstant();
			   lista.get(0).obtenerRegistroTag().setFechaFin(Date.from(instant));
		   } else
			   lista.get(0).obtenerRegistroTag().setFechaFin(carrodto.getVigencia());
	   		lista.get(0).obtenerRegistroTag().setActivo(true);
	   		parkingUsuarioService.save(lista.get(0));
	   		
	   		JSONObject json=new JSONObject();
	   		json.put("respuesta", "informacion guardada correctamente");
	   	
	   		return new ResponseEntity<>(json.toString(), HttpStatus.OK);
       }catch(Exception e) {
    	   e.printStackTrace();
		   System.out.println("Error al resgitrar carro: " + e.getMessage());
    	   JSONObject json=new JSONObject();
    	   json.put("respuesta", "ocurrio un error buscando los datos");
    	   
    	 
    	   
	   		return new ResponseEntity<>(json.toString(), HttpStatus.BAD_REQUEST);
       }
		
	}
	
	@GetMapping("/obtenerEmpleado/{horarioId}")
	@ResponseBody
	public ResponseEntity<?> obtenerEmpleado(@PathVariable("horarioId") int horarioId){
		RHEmpleado empleado=rhempleadoService.findByIdEmpleado(horarioId);
		
		
		return new ResponseEntity<>(empleado, HttpStatus.OK);	
	}
	
	
	@PostMapping("/carroNuevoEmpleado")
	@ResponseBody
	public ResponseEntity<?> carroNuevoEmpleado(@RequestBody CarroDTO carrodto){
		Session currentSession = entityManager.unwrap(Session.class);
		javax.persistence.Query query = currentSession.createNativeQuery("Select count(*) from parking_usuario WHERE id_venta_detalle<0;");
       try {
    	   
    	   int count = ((Number) query.getSingleResult()).intValue();
    	   ParkingUsuario parkingUsuario=new ParkingUsuario();
    	   parkingUsuario.setActivo(true);
    	   parkingUsuario.setCantidad(1);
    	   parkingUsuario.setCapturado(false);
    	   parkingUsuario.setClub(carrodto.getClub());
    	   parkingUsuario.setConcepto("Chip Tag");
    	   parkingUsuario.setFechaCaptura(new Date());
    	   parkingUsuario.setIdProd(1441);
    	   parkingUsuario.setIdVentaDetalle((-count)-1);
    	   parkingUsuario.setObservaciones(String.valueOf(carrodto.getIdChip()));
    	   parkingUsuario.setEstadoCobranza("Activo - Al Corriente");
    	   parkingUsuario.setPk(true);
    	   List<Carro> nuevo=new ArrayList<Carro>();
    	   parkingUsuario.setCarro(nuevo);
    	   
    	   RegistroTag registroTag= registroTagService.findByIdChip(carrodto.getIdChip());
    	   if(registroTag.isActivo()) {
    		   JSONObject json=new JSONObject();
        	   json.put("respuesta", "El chip que ingresaste ya se encuentra ocupado, revisa la información ingresada.");
        	   
    	   		return new ResponseEntity<>(json.toString(), HttpStatus.BAD_REQUEST);
    	   }    		   
    	   registroTag.setParking(parkingUsuario);
    	   registroTagService.save(registroTag);
    	   parkingUsuario.setRegistroTag(registroTagService.findByIdChip(carrodto.getIdChip()));
    	   parkingUsuario.setRhEmpleado(rhempleadoService.findByIdEmpleado(carrodto.getIdEmpleado()));
    	   parkingUsuario=parkingUsuarioService.save(parkingUsuario);
    	   
	   		Carro carro=new Carro();
	   		carro.setAnio(carrodto.getAnio());
	   		carro.setColor(carrodto.getColor());
	   		carro.setMarca(carrodto.getMarca());
	   		carro.setModelo(carrodto.getModelo());
	   		carro.setNoSerie(carrodto.getNoSerie());
	   		carro.setPlaca(carrodto.getPlaca());
	   		carro.setVigencia(carrodto.getVigencia());
	   		carro.setParkingUsuario(parkingUsuario);
	   		carro.setActivo(true);
	   		carro=carroService.save(carro);
	   		parkingUsuario.getCarro().add(carro);
	   		parkingUsuario.setCapturado(true);
	   		parkingUsuario.obtenerRegistroTag().setFechaFin(carrodto.getVigencia());
	   		parkingUsuario.obtenerRegistroTag().setActivo(true);
	   		parkingUsuarioService.save(parkingUsuario);
	   		
	   		JSONObject json=new JSONObject();
	   		json.put("respuesta", "informacion guardada correctamente");
	   	
	   		return new ResponseEntity<>(json.toString(), HttpStatus.OK);
       }catch(Exception e) {
    	   e.printStackTrace();
    	   JSONObject json=new JSONObject();
    	   json.put("respuesta", "ocurrio un error buscando los datos");
    	   
	   		return new ResponseEntity<>(json.toString(), HttpStatus.BAD_REQUEST);
       }
		
	}
	
	@PostMapping("/registrarChip")
	@ResponseBody
	public ResponseEntity<?> registrarChip(@RequestBody Body body){
		int count=0;
		for(long i=body.getFecha();i<=body.getIdChip();i++) {
			RegistroTag chip=new RegistroTag();
			chip.setActivo(false);
			chip.setClub(body.getClub());
			chip.setIdChip(i);
			RegistroTag registro=registroTagService.findByIdChip(i);
			if(registro==null) {
				try {
					registroTagService.save(chip);
					count++;
					
				}catch(Exception e) {
					
				}
				
			}
		}
		JSONObject json=new JSONObject();
		json.put("respuesta", "chips almacenado exitosamente");
		
		
		return new ResponseEntity<>(count, HttpStatus.OK);
		
	} 
	@PostMapping("/actualizarChip")
	@ResponseBody
	public ResponseEntity<?> actualizarChip(@RequestBody Body body){
   		JSONObject resp=new JSONObject();
   		try {
   			RegistroTag chipNuevo=registroTagService.findByIdChip(body.getIdChipNuevo());
   			if(chipNuevo==null) {
   	   			resp.put("respuesta", "el id del chip no existe");
   	   			return new ResponseEntity<>(resp.toString(), HttpStatus.NOT_FOUND);
   			}
   			if(chipNuevo.isActivo()) {
   	   			resp.put("respuesta", "el id de chip nuevo ya se encuentra activo");
   	   			return new ResponseEntity<>(resp.toString(), HttpStatus.CONFLICT);
   				
   			}
   			RegistroTag chipAnterior=registroTagService.findByIdChip(body.getIdChipAnterior());
   			if(!chipAnterior.getClub().equals(chipNuevo.getClub())) {
   	   			resp.put("respuesta", "Los chips deben pertenecer al mismo club");
   	   			return new ResponseEntity<>(resp.toString(), HttpStatus.CONFLICT);   				
   			}
   			int id=chipAnterior.getParking();
   			ParkingUsuario parking=parkingUsuarioService.getOne(id).get();
   			chipNuevo.setParking(parking);
   			parking.setObservaciones(chipNuevo.getIdChip());
   			parkingUsuarioService.save(parking);
   			chipNuevo.setFechaFin(chipAnterior.getFechaFin());
   			chipNuevo.setActivo(true);
   			chipAnterior.setFechaFin(null);
   			chipAnterior.setParking(null);
   			chipAnterior.setActivo(false);
   			registroTagService.save(chipAnterior);
   			registroTagService.save(chipNuevo);
   			resp.put("respuesta", "El cambio de chip se realizó de manera exitosa");
   			return new ResponseEntity<>(resp.toString(), HttpStatus.OK);   		
   		}catch(Exception e) {
   			e.printStackTrace();
   			resp.put("respuesta", "Ocurrio un error desconocido");
   			return new ResponseEntity<>(resp.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
   			
   		}
		
	   	
	}
	
	@PostMapping("/obtenerPlumasByClub")
	@ResponseBody
	public ResponseEntity<?> obtenerPlumasByClub(@RequestBody Body body){
		JSONObject json=new JSONObject();
		try {
			List<Pluma> plumas=plumaService.findByClub(body.getClub()).get();
			
			return new ResponseEntity<>(plumas, HttpStatus.OK);
			
		}catch(Exception e) {
			json.put("respuesta", "ya existe un chip con ese id");
			
			
			
			return new ResponseEntity<>(json.toString(), HttpStatus.BAD_REQUEST);
		}
	} 
	
	@PostMapping("/obtenerChipCarByClub")
	@ResponseBody
	public ResponseEntity<?> obtenerChipCarByClub(@RequestBody Body body){
		JSONObject json=new JSONObject();
		try {
			Session currentSession = entityManager.unwrap(Session.class);
			Query<ChipCarro> listaRegistro;
	        listaRegistro = currentSession.createNativeQuery("Select * from chip_carro WHERE  club='"+body.getClub()+"';",ChipCarro.class);
			List<ChipCarro> lista= listaRegistro.getResultList();
			
			
			
			return new ResponseEntity<>(lista, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			json.put("respuesta", "error al procesar la tabla");
			
			return new ResponseEntity<>(json.toString(), HttpStatus.BAD_REQUEST);
		}
	} 
	
	
	@GetMapping("/registro/{horarioId}")
	@ResponseBody
	public ResponseEntity<?> registroVehicular(@PathVariable("horarioId") int horarioId){
		try {
			Session currentSession = entityManager.unwrap(Session.class);
			Query<RegistroVehicular> listaRegistro;
	        listaRegistro = currentSession.createNativeQuery("Select * from registro_vehicular WHERE  id_cliente="+horarioId+";",RegistroVehicular.class);
			List<RegistroVehicular> lista= listaRegistro.getResultList();
			
			
			
			return new ResponseEntity<>(lista, HttpStatus.OK);
		}catch(Exception e) {
			JSONObject json=new JSONObject();
			json.put("respuesta", "error al procesar la tabla");
			
			return new ResponseEntity<>(json.toString(), HttpStatus.BAD_REQUEST);
		}	
	}
	
	@GetMapping(value= {"/finalizarTurno/{horarioId}/{lugarVenta}","/finalizarTurno/{horarioId}"})
	@ResponseBody
   	@Transactional
	public ResponseEntity<?> finalizarTurno(@PathVariable("horarioId") String horarioId,@PathVariable(required = false,value="lugarVenta") String lugarVenta){
		
			Session currentSession = entityManager.unwrap(Session.class);
			int countUpdate;
			if(lugarVenta ==null){
				 countUpdate = currentSession.createNativeQuery("update estacionamiento_externo set turno=(select COALESCE(cast(max(cast("
				 		+ "turno as integer)+1)as character varying(255)),'1') from estacionamiento_externo where club='"+horarioId+"')  "
				 				+ "where turno is null and club ='"+horarioId+"' and lugar_venta='caseta';").executeUpdate();
			}
			 else{
				 countUpdate = currentSession.createNativeQuery("update estacionamiento_externo set turno=(select COALESCE(cast(max(cast("
				 		+ "turno as integer)+1)as character varying(255)),'1') from estacionamiento_externo where club='"+horarioId+"')  "
				 				+ "where turno is null and club ='"+horarioId+"' and lugar_venta='"+lugarVenta+"';").executeUpdate();

			 }
			JSONObject json=new JSONObject();
		json.put("respuesta", "se han contado "+countUpdate+" para este turno");
		
		
		
		
		Query resultado = currentSession.createNativeQuery("select COALESCE(cast(max(cast("
		 		+ "turno as integer))as character varying(255)),'1') from estacionamiento_externo where club='"+horarioId+"'");
		if(lugarVenta==null) {
			lugarVenta="caseta";
		}
	 	String folio = (String) resultado.getSingleResult();
		RegistroCortes registroNuevo=new RegistroCortes();
		registroNuevo.setClub(horarioId);
		registroNuevo.setFechaCorte(new Date());
		registroNuevo.setIdCorte(folio);
		registroNuevo.setLugarCorte(lugarVenta);
		registroNuevo.setTotalQr(countUpdate);
		if(registroCortesService.findByIdCorte(folio)==null)
			registroCortesService.save(registroNuevo);		
		
		
		return new ResponseEntity<>(json.toString(), HttpStatus.OK);
	}
	
	@GetMapping(value= {"/estacionamientoExterno/{horarioId}/{lugarVenta}","/estacionamientoExterno/{horarioId}"})
	@ResponseBody
	public ResponseEntity<?> crearQR(@PathVariable("horarioId") String horarioId,@PathVariable(required = false,value="lugarVenta") String lugarVenta){
		List<EstacionamientoExterno> estacionamientoExterno=estacionamientoExternoService.list(horarioId);
		String id = "";
		int tamanio=0;
		if(estacionamientoExterno.size()!=0) {
			tamanio=Integer.parseInt(estacionamientoExterno.get(0).getIdRegistro())+1;
		}
		int decimas=0;
		while(tamanio>=10) {
			tamanio=(tamanio)/10;
			decimas++;
		}

		for(int i=0;i<5-decimas;i++) {
			id=id+"0";
		}

		int dato=1;
		if(estacionamientoExterno.size()!=0) {
			dato=Integer.parseInt(estacionamientoExterno.get(0).getIdRegistro())+1;
		}
		id=id+dato;

		EstacionamientoExterno estacionamiento=new EstacionamientoExterno();
		estacionamiento.colocarHoraEntrada(new Date());
		estacionamiento.setIdRegistro(id);
		 estacionamiento.setAcceso(true);
		 estacionamiento.setPagado(true);
		 estacionamiento.setQr(true);
		
		estacionamiento.setCostoHora(15);
		estacionamiento.setHoraSalida(new Timestamp(new Date().getTime()));
		long between = (estacionamiento.obtenerHoraSalida().getTime () -estacionamiento.obtenerHoraEntrada().getTime ()) / 1000; 
		 int hour1=(int) (between%(24*3600)/3600);
		 estacionamiento.setHoras(hour1);
		 estacionamiento.setCostoTotal(estacionamiento.getCostoHora());
		 estacionamiento.setClub(horarioId);

		 if(lugarVenta ==null)
			 estacionamiento.setLugarVenta("caseta");
		 else {
			 estacionamiento.setLugarVenta(lugarVenta);
		 }
		estacionamientoExternoService.save(estacionamiento);

		if (lugarVenta != null && lugarVenta.equals("ceforma_botonera")) {
			/*QRParkingDTO qrParking = new QRParkingDTO();
			qrParking.setClub(horarioId);
			qrParking.setCosto(qrEstacionamientoCostoRepository.findById(11).get().getCost());
			qrParking.setDebito(qrEstacionamientoCostoRepository.findById(11).get().getCost());
			qrParking.setIdUsuario(11);
			qrParking.setPagado(true);
			qrParking.setCambio(0);
			qrParking.setDevuelto(true);
			qrParking.setObservaciones("Qr botonera ceforma");
			qrParking.setIdRegistro(id);
			qrParkingService.save(qrParking);
			estacionamiento.setActivo(true);*/
			estacionamientoExternoService.save(estacionamiento);
		}
		
		return new ResponseEntity<>(estacionamiento, HttpStatus.OK);
	}
	
	/*@GetMapping("/estacionamientoExterno/{horarioId}")
	@ResponseBody
	public ResponseEntity<?> estacionamientoExterno(@PathVariable("horarioId") String horarioId){
		List<EstacionamientoExterno> estacionamientoExterno=estacionamientoExternoService.list();
		String id = "";
		int tamanio=estacionamientoExterno.size()+1;
		int decimas=0;
		while(tamanio>0) {
			tamanio=(tamanio)/10;
			decimas++;
		}
		for(int i=0;i<6-decimas;i++) {
			id=id+"0";
		}
		int dato=estacionamientoExterno.size()+1;
		id=id+dato;
		EstacionamientoExterno estacionamiento=new EstacionamientoExterno();
		estacionamiento.colocarHoraEntrada(new Date());
		estacionamiento.setIdRegistro(id);
		 estacionamiento.setAcceso(true);
		 estacionamiento.setPagado(true);
		 estacionamiento.setQr(true);
		estacionamiento=estacionamientoExternoService.save(estacionamiento);
		//---------------------segundo endpoint registroEstacionamientoExterno----------------------------------
		//estacionamientoExternoService.save(estacionamiento);
			
		
		//---------------------tercer endpoint cobroSalida-----------------------------
		--estacionamientoExterno.setCostoHora(15);
		estacionamientoExterno.setHoraSalida(new Timestamp(new Date().getTime()));
		
		 long between = (estacionamientoExterno.obtenerHoraSalida().getTime () -estacionamientoExterno.obtenerHoraEntrada().getTime ()) / 1000; 
		 int hour1=(int) (between%(24*3600)/3600);
		estacionamientoExterno.setHoras(hour1);--
		estacionamiento.setCostoHora(15);
		estacionamiento.setHoraSalida(new Timestamp(new Date().getTime()));
		long between = (estacionamiento.obtenerHoraSalida().getTime () -estacionamiento.obtenerHoraEntrada().getTime ()) / 1000; 
		 int hour1=(int) (between%(24*3600)/3600);
		 estacionamiento.setHoras(hour1);
		//estacionamientoExterno.setCostoTotal(hour1*estacionamientoExterno.getCostoHora());
		 estacionamiento.setCostoTotal(estacionamiento.getCostoHora());

		//----------------------------cuarto endpoint pagar-------------------------------------
		 
		 Calendar calendar = Calendar.getInstance();
		 calendar.setTime(estacionamiento.obtenerHoraEntrada());
		 int dia =  calendar.get(Calendar.DAY_OF_WEEK);
		 if(dia>=2 && dia<=6) {
			 int horaDia=calendar.get(Calendar.HOUR_OF_DAY);
			 if(horaDia >6 && horaDia<14) {
				 estacionamiento.setTurno("Primer Turno");
			 }else if(horaDia>=14 && horaDia < 22) {
				 estacionamiento.setTurno("Segundo Turno");
			 }else if(horaDia>=22 && horaDia <= 24) {
				 estacionamiento.setTurno("Tercer Turno");
			 }else if(horaDia>=0 && horaDia <= 1) {
				 estacionamiento.setTurno("Tercer Turno");
			 }
		 }else {
			 estacionamiento.setTurno("Turno Completo");
		 }
		 
		 estacionamiento.setClub(horarioId);
		
		estacionamientoExternoService.save(estacionamiento);
		
		return new ResponseEntity<>(estacionamiento, HttpStatus.OK);
	}*/
	
	@GetMapping(value= {"/perdidaQR/{horarioId}/{lugarVenta}","/perdidaQR/{horarioId}"})
	@ResponseBody
	public ResponseEntity<?> perdidaQR(@PathVariable("horarioId") String horarioId,@PathVariable(required = false,value="lugarVenta") String lugarVenta){
		List<EstacionamientoExterno> estacionamientoExterno=estacionamientoExternoService.list(horarioId);
		String id = "";
		int tamanio=0;
		if(estacionamientoExterno.size()!=0) {
			tamanio=Integer.parseInt(estacionamientoExterno.get(0).getIdRegistro())+1;
		}
		int decimas=0;
		while(tamanio>=10) {
			tamanio=(tamanio)/10;
			decimas++;
		}
		for(int i=0;i<5-decimas;i++) {
			id=id+"0";
		}
		int dato=1;
		if(estacionamientoExterno.size()!=0) {
			dato=Integer.parseInt(estacionamientoExterno.get(0).getIdRegistro())+1;
		}
		id=id+dato;
		EstacionamientoExterno estacionamiento=new EstacionamientoExterno();
		estacionamiento.colocarHoraEntrada(new Date());
		estacionamiento.setIdRegistro(id);
		 estacionamiento.setAcceso(true);
		 estacionamiento.setPagado(true);
		 estacionamiento.setQr(false);
		estacionamiento=estacionamientoExternoService.save(estacionamiento);
		estacionamiento.setCostoHora(100);
		estacionamiento.setHoraSalida(new Timestamp(new Date().getTime()));
		long between = (estacionamiento.obtenerHoraSalida().getTime () -estacionamiento.obtenerHoraEntrada().getTime ()) / 1000; 
		 int hour1=(int) (between%(24*3600)/3600);
		 estacionamiento.setHoras(hour1);
		//estacionamientoExterno.setCostoTotal(hour1*estacionamientoExterno.getCostoHora());
		 estacionamiento.setCostoTotal(estacionamiento.getCostoHora());

		
		 Caseta caseta=casetaService.getOne(1).get();
		 estacionamiento.setIdCaseta(caseta);
		 estacionamiento.setClub(horarioId);
		 if(lugarVenta ==null)
			 estacionamiento.setLugarVenta("caseta");
		 else
			 estacionamiento.setLugarVenta(lugarVenta);
		estacionamientoExternoService.save(estacionamiento);
		
		
		
		
		return new ResponseEntity<>(estacionamiento, HttpStatus.OK);
	}
	
	@GetMapping("/crearCortesiaQR/{horarioId}")
	@ResponseBody
	public ResponseEntity<?> crearCortesiaQR(@PathVariable("horarioId") String horarioId){
		
		List<QRCortesia> estacionamientoExterno=qrCortesiaService.list();
		String id = "";
		int tamanio=estacionamientoExterno.size()+1;
		int decimas=0;
		while(tamanio>0) {
			tamanio=(tamanio)/10;
			decimas++;
		}
		for(int i=0;i<6-decimas;i++) {
			id=id+"0";
		}
		id=1+id;
		int dato=estacionamientoExterno.size()+1;
		id=id+dato;
		QRCortesia estacionamiento=new QRCortesia();
		estacionamiento.setIdRegistro(id);
		 estacionamiento.setQr(true);
		estacionamiento.setCostoHora(0);

		 estacionamiento.setClub(horarioId);
		
		 estacionamiento=qrCortesiaService.save(estacionamiento);
		
		
		JSONObject json=new JSONObject();
		json.put("respuesta", "almacenado correctamente");
		return new ResponseEntity<>(json.toString(), HttpStatus.OK);
	}
	
	@GetMapping("/cortesiaQR/{horarioId}")
	@ResponseBody
	public ResponseEntity<?> cortesiaQR(@PathVariable("horarioId") String horarioId){
		
		List<QRCortesia> estacionamientoExterno=qrCortesiaService.findByRedimido(false);
		int i=0;
		QRCortesia estacionamiento=null;
		JSONObject respuesta=new JSONObject ();
		if(estacionamientoExterno.size()==0) {
			respuesta.put("respuesta", "No hay qr de cortesía disponibles en este momento");
			return new ResponseEntity<>(respuesta.toString(), HttpStatus.CONFLICT);
			//return new ResponseEntity<>("", HttpStatus.CONFLICT);
		}
		
		while(i<estacionamientoExterno.size()) {
			QRCortesia temp=estacionamientoExterno.get(i);
			if(temp.getClub().equals(horarioId) && !temp.isAcceso()) {
				estacionamiento=estacionamientoExterno.get(i);
				break;
			}
			i++;
		}
		if(estacionamiento==null) {
			respuesta.put("respuesta", "No hay qr de cortesía disponibles en este momento para este club");
			return new ResponseEntity<>(respuesta.toString(), HttpStatus.CONFLICT);
			//return new ResponseEntity<>("", HttpStatus.CONFLICT);
		}
		estacionamiento.colocarHoraEntrada(new Date());
		 estacionamiento.setAcceso(true);
		 estacionamiento.setPagado(true);
		 estacionamiento.setQr(true);
		estacionamiento=qrCortesiaService.save(estacionamiento);
		
		estacionamiento.setCostoHora(0);
		estacionamiento.setHoraSalida(new Timestamp(new Date().getTime()));
		long between = (estacionamiento.obtenerHoraSalida().getTime () -estacionamiento.obtenerHoraEntrada().getTime ()) / 1000; 
		 int hour1=(int) (between%(24*3600)/3600);
		 estacionamiento.setHoras(hour1);
		//estacionamientoExterno.setCostoTotal(hour1*estacionamientoExterno.getCostoHora());
		 estacionamiento.setCostoTotal(estacionamiento.getCostoHora());

		//----------------------------cuarto endpoint pagar-------------------------------------
		 
		 Calendar calendar = Calendar.getInstance();
		 calendar.setTime(estacionamiento.obtenerHoraEntrada());
		 int dia =  calendar.get(Calendar.DAY_OF_WEEK);
		 if(dia>=2 && dia<=6) {
			 int horaDia=calendar.get(Calendar.HOUR_OF_DAY);
			 if(horaDia >6 && horaDia<14) {
				 estacionamiento.setTurno("Primer Turno");
			 }else if(horaDia>=14 && horaDia < 22) {
				 estacionamiento.setTurno("Segundo Turno");
			 }else if(horaDia>=22 && horaDia <= 24) {
				 estacionamiento.setTurno("Tercer Turno");
			 }else if(horaDia>=0 && horaDia <= 1) {
				 estacionamiento.setTurno("Tercer Turno");
			 }
		 }else {
			 estacionamiento.setTurno("Turno Completo");
		 }
		 Caseta caseta=casetaService.getOne(1).get();
		 estacionamiento.setIdCaseta(caseta);
		 estacionamiento.setClub(horarioId);
		
		qrCortesiaService.save(estacionamiento);
		if(horarioId.equals("SP")) {
			List<PaseUsuario> paseUsuarios=new ArrayList<PaseUsuario>();
			PaseUsuario paseUsuario=new PaseUsuario();
			paseUsuario.setActivo(true);
			paseUsuario.setCantidad(0);
			paseUsuario.setConcepto("Cortesias SP");
			paseUsuario.setConsumido(0);
			paseUsuario.setCreated(new Date());
			paseUsuario.setCreatedBy("");
			paseUsuario.setDisponibles(0);
			paseUsuario.setF_compra(new Date());
			paseUsuario.setIdProd(1808);
			paseUsuario.setUpdated(new Date());
			paseUsuario.setUpdatedBy("");
			paseUsuario.setIdVentaDetalle(Integer.parseInt(estacionamiento.getIdRegistro()));
			paseUsuarios.add(paseUsuario);
			return new ResponseEntity<>(paseUsuarios, HttpStatus.OK);
		}
		return new ResponseEntity<>(estacionamiento, HttpStatus.OK);
	}
	
	/*@PostMapping("/registroEstacionamientoExterno")
	@ResponseBody
	public ResponseEntity<?> registroEstacionamientoExterno(@RequestBody EstacionamientoExterno estacionamientoExterno){
		
		
	} */
	
	
	/*@GetMapping("/cobroSalida/{horarioId}")
	@ResponseBody
	public ResponseEntity<?> cobroSalida(@PathVariable("horarioId") String horarioId){
		
		EstacionamientoExterno estacionamientoExterno=estacionamientoExternoService.getByIdRegistro(horarioId);
		if(estacionamientoExterno.isPagado()) {
			JSONObject json=new JSONObject();
			json.put("respuesta", "no se puede consultar este registro porque ya fue pagado");
			return new ResponseEntity<>(json.toString(),HttpStatus.CONFLICT);
		}else {
			estacionamientoExterno.setCostoHora(15);
			estacionamientoExterno.setHoraSalida(new Timestamp(new Date().getTime()));
			
			 long between = (estacionamientoExterno.obtenerHoraSalida().getTime () -estacionamientoExterno.obtenerHoraEntrada().getTime ()) / 1000; 
			 int hour1=(int) (between%(24*3600)/3600);
			estacionamientoExterno.setHoras(hour1);
			estacionamientoExterno.setCostoHora(15);
			estacionamientoExterno.setHoraSalida(new Timestamp(new Date().getTime()));
			long between = (estacionamientoExterno.obtenerHoraSalida().getTime () -estacionamientoExterno.obtenerHoraEntrada().getTime ()) / 1000; 
			 int hour1=(int) (between%(24*3600)/3600);
			estacionamientoExterno.setHoras(hour1);
			
			//estacionamientoExterno.setCostoTotal(hour1*estacionamientoExterno.getCostoHora());
			estacionamientoExterno.setCostoTotal(estacionamientoExterno.getCostoHora());
			
			estacionamientoExternoService.save(estacionamientoExterno);
			return new ResponseEntity<>(estacionamientoExterno, HttpStatus.OK);
			
		}
		
	}*/
	
	/*@GetMapping("/pagar/{horarioId}")
	@ResponseBody
	public ResponseEntity<?> pagar(@PathVariable("horarioId") String horarioId){
		EstacionamientoExterno estacionamientoExterno=estacionamientoExternoService.getByIdRegistro(horarioId);
		estacionamientoExterno.setPagado(true);
		
		estacionamientoExternoService.save(estacionamientoExterno);
		
		
		return new ResponseEntity<>(estacionamientoExterno, HttpStatus.OK);
		
	}*/
	
	@PostMapping("/registrarEvento")
	@ResponseBody
	public ResponseEntity<?> registrarEvento(@RequestBody Body body){
		if(body.getIdAntena()!=0 || body.getIdTipoAcceso()==-1) {
			try {
				RegistroParking registroParking=new RegistroParking();
				Caseta caseta=casetaService.getOne(body.getIdCaseta()).get();
				RegistroTag chip=null;
				try {
					chip=registroTagService.getOne(body.getIdChip()).get();
				}catch(NoSuchElementException e) {
					
				}
				
				TipoAcceso tipoAcceso=tipoAccesoService.getOne(body.getIdTipoAcceso()).get();
				if(body.getIdAntena()!=0) {
					Antena antena=antenaService.getOne(body.getIdAntena()).get();
					registroParking.setIdAntena(antena);
				}
				registroParking.setHoraEvento(new Date());
				 
				registroParking.setHoraEntrada(this.hora(new Date(body.getFecha())).getTime());
				registroParking.setIdCaseta(caseta);
				registroParking.setIdChip(chip);
				registroParking.setTipoAcceso(tipoAcceso);
				registroParking.setRaw(body.getRaw());
				registroParking.setMensaje(body.getMensaje());
				registroParking.setAcceso(body.isAcceso());
				registroParking=registroParkingService.save(registroParking);
				
				JSONObject json=new JSONObject();
				json.put("respuesta", "evento registrado exitosamente");
				return new ResponseEntity<>(json.toString(), HttpStatus.OK);
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println(body);
				JSONObject json=new JSONObject();
				json.put("respuesta", "ocurrio un error en el endpoint registrarEvento");
				
				return new ResponseEntity<>(json.toString(), HttpStatus.BAD_REQUEST);
			}
		}else {
			int raw = 0;
			try {
				raw = Integer.parseInt(body.getRaw().trim().replaceFirst("^0+(?!$)", ""));
			}
			catch(Exception ex){
				ex.printStackTrace();
				System.out.println("Error al parsear:["+body.getRaw()+"]");
			}
			if (raw!=0)
			if(raw > 1000000 && body.getIdTipoAcceso()==4) {
				QRCortesia estacionamientoExterno=qrCortesiaService.getByIdRegistro(body.getRaw());
				Caseta caseta=casetaService.getOne(body.getIdCaseta()).get();
				 TipoAcceso tipoAcceso=tipoAccesoService.getOne(body.getIdTipoAcceso()).get();
				 estacionamientoExterno.setHoraSalida(new Date(body.getFecha()));
				long between = (estacionamientoExterno.obtenerHoraSalida().getTime () -estacionamientoExterno.obtenerHoraEntrada().getTime ()) / 1000; 
				 int hour1=Math.abs((int) (between%(24*3600)/3600));
				 estacionamientoExterno.setHoras(hour1);
				 estacionamientoExterno.setIdCaseta(caseta);
				 estacionamientoExterno.setMensaje(body.getMensaje());
				 estacionamientoExterno.setAcceso(body.isAcceso());
				 estacionamientoExterno.setTipoAcceso(tipoAcceso);
				 estacionamientoExterno.setRedimido(true);
				qrCortesiaService.save(estacionamientoExterno);
			}else {
				Caseta caseta=casetaService.getOne(body.getIdCaseta()).get();
				String club=caseta.getClub().getNombre();
				if(club.equals("Club Alpha 3")) {
					club="A3";
				}else if(club.equals("Club Alpha 2")) {
					club="A2";
				}else if(club.equals("CIMERA")) {
					club="CIM";
				}

				EstacionamientoExterno estacionamientoExterno = null;
				try{
					estacionamientoExterno=estacionamientoExternoService.getByIdRegistro(body.getRaw(),club);	
					TipoAcceso tipoAcceso=tipoAccesoService.getOne(body.getIdTipoAcceso()).get();
					 estacionamientoExterno.setHoraSalida(new Date());
					long between = (estacionamientoExterno.obtenerHoraSalida().getTime () -estacionamientoExterno.obtenerHoraEntrada().getTime ()) / 1000; 
					 int hour1=Math.abs((int) (between%(24*3600)/3600));
					 estacionamientoExterno.setHoras(hour1);
					 estacionamientoExterno.setIdCaseta(caseta);
					 estacionamientoExterno.setMensaje(body.getMensaje());
					 estacionamientoExterno.setAcceso(body.isAcceso());
					 estacionamientoExterno.setTipoAcceso(tipoAcceso);
					 
					estacionamientoExternoService.save(estacionamientoExterno);
					JSONObject json=new JSONObject();
					json.put("respuesta", "evento registrado exitosamente");
					return new ResponseEntity<>(json.toString(), HttpStatus.OK);
					
				}catch(IndexOutOfBoundsException e) {
					System.out.println("\u001B[31m" + "Error en la linea 1188 ParkingController no se encontro un id_registro="+body.getRaw()+" y club "+club + "\u001B[0m");
				}
			}
		}
		JSONObject json=new JSONObject();
		json.put("respuesta", "ocurrio un error en el endpoint registrarEvento");
		return new ResponseEntity<>(json.toString(), HttpStatus.OK);
	}
	
	 /*@GetMapping("/chipsActivos/{idCaseta}")
		@ResponseBody
		@Transactional
		public ResponseEntity<?> chipActivos(@PathVariable("idCaseta") int idCaseta){

		     timeNow = LocalDateTime.now().withNano(0);

			 Caseta caseta=casetaService.getOne(idCaseta).get();
			 String nombre=caseta.getClub().getNombre();
			 Session currentSession = entityManager.unwrap(Session.class);
			 Query<RegistroTag> listaApartadosUsuario;
			 List<RegistroTag> results;
			 //if(caseta.getId()==5) {
			 if(caseta.getId() >= 3 && caseta.getId() <= 5) {
				 if (timeNow.isAfter(timeBefore.plusSeconds(1800))) {
					 listaApartadosUsuario = currentSession.createNativeQuery("update registro_Tag set activo=false where (id_parking in "
							 + "(select id_venta_Detalle from parking_usuario where estado_cobranza='Etapa 1' or estado_cobranza='Etapa 2' or estado_cobranza='Etapa 3'  or "
							 + "estado_cobranza='Baja' AND id_venta_Detalle is not null ) or current_date>fecha_fin) and"
							 + " (club='Futbol City' or club='CIMERA')", RegistroTag.class);
					 listaApartadosUsuario.executeUpdate();
				/*currentSession.createNativeQuery("SELECT id,CASE WHEN estado_cobranza='Etapa 2' or estado_cobranza='Etapa 3'"
						+ "  or estado_cobranza='Baja' or current_date>fecha_fin or REGISTRO_TAG.activo is false THEN false else true end as activo, REGISTRO_TAG.club,fecha_fin,"
						+ "id_chip,id_parking FROM REGISTRO_TAG join parking_usuario on id_venta_Detalle=id_parking WHERE ID_PARKING is not null"
						+ " and (REGISTRO_TAG.club='Futbol City' or REGISTRO_TAG.club='CIMERA')", RegistroTag.class);*/
					 /*timeBefore = LocalDateTime.now().withNano(0);
				 }
				 listaApartadosUsuario=currentSession.createNativeQuery("SELECT * FROM REGISTRO_TAG  WHERE ID_PARKING is not null"
						 + " and (club='Futbol City' or club='CIMERA') and fecha_fin > (current_date - 720)", RegistroTag.class);
				 results = listaApartadosUsuario.getResultList();
			 } if(caseta.getId() >= 6 && caseta.getId() <= 7) {
				 if (timeNow.isAfter(timeBefore.plusSeconds(1800))) {
					 listaApartadosUsuario = currentSession.createNativeQuery("update registro_Tag set activo=false where (id_parking in "
							 + "(select id_venta_Detalle from parking_usuario where estado_cobranza='Etapa 1' or estado_cobranza='Etapa 2' or estado_cobranza='Etapa 3'  or "
							 + "estado_cobranza='Baja' AND id_venta_Detalle is not null ) or current_date>fecha_fin) and"
							 + " (club='"+nombre+"' or club='CIMERA' or club = 'Sports Plaza')", RegistroTag.class);
					 listaApartadosUsuario.executeUpdate();
				/*listaApartadosUsuario = currentSession.createNativeQuery("SELECT id,CASE WHEN estado_cobranza='Etapa 2' or estado_cobranza='Etapa 3'"
						+ "  or estado_cobranza='Baja' or current_date>fecha_fin or REGISTRO_TAG.activo is false THEN false else true end as activo, REGISTRO_TAG.club,fecha_fin,"
						+ "id_chip,id_parking FROM REGISTRO_TAG join parking_usuario on id_venta_Detalle=id_parking WHERE ID_PARKING is not null"
						+ " and (REGISTRO_TAG.club='"+nombre+"' or REGISTRO_TAG.club='CIMERA')", RegistroTag.class);*/
					/* timeBefore = LocalDateTime.now().withNano(0);
				 }
				 listaApartadosUsuario = currentSession.createNativeQuery("SELECT * FROM REGISTRO_TAG  WHERE ID_PARKING is not null"
						 + " and (club='"+nombre+"' or club='CIMERA' or club = 'Sports Plaza') and fecha_fin > (current_date - 720)", RegistroTag.class);
				 results = listaApartadosUsuario.getResultList();
			 }
			 else {
				 if (timeNow.isAfter(timeBefore.plusSeconds(1800))) {
					 listaApartadosUsuario = currentSession.createNativeQuery("update registro_Tag set activo=false where (id_parking in "
							 + "(select id_venta_Detalle from parking_usuario where estado_cobranza='Etapa 1' or estado_cobranza='Etapa 2' or estado_cobranza='Etapa 3'  or "
							 + "estado_cobranza='Baja' AND id_venta_Detalle is not null ) or current_date>fecha_fin) and"
							 + " (club='"+nombre+"' or club='CIMERA')", RegistroTag.class);
					 listaApartadosUsuario.executeUpdate();
				/*listaApartadosUsuario = currentSession.createNativeQuery("SELECT id,CASE WHEN estado_cobranza='Etapa 2' or estado_cobranza='Etapa 3'"
						+ "  or estado_cobranza='Baja' or current_date>fecha_fin or REGISTRO_TAG.activo is false THEN false else true end as activo, REGISTRO_TAG.club,fecha_fin,"
						+ "id_chip,id_parking FROM REGISTRO_TAG join parking_usuario on id_venta_Detalle=id_parking WHERE ID_PARKING is not null"
						+ " and (REGISTRO_TAG.club='"+nombre+"' or REGISTRO_TAG.club='CIMERA')", RegistroTag.class);*/
				/*	 timeBefore = LocalDateTime.now().withNano(0);
				 }
				 listaApartadosUsuario = currentSession.createNativeQuery("SELECT * FROM REGISTRO_TAG  WHERE ID_PARKING is not null"
						 + " and (club='"+nombre+"' or club='CIMERA') and fecha_fin > (current_date - 720)", RegistroTag.class);
				 results = listaApartadosUsuario.getResultList();
			 }
			 return new ResponseEntity<>(results, HttpStatus.OK);
		 //return new ResponseEntity<>("No se puede ejecutar la operacion no ha pasado media hora despues de: " + timeBefore, HttpStatus.CONFLICT);
	}*/

	/*@GetMapping("/chipsActivos/{idCaseta}")
	@ResponseBody
	@Transactional
	public ResponseEntity<?> chipActivos(@PathVariable("idCaseta") int idCaseta){

		timeNow = LocalDateTime.now().withNano(0);

		Caseta caseta=casetaService.getOne(idCaseta).get();
		String nombre=caseta.getClub().getNombre();
		Session currentSession = entityManager.unwrap(Session.class);
		Query<RegistroTag> listaApartadosUsuario;
		List<RegistroTag> results;
		//if(caseta.getId()==5) {
		if(caseta.getId() >= 3 && caseta.getId() <= 5) {
			if (timeNow.isAfter(timeBefore.plusSeconds(1800))) {
				listaApartadosUsuario = currentSession.createNativeQuery("update registro_Tag set activo=false where (id_parking in "
						+ "(select id_venta_Detalle from parking_usuario where estado_cobranza='Etapa 1' or estado_cobranza='Etapa 2' or estado_cobranza='Etapa 3'  or "
						+ "estado_cobranza='Baja' AND id_venta_Detalle is not null ) or current_date>fecha_fin) and"
						+ " (club='Futbol City' or club='CIMERA')", RegistroTag.class);
				listaApartadosUsuario.executeUpdate();
				/*currentSession.createNativeQuery("SELECT id,CASE WHEN estado_cobranza='Etapa 2' or estado_cobranza='Etapa 3'"
						+ "  or estado_cobranza='Baja' or current_date>fecha_fin or REGISTRO_TAG.activo is false THEN false else true end as activo, REGISTRO_TAG.club,fecha_fin,"
						+ "id_chip,id_parking FROM REGISTRO_TAG join parking_usuario on id_venta_Detalle=id_parking WHERE ID_PARKING is not null"
						+ " and (REGISTRO_TAG.club='Futbol City' or REGISTRO_TAG.club='CIMERA')", RegistroTag.class);*/
				/*timeBefore = LocalDateTime.now().withNano(0);
			}
			listaApartadosUsuario=currentSession.createNativeQuery("SELECT * FROM REGISTRO_TAG  WHERE ID_PARKING is not null"
					+ " and (club='Futbol City' or club='CIMERA') and fecha_fin > (current_date - 720)", RegistroTag.class);
			results = listaApartadosUsuario.getResultList();
		} if(caseta.getId() >= 6 && caseta.getId() <= 7) {
			if (timeNow.isAfter(timeBefore.plusSeconds(1800))) {
				listaApartadosUsuario = currentSession.createNativeQuery("update registro_Tag set activo=false where (id_parking in "
						+ "(select id_venta_Detalle from parking_usuario where estado_cobranza='Etapa 1' or estado_cobranza='Etapa 2' or estado_cobranza='Etapa 3'  or "
						+ "estado_cobranza='Baja' AND id_venta_Detalle is not null ) or current_date>fecha_fin) and"
						+ " (club='"+nombre+"' or club='CIMERA' or club = 'Sports Plaza')", RegistroTag.class);
				listaApartadosUsuario.executeUpdate();
				/*listaApartadosUsuario = currentSession.createNativeQuery("SELECT id,CASE WHEN estado_cobranza='Etapa 2' or estado_cobranza='Etapa 3'"
						+ "  or estado_cobranza='Baja' or current_date>fecha_fin or REGISTRO_TAG.activo is false THEN false else true end as activo, REGISTRO_TAG.club,fecha_fin,"
						+ "id_chip,id_parking FROM REGISTRO_TAG join parking_usuario on id_venta_Detalle=id_parking WHERE ID_PARKING is not null"
						+ " and (REGISTRO_TAG.club='"+nombre+"' or REGISTRO_TAG.club='CIMERA')", RegistroTag.class);*/
				/*timeBefore = LocalDateTime.now().withNano(0);
			}
			listaApartadosUsuario = currentSession.createNativeQuery("SELECT * FROM REGISTRO_TAG  WHERE ID_PARKING is not null"
					+ " and (club='"+nombre+"' or club='CIMERA' or club = 'Sports Plaza') and fecha_fin > (current_date - 720)", RegistroTag.class);
			results = listaApartadosUsuario.getResultList();
		}
		else {
			if (timeNow.isAfter(timeBefore.plusSeconds(1800))) {
				listaApartadosUsuario = currentSession.createNativeQuery("update registro_Tag set activo=false where (id_parking in "
						+ "(select id_venta_Detalle from parking_usuario where estado_cobranza='Etapa 1' or estado_cobranza='Etapa 2' or estado_cobranza='Etapa 3'  or "
						+ "estado_cobranza='Baja' AND id_venta_Detalle is not null ) or current_date>fecha_fin) and"
						+ " (club='"+nombre+"' or club='CIMERA')", RegistroTag.class);
				listaApartadosUsuario.executeUpdate();
				/*listaApartadosUsuario = currentSession.createNativeQuery("SELECT id,CASE WHEN estado_cobranza='Etapa 2' or estado_cobranza='Etapa 3'"
						+ "  or estado_cobranza='Baja' or current_date>fecha_fin or REGISTRO_TAG.activo is false THEN false else true end as activo, REGISTRO_TAG.club,fecha_fin,"
						+ "id_chip,id_parking FROM REGISTRO_TAG join parking_usuario on id_venta_Detalle=id_parking WHERE ID_PARKING is not null"
						+ " and (REGISTRO_TAG.club='"+nombre+"' or REGISTRO_TAG.club='CIMERA')", RegistroTag.class);*/
				/*timeBefore = LocalDateTime.now().withNano(0);
			}
			listaApartadosUsuario = currentSession.createNativeQuery("SELECT * FROM REGISTRO_TAG  WHERE ID_PARKING is not null"
					+ " and (club='"+nombre+"' or club='CIMERA') and fecha_fin > (current_date - 720)", RegistroTag.class);
			results = listaApartadosUsuario.getResultList();
		}
		return new ResponseEntity<>(results, HttpStatus.OK);
		//return new ResponseEntity<>("No se puede ejecutar la operacion no ha pasado media hora despues de: " + timeBefore, HttpStatus.CONFLICT);
	}*/

	@GetMapping("/chipsActivos/{idCaseta}")
	public ResponseEntity<?> getChipsByClub(@PathVariable("idCaseta") int idCaseta){
		return new ResponseEntity<>(registroTagService.getChipsByClub(idCaseta), HttpStatus.OK);
	}
	 
	 @GetMapping("/listaChips")
		@ResponseBody
		@Transactional
		public ResponseEntity<?> listaChips(){
			Session currentSession = entityManager.unwrap(Session.class);
			Query<RegistroTag> listaApartadosUsuario;
			List<RegistroTag> results;
			listaApartadosUsuario = currentSession.createNativeQuery("SELECT * FROM REGISTRO_TAG ", RegistroTag.class);
			results = listaApartadosUsuario.getResultList();
			return new ResponseEntity<>(results, HttpStatus.OK);
	}
	 @GetMapping("/calcularAmonestaciones")
		@ResponseBody
		public ResponseEntity<?> calcularAmonestaciones(){
		    Session currentSession = entityManager.unwrap(Session.class);
		 	Query listaClases;
		 	/*listaClases = currentSession.createNativeQuery("SELECT *,(select sentido from caseta_antena where "
		 			+ "caseta_antena.antena_id=registro_parking.id_antena and caseta_antena.caseta_id=registro_parking.id_caseta ) from "
		 			+ "registro_parking where hora_entrada between current_timestamp - '30 hr'::INTERVAL and current_timestamp -'6 hr'::INTERVAL "
		 			+ "AND ACCESO IS TRUE and id_chip is not null order by id_chip,hora_entrada;");*/
		 	listaClases = currentSession.createNativeQuery("SELECT id,hora_Evento,id_antena,id_Caseta,id_chip,mensaje,raw,acceso,"
		 			+ "hora_Entrada,tipo_acceso,(select sentido from caseta_antena where caseta_antena.antena_id=registro_parking.id_antena and"
		 			+ " caseta_antena.caseta_id=registro_parking.id_caseta ) from registro_parking where ACCESO IS TRUE and id_chip is not"
		 			+ " null and hora_entrada between (current_timestamp - interval '24 hour') and current_timestamp order by id_chip,"
		 			+ "hora_entrada;");
		
		 	List<Object[]> listResults = listaClases.getResultList();
			currentSession.close();
			List<RegistroParkingDTO> listaDTO= new ArrayList<RegistroParkingDTO>();
			for (Object[] record : listResults) {
				RegistroParkingDTO registroParking=new RegistroParkingDTO();
				for(int i=0;i<record.length;i++) {
					registroParking.setAcceso((boolean) record[7]);
					registroParking.setHoraEntrada(((Date) record[8]).getTime());
					registroParking.setHoraEvento((Date) record[1]);
					registroParking.setIdAntena(antenaService.getOne((int) record[2]).get());
					registroParking.setIdCaseta(casetaService.getOne((int) record[3]).get());
					registroParking.setIdChip(registroTagService.getOne((int) record[4]).get());
					registroParking.setMensaje((String) record[5]);
					registroParking.setRaw((String) record[6]);
					registroParking.setSentido((boolean) record[10]);
					registroParking.setTipoAcceso(tipoAccesoService.getOne((int) record[9]).get());
				}	
				listaDTO.add(registroParking);
			}
			ArrayList<ChipHora> chipHoras=new ArrayList<ChipHora>();
			for(int i=0;i+1<listaDTO.size();i++) {
				if(listaDTO.get(i).getIdChip()==listaDTO.get(i+1).getIdChip()) {
					if(listaDTO.get(i).isSentido() && !listaDTO.get(i+1).isSentido() && listaDTO.get(i).getIdCaseta().getId() != 5 && listaDTO.get(i+1).getIdCaseta().getId() != 5) {
						ChipHora chipHora=new ChipHora();
						long between = (listaDTO.get(i+1).getHoraEntrada().getTime() -listaDTO.get(i).getHoraEntrada().getTime()) / 1000; 
						//int hour1=(int) (between%(24*3600)/3600);
						float hour1 = (between % (24f * 3600f) / 3600f); //tiene que ser asi porque si no no toma los decimales
						chipHora.setHoras(hour1);
						chipHora.setIdChip(listaDTO.get(i).getIdChip());
						chipHora.setHoraEntrada(listaDTO.get(i).getHoraEntrada());
						chipHora.setHoraSalida(listaDTO.get(i+1).getHoraEntrada());
						chipHoras.add(chipHora);
					}
				}
				
				
			}
			for (int i=0;i<chipHoras.size();i++) {
					try {
						int idVentaDetalle = chipHoras.get(i).getIdChip().getParking();
						ParkingUsuario park = parkingUsuarioService.getOne(idVentaDetalle).get();

						float horas = chipHoras.get(i).getHoras();
						String club = chipHoras.get(i).getIdChip().getClub();
						String tipoCliente = "";
						String conceptoTag = "";
						if (park.getRhEmpleado() == null) {
							tipoCliente = park.getCliente().getTipoCliente().getNombre();
							conceptoTag = park.getConcepto().toLowerCase();
							System.out.println("conceptoTag: " + conceptoTag);
						}
						/*System.out.println(horas>=4 && idVentaDetalle>0 && (!tipoCliente.equals("EQUIPO PUEBLA") && !tipoCliente.equals("PRACTICANTES")
								 && !tipoCliente.equals("EMPLEADOS ADMINISTRATIVOS Y OPERATIVOS")  && !tipoCliente.equals("EMPLEADOS CIM")
								 && !tipoCliente.equals("EMPLEADOS DIRECTIVOS") && !tipoCliente.equals("EMPLEADOS FAMILIARES DIRECTOS")
								 && !tipoCliente.equals("EMPLEADOS PLATINUM")));*/
						/*boolean excepciones=!tipoCliente.equals("EQUIPO PUEBLA") && !tipoCliente.equals("PRACTICANTES")
								 && !tipoCliente.equals("EMPLEADOS ADMINISTRATIVOS Y OPERATIVOS")  && !tipoCliente.equals("EMPLEADOS CIM")
								 && !tipoCliente.equals("EMPLEADOS DIRECTIVOS") && !tipoCliente.equals("EMPLEADOS FAMILIARES DIRECTOS")
								 && !tipoCliente.equals("EMPLEADOS PLATINUM") && !club.equals("Futbol City") ;*/
						//boolean excepciones = !tipoCliente.contains("Empleados");
						// Quitar acentos de tipo de cliente
						String tipoClienteSinAcentos = Normalizer.normalize(tipoCliente, Normalizer.Form.NFD);
						tipoClienteSinAcentos = tipoClienteSinAcentos.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

						boolean excepciones = !tipoClienteSinAcentos.contains("Empleados") && !tipoClienteSinAcentos.contains("Usuario / Cimera / Cortesias") && !conceptoTag.contains("empleado");
						ConfiguracionSancion sancion2 = configuracionSancionService.findByConcepto("Parking segunda ocasion");
						ConfiguracionSancion sancion3 = configuracionSancionService.findByConcepto("Parking tercera ocasion");
						Calendar calendar = new GregorianCalendar();
						if (horas >= 4.35f && club.equals("CIMERA") && idVentaDetalle > 0 && excepciones) {
							List<Amonestaciones> amonestacionesPorChip = new ArrayList<Amonestaciones>();
							Amonestaciones amonestacionNueva = new Amonestaciones();
							try {
								amonestacionesPorChip = amonestacionesService.getByIdChip(chipHoras.get(i).getIdChip().obtenerIdChip());
								amonestacionNueva.setCantidadAmonestaciones(amonestacionesPorChip.size() + 1);
								amonestacionNueva.setIdChip(amonestacionesPorChip.get(0).getIdChip());
								amonestacionNueva.setHoraEntrada(chipHoras.get(i).getHoraEntrada());
								amonestacionNueva.setHoraSalida(chipHoras.get(i).getHoraSalida());
								amonestacionesPorChip.add(amonestacionNueva);
							} catch (Exception e) {
								amonestacionNueva.setCantidadAmonestaciones(1);
								amonestacionNueva.setIdChip(chipHoras.get(i).getIdChip().obtenerIdChip());
								amonestacionNueva.setHoraEntrada(chipHoras.get(i).getHoraEntrada());
								amonestacionNueva.setHoraSalida(chipHoras.get(i).getHoraSalida());
								amonestacionesPorChip.add(amonestacionNueva);
								System.out.println("Error al crear amonestacion CIMERA chip: " + chipHoras.get(i) + " => " + e.getMessage());
							}
							RegistroTag chip = registroTagService.findByIdChip(amonestacionesPorChip.get(0).getIdChip());
							ParkingUsuario parkingUsuario = parkingUsuarioService.getOne(chip.getParking()).get();
							String correoAmonestado = parkingUsuario.getCorreo();
							String asunto = "Te has excedido del tiempo limite en el estacionamiento";
							String texto = "";
							String cabecera = "";
							Correo correo;

							int idCliente = parkingUsuario.getCliente().getIdCliente();
							String nombre = parkingUsuario.getCliente().getNombre() + " " + parkingUsuario.getCliente().getApellidoPaterno() + " " + parkingUsuario.getCliente().getApellidoMaterno();
							String horaEntrada = amonestacionNueva.getHoraEntrada();
							String horaSalida = amonestacionNueva.getHoraSalida();
							int idClub = parkingUsuario.getCliente().getClub().getIdClub();
							String fechaInicio = amonestacionNueva.obtenerHoraEntrada();

							switch (amonestacionesPorChip.size()) {
								case 1:
									correo = new Correo(usuarioCorreo, contrasenaCorreo, correoAmonestado, copiaOculta);
									asunto = asunto + " por primera ocasion";
									cabecera = "primera";
									texto = "<li>La estancia en el estacionamiento es de 4 horas 20 minutos por ingreso.</li>\r\n"
											+ "<li>En caso de una segunda incidencia deberá cubrir, en la caja general del club, una sanción administrativa de $40.00.</li>\r\n"
											+ "<li>En caso de una tercera incidencia deberá cubrir, en la caja general del club, una sanción administrativa de $70.00.</li>\r\n"
											+ "<li>En caso de una cuarta incidencia se desactivará definitivamente el Chip</li>\r\n"
											+ "<li>El chip permanecerá desactivado hasta cubrir la sanción administrativa.</li>\r\n";
									correo.enviar_correo4(asunto,idCliente,nombre,horaEntrada,horaSalida,texto,cabecera);
									break;
								case 2:
									String body2 = "{\r\n"
											+ "\"IDCliente\":" + idCliente + ",  \r\n"
											+ "\"IDClub\":" + idClub + ",   \r\n"
											+ "\"Cantidad\":1, \r\n"
											+ "\"IDProductoServicio\":" + sancion2.getCodigo() + ",  \r\n"
											+ "\"Observaciones\":\" hora entrada: " + horaEntrada + " hora salida: " + horaSalida + "\" ,   \r\n"
											+ "\"DescuentoPorciento\":0,  \r\n"
											+ "\"FechaInicio\":\"" + fechaInicio + "\", \r\n"
											+ "\"FechaFin\":\"" + fechaInicio + "\",  \r\n"
											+ "\"CobroProporcional\":0, \r\n"
											+ "\"Token\":\"77D5BDD4-1FEE-4A47-86A0-1E7D19EE1C74\"  \r\n"
											+ "}";
									configuracion o = configuracionService.findByServiceName("RegistraOV").get();
									try {
										System.out.println(e.conectaApiClubPOST(body2, o.getEndpointAlpha()));
									} catch (Exception e) {

									}

									correo = new Correo(usuarioCorreo, contrasenaCorreo, correoAmonestado, copiaOculta);
									asunto = asunto + " por segunda ocasion";
									cabecera = "segunda";
									texto = "<li>La estancia en el estacionamiento es de 4 horas 20 minutos por ingreso.</li>\r\n"
											+ "<li>En caso de una tercera incidencia deberá cubrir, en la caja general del club, una sanción administrativa de $70.00.</li>\r\n"
											+ "<li>En caso de una cuarta incidencia se desactivará definitivamente el Chip</li>\r\n"
											+ "<li>El chip permanecerá desactivado hasta cubrir la sanción administrativa.</li>";
									correo.enviar_correo4(asunto,idCliente,nombre,horaEntrada,horaSalida,texto,cabecera);
									break;
								case 3:
									String body3 = "{\r\n"
											+ "\"IDCliente\":" + idCliente + ",  \r\n"
											+ "\"IDClub\":" + idClub + ",   \r\n"
											+ "\"Cantidad\":1, \r\n"
											+ "\"IDProductoServicio\":" + sancion3.getCodigo() + ",  \r\n"
											+ "\"Observaciones\":\" hora entrada: " + horaEntrada + " hora salida: " + horaSalida + "\" ,   \r\n"
											+ "\"DescuentoPorciento\":0,  \r\n"
											+ "\"FechaInicio\":\"" + fechaInicio + "\", \r\n"
											+ "\"FechaFin\":\"" + fechaInicio + "\",  \r\n"
											+ "\"CobroProporcional\":0, \r\n"
											+ "\"Token\":\"77D5BDD4-1FEE-4A47-86A0-1E7D19EE1C74\"  \r\n"
											+ "}";
									configuracion o2 = configuracionService.findByServiceName("RegistraOV").get();
									try {
										System.out.println(e.conectaApiClubPOST(body3, o2.getEndpointAlpha()));

									} catch (Exception e) {

									}

									correo = new Correo(usuarioCorreo, contrasenaCorreo, correoAmonestado, copiaOculta);
									asunto = asunto + " por tercera ocasion";
									cabecera = "tercera";
									texto = "<li>La estancia en el estacionamiento es de 4 horas 20 minutos por ingreso.</li>\r\n"
											+ "<li>En caso de una cuarta incidencia se desactivará definitivamente el Chip</li>\r\n"
											+ "<li>El chip permanecerá desactivado hasta cubrir la sanción administrativa.</li>";
									correo.enviar_correo4(asunto,idCliente,nombre,horaEntrada,horaSalida,texto,cabecera);
									break;
								case 4:
									RegistroTag registroTag = registroTagService.findByIdChip(amonestacionesPorChip.get(0).getIdChip());
									registroTag.setActivo(false);
									//hacemos calculos sobre el calendario
									calendar.setTime(registroTag.getFechaFin());
									//movemos el ccalendario
									calendar.add(Calendar.YEAR, -1);
									registroTag.setFechaFin(calendar.getTime());
									registroTagService.save(registroTag);
									correo = new Correo(usuarioCorreo, contrasenaCorreo, correoAmonestado, copiaOculta);
									asunto = asunto + " por cuarta ocasion";
									correo.enviar_correo5(asunto,idCliente,nombre,horaEntrada,horaSalida);
									break;
							}

							amonestacionesService.save(amonestacionNueva);
						} // CHIPS CIMERAS
						else if (horas >= 4.35f && idVentaDetalle > 0 && excepciones && !club.equals("CIMERA")) {
							List<Amonestaciones> amonestacionesPorChip = new ArrayList<Amonestaciones>();
							Amonestaciones amonestacionNueva = new Amonestaciones();
							try {
								amonestacionesPorChip = amonestacionesService.getByIdChip(chipHoras.get(i).getIdChip().obtenerIdChip());
								amonestacionNueva.setCantidadAmonestaciones(amonestacionesPorChip.size() + 1);
								amonestacionNueva.setIdChip(amonestacionesPorChip.get(0).getIdChip());
								amonestacionNueva.setHoraEntrada(chipHoras.get(i).getHoraEntrada());
								amonestacionNueva.setHoraSalida(chipHoras.get(i).getHoraSalida());
								amonestacionesPorChip.add(amonestacionNueva);
							} catch (Exception e) {
								amonestacionNueva.setCantidadAmonestaciones(1);
								amonestacionNueva.setIdChip(chipHoras.get(i).getIdChip().obtenerIdChip());
								amonestacionNueva.setHoraEntrada(chipHoras.get(i).getHoraEntrada());
								amonestacionNueva.setHoraSalida(chipHoras.get(i).getHoraSalida());
								amonestacionesPorChip.add(amonestacionNueva);
								System.out.println("Error al crear amonestacion AlPHAS chip: " + chipHoras.get(i) + " => " + e.getMessage());
							}
							RegistroTag chip = registroTagService.findByIdChip(amonestacionesPorChip.get(0).getIdChip());
							ParkingUsuario parkingUsuario = parkingUsuarioService.getOne(chip.getParking()).get();
							String correoAmonestado = parkingUsuario.getCorreo();
							String asunto = "Te has excedido del tiempo limite en el estacionamiento";
							String texto = "";
							String cabecera = "";
							Correo correo;

							int idCliente = parkingUsuario.getCliente().getIdCliente();
							String nombre = parkingUsuario.getCliente().getNombre() + " " + parkingUsuario.getCliente().getApellidoPaterno() + " " + parkingUsuario.getCliente().getApellidoMaterno();
							String horaEntrada = amonestacionNueva.getHoraEntrada();
							String horaSalida = amonestacionNueva.getHoraSalida();
							int idClub = parkingUsuario.getCliente().getClub().getIdClub();
							String fechaInicio = amonestacionNueva.obtenerHoraEntrada();


							switch (amonestacionesPorChip.size()) {
								case 1:
									correo = new Correo(usuarioCorreo, contrasenaCorreo, correoAmonestado, copiaOculta);
									asunto = asunto + " por primera ocasion";
									cabecera = "primera";
									texto = "<li>La estancia en el estacionamiento es de 4 horas 20 minutos por ingreso.</li>\r\n"
											+ "<li>En caso de una segunda incidencia deberá cubrir, en la caja general del club, una sanción administrativa de $40.00.</li>\r\n"
											+ "<li>En caso de una tercera incidencia deberá cubrir, en la caja general del club, una sanción administrativa de $70.00.</li>\r\n"
											+ "<li>En caso de una cuarta incidencia se desactivará definitivamente el Chip</li>\r\n"
											+ "<li>El chip permanecerá desactivado hasta cubrir la sanción administrativa.</li>\r\n";
									if(parkingUsuario.getCliente().getClub().getIdClub()==3)
										correo.enviar_correo2(asunto,idCliente,nombre,horaEntrada,horaSalida,texto,cabecera);
									else if (parkingUsuario.getCliente().getClub().getIdClub()==2)
										correo.enviar_correo6(asunto,idCliente,nombre,horaEntrada,horaSalida,texto,cabecera);
									else
										correo.enviar_correo4(asunto,idCliente,nombre,horaEntrada,horaSalida,texto,cabecera);
									break;
								case 2:
									String body2 = "{\r\n"
											+ "\"IDCliente\":" + idCliente + ",  \r\n"
											+ "\"IDClub\":" + idClub + ",   \r\n"
											+ "\"Cantidad\":1, \r\n"
											+ "\"IDProductoServicio\":" + sancion2.getCodigo() + ",  \r\n"
											+ "\"Observaciones\":\" hora entrada: " + horaEntrada + " hora salida: " + horaSalida + "\" ,   \r\n"
											+ "\"DescuentoPorciento\":0,  \r\n"
											+ "\"FechaInicio\":\"" + fechaInicio + "\", \r\n"
											+ "\"FechaFin\":\"" + fechaInicio + "\",  \r\n"
											+ "\"CobroProporcional\":0, \r\n"
											+ "\"Token\":\"77D5BDD4-1FEE-4A47-86A0-1E7D19EE1C74\"  \r\n"
											+ "}";
									configuracion o = configuracionService.findByServiceName("RegistraOV").get();
									try {
										System.out.println(e.conectaApiClubPOST(body2, o.getEndpointAlpha()));
									} catch (Exception e) {

									}

									correo = new Correo(usuarioCorreo, contrasenaCorreo, correoAmonestado, copiaOculta);
									asunto = asunto + " por segunda ocasion";
									cabecera = "segunda";
									texto = "<li>La estancia en el estacionamiento es de 4 horas 20 minutos por ingreso.</li>\r\n"
											+ "<li>En caso de una tercera incidencia deberá cubrir, en la caja general del club, una sanción administrativa de $70.00.</li>\r\n"
											+ "<li>En caso de una cuarta incidencia se desactivará definitivamente el Chip</li>\r\n"
											+ "<li>El chip permanecerá desactivado hasta cubrir la sanción administrativa.</li>";
									if(parkingUsuario.getCliente().getClub().getIdClub()==3)
										correo.enviar_correo2(asunto,idCliente,nombre,horaEntrada,horaSalida,texto,cabecera);
									else if (parkingUsuario.getCliente().getClub().getIdClub()==2)
										correo.enviar_correo6(asunto,idCliente,nombre,horaEntrada,horaSalida,texto,cabecera);
									else
										correo.enviar_correo4(asunto,idCliente,nombre,horaEntrada,horaSalida,texto,cabecera);
									break;
								case 3:
									String body3 = "{\r\n"
											+ "\"IDCliente\":" + idCliente + ",  \r\n"
											+ "\"IDClub\":" + idClub + ",   \r\n"
											+ "\"Cantidad\":1, \r\n"
											+ "\"IDProductoServicio\":" + sancion3.getCodigo() + ",  \r\n"
											+ "\"Observaciones\":\" hora entrada: " + horaEntrada + " hora salida: " + horaSalida + "\" ,   \r\n"
											+ "\"DescuentoPorciento\":0,  \r\n"
											+ "\"FechaInicio\":\"" + fechaInicio + "\", \r\n"
											+ "\"FechaFin\":\"" + fechaInicio + "\",  \r\n"
											+ "\"CobroProporcional\":0, \r\n"
											+ "\"Token\":\"77D5BDD4-1FEE-4A47-86A0-1E7D19EE1C74\"  \r\n"
											+ "}";
									configuracion o2 = configuracionService.findByServiceName("RegistraOV").get();
									try {
										System.out.println(e.conectaApiClubPOST(body3, o2.getEndpointAlpha()));

									} catch (Exception e) {

									}
									correo = new Correo(usuarioCorreo, contrasenaCorreo, correoAmonestado, copiaOculta);
									asunto = asunto + " por tercera ocasion";
									cabecera = "tercera";
									texto = "<li>La estancia en el estacionamiento es de 4 horas 20 minutos por ingreso.</li>\r\n"
											+ "<li>En caso de una cuarta incidencia se desactivará definitivamente el Chip</li>\r\n"
											+ "<li>El chip permanecerá desactivado hasta cubrir la sanción administrativa.</li>";
									if(parkingUsuario.getCliente().getClub().getIdClub()==3)
										correo.enviar_correo2(asunto,idCliente,nombre,horaEntrada,horaSalida,texto,cabecera);
									else if (parkingUsuario.getCliente().getClub().getIdClub()==2)
										correo.enviar_correo6(asunto,idCliente,nombre,horaEntrada,horaSalida,texto,cabecera);
									else
										correo.enviar_correo4(asunto,idCliente,nombre,horaEntrada,horaSalida,texto,cabecera);
									break;
								case 4:
									RegistroTag registroTag = registroTagService.findByIdChip(amonestacionesPorChip.get(0).getIdChip());
									registroTag.setActivo(false);
									//hacemos calculos sobre el calendario
									calendar.setTime(registroTag.getFechaFin());
									//movemos el ccalendario
									calendar.add(Calendar.YEAR, -1);
									registroTag.setFechaFin(calendar.getTime());
									registroTagService.save(registroTag);
									correo = new Correo(usuarioCorreo, contrasenaCorreo, correoAmonestado, copiaOculta);
									asunto = asunto + " por cuarta ocasion";
									if(parkingUsuario.getCliente().getClub().getIdClub()==3)
										correo.enviar_correo3(asunto,idCliente,nombre,horaEntrada,horaSalida);
									else if (parkingUsuario.getCliente().getClub().getIdClub()==2)
										correo.enviar_correo7(asunto,idCliente,nombre,horaEntrada,horaSalida);
									else
										correo.enviar_correo5(asunto,idCliente,nombre,horaEntrada,horaSalida);
									break;
							}
							amonestacionesService.save(amonestacionNueva);
						} // CHIPS ALPHAS
					} catch (Exception e) {
						System.out.println("Error al calcular incidencias del chip: " + chipHoras.get(i) + " at: " + LocalDateTime.now().withNano(0) + " => " + e.getMessage());
					}
			}
			return new ResponseEntity<>(chipHoras, HttpStatus.OK);
		}
	 
	 @GetMapping("/calcularAmonestacionesPrueba")
		@ResponseBody
		public ResponseEntity<?> calcularAmonestacionesPrueba(){
		 	Session currentSession = entityManager.unwrap(Session.class);
		 	Query listaClases;
		 	/*listaClases = currentSession.createNativeQuery("SELECT *,(select sentido from caseta_antena where "
		 			+ "caseta_antena.antena_id=registro_parking.id_antena and caseta_antena.caseta_id=registro_parking.id_caseta ) from "
		 			+ "registro_parking where hora_entrada between current_timestamp - '30 hr'::INTERVAL and current_timestamp -'6 hr'::INTERVAL "
		 			+ "AND ACCESO IS TRUE and id_chip is not null order by id_chip,hora_entrada;");*/
		 	listaClases = currentSession.createNativeQuery("SELECT id,hora_Evento,id_antena,id_Caseta,id_chip,mensaje,raw,acceso,"
		 			+ "hora_Entrada,tipo_acceso,(select sentido from caseta_antena where caseta_antena.antena_id=registro_parking.id_antena and"
		 			+ " caseta_antena.caseta_id=registro_parking.id_caseta ) from registro_parking where ACCESO IS TRUE and id_chip is not"
		 			+ " null and hora_entrada between (current_timestamp - interval '24 hour') and current_timestamp order by id_chip,"
		 			+ "hora_entrada;");
		
		 	List<Object[]> listResults = listaClases.getResultList();
			List<RegistroParkingDTO> listaDTO= new ArrayList<RegistroParkingDTO>();
			for (Object[] record : listResults) {
				RegistroParkingDTO registroParking=new RegistroParkingDTO();
				for(int i=0;i<record.length;i++) {
					registroParking.setAcceso((boolean) record[7]);
					registroParking.setHoraEntrada(((Date) record[8]).getTime());
					registroParking.setHoraEvento((Date) record[1]);
					registroParking.setIdAntena(antenaService.getOne((int) record[2]).get());
					registroParking.setIdCaseta(casetaService.getOne((int) record[3]).get());
					registroParking.setIdChip(registroTagService.getOne((int) record[4]).get());
					registroParking.setMensaje((String) record[5]);
					registroParking.setRaw((String) record[6]);
					registroParking.setSentido((boolean) record[10]);
					registroParking.setTipoAcceso(tipoAccesoService.getOne((int) record[9]).get());
				}	
				listaDTO.add(registroParking);
			}
			ArrayList<ChipHora> chipHoras=new ArrayList<ChipHora>();
			for(int i=0;i+1<listaDTO.size();i++) {
				if(listaDTO.get(i).getIdChip()==listaDTO.get(i+1).getIdChip()) {
					if(listaDTO.get(i).isSentido() && !listaDTO.get(i+1).isSentido()) {
						ChipHora chipHora=new ChipHora();
						long between = (listaDTO.get(i+1).getHoraEntrada().getTime() -listaDTO.get(i).getHoraEntrada().getTime()) / 1000; 
						int hour1=(int) (between%(24*3600)/3600);
						chipHora.setHoras(hour1);
						chipHora.setIdChip(listaDTO.get(i).getIdChip());
						chipHora.setHoraEntrada(listaDTO.get(i).getHoraEntrada());
						chipHora.setHoraSalida(listaDTO.get(i+1).getHoraEntrada());
						chipHoras.add(chipHora);
					}
				}
				
				
			}
			for (int i=0;i<chipHoras.size();i++) {
				int idVentaDetalle=chipHoras.get(i).getIdChip().getParking();
				ParkingUsuario park=parkingUsuarioService.getOne(idVentaDetalle).get();
				float horas=chipHoras.get(i).getHoras();
				String club=chipHoras.get(i).getIdChip().getClub();
				String tipoCliente="";
				if(park.getRhEmpleado()==null)
					tipoCliente=park.getCliente().getTipoCliente().getNombre();
				boolean excepciones=!tipoCliente.equals("EQUIPO PUEBLA") && !tipoCliente.equals("PRACTICANTES")
						 && !tipoCliente.equals("EMPLEADOS ADMINISTRATIVOS Y OPERATIVOS")  && !tipoCliente.equals("EMPLEADOS CIM")
						 && !tipoCliente.equals("EMPLEADOS DIRECTIVOS") && !tipoCliente.equals("EMPLEADOS FAMILIARES DIRECTOS") 
						 && !tipoCliente.equals("EMPLEADOS PLATINUM") && !club.equals("Futbol City") ;
				if(horas>=5 && club.equals("CIMERA") && idVentaDetalle>0 &&  excepciones)  {
					
					List<Amonestaciones>amonestacionesPorChip=new ArrayList<Amonestaciones>();
					Amonestaciones amonestacionNueva=new Amonestaciones();				
					try {
						amonestacionesPorChip=amonestacionesService.getByIdChip(chipHoras.get(i).getIdChip().obtenerIdChip());
						amonestacionNueva.setCantidadAmonestaciones(amonestacionesPorChip.size()+1);
						amonestacionNueva.setIdChip(amonestacionesPorChip.get(0).getIdChip());
						amonestacionNueva.setHoraEntrada(chipHoras.get(i).getHoraEntrada());
						amonestacionNueva.setHoraSalida(chipHoras.get(i).getHoraSalida());
						amonestacionesPorChip.add(amonestacionNueva);
					}catch(NoSuchElementException e){
						amonestacionNueva.setCantidadAmonestaciones(1);
						amonestacionNueva.setIdChip(chipHoras.get(i).getIdChip().obtenerIdChip());
						amonestacionNueva.setHoraEntrada(chipHoras.get(i).getHoraEntrada());
						amonestacionNueva.setHoraSalida(chipHoras.get(i).getHoraSalida());
						amonestacionesPorChip.add(amonestacionNueva);
					}
					String horaEntrada=amonestacionNueva.getHoraEntrada();
					String horaSalida=amonestacionNueva.getHoraSalida();
					System.out.println(horaEntrada);
					System.out.println(horaSalida);
					System.out.println("Se le genero una amonestacion al chip "+amonestacionesPorChip.get(0).getIdChip() );
					RegistroTag chip=registroTagService.findByIdChip(amonestacionesPorChip.get(0).getIdChip());				
					ParkingUsuario parkingUsuario=parkingUsuarioService.getOne(chip.getParking()).get();					
					
					switch(amonestacionesPorChip.size()) {
					case 1:
						if(parkingUsuario.getCliente().getClub().getIdClub()==3)
							System.out.println("Plantilla club alpha 3");
						else
							System.out.println("Plantilla cimera");
					break;
					case 2:
						
						if(parkingUsuario.getCliente().getClub().getIdClub()==3)
							System.out.println("Plantilla club alpha 3");
						else
							System.out.println("Plantilla cimera");
					break;
					case 3:
						
						if(parkingUsuario.getCliente().getClub().getIdClub()==3)
							System.out.println("Plantilla club alpha 3");
						else
							System.out.println("Plantilla cimera");
					break;
					case 4: 
						if(parkingUsuario.getCliente().getClub().getIdClub()==3)
							System.out.println("Plantilla club alpha 3");
						else
							System.out.println("Plantilla cimera");						
					break;
						
					}
					
				}else if(horas>=4 && idVentaDetalle>0 &&  excepciones && !club.equals("CIMERA") ) {
					if(club.equals("CIMERA")) {
						System.out.println("soy de cimera y me amonestaron por estar 4 horas");
					}
					List<Amonestaciones>amonestacionesPorChip=new ArrayList<Amonestaciones>();
					Amonestaciones amonestacionNueva=new Amonestaciones();				
					try {
						amonestacionesPorChip=amonestacionesService.getByIdChip(chipHoras.get(i).getIdChip().obtenerIdChip());
						amonestacionNueva.setCantidadAmonestaciones(amonestacionesPorChip.size()+1);
						amonestacionNueva.setIdChip(amonestacionesPorChip.get(0).getIdChip());
						amonestacionNueva.setHoraEntrada(chipHoras.get(i).getHoraEntrada());
						amonestacionNueva.setHoraSalida(chipHoras.get(i).getHoraSalida());
						amonestacionesPorChip.add(amonestacionNueva);
					}catch(NoSuchElementException e){
						amonestacionNueva.setCantidadAmonestaciones(1);
						amonestacionNueva.setIdChip(chipHoras.get(i).getIdChip().obtenerIdChip());
						amonestacionNueva.setHoraEntrada(chipHoras.get(i).getHoraEntrada());
						amonestacionNueva.setHoraSalida(chipHoras.get(i).getHoraSalida());
						amonestacionesPorChip.add(amonestacionNueva);
					}
					String horaEntrada=amonestacionNueva.getHoraEntrada();
					String horaSalida=amonestacionNueva.getHoraSalida();
					System.out.println(horaEntrada);
					System.out.println(horaSalida);
					System.out.println("Se le genero una amonestacion al chip "+amonestacionesPorChip.get(0).getIdChip() );
					RegistroTag chip=registroTagService.findByIdChip(amonestacionesPorChip.get(0).getIdChip());				
					ParkingUsuario parkingUsuario=parkingUsuarioService.getOne(chip.getParking()).get();					
					
					switch(amonestacionesPorChip.size()) {
					case 1:
						if(parkingUsuario.getCliente().getClub().getIdClub()==3)
							System.out.println("Plantilla club alpha 3");
						else
							System.out.println("Plantilla cimera");
					break;
					case 2:
						
						if(parkingUsuario.getCliente().getClub().getIdClub()==3)
							System.out.println("Plantilla club alpha 3");
						else
							System.out.println("Plantilla cimera");
					break;
					case 3:
						
						if(parkingUsuario.getCliente().getClub().getIdClub()==3)
							System.out.println("Plantilla club alpha 3");
						else
							System.out.println("Plantilla cimera");
					break;
					case 4: 
						if(parkingUsuario.getCliente().getClub().getIdClub()==3)
							System.out.println("Plantilla club alpha 3");
						else
							System.out.println("Plantilla cimera");						
					break;
						
					}
					
				}
			}
			

			
			
			return new ResponseEntity<>(chipHoras, HttpStatus.OK);	
		}
	 
	 @GetMapping("/amonestacionesUsuario/{idCliente}")
		@ResponseBody
		public ResponseEntity<?> amonestacionesUsuario(@PathVariable("idCliente") int idCliente){
		 	Session currentSession = entityManager.unwrap(Session.class);
		 	Query<Amonestaciones> listaClases;
		 	listaClases = currentSession.createNativeQuery("select * from amonestaciones where  id_chip in "
		 			+ "(select id_chip from parking_usuario join registro_tag on parking_usuario.id_venta_detalle=registro_tag.id_parking"
		 			+ " where idcliente="+idCliente+");",Amonestaciones.class);
		
		 	List<Amonestaciones> listResults = listaClases.getResultList();
		 	List<AmonestacionesDTO> amonestaciones=new ArrayList<AmonestacionesDTO>(); 
			for(int i=0;i<listResults.size();i++) {
				AmonestacionesDTO amonestacion=new AmonestacionesDTO();
				if(listResults.get(i).getCantidadAmonestaciones()==1) {
					amonestacion.setIncidencia("Primera Incidencia");
					amonestacion.setHoraEntrada(listResults.get(i).getHoraEntrada());
					amonestacion.setHoraSalida(listResults.get(i).getHoraSalida());
					amonestacion.setIdChip(listResults.get(i).getIdChip());
					amonestacion.setTexto("Se envía correo de notificacion de primera incidencia");
					
				}
				if(listResults.get(i).getCantidadAmonestaciones()==2) {
					amonestacion.setIncidencia("Segunda Incidencia");
					amonestacion.setHoraEntrada(listResults.get(i).getHoraEntrada());
					amonestacion.setHoraSalida(listResults.get(i).getHoraSalida());
					amonestacion.setIdChip(listResults.get(i).getIdChip());
					amonestacion.setTexto("Se envía correo de notificacion por segunda incidencia y se le carga O.V en la cuenta por $40.00");
					
				}
				if(listResults.get(i).getCantidadAmonestaciones()==3) {
					amonestacion.setIncidencia("Tercera Incidencia");
					amonestacion.setHoraEntrada(listResults.get(i).getHoraEntrada());
					amonestacion.setHoraSalida(listResults.get(i).getHoraSalida());
					amonestacion.setIdChip(listResults.get(i).getIdChip());
					amonestacion.setTexto("Se envía correo de notificacion por tercera incidencia y se le carga O.V en la cuenta por $70.00");
					
				}
				if(listResults.get(i).getCantidadAmonestaciones()==4) {
					amonestacion.setIncidencia("Cuarta Incidencia");
					amonestacion.setHoraEntrada(listResults.get(i).getHoraEntrada());
					amonestacion.setHoraSalida(listResults.get(i).getHoraSalida());
					amonestacion.setIdChip(listResults.get(i).getIdChip());
					amonestacion.setTexto("Se envía correo de notificacion de cuarta incidencia y se le desactiva el chip");
					
				}
				Cliente cliente=clienteService.findById(idCliente);
				amonestacion.setIdCliente(idCliente);
				amonestacion.setMembresia(cliente.getNoMembresia());
				amonestaciones.add(amonestacion);
			}
			
			return new ResponseEntity<>(amonestaciones, HttpStatus.OK);
			
			
		}
	 
	 @GetMapping("/registroUsuario/{idCliente}")
		@ResponseBody
		public ResponseEntity<?> registroUsuario(@PathVariable("idCliente") int idCliente){
		 	Session currentSession = entityManager.unwrap(Session.class);
		 	Query listaClases;
		 	listaClases = currentSession.createNativeQuery("select hora_entrada as hora_evento,(select CASE  WHEN  sentido THEN "
		 			+ "'ENTRADA' ELSE 'SALIDA' END AS SENTIDO from caseta_antena where "
		 			+ "caseta_antena.antena_id=registro_parking.id_antena and caseta_antena.caseta_id=registro_parking.id_caseta ),"
		 			+ "registro_tag.id_chip,tipo_acceso.nombre from registro_parking join registro_tag on "
		 			+ "registro_parking.id_chip=registro_tag.id join parking_usuario on id_parking=id_venta_detalle join tipo_acceso on "
		 			+ "tipo_acceso.id=registro_parking.tipo_Acceso where idcliente="+idCliente+" or id_empleado="+(idCliente-90000)+"order by hora_entrada desc;");
		
		 	List<Object[]> listResults = listaClases.getResultList();
			List<RegistroUsuarioDTO> listaDTO= new ArrayList<RegistroUsuarioDTO>();
			for (Object[] record : listResults) {
				RegistroUsuarioDTO registroParking=new RegistroUsuarioDTO();
				for(int i=0;i<record.length;i++) {
					registroParking.setHoraEvento((Date) record[0]);
					registroParking.setIdChip((BigInteger) record[2]);
					registroParking.setSentido((String) record[1]);
					registroParking.setTipoAcceso((String) record[3]);
				}	
				listaDTO.add(registroParking);
			}
			
			
			return new ResponseEntity<>(listaDTO, HttpStatus.OK);
			
			
		}
	 
	 	@PostMapping("/registroPorDia")
		@ResponseBody
		public ResponseEntity<?> registroUsuario(@RequestBody Body body){
	 		long fechaInicioUnix=this.hora(body.getFechaInicio()).getTime();
	 		long fechaFinUnix=this.hora(body.getFechaFin()).getTime();
		 	Session currentSession = entityManager.unwrap(Session.class);
		 	java.sql.Date fechaInicio=new java.sql.Date(fechaInicioUnix);
		 	java.sql.Date fechaFin=new java.sql.Date(fechaFinUnix);
		 	Query listaClases;
		 	String query="select hora_entrada as hora_evento, (select CASE  WHEN  sentido THEN 'ENTRADA' ELSE 'SALIDA' END AS SENTIDO"
		 			+ " from caseta_antena where caseta_antena.antena_id=registro_parking.id_antena and "
		 			+ "caseta_antena.caseta_id=registro_parking.id_caseta ),registro_tag.id_chip,tipo_acceso.nombre,id_antena,id_caseta  "
		 			+ "from registro_parking full outer join registro_tag on registro_parking.id_chip=registro_tag.id full outer join "
		 			+ "parking_usuario on id_parking=id_venta_detalle full outer join tipo_acceso on "
		 			+ "tipo_acceso.id=registro_parking.tipo_Acceso join caseta on caseta.id=registro_parking.id_caseta join club on "
		 			+ "caseta.club_id=club.idclub where hora_entrada between '"+fechaInicio+" 00:00:00' and '"+fechaFin+"  23:59:59' and "
		 			+ "club.nombre='"+body.getClub()+"'  order by hora_evento;";
		 	listaClases = currentSession.createNativeQuery(query);
		
		 	List<Object[]> listResults = listaClases.getResultList();
			List<RegistroUsuarioDTO> listaDTO= new ArrayList<RegistroUsuarioDTO>();
			for (Object[] record : listResults) {
				RegistroUsuarioDTO registroParking=new RegistroUsuarioDTO();
				for(int i=0;i<record.length;i++) {
					registroParking.setHoraEvento((Date) record[0]);
					registroParking.setIdChip((BigInteger) record[2]);
					registroParking.setSentido((String) record[1]);
					registroParking.setTipoAcceso((String) record[3]);
					if(record[4]==null)
						registroParking.setAntena(0);
					else
						registroParking.setAntena((Integer)record[4]);
					if(record[5]==null)
						registroParking.setCaseta(0);
					else
						registroParking.setCaseta((Integer)record[5]);
					if(registroParking.getCaseta()==2 && (registroParking.getTipoAcceso().equals("Apertura Manual") || registroParking.getTipoAcceso().equals("QR"))) {
						registroParking.setSentido("SALIDA");
					}if(registroParking.getCaseta()==1 && (registroParking.getTipoAcceso().equals("Apertura Manual") || registroParking.getTipoAcceso().equals("QR"))) {
						registroParking.setSentido("ENTRADA");
					}
				}	
				listaDTO.add(registroParking);
			}
			
			
			return new ResponseEntity<>(listaDTO, HttpStatus.OK);
			
			
		}
	 	@PostMapping("/registroPorFolio")
		@ResponseBody
		public ResponseEntity<?> registroPorFolio(@RequestBody Body body){
	 		
		 	Session currentSession = entityManager.unwrap(Session.class);
		 	Query<EstacionamientoExterno> listaClases;
		 	String query="select * from estacionamiento_externo where club='"+body.getClub()+"' and turno='"+body.getFolio()+"' AND costo_total!=0 order by hora_entrada;";
		 	listaClases = currentSession.createNativeQuery(query,EstacionamientoExterno.class);
		
		 	List<EstacionamientoExterno> listResults = listaClases.getResultList();
			
			return new ResponseEntity<>(listResults, HttpStatus.OK);
			
			
		}
	 	@PostMapping("/datosQR")
		@ResponseBody
		public ResponseEntity<?> datosQR(@RequestBody Body body){
	 		
		 	Session currentSession = entityManager.unwrap(Session.class);
		 	Query<EstacionamientoExterno> listaClases;
		 	String query="select * from estacionamiento_externo where club='"+body.getClub()+"' and turno='"+body.getFolio()+"';";
		 	listaClases = currentSession.createNativeQuery(query,EstacionamientoExterno.class);
		
		 	List<EstacionamientoExterno> listResults = listaClases.getResultList();
			QRDTO respuesta=new QRDTO();
			int cuentaQrNormal = 0,cuentaQrPerdida=0;
			float cuentaTotalQrNormal = 0,cuentaTotalQrPerdida=0;
			for(int i=0;i<listResults.size();i++) {
				if(listResults.get(i).getCostoTotal()!=0) {
					if(listResults.get(i).isQr()) {
						cuentaQrNormal++;
						cuentaTotalQrNormal=cuentaTotalQrNormal+listResults.get(i).getCostoTotal();
					}else {
						cuentaQrPerdida++;
						cuentaTotalQrPerdida=cuentaTotalQrPerdida+listResults.get(i).getCostoTotal();
					}
				}
				
			}
			respuesta.setCantidadQrNormal(cuentaQrNormal);
			respuesta.setCantidadQrPerdida(cuentaQrPerdida);
			respuesta.setTotalQrNormal(cuentaTotalQrNormal);
			respuesta.setTotalQrPerdida(cuentaTotalQrPerdida);
			
			
			return new ResponseEntity<>(respuesta, HttpStatus.OK);
			
			
		}
	 	@PostMapping("registroPorDia/QR")
		@ResponseBody
		public ResponseEntity<?> qr(@RequestBody Body body){
	 		long fechaInicioUnix=this.hora(body.getFechaInicio()).getTime();
	 		long fechaFinUnix=this.hora(body.getFechaFin()).getTime();
		 	Session currentSession = entityManager.unwrap(Session.class);
		 	java.sql.Date fechaInicio=new java.sql.Date(fechaInicioUnix);
		 	java.sql.Date fechaFin=new java.sql.Date(fechaFinUnix);
		 	Query listaClases;
		 	String query="select hora_entrada ,'SALIDA' AS SENTIDO,CAST(id_registro AS BIGINT),'QR' as tipo_Acceso, "
		 			+ "1 as id_antena,id_caseta from estacionamiento_externo where hora_entrada between '"+fechaInicio+" 00:00:00' and "
		 			+ "'"+fechaFin+"  23:59:59' and club='"+body.getClub()+"' order by hora_ENTRADA;";
		 	listaClases = currentSession.createNativeQuery(query);
		
		 	List<Object[]> listResults = listaClases.getResultList();
			List<RegistroUsuarioDTO> listaDTO= new ArrayList<RegistroUsuarioDTO>();
			for (Object[] record : listResults) {
				RegistroUsuarioDTO registroParking=new RegistroUsuarioDTO();
				for(int i=0;i<record.length;i++) {
					registroParking.setHoraEvento((Date) record[0]);
					registroParking.setIdChip((BigInteger) record[2]);
					registroParking.setSentido((String) record[1]);
					registroParking.setTipoAcceso((String) record[3]);
					if(record[4]==null)
						registroParking.setAntena(0);
					else
						registroParking.setAntena((Integer)record[4]);
					if(record[5]==null)
						registroParking.setCaseta(0);
					else
						registroParking.setCaseta((Integer)record[5]);
					if(registroParking.getCaseta()==2 && (registroParking.getTipoAcceso().equals("Apertura Manual") || registroParking.getTipoAcceso().equals("QR"))) {
						registroParking.setSentido("SALIDA");
					}if(registroParking.getCaseta()==1 && (registroParking.getTipoAcceso().equals("Apertura Manual") || registroParking.getTipoAcceso().equals("QR"))) {
						registroParking.setSentido("ENTRADA");
					}
				}	
				listaDTO.add(registroParking);
			}
			
			
			return new ResponseEntity<>(listaDTO, HttpStatus.OK);
			
			
		}
	 	
	 	@PostMapping("/amonestacionesPorTiempo")
		@ResponseBody
		public ResponseEntity<?> amonestacionesUsuario(@RequestBody Body body){
	 		long fechaInicioUnix=this.hora(body.getFechaInicio()).getTime();
	 		long fechaFinUnix=this.hora(body.getFechaFin()).getTime();
		 	Session currentSession = entityManager.unwrap(Session.class);
		 	java.sql.Date fechaInicio=new java.sql.Date(fechaInicioUnix);
		 	java.sql.Date fechaFin=new java.sql.Date(fechaFinUnix);
		 	Query<Amonestaciones> listaClases;
		 	listaClases = currentSession.createNativeQuery("select * from amonestaciones where  hora_entrada between '"+fechaInicio+" 00:00:00' and "
		 			+ "'"+fechaFin+" 23:59:59' and id_chip in (select id_chip from parking_usuario join registro_tag on "
		 			+ "parking_usuario.id_venta_detalle=registro_tag.id_parking where registro_tag.club='"+body.getClub()+"');",Amonestaciones.class);
		
		 	List<Amonestaciones> listResults = listaClases.getResultList();
		 	List<AmonestacionesDTO> amonestaciones=new ArrayList<AmonestacionesDTO>(); 
		 	RegistroTag chip;
		 	try {
			 	 chip=registroTagService.findByIdChip(listResults.get(0).getIdChip());
		 	}catch(IndexOutOfBoundsException e) {
		 		return new ResponseEntity<>("[]", HttpStatus.OK);
		 	}
			for(int i=0;i<listResults.size();i++) {
				chip=registroTagService.findByIdChip(listResults.get(i).getIdChip());
				AmonestacionesDTO amonestacion=new AmonestacionesDTO();
				if(listResults.get(i).getCantidadAmonestaciones()==1) {
					amonestacion.setIncidencia("Primera Incidencia");
					amonestacion.setHoraEntrada(listResults.get(i).getHoraEntrada());
					amonestacion.setHoraSalida(listResults.get(i).getHoraSalida());
					amonestacion.setTexto("Se envía correo de notificacion de primera incidencia");
					
				}
				if(listResults.get(i).getCantidadAmonestaciones()==2) {
					amonestacion.setIncidencia("Segunda Incidencia");
					amonestacion.setHoraEntrada(listResults.get(i).getHoraEntrada());
					amonestacion.setHoraSalida(listResults.get(i).getHoraSalida());
					amonestacion.setTexto("Se envía correo de notificacion por segunda incidencia y se le carga O.V en la cuenta por $40.00");
					
				}
				if(listResults.get(i).getCantidadAmonestaciones()==3) {
					amonestacion.setIncidencia("Tercera Incidencia");
					amonestacion.setHoraEntrada(listResults.get(i).getHoraEntrada());
					amonestacion.setHoraSalida(listResults.get(i).getHoraSalida());
					amonestacion.setTexto("Se envía correo de notificacion por tercera incidencia y se le carga O.V en la cuenta por $70.00");
					
				}
				if(listResults.get(i).getCantidadAmonestaciones()==4) {
					amonestacion.setIncidencia("Cuarta Incidencia");
					amonestacion.setHoraEntrada(listResults.get(i).getHoraEntrada());
					amonestacion.setHoraSalida(listResults.get(i).getHoraSalida());
					amonestacion.setTexto("Se envía correo de notificacion de cuarta incidencia y se le desactiva el chip");
					
				}
				Cliente cliente=chip.obtenerParking().getCliente();
				amonestacion.setIdCliente(cliente.getIdCliente());
				amonestacion.setMembresia(cliente.getNoMembresia());
				amonestaciones.add(amonestacion);
			}
			
			
			return new ResponseEntity<>(amonestaciones, HttpStatus.OK);
			
			
		}
	 	
	 	@GetMapping("/turno")
		@ResponseBody
		public ResponseEntity<?> turno(){
			
			 Calendar calendar = Calendar.getInstance();
			 Date fecha=new Date();
			 calendar.setTime(fecha);
			 
			 int horaDia=calendar.get(Calendar.HOUR_OF_DAY);
			 if(horaDia>=22 || horaDia < 1) {	
				 //int minuto=calendar.get(Calendar.MINUTE);
				 //if(minuto<=30) {
				 return new ResponseEntity<>(1, HttpStatus.OK);
			 }
			
			
			return new ResponseEntity<>(0, HttpStatus.OK);
		}
	 	
	 	//-------------------------------------------------ESTACIONAMIENTO SPORTS PLAZA--------------------------------------------------
	 	@PostMapping("/crearTitular")
		@ResponseBody
		public ResponseEntity<?> crearTitular(@RequestBody ClienteExterno clienteExterno){
	 		clienteExterno.getClub();
	 		Club club=clubService.findByNombre(clienteExterno.getClub());
	 		clienteExterno.setClub(club);
	 		clienteExterno.setTitular(true);
	 		ClienteExterno cliente=clienteExternoService.save(clienteExterno);
			
			JSONObject json=new JSONObject();
			json.put("respuesta", "cliente creado exitosamente");
			return new ResponseEntity<>(json.toString(), HttpStatus.OK);
			
			
		}
	 	@GetMapping("/obtenerTitulares")
		@ResponseBody
		public ResponseEntity<?> obtenerTitulares(){
	 		List<ClienteExterno> cliente=clienteExternoService.titulares();
			
			
			return new ResponseEntity<>(cliente, HttpStatus.OK);
			
			
		}
	 	@GetMapping("/obtenerTitulares/{horarioId}")
		@ResponseBody
		public ResponseEntity<?> obtenerTitular(@PathVariable("horarioId") int horarioId){
	 		ClienteExterno cliente=clienteExternoService.getOne(horarioId).get();
			
			
			return new ResponseEntity<>(cliente, HttpStatus.OK);
	 	}
	 	@GetMapping("/obtenerDependientes/{horarioId}")
		@ResponseBody
		public ResponseEntity<?> obtenerDependientes(@PathVariable("horarioId") int horarioId){
	 		ClienteExterno cliente=clienteExternoService.getOne(horarioId).get();
	 		List<ClienteExterno>dependientes=cliente.obtenerDependientes();
			
			
			return new ResponseEntity<>(dependientes, HttpStatus.OK);
	 	}
	 	@PostMapping("/crearDependiente")
		@ResponseBody
		public ResponseEntity<?> crearDependiente(@RequestBody ClienteExterno clienteExterno){
	 		clienteExterno.getClub();
	 		Club club=clubService.findByNombre(clienteExterno.getClub());
	 		clienteExterno.setClub(club);
	 		clienteExterno.setTitular(false);
	 		
	 		ClienteExterno titular=clienteExternoService.getOne(clienteExterno.getId()).get();
	 		List<ClienteExterno>dependientes=titular.obtenerDependientes();
	 		clienteExterno.setId(0);
	 		clienteExterno=clienteExternoService.save(clienteExterno);
	 		dependientes.add(clienteExterno);
	 		clienteExternoService.save(titular);
			
			JSONObject json=new JSONObject();
			json.put("respuesta", "cliente creado exitosamente");
			return new ResponseEntity<>(json.toString(), HttpStatus.OK);
			
			
		}
	 	@RequestMapping(value="qrParkingSportsPlaza", method=RequestMethod.POST)
	   	public ResponseEntity<?> qrParkingSportsPlaza(@RequestBody body2 body)
	   	{
	 		
	 		RegistroSalidaSP registroSalida=new RegistroSalidaSP();
			JSONObject json=new JSONObject();
	   		try {
	   	   		if(body.isSuper()) {
	   	   			QRCortesia estacionamientoExterno=qrCortesiaService.getByIdRegistro(String.valueOf(body.getIdVentaDetalle()));
	   	   			if(!estacionamientoExterno.isRedimido()) {
		   	   			TipoAcceso tipoAcceso=tipoAccesoService.getOne(2).get();
						 estacionamientoExterno.setHoraSalida(new Date());
						 estacionamientoExterno.colocarHoraEntrada(new Date());
						long between = (estacionamientoExterno.obtenerHoraSalida().getTime () -estacionamientoExterno.obtenerHoraEntrada().getTime ()) / 1000; 
						 int hour1=Math.abs((int) (between%(24*3600)/3600));
						 estacionamientoExterno.setHoras(hour1);
						 estacionamientoExterno.setMensaje("ABIERTO");
						 estacionamientoExterno.setAcceso(true);
						 estacionamientoExterno.setTipoAcceso(tipoAcceso);
						 estacionamientoExterno.setRedimido(true);
						qrCortesiaService.save(estacionamientoExterno);
						registroSalida.setAcceso(true);
						registroSalida.setDisponible(0);
						registroSalida.setFolio(body.getIdVentaDetalle());
						registroSalida.setHoraSalida(new Date());
						registroSalida.setTipoAcceso(tipoAccesoService.getOne(4).get());
						registroSalidaService.save(registroSalida);
						json.put("Respuesta", "Acceso permitido");
		   	   			return new ResponseEntity<String>(json.toString(), HttpStatus.OK); 
	   	   			}else {
		   	   			registroSalida.setAcceso(false);
						registroSalida.setDisponible(0);
						registroSalida.setFolio(body.getIdVentaDetalle());
						registroSalida.setHoraSalida(new Date());
						registroSalida.setTipoAcceso(tipoAccesoService.getOne(4).get());
						registroSalidaService.save(registroSalida);
		   	   			json.put("Respuesta", "Este pase ya ha sido consumido antes");
	   	   	   			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT);
	   	   			}
					 
	   	   		}else {
	   	   			TerminalRedencion terminal=terminalRedencionService.getOne(body.getTerminal()).get();
	   	   			PaseConsumido paseConsumido=new PaseConsumido();
	   	   			PaseUsuario paseUsuario=paseUsuarioService.getOne(body.getIdVentaDetalle()).get();
	   	   			
	   	   	       	if(paseUsuario.getDisponibles()<=0) {
	   	   	       		/*if(paseUsuario.getIdVentaDetalle()<0) {
							employeeService.saveEmployees();
			   	   	       	if (employeeService.findByClaveExterna(body.getUsuario()) != null ) {
								json.put("Respuesta", "QR Empleado acceso correcto");

								registroSalida.setAcceso(true);
								registroSalida.setDisponible(0);
								registroSalida.setFolio(body.getIdVentaDetalle());
								registroSalida.setHoraSalida(new Date());
								registroSalida.setTipoAcceso(tipoAccesoService.getOne(5).get());
								registroSalidaService.save(registroSalida);

								return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
							}
	   	   	       		}*/
	   	   	       			
	   	   	   			json.put("Respuesta", "Este pase ya ha sido consumido antes");
	   	   	   			
			   	 		
				   	 	registroSalida.setAcceso(false);
						registroSalida.setDisponible(paseUsuario.getDisponibles());
						registroSalida.setFolio(body.getIdVentaDetalle());
						registroSalida.setHoraSalida(new Date());
						registroSalida.setTipoAcceso(tipoAccesoService.getOne(2).get());
						registroSalidaService.save(registroSalida);
			   	 		
	   	   	   			return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 		   	   	       		
	   	   	       	}
	   	   	       	paseUsuario.setDisponibles(paseUsuario.getDisponibles()-1);
					paseUsuarioService.save(paseUsuario);
		   			paseConsumido.setPaseUsuario(paseUsuario);
		   			paseConsumido=paseConsumidoService.save(paseConsumido);
   	   	       		paseConsumido.setTerminalRedencion(terminal);
	   	   	       	paseConsumido.setFechaRedencion(new Date());
	   	   	       	paseConsumido.setActivo(true);
	   	   	       	paseConsumido.setCreated(new Date());
	   	   	       	paseUsuario.setConsumido(paseUsuario.getConsumido()+1);
	   	   	       	paseUsuarioService.save(paseUsuario);
			   	   	
			  		paseConsumido.setCreatedBy("anonymousUser");
			  		paseConsumido.setUpdatedBy("anonymousUser");
			  		paseConsumido.setUpdated(new Date());
	   	   	       	paseConsumidoService.save(paseConsumido);
	   	   	       	
	   	   	       	registroSalida.setAcceso(true);
					registroSalida.setDisponible(paseUsuario.getDisponibles());
					registroSalida.setFolio(body.getIdVentaDetalle());
					registroSalida.setHoraSalida(new Date());
					registroSalida.setTipoAcceso(tipoAccesoService.getOne(2).get());
					registroSalidaService.save(registroSalida);
	   	   	       	
	   	   			json.put("Respuesta", "Acceso permitido");
	   	   			return new ResponseEntity<String>(json.toString(), HttpStatus.OK); 
	   	   		}
	   		}catch(IndexOutOfBoundsException e) {
	  	   		json.put("Respuesta", "No tiene pases QR para salir");
	  	   		
	   	 		
	   	 		
	   	 		
	   			return new ResponseEntity<>(json.toString(), HttpStatus.CONFLICT);		
	   		}catch(Exception e) {
	   			e.printStackTrace();
	  	   		json.put("Error", "Ha sucedido un error desconocido");
	  	   	
	   			return new ResponseEntity<>(json.toString(), HttpStatus.CONFLICT);	
	   			
	   		}
	   	}
	 	
	 	
	 	@GetMapping("/obtenerPaseQR/{idCliente}")
		@ResponseBody
		public ResponseEntity<?> getPase(@PathVariable("idCliente") int idCliente)
	   	{
			JSONObject json=new JSONObject();
			List<PaseUsuario> paseUsuario=null;
			this.getPases(idCliente);
			try {
				 paseUsuario=paseUsuarioService.getByIdClienteQR(idCliente);
				
			}catch(IndexOutOfBoundsException e) {
				json.put("respuesta", "Este cliente no tiene pases QR");
				return new ResponseEntity<>(json.toString(), HttpStatus.CONFLICT);	
			}
			
	  	   	
	   			return new ResponseEntity<>(paseUsuario, HttpStatus.OK);	
	   			
	   		}
	 	
	 	@GetMapping("/desactivarChip/{idChip}")
		@ResponseBody
		public ResponseEntity<?> desactivarChip(@PathVariable("idChip") int idChip)
	   	{
			JSONObject json=new JSONObject();
			try {
				RegistroTag chip=registroTagService.findByIdChip(idChip);
				int idParking=chip.obtenerParking().getIdVentaDetalle();
				ParkingUsuario parking=parkingUsuarioService.getOne(idParking).get();
				parking.getCarro().get(0);
				Carro carro=parking.getCarro().get(0);
				carro.setActivo(false);
				parking.setActivo(false);
				chip.setActivo(false);
				registroTagService.save(chip);
				parkingUsuarioService.save(parking);
				carroService.save(carro);
				
				json.put("respuesta", "Se desactivo el chip de manera exitosa");
	   			return new ResponseEntity<>(json.toString(), HttpStatus.OK);	
				
			}catch(Exception e) {
				e.printStackTrace();
				json.put("respuesta", "ocurrio un error desconocido");
				return new ResponseEntity<>(json.toString(), HttpStatus.INTERNAL_SERVER_ERROR);	
			}
			
	  	   	
	   			
	   		}
	 	
	 	@GetMapping("/obtenerPase/{idCliente}")
		@ResponseBody
		public List<PaseUsuario> getPases(@PathVariable("idCliente") int idCliente)
		{
    	   Connection conn = null;
         	ArrayList<PaseUsuario> listaReporte = new ArrayList<PaseUsuario>();
	        //try {
	            // Carga el driver de oracle
	        	/*DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
	        	
	            conn = DriverManager.getConnection(dbURL, userData, passData);
	            
	            PreparedStatement ps=conn.prepareStatement("EXEC DataFlowAlpha.dbo.sp_Consulta_Pases_Sports_Plaza ?");
	            ps.setInt(1, idCliente);
             	ResultSet rs =ps.executeQuery();
               while (rs.next()) {
               	
                   PaseUsuario to=new PaseUsuario();
                   Optional<PaseUsuario> optional=paseUsuarioService.getOne(rs.getInt(4));
                   if(!optional.isPresent()) {
                	   to.setActivo(true);
                       to.setIdProd(rs.getInt(1));
                       to.setF_compra(new Date(rs.getDate(5).getTime()));
                       to.setConcepto(rs.getString(3));
                       to.setIdVentaDetalle(rs.getInt(4));
                       to.setCliente(clienteService.findById(idCliente));
                       to.setCantidad(rs.getInt(11));
                       to.setConsumido(0);
                       to.setCreated(new Date());
                       to.setDisponibles(rs.getInt(11));
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
                   
               }
               ps=conn.prepareStatement("EXEC DataFlowAlpha.dbo.sp_Consulta_Paquetes_Sports_Plaza ?");
	            ps.setInt(1, idCliente);
           	rs =ps.executeQuery();
             while (rs.next()) {
                 PaseUsuario to=new PaseUsuario();
                 Optional<PaseUsuario> optional=paseUsuarioService.getOne(rs.getInt(4));
                 if(!optional.isPresent()) {
                	 to.setActivo(true);
                     if(rs.getInt(1)==1747)
                         to.setIdProd(1746);
                     to.setIdProd(rs.getInt(1));
                     to.setF_compra(new Date(rs.getDate(5).getTime()));
                     to.setConcepto(rs.getString(3));
                     to.setIdVentaDetalle(rs.getInt(4));
                     to.setCliente(clienteService.findById(idCliente));
                     to.setConsumido(0);
                     to.setCreated(new Date());
                     if(to.getConcepto().equals("SP Mensualidad Gym") || to.getConcepto().equals("SP Mensualidad Gym Estudiante")) {
                         to.setDisponibles(0);
                         to.setCantidad(0);
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
	        }*/

			String body2 = "{\n"
					+ "\"IdCliente\":"+ idCliente +",\n"
					+ "\"Token\":\"77D5BDD4-1FEE-4A47-86A0-1E7D19EE1C74\"\n"
					+ "}";
			configuracion o = configuracionService.findByServiceName("getPasesById").get();
			JSONArray json = new JSONArray(e.conectaApiClubPOST(body2,o.getEndpointAlpha()));
			for(int i = 0; i < json.length(); i++) {
				JSONObject obj = json.getJSONObject(i);
				PaseUsuario to=new PaseUsuario();
				Optional<PaseUsuario> optional=paseUsuarioService.getOne(obj.getInt("VentaDetalle"));

				if(!optional.isPresent()) {
					to.setActivo(true);
					to.setIdProd(obj.getInt("IDProdServ"));
					Date fechaCompra = new Date(TimeUnit.MILLISECONDS.toMillis(obj.getLong("FechaCaptura")));
					to.setF_compra(fechaCompra);
					to.setConcepto(obj.getString("Concepto"));
					to.setIdVentaDetalle(obj.getInt("VentaDetalle"));
					to.setCliente(clienteService.findById(idCliente));
					to.setCantidad(obj.getInt("Cantidad"));
					to.setConsumido(0);
					to.setCreated(new Date());
					to.setDisponibles(obj.getInt("Cantidad"));
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






				/*JSONObject obj = json.getJSONObject(i);
				int concepto = obj.getInt("IDProdServ");
				PaseUsuario pase = new PaseUsuario();
				pase.setIdProd(concepto);
				if ((concepto >= 1834 && concepto <= 1846) || (concepto == 1746 || concepto == 1747)) {
					pase.setCantidad(0);
					pase.setDisponibles(0);
					pase.setConsumido(0);
					if (concepto == 1747) {
						pase.setIdProd(1746);
					}
				}
				pase.setCantidad(Math.round(obj.getFloat("Cantidad")));
				pase.setIdVentaDetalle(obj.getInt("VentaDetalle"));
				pase.setConcepto(obj.getString("Concepto"));

				Date fechaCompra = new Date(TimeUnit.MILLISECONDS.toMillis(obj.getLong("FechaCaptura")));
				//System.out.println("Long: " + obj.getLong("FechaCaptura"));
				//System.out.println("Fecha parseada = " + fechaCompra);
				pase.setF_compra(fechaCompra);
				pase.setActivo(true);
				pase.setCliente(clienteService.findById(idCliente));
				pase.setCreated(new Date());
				pase.setUpdated(new Date());
				paseUsuarioService.save(pase);
				pase.setUpdatedBy(String.valueOf(idCliente));
				pase.setCreatedBy(String.valueOf(idCliente));*/
			}


	    	for(int i=0;i<listaReporte.size();i++) {
		    	PaseUsuario paseUsuario=new PaseUsuario();
	    		paseUsuario.setIdVentaDetalle(listaReporte.get(i).getIdVentaDetalle());		    	
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
	    	
	    	return paseUsuarioService.getByIdCliente(idCliente);
		}
	 	
	 	/*@GetMapping("/obtenerPaseEmpleadoQR/{idEmpleado}")
		@ResponseBody
		public ResponseEntity<?> obtenerPaseEmpleadoQR(@PathVariable("idEmpleado") int idEmpleado)
	   	{
			PaseUsuario paseUsuario=new PaseUsuario();
			paseUsuario.setActivo(true);
			paseUsuario.setCantidad(0);
			paseUsuario.setConcepto(String.valueOf(idEmpleado));
			paseUsuario.setConsumido(0);
			paseUsuario.setCreated(new Date());
			paseUsuario.setCreatedBy(String.valueOf(idEmpleado));
			paseUsuario.setDisponibles(0);
			paseUsuario.setF_compra(new Date());
			paseUsuario.setIdProd(1808);
			paseUsuario.setUpdated(new Date());
			paseUsuario.setUpdatedBy(String.valueOf(idEmpleado));
			Session currentSession = entityManager.unwrap(Session.class);
			javax.persistence.Query query = currentSession.createNativeQuery("Select coalesce(min(id_venta_detalle),-1) from pase_usuario WHERE id_venta_detalle<0;");	    	   
	    	int max = ((Number) query.getSingleResult()).intValue();
			paseUsuario.setIdVentaDetalle(max-1);
			paseUsuarioService.save(paseUsuario);
			List<PaseUsuario> paseAux=new ArrayList<PaseUsuario>();
			paseAux.add(paseUsuario);
  	   	
   			return new ResponseEntity<>(paseAux, HttpStatus.OK);	
	   			
	   		}*/
		@GetMapping("/obtenerPaseEmpleadoQR/{idEmpleado}")
		@ResponseBody
		public ResponseEntity<?> obtenerPaseEmpleadoQR(@PathVariable("idEmpleado") String idEmpleado)
		{
			try {
				employeeService.saveEmployees();
				//Employee employee = employeeService.findById(idEmpleado);
				List<PaseUsuario> paseUsuarioList = employeeService.getParkingQR(idEmpleado);
				if (paseUsuarioList == null) throw new RuntimeException("Empleado no activo o no existe");
				return new ResponseEntity<>(paseUsuarioList, HttpStatus.OK);
			} catch (Exception e) {
				System.out.println("Exception en QR Empleado => " + e.getMessage());
				//e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
	 	
	 	//------------------------------------------web services para app
	 	
	 	@GetMapping("/tiempoLimite/{idCliente}")
		@ResponseBody
		public ResponseEntity<?> tiempoLimite(@PathVariable("idCliente") int idCliente){
		 	Session currentSession = entityManager.unwrap(Session.class);
		 	Query listaClases;
		 	listaClases = currentSession.createNativeQuery("select hora_entrada as hora_evento,(select CASE  WHEN  sentido THEN 'ENTRADA'"
		 			+ " ELSE 'SALIDA' END AS SENTIDO from caseta_antena where caseta_antena.antena_id=registro_parking.id_antena and"
		 			+ " caseta_antena.caseta_id=registro_parking.id_caseta ),registro_tag.id_chip,tipo_acceso.nombre from registro_parking "
		 			+ "join registro_tag on registro_parking.id_chip=registro_tag.id join parking_usuario on id_parking=id_venta_detalle"
		 			+ " join tipo_acceso on tipo_acceso.id=registro_parking.tipo_Acceso where idcliente="+idCliente+" or "
		 					+ "id_empleado=("+idCliente+"-90000) order by hora_entrada desc limit 1;");
		
		 	List<Object[]> listResults = listaClases.getResultList();
		 	String sentido;
		 	try {
				sentido=(String) listResults.get(0)[1];		 		
		 	}catch(IndexOutOfBoundsException e) {
		 		RegistroUsuarioDTO registroParking=new RegistroUsuarioDTO();
				registroParking.setHoraEvento(new Date());
				registroParking.setIdChip(BigInteger.valueOf(0));
				registroParking.setSentido("SIN REGISTRO DE ENTRADA");
				registroParking.setTipoAcceso("SIN REGISTRO DE ENTRADA");
				return new ResponseEntity<>(registroParking, HttpStatus.OK);
		 	}
			RegistroUsuarioDTO registroParking=new RegistroUsuarioDTO();
			registroParking.setHoraEvento(new Date());
			registroParking.setIdChip(BigInteger.valueOf(0));
			registroParking.setSentido("SIN REGISTRO DE ENTRADA");
			registroParking.setTipoAcceso("SIN REGISTRO DE ENTRADA");
			// Formatear la fecha en el formato "dd-MM-yyyy"
			SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
			Date horaEvento = (Date) listResults.get(0)[0];
			// Obtener la representacion formateada de las fechas
			String fechaFormateada1 = formato.format(horaEvento);
			String fechaFormateada2 = formato.format(new Date());
			try {
				Date horaEvento2 = formato.parse(fechaFormateada1);
				Date hoy2 = formato.parse(fechaFormateada2);
				if (horaEvento2.before(hoy2))  return new ResponseEntity<>(registroParking, HttpStatus.OK);
			} catch(Exception e) {
				System.out.println("No se pudo parsear fecha en /parking/tiempoLimite/" + idCliente);
			}
			if(sentido.equals("ENTRADA")) {
				registroParking.setHoraEvento((Date) listResults.get(0)[0]);
				registroParking.setIdChip((BigInteger) listResults.get(0)[2]);
				registroParking.setSentido((String) listResults.get(0)[1]);

				// Calculo de las 4:20 horas
				LocalDateTime localDateTime = horaEvento.toInstant()
						.atZone(ZoneId.systemDefault())
						.toLocalDateTime();

				// Sumar las 4 horas 20 minutos
				LocalDateTime limiteEstacionamiento = localDateTime.plusMinutes(260);

				// Calcular el tiempo restante
				LocalDateTime hoy = LocalDateTime.now().minusHours(1);
				Duration duracion = Duration.between(limiteEstacionamiento, hoy);
				long hours = duracion.toHours();
				long minutes = duracion.toMinutes() % 60;
				String s = "";
				if (hours < 0 || minutes < 0)
					s += String.format("%d:%02d", Math.abs(hours), Math.abs(minutes));
				else
					s += String.format("-%d:%02d", Math.abs(hours), Math.abs(minutes));
				registroParking.setTipoAcceso(s);
			}
			
			
			return new ResponseEntity<>(registroParking, HttpStatus.OK);
			
			
		}
	 	@GetMapping("/ultimoRegistro/{idCliente}")
		@ResponseBody
		public ResponseEntity<?> ultimoRegistro(@PathVariable("idCliente") int idCliente){
		 	Session currentSession = entityManager.unwrap(Session.class);
		 	Query listaClases;
		 	listaClases = currentSession.createNativeQuery("select hora_entrada as hora_evento,(select CASE  WHEN  sentido THEN 'ENTRADA'"
		 			+ " ELSE 'SALIDA' END AS SENTIDO from caseta_antena where caseta_antena.antena_id=registro_parking.id_antena and"
		 			+ " caseta_antena.caseta_id=registro_parking.id_caseta ),registro_tag.id_chip,tipo_acceso.nombre from registro_parking "
		 			+ "join registro_tag on registro_parking.id_chip=registro_tag.id join parking_usuario on id_parking=id_venta_detalle"
		 			+ " join tipo_acceso on tipo_acceso.id=registro_parking.tipo_Acceso where idcliente="+idCliente+" or "
		 					+ "id_empleado=("+idCliente+"-90000) order by hora_entrada desc limit 1;");
		
		 	List<Object[]> listResults = listaClases.getResultList();
			RegistroUsuarioDTO registroParking=new RegistroUsuarioDTO();
			try {

				registroParking.setHoraEvento((Date) listResults.get(0)[0]);
				registroParking.setIdChip((BigInteger) listResults.get(0)[2]);
				registroParking.setSentido((String) listResults.get(0)[1]);
				registroParking.setTipoAcceso((String) listResults.get(0)[3]);
			}catch(IndexOutOfBoundsException e) {
				registroParking.setHoraEvento(new Date());
				registroParking.setIdChip(BigInteger.valueOf(0));
				registroParking.setSentido("NO SE TIENEN REGISTROS DE ENTRADAS O SALIDAS");
				registroParking.setTipoAcceso("NO SE TIENEN REGISTROS DE ENTRADAS O SALIDAS");
			}
			
			
			return new ResponseEntity<>(registroParking, HttpStatus.OK);
			
			
		}
	 	@GetMapping("/ultimaAmonestacion/{idCliente}")
		@ResponseBody
		public ResponseEntity<?> ultimaAmonestacion(@PathVariable("idCliente") int idCliente){
	 		Session currentSession = entityManager.unwrap(Session.class);
		 	Query<Amonestaciones> listaClases;
		 	listaClases = currentSession.createNativeQuery("select * from amonestaciones where  id_chip in (select id_chip from parking_usuario join registro_tag on"
		 			+ " parking_usuario.id_venta_detalle=registro_tag.id_parking where idcliente="+idCliente+") order by cantidad_amonestaciones "
		 			+ "limit 1;",Amonestaciones.class);
		
		 	List<Amonestaciones> listResults = listaClases.getResultList();
			AmonestacionesDTO amonestacion=new AmonestacionesDTO();
			long tiempo = 0;
			try {
				tiempo=listResults.get(0).obtenerHoraSalidaDate().getTime()-listResults.get(0).obtenerHoraEntradaDate().getTime();
			}catch(IndexOutOfBoundsException e) {
				amonestacion.setIncidencia("Sin Amonestaciones");
				amonestacion.setHoraEntrada("Sin Amonestaciones");
				amonestacion.setHoraSalida("Sin Amonestaciones");
				amonestacion.setTexto("Sin Amonestaciones");
				return new ResponseEntity<>(amonestacion, HttpStatus.OK);
			}
			long horas=TimeUnit.MILLISECONDS.toHours(tiempo);
			long minutos=TimeUnit.MILLISECONDS.toMinutes(tiempo%3600000);
			String texto="";
			if(minutos<10)
				texto=horas+":0"+minutos;
			else
				texto=horas+":"+minutos;
			if(listResults.get(0).getCantidadAmonestaciones()==1) {
				amonestacion.setIncidencia("Primera Incidencia");
				amonestacion.setHoraEntrada(listResults.get(0).getHoraEntrada());
				amonestacion.setHoraSalida(listResults.get(0).getHoraSalida());
				amonestacion.setTexto(texto);
				
			}
			if(listResults.get(0).getCantidadAmonestaciones()==2) {
				amonestacion.setIncidencia("Segunda Incidencia");
				amonestacion.setHoraEntrada(listResults.get(0).getHoraEntrada());
				amonestacion.setHoraSalida(listResults.get(0).getHoraSalida());
				amonestacion.setTexto(texto);
				
			}
			if(listResults.get(0).getCantidadAmonestaciones()==3) {
				amonestacion.setIncidencia("Tercera Incidencia");
				amonestacion.setHoraEntrada(listResults.get(0).getHoraEntrada());
				amonestacion.setHoraSalida(listResults.get(0).getHoraSalida());
				amonestacion.setTexto(texto);
				
			}
			if(listResults.get(0).getCantidadAmonestaciones()==4) {
				amonestacion.setIncidencia("Cuarta Incidencia");
				amonestacion.setHoraEntrada(listResults.get(0).getHoraEntrada());
				amonestacion.setHoraSalida(listResults.get(0).getHoraSalida());
				amonestacion.setTexto(texto);
				
			}
			Cliente cliente=clienteService.findById(idCliente);
			amonestacion.setIdCliente(idCliente);
			amonestacion.setMembresia(cliente.getNoMembresia());
			amonestacion.setIdChip(listResults.get(0).getIdChip());
			
			
			return new ResponseEntity<>(amonestacion, HttpStatus.OK);
			
			
		}

		@GetMapping("/getQRbyClub/{idClub}/{idFolio}")
		public ResponseEntity<?> getQRByClub(@PathVariable String idClub, @PathVariable String idFolio) {
			try {
				EstacionamientoExterno estacionamientoExterno = estacionamientoExternoService.getByIdRegistro(idFolio, idClub);
				QRParking qrParking = qrParkingService.findByClubAndIdRegistro(idClub, idFolio);

				if (qrParking != null && !qrParking.isPagado()) return new ResponseEntity<>(qrParking, HttpStatus.ACCEPTED);
				else if (estacionamientoExterno.getActivo() || (qrParking != null && qrParking.isPagado())) return new ResponseEntity<>(
						qrParking != null ? qrParking : estacionamientoExterno, HttpStatus.NO_CONTENT);
				else return new ResponseEntity<>(estacionamientoExterno, HttpStatus.OK);

			} catch (Exception e) {
				JSONObject notFound = new JSONObject("{\"respuesta\": \"folio no encontrado\"}");
				return new ResponseEntity<>(notFound.toMap(), HttpStatus.NOT_FOUND);
			}
		}

		@GetMapping("/getStatusQRbyClub/{idClub}/{idFolio}")
		public ResponseEntity<?> getStatusQRbyClub(@PathVariable String idClub, @PathVariable String idFolio) {
			try {
				QRParking qrParking = qrParkingService.findByClubAndIdRegistro(idClub, idFolio);
				if (qrParking != null && qrParking.isPagado()) return new ResponseEntity<>(qrParking, HttpStatus.OK);
				else {
					JSONObject notFound = new JSONObject("{\"respuesta\": \"folio no activo\"}");
					return new ResponseEntity<>(notFound.toMap(), HttpStatus.NOT_FOUND);
				}
			} catch (Exception e) {
				JSONObject notFound = new JSONObject("{\"respuesta\": \"folio no activo\"}");
				return new ResponseEntity<>(notFound.toMap(), HttpStatus.NOT_FOUND);
			}
		}

		@PostMapping("/qrParking")
		public ResponseEntity<?> createQRParking(@RequestBody QRParkingDTO qrParkingDTO) {
			try {
				//System.out.println("QRREquest => " + qrParkingDTO);
				return new ResponseEntity<>(qrParkingService.save(qrParkingDTO), HttpStatus.CREATED);
			} catch(Exception e) {
				System.out.println("Error QRParking POST: " + e.getMessage());
				//e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}

		@PostMapping("/generarCorte")
		public ResponseEntity<?> generarCorte(@RequestBody QRParkingDTO qrParkingDTO){
			try	{
				return new ResponseEntity<>(qrParkingService.generarCorte(qrParkingDTO.getClub(), qrParkingDTO.getIdUsuario()), HttpStatus.OK);
			} catch(Exception e) {
				System.out.println("fallo el corte de cortesias!!!");
				e.getMessage();
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		@PostMapping("/generarCorteCortesias")
		public ResponseEntity<?> generarCorteCortesias(@RequestBody QRParkingDTO qrParkingDTO){
			try	{
				return new ResponseEntity<>(qrParkingService.generarCorteCortesias(qrParkingDTO.getClub(), qrParkingDTO.getIdUsuario()), HttpStatus.OK);
			} catch(Exception e) {
				System.out.println("fallo el corte!!!");
				e.getMessage();
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		@PostMapping("/generarCorteQRGeneral")
		public ResponseEntity<?> generarCorteQRGeneral(@RequestBody QRParkingDTO qrParkingDTO){
			try	{
				return new ResponseEntity<>(qrParkingService.generarCorteQRGeneral(qrParkingDTO.getClub(), qrParkingDTO.getIdUsuario()), HttpStatus.OK);
			} catch(Exception e) {
				System.out.println("fallo el corte!!!");
				e.getMessage();
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		@GetMapping("/getChipInfo/{chipID}")
		public ResponseEntity<?> getChipInfo(@PathVariable String chipID) {
			return new ResponseEntity<>(parkingUsuarioService.getChipInfo(chipID), HttpStatus.OK);
		}

		@GetMapping("/getQRCostByUser/{userID}")
		public ResponseEntity<?> getCostByUser(@PathVariable int userID) throws Exception {
			QREstacionamientoCosto qrEstacionamientoCosto = qrEstacionamientoCostoRepository.findById(userID).orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userID));
			return new ResponseEntity<>(qrEstacionamientoCosto, HttpStatus.OK);
		}
	 	public String update(int horarioId){
			
			try {
				JSONObject json = new JSONObject(IOUtils.toString(new URL("http://192.168.20.107:8000/ServiciosClubAlpha/api/Miembro/"+horarioId), Charset.forName("UTF-8")));
				
				NuevoUsuario nuevoUsuario=new NuevoUsuario();
				nuevoUsuario.setCliente(json.getString("Nombre")+" "+json.getString("ApellidoPaterno")+" "+json.getString("ApellidoMaterno"));
				nuevoUsuario.setClienteTipo(json.getJSONObject("TipoCliente").getString("Nombre"));
				nuevoUsuario.setEmail(json.getString("EMail"));
				nuevoUsuario.setEstatus(json.getJSONObject("EstatusCliente").getString("Nombre"));
				nuevoUsuario.setEstatusCobranza(json.getJSONObject("EstatusCobranza").getString("Nombre"));
				nuevoUsuario.setNivel1(json.getJSONObject("Club").getString("Nombre"));
				nuevoUsuario.setNombre(json.getString("NoMembresia"));
				nuevoUsuario.setNombreUsuario(Integer.toString(horarioId));
				String x[];
				x = String.valueOf(json.get("FechaNacimiento")).split("T");
            	x = x[0].split("-");
				nuevoUsuario.setPassword(horarioId +"."+ x[0] + x[1] + x[2]);
				
            	Usuario usuario;
            	try {
            		usuario=usuarioService.getByNombreUsuario(nuevoUsuario.getNombreUsuario()).get() ;
					usuario.setCliente(nuevoUsuario.getCliente());
					usuario.setClienteTipo(nuevoUsuario.getClienteTipo());
					usuario.setEmail(nuevoUsuario.getEmail());
					usuario.setEstatus(nuevoUsuario.getEstatus());
					usuario.setNivel(nuevoUsuario.getNivel1());
					usuario.setNombre(nuevoUsuario.getNombre());
					usuario.setNombreUsuario(nuevoUsuario.getNombreUsuario());
					usuario.setEstatusCobranza(nuevoUsuario.getEstatusCobranza());
					usuarioService.save(usuario);
            	}catch(NoSuchElementException e) {
            		usuario= new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getNombreUsuario(), nuevoUsuario.getEmail(),
                             passwordEncoder.encode(nuevoUsuario.getPassword()),nuevoUsuario.getEstatus(),nuevoUsuario.getCliente(),nuevoUsuario.getClienteTipo(),nuevoUsuario.getEstatusCobranza(),nuevoUsuario.getNivel1());
		             Set<Rol> roles = new HashSet<>();
		             roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
		             if(nuevoUsuario.getRoles().contains("admin")) {
		                 roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
		             }
		             usuario.setRoles(roles);
		             usuarioService.save(usuario);
            	}catch(Exception e) {
					e.printStackTrace();
				}

        		//SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            	Cliente cliente=clienteService.findById(horarioId);
            	if(cliente!=null) {
            		cliente.setApellidoMaterno(json.getString("ApellidoMaterno"));
            		cliente.setApellidoPaterno(json.getString("ApellidoPaterno"));
            		Categoria categoria=categoriaService.findById(json.getJSONObject("Categoria").getInt("Id"));
            		if(categoria!=null)
            			cliente.setCategoria(categoria);
            		else {
            			categoria=new Categoria();
            			categoria.setActivo(true);
            			categoria.setFechaCreacion(new Date());
            			categoria.setFechaModificacion(new Date());
            			categoria.setId(json.getJSONObject("Categoria").getInt("Id"));
            			categoria.setNombre(json.getJSONObject("Categoria").getString("Nombre"));
            			categoriaService.save(categoria);
            			cliente.setCategoria(categoria);
            		}
            		Club club=clubService.findById(json.getJSONObject("Club").getInt("Id"));
            		if(club!=null)
            			cliente.setClub(club);
            		else {
            			club=new Club();
            			club.setActivo(true);
            			club.setFechaCreacion(new Date());
            			club.setFechaModificacion(new Date());
            			club.setIdClub(json.getJSONObject("Club").getInt("Id"));
            			club.setNombre(json.getJSONObject("Club").getString("Nombre"));
            			clubService.save(club);
            			cliente.setClub(club);
            		}
            		cliente.setDomicilioPago(json.getBoolean("DomiciliaPago"));
            		cliente.setEmail(json.getString("EMail"));
            		cliente.setEstatusAcceso(json.getString("EstatusAcceso"));
            		EstatusCliente estatusCliente=estatusClienteService.findById(json.getJSONObject("EstatusCliente").getInt("Id"));
            		if(estatusCliente !=null)
            			cliente.setEstatusCliente(estatusCliente);
            		else {
            			estatusCliente=new EstatusCliente();
            			estatusCliente.setActivo(true);
            			estatusCliente.setFechaCreacion(new Date());
            			estatusCliente.setFechaModificacion(new Date());
            			estatusCliente.setIdStatusCliente(json.getJSONObject("EstatusCliente").getInt("Id"));
            			estatusCliente.setNombre(json.getJSONObject("EstatusCliente").getString("Nombre"));
            			estatusClienteService.save(estatusCliente);
            			cliente.setEstatusCliente(estatusCliente);
            		}
            		EstatusCobranza estatusCobranza=estatusCobranzaService.findById(json.getJSONObject("EstatusCobranza").getInt("Id"));
            		if(estatusCobranza != null) {
                		cliente.setEstatusCobranza(estatusCobranza);
            		}else {
            			estatusCobranza=new EstatusCobranza();
            			estatusCobranza.setActivo(true);
            			estatusCobranza.setDescripcion(json.getJSONObject("EstatusCobranza").getString("Nombre"));
            			estatusCobranza.setFechaCreacion(new Date());
            			estatusCobranza.setFechaModificacion(new Date());
            			estatusCobranza.setIdEstatusCobranza(json.getJSONObject("EstatusCobranza").getInt("Id"));
            			estatusCobranzaService.save(estatusCobranza);
            			cliente.setEstatusCobranza(estatusCobranza);
            		}
            		EstatusMembresia estatusMembresia=estatusMembresiaService.findById(json.getJSONObject("EstatusMembresia").getInt("Id"));
            		if(estatusMembresia!=null) {
                		cliente.setEstatusMembresia(estatusMembresia);
            		}else {
            			estatusMembresia=new EstatusMembresia();
            			estatusMembresia.setActivo(true);
            			estatusMembresia.setDescripcion(json.getJSONObject("EstatusMembresia").getString("Nombre"));
            			estatusMembresia.setFechaCreacion(new Date());
            			estatusMembresia.setFechaModificacion(new Date());
            			estatusMembresia.setIdEstatusMembresia(json.getJSONObject("EstatusMembresia").getInt("Id"));
            			estatusMembresiaService.save(estatusMembresia);
            			cliente.setEstatusMembresia(estatusMembresia);
            		}
            		cliente.setFechaModificacion(new Date());
            		cliente.setIdCaptura(json.getInt("IDCapturo"));
            		cliente.setIdCapturaFecha(json.getInt("IDCapturoFecha"));
            		cliente.setIdCliente(horarioId);
            		cliente.setIdClienteGrupo(json.getInt("IDClienteGrupo"));
            		cliente.setIdClienteSector(json.getInt("IDClienteSector"));
            		cliente.setIdSexo(json.getInt("IDSexo"));
            		cliente.setMensualidadPagada(json.getBoolean("MensualidadPagada"));
            		cliente.setNombre(json.getString("Nombre"));
            		cliente.setNoMembresia(json.getLong("NoMembresia"));
            		cliente.setSexo(json.getString("Sexo"));
            		cliente.setTieneAcceso(json.getBoolean("TieneAcceso"));
            		cliente.setTipoAcceso(json.getBoolean("TieneAcceso"));
            		TipoCliente tipoCliente=tipoClienteService.findById(json.getJSONObject("TipoCliente").getInt("Id"));
            		if(tipoCliente !=null)
            			cliente.setTipoCliente(tipoCliente);
            		else {
            			tipoCliente=new TipoCliente();
            			tipoCliente.setActivo(true);
            			tipoCliente.setDescripcion(json.getJSONObject("TipoCliente").getString("Nombre"));
            			tipoCliente.setFechaCreacion(new Date());
            			tipoCliente.setFechaModificacion(new Date());
            			tipoCliente.setIdTipoCliente(json.getJSONObject("TipoCliente").getInt("Id"));
            			tipoClienteService.save(tipoCliente);
            			cliente.setTipoCliente(tipoCliente);
            		}
            		TipoMembresia tipoMembresia=tipoMembresiaService.findById(json.getJSONObject("TipoMembresia").getInt("Id"));
            		if(tipoMembresia != null)
            			cliente.setTipoMembresia(tipoMembresia);
            		else {
            			tipoMembresia=new TipoMembresia();
            			tipoMembresia.setActivo(true);
            			tipoMembresia.setFechaCreacion(new Date());
            			tipoMembresia.setFechaModificacion(new Date());
            			tipoMembresia.setHorarioacceso(horarioAccesoService.findById(2));
            			tipoMembresia.setIdTipoMembresia(json.getJSONObject("TipoMembresia").getInt("Id"));
            			tipoMembresia.setNombre(json.getJSONObject("TipoMembresia").getString("Nombre"));
            			tipoMembresia.setTerminalId(json.getJSONObject("TipoMembresia").getInt("Id"));
            			tipoMembresiaService.save(tipoMembresia);
            			cliente.setTipoMembresia(tipoMembresia);                			
            		}
					if(cliente.getURLFoto()==null) {
						Foto foto=this.addFoto(json.getString("UrlFoto"),cliente);
						cliente.setURLFoto(foto);
					}else {
						cliente.setURLFoto(cliente.getURLFoto());						
					}
					cliente.setFechaNacimiento(formato.parse(json.getString("FechaNacimiento")) );
					cliente.setFechaFinAcceso(formato.parse(json.getString("FechaFinAcceso")) );
            		cliente.setInicioActividades(formato.parse(json.getString("InicioActividades")));
            		clienteService.save(cliente);
            		List<ParkingUsuario> pu=parkingUsuarioService.findByIdCliente(cliente);
            		for(int i=0;i<pu.size();i++) {
            			pu.get(i).setEstadoCobranza(cliente.getEstatusCobranza().getNombre());
            			parkingUsuarioService.save(pu.get(i));
            		}
            	}else {
            		cliente=new Cliente();
            		cliente.setApellidoMaterno(json.getString("ApellidoMaterno"));
            		cliente.setApellidoPaterno(json.getString("ApellidoPaterno"));
            		Categoria categoria=categoriaService.findById(json.getJSONObject("Categoria").getInt("Id"));
            		if(categoria!=null)
            			cliente.setCategoria(categoria);
            		else {
            			categoria=new Categoria();
            			categoria.setActivo(true);
            			categoria.setFechaCreacion(new Date());
            			categoria.setFechaModificacion(new Date());
            			categoria.setId(json.getJSONObject("Categoria").getInt("Id"));
            			categoria.setNombre(json.getJSONObject("Categoria").getString("Nombre"));
            			categoriaService.save(categoria);
            			cliente.setCategoria(categoria);
            		}
            		Club club=clubService.findById(json.getJSONObject("Club").getInt("Id"));
            		if(club!=null)
            			cliente.setClub(club);
            		else {
            			club=new Club();
            			club.setActivo(true);
            			club.setFechaCreacion(new Date());
            			club.setFechaModificacion(new Date());
            			club.setIdClub(json.getJSONObject("Club").getInt("Id"));
            			club.setNombre(json.getJSONObject("Club").getString("Nombre"));
            			clubService.save(club);
            			cliente.setClub(club);
            		}
            		cliente.setDomicilioPago(json.getBoolean("DomiciliaPago"));
            		cliente.setEmail(json.getString("EMail"));
            		cliente.setEstatusAcceso(json.getString("EstatusAcceso"));
            		EstatusCliente estatusCliente=estatusClienteService.findById(json.getJSONObject("EstatusCliente").getInt("Id"));
            		if(estatusCliente !=null)
            			cliente.setEstatusCliente(estatusCliente);
            		else {
            			estatusCliente=new EstatusCliente();
            			estatusCliente.setActivo(true);
            			estatusCliente.setFechaCreacion(new Date());
            			estatusCliente.setFechaModificacion(new Date());
            			estatusCliente.setIdStatusCliente(json.getJSONObject("EstatusCliente").getInt("Id"));
            			estatusCliente.setNombre(json.getJSONObject("EstatusCliente").getString("Nombre"));
            			estatusClienteService.save(estatusCliente);
            			cliente.setEstatusCliente(estatusCliente);
            		}
            		EstatusCobranza estatusCobranza=estatusCobranzaService.findById(json.getJSONObject("EstatusCobranza").getInt("Id"));
            		if(estatusCobranza != null) {
                		cliente.setEstatusCobranza(estatusCobranza);
            		}else {
            			estatusCobranza=new EstatusCobranza();
            			estatusCobranza.setActivo(true);
            			estatusCobranza.setDescripcion(json.getJSONObject("EstatusCobranza").getString("Nombre"));
            			estatusCobranza.setFechaCreacion(new Date());
            			estatusCobranza.setFechaModificacion(new Date());
            			estatusCobranza.setIdEstatusCobranza(json.getJSONObject("EstatusCobranza").getInt("Id"));
            			estatusCobranzaService.save(estatusCobranza);
            			cliente.setEstatusCobranza(estatusCobranza);
            		}
            		EstatusMembresia estatusMembresia=estatusMembresiaService.findById(json.getJSONObject("EstatusMembresia").getInt("Id"));
            		if(estatusMembresia!=null) {
                		cliente.setEstatusMembresia(estatusMembresia);
            		}else {
            			estatusMembresia=new EstatusMembresia();
            			estatusMembresia.setActivo(true);
            			estatusMembresia.setDescripcion(json.getJSONObject("EstatusMembresia").getString("Nombre"));
            			estatusMembresia.setFechaCreacion(new Date());
            			estatusMembresia.setFechaModificacion(new Date());
            			estatusMembresia.setIdEstatusMembresia(json.getJSONObject("EstatusMembresia").getInt("Id"));
            			estatusMembresiaService.save(estatusMembresia);
            			cliente.setEstatusMembresia(estatusMembresia);
            		}
            		cliente.setFechaModificacion(new Date());
            		cliente.setIdCaptura(json.getInt("IDCapturo"));
            		cliente.setIdCapturaFecha(json.getInt("IDCapturoFecha"));
            		cliente.setIdCliente(horarioId);
            		cliente.setIdClienteGrupo(json.getInt("IDClienteGrupo"));
            		cliente.setIdClienteSector(json.getInt("IDClienteSector"));
            		cliente.setIdSexo(json.getInt("IDSexo"));
            		cliente.setMensualidadPagada(json.getBoolean("MensualidadPagada"));
            		cliente.setNombre(json.getString("Nombre"));
            		cliente.setNoMembresia(json.getLong("NoMembresia"));
            		cliente.setSexo(json.getString("Sexo"));
            		cliente.setTieneAcceso(json.getBoolean("TieneAcceso"));
            		cliente.setTipoAcceso(json.getBoolean("TieneAcceso"));
            		TipoCliente tipoCliente=tipoClienteService.findById(json.getJSONObject("TipoCliente").getInt("Id"));
            		if(tipoCliente !=null)
            			cliente.setTipoCliente(tipoCliente);
            		else {
            			tipoCliente=new TipoCliente();
            			tipoCliente.setActivo(true);
            			tipoCliente.setDescripcion(json.getJSONObject("TipoCliente").getString("Nombre"));
            			tipoCliente.setFechaCreacion(new Date());
            			tipoCliente.setFechaModificacion(new Date());
            			tipoCliente.setIdTipoCliente(json.getJSONObject("TipoCliente").getInt("Id"));
            			tipoClienteService.save(tipoCliente);
            			cliente.setTipoCliente(tipoCliente);
            		}
            		TipoMembresia tipoMembresia=tipoMembresiaService.findById(json.getJSONObject("TipoMembresia").getInt("Id"));
            		if(tipoMembresia != null)
            			cliente.setTipoMembresia(tipoMembresia);
            		else {
            			tipoMembresia=new TipoMembresia();
            			tipoMembresia.setActivo(true);
            			tipoMembresia.setFechaCreacion(new Date());
            			tipoMembresia.setFechaModificacion(new Date());
            			tipoMembresia.setHorarioacceso(horarioAccesoService.findById(2));
            			tipoMembresia.setIdTipoMembresia(json.getJSONObject("TipoMembresia").getInt("Id"));
            			tipoMembresia.setNombre(json.getJSONObject("TipoMembresia").getString("Nombre"));
            			tipoMembresia.setTerminalId(json.getJSONObject("TipoMembresia").getInt("Id"));
            			tipoMembresiaService.save(tipoMembresia);
            			cliente.setTipoMembresia(tipoMembresia);                			
            		}cliente.setActivo(true);
            		cliente.setFechaCreacion(new Date());
            		cliente.setFechaModificacion(new Date());
					cliente.setFechaNacimiento(formato.parse(json.getString("FechaNacimiento")) );
					cliente.setFechaFinAcceso(formato.parse(json.getString("FechaFinAcceso")) );
            		cliente.setInicioActividades(formato.parse(json.getString("InicioActividades")));
					Foto foto=this.addFoto(json.getString("UrlFoto"),cliente);
					cliente.setURLFoto(foto);
					clienteService.save(cliente);
					List<ParkingUsuario> pu=parkingUsuarioService.findByIdCliente(cliente);
            		for(int i=0;i<pu.size();i++) {
            			pu.get(i).setEstadoCobranza(cliente.getEstatusCobranza().getNombre());
            			parkingUsuarioService.save(pu.get(i));
            		}
            	}
            	
            	
	             
			}catch(Exception e) {
				e.printStackTrace();
			}
			
	        return "almacenado";
	    }//Fin del metodo
	 	public Foto addFoto(String url2,Cliente cliente) {
	    	
	    	String[] parts = url2.split("\\\\");
	    	try{
		        String path=url+parts[7];
		        
		             //The NtlmPasswordAuthentication class is used for domain authentication and requires three parameters. The first is the domain name. If not, assign a value of null, the second is the user name, and the third is the password.
		     
		        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("",user, pass);
		     
		             //Create file and get file stream
		        SmbFile file = new SmbFile(path,auth);
		        if(file.exists()){
		            InputStream is = file.getInputStream();
		            byte[] bytes = IOUtils.toByteArray(is);
		            Foto foto=new Foto(bytes);				
					foto.setFechaCreacion(new Date());
					foto.setFechaModificacion(new Date());
					foto.setActivo(true);
					foto.setCliente(cliente);
					fotoService.save(foto);
			        return foto;
		        }
		    }catch(Exception e){
		    	e.printStackTrace();
		    }finally {
		    }
			return null;
	    }//fin del metodo
		public Date hora( Date date){
	 			String format = "yyyy/MM/dd HH:mm:ss";
	 		    SimpleDateFormat sdf = new SimpleDateFormat(format);
	 		    // Convert Local Time to UTC (Works Fine)
	 		    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
	 		  SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	 	        Date fechaDate = null;
	 	        try {
	 	            fechaDate = formato.parse(sdf.format(date));
	 	        } 
	 	        catch (ParseException ex) 
	 	        {
	 	            System.out.println(ex);
	 	        }
	 		    return fechaDate;
	       
			
		}


	@GetMapping("/calcularAmonestaciones2")
	@ResponseBody
	public ResponseEntity<?> calcularAmonestaciones2() {
		Session currentSession = entityManager.unwrap(Session.class);
		Query listaClases;
		 	/*listaClases = currentSession.createNativeQuery("SELECT *,(select sentido from caseta_antena where "
		 			+ "caseta_antena.antena_id=registro_parking.id_antena and caseta_antena.caseta_id=registro_parking.id_caseta ) from "
		 			+ "registro_parking where hora_entrada between current_timestamp - '30 hr'::INTERVAL and current_timestamp -'6 hr'::INTERVAL "
		 			+ "AND ACCESO IS TRUE and id_chip is not null order by id_chip,hora_entrada;");*/
		listaClases = currentSession.createNativeQuery("SELECT id,hora_Evento,id_antena,id_Caseta,id_chip,mensaje,raw,acceso,"
				+ "hora_Entrada,tipo_acceso,(select sentido from caseta_antena where caseta_antena.antena_id=registro_parking.id_antena and"
				+ " caseta_antena.caseta_id=registro_parking.id_caseta ) from registro_parking WHERE ACCESO IS TRUE and raw = '176e4f' and hora_entrada between " +
				"'2024-03-01 00:00:00' and '2024-03-01 23:59:59'  order by hora_entrada;");

		List<Object[]> listResults = listaClases.getResultList();
		//currentSession.close();
		List<RegistroParkingDTO> listaDTO = new ArrayList<RegistroParkingDTO>();
		for (Object[] record : listResults) {
			RegistroParkingDTO registroParking = new RegistroParkingDTO();
			for (int i = 0; i < record.length; i++) {
				registroParking.setAcceso((boolean) record[7]);
				registroParking.setHoraEntrada(((Date) record[8]).getTime());
				registroParking.setHoraEvento((Date) record[1]);
				registroParking.setIdAntena(antenaService.getOne((int) record[2]).get());
				registroParking.setIdCaseta(casetaService.getOne((int) record[3]).get());
				registroParking.setIdChip(registroTagService.getOne((int) record[4]).get());
				registroParking.setMensaje((String) record[5]);
				registroParking.setRaw((String) record[6]);
				registroParking.setSentido((boolean) record[10]);
				registroParking.setTipoAcceso(tipoAccesoService.getOne((int) record[9]).get());
			}
			listaDTO.add(registroParking);
		}
		ArrayList<ChipHora> chipHoras = new ArrayList<ChipHora>();
		for (int i = 0; i + 1 < listaDTO.size(); i++) {
			if (listaDTO.get(i).getIdChip() == listaDTO.get(i + 1).getIdChip()) {
				if (listaDTO.get(i).isSentido() && !listaDTO.get(i + 1).isSentido()) {
					ChipHora chipHora = new ChipHora();
					long between = (listaDTO.get(i + 1).getHoraEntrada().getTime() - listaDTO.get(i).getHoraEntrada().getTime()) / 1000;
					int hour1 = (int) (between % (24 * 3600) / 3600);
					float horas = (between % (24f * 3600f) / 3600f); //tiene que ser asi porque si no no toma los decimales
					System.out.println("el between " + between);
					System.out.println("primt horas int => " + hour1);
					System.out.println("primt horas float => " + horas);
					//tolerancia 260 minutos = 4:20 horas
					//float horas = 4.31667f; //259 minutos valido
					//float horas = 4.33333f; //260 minutos valido
					//float horas = 4.35f; // 261 minutos excedio

					if(horas >= 4.35f) {
						System.out.println("Excediste el tiempo en el estacionamiento: " + horas);
					}else {
						System.out.println("Tiempo valido de estacionamiento: " + horas);
					}
					chipHora.setHoras(horas);
					chipHora.setIdChip(listaDTO.get(i).getIdChip());
					chipHora.setHoraEntrada(listaDTO.get(i).getHoraEntrada());
					chipHora.setHoraSalida(listaDTO.get(i + 1).getHoraEntrada());
					chipHoras.add(chipHora);
				}
			}


		}

		return new ResponseEntity<>(chipHoras, HttpStatus.OK);
	}

	@GetMapping("/licensePlatesByClub/{clubID}")
	public ResponseEntity<?> getLicensePlatesByClub(@PathVariable("clubID") int clubID){
		return new ResponseEntity<>(registroTagService.getLicensePlatesByClub(clubID), HttpStatus.OK);
	}
}//fin de la clase

