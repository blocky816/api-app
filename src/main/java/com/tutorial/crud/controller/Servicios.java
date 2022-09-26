package com.tutorial.crud.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.io.IOUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutorial.crud.aopDao.endpoints;
import com.tutorial.crud.correo.Correo;
import com.tutorial.crud.dto.ClienteDTOO;
import com.tutorial.crud.dto.ClienteReferenciado;
import com.tutorial.crud.dto.ClienteRutina;
import com.tutorial.crud.dto.DatosFiscalesDTO;
import com.tutorial.crud.dto.DomiciliacionDatos;
import com.tutorial.crud.dto.FacturaOnline;
import com.tutorial.crud.dto.GlobalInformation;
import com.tutorial.crud.dto.Item;
import com.tutorial.crud.dto.ItemFallido;
import com.tutorial.crud.dto.MensajeError;
import com.tutorial.crud.dto.MovimientoDTO;
import com.tutorial.crud.dto.PagoApi;
import com.tutorial.crud.dto.Receiver;
import com.tutorial.crud.dto.ReciboSAT;
import com.tutorial.crud.dto.Tax;
import com.tutorial.crud.dto.body2;
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

@RestController
@RequestMapping("/alpha")
@CrossOrigin(origins = "*")
public class Servicios 
{

	@Autowired
	private EntityManager entityManager;
	


	endpoints e = new endpoints();
    
	
    @Autowired
    configuracionService configuracionService;
    
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    AccesosClubService accesosClubService;
    

    @Autowired
    DatosUsuarioService datosUsuarioService;
    
    @Autowired
    RegistroFacturaGlobalService registroFacturaGlobalService;
    
    @Autowired
    PaseUsuarioService paseUsuarioService;

    @Autowired
    RolService rolService;
    
	//service para club
	@Autowired
	private ClubService clubService;
	//service para lunes
	@Autowired
	private LunesService lunesService;
	
	//service para martes
	@Autowired
	private MartesService martesService;
	
	//service para miercoles
	@Autowired
	private MiercolesService miercolesService;
	
	//service para jueves
	@Autowired
	private JuevesService juevesService;
	

	//service para viernes
	@Autowired
	private ViernesService viernesService;

	//service para sabado
	@Autowired
	private SabadoService sabadoService;
	

	//service para domingo
	@Autowired
	private DomingoService domingoService;
	
	//service para horario
	@Autowired
	private HorarioService horarioService;
	
	//service para horario acceso
	@Autowired
	private HorarioAccesoService horarioAccesoService;
	
	//service para horario otro club
	@Autowired
	private HorarioOtroClubService horarioOtroClubService;
	
	//service para mensajes
	@Autowired
	private MensajesService mensajesService;
	
	//service para estatus cobranza
	@Autowired
	private EstatusCobranzaService estatusCobranzaService;
	
	//service para estatus membresia
	@Autowired
	private EstatusMembresiaService estatusMembresiaService;
	
	//service para tipo cliente
	@Autowired
	private TipoClienteService tipoClienteService;
	
	//service para curso detalle 
	@Autowired
	private CursoDetalleService cursoDetalleService;
	

	//service para curso horario
	@Autowired
	private CursoHorarioService cursoHorarioService;
	
	//service para orden venta
	@Autowired
	private OrdenVentaService ordenVentaService;
	
	//service para pago
	@Autowired
	private PagoService pagoService;
	
	//service para pase
	@Autowired
	private PaseService paseService;
	
	//service para pedido
	@Autowired
	private PedidoService pedidoService;
	
	//service para pedido detalle
	@Autowired
	private PedidoDetalleService pedidoDetalleService;
	
	
	//service para venta
	@Autowired
	private VentaService ventaService;
	
	//sevice para categoria
	@Autowired
	private CategoriaService categoriaService;
	
	//service para estatus cliente
	@Autowired
	private EstatusClienteService estatusClienteService;
	
	//service para tipo membresia
	@Autowired
	private TipoMembresiaService tipoMembresiaService;
	
	@Autowired
	private EstadoDeCobranzaService acumuladoService;
	
    @Autowired
    PasswordEncoder passwordEncoder;
    
	//service para cliente
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private FotoServiceImpl fotoService;
	
	@Autowired
	private MovimientosService movimientosService;	

	@Autowired
	private ParkingUsuarioService parkingUsuarioService;		

	@Autowired
	private FacturaService facturaService;	

	@Autowired
	private ReferenciaService referenciaService;	

	@Autowired
	private ClienteDomiciliadoService clienteDomiciliadoService;	
	
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
	
	@Value("${my.property.global}")
	String dbURLGlobal;

	@Value("${my.property.userGlobal}")
	String userGlobal;

	@Value("${my.property.passGlobal}")
    String passGlobal;
	@Value("${my.property.codigoPostal}")
	String codigoPostal;

	@Value("${my.property.rfc}")
	String rfc;
	

	@Value("${my.property.tipoCFDI}")
	String tipoCFDI;
	

	@Value("${my.property.uso}")
	String uso;
	

	@Value("${my.property.regimenFiscal}")
	String regimenFiscal;
	
	@Value("${my.property.name}")
	String name;
	
	@Value("${my.property.correoFactura}")
	String correoFactura;
	

	@Value("${my.property.passFacturama}")
	String basicAuth;
	
	@Value("${my.property.apiFacturama}")
	String api;
	
	@Value("${my.property.usuarioCorreo}")
	String usuarioCorreo;
	
	@Value("${my.property.contrasenaCorreo}")
	String contrasenaCorreo;
	
	@Value("${my.property.copiaOculta2}")
	String copiaOculta;
	
	//--------------------------SERVICIO WEB CLUB----------------------------------
	/**
	 * Metodo que muestra todos los clubs almacenados en la base de datos
	 * @return lista de clubs
	 */
	@GetMapping("/obtenerClub")
    public List<Club> findAll(){
        return clubService.findAll();
    }//fin del metodo
	
	/**
	 * Metodo que muestra solo un club
	 * @param clubId es el id del club que se quiere mostrar, en caso de no encontrarlo genera un RuntimeException
	 * @return El club con el id clubId
	 */
	@GetMapping("/obtenerClub/{clubId}")
	@ResponseBody
    public Club getClub(@PathVariable("clubId") int clubId){
        Club club = clubService.findById(clubId);

        if(club == null) {
            throw new RuntimeException("club id not found -"+clubId);
        }
        return club;
    }//fin del metodo
	
	@GetMapping("/obtenerUsuariosByClub/{clubId}")
	@ResponseBody 
    public List<ClienteDTOO> getUsuariosByClub(@PathVariable("clubId") int clubId){
		List<ClienteDTOO> clientes = clienteService.findClientesByIdClub(clubId);

        return clientes;
    }//fin del metodo
	
	/**
	 * Metodo que añade a la base de datos un nuevo club
	 * @param club es el objeto club que se desea añadir, en caso de contar con el mismo id, actualiza los valores solamente
	 * @return el objeto club que fue almacenado
	 */
	
	@PostMapping("/agregarClub")
    public Club addClub(@RequestBody Club club) {
		club.setFechaCreacion(new Date());
		club.setFechaModificacion(new Date());
		club.setActivo(true);
        clubService.save(club);
        return club;

    }//fin del metodo
	
	/**
	 * Metodo que modifica un club ya existente en la base de datos (el club debe existir sino sera creado uno nuevo)
	 * @param club es el objecto club que se quiere modificar
	 * @return objeto club ya modificado
	 */
	@PutMapping("/modificarClub")
    public Club updateClub(@RequestBody Club club) {
		Club clubs=clubService.findById(club.getIdClub());
		clubs.setNombre(club.getNombre());
		clubs.setActivo(club.isActivo());
		clubs.setFechaCreacion(club.getFechaCreacion());
		clubs.setFechaModificacion(club.getFechaModificacion());
        clubService.save(clubs);
        //este metodo actualizará al usuario enviado
        club.setFechaModificacion(new Date());
        return club;
    }//fin del metodo
	//--------------------------SERVICIO WEB HORARIO LUNES----------------------------------
	
	/**
	 * Muestra todos los dias lunes registrados
	 * @return regresa la lista de objetos lunes
	 */
		@GetMapping("/obtenerHorarioLunes")
	    public List<Lunes> findAllHorarioLunes(){
	        return lunesService.findAll();
	    }//fin del metodo
		
		/**
		 * Muestra solo un dia lunes 
		 * @param horarioId id del dia lunes que se quiere mostrar, en caso de no encontrarse se genera RuntimeException
		 * @return regresa el objeto lunes con id horarioId
		 */
		@GetMapping("/obtenerHorarioLunes/{horarioId}")
		@ResponseBody
	    public Lunes getLunes(@PathVariable("horarioId") int horarioId){
	        Lunes lunes = lunesService.findById(horarioId);

	        if(lunes == null) {
	            throw new RuntimeException("lunes id not found -"+horarioId);
	        }
	        //retornará al usuario con id pasado en la url
	        return lunes;
	    }//fin del metodo
		
		/**
		 * Este metodo añade un nuevo horario para el día lunes
		 * @param lunes objeto lunes que se va a agregar a la base de datos, en caso de tener un id ya registrado solo se actualizan los valroes
		 * @return objeto lunes añadido
		 */
		@PostMapping("/agregarHorarioLunes")
	    public Lunes addLunes(@RequestBody Lunes lunes) {
	        //Este metodo guardará al usuario enviado
			lunes.setFechamodificacion(new Date());
			lunes.setFechacreacion(new Date());
			lunes.setActivo(true);
	        lunesService.save(lunes);

	        return lunes;

	    }//fin del metodo
		
		/**
		 * Metodo que modifica un horario del día lunes ya registrado
		 * @param lunes objeto que se desea modificar, en caso de no existir se crea uno nuevo
		 * @return objecto lunes que fue modificado
		 */
		@PutMapping("/modificarHorarioLunes")
	    public Lunes updateLunes(@RequestBody Lunes lunes) {
			Lunes luness=lunesService.findById(lunes.getIdLunes());
			luness.setHoraentrada(lunes.getHoraentrada());
			luness.setHorasalida(lunes.getHorasalida());
			luness.setActivo(lunes.isActivo());
			luness.setFechacreacion(lunes.getFechacreacion());
			luness.setFechamodificacion(lunes.getFechamodificacion());
	        lunesService.save(luness);
	        //este metodo actualizará al usuario enviado
	        lunes.setFechamodificacion(new Date());
	        return lunes;
	    }// fin del metodo
		//--------------------------SERVICIO WEB HORARIO MARTES----------------------------------
	
		/**
		 * Muestra todos los dias martes registrados
		 * @return regresa la lista de objetos martes
		 */
		@GetMapping("/obtenerHorarioMartes")
	    public List<Martes> findAllHorarioMartes(){
	        return martesService.findAll();
	    }
		/**
		 * Muestra solo un dia martes 
		 * @param horarioId id del dia martes que se quiere mostrar, en caso de no encontrarse se genera RuntimeException
		 * @return regresa el objeto martes con id horarioId
		 */
		@GetMapping("/obtenerHorarioMartes/{horarioId}")
		@ResponseBody
	    public Martes getMartes(@PathVariable("horarioId") int horarioId){
	        Martes martes = martesService.findById(horarioId);

	        if(martes == null) {
	            throw new RuntimeException("martes id not found -"+horarioId);
	        }
	        //retornará al usuario con id pasado en la url
	        return martes;
	    }//Fin del metodo
		
		/**
		 * Este metodo añade un nuevo horario para el día martes
		 * @param martes objeto martes que se va a agregar a la base de datos, en caso de tener un id ya registrado solo se actualizan los valores
		 * @return objeto martes añadido
		 */
		@PostMapping("/agregarHorarioMartes")
	    public Martes addMartes(@RequestBody Martes martes) {
	        //Este metodo guardará al usuario enviado
			martes.setFechamodificacion(new Date());
			martes.setFechacreacion(new Date());
			martes.setActivo(true);
	        martesService.save(martes);

	        return martes;

	    }// FIN DEL METODO
		
		/**
		 * Metodo que modifica un horario del día martes ya registrado
		 * @param martes objeto que se desea modificar, en caso de no existir se crea uno nuevo
		 * @return objeto martes que fue modificado
		 */
		@PutMapping("/modificarHorarioMartes")
	    public Martes updateMartes(@RequestBody Martes martes) {
			Martes martess=martesService.findById(martes.getIdMartes());
			martess.setHoraentrada(martes.getHoraentrada());
			martess.setHorasalida(martes.getHorasalida());
			martess.setActivo(martes.isActivo());
			martess.setFechacreacion(martes.getFechacreacion());
			martess.setFechamodificacion(martes.getFechamodificacion());
	        martesService.save(martess);
	        //este metodo actualizará al usuario enviado
	        martes.setFechamodificacion(new Date());
	        return martes;
	    }//Fin del metodo
		//--------------------------SERVICIO WEB HORARIO MIERCOLES----------------------------------
		/**
		 * Muestra todos los dias miercoles registrados
		 * @return regresa la lista de objetos miercoles
		 */
			@GetMapping("/obtenerHorarioMiercoles")
		    public List<Miercoles> findAllHorarioMiercoles(){
		        return miercolesService.findAll();
		    }// fin del metodo
			/**
			 * Muestra solo un dia miercoles 
			 * @param horarioId id del dia miercoles que se quiere mostrar, en caso de no encontrarse se genera RuntimeException
			 * @return regresa el objeto miercoles con id horarioId
			 */
			@GetMapping("/obtenerHorarioMiercoles/{horarioId}")
			@ResponseBody
		    public Miercoles getMiercoles(@PathVariable("horarioId") int horarioId){
		        Miercoles miercoles = miercolesService.findById(horarioId);

		        if(miercoles == null) {
		            throw new RuntimeException("miercoles id not found -"+horarioId);
		        }
		        //retornará al usuario con id pasado en la url
		        return miercoles;
		    }//Fin del metodo
			
			/**
			 * Este metodo añade un nuevo horario para el día Miercoles
			 * @param miercoles objeto miercoles que se va a agregar a la base de datos, en caso de tener un id ya registrado solo se actualizan los valores
			 * @return objeto miercoles añadido
			 */
			@PostMapping("/agregarHorarioMiercoles")
		    public Miercoles addMiercoles(@RequestBody Miercoles miercoles) {
		        //Este metodo guardará al usuario enviado
				miercoles.setFechamodificacion(new Date());
				miercoles.setFechacreacion(new Date());
				miercoles.setActivo(true);
		        miercolesService.save(miercoles);

		        return miercoles;

		    }//Fin del metodo
			
			/**
			 * Metodo que modifica un horario del día miercoles ya registrado
			 * @param miercoles objeto que se desea modificar, en caso de no existir se crea uno nuevo
			 * @return objeto miercoles que fue modificado
			 */
			@PutMapping("/modificarHorarioMiercoles")
		    public Miercoles updateMiercoles(@RequestBody Miercoles miercoles) {
				Miercoles miercoless=miercolesService.findById(miercoles.getIdMiercoles());
				miercoless.setHoraentrada(miercoles.getHoraentrada());
				miercoless.setHorasalida(miercoles.getHorasalida());
				miercoless.setActivo(miercoles.isActivo());
				miercoless.setFechacreacion(miercoles.getFechacreacion());
				miercoless.setFechamodificacion(miercoles.getFechamodificacion());
		        miercolesService.save(miercoless);
		        //este metodo actualizará al usuario enviado
		        miercoles.setFechamodificacion(new Date());
		        return miercoles;
		    }//Fin del metodo
			
			//--------------------------SERVICIO WEB HORARIO JUEVES----------------------------------
			/**
			 * Muestra todos los dias jueves registrados
			 * @return regresa la lista de objetos jueves
			 */
			@GetMapping("/obtenerHorarioJueves")
		    public List<Jueves> findAllHorarioJueves(){
		        return juevesService.findAll();
		    }// Fin del metodo
			/**
			 * Muestra solo un dia jueves 
			 * @param horarioId id del dia jueves que se quiere mostrar, en caso de no encontrarse se genera RuntimeException
			 * @return regresa el objeto jueves con id horarioId
			 */
			@GetMapping("/obtenerHorarioJueves/{horarioId}")
			@ResponseBody
		    public Jueves getJueves(@PathVariable("horarioId") int horarioId){
		        Jueves jueves = juevesService.findById(horarioId);

		        if(jueves == null) {
		            throw new RuntimeException("jueves id not found -"+horarioId);
		        }
		        //retornará al usuario con id pasado en la url
		        return jueves;
		    }//Fin del metodo
			
			/**
			 * Este metodo añade un nuevo horario para el día jueves
			 * @param jueves objeto jueves que se va a agregar a la base de datos, en caso de tener un id ya registrado solo se actualizan los valores
			 * @return objeto jueves añadido
			 */
			@PostMapping("/agregarHorarioJueves")
		    public Jueves addJueves(@RequestBody Jueves jueves) {
		        //Este metodo guardará al usuario enviado
				jueves.setFechamodificacion(new Date());
				jueves.setFechacreacion(new Date());
				jueves.setActivo(true);
		        juevesService.save(jueves);

		        return jueves;

		    }//fiN DEL METODO
			
			/**
			 * Metodo que modifica un horario del día jueves ya registrado
			 * @param jueves objeto que se desea modificar, en caso de no existir se crea uno nuevo
			 * @return objeto jueves que fue modificado
			 */
			@PutMapping("/modificarHorarioJueves")
		    public Jueves updateJueves(@RequestBody Jueves jueves) {
				Jueves juevess=juevesService.findById(jueves.getIdJueves());
				juevess.setHoraentrada(jueves.getHoraentrada());
				juevess.setHorasalida(jueves.getHorasalida());
				juevess.setActivo(jueves.isActivo());
				juevess.setFechacreacion(jueves.getFechacreacion());
				juevess.setFechamodificacion(jueves.getFechamodificacion());
		        juevesService.save(juevess);
		        //este metodo actualizará al usuario enviado
		        jueves.setFechamodificacion(new Date());
		        return jueves;
		    }//Fin del metodo
//--------------------------SERVICIO WEB HORARIO VIERNES----------------------------------
			/**
			 * Muestra todos los dias viernes registrados
			 * @return regresa la lista de objetos viernes
			 */
			@GetMapping("/obtenerHorarioViernes")
		    public List<Viernes> findAllHorarioViernes(){
		        return viernesService.findAll();
		    }//Fin del metodo
			/**
			 * Muestra solo un dia Viernes 
			 * @param horarioId id del dia viernes que se quiere mostrar, en caso de no encontrarse se genera RuntimeException
			 * @return regresa el objeto viernes con id horarioId
			 */
			@GetMapping("/obtenerHorarioViernes/{horarioId}")
			@ResponseBody
		    public Viernes getViernes(@PathVariable("horarioId") int horarioId){
		        Viernes viernes = viernesService.findById(horarioId);

		        if(viernes == null) {
		            throw new RuntimeException("viernes id not found -"+horarioId);
		        }
		        //retornará al usuario con id pasado en la url
		        return viernes;
		    }//Fin del metodo
			
			/**
			 * Este metodo añade un nuevo horario para el día viernes
			 * @param viernes objeto viernes que se va a agregar a la base de datos, en caso de tener un id ya registrado solo se actualizan los valores
			 * @return objeto viernes añadido
			 */
			@PostMapping("/agregarHorarioViernes")
		    public Viernes addViernes(@RequestBody Viernes viernes) {
		        //Este metodo guardará al usuario enviado
				viernes.setFechamodificacion(new Date());
				viernes.setFechacreacion(new Date());
				viernes.setActivo(true);
		        viernesService.save(viernes);

		        return viernes;

		    }//Fin del metodo
			
			/**
			 * Metodo que modifica un horario del día viernes ya registrado
			 * @param viernes objeto que se desea modificar, en caso de no existir se crea uno nuevo
			 * @return objeto viernes que fue modificado
			 */
			@PutMapping("/modificarHorarioViernes")
		    public Viernes updateViernes(@RequestBody Viernes viernes) {
				Viernes vierness=viernesService.findById(viernes.getIdViernes());
				vierness.setHoraentrada(viernes.getHoraentrada());
				vierness.setHorasalida(viernes.getHorasalida());
				vierness.setActivo(viernes.isActivo());
				vierness.setFechacreacion(viernes.getFechacreacion());
				vierness.setFechamodificacion(viernes.getFechamodificacion());
		        viernesService.save(vierness);
		        //este metodo actualizará al usuario enviado
		        vierness.setFechamodificacion(new Date());
		        return vierness;
		    }//Fin del metodo
//--------------------------SERVICIO WEB HORARIO SABADO----------------------------------
			/**
			 * Muestra todos los dias sabado registrados
			 * @return regresa la lista de objetos sabado
			 */
			@GetMapping("/obtenerHorarioSabado")
		    public List<Sabado> findAllHorarioSabado(){
		        return sabadoService.findAll();
		    }// Fin del metodo
			/**
			 * Muestra solo un dia sabado 
			 * @param horarioId id del dia sabado que se quiere mostrar, en caso de no encontrarse se genera RuntimeException
			 * @return regresa el objeto sabado con id horarioId
			 */
			@GetMapping("/obtenerHorarioSabado/{horarioId}")
			@ResponseBody
		    public Sabado getSabado(@PathVariable("horarioId") int horarioId){
		        Sabado sabado = sabadoService.findById(horarioId);

		        if(sabado == null) {
		            throw new RuntimeException("sabado id not found -"+horarioId);
		        }
		        //retornará al usuario con id pasado en la url
		        return sabado;
		    }//Fin del metodo
			
			/**
			 * Este metodo añade un nuevo horario para el día Sabado
			 * @param sabado objeto sabado que se va a agregar a la base de datos, en caso de tener un id ya registrado solo se actualizan los valores
			 * @return objeto sabado añadido
			 */
			@PostMapping("/agregarHorarioSabado")
		    public Sabado addSabado(@RequestBody Sabado sabado) {
		        //Este metodo guardará al usuario enviado
				sabado.setFechamodificacion(new Date());
				sabado.setFechacreacion(new Date());
				sabado.setActivo(true);
		        sabadoService.save(sabado);

		        return sabado;

		    }//Fin del metodo
			
			/**
			 * Metodo que modifica un horario del día sabado ya registrado
			 * @param sabado objeto que se desea modificar, en caso de no existir se crea uno nuevo
			 * @return objeto sabado que fue modificado
			 */
			@PutMapping("/modificarHorarioSabado")
		    public Sabado updateSabado(@RequestBody Sabado sabado) {
				Sabado sabados=sabadoService.findById(sabado.getIdSabado());
				sabados.setHoraentrada(sabado.getHoraentrada());
				sabados.setHorasalida(sabado.getHorasalida());
				sabados.setActivo(sabado.isActivo());
				sabados.setFechacreacion(sabado.getFechacreacion());
				sabados.setFechamodificacion(sabado.getFechamodificacion());
		        sabadoService.save(sabados);
		        //este metodo actualizará al usuario enviado
		        sabados.setFechamodificacion(new Date());
		        return sabados;
		    }//FIn del metodo
			
//--------------------------SERVICIO WEB HORARIO DOMINGO----------------------------------
			/**
			 * Muestra todos los dias Domingo registrados
			 * @return regresa la lista de objetos domingo
			 */
			@GetMapping("/obtenerHorarioDomingo")
		    public List<Domingo> findAllHorarioDomingo(){
		        return domingoService.findAll();
		    }//Fin del metodo
			/**
			 * Muestra solo un dia Domingo 
			 * @param horarioId id del dia domingo que se quiere mostrar, en caso de no encontrarse se genera RuntimeException
			 * @return regresa el objeto domingo con id horarioId
			 */
			@GetMapping("/obtenerHorarioDomingo/{horarioId}")
			@ResponseBody
		    public Domingo getDomingo(@PathVariable("horarioId") int horarioId){
		        Domingo domingo = domingoService.findById(horarioId);

		        if(domingo == null) {
		            throw new RuntimeException("domingo id not found -"+horarioId);
		        }
		        //retornará al usuario con id pasado en la url
		        return domingo;
		    }//Fin del metodo
			
			/**
			 * Este metodo añade un nuevo horario para el día domingo
			 * @param domingo objeto domingo que se va a agregar a la base de datos, en caso de tener un id ya registrado solo se actualizan los valores
			 * @return objeto domingo añadido
			 */
			@PostMapping("/agregarHorarioDomingo")
		    public Domingo addDomingo(@RequestBody Domingo domingo) {
		        //Este metodo guardará al usuario enviado
				domingo.setFechamodificacion(new Date());
				domingo.setFechacreacion(new Date());
				domingo.setActivo(true);
		        domingoService.save(domingo);

		        return domingo;

		    }//Fin del metodo
			
			/**
			 * Metodo que modifica un horario del día domingo ya registrado
			 * @param domingo objeto que se desea modificar, en caso de no existir se crea uno nuevo
			 * @return objeto domingo que fue modificado
			 */
			@PutMapping("/modificarHorarioDomingo")
		    public Domingo updateDomingo(@RequestBody Domingo domingo) {
				Domingo domingos=domingoService.findById(domingo.getIdDomingo());
				domingos.setHoraentrada(domingo.getHoraentrada());
				domingos.setHorasalida(domingo.getHorasalida());
				domingos.setActivo(domingo.isActivo());
				domingos.setFechacreacion(domingo.getFechacreacion());
				domingos.setFechamodificacion(domingo.getFechamodificacion());
		        domingoService.save(domingos);
		        //este metodo actualizará al usuario enviado
		        domingos.setFechamodificacion(new Date());
		        return domingos;
		    }//fin del metodo
			
//--------------------------SERVICIO WEB HORARIO OTRO CLUB----------------------------------
			/**
			 * Muestra todos los Horario otro club registrados
			 * @return regresa la lista de objetos HorarioOtroClub
			 */
			@GetMapping("/obtenerHorarioOtroClub")
		    public List<HorarioOtroClub> findAllHorarioOtroClub(){
		        return horarioOtroClubService.findAll();
		    }// Fin del metodo
			
			/**
			 * Este metodo añade un nuevo horario otro club
			 * @param horarioOtroClub objeto de tipo HorarioOtroClub que se va a agregar a la base de datos, en caso de tener un id ya registrado solo se actualizan los valores
			 * @return objeto HorarioOtroClub añadido
			 */
			@PostMapping("/agregarHorarioOtroClub")
		    public HorarioOtroClub addHorarioOtroClub(@RequestBody HorarioOtroClub horarioOtroClub) {
		        //Este metodo guardará al usuario enviado
				horarioOtroClub.setFechaCreacion(new Date());
				horarioOtroClub.setFechaModificacion(new Date());
				horarioOtroClub.setActivo(true);
				horarioOtroClubService.save(horarioOtroClub);

		        return horarioOtroClub;

		    }//Fin del metodo
			
			/**
			 * Metodo que modifica un horario otro club ya registrado
			 * @param horarioOtroClub objeto que se desea modificar, en caso de no existir se crea uno nuevo
			 * @return objeto horarioOtroClub que fue modificado
			 */
			@PutMapping("/modificarHorarioOtroClub")
		    public HorarioOtroClub updatehorarioOtroClub(@RequestBody HorarioOtroClub horarioOtroClub) {
				horarioOtroClubService.save(horarioOtroClub);
		        //este metodo actualizará al usuario enviado
		        horarioOtroClub.setFechaModificacion(new Date());
		        return horarioOtroClub;
		    }//fin del metodo
			
//--------------------------SERVICIO WEB HORARIO ----------------------------------
			/**
			 * Muestra todos los Horarios registrados
			 * @return regresa la lista de objetos Horario
			 */
			@GetMapping("/obtenerHorario")
		    public List<Horario> findAllHorario(){
		        return horarioService.findAll();
		    }//Fin del metodo
			/**
			 * Muestra solo un Horario 
			 * @param horarioId id del horario que se quiere mostrar, en caso de no encontrarse se genera RuntimeException
			 * @return regresa el objeto horario con id horarioId
			 */
			@GetMapping("/obtenerHorario/{horarioId}")
			@ResponseBody
		    public Horario getHorario(@PathVariable("horarioId") int horarioId){
		        Horario horario = horarioService.findById(horarioId);
		        if(horario == null) {
		            throw new RuntimeException("horario id not found -"+horarioId);
		        }
		        //retornará al usuario con id pasado en la url
		        return horario;
		    }//Fin del metodo
			
			/**
			 * Este metodo añade un nuevo horario
			 * @param horario objeto de tipo Horario que se va a agregar a la base de datos, en caso de tener un id ya registrado solo se actualizan los valores
			 * @return objeto Horario añadido
			 */
			@PostMapping("/agregarHorario")
		    public Horario addHorario(@RequestBody Horario horario) {
		        //Este metodo guardará al usuario enviado
				horario.setFechaCreacion(new Date());
				horario.setFechaModificacion(new Date());
				horario.setActivo(true);
				horario.getIdLunes().setFechacreacion(new Date());
				horario.getIdLunes().setFechamodificacion(new Date());
				horario.getIdLunes().setActivo(true);
				
				horario.getIdMartes().setFechacreacion(new Date());
				horario.getIdMartes().setFechamodificacion(new Date());
				horario.getIdMartes().setActivo(true);
				
				horario.getIdMiercoles().setFechacreacion(new Date());
				horario.getIdMiercoles().setFechamodificacion(new Date());
				horario.getIdMiercoles().setActivo(true);
				
				horario.getIdJueves().setFechacreacion(new Date());
				horario.getIdJueves().setFechamodificacion(new Date());
				horario.getIdJueves().setActivo(true);
				
				horario.getIdViernes().setFechacreacion(new Date());
				horario.getIdViernes().setFechamodificacion(new Date());
				horario.getIdViernes().setActivo(true);
				
				horario.getIdSabado().setFechacreacion(new Date());
				horario.getIdSabado().setFechamodificacion(new Date());
				horario.getIdSabado().setActivo(true);
				
				horario.getIdDomingo().setFechacreacion(new Date());
				horario.getIdDomingo().setFechamodificacion(new Date());
				horario.getIdDomingo().setActivo(true);
		        horarioService.save(horario);

		        return horario;

		    }//Fin del metodo
			/**
			 * Metodo que modifica un horario ya registrado
			 * @param horario objeto que se desea modificar, en caso de no existir se crea uno nuevo
			 * @return objeto horario que fue modificado
			 */
			@PutMapping("/modificarHorario")
		    public Horario updateHorario(@RequestBody Horario horario) {
		        horarioService.save(horario);
		        //este metodo actualizará al usuario enviado
		        horario.setFechaModificacion(new Date());
		        return horario;
		    }//fin del metodo
			
//--------------------------SERVICIO WEB HORARIO ACCESO----------------------------------
			/**
			 * Muestra todos los Horario acceso registrados
			 * @return regresa la lista de objetos HorarioAcceso
			 */
			@GetMapping("/obtenerHorarioAcceso")
		    public List<HorarioAcceso> findAllHorarioAcceso(){
		        return horarioAccesoService.findAll();
		    }//Fin del metodo
			
			/**
			 * Muestra solo un horario Acceso 
			 * @param horarioId id del horario acceso que se quiere mostrar, en caso de no encontrarse se genera RuntimeException
			 * @return regresa el objeto HorarioAcceso con id horarioId
			 */
			@GetMapping("/obtenerHorarioAcceso/{horarioId}")
			@ResponseBody
		    public HorarioAcceso getHorarioAcceso(@PathVariable("horarioId") int horarioId){
		        HorarioAcceso horarioAcceso = horarioAccesoService.findById(horarioId);
		        if(horarioAcceso == null) {
		            throw new RuntimeException("horario Acceso id not found -"+horarioId);
		        }
		        //retornará al usuario con id pasado en la url
		        return horarioAcceso;
		    }//FIn del metodo
			
			/**
			 * Este metodo añade un nuevo horario acceso
			 * @param horarioAcceso objeto de tipo HorarioAcceso que se va a agregar a la base de datos, en caso de tener un id ya registrado solo se actualizan los valores
			 * @return objeto HorarioAcceso añadido
			 */
			@PostMapping("/agregarHorarioAcceso")
		    public HorarioAcceso addHorarioAcceso(@RequestBody HorarioAcceso horarioAcceso) {
		        //Este metodo guardará al usuario enviado
				horarioAcceso.setFechaCreacion(new Date());
				horarioAcceso.setFechaModificacion(new Date());
				horarioAcceso.setActivo(true);
		        horarioAccesoService.save(horarioAcceso);

		        return horarioAcceso;

		    }//Fin del metodo
			
			/**
			 * Metodo que modifica un horario acceso ya registrado
			 * @param horarioAcceso objeto que se desea modificar, en caso de no existir se crea uno nuevo
			 * @return objeto HorarioAcceso que fue modificado
			 */
			@PutMapping("/modificarHorarioAcceso")
		    public HorarioAcceso updateHorarioAcceso(@RequestBody HorarioAcceso horarioAcceso) {
		        horarioAccesoService.save(horarioAcceso);
		        //este metodo actualizará al usuario enviado
		        horarioAcceso.setFechaModificacion(new Date());
		        return horarioAcceso;
		    }//Fin de metodo
//--------------------------SERVICIO WEB MENSAJES----------------------------------
			/**
			 * Muestra todos los Mensajes registrados
			 * @return regresa la lista de objetos Mensajes
			 */
			@GetMapping("/obtenerMensajes")
		    public List<Mensajes> findAllMensajes(){
		        return mensajesService.findAll();
		    }//Fin del Metodo
			
			/**
			 * Muestra solo un mensaje
			 * @param horarioId id del mensaje que se quiere mostrar, en caso de no encontrarse se genera RuntimeException
			 * @return regresa el objeto Mensaje con id horarioId
			 */
			@GetMapping("/obtenerMensajes/{horarioId}")
			@ResponseBody
		    public Mensajes getMensajes(@PathVariable("horarioId") int horarioId){
		        Mensajes mensajes = mensajesService.findById(horarioId);
		        if(mensajes == null) {
		            throw new RuntimeException("mensajes id not found -"+horarioId);
		        }
		        //retornará al usuario con id pasado en la url
		        return mensajes;
		    }//Fin del metodo
			
			/**
			 * Este metodo añade un nuevo mensaje
			 * @param mensajes objeto de tipo Mensajes que se va a agregar a la base de datos, en caso de tener un id ya registrado solo se actualizan los valores
			 * @return objeto Mensajes añadido
			 */
			@PostMapping("/agregarMensajes")
		    public Mensajes addMensajes(@RequestBody Mensajes mensajes) {
		        //Este metodo guardará al usuario enviado
				mensajes.setFechaCreacion(new Date());
				mensajes.setFechaModificacion(new Date());
				mensajes.setActivo(true);
				mensajesService.save(mensajes);

		        return mensajes;

		    }//Fin del metodo
			/**
			 * Metodo que modifica un mensajes ya registrado
			 * @param mensajes objeto que se desea modificar, en caso de no existir se crea uno nuevo
			 * @return objeto mensajes que fue modificado
			 */
			@PutMapping("/modificarMensajes")
		    public Mensajes updateMensajes(@RequestBody Mensajes mensajes) {
				mensajesService.save(mensajes);
		        //este metodo actualizará al usuario enviado
		        mensajes.setFechaModificacion(new Date());
		        return mensajes;
		    }//Fin del metodo
//--------------------------SERVICIO WEB ESTATUS COBRANZA----------------------------------
			/**
			 * Muestra todos los Estatus cobranza registrados
			 * @return regresa la lista de objetos EstatusCobranza
			 */
			@GetMapping("/obtenerEstatusCobranza")
		    public List<EstatusCobranza> findAllEstatusCobranza(){
		        return estatusCobranzaService.findAll();
		    }//Fin del Metodo
			/**
			 * Muestra solo un estatus cobranza 
			 * @param horarioId id del estatus cobranza que se quiere mostrar, en caso de no encontrarse se genera RuntimeException
			 * @return regresa el objeto EstatusCobranza con id horarioId
			 */
			@GetMapping("/obtenerEstatusCobranza/{horarioId}")
			@ResponseBody
		    public EstatusCobranza getEstatusCobranza(@PathVariable("horarioId") int horarioId){
				EstatusCobranza estatusCobranza = estatusCobranzaService.findById(horarioId);
		        if(estatusCobranza == null) {
		            throw new RuntimeException("Estatus Cobranza id not found -"+horarioId);
		        }
		        //retornará al usuario con id pasado en la url
		        return estatusCobranza;
		    }//Fin del metodo
			
			/**
			 * Este metodo añade un nuevo estatus cobranza
			 * @param estatusCobranza objeto de tipo EstatusCobranza que se va a agregar a la base de datos, en caso de tener un id ya registrado solo se actualizan los valores
			 * @return objeto EstatusCobranza añadido
			 */
			@PostMapping("/agregarEstatusCobranza")
		    public EstatusCobranza addEstatusCobranza(@RequestBody EstatusCobranza estatusCobranza) {
		        //Este metodo guardará al usuario enviado
				estatusCobranza.setFechaCreacion(new Date());
				estatusCobranza.setFechaModificacion(new Date());
				estatusCobranza.setActivo(true);
				estatusCobranzaService.save(estatusCobranza);

		        return estatusCobranza;

		    }//Fin del metodo
			
			/**
			 * Metodo que modifica un estatus cobranza ya registrado
			 * @param estatusCobranza objeto que se desea modificar, en caso de no existir se crea uno nuevo
			 * @return objeto EstatusCobranza que fue modificado
			 */
			@PutMapping("/modificarEstatusCobranza")
		    public EstatusCobranza updateEstatusCobranza(@RequestBody EstatusCobranza estatusCobranza) {
				estatusCobranzaService.save(estatusCobranza);
		        //este metodo actualizará al usuario enviado
				estatusCobranza.setFechaModificacion(new Date());
		        return estatusCobranza;
		    }//Fin del metodo
//--------------------------SERVICIO WEB ESTATUS MEMBRESIA----------------------------------
			/**
			 * Muestra todos los Estatus Membresia registrados
			 * @return regresa la lista de objetos EstatusMembresia
			 */
			@GetMapping("/obtenerEstatusMembresia")
		    public List<EstatusMembresia> findAllEstatusMembresia(){
		        return estatusMembresiaService.findAll();
		    }//Fin del Metodo
			
			/**
			 * Muestra solo un Estatus membresia
			 * @param horarioId id del estatus membresia que se quiere mostrar, en caso de no encontrarse se genera RuntimeException
			 * @return regresa el objeto estatus membresia con id horarioId
			 */
			@GetMapping("/obtenerEstatusMembresia/{horarioId}")
			@ResponseBody
		    public EstatusMembresia getEstatusMembresia(@PathVariable("horarioId") int horarioId){
				EstatusMembresia estatusMembresia = estatusMembresiaService.findById(horarioId);
		        if(estatusMembresia == null) {
		            throw new RuntimeException("Estatus Membresia id not found -"+horarioId);
		        }
		        //retornará al usuario con id pasado en la url
		        return estatusMembresia;
		    }//Fin del metodo
			
			/**
			 * Este metodo añade un nuevo estatus Membresia
			 * @param estatusMembresia objeto de tipo EstatusMembresia que se va a agregar a la base de datos, en caso de tener un id ya registrado solo se actualizan los valores
			 * @return objeto EstatusMembresia añadido
			 */
			@PostMapping("/agregarEstatusMembresia")
		    public EstatusMembresia addEstatusMembresia(@RequestBody EstatusMembresia estatusMembresia) {
		        //Este metodo guardará al usuario enviado
				estatusMembresia.setFechaCreacion(new Date());
				estatusMembresia.setFechaModificacion(new Date());
				estatusMembresia.setActivo(true);
				estatusMembresiaService.save(estatusMembresia);

		        return estatusMembresia;

		    }//Fin del metodo
			
			/**
			 * Metodo que modifica un Estatus membresia ya registrado
			 * @param estatusMembresia objeto que se desea modificar, en caso de no existir se crea uno nuevo
			 * @return objeto EstatusMembresia que fue modificado
			 */
			@PutMapping("/modificarEstatusMembresia")
		    public EstatusMembresia updateEstatusMembresia(@RequestBody EstatusMembresia estatusMembresia) {
				estatusMembresiaService.save(estatusMembresia);
		        //este metodo actualizará al usuario enviado
				estatusMembresia.setFechaModificacion(new Date());
		        return estatusMembresia;
		    }//FIn del metodo
//--------------------------SERVICIO WEB TIPO CLIENTE----------------------------------
			/**
			 * Muestra todos los Tipos Clientes registrados
			 * @return regresa la lista de objetos TipoCliente
			 */
			@GetMapping("/obtenerTipoCliente")
		    public List<TipoCliente> findAllTipoCliente(){
		        return tipoClienteService.findAll();
		    }//Fin del metodo
			
			/**
			 * Muestra solo un tipo cliente 
			 * @param horarioId id del tipo cliente que se quiere mostrar, en caso de no encontrarse se genera RuntimeException
			 * @return regresa el objeto TipoCliente con id horarioId
			 */
			@GetMapping("/obtenerTipoCliente/{horarioId}")
			@ResponseBody
		    public TipoCliente getTipoCliente(@PathVariable("horarioId") int horarioId){
				TipoCliente tipoCliente = tipoClienteService.findById(horarioId);
		        if(tipoCliente == null) {
		            throw new RuntimeException("Tipo Cliente id not found -"+horarioId);
		        }
		        //retornará al usuario con id pasado en la url
		        return tipoCliente;
		    }//Fin del metodo
			
			/**
			 * Este metodo añade un nuevo TipoCliente
			 * @param tipoCliente objeto de TipoCliente que se va a agregar a la base de datos, en caso de tener un id ya registrado solo se actualizan los valores
			 * @return objeto TipoCliente añadido
			 */
			@PostMapping("/agregarTipoCliente")
		    public TipoCliente addTipoCliente(@RequestBody TipoCliente tipoCliente) {
		        //Este metodo guardará al usuario enviado
				tipoCliente.setFechaCreacion(new Date());
				tipoCliente.setFechaModificacion(new Date());
				tipoCliente.setActivo(true);
				tipoClienteService.save(tipoCliente);

		        return tipoCliente;

		    }//Fin del metodo
			/**
			 * Metodo que modifica un tipo cliente ya registrado
			 * @param TipoCliente objeto que se desea modificar, en caso de no existir se crea uno nuevo
			 * @return objeto TipoCliente que fue modificado
			 */
			@PutMapping("/modificarTipoCliente")
		    public TipoCliente updateTipoCliente(@RequestBody TipoCliente tipoCliente) {
				tipoClienteService.save(tipoCliente);
		        //este metodo actualizará al usuario enviado
				tipoCliente.setFechaModificacion(new Date());
		        return tipoCliente;
		    }//FIn del metodo
			
			//--------------------------SERVICIO WEB CURSO DETALLE----------------------------------
			/**
			 * Muestra todos los detalles de curso registrados
			 * @return regresa la lista de objetos CursoDetalle
			 */
			@GetMapping("/obtenerCursoDetalle")
			public List<CursoDetalle> findAllCursoDetalle()
			{
				return cursoDetalleService.findAll();
			}//Fin del metodo
			/**
			 * Muestra solo un curso detalle
			 * @param cursoDetalleid id del curso detalle que se quiere mostrar, en caso de no encontrarse se genera RuntimeException
			 * @return regresa el objeto CursoDetalle con id cursoDetalleid
			 */
			@GetMapping("/obtenerCursoDetalle/{cursoDetalleid}")
			@ResponseBody
			public CursoDetalle getCursoDetalle(@PathVariable("cursoDetalleid") int cursoDetalleid)
			{
				CursoDetalle cursoDetalle = cursoDetalleService.findById(cursoDetalleid);
				if(cursoDetalle == null)
				{
					throw new RuntimeException("cursoDetalle id not found -"+cursoDetalleid);
				}
				return cursoDetalle;
			}//Fin del metodo
			
			/**
			 * Este metodo añade un nuevo curso detalle
			 * @param cursoDetalle objeto de CursoDetalle que se va a agregar a la base de datos, en caso de tener un id ya registrado solo se actualizan los valores
			 * @return objeto CursoDetalle añadido
			 */
			@PostMapping("/agregarCursoDetalle")
			public CursoDetalle addCursoDetalle(@RequestBody CursoDetalle cursoDetalle)
			{
				cursoDetalleService.save(cursoDetalle);
				return cursoDetalle;
			}//Fin del metodo
			
			/**
			 * Metodo que modifica un curso detalle ya registrado
			 * @param cursoDetalle objeto que se desea modificar, en caso de no existir se crea uno nuevo
			 * @return objeto CursoDetalle que fue modificado
			 */
			@PutMapping("/modificarCursoDetalle")
		    public CursoDetalle updateCursoDetalle(@RequestBody CursoDetalle cursoDetalle) 
			{
				CursoDetalle cursosDetalles = cursoDetalleService.findById(cursoDetalle.getIdCursoDetalle());
				cursosDetalles.setPeriodo(cursoDetalle.getPeriodo());
				cursosDetalles.setCursoTipo(cursoDetalle.getCursoTipo());
				cursosDetalles.setArea(cursoDetalle.getArea());
				cursosDetalles.setInstalacion(cursoDetalle.getInstalacion());
				cursosDetalles.setUsadoPor(cursoDetalle.getUsadoPor());
				cursosDetalles.setProfesor(cursoDetalle.getProfesor());
				cursosDetalles.setHorarioInicio(cursoDetalle.getHorarioInicio());
				cursosDetalles.setEdadMinima(cursoDetalle.getEdadMinima());
				cursosDetalles.setEdadMaxima(cursoDetalle.getEdadMaxima());
				cursosDetalles.setIdCursoHorario(cursoDetalle.getIdCursoHorario());
				cursosDetalles.setCursoTipo(cursoDetalle.getCursoTipo());
				cursosDetalles.setActivo(true);
				cursosDetalles.setFechaCreacion(new Date());
				cursosDetalles.setFechaModificacion(cursoDetalle.getFechaModificacion());
				cursoDetalleService.save(cursosDetalles);
				return cursoDetalle;
			}//Fin del metodo
			
			//--------------------------SERVICIO WEB CURSO HORARIO----------------------------------
			/**
			 * Muestra todos los cursos horarios registrados
			 * @return regresa la lista de objetos CursoHorario
			 */
			@GetMapping("/obtenerCursoHorario")
			public List<CursoHorario> findAllCursoHorario()
			{
				return cursoHorarioService.findAll();
			}//Fin del metodo
			
			/**
			 * Muestra solo un curso Horario
			 * @param cursoHorarioid id del curso horario que se quiere mostrar, en caso de no encontrarse se genera RuntimeException
			 * @return regresa el objeto CursoHorario con id cursoHorarioid
			 */
			@GetMapping("/obtenerCursoHorario/{cursoHorarioid}")
			@ResponseBody
			public CursoHorario getCursoHorario(@PathVariable("cursoHorarioid") int cursoHorarioid)
			{
				CursoHorario cursoHorario = cursoHorarioService.findById(cursoHorarioid);
				if(cursoHorario == null)
				{
					throw new RuntimeException("cursoDetalle id not found -"+cursoHorarioid);
				}
				return cursoHorario;
			}//Fin del metodo
			
			/**
			 * Este metodo añade un nuevo curso Horario
			 * @param cursoHorario objeto de tipo CursoHorario que se va a agregar a la base de datos, en caso de tener un id ya registrado solo se actualizan los valores
			 * @return objeto CursoHorario añadido
			 */
			@PostMapping("/agregarCursoHorario")
			public CursoHorario addCursoHorario(@RequestBody CursoHorario cursoHorario)
			{
				cursoHorarioService.save(cursoHorario);
				return cursoHorario;
			}//Fin del metodo
			
			/**
			 * Metodo que modifica un Curso Horario ya registrado
			 * @param cursoHorario objeto que se desea modificar, en caso de no existir se crea uno nuevo
			 * @return objeto CursoHorario que fue modificado
			 */
			@PutMapping("/modificarCursoHorario")
		    public CursoHorario updateCursoHorario(@RequestBody CursoHorario cursoHorario) 
			{
				CursoHorario cursosHorarios = cursoHorarioService.findById(cursoHorario.getIdCurso());
				cursosHorarios.setNombreCurso(cursoHorario.getNombreCurso());
				cursosHorarios.setLunesHoraEntrada(cursoHorario.getLunesHoraEntrada());		
				cursosHorarios.setLunesHoraSalida(cursoHorario.getLunesHoraSalida());
				cursosHorarios.setMartesHoraEntrada(cursoHorario.getMartesHoraEntrada());
				cursosHorarios.setMartesHoraSalida(cursoHorario.getMartesHoraSalida());
				cursosHorarios.setMiercolesHoraEntrada(cursoHorario.getMiercolesHoraEntrada());
				cursosHorarios.setMiercolesHoraSalida(cursoHorario.getMiercolesHoraSalida());
				cursosHorarios.setJuevesHoraEntrada(cursoHorario.getJuevesHoraEntrada());
				cursosHorarios.setJuevesHoraSalida(cursoHorario.getJuevesHoraSalida());
				cursosHorarios.setViernesHoraEntrada(cursoHorario.getViernesHoraEntrada());
				cursosHorarios.setViernesHoraSalida(cursoHorario.getViernesHoraSalida());
				cursosHorarios.setSabadoHoraEntrada(cursoHorario.getSabadoHoraEntrada());
				cursosHorarios.setSabadoHoraSalida(cursoHorario.getSabadoHoraSalida());
				cursosHorarios.setDomingoHoraEntrada(cursoHorario.getDomingoHoraEntrada());
				cursosHorarios.setDomingoHoraSalida(cursoHorario.getDomingoHoraSalida());
				cursosHorarios.setActivo(true);
				cursosHorarios.setFechaCreacion(new Date());
				cursosHorarios.setFechaModificacion(cursoHorario.getFechaModificacion());
				cursoHorarioService.save(cursosHorarios);
				return cursoHorario;
			}//FIn del metodo
			//--------------------------SERVICIO WEB ORDEN VENTA----------------------------------
			/**
			 * Muestra todos los orden de venta registrados
			 * @return regresa la lista de objetos OrdenVenta
			 */
			@GetMapping("/obtenerOrdenVenta")
			public List<OrdenVenta> findAllOrdenVenta()
			{
				return ordenVentaService.findAll();
			}//Fin del metodo
			
			/**
			 * Muestra solo un orden venta
			 * @param ordenVentaid id del orden venta que se quiere mostrar, en caso de no encontrarse se genera RuntimeException
			 * @return regresa el objeto OrdenVenta con id ordenVentaid
			 */
			@GetMapping("/obtenerOrdenVenta/{ordenVentaid}")
			@ResponseBody
			public OrdenVenta getOrdenVenta(@PathVariable("ordenVentaid") int ordenVentaid)
			{
				OrdenVenta ordenVenta = ordenVentaService.findById(ordenVentaid);
				if(ordenVenta == null)
				{
					throw new RuntimeException("ordenVenta id not found -"+ordenVentaid);
				}
				return ordenVenta;
			}//Fin del metodo
			
			/**
			 * Este metodo añade un nuevo orden venta
			 * @param ordenVenta objeto de tipo OrdenVenta que se va a agregar a la base de datos, en caso de tener un id ya registrado solo se actualizan los valores
			 * @return objeto Ordenventa añadido
			 */
			@PostMapping("/agregarOrdenVenta")
			public OrdenVenta addOrdenVenta(@RequestBody OrdenVenta ordenVenta)
			{
				ordenVentaService.save(ordenVenta);
				return ordenVenta;
			}//Fin del metodo
			
			/**
			 * Metodo que modifica un Orden venta ya registrado
			 * @param ordenVenta objeto que se desea modificar, en caso de no existir se crea uno nuevo
			 * @return objeto OrdenVenta que fue modificado
			 */
			@PutMapping("/modificarOrdenVenta")
		    public OrdenVenta updateOrdenVenta(@RequestBody OrdenVenta ordenVenta) 
			{
				OrdenVenta ordenesVentas = ordenVentaService.findById(ordenVenta.getIdOV());
				ordenesVentas.setIdCliente(ordenesVentas.getIdCliente());
				ordenesVentas.setIdVenta(ordenesVentas.getIdVenta());
				ordenesVentas.setCantidad(ordenesVentas.getCantidad());
				ordenesVentas.setIdProductoServicio(ordenesVentas.getIdProductoServicio());
				ordenesVentas.setObservaciones(ordenesVentas.getObservaciones());
				ordenesVentas.setDescuentoPorCiento(ordenesVentas.getDescuentoPorCiento());
				ordenesVentas.setFechaInicio(ordenesVentas.getFechaInicio());
				ordenesVentas.setFechaFinal(ordenesVentas.getFechaFinal());
				ordenesVentas.setCobroProporcional(ordenesVentas.getCobroProporcional());
				ordenesVentas.setActivo(true);
				ordenesVentas.setFechaCreacion(new Date());
				ordenesVentas.setFechaModificacion(ordenesVentas.getFechaModificacion());
				ordenVentaService.save(ordenVenta);
				return ordenVenta;
			}//Fin del metodo

			//--------------------------SERVICIO WEB PAGO----------------------------------
			/**
			 * Muestra todos los Pagos registrados
			 * @return regresa la lista de objetos Pago
			 */
			@GetMapping("/obtenerPago")
			public List<Pago> findAllPago()
			{
				return pagoService.findAll();
			}//Fin del metodo

			/**
			 * Muestra solo un orden pago
			 * @param ordenPagoid id del orden pago que se quiere mostrar, en caso de no encontrarse se genera RuntimeException
			 * @return regresa el objeto OrdenPago con id ordenPagoid
			 */
			@GetMapping("/obtenerPago/{ordenPagoid}")
			@ResponseBody
			public Pago getPago(@PathVariable("pagoid") int pagoid)
			{
				Pago pago = pagoService.findById(pagoid);
				if(pago == null)
				{
					throw new RuntimeException("pago id not found -"+pagoid);
				}
				return pago;
			}//Fin del metodo

			/**
			 * Este metodo añade un nuevo pago
			 * @param pago objeto de tipo Pago que se va a agregar a la base de datos, en caso de tener un id ya registrado solo se actualizan los valores
			 * @return objeto Pago añadido
			 */
			@PostMapping("/agregarPago")
			public Pago addPago(@RequestBody Pago pago)
			{
				pagoService.save(pago);
				return pago;
			}//Fin del metodo
			
			/**
			 * Metodo que modifica un Pago ya registrado
			 * @param pago objeto que se desea modificar, en caso de no existir se crea uno nuevo
			 * @return objeto Pago que fue modificado
			 */
			@PutMapping("/modificarPago")
		    public Pago updatePago(@RequestBody Pago pago) 
			{
				Pago pagos = pagoService.findById(pago.getIdPago());
				pagos.setNoPedido(pagos.getNoPedido());
				pagos.setMonto(pagos.getMonto());
				pagos.setNoTarjeta(pagos.getNoTarjeta());
				pagos.setFolioInterbancario(pagos.getFolioInterbancario());
				pagos.setNoAutorizacion(pagos.getNoAutorizacion());
				pagos.setFechaPago(pagos.getFechaPago());
				pagos.setHoraPago(pagos.getHoraPago());
				pagos.setTitularCuenta(pagos.getTitularCuenta());
				pagos.setActivo(true);
				pagos.setFechaCreacion(new Date());
				pagos.setFechaModificacion(pagos.getFechaModificacion());
				pagoService.save(pago);
				return pago;
			}//Fin del metodo
			
			//--------------------------SERVICIO WEB PASE----------------------------------
			/**
			 * Muestra todos los pases registrados
			 * @return regresa la lista de objetos Pase
			 */
			@GetMapping("/obtenerPase")
			public List<Pase> findAllPase()
			{
				return paseService.findAll();
			}//Fin del Metodo

			/**
			 * Muestra solo un pase
			 * @param ordenPaseid id del pase que se quiere mostrar, en caso de no encontrarse se genera RuntimeException
			 * @return regresa el objeto Pase con id ordenPaseid
			 */
			@GetMapping("/obtenerPase/{paseid}")
			@ResponseBody
			public String getPase(@PathVariable("paseid") int paseid)
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
			
			/**
			 * Este metodo añade un nuevo pase
			 * @param pase objeto de tipo Pase que se va a agregar a la base de datos, en caso de tener un id ya registrado solo se actualizan los valores
			 * @return objeto Pase añadido
			 */
			@PostMapping("/agregarPase")
			public Pase addPase(@RequestBody Pase pase)
			{
				paseService.save(pase);
				return pase;
			}//Fin del metodo
			
			/**
			 * Metodo que modifica un Pase ya registrado
			 * @param pase objeto que se desea modificar, en caso de no existir se crea uno nuevo
			 * @return objeto Pase que fue modificado
			 */
			@PutMapping("/modificarPase")
		    public Pase updatePase(@RequestBody Pase pase) 
			{
				Pase pases = paseService.findById(pase.getIdPase());
				pases.setIdProdServ(pases.getIdProdServ());
				pases.setFechaCaptura(pases.getFechaCaptura());
				pases.setConcepto(pases.getConcepto());
				pases.setVentaDetalle(pases.getVentaDetalle());
				pases.setActivo(true);
				pases.setFechaCreacion(new Date());
				pases.setFechaModificacion(pases.getFechaModificacion());
				paseService.save(pase);
				return pase;
			}//Fin del metodo

			//--------------------------SERVICIO WEB PEDIDO----------------------------------
			/**
			 * Muestra todos los pedidos registrados
			 * @return regresa la lista de objetos Pedido
			 */
			@GetMapping("/obtenerPedido")
			public List<Pedido> findAllPedido()
			{
				return pedidoService.findAll();
			}//Fin del metodo
			
			/**
			 * Muestra solo un pedido
			 * @param ordenPedidoid id del pedido que se quiere mostrar, en caso de no encontrarse se genera RuntimeException
			 * @return regresa el objeto Pedido con id ordenPedidoid
			 */
			@GetMapping("/obtenerPedidoId/{ordenPedidoid}")
			@ResponseBody
			public String getPedidoId(@PathVariable("ordenPedidoid") int pedidoid)
			{
				try {
					String body2 = "{\n"
			    			+ "\"IdCliente\":"+pedidoid+",\n"
			    			+ "\"Token\":\"77D5BDD4-1FEE-4A47-86A0-1E7D19EE1C74\"\n"
			    			+ "}";
			    	configuracion o = configuracionService.findByServiceName("getPedido").get(); 
			    	JSONObject json=new JSONObject(e.conectaApiClubPOST(body2,o.getEndpointAlpha()));
			    	JSONObject json2=this.convertirPedido(json);
			    	return json2.toString();
				}catch(JSONException e) {
					
				}
				return null;
			}//Fin del metodo
			
			

			/**
			 * Este metodo añade un nuevo pedido
			 * @param pedido objeto de tipo Pedido que se va a agregar a la base de datos, en caso de tener un id ya registrado solo se actualizan los valores
			 * @return objeto Pedido añadido
			 */
			@PostMapping("/agregarPedido")
			public Pedido addPedido(@RequestBody Pedido pedido)
			{
				pedido.setActivo(true);
				pedido.setFechaCreacion(new Date());
				pedido.setFechaModificacion(new Date());
				
				pedidoService.save(pedido);
				return pedido;
			}//Fin del metodo
			
			/**
			 * Metodo que modifica un pedido ya registrado
			 * @param pedido objeto que se desea modificar, en caso de no existir se crea uno nuevo
			 * @return objeto Pedido que fue modificado
			 */
			@PutMapping("/modificarPedido")
		    public Pedido updatePedido(@RequestBody Pedido pedido) 
			{
				Pedido pedidos = pedidoService.findById(pedido.getNoPedido());
				pedidos.setIdPago(pedidos.getIdPago());
				pedidos.setNoPedido(pedidos.getNoPedido());
				pedidos.setIdCliente(pedidos.getIdCliente());
				pedidos.setURLLiga(pedidos.getURLLiga());
				pedidos.setStatus(pedidos.getStatus());
				pedidos.setCreado(pedidos.getCreado());
				pedidos.setPagoFecha(pedidos.getPagoFecha());
				pedidos.setCanceladoFecha(pedidos.getCanceladoFecha());
				pedidos.setPagado(pedidos.isPagado());
				pedidos.setCancelado(pedidos.isCancelado());
				pedidos.setCorreoCliente(pedidos.getCorreoCliente());
				pedidos.setActivo(true);
				pedidos.setFechaCreacion(new Date());
				pedidos.setFechaModificacion(pedidos.getFechaModificacion());
				pedidoService.save(pedido);
				return pedido;
			}//fIN DEL METODO
			
			//--------------------------SERVICIO WEB PEDIDODETALLE----------------------------------
			/**
			 * Muestra todos los detalles de los pedidos registrados
			 * @return regresa la lista de objetos PedidoDetalle
			 */
			@GetMapping("/obtenerPedidoDetalle")
			public List<PedidoDetalle> findAllPedidoDetalle()
			{
				return pedidoDetalleService.findAll();
			}//Fin del metodo
			
			/**
			 * Muestra solo un pedido Detalle
			 * @param ordenPedidoDetalleid id del pedido detalle que se quiere mostrar, en caso de no encontrarse se genera RuntimeException
			 * @return regresa el objeto PedidoDetalle con id ordenPedidoDetalleid
			 */
			@GetMapping("/obtenerPedidoDetalle/{ordenPedidoDetalleid}")
			@ResponseBody
			public PedidoDetalle getPedidoDetalle(@PathVariable("pedidoDetalleid") int pedidoDetalleid)
			{
				PedidoDetalle pedidoDetalle = pedidoDetalleService.findById(pedidoDetalleid);
				if(pedidoDetalle == null)
				{
					throw new RuntimeException("pedidoDetalle id not found -"+pedidoDetalleid);
				}
				return pedidoDetalle;
			}
			
			
			
			
			/**
			 * Este metodo añade un nuevo Pedido detalle
			 * @param pedidoDetalle objeto de tipo PedidoDetalle que se va a agregar a la base de datos, en caso de tener un id ya registrado solo se actualizan los valores
			 * @return objeto PedidoDetalle añadido
			 */
			@PostMapping("/agregarPedidoDetalle")
			public PedidoDetalle addPedidoDetalle(@RequestBody PedidoDetalle pedidoDetalle)
			{
				pedidoDetalleService.save(pedidoDetalle);
				return pedidoDetalle;
			}//Fin del metodo
			
			/**
			 * Metodo que modifica un Pedido detalle ya registrado
			 * @param pedidoDetalle objeto que se desea modificar, en caso de no existir se crea uno nuevo
			 * @return objeto PedidoDetalle que fue modificado
			 */
			@PutMapping("/modificarPedidoDetalle")
		    public PedidoDetalle updatePedidoDetalle(@RequestBody PedidoDetalle pedidoDetalle) 
			{
				PedidoDetalle pedidosDetalles = pedidoDetalleService.findById(pedidoDetalle.getNoPedido());
				pedidosDetalles.setIdOrdenVentaDetalle(pedidosDetalles.getIdOrdenVentaDetalle());
				pedidosDetalles.setConcepto(pedidosDetalles.getConcepto());
				pedidosDetalles.setCantidad(pedidosDetalles.getCantidad());
				pedidosDetalles.setImporte(pedidosDetalles.getImporte());
				pedidosDetalles.setFechaInicio(pedidosDetalles.getFechaInicio());
				pedidosDetalles.setFechaFin(pedidosDetalles.getFechaFin());
				pedidosDetalles.setIdOrdenDeVenta(pedidosDetalles.getIdOrdenDeVenta());
				pedidosDetalles.setIdProdServ(pedidosDetalles.getIdProdServ());
				pedidosDetalles.setPrecioUnitario(pedidosDetalles.getPrecioUnitario());
				pedidosDetalles.setIdCasillero(pedidosDetalles.getIdCasillero());
				pedidosDetalles.setDescuento(pedidosDetalles.getDescuento());
				pedidosDetalles.setIVA(pedidosDetalles.getIVA());
				pedidosDetalles.setSubImporte(pedidosDetalles.getSubImporte());
				pedidosDetalles.setActivo(true);
				pedidosDetalles.setFechaCreacion(new Date());
				pedidosDetalles.setFechaModificacion(pedidosDetalles.getFechaModificacion());
				pedidoDetalleService.save(pedidoDetalle);
				return pedidoDetalle;
			}//Fin del metodo

			//--------------------------SERVICIO WEB RECIBO----------------------------------
			@PostMapping("/obtenerRecibo")
			@ResponseBody
			public ResponseEntity<?> obtenerRecibo(@RequestBody Body body){
				Connection conn = null;
	          	ArrayList<com.tutorial.crud.dto.Recibo> listaReporte = new ArrayList<com.tutorial.crud.dto.Recibo>();
		        try {
		            // Carga el driver de oracle
		        	DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
		            

		        	
		            conn = DriverManager.getConnection(dbURL, userData, passData);
		            
		            PreparedStatement ps=conn.prepareStatement("DataFlowAlpha.dbo.sp_Consulta_Recibo_Conceptos ? ");
		            ps.setString(1, body.getRecibo());
	              	ResultSet rs =ps.executeQuery();
	              	try {
	                    Factura factura=facturaService.getByRecibo(body.getRecibo());
	                    factura.setFacturado(true);
	                    facturaService.save(factura);
        				return new ResponseEntity<>(factura, HttpStatus.OK);        		
                	}catch(NoSuchElementException e) {                		
                	}
	                while (rs.next()) {	                	
	                	com.tutorial.crud.dto.Recibo to=new com.tutorial.crud.dto.Recibo();
	                	to.setFechaCaptura(rs.getDate(1));
	                	to.setFolio(rs.getString(2));
	                	to.setIdCliente(rs.getInt(3));
	                	to.setNombreCliente(rs.getString(4));
	                	to.setMembresia(rs.getLong(5));
	                	to.setCodigoPostal(rs.getString(6));
	                	to.setRfc(rs.getString(7));
	                	to.setIdVenta(rs.getInt(8));
	                	to.setConcepto(rs.getString(9));
	                	to.setCosto(rs.getFloat(10));
	                	to.setClave(rs.getString(11));
	                	to.setPrecioUnitario(rs.getFloat(12));
	                	to.setPrecioUnitarioIVA(rs.getFloat(13));
	                	to.setTotal(rs.getFloat(14));
	                	to.setIdProducto(String.valueOf(rs.getInt(15)));
		              	this.update(to.getIdCliente());
	                	Cliente cliente=clienteService.findById(to.getIdCliente());
	                	if(cliente.getEmail()==null) {
	                		to.setCorreo("");
	                	}
	                	to.setCorreo(cliente.getEmail());
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
		        try {
		        conn = DriverManager.getConnection(dbURLGlobal, userGlobal, passGlobal);
	            
	            PreparedStatement ps=conn.prepareStatement("exec globalsoft_pruebas.dbo.sp_Consulta_Recibo_Conceptos ? ");
	            ps.setString(1, body.getRecibo());
              	ResultSet rs =ps.executeQuery();
              	try {
                    Factura factura=facturaService.getByRecibo(body.getRecibo());
                    factura.setFacturado(true);
                    facturaService.save(factura);
    				return new ResponseEntity<>(factura, HttpStatus.OK);        		
            	}catch(NoSuchElementException e) {                		
            	}
                while (rs.next()) {	                	
                	com.tutorial.crud.dto.Recibo to=new com.tutorial.crud.dto.Recibo();
                	to.setFechaCaptura(rs.getDate(1));
                	to.setFolio(rs.getString(2));
                	to.setIdCliente(0);
                	to.setNombreCliente("");
                	to.setMembresia(0);
                	to.setCodigoPostal("");
                	to.setRfc("");
                	to.setIdVenta(0);
                	to.setConcepto(rs.getString(3));
                	to.setCosto(rs.getFloat(4));
                	to.setClave(rs.getString(5));
                	to.setPrecioUnitario(rs.getFloat(6));
                	to.setPrecioUnitarioIVA(rs.getFloat(7));
                	to.setTotal(rs.getFloat(8));
                	to.setCodigo(rs.getString(9));
                	to.setIdProducto(rs.getString(10));
                	to.setUnidad(rs.getString(11));
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
		        
				return new ResponseEntity<>(listaReporte, HttpStatus.OK);
			}
			public ArrayList<ReciboSAT> obtenerReciboSAT( Body body){
				Connection conn = null;
	          	ArrayList<ReciboSAT> listaReporte = new ArrayList<ReciboSAT>();
		        try {
		            // Carga el driver de oracle
		        	DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
		            

		        	
		            conn = DriverManager.getConnection(dbURL, userData, passData);

		            //conn = DriverManager.getConnection("jdbc:sqlserver://192.168.20.12;database=DataFlowAlpha", "extranet_user", "tyGDix##dJGJ5");
		            PreparedStatement ps=conn.prepareStatement("DataFlowAlpha.dbo.sp_Consulta_Recibo_Conceptos_SAT_Completo ? ");
		            ps.setString(1, body.getRecibo());
	              	ResultSet rs =ps.executeQuery();
	              	/*try {
	                    Factura factura=facturaService.getByRecibo(body.getRecibo());
	                    factura.setFacturado(true);
	                    facturaService.save(factura);
        				return factura;        		
                	}catch(NoSuchElementException e) {                		
                	}*/
	                while (rs.next()) {	                	
	                	ReciboSAT to=new ReciboSAT();
	                	to.setFechaCaptura(rs.getDate(1));
	                	to.setFolio(rs.getString(2));
	                	to.setIdCliente(rs.getInt(3));
	                	to.setNombreCliente(rs.getString(4));
	                	to.setCodigoPostal(rs.getString(6));
	                	to.setRfc(rs.getString(7));
	                	to.setIdVenta(rs.getInt(8));
	                	to.setConcepto(rs.getString(9));
	                	to.setCosto(rs.getFloat(10));
	                	to.setClave(rs.getString(11));
	                	to.setPrecioUnitario(rs.getFloat(12));
	                	to.setPrecioUnitarioIVA(rs.getFloat(13));
	                	to.setTotal(rs.getFloat(14));
	                	to.setIdProducto(String.valueOf(rs.getInt(15)));
		              	this.update(to.getIdCliente());
	                	Cliente cliente=clienteService.findById(to.getIdCliente());
	                	if(cliente!=null) {
	                		if(cliente.getEmail()==null) {
	                    		to.setCorreo("");
	                    	}else {
	                        	to.setCorreo(cliente.getEmail());                    		
	                    	}
	                    	to.setMembresia(cliente.getNoMembresia());
	                	}
	                	
	                	String observaciones=rs.getString(16);
	                	String[] observacionesSplit=observaciones.split("\\|");
	                	to.setUnidad(observacionesSplit[1]);
	                	to.setProductCode(observacionesSplit[0]);
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
		        try {
		        conn = DriverManager.getConnection(dbURLGlobal, userGlobal, passGlobal);
	            
	            PreparedStatement ps=conn.prepareStatement("exec globalsoft.dbo.sp_Consulta_Recibo_Conceptos ? ");
	            ps.setString(1, body.getRecibo());
              	ResultSet rs =ps.executeQuery();
              	/*try {
                    Factura factura=facturaService.getByRecibo(body.getRecibo());
                    factura.setFacturado(true);
                    facturaService.save(factura);
    				return new ResponseEntity<>(factura, HttpStatus.OK);        		
            	}catch(NoSuchElementException e) {                		
            	}*/
                while (rs.next()) {	                	
                	ReciboSAT to=new ReciboSAT();
                	to.setFechaCaptura(rs.getDate(1));
                	to.setFolio(rs.getString(2));
                	to.setIdCliente(0);
                	to.setNombreCliente("");
                	to.setMembresia(0);
                	to.setCodigoPostal("");
                	to.setRfc("");
                	to.setIdVenta(0);
                	to.setConcepto(rs.getString(3));
                	to.setCosto(rs.getFloat(4));
                	to.setClave(rs.getString(5));
                	to.setPrecioUnitario(rs.getFloat(6));
                	to.setPrecioUnitarioIVA(rs.getFloat(7));
                	to.setTotal(rs.getFloat(8));
                	to.setCodigo(rs.getString(9));
                	to.setIdProducto(rs.getString(10));
                	to.setUnidad(rs.getString(11));
                	to.setProductCode(rs.getString(5));
                	to.setUnidad(rs.getString(9));
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
		        
				return listaReporte;
			}
			@PostMapping("/facturarRecibo")
			@ResponseBody  
			public ResponseEntity<?> facturarRecibo(@RequestBody Body body){
				Factura factura;
		        Calendar ca=Calendar.getInstance();
		        Calendar fecha = Calendar.getInstance();
				int mesActual = fecha.get(Calendar.MONTH)+1;
				int mesRecibo,idCliente;
				JSONObject json=new JSONObject();
				ArrayList<ReciboSAT>reciboValido=this.obtenerReciboSAT(body);
				if(reciboValido.size()==0){
					json.put("respuesta", "Este recibo no es valido");
					return new ResponseEntity<>(json.toString(), HttpStatus.CONFLICT);  
				}

				Date fecha2 = null;
				try {
					ca.setTime(reciboValido.get(0).getFechaCaptura());
				}catch(NullPointerException e) {
					for(int i=0;i<reciboValido.size();i++) {
						if(reciboValido.get(i).getFechaCaptura()!=null) {
							fecha2=reciboValido.get(i).getFechaCaptura();
						}
					}
					try {
						ca.setTime(fecha2);
						
					}catch(NullPointerException e1) {
						System.out.println("Fallo al intentar facturar recibo: "+body.getRecibo());
					}
				}
				mesRecibo = ca.get(Calendar.MONTH)+1;
				if(mesRecibo!=mesActual) {
					json.put("respuesta", "El mes actual no coincide con el mes del recibo, fecha del recibo "+reciboValido.get(0).getFechaCaptura());
					return new ResponseEntity<>(json.toString(), HttpStatus.CONFLICT);  
				}
				idCliente=body.getUsuario();
				/*idClienteRecibo=reciboValido.get(0).getIdCliente();
				if(idClienteRecibo!=idCliente && idClienteRecibo!=0) {
					json.put("respuesta", "El id del cliente no coincide");
					return new ResponseEntity<>(json.toString(), HttpStatus.CONFLICT); 
				}*/
				try {
					factura=facturaService.getByRecibo(body.getRecibo());
					json.put("respuesta", "Este recibo ya esta facturado");
					return new ResponseEntity<>(json.toString(), HttpStatus.UNAUTHORIZED);    		
            	}catch(NoSuchElementException e) {
            		/*factura=new Factura();
					factura.setFechaCreacion(new Date());
					factura.setFechaModificacion(new Date());
					factura.setRecibo(body.getRecibo());
					factura.setUuid(body.getFactura());
					facturaService.save(factura);*/
            		try {
                		factura=facturaService.getByCliente(idCliente).get(0);
            		}catch(IndexOutOfBoundsException ex) {
            			json.put("respuesta", "Recibo no facturado");       
    					return new ResponseEntity<>(json.toString(), HttpStatus.OK);   	
            		}
            		DatosFiscalesDTO datos=new DatosFiscalesDTO();
            		datos.setCodigoPostal(factura.getCodigoPostal());
            		datos.setEmail(factura.getEmail());
            		datos.setNombre(factura.getNombre());
            		datos.setRegimenFiscal(factura.getRegimenFiscal());
            		datos.setRfc(factura.getRfc());
            		datos.setUsoCFDI(factura.getUsoCFDI()); 
					return new ResponseEntity<>(datos, HttpStatus.OK);   		
            	}
				
		     
			}
			//--------------------------SERVICIO WEB VENTA----------------------------------
			/**
			 * Muestra todos las ventas registrados
			 * @return regresa la lista de objetos Venta
			 */
			@GetMapping("/obtenerVenta")
			public List<Venta> findAllVenta()
			{
				return ventaService.findAll();
			}//Fin del metodo
			
			/**
			 * Muestra solo un venta
			 * @param ordenVentaid id del venta que se quiere mostrar, en caso de no encontrarse se genera RuntimeException
			 * @return regresa el objeto Venta con id ordenVentaid
			 */
			@GetMapping("/obtenerVenta/{ordenVentaid}")
			@ResponseBody
			public Venta getVenta(@PathVariable("ventaid") int ventaid)
			{
				Venta venta = ventaService.findById(ventaid);
				if(venta == null)
				{
					throw new RuntimeException("venta id not found -"+ventaid);
				}
				return venta;
			}//Fin del metodo

			/**
			 * Este metodo añade un nuevo venta
			 * @param venta objeto de tipo Venta que se va a agregar a la base de datos, en caso de tener un id ya registrado solo se actualizan los valores
			 * @return objeto Venta añadido
			 */
			@PostMapping("/agregarVenta")
			public Venta addVenta(@RequestBody Venta venta)
			{
				ventaService.save(venta);
				return venta;
			}//Fin del metodo
			
			/**
			 * Metodo que modifica un Venta ya registrado
			 * @param venta objeto que se desea modificar, en caso de no existir se crea uno nuevo
			 * @return objeto Venta que fue modificado
			 */
			@PutMapping("/modificarVenta")
		    public Venta updatePedidoDetalle(@RequestBody Venta venta) 
			{
				Venta ventas = ventaService.findById(venta.getIdVenta());
				ventas.setIdCliente(ventas.getIdCliente());
				ventas.setConcepto(ventas.getConcepto());
				ventas.setImporteOV(ventas.getImporteOV());
				ventas.setCancelado(ventas.isCancelado());
				ventas.setIdOrdenDeVentaDetalle(ventas.getIdOrdenDeVentaDetalle());
				ventas.setFechaInicio(ventas.getFechaInicio());
				ventas.setFechaFin(ventas.getFechaFin());
				ventas.setMedioDePago(ventas.getMedioDePago());
				ventas.setImportePago(ventas.getImportePago());
				ventas.setFechaAplicacion(ventas.getFechaAplicacion());
				ventas.setFolioRecibo(ventas.getFolioRecibo());
				ventas.setEstaPagado(ventas.isEstaPagado());
				ventas.setCuotaTipo(ventas.getCuotaTipo());
				ventas.setFamilia(ventas.getFamilia());
				ventas.setGrupo(ventas.getGrupo());
				ventas.setCategoria(ventas.getCategoria());
				ventas.setSubcategoria(ventas.getSubcategoria());
				ventas.setProgramacionTipo(ventas.getProgramacionTipo());
				ventas.setClienteTipo(ventas.getClienteTipo());
				ventas.setMembresiaTipo(ventas.getMembresiaTipo());
				ventas.setActivo(true);
				ventas.setFechaCreacion(new Date());
				ventas.setFechaModificacion(ventas.getFechaModificacion());
				ventaService.save(venta);
				return venta;
			}//Fin del metodo
			
			//--------------------------SERVICIO WEB CATEGORIA----------------------------------
			/**
			 * Muestra todos las categorias registrados
			 * @return regresa la lista de objetos Categoria
			 */
			@GetMapping("/obtenerCategoria")
			public List<Categoria> findAllCategoria()
			{
				return categoriaService.findAll();
			}//Fin del metodo

			/**
			 * Muestra solo un Categoria
			 * @param ordenCategoriaid id del categoria que se quiere mostrar, en caso de no encontrarse se genera RuntimeException
			 * @return regresa el objeto Categoria con id ordenCategoriaid
			 */
			@GetMapping("/obtenerCategoria/{ordenCategoriaid}")
			@ResponseBody
			public Categoria getCategoria(@PathVariable("categoriaid") int categoriaid)
			{
				Categoria categoria = categoriaService.findById(categoriaid);
				if(categoria == null)
				{
					throw new RuntimeException("categoria id not found -"+categoriaid);
				}
				return categoria;
			}//Fin del metodo

			/**
			 * Este metodo añade un nuevo categoria
			 * @param categoria objeto de tipo Categoria que se va a agregar a la base de datos, en caso de tener un id ya registrado solo se actualizan los valores
			 * @return objeto Categoria añadido
			 */
			@PostMapping("/agregarCategoria")
			public Categoria addCategoria(@RequestBody Categoria categoria)
			{
				categoriaService.save(categoria);
				return categoria;
			}//FIn del metodo
			
			/**
			 * Metodo que modifica un Categoria ya registrado
			 * @param categoria objeto que se desea modificar, en caso de no existir se crea uno nuevo
			 * @return objeto Categoria que fue modificado
			 */
			@PutMapping("/modificarCategoria")
		    public Categoria updateCategoria(@RequestBody Categoria categoria) 
			{
				Categoria categorias = categoriaService.findById(categoria.getId());
				categorias.setNombre(categorias.getNombre());
				categorias.setActivo(true);
				categorias.setFechaCreacion(new Date());
				categorias.setFechaModificacion(categorias.getFechaModificacion());
				categoriaService.save(categoria);
				return categoria;
			}//FIn del metodo
			
			//--------------------------SERVICIO WEB ESTATUSCLIENTE----------------------------------
			/**
			 * Muestra todos los Estatus cliente registrados
			 * @return regresa la lista de objetos EstatusCliente
			 */
			@GetMapping("/obtenerEstatusCliente")
			public List<EstatusCliente> findAllEstatusCliente()
			{
				return estatusClienteService.findAll();
			}//Fin del metodo
			
			/**
			 * Muestra solo un Estatus cliente
			 * @param ordenCategoriaid id del estatus cliente que se quiere mostrar, en caso de no encontrarse se genera RuntimeException
			 * @return regresa el objeto EstatusCliente con id ordenCategoriaid
			 */
			@GetMapping("/obtenerEstatusCliente/{ordenCategoriaid}")
			@ResponseBody
			public EstatusCliente getEstatusCliente(@PathVariable("estatusClienteid") int estatusClienteid)
			{
				EstatusCliente estatusCliente = estatusClienteService.findById(estatusClienteid);
				if(estatusCliente == null)
				{
					throw new RuntimeException("estatusCliente id not found -"+estatusClienteid);
				}
				return estatusCliente;
			}//Fin del metodo

			/**
			 * Este metodo añade un nuevo estatus cliente
			 * @param estatusCliente objeto de tipo EstatusCliente que se va a agregar a la base de datos, en caso de tener un id ya registrado solo se actualizan los valores
			 * @return objeto EstatusCliente añadido
			 */
			@PostMapping("/agregarEstatusCliente")
			public EstatusCliente addEstatusCliente(@RequestBody EstatusCliente estatusCliente)
			{
				estatusClienteService.save(estatusCliente);
				return estatusCliente;
			}//Fin del metodo
			
			/**
			 * Metodo que modifica un Estatus Cliente ya registrado
			 * @param estatusCliente objeto que se desea modificar, en caso de no existir se crea uno nuevo
			 * @return objeto EstatusCliente que fue modificado
			 */
			@PutMapping("/modificarEstatusCliente")
		    public EstatusCliente updateEstatusCliente(@RequestBody EstatusCliente estatusCliente) 
			{
				EstatusCliente estatusCategorias = estatusClienteService.findById(estatusCliente.getIdStatusCliente());
				estatusCategorias.setNombre(estatusCategorias.getNombre());
				estatusCategorias.setActivo(true);
				estatusCategorias.setFechaCreacion(new Date());
				estatusCategorias.setFechaModificacion(estatusCategorias.getFechaModificacion());
				estatusClienteService.save(estatusCliente);
				return estatusCliente;
			}//Fin del metodo
			
//--------------------------SERVICIO WEB TIPO MEMBRESIA----------------------------------
			/**
			 * Muestra todos los Tipos de membresia registrados
			 * @return regresa la lista de objetos TipoMembresia
			 */
			@GetMapping("/obtenerTipoMembresia")
			public List<TipoMembresia> findAllTipoMembresia()
			{
				return tipoMembresiaService.findAll();
			}//Fin del metodo
			
			/**
			 * Muestra solo un Tipo membresia
			 * @param  id del tipo membresia que se quiere mostrar, en caso de no encontrarse se genera RuntimeException
			 * @return regresa el objeto TipoMembresia con id {id}
			 */
			@GetMapping("/obtenerTipoMembresia/{id}")
			@ResponseBody
			public TipoMembresia getTipoMembresia(@PathVariable("id") int id)
			{
				TipoMembresia tipoMembresia = tipoMembresiaService.findById(id);
				if(tipoMembresia == null)
				{
					throw new RuntimeException("TipoMembresia id not found -"+id);
				}
				return tipoMembresia;
			}//Fin del metodo

			/**
			 * Este metodo añade un nuevo Tipo membresia
			 * @param tipoMembresia objeto de tipo TipoMembresia que se va a agregar a la base de datos, en caso de tener un id ya registrado solo se actualizan los valores
			 * @return objeto TipoMembresia añadido
			 */
			@PostMapping("/agregarTipoMembresia")
			public TipoMembresia addTipoMembresia(@RequestBody TipoMembresia tipoMembresia)
			{
				tipoMembresiaService.save(tipoMembresia);
				return tipoMembresia;
			}//Fin del metodo
			
			/**
			 * Metodo que modifica un Tipo Membresia ya registrado
			 * @param tipoMembresia objeto que se desea modificar, en caso de no existir se crea uno nuevo
			 * @return objeto TipoMembresia que fue modificado
			 */
			@PutMapping("/modificarTipoMembresia")
		    public TipoMembresia updateTipoMembresia(@RequestBody TipoMembresia tipoMembresia) {
				tipoMembresiaService.save(tipoMembresia);
		        //este metodo actualizará al usuario enviado
				tipoMembresia.setFechaModificacion(new Date());
		        return tipoMembresia;
			}//FIn del metodo
			
//--------------------------SERVICIO WEB CLIENTE----------------------------------
			/**
			 * Muestra todos los Clientes registrados
			 * @return regresa la lista de objetos Cliente
			 */
			@GetMapping("/obtenerCliente")
		    public List<Cliente> findAllCliente(){
		        return clienteService.findAll();
		    }//Fin del metodo
			
			/**
			 * Muestra solo un cliente
			 * @param  horarioId del cliente que se quiere mostrar, en caso de no encontrarse se genera RuntimeException
			 * @return regresa el objeto Cliente con id horarioId
			 */
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
			
			@GetMapping("/clienteRutina/{horarioId}")
			@CrossOrigin(origins = "*")
			@ResponseBody
		    public ResponseEntity<?> clienteRutina(@PathVariable("horarioId") int horarioId){
				Session currentSession = entityManager.unwrap(Session.class);
			 	Query listaClases;
			 	listaClases = currentSession.createNativeQuery("select idcliente,(cliente.nombre || ' ' || apellidopaterno || ' ' || "
			 			+ "apellidomaterno)as nombre, estatuscobranza.nombre as estatuscobranza,imagen from cliente join estatuscobranza"
			 			+ " on cliente.estatuscobranza=estatuscobranza.id_estatuscobranza left join fotos on cliente.id_foto=fotos.id_foto "
			 			+ "where idcliente="+horarioId+";");

			 	List<Object[]> listResults = listaClases.getResultList();
				List<ClienteRutina> listaDTO= new ArrayList<ClienteRutina>();
				ClienteRutina cliente=new ClienteRutina();    
				cliente.setIdCliente((int) listResults.get(0)[0]);
				cliente.setNombre((String)listResults.get(0)[1]);
				cliente.setEstatusCobranza((String)listResults.get(0)[2]);
				cliente.setImagen((byte[])listResults.get(0)[3]);
		        return new ResponseEntity<>(cliente, HttpStatus.OK);
		    }//Fin del metodo
			
			@GetMapping("/idClienteByMembresia/{idMembresia}")
			@ResponseBody
			public ResponseEntity<?> getIdClienteByMembresia(@PathVariable("idMembresia") long idMembresia){
				Session currentSession = entityManager.unwrap(Session.class);
			 	Query listaClases;
			 	listaClases = currentSession.createNativeQuery("select idcliente from cliente where nomembresia="+idMembresia+";");
			 	int idcliente=(int)listaClases.getSingleResult();
				return new ResponseEntity<>(idcliente, HttpStatus.OK);
			}
			
			@GetMapping("/clienteByMembresia/{idMembresia}")
			@ResponseBody
			public ResponseEntity<?> getClienteByMembresia(@PathVariable("idMembresia") String idMembresia){
				Connection conn = null;
                ClienteReferenciado to=new ClienteReferenciado();
			       try {
			           // Carga el driver de oracle
			       		DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
			           conn = DriverManager.getConnection(dbURL, userData, passData);
			           PreparedStatement ps=conn.prepareStatement("EXEC DataFlowAlpha.dbo.GetMiembroById_Pagos ? ");
			           ps.setString(1, idMembresia);
			          
			           ps.execute();
			           ResultSet rs =ps.executeQuery();
			           rs.next();
		                to.setIdCliente(rs.getInt(1));
	                    to.setMembresia(rs.getString(2));
	                    to.setNombreCompleto(rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5));
			           
			           
			         
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
					return new ResponseEntity<>(to, HttpStatus.OK);
			}
			@GetMapping("/updateCliente/{horarioId}")
			@ResponseBody
		    public String update(@PathVariable("horarioId") int horarioId){
				
				try {
					JSONObject json = new JSONObject(IOUtils.toString(new URL("http://192.168.20.47/ServiciosClubAlpha/api/Miembro/"+horarioId), Charset.forName("UTF-8")));
					
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

            		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
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
                		}
                		cliente.setActivo(true);
                		cliente.setFechaCreacion(new Date());
                		cliente.setFechaModificacion(new Date());
						cliente.setFechaNacimiento(formato.parse(json.getString("FechaNacimiento")) );
						cliente.setFechaFinAcceso(formato.parse(json.getString("FechaFinAcceso")) );
                		cliente.setInicioActividades(formato.parse(json.getString("InicioActividades")));
						this.addFoto(json.getString("UrlFoto"),cliente);
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
			
			/**
			 * Este metodo recibe un objeto ClienteAux y devuelve un objeto Cliente, ClienteAux contiene la ruta de la foto del cliente
			 * y Cliente contiene la foto en si.
			 * @param cliente objeto de tipo ClienteAux que se va a agregar a la base de datos, dentro del metodo se hace uso del
			 * metodo addFoto para poder cambiar la ruta de la foto a una foto, en caso de tener un id ya registrado solo se actualizan los valores
			 * @return objeto Cliente añadido
			 */
			
			@PostMapping("/domiciliarCliente")
			public ResponseEntity<?> domiciliarCliente(@RequestBody Body body){
				JSONObject resp=new JSONObject();
				try {
					Cliente cliente=clienteService.findById(body.getUsuario());
					cliente.setToken(body.getToken());
					cliente.setDomiciliado(true);
					clienteService.save(cliente);
					resp.put("respuesta", "domiciliacion exitosa");
					return new ResponseEntity<>(resp.toString(), HttpStatus.OK);
					
				}catch(NullPointerException e){
					resp.put("respuesta", "id de cliente no valido");
					return new ResponseEntity<>(resp.toString(), HttpStatus.BAD_REQUEST);
					
				}catch(Exception e) {
					e.printStackTrace();
					resp.put("respuesta", "ocurrio un error desconocido durante la domiciliacion");
					return new ResponseEntity<>(resp.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			
			@PostMapping("/cargarDomiciliacion")
			public ResponseEntity<?> cargarDomiciliacion(@RequestBody Body body){
				JSONObject resp=new JSONObject();
				configuracion o;
				String body2;
				try {
					Cliente cliente=clienteService.findById(body.getUsuario());
					if(!cliente.isCargoDomiciliacion()) {
						body2 = "{\r\n"
								+ "\"IDCliente\":"+cliente.getIdCliente()+",  \r\n"
								+ "\"IDClub\":"+cliente.getClub().getIdClub()+",   \r\n"
								+ "\"Cantidad\":1, \r\n"
								+ "\"IDProductoServicio\":"+body.getProducto()+",  \r\n"
								+ "\"Observaciones\":\"\" ,   \r\n"
								+ "\"DescuentoPorciento\":0,  \r\n"
								+ "\"FechaInicio\":\""+new java.sql.Date(new Date().getTime())+" 00:00:00\", \r\n"
								+ "\"FechaFin\":\""+new java.sql.Date(new Date().getTime())+" 00:00:00\",  \r\n"
								+ "\"CobroProporcional\":0, \r\n"
								+ "\"Token\":\"77D5BDD4-1FEE-4A47-86A0-1E7D19EE1C74\"  \r\n"
								+ "}";
						o = configuracionService.findByServiceName("RegistraOV").get();
						try {
					    	System.out.println(e.conectaApiClubPOST(body2,o.getEndpointAlpha()));							
						}catch(Exception e) {
							
						}
						
						cliente.setCargoDomiciliacion(true);
						clienteService.save(cliente);
					}o = configuracionService.findByServiceName("getPedido").get();
					body2 = "{\r\n"
							+ "\"IdCliente\":"+cliente.getIdCliente()+",\r\n"
							+ "\"Token\":\"77D5BDD4-1FEE-4A47-86A0-1E7D19EE1C74\"\r\n"
							+ "}";
					double importe=0;
					try {
				    	String json=e.conectaApiClubPOST(body2,o.getEndpointAlpha());	
				    	JSONObject resp2=new JSONObject(json);
						JSONArray detalle=resp2.getJSONArray("Detalle");
						for(int i=0;i<detalle.length();i++) {
							JSONObject aux=detalle.getJSONObject(i);
							importe=importe+aux.getDouble("Importe");
						}
					}catch(Exception e) {
						e.printStackTrace();
						resp.put("adeudo", "0.00");
						return new ResponseEntity<>(resp.toString(), HttpStatus.OK);
						
					}
					resp.put("adeudo", importe);
					return new ResponseEntity<>(resp.toString(), HttpStatus.OK);
					
					
				}catch(NullPointerException e){
					resp.put("respuesta", "id de cliente no valido");
					return new ResponseEntity<>(resp.toString(), HttpStatus.BAD_REQUEST);
					
				}catch(Exception e) {
					e.printStackTrace();
					resp.put("respuesta", "error durante el proceso");
					return new ResponseEntity<>(resp.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			@PostMapping("/agregarCliente")
		    public Cliente addCliente(@RequestBody ClienteAux client)  {
				Cliente cliente=new Cliente();
				cliente.setApellidoMaterno(client.getApellidoMaterno());
				cliente.setApellidoPaterno(client.getApellidoPaterno());
				cliente.setCategoria(client.getCategoria());
				cliente.setClub(client.getClub());
				cliente.setDomicilioPago(client.isDomicilioPago());
				cliente.setEmail(client.getEmail());
				cliente.setEstatusAcceso(client.getEstatusAcceso());
				cliente.setEstatusCliente(client.getEstatusCliente());
				cliente.setEstatusCobranza(client.getEstatusCobranza());
				cliente.setEstatusMembresia(client.getEstatusMembresia());
				cliente.setFechaFinAcceso(client.getFechaFinAcceso());
				cliente.setFechaNacimiento(client.getFechaNacimiento());
				cliente.setHorarioOtroClub(client.getHorarioOtroClub());
				cliente.setIdCaptura(client.getIdCaptura());
				cliente.setIdCapturaFecha(client.getIdCapturaFecha());
				cliente.setIdCliente(client.getIdCliente());
				cliente.setIdClienteGrupo(client.getIdClienteGrupo());
				cliente.setIdClienteSector(client.getIdClienteSector());
				cliente.setIdSexo(client.getIdSexo());
				cliente.setInicioActividades(client.getInicioActividades());
				cliente.setMensajes(client.getMensajes());
				cliente.setMensualidadPagada(client.isMensualidadPagada());
				cliente.setNacionalidad(client.getNacionalidad());
				cliente.setNombre(client.getNombre());
				cliente.setNombreCompleto(client.getNombreCompleto());
				cliente.setNoMembresia(client.getNoMembresia());
				cliente.setServicio(client.getServicio());
				cliente.setSexo(client.getSexo());
				cliente.setTelefono(client.getTelefono());
				cliente.setTieneAcceso(client.isTieneAcceso());
				cliente.setTipoAcceso(client.isTipoAcceso());
				cliente.setTipoCliente(client.getTipoCliente());
				cliente.setTipoMembresia(client.getTipoMembresia());
				
			try {
				
				 //Este metodo guardará al usuario enviado
					cliente.setFechaCreacion(new Date());
					cliente.setFechaModificacion(new Date());
					cliente.setActivo(true);

					
					Club club=clubService.findById(cliente.getClub().getIdClub());
					if(club==null) {
						cliente.getClub().setFechaCreacion(new Date());
						cliente.getClub().setFechaModificacion(new Date());
						cliente.getClub().setActivo(true);
					}else {
						cliente.setClub(club);
					}
					
					TipoMembresia tipoMembresia=tipoMembresiaService.findById(cliente.getTipoMembresia().getIdTipoMembresia());
					if(tipoMembresia==null) {
						cliente.getTipoMembresia().setFechaCreacion(new Date());
						cliente.getTipoMembresia().setFechaModificacion(new Date());
						cliente.getTipoMembresia().setActivo(true);
						HorarioAcceso horarioAcceso=horarioAccesoService.findById(cliente.getTipoMembresia().getHorarioacceso().getIdHorarioAcceso());
						if(horarioAcceso==null) {
							cliente.getTipoMembresia().getHorarioacceso().setFechaCreacion(new Date());
							cliente.getTipoMembresia().getHorarioacceso().setFechaModificacion(new Date());
							cliente.getTipoMembresia().getHorarioacceso().setActivo(true);
							
							Horario horario=cliente.getTipoMembresia().getHorarioacceso().getHorario();
							horario.setIdHorario(cliente.getTipoMembresia().getHorarioacceso().getIdHorarioAcceso());
							horario.setFechaCreacion(new Date());
							horario.setFechaModificacion(new Date());
							horario.setActivo(true);

							horario.getIdLunes().setIdLunes(cliente.getTipoMembresia().getHorarioacceso().getIdHorarioAcceso());
							horario.getIdLunes().setActivo(true);
							horario.getIdLunes().setFechacreacion(new Date());
							horario.getIdLunes().setFechamodificacion(new Date());

							horario.getIdMartes().setIdMartes(cliente.getTipoMembresia().getHorarioacceso().getIdHorarioAcceso());
							horario.getIdMartes().setActivo(true);
							horario.getIdMartes().setFechacreacion(new Date());
							horario.getIdMartes().setFechamodificacion(new Date());


							horario.getIdMiercoles().setIdMiercoles(cliente.getTipoMembresia().getHorarioacceso().getIdHorarioAcceso());
							horario.getIdMiercoles().setActivo(true);
							horario.getIdMiercoles().setFechacreacion(new Date());
							horario.getIdMiercoles().setFechamodificacion(new Date());

							horario.getIdJueves().setIdJueves(cliente.getTipoMembresia().getHorarioacceso().getIdHorarioAcceso());
							horario.getIdJueves().setActivo(true);
							horario.getIdJueves().setFechacreacion(new Date());
							horario.getIdJueves().setFechamodificacion(new Date());

							horario.getIdViernes().setIdViernes(cliente.getTipoMembresia().getHorarioacceso().getIdHorarioAcceso());
							horario.getIdViernes().setActivo(true);
							horario.getIdViernes().setFechacreacion(new Date());
							horario.getIdViernes().setFechamodificacion(new Date());

							horario.getIdSabado().setIdSabado(cliente.getTipoMembresia().getHorarioacceso().getIdHorarioAcceso());
							horario.getIdSabado().setActivo(true);
							horario.getIdSabado().setFechacreacion(new Date());
							horario.getIdSabado().setFechamodificacion(new Date());

							horario.getIdDomingo().setIdDomingo(cliente.getTipoMembresia().getHorarioacceso().getIdHorarioAcceso());
							horario.getIdDomingo().setActivo(true);
							horario.getIdDomingo().setFechacreacion(new Date());
							horario.getIdDomingo().setFechamodificacion(new Date());
						}else {
							cliente.getTipoMembresia().setHorarioacceso(horarioAcceso);
						}
					}else {
						cliente.setTipoMembresia(tipoMembresia);
					}
					
					TipoCliente tipoCliente=tipoClienteService.findById(cliente.getTipoCliente().getIdTipoCliente());
					if(tipoCliente==null) {
						cliente.getTipoCliente().setFechaCreacion(new Date());
						cliente.getTipoCliente().setFechaModificacion(new Date());
						cliente.getTipoCliente().setActivo(true);
					}else {
						cliente.setTipoCliente(tipoCliente);
					}
					
					Categoria categoria=categoriaService.findById(cliente.getCategoria().getId());
					if(categoria==null) {
						cliente.getCategoria().setFechaCreacion(new Date());
						cliente.getCategoria().setFechaModificacion(new Date());
						cliente.getCategoria().setActivo(true);
					}else {
						cliente.setCategoria(categoria);
					}
					
					EstatusCliente estatusCliente=estatusClienteService.findById(cliente.getEstatusCliente().getIdStatusCliente());
					if(estatusCliente==null) {
						cliente.getEstatusCliente().setFechaCreacion(new Date());
						cliente.getEstatusCliente().setFechaModificacion(new Date());
						cliente.getEstatusCliente().setActivo(true);
					}else {
						cliente.setEstatusCliente(estatusCliente);
					}
					
					EstatusMembresia estatusMembresia=estatusMembresiaService.findById(cliente.getEstatusMembresia().getIdEstatusMembresia());
					if(estatusMembresia==null) {
						cliente.getEstatusMembresia().setFechaCreacion(new Date());
						cliente.getEstatusMembresia().setFechaModificacion(new Date());
						cliente.getEstatusMembresia().setActivo(true);
					}else {
						cliente.setEstatusMembresia(estatusMembresia);
					}
					
					EstatusCobranza estatusCobranza=estatusCobranzaService.findById(cliente.getEstatusCobranza().getIdEstatusCobranza());
					if(estatusCobranza==null) {
						cliente.getEstatusCobranza().setFechaCreacion(new Date());
						cliente.getEstatusCobranza().setFechaModificacion(new Date());
						cliente.getEstatusCobranza().setActivo(true);
					}else {
						cliente.setEstatusCobranza(estatusCobranza);
					}
					if(cliente.getMensajes()!=null) {
						List<Mensajes> mensajes=cliente.getMensajes();
						int longitud=mensajes.size();
						for(int i=0;i<longitud;i++) {
							Mensajes mensaje=mensajesService.findById(mensajes.get(i).getIdMensaje());
							if(mensaje==null) {
								cliente.getMensajes().get(i).setFechaCreacion(new Date());
								cliente.getMensajes().get(i).setFechaModificacion(new Date());
								cliente.getMensajes().get(i).setActivo(true);
								cliente.getMensajes().get(i).setCliente(cliente.getIdCliente());
								cliente.getMensajes().add(mensaje);

							}else {
								cliente.getMensajes().set(i, mensaje);
							}
						}
					} 
					 List<HorarioOtroClub> horarioOtroClubs=cliente.getHorarioOtroClub();
					if(cliente.getHorarioOtroClub()!=null) {
						int longitud=horarioOtroClubs.size();
						for(int i=0;i<longitud;i++) {
							HorarioOtroClub horarioOtroClub=horarioOtroClubService.findById(new HorarioOtroClubId(cliente.getHorarioOtroClub().get(i).getTerminalId(),cliente.getHorarioOtroClub().get(i).getDesde(),cliente.getHorarioOtroClub().get(i).getHasta()));
							if(horarioOtroClub==null) {
								cliente.getHorarioOtroClub().get(i).setFechaCreacion(new Date());
								cliente.getHorarioOtroClub().get(i).setFechaModificacion(new Date());
								cliente.getHorarioOtroClub().get(i).setActivo(true);
								HorarioAcceso horarioAcceso=horarioAccesoService.findById(cliente.getHorarioOtroClub().get(i).getHorarioacceso().getIdHorarioAcceso());
								if(horarioAcceso==null) {
									cliente.getHorarioOtroClub().get(i).getHorarioacceso().setFechaCreacion(new Date());
									cliente.getHorarioOtroClub().get(i).getHorarioacceso().setFechaModificacion(new Date());
									cliente.getHorarioOtroClub().get(i).getHorarioacceso().setActivo(true);
									
									Horario horario=cliente.getHorarioOtroClub().get(i).getHorarioacceso().getHorario();

									horario.setIdHorario(cliente.getHorarioOtroClub().get(i).getHorarioacceso().getIdHorarioAcceso());
									horario.setFechaCreacion(new Date());
									horario.setFechaModificacion(new Date());
									horario.setActivo(true);

									horario.getIdLunes().setIdLunes(cliente.getHorarioOtroClub().get(i).getHorarioacceso().getIdHorarioAcceso());
									horario.getIdLunes().setActivo(true);
									horario.getIdLunes().setFechacreacion(new Date());
									horario.getIdLunes().setFechamodificacion(new Date());

									horario.getIdMartes().setIdMartes(cliente.getHorarioOtroClub().get(i).getHorarioacceso().getIdHorarioAcceso());
									horario.getIdMartes().setActivo(true);
									horario.getIdMartes().setFechacreacion(new Date());
									horario.getIdMartes().setFechamodificacion(new Date());


									horario.getIdMiercoles().setIdMiercoles(cliente.getHorarioOtroClub().get(i).getHorarioacceso().getIdHorarioAcceso());
									horario.getIdMiercoles().setActivo(true);
									horario.getIdMiercoles().setFechacreacion(new Date());
									horario.getIdMiercoles().setFechamodificacion(new Date());

									horario.getIdJueves().setIdJueves(cliente.getHorarioOtroClub().get(i).getHorarioacceso().getIdHorarioAcceso());
									horario.getIdJueves().setActivo(true);
									horario.getIdJueves().setFechacreacion(new Date());
									horario.getIdJueves().setFechamodificacion(new Date());

									horario.getIdViernes().setIdViernes(cliente.getHorarioOtroClub().get(i).getHorarioacceso().getIdHorarioAcceso());
									horario.getIdViernes().setActivo(true);
									horario.getIdViernes().setFechacreacion(new Date());
									horario.getIdViernes().setFechamodificacion(new Date());

									horario.getIdSabado().setIdSabado(cliente.getHorarioOtroClub().get(i).getHorarioacceso().getIdHorarioAcceso());
									horario.getIdSabado().setActivo(true);
									horario.getIdSabado().setFechacreacion(new Date());
									horario.getIdSabado().setFechamodificacion(new Date());

									horario.getIdDomingo().setIdDomingo(cliente.getHorarioOtroClub().get(i).getHorarioacceso().getIdHorarioAcceso());
									horario.getIdDomingo().setActivo(true);
									horario.getIdDomingo().setFechacreacion(new Date());
									horario.getIdDomingo().setFechamodificacion(new Date());
								}else {
									cliente.getHorarioOtroClub().get(i).setHorarioacceso(horarioAcceso);
								}
							}else {
								cliente.getHorarioOtroClub().set(i, horarioOtroClub);
							}
						}
					}
					
					for(int i=0;i<cliente.getHorarioOtroClub().size();i++ ) {
						HorarioOtroClubId auxHorario1=new HorarioOtroClubId(cliente.getHorarioOtroClub().get(i).getTerminalId(),
								cliente.getHorarioOtroClub().get(i).getDesde(),cliente.getHorarioOtroClub().get(i).getHasta());
						for(int j=i+1;j<cliente.getHorarioOtroClub().size();j++) {
							HorarioOtroClubId auxHorario2=new HorarioOtroClubId(cliente.getHorarioOtroClub().get(j).getTerminalId(),
									cliente.getHorarioOtroClub().get(j).getDesde(),cliente.getHorarioOtroClub().get(j).getHasta());
							if(auxHorario1.getTerminalId()==auxHorario2.getTerminalId() && auxHorario1.getDesde().equals(auxHorario1.getDesde() )&& auxHorario1.getHasta().equals(auxHorario2.getHasta())) {
								cliente.getHorarioOtroClub().remove(j);
							}
						}
					}
					Cliente clAux=clienteService.findById(client.getIdCliente());
					if(clAux==null) {
						Foto foto=this.addFoto(client.getURLFoto(),cliente);
						cliente.setURLFoto(foto);
					}else {
						if(clAux.getURLFoto()==null) {
							Foto foto=this.addFoto(client.getURLFoto(),cliente);
							cliente.setURLFoto(foto);
						}else {
							cliente.setURLFoto(cliente.getURLFoto());						
						}
					}
					
					clienteService.save(cliente);
			        return cliente;
			        
				} catch (Exception e) {
				}
		        return cliente;

		    }
			/**
			 * Metodo que modifica un Cliente ya registrado
			 * @param cliente objeto que se desea modificar, en caso de no existir se crea uno nuevo
			 * @return objeto Cliente que fue modificado
			 */
			@PutMapping("/modificarCliente")
		    public Cliente updateCliente(@RequestBody Cliente cliente) {
				clienteService.save(cliente);
		        //este metodo actualizará al usuario enviado
		        cliente.setFechaModificacion(new Date());
		        return cliente;
		    }//FIn del metodo
			
			
			
			
			/**
			 * Metodo que muestra todas las fotos almacenados en la base de datos
			 * @return lista de fotos
			 */
			
			
			/**
			 * Metodo que muestra solo una foto
			 * @param fotoId es el id de la foto que se quiere mostrar, en caso de no encontrarlo genera un RuntimeException
			 * @return La foto con el id fotoId
			 */
			@GetMapping("/obtenerFoto")
			@ResponseBody
		    public Foto getFoto(){
				Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				String username;
				if (principal instanceof UserDetails) {
					username = ((UserDetails)principal).getUsername();
				} else {
				  	username = principal.toString();
				}
		        Foto foto = clienteService.findById( Integer.parseInt(username)).getURLFoto();
		      
		        return foto;
		    }//fin del metodo
			
			/**
			 * Metodo que añade a la base de datos una nueva foto, solamente puede ser usado este metodo dentro del metodo addCliente
			 * @param foto es el objeto foto que se desea añadir, en caso de contar con el mismo id de algun registro en la bd
			 *  solo se actualizan los valores
			 * @return el objeto foto que fue almacenado
			 */
			
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
						cliente.setURLFoto(foto);
						//fotoService.save(foto);		quitar comentario despues
				        return foto;
			        }
			    }catch(Exception e){
			    	e.printStackTrace();
			    }finally {
			    }
				return null;
		    }//fin del metodo

		    
		    
			/**
			 * Metodo que modifica una foto ya existente en la base de datos (la foto debe existir sino sera creado uno nuevo)
			 * @param foto es el objeto foto que se quiere modificar
			 * @return objeto foto ya modificado
			 */
			@PutMapping("/modificarFoto")
		    public Foto updateFoto(@RequestBody Foto foto) {
				Foto fotoaux=this.getFoto();
				if(fotoaux!=null) {
					fotoService.save(foto);
			        //este metodo actualizará al usuario enviado
					foto.setFechaModificacion(new Date());
				}
		        return foto;
		    }//FIn del metodo
			
			//--------------------------SERVICIO WEB Movimientos----------------------------------
			/**
			 * Metodo que muestra todos los movimientos almacenados en la base de datos
			 * @return lista de movimientos
			 */
			@GetMapping("/obtenerMovimientos")
		    public List<Movimientos> findAllMovimientos(){
		        return movimientosService.findAll();
		    }//fin del metodo
			
			/**
			 * Metodo que muestra la lista de movimientos de un solo cliente
			 * @param idCliente es el id del cliente que se quiere mostrar, en caso de no encontrarlo genera un RuntimeException
			 * @return los movimientos del cliente idCliente
			 */
			@GetMapping("/obtenerMovimientos/{idCliente}")
			@ResponseBody
		    public ResponseEntity<?> getMovimientos(@PathVariable("idCliente") int idCliente){
				String body2 = "{\n"
		    			+ "\"IdCliente\":"+idCliente+",\n"
		    			+ "\"Token\":\"77D5BDD4-1FEE-4A47-86A0-1E7D19EE1C74\"\n"
		    			+ "}";
		    	configuracion o = configuracionService.findByServiceName("GetMovimientosbyId").get();
		    	JSONArray json=new JSONArray(e.conectaApiClubPOST(body2,o.getEndpointAlpha()));
		    	ArrayList<MovimientoDTO> json2=this.convertirMovimiento(json);
		    	//Collections.sort(json2);
		    	return new ResponseEntity<>(json2, HttpStatus.OK);
		    }//fin del metodo
			
			@PostMapping("/agregarMovimientos")
		    public ResponseEntity<?> addMovimientos() {
				try {
		            JSONObject token=new JSONObject();
		            token.put("Token",Token);
		            String query= getUsuarios;
		            URL url = new URL(query);
		            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				    conn.setConnectTimeout(5000);
				    conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
				    conn.setDoOutput(true);
				    conn.setDoInput(true);
				    conn.setRequestMethod("POST");

				    OutputStream os = conn.getOutputStream();
				    os.write(token.toString().getBytes("UTF-8"));
				    os.close();

				    // read the response
				    InputStream in = new BufferedInputStream(conn.getInputStream());
				    String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
				    JSONArray miembros = new JSONArray(result);
		            in.close();
		            conn.disconnect();

					//JSONObject json = new JSONObject(IOUtils.toString(new URL("https://af3bad3e-a46c-4c71-949a-fc6b245e7cee.mock.pstmn.io/ServiciosClubAlpha/api/Miembro/11184"), Charset.forName("UTF-8")));
					//JSONArray json = new JSONArray(IOUtils.toString(new URL("https://a0d69c82-099e-457e-874b-7b6f98384cbc.mock.pstmn.io/alpha/obtenerCliente"), Charset.forName("UTF-8")));
					for(int i=0;i<miembros.length();i++) {
		            	try {					
							JSONObject token2=new JSONObject();
							token2.put("IDCliente", ((JSONObject) miembros.get(i)).get("IDCliente"));
				            token2.put("Token",Token);
							query=(String) GetMovimientosbyId;
							url = new URL(query);
						    conn = (HttpURLConnection) url.openConnection();
						    conn.setConnectTimeout(5000);
						    conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
						    conn.setDoOutput(true);
						    conn.setDoInput(true);
						    conn.setRequestMethod("POST");
						    os = conn.getOutputStream();
						    os.write(token2.toString().getBytes("UTF-8"));
						    os.close();
						    // read the response
						    in = new BufferedInputStream(conn.getInputStream());
						    result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
						    JSONArray movimientosCliente = new JSONArray(result);
				            in.close();
				            conn.disconnect();
						    for(int j=0;j<movimientosCliente.length();j++) {
									JSONObject json = (JSONObject) movimientosCliente.get(j);
									JSONObject json2 = new JSONObject();
									json2.put("idclienteMovimiento", json.get("IDClienteMovimiento"));
									json2.put("idcliente",json.get("IDCliente") );
									json2.put("cliente", json.get("Cliente"));
									json2.put("concepto", json.get("Concepto"));
									json2.put("fechaDeAplicacion", json.get("FechaDeAplicacion"));
									json2.put("idordenDeVenta", json.get("IDOrdenDeVenta"));
									json2.put("idordenDeVentaDetalle", json.get("IDOrdenDeVentaDetalle"));
									json2.put("debito",json.get("Debito"));
									json2.put("saldo",json.get("Saldo"));
									Movimientos movimientos=new Movimientos(json2);
									movimientos.setFechaCreacion(new Date());
									movimientos.setFechaModificacion(new Date());
									movimientos.setActivo(true);
									movimientosService.save(movimientos);
														
				            }
		            	}catch(FileNotFoundException ex) {
						}catch(IOException e) {
						}
						
					}
					System.out.println("Fin");
					
				}catch(Exception e){
					e.printStackTrace();
				}
				return new ResponseEntity<>("Movimientos cargados", HttpStatus.OK);
			}//cierre de metodo
			
			/**
			 * Metodo que modifica los datos de la tabla movimientos ya existente en la base de datos (el id de movimiento
			 *  debe existir sino sera creado uno nuevo)
			 * @param movimientos es el objeto movimiento que se quiere modificar
			 * @return objeto movimiento ya modificado
			 */
			@PutMapping("/modificarMovimientos")
		    public Movimientos updatemovimientos(@RequestBody Movimientos movimientos) {
				Movimientos movimientoss=movimientosService.findById(movimientos.getIDClienteMovimiento());
				movimientoss.setCliente(movimientos.getCliente());
				movimientoss.setConcepto(movimientos.getConcepto());
				movimientoss.setDebito(movimientos.getDebito());
				movimientoss.setFechaDeAplicacion(movimientos.getFechaDeAplicacion());
				movimientoss.setIDCliente(movimientos.getIDCliente());
				movimientoss.setIDClienteMovimiento(movimientos.getIDClienteMovimiento());
				movimientoss.setIDOrdenDeVenta(movimientos.getIDOrdenDeVenta());
				movimientoss.setIDOrdenDeVentaDetalle(movimientos.getIDOrdenDeVentaDetalle());
				movimientoss.setSaldo(movimientos.getSaldo());
				movimientoss.setActivo(movimientos.isActivo());
				movimientoss.setFechaModificacion(new Date());
				movimientosService.save(movimientoss);
		        return movimientoss;
		    }//fin del metodo
			
			
			public ArrayList<MovimientoDTO> convertirMovimiento(JSONArray movimientosCliente) {
				ArrayList<MovimientoDTO> jsonArray=new ArrayList<MovimientoDTO>();
				for(int j=0;j<movimientosCliente.length();j++) {
					JSONObject json = (JSONObject) movimientosCliente.get(j);
					MovimientoDTO json2=new MovimientoDTO();
					json2.setCliente(json.getString("Cliente"));
					json2.setConcepto(json.getString("Concepto"));
					json2.setCredito(json.getFloat("Credito"));
					json2.setDebito(json.getFloat("Debito"));
					json2.setFechaDeAplicacion(json.getString("FechaDeAplicacion"));
					json2.setFolio(json.getString("Folio"));
					json2.setIdcliente(json.getInt("IDCliente"));
					json2.setIdclienteMovimiento(json.getLong("IDClienteMovimiento"));
					json2.setIdordenDeVenta(json.getLong("IDOrdenDeVenta"));
					json2.setIdordenDeVentaDetalle(json.getLong("IDOrdenDeVentaDetalle"));
					json2.setSaldo(json.getFloat("Saldo"));
					jsonArray.add(json2);
				}
				return jsonArray;
				
			}
			
			public JSONObject convertirPedido(JSONObject pedidoCliente) {
				JSONArray detalle=(JSONArray) pedidoCliente.get("Detalle");
			    JSONArray pedidoDetalle=new JSONArray();
			    JSONObject json=new JSONObject();
			    for(int i1=0;i1<detalle.length();i1++){
			    	JSONObject deta=new JSONObject();
			    	deta.put("noPedido", ((JSONObject) detalle.get(i1)).get("NoPedido"));
			    	deta.put("idCliente", ((JSONObject) detalle.get(i1)).get("IDCliente"));
			    	deta.put("idOrdenVentaDetalle",((JSONObject) detalle.get(i1)).get("IDOrdendeVentaDetalle"));
			    	deta.put("concepto", ((JSONObject) detalle.get(i1)).get("Concepto"));
			    	deta.put("cantidad", ((JSONObject) detalle.get(i1)).get("Cantidad"));
			    	deta.put("importe", ((JSONObject) detalle.get(i1)).get("Importe"));
			    	deta.put("fechaInicio", ((JSONObject) detalle.get(i1)).get("FechaInicio"));
			    	deta.put("fechaFin", ((JSONObject) detalle.get(i1)).get("FechaFin"));
			    	deta.put("idOrdenDeVenta", ((JSONObject) detalle.get(i1)).get("IDOrdendeVenta"));
			    	deta.put("idProdServ", ((JSONObject) detalle.get(i1)).get("IDProdServ"));
			    	deta.put("precioUnitario",((JSONObject) detalle.get(i1)).get("PrecioUnitario"));
			    	deta.put("idCasillero", ((JSONObject) detalle.get(i1)).get("IDCasillero"));
			    	deta.put("descuento", ((JSONObject) detalle.get(i1)).get("Descuento"));
			    	deta.put("iVA", ((JSONObject) detalle.get(i1)).get("IVA"));
			    	deta.put("subImporte",((JSONObject) detalle.get(i1)).get("SubImporte"));
			    	pedidoDetalle.put(deta);
			    }			    
			    json.put("noPedido",pedidoCliente.get("NoPedido") );
			    json.put("idCliente",pedidoCliente.get("IDCliente") );
			    json.put("uRLLiga", pedidoCliente.get("URLLigaPago"));
			    json.put("status", pedidoCliente.get("Status"));
			    json.put("creado", pedidoCliente.get("Creado"));
			    json.put("pagoFecha", pedidoCliente.get("PagadoFecha"));
			    json.put("canceladoFecha", pedidoCliente.get("CanceladoFecha"));
			    json.put("pagado", pedidoCliente.get("Pagado"));
			    json.put("cancelado", pedidoCliente.get("Cancelado"));
			    json.put("correoCliente", pedidoCliente.get("CorreoCliente"));
			    json.put("pedidoDetalle", pedidoDetalle);
				return json;
				
			}
			 @PostMapping("/RegistraOV")
			 @ResponseBody
			    public int setRegistraOV(@RequestBody body2 body) {
			        SimpleDateFormat print = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			        Date inicio=body.getFechaInicio();
			        Date fin=body.getFechaFin();
				 	
			    	String body2 = "{\n"
			    			+ "\"IDCliente\":"+body.getIDCliente()+",\n"
			    			+ "\"IDClub\":"+body.getIDClub()+",\n"
			    			+ "\"Cantidad\":"+body.getCantidad()+",\n"
			    			+ "\"IDProductoServicio\":"+body.getIDProductoServicio()+",\n"
			    			+ "\"Observaciones\":\""+body.getObservaciones()+"\",\n"
			    			+ "\"DescuentoPorciento\":"+body.getDescuentoPorciento()+",\n"
			    			+ "\"FechaInicio\":\""+ print.format(inicio)+"\",\n"
			    			+ "\"FechaFin\":\""+ print.format(fin)+"\",\n"
			    			+ "\"CobroProporcional\":"+body.getCobroProporcional()+",\n"
			    			+ "\"Token\":\"77D5BDD4-1FEE-4A47-86A0-1E7D19EE1C74\"\n"
			    			+ "}";
			    	configuracion o = configuracionService.findByServiceName("RegistraOV").get();
			    	return Integer.parseInt(e.conectaApiClubPOST(body2,o.getEndpointAlpha()));
			    }
		@PostMapping("/reporte")
		@ResponseBody
		public ResponseEntity<?> getReporte(@RequestBody Body body){
			Connection conn = null;
          	ArrayList<Reporte> listaReporte = new ArrayList<Reporte>();
	        try {
	            // Carga el driver de oracle
	        	DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
	            

	        	
	            conn = DriverManager.getConnection(dbURL, userData, passData);
	            
	            PreparedStatement ps=conn.prepareStatement("EXEC DataFlowAlpha.dbo.sp_Consulta_Registros_Sports_Plaza ?,?,?");
	            ps.setInt(1, body.getIdClub());
	            ps.setDate(2, body.getFechaInicio());
	            ps.setDate(3, body.getFechaFin());
              	ResultSet rs =ps.executeQuery();
                while (rs.next()) {
                	
                    Reporte to=new Reporte();
                    to.setIdCliente(rs.getInt(1));
                    to.setCliente(rs.getString(2)) ;
                    to.setIdEmpresa(rs.getInt(3));
                    to.setIdContacto(rs.getInt(4));
                    to.setContacto(rs.getString(5));
                    to.setRazonSocial(rs.getString(6));
                    to.setDeudor(rs.getString(7));
                    to.setIdTitular(rs.getInt(8));
                    to.setIdEstatus(rs.getInt(9));
                    to.setEstatus(rs.getString(10));
                    to.setIdClienteTipo(rs.getInt(11));
                    to.setClienteTipo(rs.getString(12));
                    to.setIdClienteGrupo(rs.getInt(13));
                    to.setClienteGrupo(rs.getString(14));
                    to.setTitular(rs.getBoolean(15));
                    to.setTitularSN(rs.getString(16));
                    to.setIdNivel0(rs.getInt(17));
                    to.setIdNivel1(rs.getInt(18));
                    to.setIdNivel2(rs.getInt(19));
                    to.setIdNivel3(rs.getInt(20));
                    to.setIdNivel4(rs.getInt(21));
                    to.setNombreDelTitular(rs.getString(22));
                    to.setCargo(rs.getFloat(23));
                    to.setAbono(rs.getFloat(24));
                    to.setSaldo(rs.getFloat(25));
                    to.setIdGrupoEmpresarial(rs.getInt(26));
                    to.setCodMoneda(rs.getString(27));
                    to.setIdVendedorAsignado(rs.getInt(28));
                    to.setEmpleado(rs.getString(29));
                    to.setIniciales(rs.getString(30));
                    to.setIdAdicionalTipo(rs.getInt(31));
                    to.setAdicionalTipo(rs.getString(32));
                    to.setMembresia(rs.getLong(33));
                    to.setCuotaTipo(rs.getString(34));
                    to.setIdClienteCategoria(rs.getInt(35));
                    to.setIdMembresiaTipo(rs.getInt(36));
                    to.setMembresiaTipo(rs.getString(37));
                    to.setClienteCategoria(rs.getString(38));
                    to.setHuella(rs.getInt(39));
                    to.setIdCapturo(rs.getInt(40));
                    to.setIdCapturoFecha(rs.getDate(41));
                    to.setCapturo(rs.getString(42));
                    to.setMembresiaEmpresarial(rs.getBoolean(43));
                    to.setIdEmpresaTitular(rs.getInt(44));
                    to.setIdClienteSector(rs.getInt(45));
                    to.setIdClienteSubgrupo(rs.getInt(46));
                    to.setIdClienteGiro(rs.getInt(47));
                    to.setClienteSector(rs.getString(48));
                    to.setClienteSubgrupo(rs.getString(49));
                    to.setClienteGiro(rs.getString(50));
                    to.setIdEstatusCobranza(rs.getInt(51));
                    to.setIdEstatuMembresia(rs.getInt(52));
                    to.setEstatusCobranza(rs.getString(53));
                    to.setEstatusMembresia(rs.getString(54));
                    to.setFechaNacimiento(rs.getDate(55));
                    to.setMesNacimiento(rs.getInt(56));
                    to.setDiaNacimiento(rs.getInt(57));
                    to.setAnioNacimiento(rs.getInt(58));
                    to.setEdad(rs.getInt(59));
                    to.setIdSexo(rs.getInt(60));
                    to.setIdEstadoCivil(rs.getInt(61));
                    to.setEstadoCivil(rs.getString(62));
                    to.setSexo(rs.getString(63));
                    to.setMembresiaAsociada(rs.getString(64));
                    to.setInicioDeActividades(rs.getDate(65));
                    to.setIdEstatusAccceso(rs.getInt(66));
                    to.setEstatusAcceso(rs.getString(67));
                    to.setEmpresa(rs.getString(68));
                    to.setNivel1(rs.getString(69));
                    to.setEmail(rs.getString(70));
                    to.setTelefonoCasa(rs.getString(71));
                    to.setTelefonoMovil(rs.getString(72));
                    to.setTelefonoOficina(rs.getString(73));
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
	        ZoneId timeZone = ZoneId.systemDefault();
	        Date date=new Date();
	        LocalDate getLocalDate = date.toInstant().atZone(timeZone).toLocalDate();
	        EstadoDeCobranza enero=acumuladoService.getByMesAndClubAndYear("Enero",clubService.findById(body.getIdClub()).getNombre(),getLocalDate.getYear());
	        EstadoDeCobranza febrero=acumuladoService.getByMesAndClubAndYear("Febrero",clubService.findById(body.getIdClub()).getNombre(),getLocalDate.getYear());
	        EstadoDeCobranza marzo=acumuladoService.getByMesAndClubAndYear("Marzo",clubService.findById(body.getIdClub()).getNombre(),getLocalDate.getYear());
	        EstadoDeCobranza abril=acumuladoService.getByMesAndClubAndYear("Abril",clubService.findById(body.getIdClub()).getNombre(),getLocalDate.getYear());
	        EstadoDeCobranza mayo=acumuladoService.getByMesAndClubAndYear("Mayo",clubService.findById(body.getIdClub()).getNombre(),getLocalDate.getYear());
	        EstadoDeCobranza junio=acumuladoService.getByMesAndClubAndYear("Junio",clubService.findById(body.getIdClub()).getNombre(),getLocalDate.getYear());
	        EstadoDeCobranza julio=acumuladoService.getByMesAndClubAndYear("Julio",clubService.findById(body.getIdClub()).getNombre(),getLocalDate.getYear());
	        EstadoDeCobranza agosto=acumuladoService.getByMesAndClubAndYear("Agosto",clubService.findById(body.getIdClub()).getNombre(),getLocalDate.getYear());
	        EstadoDeCobranza septiembre=acumuladoService.getByMesAndClubAndYear("Septiembre",clubService.findById(body.getIdClub()).getNombre(),getLocalDate.getYear());
	        EstadoDeCobranza octubre=acumuladoService.getByMesAndClubAndYear("Octubre",clubService.findById(body.getIdClub()).getNombre(),getLocalDate.getYear());
	        EstadoDeCobranza noviembre=acumuladoService.getByMesAndClubAndYear("Noviembre",clubService.findById(body.getIdClub()).getNombre(),getLocalDate.getYear());
	        EstadoDeCobranza diciembre=acumuladoService.getByMesAndClubAndYear("Diciembre",clubService.findById(body.getIdClub()).getNombre(),getLocalDate.getYear());

	        int[][][] acumulado=new int[12][5][2];
	        for(int i=0;i<listaReporte.size();i++) {
	        	Date fecha=listaReporte.get(i).getInicioDeActividades();
	        	Calendar cal = Calendar.getInstance();
	        	cal.setTime(fecha);
	        	int month = cal.get(Calendar.MONTH);
	        	switch(month) {
	        		case 0:
	        			switch(listaReporte.get(i).getIdEstatusCobranza()) {
		        			case 1:
			        			if(listaReporte.get(i).isTitular()) {
			        				acumulado[0][0][0]++;
			        			}else {
			        				acumulado[0][0][1]++;			        				
			        			}
			        			break;
		        			case 2:
		        				if(listaReporte.get(i).isTitular()) {
			        				acumulado[0][1][0]++;
			        			}else {
			        				acumulado[0][1][1]++;			        				
			        			}
			        			break;
		        			case 3:
		        				if(listaReporte.get(i).isTitular()) {
			        				acumulado[0][2][0]++;
			        			}else {
			        				acumulado[0][2][1]++;			        				
			        			}
			        			break;
		        			case 4:
		        				if(listaReporte.get(i).isTitular()) {
			        				acumulado[0][3][0]++;
			        			}else {
			        				acumulado[0][3][1]++;			        				
			        			}
			        			break;
			        		case 6:
		        				if(listaReporte.get(i).isTitular()) {
			        				acumulado[0][4][0]++;
			        			}else {
			        				acumulado[0][4][1]++;			        				
			        			}
			        			break;
	        			}
	        			break;
	        		case 1:
	        			switch(listaReporte.get(i).getIdEstatusCobranza()) {
	        			case 1:
		        			if(listaReporte.get(i).isTitular()) {
		        				acumulado[1][0][0]++;
		        			}else {
		        				acumulado[1][0][1]++;			        				
		        			}
		        			break;
	        			case 2:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[1][1][0]++;
		        			}else {
		        				acumulado[1][1][1]++;			        				
		        			}
		        			break;
	        			case 3:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[1][2][0]++;
		        			}else {
		        				acumulado[1][2][1]++;			        				
		        			}
		        			break;
	        			case 4:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[1][3][0]++;
		        			}else {
		        				acumulado[1][3][1]++;			        				
		        			}
		        			break;
	        			case 6:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[1][4][0]++;
		        			}else {
		        				acumulado[1][4][1]++;			        				
		        			}
		        			break;
        			}
        			break;
	        		case 2:
	        			switch(listaReporte.get(i).getIdEstatusCobranza()) {
	        			case 1:
		        			if(listaReporte.get(i).isTitular()) {
		        				acumulado[2][0][0]++;
		        			}else {
		        				acumulado[2][0][1]++;			        				
		        			}
		        			break;
	        			case 2:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[2][1][0]++;
		        			}else {
		        				acumulado[2][1][1]++;			        				
		        			}
		        			break;
	        			case 3:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[2][2][0]++;
		        			}else {
		        				acumulado[2][2][1]++;			        				
		        			}
		        			break;
	        			case 4:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[2][3][0]++;
		        			}else {
		        				acumulado[2][3][1]++;			        				
		        			}
		        			break;
	        			case 6:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[2][4][0]++;
		        			}else {
		        				acumulado[2][4][1]++;			        				
		        			}
		        			break;
        			}
        			break;
	        		case 3:
	        			switch(listaReporte.get(i).getIdEstatusCobranza()) {
	        			case 1:
		        			if(listaReporte.get(i).isTitular()) {
		        				acumulado[3][0][0]++;
		        			}else {
		        				acumulado[3][0][1]++;			        				
		        			}
		        			break;
	        			case 2:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[3][1][0]++;
		        			}else {
		        				acumulado[3][1][1]++;			        				
		        			}
		        			break;
	        			case 3:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[3][2][0]++;
		        			}else {
		        				acumulado[3][2][1]++;			        				
		        			}
		        			break;
	        			case 4:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[3][3][0]++;
		        			}else {
		        				acumulado[3][3][1]++;			        				
		        			}
		        			break;
	        			case 6:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[3][4][0]++;
		        			}else {
		        				acumulado[3][4][1]++;			        				
		        			}
		        			break;
        			}
        			break;
	        		case 4:
	        			switch(listaReporte.get(i).getIdEstatusCobranza()) {
	        			case 1:
		        			if(listaReporte.get(i).isTitular()) {
		        				acumulado[4][0][0]++;
		        			}else {
		        				acumulado[4][0][1]++;			        				
		        			}
		        			break;
	        			case 2:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[4][1][0]++;
		        			}else {
		        				acumulado[4][1][1]++;			        				
		        			}
		        			break;
	        			case 3:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[4][2][0]++;
		        			}else {
		        				acumulado[4][2][1]++;			        				
		        			}
		        			break;
	        			case 4:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[4][3][0]++;
		        			}else {
		        				acumulado[4][3][1]++;			        				
		        			}
		        			break;
	        			case 6:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[4][4][0]++;
		        			}else {
		        				acumulado[4][4][1]++;			        				
		        			}
		        			break;
        			}
        			break;
	        		case 5:
	        			switch(listaReporte.get(i).getIdEstatusCobranza()) {
	        			case 1:
		        			if(listaReporte.get(i).isTitular()) {
		        				acumulado[5][0][0]++;
		        			}else {
		        				acumulado[5][0][1]++;			        				
		        			}
		        			break;
	        			case 2:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[5][1][0]++;
		        			}else {
		        				acumulado[5][1][1]++;			        				
		        			}
		        			break;
	        			case 3:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[5][2][0]++;
		        			}else {
		        				acumulado[5][2][1]++;			        				
		        			}
		        			break;
	        			case 4:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[5][3][0]++;
		        			}else {
		        				acumulado[5][3][1]++;			        				
		        			}
		        			break;
	        			case 6:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[5][4][0]++;
		        			}else {
		        				acumulado[5][4][1]++;			        				
		        			}
		        			break;
        			}
        			break;
	        		case 6:
	        			switch(listaReporte.get(i).getIdEstatusCobranza()) {
	        			case 1:
		        			if(listaReporte.get(i).isTitular()) {
		        				acumulado[6][0][0]++;
		        			}else {
		        				acumulado[6][0][1]++;			        				
		        			}
		        			break;
	        			case 2:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[6][1][0]++;
		        			}else {
		        				acumulado[6][1][1]++;			        				
		        			}
		        			break;
	        			case 3:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[6][2][0]++;
		        			}else {
		        				acumulado[6][2][1]++;			        				
		        			}
		        			break;
	        			case 4:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[6][3][0]++;
		        			}else {
		        				acumulado[6][3][1]++;			        				
		        			}
		        			break;
	        			case 6:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[6][4][0]++;
		        			}else {
		        				acumulado[6][4][1]++;			        				
		        			}
		        			break;
        			}
        			break;
	        		case 7:
	        			switch(listaReporte.get(i).getIdEstatusCobranza()) {
	        			case 1:
		        			if(listaReporte.get(i).isTitular()) {
		        				acumulado[7][0][0]++;
		        			}else {
		        				acumulado[7][0][1]++;			        				
		        			}
		        			break;
	        			case 2:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[7][1][0]++;
		        			}else {
		        				acumulado[7][1][1]++;			        				
		        			}
		        			break;
	        			case 3:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[7][2][0]++;
		        			}else {
		        				acumulado[7][2][1]++;			        				
		        			}
		        			break;
	        			case 4:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[7][3][0]++;
		        			}else {
		        				acumulado[7][3][1]++;			        				
		        			}
		        			break;
	        			case 6:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[7][4][0]++;
		        			}else {
		        				acumulado[7][4][1]++;			        				
		        			}
		        			break;
        			}
        			break;
	        		case 8:
	        			switch(listaReporte.get(i).getIdEstatusCobranza()) {
	        			case 1:
		        			if(listaReporte.get(i).isTitular()) {
		        				acumulado[8][0][0]++;
		        			}else {
		        				acumulado[8][0][1]++;			        				
		        			}
		        			break;
	        			case 2:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[8][1][0]++;
		        			}else {
		        				acumulado[8][1][1]++;			        				
		        			}
		        			break;
	        			case 3:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[8][2][0]++;
		        			}else {
		        				acumulado[8][2][1]++;			        				
		        			}
		        			break;
	        			case 4:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[8][3][0]++;
		        			}else {
		        				acumulado[8][3][1]++;			        				
		        			}
		        			break;
	        			case 6:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[8][4][0]++;
		        			}else {
		        				acumulado[8][4][1]++;			        				
		        			}
		        			break;
        			}
        			break;
	        		case 9:
	        			switch(listaReporte.get(i).getIdEstatusCobranza()) {
	        			case 1:
		        			if(listaReporte.get(i).isTitular()) {
		        				acumulado[9][0][0]++;
		        			}else {
		        				acumulado[9][0][1]++;			        				
		        			}
		        			break;
	        			case 2:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[9][1][0]++;
		        			}else {
		        				acumulado[9][1][1]++;			        				
		        			}
		        			break;
	        			case 3:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[9][2][0]++;
		        			}else {
		        				acumulado[9][2][1]++;			        				
		        			}
		        			break;
	        			case 4:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[9][3][0]++;
		        			}else {
		        				acumulado[9][3][1]++;			        				
		        			}
		        			break;
	        			case 6:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[9][4][0]++;
		        			}else {
		        				acumulado[9][4][1]++;			        				
		        			}
		        			break;
        			}
        			break;
	        		case 10:
	        			switch(listaReporte.get(i).getIdEstatusCobranza()) {
	        			case 1:
		        			if(listaReporte.get(i).isTitular()) {
		        				acumulado[10][0][0]++;
		        			}else {
		        				acumulado[10][0][1]++;			        				
		        			}
		        			break;
	        			case 2:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[10][1][0]++;
		        			}else {
		        				acumulado[10][1][1]++;			        				
		        			}
		        			break;
	        			case 3:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[10][2][0]++;
		        			}else {
		        				acumulado[10][2][1]++;			        				
		        			}
		        			break;
	        			case 4:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[10][3][0]++;
		        			}else {
		        				acumulado[10][3][1]++;			        				
		        			}
		        			break;
	        			case 6:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[10][4][0]++;
		        			}else {
		        				acumulado[10][4][1]++;			        				
		        			}
		        			break;
        			}
        			break;
	        		case 11:
	        			switch(listaReporte.get(i).getIdEstatusCobranza()) {
	        			case 1:
		        			if(listaReporte.get(i).isTitular()) {
		        				acumulado[11][0][0]++;
		        			}else {
		        				acumulado[11][0][1]++;			        				
		        			}
		        			break;
	        			case 2:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[11][1][0]++;
		        			}else {
		        				acumulado[11][1][1]++;			        				
		        			}
		        			break;
	        			case 3:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[11][2][0]++;
		        			}else {
		        				acumulado[11][2][1]++;			        				
		        			}
		        			break;
	        			case 4:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[11][3][0]++;
		        			}else {
		        				acumulado[11][3][1]++;			        				
		        			}
		        			break;
	        			case 6:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[11][4][0]++;
		        			}else {
		        				acumulado[11][4][1]++;			        				
		        			}
		        			break;
        			}
        			break;
	        	
	        	}
	        }
	        enero.setCorrienteDependiente(acumulado[0][0][1]);
	        enero.setCorrienteTitular(acumulado[0][0][0]);
	        enero.setEtapa1Dependiente(acumulado[0][1][1]);
	        enero.setEtapa1Titular(acumulado[0][1][0]);
	        enero.setEtapa2Dependiente(acumulado[0][2][1]);
	        enero.setEtapa2Titular(acumulado[0][2][0]);
	        enero.setEtapa3Dependiente(acumulado[0][3][1]);
	        enero.setEtapa3Titular(acumulado[0][3][0]);
	        enero.setEtapa4Dependiente(acumulado[0][4][1]);
	        enero.setEtapa4Titular(acumulado[0][4][0]);
	        
	        febrero.setCorrienteDependiente(acumulado[1][0][1]);
	        febrero.setCorrienteTitular(acumulado[1][0][0]);
	        febrero.setEtapa1Dependiente(acumulado[1][1][1]);
	        febrero.setEtapa1Titular(acumulado[1][1][0]);
	        febrero.setEtapa2Dependiente(acumulado[1][2][1]);
	        febrero.setEtapa2Titular(acumulado[1][2][0]);
	        febrero.setEtapa3Dependiente(acumulado[1][3][1]);
	        febrero.setEtapa3Titular(acumulado[1][3][0]);
	        febrero.setEtapa4Dependiente(acumulado[1][4][1]);
	        febrero.setEtapa4Titular(acumulado[1][4][0]);
	        
	        marzo.setCorrienteDependiente(acumulado[2][0][1]);
	        marzo.setCorrienteTitular(acumulado[2][0][0]);
	        marzo.setEtapa1Dependiente(acumulado[2][1][1]);
	        marzo.setEtapa1Titular(acumulado[2][1][0]);
	        marzo.setEtapa2Dependiente(acumulado[2][2][1]);
	        marzo.setEtapa2Titular(acumulado[2][2][0]);
	        marzo.setEtapa3Dependiente(acumulado[2][3][1]);
	        marzo.setEtapa3Titular(acumulado[2][3][0]);
	        marzo.setEtapa4Dependiente(acumulado[2][4][1]);
	        marzo.setEtapa4Titular(acumulado[2][4][0]);
	        
	        abril.setCorrienteDependiente(acumulado[3][0][1]);
	        abril.setCorrienteTitular(acumulado[3][0][0]);
	        abril.setEtapa1Dependiente(acumulado[3][1][1]);
	        abril.setEtapa1Titular(acumulado[3][1][0]);
	        abril.setEtapa2Dependiente(acumulado[3][2][1]);
	        abril.setEtapa2Titular(acumulado[3][2][0]);
	        abril.setEtapa3Dependiente(acumulado[3][3][1]);
	        abril.setEtapa3Titular(acumulado[3][3][0]);
	        abril.setEtapa4Dependiente(acumulado[3][4][1]);
	        abril.setEtapa4Titular(acumulado[3][4][0]);
	        
	        mayo.setCorrienteDependiente(acumulado[4][0][1]);
	        mayo.setCorrienteTitular(acumulado[4][0][0]);
	        mayo.setEtapa1Dependiente(acumulado[4][1][1]);
	        mayo.setEtapa1Titular(acumulado[4][1][0]);
	        mayo.setEtapa2Dependiente(acumulado[4][2][1]);
	        mayo.setEtapa2Titular(acumulado[4][2][0]);
	        mayo.setEtapa3Dependiente(acumulado[4][3][1]);
	        mayo.setEtapa3Titular(acumulado[4][3][0]);
	        mayo.setEtapa4Dependiente(acumulado[4][4][1]);
	        mayo.setEtapa4Titular(acumulado[4][4][0]);
	        
	        junio.setCorrienteDependiente(acumulado[5][0][1]);
	        junio.setCorrienteTitular(acumulado[5][0][0]);
	        junio.setEtapa1Dependiente(acumulado[5][1][1]);
	        junio.setEtapa1Titular(acumulado[5][1][0]);
	        junio.setEtapa2Dependiente(acumulado[5][2][1]);
	        junio.setEtapa2Titular(acumulado[5][2][0]);
	        junio.setEtapa3Dependiente(acumulado[5][3][1]);
	        junio.setEtapa3Titular(acumulado[5][3][0]);
	        junio.setEtapa4Dependiente(acumulado[5][4][1]);
	        junio.setEtapa4Titular(acumulado[5][4][0]);
	        
	        julio.setCorrienteDependiente(acumulado[6][0][1]);
	        julio.setCorrienteTitular(acumulado[6][0][0]);
	        julio.setEtapa1Dependiente(acumulado[6][1][1]);
	        julio.setEtapa1Titular(acumulado[6][1][0]);
	        julio.setEtapa2Dependiente(acumulado[6][2][1]);
	        julio.setEtapa2Titular(acumulado[6][2][0]);
	        julio.setEtapa3Dependiente(acumulado[6][3][1]);
	        julio.setEtapa3Titular(acumulado[6][3][0]);
	        julio.setEtapa4Dependiente(acumulado[6][4][1]);
	        julio.setEtapa4Titular(acumulado[6][4][0]);
	        
	        agosto.setCorrienteDependiente(acumulado[7][0][1]);
	        agosto.setCorrienteTitular(acumulado[7][0][0]);
	        agosto.setEtapa1Dependiente(acumulado[7][1][1]);
	        agosto.setEtapa1Titular(acumulado[7][1][0]);
	        agosto.setEtapa2Dependiente(acumulado[7][2][1]);
	        agosto.setEtapa2Titular(acumulado[7][2][0]);
	        agosto.setEtapa3Dependiente(acumulado[7][3][1]);
	        agosto.setEtapa3Titular(acumulado[7][3][0]);
	        agosto.setEtapa4Dependiente(acumulado[7][4][1]);
	        agosto.setEtapa4Titular(acumulado[7][4][0]);
	        
	        septiembre.setCorrienteDependiente(acumulado[8][0][1]);
	        septiembre.setCorrienteTitular(acumulado[8][0][0]);
	        septiembre.setEtapa1Dependiente(acumulado[8][1][1]);
	        septiembre.setEtapa1Titular(acumulado[8][1][0]);
	        septiembre.setEtapa2Dependiente(acumulado[8][2][1]);
	        septiembre.setEtapa2Titular(acumulado[8][2][0]);
	        septiembre.setEtapa3Dependiente(acumulado[8][3][1]);
	        septiembre.setEtapa3Titular(acumulado[8][3][0]);
	        septiembre.setEtapa4Dependiente(acumulado[8][4][1]);
	        septiembre.setEtapa4Titular(acumulado[8][4][0]);
	        
	        octubre.setCorrienteDependiente(acumulado[9][0][1]);
	        octubre.setCorrienteTitular(acumulado[9][0][0]);
	        octubre.setEtapa1Dependiente(acumulado[9][1][1]);
	        octubre.setEtapa1Titular(acumulado[9][1][0]);
	        octubre.setEtapa2Dependiente(acumulado[9][2][1]);
	        octubre.setEtapa2Titular(acumulado[9][2][0]);
	        octubre.setEtapa3Dependiente(acumulado[9][3][1]);
	        octubre.setEtapa3Titular(acumulado[9][3][0]);
	        octubre.setEtapa4Dependiente(acumulado[9][4][1]);
	        octubre.setEtapa4Titular(acumulado[9][4][0]);
	        
	        noviembre.setCorrienteDependiente(acumulado[10][0][1]);
	        noviembre.setCorrienteTitular(acumulado[10][0][0]);
	        noviembre.setEtapa1Dependiente(acumulado[10][1][1]);
	        noviembre.setEtapa1Titular(acumulado[10][1][0]);
	        noviembre.setEtapa2Dependiente(acumulado[10][2][1]);
	        noviembre.setEtapa2Titular(acumulado[10][2][0]);
	        noviembre.setEtapa3Dependiente(acumulado[10][3][1]);
	        noviembre.setEtapa3Titular(acumulado[10][3][0]);
	        noviembre.setEtapa4Dependiente(acumulado[10][4][1]);
	        noviembre.setEtapa4Titular(acumulado[10][4][0]);
	        
	        diciembre.setCorrienteDependiente(acumulado[11][0][1]);
	        diciembre.setCorrienteTitular(acumulado[11][0][0]);
	        diciembre.setEtapa1Dependiente(acumulado[11][1][1]);
	        diciembre.setEtapa1Titular(acumulado[11][1][0]);
	        diciembre.setEtapa2Dependiente(acumulado[11][2][1]);
	        diciembre.setEtapa2Titular(acumulado[11][2][0]);
	        diciembre.setEtapa3Dependiente(acumulado[11][3][1]);
	        diciembre.setEtapa3Titular(acumulado[11][3][0]);
	        diciembre.setEtapa4Dependiente(acumulado[11][4][1]);
	        diciembre.setEtapa4Titular(acumulado[11][4][0]);
	        
	        enero.setConsolidadoDependiente(enero.getCorrienteDependiente()+enero.getEtapa1Dependiente()+enero.getEtapa2Dependiente()+enero.getEtapa3Dependiente());
	        enero.setConsolidadoTitular(enero.getCorrienteTitular()+enero.getEtapa1Titular()+enero.getEtapa2Titular()+enero.getEtapa3Titular());
	        
	        febrero.setConsolidadoDependiente(febrero.getCorrienteDependiente()+febrero.getEtapa1Dependiente()+febrero.getEtapa2Dependiente()+febrero.getEtapa3Dependiente());
	        febrero.setConsolidadoTitular(febrero.getCorrienteTitular()+febrero.getEtapa1Titular()+febrero.getEtapa2Titular()+febrero.getEtapa3Titular());

	        marzo.setConsolidadoDependiente(marzo.getCorrienteDependiente()+marzo.getEtapa1Dependiente()+marzo.getEtapa2Dependiente()+marzo.getEtapa3Dependiente());
	        marzo.setConsolidadoTitular(marzo.getCorrienteTitular()+marzo.getEtapa1Titular()+marzo.getEtapa2Titular()+marzo.getEtapa3Titular());
	        
	        abril.setConsolidadoDependiente(abril.getCorrienteDependiente()+abril.getEtapa1Dependiente()+abril.getEtapa2Dependiente()+abril.getEtapa3Dependiente());
	        abril.setConsolidadoTitular(abril.getCorrienteTitular()+abril.getEtapa1Titular()+abril.getEtapa2Titular()+abril.getEtapa3Titular());
	        
	        mayo.setConsolidadoDependiente(mayo.getCorrienteDependiente()+mayo.getEtapa1Dependiente()+mayo.getEtapa2Dependiente()+mayo.getEtapa3Dependiente());
	        mayo.setConsolidadoTitular(mayo.getCorrienteTitular()+mayo.getEtapa1Titular()+mayo.getEtapa2Titular()+mayo.getEtapa3Titular());
	        
	        junio.setConsolidadoDependiente(junio.getCorrienteDependiente()+junio.getEtapa1Dependiente()+junio.getEtapa2Dependiente()+junio.getEtapa3Dependiente());
	        junio.setConsolidadoTitular(junio.getCorrienteTitular()+junio.getEtapa1Titular()+junio.getEtapa2Titular()+junio.getEtapa3Titular());
	        
	        julio.setConsolidadoDependiente(julio.getCorrienteDependiente()+julio.getEtapa1Dependiente()+julio.getEtapa2Dependiente()+julio.getEtapa3Dependiente());
	        julio.setConsolidadoTitular(julio.getCorrienteTitular()+julio.getEtapa1Titular()+julio.getEtapa2Titular()+julio.getEtapa3Titular());
	        
	        agosto.setConsolidadoDependiente(agosto.getCorrienteDependiente()+agosto.getEtapa1Dependiente()+agosto.getEtapa2Dependiente()+agosto.getEtapa3Dependiente());
	        agosto.setConsolidadoTitular(agosto.getCorrienteTitular()+agosto.getEtapa1Titular()+agosto.getEtapa2Titular()+agosto.getEtapa3Titular());
	        
	        septiembre.setConsolidadoDependiente(septiembre.getCorrienteDependiente()+septiembre.getEtapa1Dependiente()+septiembre.getEtapa2Dependiente()+septiembre.getEtapa3Dependiente());
	        septiembre.setConsolidadoTitular(septiembre.getCorrienteTitular()+septiembre.getEtapa1Titular()+septiembre.getEtapa2Titular()+septiembre.getEtapa3Titular());
	        
	        octubre.setConsolidadoDependiente(octubre.getCorrienteDependiente()+octubre.getEtapa1Dependiente()+octubre.getEtapa2Dependiente()+octubre.getEtapa3Dependiente());
	        octubre.setConsolidadoTitular(octubre.getCorrienteTitular()+octubre.getEtapa1Titular()+octubre.getEtapa2Titular()+octubre.getEtapa3Titular());
	        
	        noviembre.setConsolidadoDependiente(noviembre.getCorrienteDependiente()+noviembre.getEtapa1Dependiente()+noviembre.getEtapa2Dependiente()+noviembre.getEtapa3Dependiente());
	        noviembre.setConsolidadoTitular(noviembre.getCorrienteTitular()+noviembre.getEtapa1Titular()+noviembre.getEtapa2Titular()+noviembre.getEtapa3Titular());
	        
	        diciembre.setConsolidadoDependiente(diciembre.getCorrienteDependiente()+diciembre.getEtapa1Dependiente()+diciembre.getEtapa2Dependiente()+diciembre.getEtapa3Dependiente());
	        diciembre.setConsolidadoTitular(diciembre.getCorrienteTitular()+diciembre.getEtapa1Titular()+diciembre.getEtapa2Titular()+diciembre.getEtapa3Titular());
	        
	        
	        
	        
	        acumuladoService.save(enero);
	        acumuladoService.save(febrero);
	        acumuladoService.save(marzo);
	        acumuladoService.save(abril);
	        acumuladoService.save(mayo);
	        acumuladoService.save(junio);
	        acumuladoService.save(julio);
	        acumuladoService.save(agosto);
	        acumuladoService.save(septiembre);
	        acumuladoService.save(octubre);
	        acumuladoService.save(noviembre);
	        acumuladoService.save(diciembre);
	      
			return new ResponseEntity<>(listaReporte, HttpStatus.OK);
		}
		@PostMapping("/reporte2")
		@ResponseBody
		public ResponseEntity<?> getReporte2(@RequestBody Body body){
			Connection conn = null;
          	ArrayList<Reporte2> listaReporte = new ArrayList<Reporte2>();
	        try {
	            // Carga el driver de oracle
	        	DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
	            

	        	
	            conn = DriverManager.getConnection(dbURL, userData, passData);
	            
	            PreparedStatement ps=conn.prepareStatement("EXEC DataFlowAlpha.dbo.sp_Consulta_Usuarios_Club ?,?");
	            ps.setInt(1,body.getIdClub());
	            ps.setInt(2,body.getMes() ); 
              	ResultSet rs =ps.executeQuery();
                while (rs.next()) {
                	Reporte2 reporte=new Reporte2();
                	reporte.setIdMembresiaPorPeriodo(rs.getLong(1));
                	reporte.setIdCliente(rs.getInt(2));
                	reporte.setIdPeriodo(rs.getInt(3));
                	reporte.setIdPeriodoMes(rs.getInt(4));
                	reporte.setIdEstatus(rs.getInt(5));
                	reporte.setCliente(rs.getString(6));
                	reporte.setIdMembresiaTipo(rs.getInt(7));
                	reporte.setTitular(rs.getBoolean(8));
                	reporte.setIdTitular(rs.getInt(9));
                	reporte.setIdNivel0(rs.getInt(10));
                	reporte.setIdNivel1(rs.getInt(11));
                	reporte.setIdClienteSector(rs.getInt(12));
                	reporte.setIdClienteGrupo(rs.getInt(13));
                	reporte.setIdClienteSubgrupo(rs.getInt(14));
                	reporte.setIdClienteGiro(rs.getInt(15));
                	reporte.setIdClienteCategoria(rs.getInt(16));
                	reporte.setMembresia(rs.getLong(17));
                	reporte.setIdEmpresaTitular(rs.getInt(18));
                	reporte.setIdEstatusCobranza(rs.getInt(19));
                	reporte.setIdEstatusMembresia(rs.getInt(20));
                	reporte.setIdEstatusAcceso(rs.getInt(21));
                	reporte.setInicioDeActividades(rs.getDate(22));
                	reporte.setObservaciones(rs.getString(23));
                	reporte.setPeriodo(rs.getInt(24));
                	reporte.setIdMes(rs.getInt(25));
                	reporte.setMes(rs.getString(26));
                	reporte.setNombreComercial(rs.getString(27));
                	reporte.setNivel1(rs.getString(28));
                	reporte.setTitularAdicional(rs.getString(29));
                	reporte.setMembresiaTipo(rs.getString(30));
                	reporte.setEstatus(rs.getString(31));
                	reporte.setEstatusCobranza(rs.getString(32));
                	reporte.setEstatusAcceso(rs.getString(33));
                	reporte.setEstatusMembresia(rs.getString(34));
                	reporte.setClienteSector(rs.getString(35));
                	reporte.setClienteGrupo(rs.getString(36));
                	reporte.setClienteSubgrupo(rs.getString(37));
                	reporte.setClienteGiro(rs.getString(38));
                	reporte.setIdClienteTipo(rs.getInt(39));
                	reporte.setClienteCategoria(rs.getString(40));
                	reporte.setClienteTipo(rs.getString(41));
                	reporte.setMembresiaAsociada(rs.getString(42));
                    listaReporte.add(reporte);
                }
	        }catch (SQLException ex) {
	            System.out.println("Error: " + ex.getMessage());
	            ex.printStackTrace();
	        } finally {
	            try {
	            	conn.close();
	            } catch (SQLException ex) {
	                System.out.println("Error: " + ex.getMessage());
	            }
	        }
	        System.out.println("mes "+listaReporte.get(0).getMes());

	        System.out.println("club "+listaReporte.get(0).getNivel1());	        

	        System.out.println("año "+listaReporte.get(0).getPeriodo());
	        EstadoDeCobranza mesConsultado=acumuladoService.getByMesAndClubAndYear(listaReporte.get(0).getMes(),listaReporte.get(0).getNivel1(),listaReporte.get(0).getPeriodo());

	        int[][] acumulado=new int[5][2];
	        for(int i=0;i<listaReporte.size();i++) {
        			switch(listaReporte.get(i).getIdEstatusCobranza()) {
	        			case 1:
		        			if(listaReporte.get(i).isTitular()) {
		        				acumulado[0][0]++;
		        			}else {
		        				acumulado[0][1]++;			        				
		        			}
		        			break;
	        			case 2:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[1][0]++;
		        			}else {
		        				acumulado[1][1]++;			        				
		        			}
		        			break;
	        			case 3:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[2][0]++;
		        			}else {
		        				acumulado[2][1]++;			        				
		        			}
		        			break;
	        			case 4:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[3][0]++;
		        			}else {
		        				acumulado[3][1]++;			        				
		        			}
		        			break;
		        		case 6:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[4][0]++;
		        			}else {
		        				acumulado[4][1]++;			        				
		        			}
		        			break;
		        		case 7:
	        				if(listaReporte.get(i).isTitular()) {
		        				acumulado[0][0]++;
		        			}else {
		        				acumulado[0][1]++;			        				
		        			}
		        			break;
	        	}
	        }
	        mesConsultado.setCorrienteDependiente(acumulado[0][1]);
	        mesConsultado.setCorrienteTitular(acumulado[0][0]);
	        mesConsultado.setEtapa1Dependiente(acumulado[1][1]);
	        mesConsultado.setEtapa1Titular(acumulado[1][0]);
	        mesConsultado.setEtapa2Dependiente(acumulado[2][1]);
	        mesConsultado.setEtapa2Titular(acumulado[2][0]);
	        mesConsultado.setEtapa3Dependiente(acumulado[3][1]);
	        mesConsultado.setEtapa3Titular(acumulado[3][0]);
	        mesConsultado.setEtapa4Dependiente(acumulado[4][1]);
	        mesConsultado.setEtapa4Titular(acumulado[4][0]);
	        
	        
	        mesConsultado.setConsolidadoDependiente(mesConsultado.getCorrienteDependiente()+mesConsultado.getEtapa1Dependiente()+mesConsultado.getEtapa2Dependiente()+mesConsultado.getEtapa3Dependiente());
	        mesConsultado.setConsolidadoTitular(mesConsultado.getCorrienteTitular()+mesConsultado.getEtapa1Titular()+mesConsultado.getEtapa2Titular()+mesConsultado.getEtapa3Titular());
	        
	        
	        acumuladoService.save(mesConsultado);
	      
			return new ResponseEntity<>(listaReporte, HttpStatus.OK);
		}
			
		
		@GetMapping("/reportePromociones")
		@ResponseBody
		public ResponseEntity<?> getReportePromociones(){
			Connection conn = null;
          	ArrayList<ReportePromocion> listaReporte = new ArrayList<ReportePromocion>();
	        try {
	            // Carga el driver de oracle
	        	DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
	            

	        	
	            conn = DriverManager.getConnection(dbURL, userData, passData);
	            
	            PreparedStatement ps=conn.prepareStatement("EXEC DataFlowAlpha.dbo.sp_Reporte_Promociones ");
              	ResultSet rs =ps.executeQuery();
                while (rs.next()) {
                	
                    ReportePromocion to=new ReportePromocion();
                    to.setIdPromocion(rs.getInt(1));
                    to.setPromocion(rs.getString(2));
                    to.setConcepto(rs.getString(3));
                    to.setFechaInicio(rs.getDate(4));
                    to.setFechaFin(rs.getDate(5));
                    to.setIdCapturoFecha(rs.getTimestamp(6));
                    to.setSubImporte(rs.getFloat(7));
                    to.setIva(rs.getFloat(8));
                    to.setRecibo(rs.getString(9));
                    to.setReciboFecha(rs.getTimestamp(10));
                    to.setIdOrdenDeVenta(rs.getInt(11));
                    to.setTotal(rs.getFloat(12));
                    to.setRestante(rs.getFloat(13));
                    to.setPagado(rs.getFloat(14));
                    to.setDescripcion(rs.getString(15));
                    to.setClub(rs.getString(16));
                    to.setIdCliente(rs.getInt(17));
                    to.setCliente(rs.getString(18));
                    to.setMembresia(rs.getLong(19));
                    to.setObservaciones(rs.getString(20));
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
	      
			return new ResponseEntity<>(listaReporte, HttpStatus.OK);
		}
		
		@GetMapping("/productoByClub/{idClub}")
		@ResponseBody
		public ResponseEntity<?> getproductoByClub(@PathVariable("idClub") int idClub){
			Connection conn = null;
          	ArrayList<ProductoServicio> listaReporte = new ArrayList<ProductoServicio>();
	        try {
	            // Carga el driver de oracle
	        	DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
	            

	        	
	            conn = DriverManager.getConnection(dbURL, userData, passData);
	            
	            PreparedStatement ps=conn.prepareStatement("EXEC DataFlowAlpha.dbo.sp_Consulta_Productos_Codigo ? ");
	            ps.setInt(1, idClub);
              	ResultSet rs =ps.executeQuery();
                while (rs.next()) {
                	
                	ProductoServicio to=new ProductoServicio();
                	to.setIdProdServ(rs.getInt(1));
                	to.setClave(rs.getString(2));
                	to.setConcepto(rs.getString(3));
                	to.setTotal(rs.getFloat(4));
                	to.setMonedaTipo(rs.getString(5));
                	to.setCodigo(rs.getString(6));
                	to.setNivel1(rs.getString(7));
                	
                    
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
	        
			return new ResponseEntity<>(listaReporte, HttpStatus.OK);
		}
		
		@GetMapping("/getDia")
		 @ResponseBody
		public ResponseEntity<?> getDiaSemana(){
			Session currentSession = entityManager.unwrap(Session.class);
			Query query = currentSession.createNativeQuery("select * from dias_semana");
			List<Object[]> listResults = query.getResultList();
			JSONArray array=new JSONArray();
			for (Object[] record : listResults) {
				JSONObject json=new JSONObject();
				json.put((String) record[1], (String) record[0]);
				array.put(json);
			}

	    	return new ResponseEntity<>(array.toList(), HttpStatus.OK);
		}
		@GetMapping("/enviarCorreo")
		 @ResponseBody
		public ResponseEntity<?> enviarCorreo(){
			String asunto="Te has excedido del tiempo limite en el estacionamiento";
			Correo correo=new Correo("daniel.garcia@clubalpha.com.mx","farmacia123haloreach","bloodgigametal@gmail.com","bloodgigametal@gmail.com");
			asunto=asunto+" por segunda ocasion";
			String cabecera="segunda";
			String texto="<li>La estancia en el estacionamiento es de 4 horas por ingreso.</li>\r\n"
					+ "<li>En caso de una tercera incidencia deberá cubrir, en la caja general del club, una sanción administrativa de $65.00.</li>\r\n"
					+ "<li>En caso de una cuarta incidencia se desactivará definitivamente el Chip</li>\r\n"
					+ "<li>El chip permanecerá desactivado hasta cubrir la sanción administrativa.</li>";
			correo.enviar_correo4(asunto,63821,"Daniel García Velasco","01/03/2022 17:45","01/03/2022 21:53",texto,cabecera);
			correo.enviar_correo5(asunto,63821,"Daniel García Velasco","01/03/2022 17:45","01/03/2022 21:53");
	    	return new ResponseEntity<>("correo enviado", HttpStatus.OK);
		}
		
		@GetMapping("/recibosMes/{nombre}")
		@ResponseBody
		public ResponseEntity<?> recibosMes(@PathVariable("nombre") String nombre){
			
		    System.out.println(new Date());
		    RegistroFacturaGlobal registro=new RegistroFacturaGlobal();
		    registro.setFecha_solicitud(new Date());
		    registro.setSolicito(nombre);
		    
		    registroFacturaGlobalService.save(registro);
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		    Calendar calendar = Calendar.getInstance(); 
		    int day=calendar.get(Calendar.DAY_OF_MONTH);
		    java.sql.Date fechaInicio;
		    java.sql.Date fechaFin;
		    if(day==1 || day==2 ) {
		    	System.out.println("entre al if de la facturacion");
		    	calendar.set(Calendar.DAY_OF_MONTH,1);
		    	calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH)-1);
			    fechaInicio= new java.sql.Date(calendar.getTime().getTime());
			    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		    	calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH));
			    fechaFin= new java.sql.Date(calendar.getTime().getTime());
		    }else {
		    	calendar.set(Calendar.DAY_OF_MONTH,1);
			    fechaInicio= new java.sql.Date(calendar.getTime().getTime());
			    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			    fechaFin= new java.sql.Date(calendar.getTime().getTime());
		    }
			Connection conn = null;
          	ArrayList<ReciboSAT> listaReporte = new ArrayList<ReciboSAT>();
	        try {
	            // Carga el driver de oracle
	        	DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
	            

	        	
	            conn = DriverManager.getConnection("jdbc:sqlserver://192.168.20.12;database=DataFlowAlpha", "extranet_user", "tyGDix##dJGJ5");
	            
	            PreparedStatement ps=conn.prepareStatement("EXEC DataFlowAlpha.dbo.sp_Consulta_Recibo_Conceptos_por_Fecha_SAT_Completo ?,? ");
	            ps.setDate(1, fechaInicio);
	            ps.setDate(2, fechaFin);
              	ResultSet rs =ps.executeQuery();
                while (rs.next()) {	     
                	ReciboSAT to=new ReciboSAT();
                	to.setFechaCaptura(rs.getDate(1));
                	to.setFolio(rs.getString(2));
                	to.setIdCliente(rs.getInt(3));
                	to.setNombreCliente(rs.getString(4));
                	to.setCodigoPostal(rs.getString(6));
                	to.setRfc(rs.getString(7));
                	to.setIdVenta(rs.getInt(8));
                	to.setConcepto(rs.getString(9));
                	to.setCosto(rs.getFloat(10));
                	to.setClave(rs.getString(11));
                	to.setPrecioUnitario(rs.getFloat(12));
                	to.setPrecioUnitarioIVA(rs.getFloat(13));
                	to.setTotal(rs.getFloat(14));
                	to.setIdProducto(String.valueOf(rs.getInt(15)));
	              	
                	
                	String observaciones=rs.getString(16);
                	
                	if(to.getCosto()!=0 && to.getPrecioUnitario()!=0 && observaciones!=null) {
                		String[] observacionesSplit=observaciones.split("\\|");
                    	to.setUnidad(observacionesSplit[1]);
                    	to.setProductCode(observacionesSplit[0].trim());
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
	        }
	        try {
		        conn = DriverManager.getConnection("jdbc:sqlserver://192.168.20.12;database=globalsoft", "facturacion", "WZ155YH%a");
	            
		        PreparedStatement ps=conn.prepareStatement("exec globalsoft.dbo.sp_Consulta_Recibo_Conceptos_por_Fecha_SAT ?,? ");
	            ps.setDate(1, fechaInicio);
	            ps.setDate(2, fechaFin);
              	ResultSet rs =ps.executeQuery();
              
                while (rs.next()) {	                	 
                	ReciboSAT to=new ReciboSAT();
                	to.setFechaCaptura(rs.getDate(1));
                	to.setFolio(rs.getString(2));
                	to.setIdCliente(0);
                	to.setNombreCliente("");
                	to.setMembresia(0);
                	to.setCodigoPostal("");
                	to.setRfc("");
                	to.setIdVenta(0);
                	to.setConcepto(rs.getString(3));
                	to.setCosto(rs.getFloat(4));
                	to.setClave(rs.getString(5));
                	to.setPrecioUnitario(rs.getFloat(6));
                	to.setPrecioUnitarioIVA(rs.getFloat(7));
                	to.setTotal(rs.getFloat(8));
                	to.setCodigo(rs.getString(9));
                	to.setIdProducto(rs.getString(10));
                	to.setUnidad(rs.getString(11));
                	to.setProductCode(rs.getString(5).trim());
                	to.setUnidad(rs.getString(9));
                	if(to.getCosto()!=0 || to.getPrecioUnitario()!=0)
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
	        List<Factura>recibosFacturados=facturaService.list();
	        List<ReciboSAT> recibosSinFacturar=new ArrayList<ReciboSAT>();

			JSONObject respuesta2=new JSONObject("{}");
      		for(int i=0;i<listaReporte.size();i++) {
        		boolean ban=false;
	        	for(int j=0;j<recibosFacturados.size();j++) {
	        		if(recibosFacturados.get(j).getRecibo().equals(listaReporte.get(i).getFolio())) {
	        			ban=true;
	        		}
	        	}
	        	if(!ban) {
	        		recibosSinFacturar.add(listaReporte.get(i));
	        	}
	        }

			    Calendar fecha = Calendar.getInstance();
				int mesActual = fecha.get(Calendar.MONTH)+1;
				int yearActual = fecha.get(Calendar.YEAR);
    	        FacturaOnline factura=new FacturaOnline();
    			Receiver receiver=new Receiver();
    			GlobalInformation globalInformation=new GlobalInformation();
    			factura.setCfdiType(tipoCFDI);
    			factura.setCurrency("MXN");
    			factura.setExpeditionPlace("72430");
    			factura.setPaymentForm("01");
    			factura.setPaymentMethod("PUE");
    			Calendar ca = Calendar.getInstance();
    			Calendar caAux = Calendar.getInstance();
    			Calendar today = Calendar.getInstance();
    			ca.setTime(fechaInicio);
    		    ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
    		    ca.set(Calendar.HOUR_OF_DAY, 23);  
    		    ca.set(Calendar.MINUTE, ca.getActualMaximum(Calendar.MINUTE));  
    		    ca.set(Calendar.SECOND, ca.getActualMaximum(Calendar.SECOND));  
    		    //factura.setDate(new Date(ca.getTime().getTime()));
    		    caAux.add(Calendar.DAY_OF_MONTH, 3);
		    	SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		    	Date date;
		    	String fechaComoCadena;
    		    if(today.before(caAux)) {
    		    	date=new Date(ca.getTime().getTime());
    		    	fechaComoCadena = formato.format(date);
    			    factura.setDate(fechaComoCadena);
    		    	
    		    }else {
    		    	date=new Date(today.getTime().getTime());
    		    	fechaComoCadena = formato.format(date);
    			    factura.setDate(fechaComoCadena);
    		    	  
    		    }

    			
    			receiver.setCfdiUse(uso);
    			receiver.setName(name);
    			receiver.setRfc(rfc);
    			receiver.setFiscalRegime(regimenFiscal);
    			receiver.setTaxZipCode(codigoPostal);
    			factura.setReceiver(receiver);
    			
    			globalInformation.setMonths(String.format("%02d",mesActual));
    			globalInformation.setPeriodicity("04");
    			globalInformation.setYear(String.valueOf(yearActual));    			
    			factura.setGlobalInformation(globalInformation);
    			
    			ArrayList<Item> items=new ArrayList<Item>();
    			ArrayList<ItemFallido> itemFallidos=new ArrayList<ItemFallido>();
				for(int i=0;i<recibosSinFacturar.size();i++) {
    				ReciboSAT producto=recibosSinFacturar.get(i);
    				if(!producto.getProductCode().equals("0")) {
    					
    					
    					Item item=new Item();
    					item.setDescription(producto.getConcepto());
    					item.setDiscount(0);
    					item.setIdentificationNumber(producto.getFolio());
    					item.setProductCode(producto.getProductCode());
    					
    					Pattern pat = Pattern.compile("[0-9]{8}");
    					Matcher mat = pat.matcher(item.getProductCode());
    					
    					double cantidad=fijarNumero(producto.getTotal()/producto.getPrecioUnitarioIVA(),2);					
    					item.setQuantity(cantidad);
    					
    					if(producto.getPrecioUnitario()==producto.getPrecioUnitarioIVA()) {
    						item.setTaxObject("01");
    						item.setUnitPrice(fijarNumero(producto.getTotal()/cantidad,2));
    						item.setSubtotal(fijarNumero(producto.getTotal(),2));
    					}else {
    						item.setSubtotal(fijarNumero(producto.getTotal()/1.16,2));
    						double subTotal=fijarNumero(producto.getTotal()/1.16,2);
    						double totalImpuestos=fijarNumero((fijarNumero(producto.getTotal(),2)-subTotal),2);
    						if(totalImpuestos>0) {
    							item.setTaxObject("02");
    							ArrayList<Tax> taxes=new ArrayList<Tax> ();
    							Tax tax=new Tax();
    							
    							tax.setName("IVA");
    							tax.setRate(.16);
    							tax.setRetention(false);
    							tax.setTotal(totalImpuestos);
    							tax.setBase(fijarNumero(totalImpuestos*6.25,2));
    							taxes.add(tax);
    							item.setTaxes(taxes);
    						}
    						item.setUnitPrice(fijarNumero(producto.getTotal()/1.16/cantidad,2));
    					}
    					item.setTotal(fijarNumero(producto.getTotal(),2));
    					item.setUnitCode(producto.getUnidad().trim());
    					if (!mat.matches() || item.getUnitCode().equals("")) {
    						ItemFallido itemF=new ItemFallido();
    						itemF.setFolio(producto.getFolio());
    						itemF.setProducto(producto.getConcepto());
    						itemF.setIdProducto(producto.getIdProducto());
    						itemFallidos.add(itemF);
    					 }else if(cantidad<1){
    						 System.out.println(item);
    					 }else {
 							items.add(item);
    						 
    					 }
    					
    						
    				}				
    				
    			}
    			
    			if(itemFallidos.size()>0) {
    				MensajeError mensaje=new MensajeError();
    				mensaje.setMsg("Fallo en la factura global la clave de los productos no cumple con el formato correcto");
    				mensaje.setProductosFallidos(itemFallidos);
    				return new ResponseEntity<>(mensaje, HttpStatus.INTERNAL_SERVER_ERROR);
    			}
    			//factura.setItems(items);
				System.out.println(items.size());
    			ArrayList<Item> itemAux = null;
    			for(int i=0;i<items.size();) {
    				itemAux=new ArrayList<Item>();
    				
    				for(int j=1;j!=0 && i<items.size();i++) {
    					j=(i+1)%1000;
    					itemAux.add(items.get(i));
        				
    				}
    				factura.setItems(itemAux);
        			String query=api+"3/cfdis";
    				System.out.println(query);
        			JSONObject object = null;
        				ObjectMapper mapper = new ObjectMapper();
        				String json = null;
    					try {
    						json = mapper.writeValueAsString(factura);
    					} catch (JsonProcessingException e1) {
    						// TODO Auto-generated catch block
    						e1.printStackTrace();
    					}
        				try
        		        {    
        					String ruta = "json.txt";
        		            String contenido = json;
        		            File file = new File(ruta);
        		            // Si el archivo no existe es creado
        		            if (!file.exists()) {
        		                file.createNewFile();
        		            }
        		            FileWriter fw = new FileWriter(file);
        		            BufferedWriter bw = new BufferedWriter(fw);
        		            bw.write(contenido);
        		            bw.close();
        		        }
        		        catch(Exception e)
        		        {
        		            System.out.println(e);
        		        }
        				object=new JSONObject(json);
        				respuesta2 = consultarAPI(query, object);
        				String uuid="";
        				try {
            				uuid=respuesta2.getString("Id");    					
        				}catch(Exception e) {
        					return new ResponseEntity<>(respuesta2.toString(), HttpStatus.CONFLICT);
        					
        				}double total = 0;
        				for(Item recibo: itemAux) {
        					Factura recibofactura=new Factura();
        		            recibofactura.setFechaCreacion(new Date());
        		            recibofactura.setFechaModificacion(new Date());
        		            recibofactura.setRecibo(recibo.getIdentificationNumber());
        		            recibofactura.setUuid(uuid);
        	            	total=0;
        		            for(Item item: factura.getItems()) {
        		            	 total=item.getTotal()+total;
        		            }
        		            recibofactura.setTotal(total);
        					facturaService.save(recibofactura);
        				}

        				query=api+"cfdi?cfdiType=issued&cfdiId="+uuid+"&email="+correoFactura;
        				System.out.println(query);
        				JSONObject obj=new JSONObject("{}");
        				respuesta2=consultarAPI(query, obj);
        				//System.out.println(respuesta2);

    				
    			}
				System.out.println(new Date());
				return new ResponseEntity<>(respuesta2.toString(), HttpStatus.OK);
			
		}
		
		@GetMapping("/recibosMes2")
		@ResponseBody
		public ResponseEntity<?> recibosMes2(){
			
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		    Calendar calendar = Calendar.getInstance(); 
		    int day=calendar.get(Calendar.DAY_OF_MONTH);
		    java.sql.Date fechaInicio;
		    java.sql.Date fechaFin;
		    if(day==1 || day==2) {
		    	calendar.set(Calendar.DAY_OF_MONTH,1);
		    	calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH)-1);
			    fechaInicio= new java.sql.Date(calendar.getTime().getTime());
			    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		    	calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH));
			    fechaFin= new java.sql.Date(calendar.getTime().getTime());
		    }else {
		    	calendar.set(Calendar.DAY_OF_MONTH,1);
			    fechaInicio= new java.sql.Date(calendar.getTime().getTime());
			    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			    fechaFin= new java.sql.Date(calendar.getTime().getTime());
		    }
		    
			Connection conn = null;
          	ArrayList<ReciboSAT> listaReporte = new ArrayList<ReciboSAT>();
	        try {
	            // Carga el driver de oracle
	        	DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
	            

	        	
	            conn = DriverManager.getConnection("jdbc:sqlserver://192.168.20.12;database=DataFlowAlpha", "extranet_user", "tyGDix##dJGJ5");
	            
	            PreparedStatement ps=conn.prepareStatement("EXEC DataFlowAlpha.dbo.sp_Consulta_Recibo_Conceptos_por_Fecha_SAT ?,? ");

	            ps.setDate(1, fechaInicio);
	            ps.setDate(2, fechaFin);
              	ResultSet rs =ps.executeQuery();
                while (rs.next()) {	     
                	ReciboSAT to=new ReciboSAT();
                	to.setFechaCaptura(rs.getDate(1));
                	to.setFolio(rs.getString(2));
                	to.setIdCliente(rs.getInt(3));
                	to.setNombreCliente(rs.getString(4));
                	to.setCodigoPostal(rs.getString(6));
                	to.setRfc(rs.getString(7));
                	to.setIdVenta(rs.getInt(8));
                	to.setConcepto(rs.getString(9));
                	to.setCosto(rs.getFloat(10));
                	to.setClave(rs.getString(11));
                	to.setPrecioUnitario(rs.getFloat(12));
                	to.setPrecioUnitarioIVA(rs.getFloat(13));
                	to.setTotal(rs.getFloat(14));
                	to.setIdProducto(String.valueOf(rs.getInt(15)));
	              	
                	
                	String observaciones=rs.getString(16);
                	String[] observacionesSplit=observaciones.split("\\|");
                	to.setUnidad(observacionesSplit[1]);
                	to.setProductCode(observacionesSplit[0].trim());
                	if(to.getCosto()!=0 && to.getPrecioUnitario()!=0)
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
	        try {
		        conn = DriverManager.getConnection(dbURLGlobal, userGlobal, passGlobal);
	            
		        PreparedStatement ps=conn.prepareStatement("exec globalsoft.dbo.sp_Consulta_Recibo_Conceptos_por_Fecha_SAT ?,? ");
	            ps.setDate(1, fechaInicio);
	            ps.setDate(2, fechaFin);
              	ResultSet rs =ps.executeQuery();
              
                while (rs.next()) {	                	 
                	ReciboSAT to=new ReciboSAT();
                	to.setFechaCaptura(rs.getDate(1));
                	to.setFolio(rs.getString(2));
                	to.setIdCliente(0);
                	to.setNombreCliente("");
                	to.setMembresia(0);
                	to.setCodigoPostal("");
                	to.setRfc("");
                	to.setIdVenta(0);
                	to.setConcepto(rs.getString(3));
                	to.setCosto(rs.getFloat(4));
                	to.setClave(rs.getString(5));
                	to.setPrecioUnitario(rs.getFloat(6));
                	to.setPrecioUnitarioIVA(rs.getFloat(7));
                	to.setTotal(rs.getFloat(8));
                	to.setCodigo(rs.getString(9));
                	to.setIdProducto(rs.getString(10));
                	to.setUnidad(rs.getString(11));
                	to.setProductCode(rs.getString(5).trim());
                	to.setUnidad(rs.getString(9));
                	if(to.getCosto()!=0 || to.getPrecioUnitario()!=0)
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
	        List<Factura>recibosFacturados=facturaService.list();
	        List<ReciboSAT> recibosSinFacturar=new ArrayList<ReciboSAT>();

			JSONObject respuesta2=new JSONObject("{}");
      		for(int i=0;i<listaReporte.size();i++) {
        		boolean ban=false;
	        	for(int j=0;j<recibosFacturados.size();j++) {
	        		if(recibosFacturados.get(j).getRecibo().equals(listaReporte.get(i).getFolio())) {
	        			ban=true;
	        		}
	        	}
	        	if(!ban) {
	        		recibosSinFacturar.add(listaReporte.get(i));
	        	}
	        }

			    Calendar fecha = Calendar.getInstance();
				int mesActual = fecha.get(Calendar.MONTH)+1;
				int yearActual = fecha.get(Calendar.YEAR);
    	        FacturaOnline factura=new FacturaOnline();
    			Receiver receiver=new Receiver();
    			GlobalInformation globalInformation=new GlobalInformation();
    			factura.setCfdiType(tipoCFDI);
    			factura.setCurrency("MXN");
    			factura.setExpeditionPlace("72430");
    			factura.setPaymentForm("01");
    			factura.setPaymentMethod("PUE");
    			Calendar ca = Calendar.getInstance();
    			Calendar caAux = Calendar.getInstance();
    			Calendar today = Calendar.getInstance();
    			ca.setTime(fechaInicio);
    		    ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
    		    ca.set(Calendar.HOUR_OF_DAY, 23);  
    		    ca.set(Calendar.MINUTE, ca.getActualMaximum(Calendar.MINUTE));  
    		    ca.set(Calendar.SECOND, ca.getActualMaximum(Calendar.SECOND));  
    		    //factura.setDate(new Date(ca.getTime().getTime()));
    		    caAux.add(Calendar.DAY_OF_MONTH, 3);
		    	SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		    	System.out.println(caAux.getTime());
		    	Date date;
		    	String fechaComoCadena;
    		    if(today.before(caAux)) {
    		    	date=new Date(ca.getTime().getTime());
    		    	fechaComoCadena = formato.format(date);
    			    factura.setDate(fechaComoCadena);
    		    	
    		    }else {
    		    	date=new Date(today.getTime().getTime());
    		    	fechaComoCadena = formato.format(date);
    			    factura.setDate(fechaComoCadena);
    		    	  
    		    }
				return new ResponseEntity<>(factura, HttpStatus.OK);
			
		}
		
		public double fijarNumero(double numero, int digitos) {
	        double resultado;
	        resultado = numero * Math.pow(10, digitos);
	        resultado = Math.round(resultado);
	        resultado = resultado/Math.pow(10, digitos);
	        return resultado;
	    }
		@PostMapping("/facturacion")
		 @ResponseBody
		public ResponseEntity<?> facturacion(@RequestBody Body body){
			Factura facturaExiste=this.reciboFacturado(body.getRecibo().trim());
			JSONObject json=new JSONObject();
			if(facturaExiste!=null) {
				json.put("respuesta", "Este folio ya fue facturado");
				return new ResponseEntity<>(json.toString(), HttpStatus.CONFLICT);
			}
			FacturaOnline factura=new FacturaOnline();
			Receiver receiver=new Receiver();
			factura.setCfdiType(body.getTipoCFDI().trim());
			factura.setCurrency("MXN");
			factura.setExpeditionPlace("72430");
			factura.setPaymentForm(body.getPago().trim());
			factura.setPaymentMethod("PUE");
			factura.setObservations(body.getObservaciones());
			String original =body.getName().toUpperCase().trim();
			/*String cadenaNormalize = Normalizer.normalize(original, Normalizer.Form.NFD);   
			String cadenaSinAcentos = cadenaNormalize.replaceAll("[\u0300-\u0301]", "");
			original = original.replace('Ñ','\u00d1');
			System.out.println("replace: " + original);
			original = Normalizer.normalize(original, Normalizer.Form.NFD);
			original = original.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
			original = original.replace('\u0001', 'Ñ');
			System.out.println("Resultado: " +original);
			System.out.println("Resultado: " + new String(cadenaSinAcentos.getBytes(), StandardCharsets.UTF_8).replace('Ñ','\u00d1'));*/
			receiver.setCfdiUse(body.getUso().trim());
			//receiver.setName(new String(cadenaSinAcentos.getBytes(), StandardCharsets.UTF_8).replace('Ñ','\u00d1'));
			receiver.setName(original);
			receiver.setRfc(body.getRfc().toUpperCase().trim());
			receiver.setFiscalRegime(body.getRegimenFiscal().trim());
			receiver.setTaxZipCode(body.getCodigoPostal().trim());
			factura.setReceiver(receiver);

			ArrayList<ReciboSAT> recibo=this.obtenerReciboSAT(body);
			ArrayList<Item> items=new ArrayList<Item>();
			for(int i=0;i<recibo.size();i++) {
				ReciboSAT producto=recibo.get(i);
				if(!producto.getProductCode().equals("0")) {
					
					Item item=new Item();
					item.setDescription(producto.getConcepto());
					item.setDiscount(0);
					item.setIdentificationNumber(producto.getFolio());
					item.setProductCode(producto.getProductCode());
					
					double cantidad=fijarNumero(producto.getTotal()/producto.getPrecioUnitarioIVA(),2);					
					item.setQuantity(cantidad);
					
					if(producto.getPrecioUnitario()==producto.getPrecioUnitarioIVA()) {
						item.setTaxObject("01");
						item.setUnitPrice(fijarNumero(producto.getTotal()/cantidad,2));
						item.setSubtotal(fijarNumero(producto.getTotal(),2));
					}else {
						item.setSubtotal(fijarNumero(producto.getTotal()/1.16,2));
						double subTotal=fijarNumero(producto.getTotal()/1.16,2);
						double totalImpuestos=fijarNumero((fijarNumero(producto.getTotal(),2)-subTotal),2);
						if(totalImpuestos>0) {
							item.setTaxObject("02");
							ArrayList<Tax> taxes=new ArrayList<Tax> ();
							Tax tax=new Tax();
							
							tax.setName("IVA");
							tax.setRate(.16);
							tax.setRetention(false);
							tax.setTotal(totalImpuestos);
							tax.setBase(fijarNumero(totalImpuestos*6.25,2));
							taxes.add(tax);
							item.setTaxes(taxes);
						}
						item.setUnitPrice(fijarNumero(producto.getTotal()/1.16/cantidad,2));
					}
					
					
					//tax.setTotal(redondearDosDecimales(cantidad*producto.getPrecioUnitario()*.16));
					//double resta=producto.getTotal()-(fijarNumero(subTotal+item.getTaxes().get(0).getTotal(),2));
					
					item.setTotal(fijarNumero(producto.getTotal(),2));
					item.setUnitCode(producto.getUnidad().trim());
					items.add(item);
				}				
			}
			factura.setItems(items); 
			String query=api+"3/cfdis";
			System.out.println(query);
            JSONObject object=new JSONObject(factura);
			System.out.println(object);
			JSONObject respuesta2=null;
			try {
				 respuesta2 = consultarAPI(query, object);
				String uuid=respuesta2.getString("Id");
	            Factura recibofactura=new Factura();
	            recibofactura.setFechaCreacion(new Date());
	            recibofactura.setFechaModificacion(new Date());
	            recibofactura.setRecibo(body.getRecibo().trim());
	            recibofactura.setUuid(uuid);
	            recibofactura.setIdCliente(body.getUsuario());
	            recibofactura.setUsoCFDI(body.getUso().trim());
	            recibofactura.setNombre(body.getName().toUpperCase().trim());
	            recibofactura.setRfc(body.getRfc().toUpperCase().trim());
	            recibofactura.setRegimenFiscal(body.getRegimenFiscal().trim());
	            recibofactura.setCodigoPostal(body.getCodigoPostal().trim());
	            recibofactura.setEmail(body.getEmail().trim());
	            items=factura.getItems();
            	double total=0;
	            for(Item item: items) {
	            	 total=total+item.getTotal();
	            }
	            recibofactura.setTotal(total);
				facturaService.save(recibofactura);

				query=api+"cfdi?cfdiType=issued&cfdiId="+uuid+"&email="+body.getEmail().trim();
				System.out.println(query);
				JSONObject obj=new JSONObject("{}");
				respuesta2=consultarAPI(query, obj);
				System.out.println(respuesta2);
				return new ResponseEntity<>(respuesta2.toString(), HttpStatus.OK);
			}catch(Exception e) {
				try {
					System.out.println();
					System.out.println(respuesta2);
					String mensajeRespuesta=respuesta2.getString("Message");
					if(mensajeRespuesta.equals("Este RFC del receptor no existe en la lista de RFC inscritos no cancelados del SAT.")) {
						return new ResponseEntity<>(respuesta2.toString(), HttpStatus.CONFLICT);
					}
					if(mensajeRespuesta.equals("El campo Nombre del receptor, debe pertenecer al nombre asociado al RFC registrado en el campo Rfc del Receptor.")) {
						return new ResponseEntity<>(respuesta2.toString(), HttpStatus.CONFLICT);
					}
					if(mensajeRespuesta.equals("El campo DomicilioFiscalReceptor del receptor, debe pertenecer al nombre asociado al RFC registrado en el campo Rfc del Receptor.")) {
						return new ResponseEntity<>(respuesta2.toString(), HttpStatus.CONFLICT);
					}
					JSONObject model=respuesta2.getJSONObject("ModelState");
					String mensaje="";
					JSONArray errores=null;
					try {
						errores=model.getJSONArray("cfdiToCreate.Receiver.Rfc");
						for(int i=0;i<errores.length();i++) {
							mensaje=mensaje+errores.getString(i)+", ";
						}
					}catch(Exception e2) {
						
					}
					try {
						errores=model.getJSONArray("cfdiToCreate.Receiver.FiscalRegime");
						for(int i=0;i<errores.length();i++) {
							mensaje=mensaje+errores.getString(i)+", ";
						}
					}catch(Exception e2) {
						
					}
					try {
						errores=model.getJSONArray("cfdiToCreate.Receiver.TaxZipCode");
						for(int i=0;i<errores.length();i++) {
							mensaje=mensaje+errores.getString(i)+", ";
						}
					}catch(Exception e2) {
						
					}
					try {
						errores=model.getJSONArray("cfdiToCreate.Receiver.Name");
						for(int i=0;i<errores.length();i++) {
							mensaje=mensaje+errores.getString(i)+", ";
						}
					}catch(Exception e2) {
						
					}
					mensaje.replace("código postal", "TaxZipCode");
					
					
					
					respuesta2.put("Message", mensaje.replace("TaxZipCode", "código postal"));
				
				}catch(Exception e1) {
					try{
							String mensaje ="Este proceso necesita atención. Favor de reportarlo en el chat o en los teléfonos 444-8356098 o 444-1510191"
								+ " para brindarle una mejor solución";
							respuesta2.put("Message", mensaje);
							String model=respuesta2.getString("Message");
							
							if(model.equals(mensaje)) {
								respuesta2.put("Message", "Tenemos intermitencia en el servicio, por favor intente más tarde.Disculpe las molestias");
							}
					}catch(Exception e2) {
						
					}
					
					
					
				}
				System.out.println(respuesta2);
				return new ResponseEntity<>(respuesta2.toString(), HttpStatus.CONFLICT);
			}
			
		}
		
		@PostMapping("/facturacionRetrasada")
		 @ResponseBody
		public ResponseEntity<?> facturacionRetrasada(@RequestBody Body body){
			Factura facturaExiste=this.reciboFacturado(body.getRecibo().trim());
			JSONObject json=new JSONObject();
			if(facturaExiste!=null) {
				json.put("respuesta", "Este folio ya fue facturado");
				return new ResponseEntity<>(json.toString(), HttpStatus.CONFLICT);
			}
			FacturaOnline factura=new FacturaOnline();
			Receiver receiver=new Receiver();
			factura.setCfdiType(body.getTipoCFDI().trim());
			factura.setCurrency("MXN");
			factura.setExpeditionPlace("72430");
			factura.setPaymentForm(body.getPago().trim());
			factura.setPaymentMethod("PUE");
			factura.setObservations(body.getObservaciones());
			factura.setDate(body.getDate());
			
			receiver.setCfdiUse(body.getUso().trim());
			receiver.setName(body.getName().toUpperCase().trim());
			receiver.setRfc(body.getRfc().toUpperCase().trim());
			receiver.setFiscalRegime(body.getRegimenFiscal().trim());
			receiver.setTaxZipCode(body.getCodigoPostal().trim());
			factura.setReceiver(receiver);
			ArrayList<ReciboSAT> recibo=this.obtenerReciboSAT(body);
			ArrayList<Item> items=new ArrayList<Item>();
			for(int i=0;i<recibo.size();i++) {
				ReciboSAT producto=recibo.get(i);
				if(!producto.getProductCode().equals("0")) {
					
					Item item=new Item();
					item.setDescription(producto.getConcepto());
					item.setDiscount(0);
					item.setIdentificationNumber(producto.getFolio());
					item.setProductCode(producto.getProductCode());
					
					double cantidad=fijarNumero(producto.getTotal()/producto.getPrecioUnitarioIVA(),2);					
					item.setQuantity(cantidad);
					
					if(producto.getPrecioUnitario()==producto.getPrecioUnitarioIVA()) {
						item.setTaxObject("01");
						item.setUnitPrice(fijarNumero(producto.getTotal()/cantidad,2));
						item.setSubtotal(fijarNumero(producto.getTotal(),2));
					}else {
						item.setSubtotal(fijarNumero(producto.getTotal()/1.16,2));
						double subTotal=fijarNumero(producto.getTotal()/1.16,2);
						double totalImpuestos=fijarNumero((fijarNumero(producto.getTotal(),2)-subTotal),2);
						if(totalImpuestos>0) {
							item.setTaxObject("02");
							ArrayList<Tax> taxes=new ArrayList<Tax> ();
							Tax tax=new Tax();
							
							tax.setName("IVA");
							tax.setRate(.16);
							tax.setRetention(false);
							tax.setTotal(totalImpuestos);
							tax.setBase(fijarNumero(totalImpuestos*6.25,2));
							taxes.add(tax);
							item.setTaxes(taxes);
						}
						item.setUnitPrice(fijarNumero(producto.getTotal()/1.16/cantidad,2));
					}
					
					
					//tax.setTotal(redondearDosDecimales(cantidad*producto.getPrecioUnitario()*.16));
					//double resta=producto.getTotal()-(fijarNumero(subTotal+item.getTaxes().get(0).getTotal(),2));
					
					item.setTotal(fijarNumero(producto.getTotal(),2));
					item.setUnitCode(producto.getUnidad().trim());
					items.add(item);
				}				
			}
			factura.setItems(items); 
			String query=api+"3/cfdis";
			System.out.println(query);
           JSONObject object=new JSONObject(factura);
			System.out.println(object);
			JSONObject respuesta2=null;
			try {
				 respuesta2 = consultarAPI(query, object);
				String uuid=respuesta2.getString("Id");
	            Factura recibofactura=new Factura();
	            recibofactura.setFechaCreacion(new Date());
	            recibofactura.setFechaModificacion(new Date());
	            recibofactura.setRecibo(body.getRecibo().trim());
	            recibofactura.setUuid(uuid);
	            recibofactura.setIdCliente(body.getUsuario());
	            recibofactura.setUsoCFDI(body.getUso().trim());
	            recibofactura.setNombre(body.getName().toUpperCase().trim());
	            recibofactura.setRfc(body.getRfc().toUpperCase().trim());
	            recibofactura.setRegimenFiscal(body.getRegimenFiscal().trim());
	            recibofactura.setCodigoPostal(body.getCodigoPostal().trim());
	            recibofactura.setEmail(body.getEmail().trim());
	            items=factura.getItems();
           	double total=0;
	            for(Item item: items) {
	            	 total=item.getTotal();
	            }
	            recibofactura.setTotal(total);
				facturaService.save(recibofactura);

				query=api+"cfdi?cfdiType=issued&cfdiId="+uuid+"&email="+body.getEmail().trim();
				System.out.println(query);
				JSONObject obj=new JSONObject("{}");
				respuesta2=consultarAPI(query, obj);
				System.out.println(respuesta2);
				return new ResponseEntity<>(respuesta2.toString(), HttpStatus.OK);
			}catch(Exception e) {
				try {
					JSONObject model=respuesta2.getJSONObject("ModelState");
					String mensaje="";
					JSONArray errores=null;
					try {
						errores=model.getJSONArray("cfdiToCreate.Receiver.Rfc");
						for(int i=0;i<errores.length();i++) {
							mensaje=mensaje+errores.getString(i)+", ";
						}
					}catch(Exception e2) {
						
					}
					try {
						errores=model.getJSONArray("cfdiToCreate.Receiver.FiscalRegime");
						for(int i=0;i<errores.length();i++) {
							mensaje=mensaje+errores.getString(i)+", ";
						}
					}catch(Exception e2) {
						
					}
					try {
						errores=model.getJSONArray("cfdiToCreate.Receiver.TaxZipCode");
						for(int i=0;i<errores.length();i++) {
							mensaje=mensaje+errores.getString(i)+", ";
						}
					}catch(Exception e2) {
						
					}
					try {
						errores=model.getJSONArray("cfdiToCreate.Receiver.Name");
						for(int i=0;i<errores.length();i++) {
							mensaje=mensaje+errores.getString(i)+", ";
						}
					}catch(Exception e2) {
						
					}
					mensaje.replace("código postal", "TaxZipCode");
					
					
					
					respuesta2.put("Message", mensaje.replace("TaxZipCode", "código postal"));
				
				}catch(Exception e1) {
					try{
						String mensaje ="Este proceso necesita atención. Favor de reportarlo en el chat o en los teléfonos 444-8356098 o 444-1510191"
								+ " para brindarle una mejor solución";
							respuesta2.put("Message", mensaje);
							String model=respuesta2.getString("Message");
							
							if(model.equals(mensaje)) {
								respuesta2.put("Message", "Tenemos intermitencia en el servicio, por favor intente más tarde.Disculpe las molestias");
							}
					}catch(Exception e2) {
						
					}
					
					
					
				}
				System.out.println(respuesta2.getString("Message"));
				return new ResponseEntity<>(respuesta2.toString(), HttpStatus.CONFLICT);
			}
			
		}
		
		@PostMapping("/descargarFactura")
		 @ResponseBody
		public ResponseEntity<?> descargarFactura(@RequestBody Body body){
			JSONObject respuesta2=null;
			try {
				String query=api+"cfdi/"+body.getFormato()+"/"+body.getType()+"/"+body.getIdFactura();

				URL url = new URL(query);
				HttpURLConnection http = (HttpURLConnection)url.openConnection();
				http.setRequestProperty("Accept", "application/json");
				http.setRequestProperty("Authorization", basicAuth);
			    int statusCode = http.getResponseCode();
			    InputStream is = null;

			    if (statusCode >= 200 && statusCode < 400) {
			       is = http.getInputStream();
			       String result = org.apache.commons.io.IOUtils.toString(is, "UTF-8");
		            respuesta2 = new JSONObject(result);
		            is.close();
		            http.disconnect();
					return new ResponseEntity<>(respuesta2.toString(), HttpStatus.OK);
			    }
			    else {
			       is = http.getErrorStream();
			       String result = org.apache.commons.io.IOUtils.toString(is, "UTF-8");
		            respuesta2 = new JSONObject(result);
		            is.close();
		            http.disconnect();
					return new ResponseEntity<>(respuesta2.toString(), HttpStatus.CONFLICT);
			    }

	            
			}catch (IOException e) {
				e.printStackTrace();
			}
			respuesta2=new JSONObject();
			respuesta2.put("respuesta","ocurrio un error durante la descarga de la factura");
			return new ResponseEntity<>(respuesta2.toString(), HttpStatus.CONFLICT);
		} 

		private JSONObject consultarAPI(String query, JSONObject object) {
			JSONObject respuesta2 = null;
			try {
				URL url = new URL(query);
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	            conn.setRequestProperty ("Authorization", basicAuth);
	            conn.setConnectTimeout(5000);
	            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            conn.setRequestMethod("POST");
	            OutputStream os = conn.getOutputStream();
			    os.write(object.toString().getBytes("UTF-8"));
			    os.close();
			    int statusCode = conn.getResponseCode();
			    InputStream is = null;

			    if (statusCode >= 200 && statusCode < 400) {
			       // Create an InputStream in order to extract the response object
			       is = conn.getInputStream();
			    }
			    else {
			       is = conn.getErrorStream();
			    }

	            //InputStream in = new BufferedInputStream(conn.getInputStream());
	            String result = org.apache.commons.io.IOUtils.toString(is, "UTF-8");
	            respuesta2 = new JSONObject(result);
	            is.close();
	            conn.disconnect();
	            
			}catch(Exception e) {
				e.printStackTrace();
			}
			return respuesta2;
		}
		public Factura reciboFacturado(String recibo) {
			try {
                Factura factura=facturaService.getByRecibo(recibo);
                factura.setFacturado(true);
                facturaService.save(factura);
				return factura;        		
        	}catch(NoSuchElementException e) {                		
        	}
			return null;
		}
			
		
		
		@GetMapping("/clienteFactura/{idCliente}")
		public ResponseEntity<?> clienteFactura(@PathVariable("idCliente") int idCliente){
			JSONObject json=new JSONObject();
			try {
				List<Factura> factura=facturaService.getByCliente(idCliente);
				
				return new ResponseEntity<>(factura, HttpStatus.OK); 
			}catch(Exception e) {		
				e.printStackTrace();
				json.put("respuesta", "Error al procesar la factura");
				return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT); 
			}
			
		}
		@GetMapping("/generarArchivoDomiciliacion")
		public ResponseEntity<?> generarArchivo(){
			Connection conn = null;
	        try {
	            // Carga el driver de oracle
	        	DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
	            

	        	
	            conn = DriverManager.getConnection(dbURL, userData, passData);
	            
	            PreparedStatement ps=conn.prepareStatement("EXEC DataFlowAlpha.dbo.sp_Generar_Archivo_Domiciliacion ");
	            List<DomiciliacionDatos>lista=new ArrayList<DomiciliacionDatos>();
              	ResultSet rs =ps.executeQuery();
                while (rs.next()) {	     
                	DomiciliacionDatos to=new DomiciliacionDatos();
                	to.setReferencia(rs.getString(1));
                	to.setNombre(rs.getString(2));
                	to.setPaterno(rs.getString(3));
                	to.setMaterno(rs.getString(4));
                	to.setCuenta(rs.getString(5));
                	to.setInicio(rs.getString(6));
                	to.setFin(rs.getString(7));
                	to.setVencimiento(rs.getString(8));
                	to.setMonto(rs.getFloat(9));
                	to.setContrato(rs.getString(10));                	
                	lista.add(to);
                	
                }
                
            	conn.close();
            	return new ResponseEntity<>(lista, HttpStatus.OK);
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
			return null;
		}
		
		@PostMapping("/registrarAcceso")
		 @ResponseBody
		public ResponseEntity<?> registrarAcceso(@RequestBody AccesosClub body){
			JSONObject respuesta2=null;
			try {
				body.setFechaAcceso(new Date());
				accesosClubService.save(body);			    
			}catch (Exception e) {
				e.printStackTrace();
			}
			respuesta2=new JSONObject();
			respuesta2.put("respuesta","ocurrio un error durante el registro");
			return new ResponseEntity<>(respuesta2.toString(), HttpStatus.CONFLICT);
		} 
		@PostMapping("/registrarDatos")
		 @ResponseBody
		public ResponseEntity<?> registrarDatos(@RequestBody DatosUsuario body){
			JSONObject respuesta2=null;
			respuesta2=new JSONObject();
			try {
				datosUsuarioService.save(body);		
				respuesta2.put("respuesta","Informacion guardada correctamente");
				return new ResponseEntity<>(respuesta2.toString(), HttpStatus.OK);	    
			}catch (Exception e) {
				e.printStackTrace();
			}
			respuesta2.put("respuesta","ocurrio un error durante el registro");
			return new ResponseEntity<>(respuesta2.toString(), HttpStatus.CONFLICT);
		} 

		@PostMapping("/aplicarPago")
		@ResponseBody
		public ResponseEntity<?> registrarDatos(@RequestBody PagoApi body){
			JSONObject resp=new JSONObject();
			
			try {
				this.sp(body.noPedido, body.monto, body.notarjeta, body.folioInterbancario, body.noAutorizacion, body.fechaPago,
						body.idBanco,body.idCuentaDeBanco,body.horaPago,body.formaPago, body.titularCuenta,body.reciboName,
						body.fechaImpresion);

				resp.put("respuesta", "el pago se aplico exitosamente");
				return new ResponseEntity<>(resp.toString(), HttpStatus.OK);
			}catch(Exception e) {
				resp.put("respuesta", "ocurrio un error durante la aplicacion del pago");
				return new ResponseEntity<>(resp.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		public void sp(long noPedido, double monto, String noTarjeta, String folioInterbancario, String noAutorizacion,
				String fechaPago,int idBanco,int idCuentaBanco, String horaPago,int formaPago, String titularCuenta,String reciboName,String horaImpresion) {
			   Connection conn = null;
		       try {
		           // Carga el driver de oracle
		       		DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
		           conn = DriverManager.getConnection(dbURL, userData, passData);
		           Long thirtyDaysFromNowUnixTimestamp= 8L * 60 * 60*1000;
		           PreparedStatement ps=conn.prepareStatement("EXEC DataFlowAlpha.dbo.CreaReciboPagoPOL_RECIBOS_API ?,?,?,?,?,?,?,?,?,?,?,?,?,?,? ");
		           ps.setInt(1, 1);
		           ps.setString(2, horaImpresion);
		           ps.setString(3, horaImpresion);
		           ps.setLong(4, noPedido);
		           ps.setString(5, "");
		           ps.setDouble(6, monto);
		           ps.setInt(7, idBanco);
		           ps.setString(8, noTarjeta);
		           ps.setString(9, folioInterbancario);
		           ps.setString(10, fechaPago+" "+horaPago);
		           ps.setString(11, noAutorizacion);
		           ps.setInt(12, idCuentaBanco);
		           ps.setInt(13, formaPago);
		           ps.setString(14, titularCuenta);
		           ps.setString(15, reciboName);
		           //Codigo para pruebas dataflow
		           /* ps.setInt(1, 1);
		           ps.setString(2, horaImpresion);
		           ps.setString(3, fechaPago+" "+horaPago);
		           ps.setLong(4, noPedido);
		           ps.setString(5, "");
		           ps.setDouble(6, monto);
		           ps.setInt(7, idBanco);
		           ps.setString(8, noTarjeta);
		           ps.setString(9, folioInterbancario);
		           ps.setString(10, fechaPago+" "+horaPago);
		           ps.setString(11, noAutorizacion);
		           ps.setInt(12, idCuentaBanco);
		           ps.setInt(13, formaPago);
		           ps.setString(14, titularCuenta);
		           ps.setString(15, reciboName);
		            */
		           ps.execute();
		           
		         
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
		   }
		
		
@GetMapping("/pruebaApiFiserv")
@ResponseBody
public ResponseEntity<?> pruebaApiFiserv(){
	UUID uuid = UUID.randomUUID();
    String uuidAsString = uuid.toString();
    Date fechaActual=new Date();
    long timestamp=fechaActual.getTime();
    //Modificar por lo que quieras
    //String input = "{\"transactionAmount\":{\"total\":\"6.00\",\"currency\":\"MXN\"},\"requestType\":\"PaymentTokenSaleTransaction\",\"paymentMethod\":{\"paymentToken\":{\"value\":\"DF5E0B3F-AB67-47F0-B215-A6C5EB4F5B90\",\"securityCode\":\"123\"}}}";
    String input = "{\"requestType\":\"PaymentCardPaymentTokenizationRequest\",\"paymentCard\":{\"number\":\"4761739001010010\",\"expiryDate\":{\"month\":\"10\",\"year\":\"22\"}},\"createToken\":{\"reusable\":true,\"declineDuplicates\":false}}";
    
    String msgSignatureStrin= "tFSef3RsuLpzPIMcDeuSYisgusLiQy9z"+uuidAsString + String.valueOf(timestamp) + input;
    System.out.println(msgSignatureStrin);
    
    String hmac = new HmacUtils("HmacSHA256", "DVGUnyrS6yar5sSX631rtyMQRcwpcN7mKQ13Ay59LJf").hmacHex(msgSignatureStrin);
    System.out.println(hmac);
    byte[]valueDecoded=Base64.encodeBase64(hmac.getBytes());
    System.out.println(new String(valueDecoded));
    
    
   /* Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
    SecretKeySpec secret_key = new SecretKeySpec("DVGUnyrS6yar5sSX631rtyMQRcwpcN7mKQ13Ay59LJf".getBytes("UTF-8"), "HmacSHA256");
    sha256_HMAC.init(secret_key);

    bytesToHex(sha256_HMAC.doFinal(msgSignatureStrin.getBytes()));*/
    /*try {
    	URL url = new URL("http://cert.api.firstdata.com/gateway/v2/payment-tokens");
    	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    	conn.setDoOutput(true);
    	conn.setRequestMethod("POST");
    	conn.setRequestProperty("Content-Type", "application/json");
    	conn.setRequestProperty("Api-Key", "tFSef3RsuLpzPIMcDeuSYisgusLiQy9z");
    	conn.setRequestProperty("Client-Request-Id", uuidAsString);
    	conn.setRequestProperty("Timestamp", String.valueOf(timestamp));
    	conn.setRequestProperty("Message-Signature",new String(valueDecoded));    	
    	conn.setDoOutput(true);
        conn.setDoInput(true);
    	OutputStream os = conn.getOutputStream();
    	os.write(input.getBytes());
    	os.close();
	    int statusCode = conn.getResponseCode();
	    InputStream is = null;
	    System.out.println(statusCode);
	    if (statusCode >= 200 && statusCode < 400) {
	       // Create an InputStream in order to extract the response object
	       is = conn.getInputStream();
	    }
	    else {
	       is = conn.getErrorStream();
	    }

	    System.out.println(is);
	    String result = org.apache.commons.io.IOUtils.toString(is, "UTF-8");
	    System.out.println(result);
        //JSONObject respuesta2 = new JSONObject(result);
        is.close();
        conn.disconnect();
    }catch(Exception e) {
    	e.printStackTrace();
    }*/
    
    JSONObject json=new JSONObject();
    json.put("api_key", "tFSef3RsuLpzPIMcDeuSYisgusLiQy9z");
    json.put("client_id", uuidAsString);
    json.put("timestamp", String.valueOf(timestamp));
    json.put("data", new String(valueDecoded));
	return new ResponseEntity<>(json.toString(), HttpStatus.OK);
}

@PostMapping("/referencia")
@ResponseBody
public ResponseEntity<?> referencia(@RequestBody Referencia body){
	JSONObject resp=new JSONObject();
	
	try {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<Referencia> listaClases = currentSession.createNativeQuery("select * from referencia  where "
				+ "no_autorizacion LIKE '%' || trim('"+body.getNoAutorizacion()+"') || '%' and id_cliente="+body.getIdCliente()+""
				+ " and cast ('"+body.getImporte()+"' as numeric)= cast (importe as numeric);",Referencia.class);
        List<Referencia> lista=listaClases.getResultList();
        if(lista.size()>0) {
    		resp.put("respuesta", "este pago ya fue aplicado");
    		return new ResponseEntity<>(resp.toString(), HttpStatus.CONFLICT);
        	
        }
		resp.put("respuesta", "el pago no ha sido aplicado");
		
		return new ResponseEntity<>(resp.toString(), HttpStatus.OK);
	}catch(Exception e) {
		resp.put("respuesta", "ocurrio un error durante la aplicacion del pago");
		return new ResponseEntity<>(resp.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
@PostMapping("/guardarReferencia")
@ResponseBody
public ResponseEntity<?> guardarReferencia(@RequestBody Referencia body){
	JSONObject resp=new JSONObject();
	
	try {
		referenciaService.save(body);		
		resp.put("respuesta", "pago guardado");
		
		return new ResponseEntity<>(resp.toString(), HttpStatus.OK);
	}catch(Exception e) {
		resp.put("respuesta", "ocurrio un error durante la aplicacion del pago");
		return new ResponseEntity<>(resp.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

	@GetMapping("/actualizarDomiciliacion")
	@Transactional(rollbackOn =Exception.class)
	public ResponseEntity<?> actualizarDomiciliacion(){
		Connection conn = null;
	    try {
	    	Session currentSession = entityManager.unwrap(Session.class);
	    	currentSession.createNativeQuery("delete from cliente_domiciliado").executeUpdate();
	    	DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
	        conn = DriverManager.getConnection(dbURL, userData, passData);
	        
	        PreparedStatement ps=conn.prepareStatement("EXEC DataFlowAlpha.dbo.sp_Generar_Archivo_Domiciliacion_fiserv ");
	        List<ClienteDomiciliado>lista=new ArrayList<ClienteDomiciliado>();
	      	ResultSet rs =ps.executeQuery();
	        while (rs.next()) {	     
	        	ClienteDomiciliado to=new ClienteDomiciliado();
	        	to.setIdCliente(rs.getInt(1));
	        	to.setMembresia(rs.getString(2));
	        	to.setNombre(rs.getString(3));
	        	to.setMonto(rs.getFloat(8));
	        	clienteDomiciliadoService.save(to);
	        	lista.add(to);
	        	
	        }
	        
	    	conn.close();
	    	return new ResponseEntity<>(lista, HttpStatus.OK);
	    } catch (SQLException ex) {
	        System.out.println("Error: " + ex.getMessage());
	        ex.printStackTrace();
	    } 
	    catch(Exception e){
	    	e.printStackTrace();
	    }finally {
	        try {
	        	if(conn!=null) {
		        	conn.close();	        		
	        	}
	        } catch (SQLException ex) {
	            System.out.println("Error: " + ex.getMessage());
	        }
	    }
		return null;
	}

}//fin de la clase

