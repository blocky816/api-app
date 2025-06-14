/*Y la clase ClienteServiceImpl.java, será implementada por la interfaz anterior. 
 * Le añadiremos la anotación @Service, para indicar que es un servicio y 
 * también de @Autowired para inyectar nuestro DAO y hacer uso de él:
 *	@autor: Daniel García Velasco 
 * 			Abimael Rueda Galindo
 *	@version: 1
 *12/07/2021
*/
package com.tutorial.crud.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;

import com.google.gson.annotations.JsonAdapter;
import com.tutorial.crud.controller.Servicios;
import com.tutorial.crud.dto.PlatinumUsers;
import com.tutorial.crud.entity.*;
import com.tutorial.crud.repository.ClienteRepository;
import com.tutorial.crud.scheduling.ScheduledTasks;
import com.tutorial.crud.security.service.UsuarioService;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tutorial.crud.aopDao.ClienteDAO;
import com.tutorial.crud.dto.ClienteDTOO;

@Service //marca la clase java que realiza algún servicio
public class ClienteServiceImpl implements ClienteService {

	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	@Autowired
	private EntityManager entityManager;
	
	@Autowired  //Inyecta a nuestro DAO y lo utiliza. 
	private ClienteDAO clienteDAO;
	//Método en el cual manda a llamar categoriaDAO y le asigna lo que tenga a la lista.

	@Autowired
	EstatusCobranzaService estatusCobranzaService;
	@Autowired
	EstatusClienteService estatusClienteService;
	@Autowired
	UsuarioService usuarioService;
	@Autowired
	ClienteRepository clienteRepository;
	@Autowired
	ParkingUsuarioService parkingUsuarioService;
	@Autowired
	Servicios servicios;
	@Autowired
	ClubService clubService;
	@Autowired
	TipoMembresiaService tipoMembresiaService;
	@Override 
	public List<Cliente> findAll() {
		List<Cliente> listCliente= clienteDAO.findAll();
        return listCliente;
	}
	//Método en el que se inserta el ID del DAO
	@Override
	public Cliente findById(int clave) {
		Cliente cliente = clienteDAO.findById(clave);
        return cliente;
	}
	
	@Override

    public Cliente findByNoMembresia(long clave) {
		Cliente cliente=clienteDAO.findByIdMembresia(clave);
		return cliente;
	}
	
	//Guarda todo lo de la lista al DAO 
	@Override
	public void save(Cliente cliente) {
		clienteDAO.save(cliente);		
	}
	@Override
	public boolean findCitas(Date fechaActual, CASala obtenerSala, int cliente) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<CAApartados> listaApartadosUsuario = currentSession.createQuery("select a.apartados FROM CAApartadosUsuario a where a.cliente.idCliente=:o and a.apartados.horario.sala.id=:u",CAApartados.class);
		listaApartadosUsuario.setParameter("o",cliente);
		listaApartadosUsuario.setParameter("u",obtenerSala.getId());
		List<CAApartados> lista = listaApartadosUsuario.getResultList();
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List<CAApartados> lista2=new ArrayList<CAApartados>();
		Long twenty = 60000L;
		for(int i=0;i<lista.size();i++) {
			String rango =lista.get(i).getHorario().getRango();
			String[] hora=rango.split("-");
			try {
				Date fecha = formato.parse(lista.get(i).getDia()+" "+hora[0]);
				Date fecha2 = formato.parse(lista.get(i).getDia()+" "+hora[1]);
				Date sports = new Date(fecha.getTime() - twenty * 21);
				if (lista.get(0).getHorario().getActividad().getPaga() == 500 && fechaActual.after(sports) && fecha.before(fecha2)) {
					//System.out.println("SOY DE SPORTS ALBERCA !! time => " + fechaActual + " sports => " + sports + " fecha => " + fecha + " fecha2 => " + fecha2);
					lista2.add(lista.get(i));
					continue;
				}
				if(fechaActual.after(fecha) && fechaActual.before(fecha2)) {
					lista2.add(lista.get(i));
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		if(lista2.size()>0)
			return true;
		return false;
		/*Session currentSession = entityManager.unwrap(Session.class);
		Query<CAApartados> listaApartadosUsuario = currentSession.createQuery("select a.apartados FROM CAApartadosUsuario a where a.cliente.IdCliente=:o and a.apartados.horario.sala.id=:u and a.activo=true",CAApartados.class);
		listaApartadosUsuario.setParameter("o",cliente);
		listaApartadosUsuario.setParameter("u",obtenerSala.getId());
		List<CAApartados> lista = listaApartadosUsuario.getResultList();
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List<CAApartados> lista2=new ArrayList<CAApartados>();
		for(int i=0;i<lista.size();i++) {
			try {
				Date fecha = formato.parse(lista.get(i).getDia()+" 00:00");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(fecha); // Configuramos la fecha que se recibe
				calendar.add(Calendar.DAY_OF_YEAR, 1);
				if(fechaActual.after(fecha) && fechaActual.before(calendar.getTime())) {
					lista2.add(lista.get(i));
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
		}
			if(lista2.size()>0) 
				return true;
			return false;*/
	}
	@Override
	public boolean findCitas(UUID idApartados, int cliente) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<CAApartados> listaApartadosUsuario = currentSession.createQuery("select a.apartados FROM CAApartadosUsuario a where a.cliente.idCliente=:o and a.apartados.id=:u",CAApartados.class);
		listaApartadosUsuario.setParameter("o",cliente);
		listaApartadosUsuario.setParameter("u",idApartados);
		List<CAApartados> lista = listaApartadosUsuario.getResultList();
		if(lista.size()>0) 
			return true;
		return false;
		/*Session currentSession = entityManager.unwrap(Session.class);
		Query<CAApartados> listaApartadosUsuario = currentSession.createQuery("select a.apartados FROM CAApartadosUsuario a where a.cliente.IdCliente=:o and a.apartados.horario.sala.id=:u and a.activo=true",CAApartados.class);
		listaApartadosUsuario.setParameter("o",cliente);
		listaApartadosUsuario.setParameter("u",obtenerSala.getId());
		List<CAApartados> lista = listaApartadosUsuario.getResultList();
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List<CAApartados> lista2=new ArrayList<CAApartados>();
		for(int i=0;i<lista.size();i++) {
			try {
				Date fecha = formato.parse(lista.get(i).getDia()+" 00:00");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(fecha); // Configuramos la fecha que se recibe
				calendar.add(Calendar.DAY_OF_YEAR, 1);
				if(fechaActual.after(fecha) && fechaActual.before(calendar.getTime())) {
					lista2.add(lista.get(i));
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
		}
			if(lista2.size()>0) 
				return true;
			return false;*/
	}
	@Override
	public List<ClienteDTOO> findClientesByIdClub(int clubId) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query listaClientes = currentSession.createNativeQuery("select idcliente, nombre,estatusacceso from cliente where idclub="+clubId+";");
		List<Object[]> listResults = listaClientes.getResultList();
		List<ClienteDTOO> listaDTO= new ArrayList<ClienteDTOO>();
		for (Object[] record : listResults) {
			ClienteDTOO cliente=new ClienteDTOO();
			cliente.setEstatusAccesos((String) record[2]);
			cliente.setIdCliente((int) record[0]);
			cliente.setNombre((String) record[1]);
			listaDTO.add(cliente);
			
		}
		return listaDTO;
	}
	@Override
	public CAApartados findApartados(Date fechaActual, CASala obtenerSala, int cliente) {
			Session currentSession = entityManager.unwrap(Session.class);
			//Query<CAApartados> listaApartadosUsuario = currentSession.createQuery("select a.apartados FROM CAApartadosUsuario a where a.cliente.idCliente=:o and a.apartados.horario.sala.id=:u",CAApartados.class);
		Query<CAApartados> listaApartadosUsuario = currentSession.createQuery("select a.apartados FROM CAApartadosUsuario a where a.cliente.idCliente=:o and a.apartados.horario.sala.id=:u and current_date = to_date(a.apartados.dia, 'YYYY-MM-DD')",CAApartados.class);
			listaApartadosUsuario.setParameter("o",cliente);
			listaApartadosUsuario.setParameter("u",obtenerSala.getId());
			List<CAApartados> lista = listaApartadosUsuario.getResultList();
			SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			List<CAApartados> lista2=new ArrayList<CAApartados>();
		Long twenty = 60000L;
			for(int i=0;i<lista.size();i++) {
				String rango =lista.get(i).getHorario().getRango();
				String[] hora=rango.split("-");
				try {
					
					Date fecha = formato.parse(lista.get(i).getDia()+" "+hora[0]);
					Date fecha2 = formato.parse(lista.get(i).getDia()+" "+hora[1]);
					Date sports = new Date(fecha.getTime() - twenty * 21);
					if (lista.get(0).getHorario().getActividad().getPaga() == 500 && fechaActual.after(sports) && fecha.before(fecha2)) {
						//System.out.println("FIND APARTADOS DE SPORTS ALBERCA !! time => " + fechaActual + " sports => " + sports + " fecha => " + fecha + " fecha2 => " + fecha2);
						lista2.add(lista.get(i));
						continue;
					} else if(fechaActual.after(fecha) && fechaActual.before(fecha2)) {
						lista2.add(lista.get(i));
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
			if(lista2.size()>0)
				return lista2.get(0);
			return null;
	}
	@Override
	public List<ClienteDTOO> asistenciaClientes(UUID id) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query listaClientes = currentSession.createNativeQuery("select cliente.idcliente,nombre from pase_consumido"
				+ " join ca_apartados_usuario on pase_consumido.apartado_usuario=ca_apartados_usuario.id "
				+ " join ca_apartados on ca_apartados_usuario.id_apartados=ca_apartados.id_apartados "
				+ " join cliente on ca_apartados_usuario.idcliente=cliente.idcliente"
				+ " where terminal_redencion_id is not null and ca_apartados.id_apartados='"+id+"';");
		List<Object[]> listResults = listaClientes.getResultList();
		List<ClienteDTOO> listaDTO= new ArrayList<ClienteDTOO>();
		for (Object[] record : listResults) {
			ClienteDTOO cliente=new ClienteDTOO();
			cliente.setIdCliente((int) record[0]);
			cliente.setNombre((String) record[1]);
			listaDTO.add(cliente);
			
		}
		return listaDTO;
	}
	@Override
	public List<ClienteDTOO> asistenciaGimnasioClientes(UUID id) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query listaClientes = currentSession.createNativeQuery("select idCliente,nombre from registro_gimnasio"
				+ " join cliente on cliente.idCliente=registro_gimnasio.id_cliente"
				+ " where id_apartados='"+id+"';");
		List<Object[]> listResults = listaClientes.getResultList();
		List<ClienteDTOO> listaDTO= new ArrayList<ClienteDTOO>();
		for (Object[] record : listResults) {
			ClienteDTOO cliente=new ClienteDTOO();
			cliente.setIdCliente((int) record[0]);
			cliente.setNombre((String) record[1]);
			listaDTO.add(cliente);			
		}
		return listaDTO;
	}

	@Override
	public List<Cliente> findAllByEstatusMembresia() {
		System.out.println("Iniciandp consulta de clientes activos a las: " + LocalTime.now());
		Session currentSession = entityManager.unwrap(Session.class);
		EstatusCobranza estatusCobranza = estatusCobranzaService.findById(1);
		System.out.println("EStatus cobranza : " + estatusCobranza.getNombre());
		Query<Cliente> clientesActivos = currentSession.createQuery("from Cliente c where c.estatusCobranza = :o and c.idCliente <> 0 ORDER BY c.idCliente ASC", Cliente.class);
		clientesActivos.setParameter("o", estatusCobranza);
		//clientesActivos.setMaxResults(1);

		List<Cliente> lista = clientesActivos.getResultList();

		System.out.println("Lista de clinetes ectivos: " + lista.size() + " at: " + LocalTime.now());

		return lista;
	}

	@Override
	public void activateCustomer(int customerID, int statusCobranza) {
		servicios.update(customerID);
		Cliente customer = findById(customerID);
		if (customer != null && Objects.nonNull(customer.getURLFoto())){
			EstatusCobranza paymentStatusActive = estatusCobranzaService.findById(statusCobranza);
			EstatusCliente customerStatus;
			if (paymentStatusActive.getIdEstatusCobranza() == 6) customerStatus = estatusClienteService.findById(2);
			else customerStatus = estatusClienteService.findById(1);
			customer.setEstatusCobranza(paymentStatusActive);
			customer.setEstatusCliente(customerStatus);
			if (paymentStatusActive.getIdEstatusCobranza() == 1) {
				customer.setEstatusAcceso("Acceso permitido");
				//customer.setTieneAcceso(true);
			}
			else {
				//customer.setTieneAcceso(false);
				customer.setEstatusAcceso("Sin Acceso");
			}
			save(customer);


			List<ParkingUsuario> pu = parkingUsuarioService.findByIdCliente(customer);
			if (Objects.nonNull(pu) && !customer.getTipoCliente().getNombre().toLowerCase().contains("temporal"))
				for (int i = 0; i < pu.size(); i++)
					try {
						pu.get(i).setEstadoCobranza(customer.getEstatusCobranza().getNombre());
						Date date = pu.get(i).obtenerRegistroTag().getFechaFin();
						LocalDateTime endDateTag = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
						if (customer.getEstatusCobranza().getIdEstatusCobranza() != 1 && endDateTag.isAfter(LocalDateTime.now()) && !customer.getTipoCliente().getNombre().toLowerCase().contains("empleado"))
							pu.get(i).obtenerRegistroTag().setActivo(false);
						else if (customer.getEstatusCobranza().getIdEstatusCobranza() == 1 && endDateTag.isAfter(LocalDateTime.now()))
							pu.get(i).obtenerRegistroTag().setActivo(true);
						parkingUsuarioService.save(pu.get(i));
					} catch (Exception e) {
						log.error("Error al desactivar/activar chips del user: {} => {}", customerID , e.toString());
					}
		} /*else {
			servicios.update(customerID);
		}*/
	}

	public String getPasswordHash(String customerID) {
		return usuarioService.getByNombreUsuario(customerID).isPresent() ?
				usuarioService.getByNombreUsuario(customerID).get().getPassword() : null;
	}

	@Override
	public String getSinEtapa(int club) throws IOException, InterruptedException {
		// create a client
		var client = HttpClient.newHttpClient();
		// create a request
		var request = HttpRequest.newBuilder(
						URI.create("http://192.168.20.107:8000/ServiciosClubAlpha/api/sin/etapas/" + club))
				.header("accept", "application/json")
				//.headers("Content-Type", "text/plain;charset=UTF-8")
				.GET()
				.build();

		// use the client to send the request
		var response = client.send(request, HttpResponse.BodyHandlers.ofString());
		// the response:
		//System.out.println("Response body getSinEtapas => " + response.body());
		return response.body();
	}

	@Override
	public String getConEtapa(int club) throws IOException, InterruptedException {
		// create a client
		var client = HttpClient.newHttpClient();
		// create a request
		var request = HttpRequest.newBuilder(
						URI.create("http://192.168.20.107:8000/ServiciosClubAlpha/api/con/etapas/" + club))
				.header("accept", "application/json")
				//.headers("Content-Type", "text/plain;charset=UTF-8")
				.GET()
				.build();

		// use the client to send the request
		var response = client.send(request, HttpResponse.BodyHandlers.ofString());
		// the response:
		//System.out.println("Response body getSinEtapas => " + response.body());
		return response.body();
	}

	@Override
	public void actualizarActivosxClub(int club) {
		try {
			log.info("Iniciando la actualizacion de activos y al corriente {} del club {} ...", dateFormat.format(new Date()), club);
			JSONArray activos = new JSONArray(getSinEtapa(club));
			int numNucleos = Runtime.getRuntime().availableProcessors();
			//System.out.println("num de nucleos activos => " + numNucleos);
			ExecutorService threadPool = Executors.newFixedThreadPool(numNucleos);
			for (int i = 0; i < activos.length(); i++) {
				final int finalI = i;
				threadPool.execute(() -> {
				String idStr = activos.getJSONObject(finalI).getString("id_cliente");
				try {
					int idCliente = Integer.parseInt(idStr.replace("/", "").trim());
					activateCustomer(idCliente, 1);
				} catch (NumberFormatException e) {
					log.error("Error parseando id {} => {}", idStr, e.toString());
				} catch (Exception e) {
					log.error("No se pudo activar al corriente el usuario: {} => {}", idStr, e.toString());
				}
				});
			}
			// Espera a que todos los hilos terminen
			threadPool.shutdown();
			//System.out.println(threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS));
			try {
				threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt(); // Restaura el estado de interrupción
				log.error("La espera de finalización del thread pool fue interrumpida.");
			}
			log.info("Clientes Alpha {} activos y al corriente actualizados a las {}", club, LocalTime.now().withNano(0));
		} catch (Exception e) {
			log.error("Error al actualizar activos y al corriente {} del club {} => {}", dateFormat.format(new Date()), club, e.toString());
			e.printStackTrace();
		}
	}

	@Override
	public void actualizarEtapasCanceladosxClub(int club) {
		try {
			JSONArray etapas = new JSONArray(getConEtapa(club));
			log.info("Iniciando la actualizacion de etapas {} del club {} ...", dateFormat.format(new Date()), club);
			int numNucleos = Runtime.getRuntime().availableProcessors();
			//System.out.println("num de nucleos etapas => " + numNucleos);
			ExecutorService threadPool = Executors.newFixedThreadPool(numNucleos);
			for (int i = 0; i < etapas.length(); i++) {
				final int finalI = i;
				threadPool.execute(() -> {
				String idStr = etapas.getJSONObject(finalI).getString("id_cliente");
				try {
					int idCliente = Integer.parseInt(idStr.replace("/", "").trim());
					switch (etapas.getJSONObject(finalI).getString("stage")) {
						case "E1":
							activateCustomer(idCliente, 2);
							break;
						case "E2":
							activateCustomer(idCliente, 3);
							break;
						case "E3":
							activateCustomer(idCliente, 4);
							break;
						case "cancel":
							activateCustomer(idCliente, 6);
							break;
					}
				} catch (NumberFormatException e) {
					log.error("Error parseando id {} => {}", idStr, e.toString());
				} catch (Exception e) {
					log.error("No se pudo actualizar el estatus de cobranza del usuario: {} => {}", idStr, e.toString());
				}
				});
			}
			// Espera a que todos los hilos terminen
			threadPool.shutdown();
			//System.out.println(threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS));
			try {
				threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt(); // Restaura el estado de interrupción
				log.error("La espera de finalización del thread pool fue interrumpida.");
			}
			log.info("Clientes Alpha {} bajas y etapas actualizados a las {}", club, LocalTime.now().withNano(0));
		} catch (Exception e) {
			log.error("Error al actualizar etapas y bajas {} del club {} => {}", dateFormat.format(new Date()) , club, e.toString());
			e.printStackTrace();
		}
	}

	@Override
	public Cliente findByIdOdoo(int OdooId) {
		return clienteRepository.findByIdOdoo(OdooId);
	}

	@Override
	public List<PlatinumUsers> getPlatinumUsersByClub(int club) {
		Club clubUsers = clubService.findById(club);

		// Verificar si el club no existe (es null)
		if (clubUsers == null) {
			return new ArrayList<>();  // Retorna un array vacío si no se encuentra el club
		}


		List<Cliente> clientes = new ArrayList<>();
		if (clubUsers.getNombre().toLowerCase().contains("cenit")){
			clientes = clienteRepository.findAllByClub(clubUsers);
		} else {
			TipoMembresia tipoMembresia = tipoMembresiaService.findById(21); // Platinum
			EstatusCobranza estatusCobranza = estatusCobranzaService.findById(1); // Activos al corriente
			clientes = clienteRepository.findAllByClubAndTipoMembresiaAndEstatusCobranza(clubUsers, tipoMembresia, estatusCobranza);
		}

		List<PlatinumUsers> platinumUsers = new ArrayList<>();

		if (!clientes.isEmpty())
			for (Cliente cliente: clientes) {
				PlatinumUsers platinumUser = new PlatinumUsers();
				platinumUser.setId(cliente.getIdCliente());
				platinumUser.setNombre(cliente.getNombreCompleto());
				platinumUser.setTipo_membresia(cliente.getCategoria().getNombre());
				platinumUser.setCupones_aplicados(new ArrayList<>());
				platinumUsers.add(platinumUser);
			}

		return platinumUsers;
	}

	public Boolean esMenorDeEdad(Date fechaNacimiento) {
		Calendar fechaActual = Calendar.getInstance();
		Calendar fechaNac = Calendar.getInstance();
		fechaNac.setTime(fechaNacimiento);
		int edad = fechaActual.get(Calendar.YEAR) - fechaNac.get(Calendar.YEAR);

		if (fechaActual.get(Calendar.MONTH) < fechaNac.get(Calendar.MONTH) || (fechaActual.get(Calendar.MONTH) == fechaNac.get(Calendar.MONTH) && fechaActual.get(Calendar.DAY_OF_MONTH) < fechaNac.get(Calendar.DAY_OF_MONTH))) {
			edad--;
		}

		return edad < 18;
	}

	// Método para calcular la edad
	public int calcularEdad(Date fechaNacimiento) {
		if (fechaNacimiento == null) {
			return 0; // o lanzar excepción, dependiendo de tu lógica
		}

		// Convertir Date a LocalDate
		LocalDate fechaNacimientoLocalDate = fechaNacimiento.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate();

		// Obtener la fecha actual
		LocalDate fechaActual = LocalDate.now();

		// Calcular la edad usando Period
		Period periodo = Period.between(fechaNacimientoLocalDate, fechaActual);
		return periodo.getYears(); // Devuelve la edad en años
	}



	/*public void sendNewPasswordHash(String userID) {
		var client = HttpClient.newHttpClient();
		var	newPasswordHash = getPasswordHash(userID);
		var request = HttpRequest.newBuilder(
				URI.create("http://192.168.20.107:8000/ServiciosClubAlpha/api/Usuarios/update/pwd"))
				.header("accept", "application/json")
				//.headers("Content-Type", "text/plain;charset=UTF-8")
				.POST(HttpRequest.BodyPublishers.ofString("{\n" +
						"\"id_cliente\":"+ Integer.parseInt(userID.trim()) + ",\n" +
						"\"new_pwd\":" + newPasswordHash + ",\n" +
						"}"))
				.build();

		try {
			var response = client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		// the response:
		//System.out.println("Response body pases alberca => " + response.body());
		//return response;
	}*/

	/*@Override
	public List<Cliente> findAllByClub(Club club) {
		return clienteRepository.findAllByClub(club);
	}*/

	/*public List<Integer> getIdDeportistasByIdCliente(int idCliente) {
		return clienteRepository.findDependientesIdByTitularId(idCliente);
	}*/
}
