package com.tutorial.crud.service;

import com.tutorial.crud.controller.Servicios;
import com.tutorial.crud.dto.ClienteVista;
import com.tutorial.crud.dto.ParkingUsuarioDTO;
import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.ParkingUsuario;
import com.tutorial.crud.entity.RHEmpleado;
import com.tutorial.crud.entity.RegistroTag;
import com.tutorial.crud.repository.ParkingUsuarioRepository;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class ParkingUsuarioService {

    @Autowired
    ParkingUsuarioRepository parkingUsuarioRepository;
    @Autowired
    ClienteService clienteService;

    @Autowired
    Servicios servicios;

    @Autowired RegistroTagService registroTagService;

    public List<ParkingUsuario> list(){
        return parkingUsuarioRepository.findAll();
    }

    public Optional<ParkingUsuario> getOne(int id){
        return parkingUsuarioRepository.findById(id);
    }
    
    public List<ParkingUsuario> findByIdCliente(Cliente cliente) {
    	return parkingUsuarioRepository.findByCliente(cliente);
    }
    public List<ParkingUsuario> findByRhEmpleado(RHEmpleado empleado) {
    	return parkingUsuarioRepository.findByRhEmpleado(empleado);
    }
    public List<ParkingUsuario> findByCapturado(boolean capturado) {
    	return parkingUsuarioRepository.findByCapturado(capturado);
    }

    public ParkingUsuario  save(ParkingUsuario actividad){
    	return parkingUsuarioRepository.save(actividad);
    }

    public ParkingUsuario getChipInfo(String chipID) {
        System.out.println("IDCHIP => " + chipID);
        ParkingUsuario parkingUsuario = parkingUsuarioRepository.findByObservaciones(String.valueOf(Integer.parseInt(chipID, 16)));
        System.out.println("Parking usuario => " + parkingUsuario);

        return parkingUsuario;
    }

    public List<ParkingUsuario> obtenerRecienteParkingUsuarios(int idUsuario) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://192.168.20.107:8000/parking/nuevo/" + idUsuario))
                .GET()
                .build();

        try {
            HttpResponse<String> respuesta = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONArray jarray = new JSONArray(respuesta.body());
            List<ParkingUsuario> lista = new ArrayList<>();

            for (int i = 0; i < jarray.length(); i++) {
                ParkingUsuario to = new ParkingUsuario();
                try {
                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                    Date fechaCaptura = formato.parse(jarray.getJSONObject(i).getString("fechaCaptura"));
                    to.setFechaCaptura(fechaCaptura);

                    Optional<ParkingUsuario> parkingUsuarioExistente = getOne(jarray.getJSONObject(i).getInt("idVentaDetalle"));
                    if (parkingUsuarioExistente.isPresent()) continue;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                String club = jarray.getJSONObject(i).getString("club");
                String concepto = jarray.getJSONObject(i).getString("concepto").toLowerCase();
                if (concepto.contains("empleado")) {
                    if (concepto.contains("cimera"))
                        club = "CIMERA";
                    else if (concepto.contains("alpha"))
                        club = club;
                    else if (concepto.contains("sp") || concepto.contains("sports plaza"))
                        club = "Sports Plaza";
                }

                to.setClub(club);
                to.setIdProd(jarray.getJSONObject(i).getInt("idProd"));
                to.setConcepto(jarray.getJSONObject(i).getString("concepto"));
                to.setIdVentaDetalle(jarray.getJSONObject(i).getInt("idVentaDetalle"));
                to.setObservaciones(jarray.getJSONObject(i).getString("observaciones"));
                to.setEstadoCobranza(jarray.getJSONObject(i).getString("estadoCobranza"));
                to.setCorreo(jarray.getJSONObject(i).getString("correo"));
                to.setCantidad(Math.round(jarray.getJSONObject(i).getFloat("cantidad")));
                to.setCliente(clienteService.findById(idUsuario));
                to.setPk(true);

                servicios.update(idUsuario);  // Actualizar servicio en cada iteración (o trasladarlo fuera si es necesario)
                lista.add(to);
                save(to);
            }
            return lista;
        } catch (Exception e){
            return null;
        }
    }

    public ParkingUsuario obtenerParkingUsuarioNoCapturado(int horarioId) {
        Cliente cliente = clienteService.findById(horarioId);
        if (cliente == null) {
            return null;  // Retorna null si el cliente no existe
        }
        List<ParkingUsuario> parkingUsuario = findByIdCliente(cliente);
        return parkingUsuario.stream()
                .filter(pu -> !pu.isCapturado() && !pu.getObservaciones().isEmpty())
                .findFirst()
                .orElse(null); // Retorna el primer no capturado o null si no hay ninguno
    }

    public Optional<Long> obtenerIdChip(ParkingUsuario usuario) {
        try {
            return Optional.of(Long.parseLong(usuario.getObservaciones().trim()));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public boolean validarChipActivo(Long idChip) {
        RegistroTag registroTag = registroTagService.findByIdChip(idChip);
        if (Objects.nonNull(registroTag))
            return registroTag.isActivo();
        else
            return false;
    }

    public ResponseEntity<?> generarRespuestaFinal(ParkingUsuario usuario, Long idChip) {
        RegistroTag registroTag = registroTagService.findByIdChip(idChip);

        if (Objects.isNull(registroTag)) {
            return new ResponseEntity<>(crearRespuestaError("ID de chip: " + idChip + " ya esta asignado o no existe en el sistema").toMap(), HttpStatus.BAD_REQUEST);
        }

        // Si el tipo de cliente contiene "empleado", cambiamos el club en registroTag
        if (usuario.getCliente().getTipoCliente().getNombre().toLowerCase().contains("empleado")) {
            registroTag.setClub(usuario.getClub());
        }
        usuario.setRegistroTag(registroTag);
        registroTag.setParking(usuario);

        ParkingUsuarioDTO vista = new ParkingUsuarioDTO();
        vista.setCliente(crearClienteVista(usuario));
        vista.setCantidad(usuario.getCantidad());
        vista.setConcepto(usuario.getConcepto());
        vista.setFechaCaptura(usuario.getFechaCaptura());
        vista.setIdProd(usuario.getIdProd());
        vista.setIdVentaDetalle(usuario.getIdVentaDetalle());
        vista.setPk(usuario.isPk());
        vista.setObservaciones(usuario.getObservaciones());
        vista.setVigencia(calcularVigenciaChip(usuario.getFechaCaptura()));

        save(usuario);
        return new ResponseEntity<>(vista, HttpStatus.OK);
    }

    private ClienteVista crearClienteVista(ParkingUsuario usuario) {
        ClienteVista clienteVista = new ClienteVista();
        clienteVista.setClienteTipo(usuario.getCliente().getTipoCliente().getNombre());
        clienteVista.setClub(usuario.getCliente().getClub().getNombre());
        clienteVista.setIdCLiente(usuario.getCliente().getIdCliente());
        clienteVista.setMembresia(usuario.getCliente().getNoMembresia());
        clienteVista.setNombre(usuario.getCliente().getNombre() + " " +
                usuario.getCliente().getApellidoPaterno() + " " +
                usuario.getCliente().getApellidoMaterno());
        return clienteVista;
    }

    private Date calcularVigenciaChip(Date fechaCaptura) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaCaptura);
        calendar.add(Calendar.YEAR, 1);
        return calendar.getTime();
    }

    private JSONObject crearRespuestaError(String mensaje) {
        JSONObject json = new JSONObject();
        json.put("respuesta", mensaje);
        return json;
    }
}
