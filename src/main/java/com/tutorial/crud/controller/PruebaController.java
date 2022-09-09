package com.tutorial.crud.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
import com.tutorial.crud.dto.ClienteDTOO;
import com.tutorial.crud.dto.HorarioDTO;
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
@CrossOrigin(origins = "*")
public class PruebaController 
{

	
	//-------------------------------------- WEB SERVICE SALAS------------------------------------------------------
	/**
	 * Metodo que muestra todos los Miembros almacenados en la base de datos
	 * @return lista de Miembro
	 */
	@GetMapping("/abrir")
	public ResponseEntity<?> obtenerSala()
	{
		
		return ResponseEntity.ok("abriendo la pluma");
	}
	
	

}//fin de la clase
