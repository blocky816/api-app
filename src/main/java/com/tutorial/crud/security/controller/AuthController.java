package com.tutorial.crud.security.controller;

import com.tutorial.crud.controller.ProductoController;
import com.tutorial.crud.correo.Correo;
import com.tutorial.crud.dto.Mensaje;
import com.tutorial.crud.entity.Body;
import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.Club;
import com.tutorial.crud.security.dto.JwtDto;
import com.tutorial.crud.security.dto.LoginUsuario;
import com.tutorial.crud.security.dto.NuevoUsuario;
import com.tutorial.crud.security.encryption.DesCipherUtil;
import com.tutorial.crud.security.entity.Rol;
import com.tutorial.crud.security.entity.Usuario;
import com.tutorial.crud.security.enums.RolNombre;
import com.tutorial.crud.security.jwt.JwtProvider;
import com.tutorial.crud.security.repository.UsuarioRepository;
import com.tutorial.crud.security.service.RolService;
import com.tutorial.crud.security.service.UsuarioService;
import com.tutorial.crud.service.ClienteServiceImpl;
import com.tutorial.crud.service.ClubServiceImpl;

import org.apache.log4j.BasicConfigurator;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.security.core.AuthenticationException;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController{
	@Value("${my.property.s}")
	private String ss;
	
	@Value("${my.property.ruta}")
	private String ruta;

	private static Logger logJava =  Logger.getLogger("AuthController");
	
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;
    
    @Autowired
    UsuarioRepository u;
    

    @Autowired
    ClubServiceImpl clubService;    

    @Autowired
    ClienteServiceImpl clienteService;

	@Value("${my.property.usuarioCorreo}")
	String usuarioCorreo;
	
	@Value("${my.property.contrasenaCorreo}")
	String contrasenaCorreo;

    public void nuevo( NuevoUsuario nuevoUsuario){
        	 Usuario usuario =
                     new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getNombreUsuario(), nuevoUsuario.getEmail(),
                             passwordEncoder.encode(nuevoUsuario.getPassword()),nuevoUsuario.getEstatus(),nuevoUsuario.getCliente(),
                             nuevoUsuario.getClienteTipo(),nuevoUsuario.getEstatusCobranza(),nuevoUsuario.getNivel1());
             Set<Rol> roles = new HashSet<>();
             roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
             if(nuevoUsuario.getRoles().contains("admin")) {
                 roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
             }
             usuario.setRoles(roles);
             usuarioService.save(usuario);
             
           
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult){
    	
    	
    	FileHandler fh;  

        try {  

            // This block configure the logger with handler and formatter  
            fh = new FileHandler(ruta,true);  
            logJava.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();  
            fh.setFormatter(formatter);  

            // the following statement is used to log any messages  

           
        String pass = DesCipherUtil.decrypt(loginUsuario.getPassword(),ss);
    	if(bindingResult.hasErrors()) {
            return new ResponseEntity(new Mensaje("campos mal puestos"), HttpStatus.BAD_REQUEST);
        }
    	//logJava.info(loginUsuario.getNombreUsuario() + ", intentando loggear ");
    	try {
    		
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), pass));    	
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //logJava.info(loginUsuario.getNombreUsuario() + ", log correcto ");
            String jwt = jwtProvider.generateToken(authentication);
            UserDetails userDetails = (UserDetails)authentication.getPrincipal();
            Usuario cliente=usuarioService.getByNombreUsuario(loginUsuario.getNombreUsuario()).get();
            String nombreClub=cliente.getNivel();
            Club club=clubService.findByNombre(nombreClub);
            int idClub=club.getIdClub();
            boolean activo=false;
            String estatusCobranza=cliente.getEstatusCobranza();
            if(!estatusCobranza.equals("Baja")) {
            	activo=true;
            }
            JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities(),idClub,activo);
            fh.close();
          
            return new ResponseEntity<>(jwtDto, HttpStatus.OK);
    	}catch(AuthenticationException e) {
    		//logJava.warning(loginUsuario.getNombreUsuario() + ", log incorrecto ");
    		
    	}
    	fh.close();
        } catch (SecurityException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
      
        return new ResponseEntity<>("Error en el login - datos no validos", HttpStatus.BAD_REQUEST);
    }
    
    @PostMapping("/loginFit")
    public ResponseEntity<?> loginFit(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult){
    	
    	
    	FileHandler fh;  

        try {  

            // This block configure the logger with handler and formatter  
            fh = new FileHandler(ruta,true);  
            logJava.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();  
            fh.setFormatter(formatter);  

            // the following statement is used to log any messages  

           
    	if(bindingResult.hasErrors()) {
            return new ResponseEntity(new Mensaje("campos mal puestos"), HttpStatus.BAD_REQUEST);
        }
    	//logJava.info(loginUsuario.getNombreUsuario() + ", intentando loggear ");
    	try {
    		
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), loginUsuario.getPassword()));    	
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //logJava.info(loginUsuario.getNombreUsuario() + ", log correcto ");
            String jwt = jwtProvider.generateToken(authentication);
            UserDetails userDetails = (UserDetails)authentication.getPrincipal();
            Usuario cliente=usuarioService.getByNombreUsuario(loginUsuario.getNombreUsuario()).get();
            String nombreClub=cliente.getNivel();
            Club club=clubService.findByNombre(nombreClub);
            int idClub=club.getIdClub();
            boolean activo=false;
            String estatusCobranza=cliente.getEstatusCobranza();
            if(!estatusCobranza.equals("Baja")) {
            	activo=true;
            }
            JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities(),idClub,activo);
            fh.close();
          
            return new ResponseEntity<>(jwtDto, HttpStatus.OK);
    	}catch(AuthenticationException e) {
    		//logJava.warning(loginUsuario.getNombreUsuario() + ", log incorrecto ");
    		
    	}
    	fh.close();
        } catch (SecurityException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
      
        return new ResponseEntity<>("Error en el login - datos no validos", HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/forgot-password")
	@ResponseBody
	public ResponseEntity<?> forgotPassword(@RequestBody Body body){
    	body.setAnio(new java.sql.Date(body.getAnio().getTime()+(6*60*60*1000)));
    	Usuario usuario=usuarioService.getByNombreUsuario(String.valueOf(body.getUsuario())).get();
    	Cliente cliente=clienteService.findById(body.getUsuario());
    	Date date=cliente.getFechaNacimiento();
    	JSONObject json=new JSONObject();
    	Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dateYear = calendar.get(Calendar.YEAR);
        int dateMonth = calendar.get(Calendar.MONTH);
        int dateDay = calendar.get(Calendar.DAY_OF_MONTH);
        
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(body.getAnio());
        int dateYear2 = calendar2.get(Calendar.YEAR);
        int dateMonth2 = calendar2.get(Calendar.MONTH);
        int dateDay2 = calendar2.get(Calendar.DAY_OF_MONTH);
        
    	if(dateYear==dateYear2 && dateMonth==dateMonth2 && dateDay==dateDay2) {
    		String[] symbols = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "m", "n", "o", "p", "q"
        			, "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "J"
        			, "K", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2"
        			, "3", "4", "5", "6", "7", "8", "9"};
            int length = (int) (Math.random()*(6)) + 6;
            //Random random;
                //random = SecureRandom.getInstanceStrong();
                StringBuilder sb = new StringBuilder(length);
                for (int i = 0; i < length; i++) {
                	int index = (int)(Math.random() * symbols.length);
                     //int indexRandom = random.nextInt ( symbols.length );
                     sb.append( symbols[index] );
                }
                String password = sb.toString();
                String passwordEncode=passwordEncoder.encode(password);
                usuario.setPassword(passwordEncode);
                usuarioService.save(usuario);
                String correoUsuario=usuario.getEmail();
                Correo correo=new Correo(usuarioCorreo,contrasenaCorreo,correoUsuario);
                String asunto="Restablecer Contraseña";
                String club=usuario.getNivel();
                if(club.equals("Sports Plaza")) {
                    correo.enviar_correoContrasenaSports(asunto, usuario.getNombreUsuario(), password);
                }else if(club.equals("CIMERA")) {
                    correo.enviar_correoContrasenaCimera(asunto, usuario.getNombreUsuario(), password);
                }else  {
                    correo.enviar_correoContrasenaAlpha(asunto, usuario.getNombreUsuario(), password);
                }
                json.put("respuesta", "Cambio de contraseña Realizado exitosamente");
            	return new ResponseEntity<>(json.toString(), HttpStatus.OK);
        	//JwtProvider jwt=new JwtProvider();
        	/*if(!datos.isEmpty()) {
        		JSONObject json = new JSONObject(datos);
        		Optional<Usuario> x = u.findByNombreUsuario(json.getString("nombreUsuario"));
        		Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)(\\.[A-Za-z]{2,})$");
        		Matcher mEmail = pattern.matcher(x.get().getEmail().toLowerCase());
        		if(mEmail.matches()) {
        			Correo c = new Correo("bloodgigametal@gmail.com","farmacia123",x.get().getEmail());
        			c.enviar_correo("lalo",jwt.generateTokenReset(x.get()));
        			return new ResponseEntity(new Mensaje("Se ha enviado un correo con el token"), HttpStatus.OK);
        		}else {
        			return new ResponseEntity(new Mensaje("No tiene asignado un correo o no es valido"), HttpStatus.OK);
        		}
        	}
        	return new ResponseEntity(new Mensaje("no ha mandado nada"), HttpStatus.BAD_REQUEST);*/
        	
    	}
    	json.put("respuesta", "Datos no validos para cambiar la contraseña");
    	return new ResponseEntity<>(json.toString(), HttpStatus.BAD_REQUEST);
    	
    }
    
    @PostMapping("/new-password")
    public ResponseEntity<?> newPassword(@RequestBody String datos) {
    	if(!datos.isEmpty()) {
    		JSONObject respuesta=new JSONObject();
    		JSONObject json = new JSONObject(datos);
    		String usuario = json.getString("usuario");
			Optional<Usuario> x = u.findByNombreUsuario(usuario);
			try {
				Authentication authentication =
		                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuario, json.getString("passwordAnterior")));
				SecurityContextHolder.getContext().setAuthentication(authentication);
				String jwt = jwtProvider.generateToken(authentication);
		        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
		        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
				x.get().setPassword(passwordEncoder.encode(json.getString("passwordNuevo")));
				u.save(x.get());
				return new ResponseEntity<>(jwtDto, HttpStatus.OK);
			}catch(BadCredentialsException e){
				respuesta.put("respuesta", "Contraseña o usuario incorrectos");
				return new ResponseEntity<>(respuesta.toString(), HttpStatus.CONFLICT);
			}
			 
		}else {
			return new ResponseEntity<>(new Mensaje("Las contraseñas no coinciden"), HttpStatus.BAD_REQUEST);
		}
    	
    }
    
    @GetMapping("/reset-password/{idCliente}")
    public ResponseEntity<?> resetPassword(@PathVariable("idCliente") int idCliente) {
    	JSONObject json =new JSONObject();
    	try {
    		Usuario usuario=usuarioService.getByNombreUsuario(String.valueOf(idCliente)).get();
        	Cliente cliente=clienteService.findById(idCliente);
        	String x[];
    		x = String.valueOf(cliente.getFechaNacimiento()).split(" ");
        	x = x[0].split("-");
        	String password=idCliente +"."+ x[0] + x[1] + x[2];
        	usuario.setPassword(passwordEncoder.encode(password));

            usuarioService.save(usuario);

    		json.put("respuesta", "Contraseña restablecida "+password);
    		return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    	}catch(NoSuchElementException e) {
		  	System.out.println("\u001B[31m"+"Error en la linea 278 ingresaron un id de cliente no valido idCliente="+idCliente+"\u001B[0m");

    		json.put("respuesta", "No se encontro al usuario ");
    		return new ResponseEntity<>(json.toString(), HttpStatus.CONFLICT);
    	}
    	
    	
    }
}
