package com.tutorial.crud.controller;

import com.tutorial.crud.dto.*;
import com.tutorial.crud.entity.*;
import com.tutorial.crud.service.*;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/rutinas")
@CrossOrigin(origins = "*")
public class RutinaNuevoController {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    ClienteService clienteService;

    @Autowired
    RutinaNuevoService rutinaNuevoService;

    @Autowired
    RutinaEjercicioNuevoService rutinaEjercicioNuevoService;

    @Autowired
    EjercicioNuevoService ejercicioNuevoService;

    @Autowired
    AgendaReservasService agendaReservasService;

    @Autowired
    AgendaHorarioService agendaHorarioService;

    @Autowired
    AgendaReservasUsuarioService agendaReservasUsuarioService;


    // Crear una nueva plantilla rutina
    @PostMapping("/crearPlantillaRutina")
    @ResponseBody
    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<?> crearPlantillaRutina(@RequestBody ArrayPruebaNuevo body){
        JSONObject respuesta=new JSONObject();
        System.out.println("ENDPOINT crear plantilla rutina");
        try {
            ArrayPrueba2Nuevo nuevaRutina = body.getBody().get(0);
            //Obtener los ejercicios dentro de Array ejercicios del requestBody
            ArrayList<EjercicioNuevo> listaEjercicios = nuevaRutina.getEjercicios();

            RutinaNuevo rutina = new RutinaNuevo();
            rutina.setActivo(true);
            rutina.setCreated(LocalDateTime.now().withNano(0));
            rutina.setNombreObjetivo(nuevaRutina.getNombreObjetivo());
            rutina.setNombreRutina(nuevaRutina.getNombreRutina());
            rutina.setUpdated(LocalDateTime.now().withNano(0));
            rutina.setTipoPlantilla(nuevaRutina.getTipoPlantilla());
            rutina.setSemanas(nuevaRutina.getSemanas());
            rutina.setComentarios(nuevaRutina.getComentarios());

            List<RutinaEjercicioNuevo> rutinaEjercicios = new ArrayList<RutinaEjercicioNuevo>();
            //Se recorren los ejercicios del request y se guardan en RutinaEjercicioNuevo
            //System.out.println("Lista de ejercicios: " + listaEjercicios);
            for(EjercicioNuevo r: listaEjercicios){
                RutinaEjercicioNuevo rutinaejercicio = new RutinaEjercicioNuevo();

                System.out.println("r id: " + r.getId());
                Optional<EjercicioNuevo> ej = ejercicioNuevoService.findById(r.getId());

                if (!ej.isPresent()){
                    respuesta.put("respuesta", "El ejercicio con id: " + r.getId() + " no existe");
                    return new ResponseEntity<>(respuesta.toString(), HttpStatus.NOT_FOUND);
                }

                rutinaejercicio.setRutina(rutina);
                rutinaejercicio.setEjercicio(ej.get());
                //rutinaejercicio.setDia(ej.get().getDia());
                rutinaejercicio.setDia(r.getDia());
                rutinaEjercicios.add(rutinaejercicio);
                ej.get().setEjercicios(rutinaEjercicios);
            }
            rutina.setEjercicios(rutinaEjercicios);
            rutinaNuevoService.save(rutina);
            respuesta.put("respuesta", "Plantilla almacenada exitosamente");
            return new ResponseEntity<>(respuesta.toString(), HttpStatus.OK);
        }catch(Exception e) {
            e.printStackTrace();
            respuesta.put("respuesta", "No se pudo almacenar la plantilla nueva");
            return new ResponseEntity<>(respuesta.toString(), HttpStatus.CONFLICT);
        }
    }

    //Obtener rutina por id
    @GetMapping("/getPlantillaById")
    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<?> obtenerRutinaById(@RequestParam int idRutina){
        JSONObject json = new JSONObject();

        try{
            Optional<RutinaNuevo> rutinaNuevo = rutinaNuevoService.findById(idRutina);
            if (rutinaNuevo.isPresent() && rutinaNuevo.get().getActivo()){

                RutinaNuevoDTO rutina = new RutinaNuevoDTO();
                rutina.setNombreRutina(rutinaNuevo.get().getNombreRutina());
                rutina.setNombreObjetivo(rutinaNuevo.get().getNombreObjetivo());
                rutina.setSemanas(rutinaNuevo.get().getSemanas());
                rutina.setTipoPlantilla(rutinaNuevo.get().getTipoPlantilla());

                List<RutinaEjercicioNuevo> listaEjercicios = rutinaEjercicioNuevoService.findAllByRutinanuevo(rutinaNuevo.get());
                System.out.println("lista rutinaejericicio nuevo is empty ? : " + listaEjercicios.isEmpty());
                List<EjercicioNuevoDTO> ejercicioNuevos = new ArrayList<>();
                for (RutinaEjercicioNuevo ejercicios: listaEjercicios){
                    Optional<EjercicioNuevo> ejercicio = ejercicioNuevoService.findById(ejercicios.getEjercicio().getId());
                    EjercicioNuevoDTO nuevoDTO = new EjercicioNuevoDTO();
                    nuevoDTO.setId(ejercicio.get().getId());
                    nuevoDTO.setNombre(ejercicio.get().getNombre());
                    nuevoDTO.setDia(ejercicios.getDia());
                    nuevoDTO.setClub(ejercicio.get().getClub());
                    nuevoDTO.setNivel(ejercicio.get().getNivel());
                    nuevoDTO.setTipo(ejercicio.get().getTipo());
                    nuevoDTO.setImagen(ejercicio.get().getImagen());
                    //ejercicio.get().setDia(ejercicios.getDia());
                    ejercicioNuevos.add(nuevoDTO);
                }
                rutina.setEjercicios(ejercicioNuevos);
                return new ResponseEntity<>(rutina, HttpStatus.OK);
            }

            json.put("respuesta", "RUTINA " + idRutina + " no existe");
            return new ResponseEntity<>(json.toString(), HttpStatus.NOT_FOUND);
        }catch(Exception e){
            json.put("respuesta", "RUTINA " + idRutina + " no existe");
            return new ResponseEntity<>(json.toString(), HttpStatus.NOT_FOUND);
        }
    }

    // Actualizar plantilla
    @PutMapping("/actualizarPlantilla")
    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<?> actualizarPlantilla(@RequestBody ArrayPruebaNuevo nuevaRutina){
        //List<EjercicioDTO>listaEjercicios=nuevaRutina.getEjercicios();
        JSONObject json = new JSONObject();
        try {
            int idRutina = nuevaRutina.getBody().get(0).getId();
            Optional<RutinaNuevo> rutina = rutinaNuevoService.findById(idRutina);

            if (rutina.isPresent() && rutina.get().getActivo()){
                System.out.println("Rutina id found: " + rutina.get().getId());
                //primero eliminar los ejercicios de la rutina en rutina_ejercicios_nuevo

                RutinaNuevo rutinaActualizar = rutina.get();
                rutinaActualizar.setNombreRutina(nuevaRutina.getBody().get(0).getNombreRutina());
                rutinaActualizar.setComentarios(nuevaRutina.getBody().get(0).getComentarios());
                rutinaActualizar.setNombreObjetivo(nuevaRutina.getBody().get(0).getNombreObjetivo());
                rutinaActualizar.setSemanas(nuevaRutina.getBody().get(0).getSemanas());
                rutinaActualizar.setTipoPlantilla(nuevaRutina.getBody().get(0).getTipoPlantilla());
                //rutinaActualizar.setActivo(true);
                //rutinaActualizar.setCreated(rutina.get().getCreated());
                rutinaActualizar.setUpdated(LocalDateTime.now().withNano(0));

                if (!nuevaRutina.getBody().get(0).getEjercicios().isEmpty()) {
                    rutinaEjercicioNuevoService.deleteByRutinanuevo(rutina.get());

                    //obtener los nuevos ejercicios del request
                    ArrayList<EjercicioNuevo> listaEjercicios = nuevaRutina.getBody().get(0).getEjercicios();
                    List<RutinaEjercicioNuevo> rutinaEjercicios = new ArrayList<RutinaEjercicioNuevo>();


                    for(EjercicioNuevo r: listaEjercicios){
                        RutinaEjercicioNuevo rutinaejercicio = new RutinaEjercicioNuevo();

                        System.out.println("r id: " + r.getId());
                        Optional<EjercicioNuevo> ej = ejercicioNuevoService.findById(r.getId());

                        if (!ej.isPresent()){
                            json.put("respuesta", "El ejercicio con id: " + r.getId() + " no existe");
                            return new ResponseEntity<>(json.toString(), HttpStatus.NOT_FOUND);
                        }

                        rutinaejercicio.setRutina(rutina.get());
                        rutinaejercicio.setEjercicio(ej.get());
                        //rutinaejercicio.setDia(ej.get().getDia());
                        rutinaejercicio.setDia(r.getDia());
                        rutinaEjercicios.add(rutinaejercicio);
                        ej.get().setEjercicios(rutinaEjercicios);
                    }
                    rutina.get().setEjercicios(rutinaEjercicios);
                }
                rutinaNuevoService.save(rutinaActualizar);

                json.put("respuesta", "Rutina actualizada exitosamente");
                return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
            }
            //rutina = rutinaService.getOne(nuevaRutina.getIdPlantilla()).get();

            /*rutina.setActivo(true);
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
            }*/
            //rutina.setEjercicios(rutinaActualizada.getEjercicios());
            json.put("respuesta", "Rutina no existe");
            return new ResponseEntity<String>(json.toString(), HttpStatus.NOT_FOUND);
        }catch(IndexOutOfBoundsException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            json.put("respuesta", "IDs de ejercicios no existen");
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

    // Obtener plantillas activas y generales
    @GetMapping("/getByActivos")
    @ResponseBody
    public ResponseEntity<?> getPlantillaById() {

        List<RutinaNuevo> rutinaNuevos = rutinaNuevoService.findAllByActivoAndTipoPlantilla(true, "General");

        List<RutinaNuevoDTO> rutinasGenerales = new ArrayList<>();
        for (RutinaNuevo rutinaIterator: rutinaNuevos) {

            RutinaNuevoDTO rutina = new RutinaNuevoDTO();

            rutina.setId(rutinaIterator.getId());
            rutina.setNombreRutina(rutinaIterator.getNombreRutina());
            rutina.setNombreObjetivo(rutinaIterator.getNombreObjetivo());
            rutina.setSemanas(rutinaIterator.getSemanas());
            rutina.setTipoPlantilla(rutinaIterator.getTipoPlantilla());

            List<RutinaEjercicioNuevo> listaEjercicios = rutinaEjercicioNuevoService.findAllByRutinanuevo(rutinaIterator);
            System.out.println("lista rutinaejericicio nuevo is empty ? : " + listaEjercicios.isEmpty());
            List<EjercicioNuevoDTO> ejercicioNuevos = new ArrayList<>();
            for (RutinaEjercicioNuevo ejercicios: listaEjercicios){
                Optional<EjercicioNuevo> ejercicio = ejercicioNuevoService.findById(ejercicios.getEjercicio().getId());
                EjercicioNuevoDTO nuevoDTO = new EjercicioNuevoDTO();
                nuevoDTO.setId(ejercicio.get().getId());
                nuevoDTO.setNombre(ejercicio.get().getNombre());
                nuevoDTO.setDia(ejercicios.getDia());
                nuevoDTO.setClub(ejercicio.get().getClub());
                nuevoDTO.setNivel(ejercicio.get().getNivel());
                nuevoDTO.setTipo(ejercicio.get().getTipo());
                nuevoDTO.setImagen(ejercicio.get().getImagen());
                //ejercicio.get().setDia(ejercicios.getDia());
                ejercicioNuevos.add(nuevoDTO);
            }
            rutina.setEjercicios(ejercicioNuevos);
            rutinasGenerales.add(rutina);
        }
        //respuesta.put("respuesta", "Plantilla almacenada exitosamente");
        return new ResponseEntity<>(rutinasGenerales, HttpStatus.OK);
    }

    //obtener la rutina asignada a un cliente si existe
    @GetMapping("/getByCliente")
    @ResponseBody
    public ResponseEntity<?> getPlantillaByCliente(@RequestParam Integer idCliente) {

        JSONObject respuesta=new JSONObject();

        if (idCliente == null){
            respuesta.put("respuesta", "CLIENTE no existe");
            return new ResponseEntity<>(respuesta.toMap(), HttpStatus.NOT_FOUND);
        }
        Cliente cliente = clienteService.findById(idCliente);
        if (cliente != null){
            LocalDateTime now = LocalDateTime.now().withNano(0);
            System.out.println("NOW: " + now);
            if (cliente.obtenerRutinanuevo() == null){
                respuesta.put("respuesta", "CLIENTE no tiene una rutina asignada");
                return new ResponseEntity<>(respuesta.toMap(), HttpStatus.NOT_FOUND);
            } else {

                if(cliente.obtenerDiaFinalNuevo().isAfter(now)){
                    //List<RutinaNuevo> rutinaNuevos = rutinaNuevoService.findAllByActivo(true);
                    RutinaByCliente rutina = new RutinaByCliente();

                    rutina.setNombreRutina(cliente.obtenerRutinanuevo().getNombreRutina());
                    rutina.setNombreObjetivo(cliente.obtenerRutinanuevo().getNombreObjetivo());
                    rutina.setSemanas(cliente.obtenerRutinanuevo().getSemanas());
                    rutina.setInicio(cliente.getDiaInicioRutinanuevo());
                    System.out.println("CLiente dia final: " + cliente.obtenerDiaFinalNuevo());
                    //rutina.setFin(LocalDateTime.parse(cliente.obtenerDiaFinalNuevo()));
                    rutina.setFin(cliente.obtenerDiaFinalNuevo());

                    List<RutinaEjercicioNuevo> listaEjercicios = rutinaEjercicioNuevoService.findAllByRutinanuevo(cliente.obtenerRutinanuevo());
                    System.out.println("lista rutinaejericicio nuevo is empty ? : " + listaEjercicios.isEmpty());
                    List<EjercicioNuevoDTO> ejercicioNuevos = new ArrayList<>();
                    for (RutinaEjercicioNuevo ejercicios: listaEjercicios){
                        Optional<EjercicioNuevo> ejercicio = ejercicioNuevoService.findById(ejercicios.getEjercicio().getId());
                        EjercicioNuevoDTO nuevoDTO = new EjercicioNuevoDTO();
                        nuevoDTO.setId(ejercicio.get().getId());
                        nuevoDTO.setNombre(ejercicio.get().getNombre());
                        nuevoDTO.setDia(ejercicios.getDia());
                        nuevoDTO.setClub(ejercicio.get().getClub());
                        nuevoDTO.setNivel(ejercicio.get().getNivel());
                        nuevoDTO.setTipo(ejercicio.get().getTipo());
                        nuevoDTO.setImagen(ejercicio.get().getImagen());
                        //ejercicio.get().setDia(ejercicios.getDia());
                        ejercicioNuevos.add(nuevoDTO);
                    }
                    rutina.setEjercicios(ejercicioNuevos);
                    return new ResponseEntity<>(rutina, HttpStatus.OK);
                } else {
                    cliente.setRutinanuevo(null);
                    cliente.setSemanasnuevo(null);
                    cliente.setDiaInicioNuevo(null);
                    clienteService.save(cliente);
                    respuesta.put("respuesta", "CLIENTE no tiene una rutina asignada");
                    return new ResponseEntity<>(respuesta.toMap(), HttpStatus.NOT_FOUND);
                }
            }

        }else {
            respuesta.put("respuesta", "CLIENTE no existe");
            return new ResponseEntity<>(respuesta.toMap(), HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/asignarRutinaPlantilla")
    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<?> asignarRutinaPlantilla(@RequestBody Body body){
        JSONObject json=new JSONObject();

        int idCliente = body.getUsuario();
        int idRutina = body.getIdRutina();
        try {
            Cliente cliente = clienteService.findById(idCliente);
            RutinaNuevo rutinaCliente = cliente.obtenerRutinanuevo();
            Optional<RutinaNuevo> rutina = rutinaNuevoService.findById(idRutina);

            if (cliente == null){
                json.put("respuesta", "CLIENTE con id "+ idCliente + " no existe");
                return new ResponseEntity<String>(json.toString(), HttpStatus.NOT_FOUND);
            }
            if (!rutina.isPresent() || !rutina.get().getActivo()){
                json.put("respuesta", "RUTINA no existe");
                return new ResponseEntity<String>(json.toString(), HttpStatus.NOT_FOUND);
            }
            // si el cliente ya tiene una rutina asignada
            if(rutinaCliente != null) {
                LocalDateTime diaFinal = cliente.obtenerDiaFinalNuevo();
                System.out.println("Dia final: " + diaFinal);
                LocalDateTime now = LocalDateTime.now();
                if(diaFinal.isBefore(now)) {
                    this.eliminarRutinaByCliente(idCliente);
                    System.out.println("La fecha final  de la rutina ya paso");
                    rutina.get().setCliente(cliente);
                    cliente.setRutinanuevo(rutina.get());
                    cliente.setSemanas(rutina.get().getSemanas());
                    cliente.setDiaInicioNuevo(LocalDateTime.now().withNano(0));
                    //this.enviarCorreo(idCliente);
                    json.put("respuesta", "Rutina cargada exitosamente al cliente "+ idCliente);
                    return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
                }else {
                    throw new RuntimeException("CLIENTE ya tiene una rutina asignada");
                }

            }
            else {
                rutina.get().setCliente(cliente);
                cliente.setRutinanuevo(rutina.get());
                cliente.setSemanasnuevo(rutina.get().getSemanas());
                cliente.setDiaInicioNuevo(LocalDateTime.now().withNano(0));
                //this.enviarCorreo(idCliente);
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
            json.put("respuesta", "ID´s incorrectos");
            return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT);
        }

    }

    //eliminar rutina a un cliente
    @GetMapping("/deleteRutinaByCliente/{idCliente}")
    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<?> eliminarRutinaByCliente(@PathVariable("idCliente") int idCliente){

        JSONObject json=new JSONObject();
        try {
            Cliente cliente = clienteService.findById(idCliente);
            RutinaNuevo rutina = cliente.obtenerRutinanuevo();

            if (rutina != null){
                List<Cliente> clientes = rutina.obtenerClientes();

                if (clientes.contains(cliente)){
                    System.out.println("El cliente se encontro en la lista de clientes");
                    clientes.remove(cliente);
                    rutina.colocarCliente(clientes);
                    cliente.setRutinanuevo(null);
                    cliente.setDiaInicioNuevo(null);
                    cliente.setSemanasnuevo(null);
                    json.put("respuesta", "Rutina borrada exitosamente al cliente " + idCliente);
                    return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
                }
            }
            json.put("respuesta", "Cliente " + idCliente + " no tiene una rutina asignada");
            return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
            /*if(rutina.esPlantilla()) {
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
            }*/
        }catch(RuntimeException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            json.put("respuesta", "El usuario no tiene una rutina asignada");
            return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT);
        }catch(Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            json.put("respuesta", "CLIENTE incorrecto");
            return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT);
        }
    }

    //eliminar una rutina mediante su id
    @GetMapping("/deleteRutina/{idRutina}")
    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<?> eliminarRutina(@PathVariable("idRutina") int idRutina){

        JSONObject json=new JSONObject();
        try {
            Optional<RutinaNuevo> rutina = rutinaNuevoService.findById(idRutina);

            if (rutina.isPresent()){
                if (rutina.get().getCliente().isEmpty()){
                    rutina.get().setActivo(false);
                    rutinaNuevoService.save(rutina.get());
                    json.put("respuesta", "Rutina " + idRutina + " eliminada correctamente");
                    return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
                }

                json.put("respuesta", "Rutina " + idRutina + " no se puede eliminar tiene clientes asignados");
                return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT);
            }
            json.put("respuesta", "Rutina " + idRutina + " no existe");
            return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
        }catch(RuntimeException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            json.put("respuesta", "RUTINA no existe");
            return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT);
        }catch(Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            json.put("respuesta", "Rutina incorrecta");
            return new ResponseEntity<String>(json.toString(), HttpStatus.CONFLICT);
        }
    }



    //--------------------------------------- WEB SERVICE AGENDA----------------------------------------------------
    @RequestMapping(value="crearHorarioAgenda", method=RequestMethod.POST)
    public ResponseEntity<?> crearHorarioAgenda(@RequestBody HorarioDTO miHorario)
    {
        //SELECT id FROM ca_sala ORDER BY RANDOM() limit 1

        AgendaHorario nuevoHorario = new AgendaHorario();
        nuevoHorario.setActivo(true);
        nuevoHorario.setCreated(new Date());
        // obtener el contexto de seguridad en Spring Security y obtener el nombre del usuario conectado actualmente
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
        }//for
        return new ResponseEntity<>("Horario creado Correctamente", HttpStatus.OK);
    }


    @RequestMapping(value="crearReserva", method=RequestMethod.POST)
    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<String> crearReserva(@RequestBody Body body) {

        JSONObject json = new JSONObject();
        // Obtiene el apartado
        AgendaReservas apartado = agendaReservasService.getOne(body.getId());
        // Asigna el asesor si existe en el request
        apartado.setAsesor(body.getAsesor());
        // Obtiene el horario de ese apartado
        AgendaHorario horario1 = apartado.getHorario();

        // Se crea una nueva reserva y se busca al cliente
        AgendaReservasUsuario apartadosUsuario = new AgendaReservasUsuario();
        Cliente cliente = clienteService.findById(body.getUsuario());

        try {
            if(cliente.getEstatusCobranza().getIdEstatusCobranza() != 1) {
                throw new Exception("El usuario no esta activo y al corriente");
            }
            Session currentSession = entityManager.unwrap(Session.class);
            // Consulta para tener los apartados por usuario activos y mayores a la fecha de la consulta.
            Query<?> clasesDia = currentSession.createNativeQuery("select count(*) from agenda_reservas_usuario join agenda_reservas on "
                    + "agenda_reservas.id_apartados=agenda_reservas_usuario.id_reservas join agenda_horario on "
                    + "agenda_horario.id=agenda_reservas.id_horario_agenda where idcliente="+body.getUsuario()+" and "
                    + "agenda_reservas_usuario.activo is true and "
                    + "current_timestamp<TO_TIMESTAMP(agenda_reservas.dia||split_part(agenda_horario.rango, '-', 2),'YYYY-MM-DDHH24:MI');");

            // Se convierte a BigInteger el count de la consulta
            BigInteger cantidad = (BigInteger) clasesDia.getSingleResult();
            try {
                // Si el query trajo resultados y el cliente no tiene asignada una rutina
                if(cantidad.intValue() > 0 && cliente.obtenerRutinanuevo() == null) {
                    throw new ParseException("El cliente ya cuenta con una reserva para este día", 0);
                }else if(cliente.obtenerRutinanuevo() != null ){
                    // Si el el cliente tiene asignada una rutina y su rutina todavía no ha terminado
                    //SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                    //Date fecha = formato.parse(cliente.obtenerDiaFinalNuevo());
                    //Date diaSeleccionado = formato.parse(apartado.getDia());
                    LocalDateTime diaFinal = cliente.obtenerDiaFinalNuevo();
                    LocalDateTime diaSeleccionado = LocalDateTime.parse(apartado.getDia());
                    if(diaFinal.isAfter(diaSeleccionado)) {
                        throw new Exception("No se puede agendar cita si el usuario no ha completado su rutina");
                    }
                }
            }catch(ParseException e) {
                json.put("respuesta", "El cliente ya cuenta con una reserva para la fecha " + apartado.getDia());
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

        } catch (IOException e) {
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

    /*
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
    }*/
}
