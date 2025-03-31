package com.tutorial.crud.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import com.tutorial.crud.Odoo.Spec.dto.ClientePase;
import com.tutorial.crud.Odoo.Spec.dto.PaseQR;
import com.tutorial.crud.controller.Servicios;
import com.tutorial.crud.dto.MovimientoDTO;
import com.tutorial.crud.entity.CAClase;
import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.PaseUsuario;
import com.tutorial.crud.Odoo.Spec.entity.PaseHealthStudioConsumido;
import com.tutorial.crud.exception.ClienteNoEncontradoException;
import com.tutorial.crud.repository.CAClaseRepository;
import com.tutorial.crud.repository.PaseUsuarioRepository;

import com.tutorial.crud.Odoo.Spec.repository.PasesHealthStudioConsumidoRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.http.HttpResponse.BodyHandlers;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Service
@Transactional
public class PaseUsuarioService {

	private static final Logger logger = Logger.getLogger(PaseUsuarioService.class.getName());

    @Autowired
    PaseUsuarioRepository paseUsuarioRepository;

	@Autowired
	PasesHealthStudioConsumidoRepository pasesHealthStudioConsumidoRepository;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private ConfiguracionSancionService configuracionSancionService;

	@Autowired
	private Servicios servicios;

	@Autowired
	private CAClaseRepository caClaseRepository;

    public List<PaseUsuario> list(){
        return paseUsuarioRepository.findAll();
    }

    public Optional<PaseUsuario> getOne(int id){
        return paseUsuarioRepository.findById(id);
    }

    public PaseUsuario  save(PaseUsuario paseUsuario){
    	return paseUsuarioRepository.save(paseUsuario);
    }

	public List<PaseUsuario> saveAll(List<PaseUsuario> paseUsuarios) { return  paseUsuarioRepository.saveAll(paseUsuarios); }

	public List<PaseUsuario> getByActivo(boolean activo) {
    	return paseUsuarioRepository.findByActivo(activo).get();
	}

	public List<PaseUsuario> getByIdCliente(int usuario) {
		deactivateExpiredPasses(usuario);
		deactivateExpiredPassesForUser(usuario);
		try {
			servicios.getMovimientos(usuario);
		} catch (Exception e) {
			logger.warning("Fallo el consultar movimientos para usuario: " + usuario);
		}
		Session currentSession = entityManager.unwrap(Session.class);
		Query<PaseUsuario> listaPaseUsuario = currentSession.createQuery("" +
				"FROM PaseUsuario p WHERE " +
				"((p.cliente.idCliente=:o AND p.idProd=1746) OR " +
				"(p.cliente.idCliente=:o AND p.disponibles>0)) " +
				"AND p.activo = true " +
				"AND lower(p.concepto) NOT LIKE '%placa%' " +
				"ORDER BY idVentaDetalle", PaseUsuario.class);
		listaPaseUsuario.setParameter("o",usuario);
		List<PaseUsuario> results = listaPaseUsuario.getResultList();
		return results;
	}

	private void deactivateExpiredPassesForUser(int usuario) {
		LocalDateTime now = LocalDateTime.now(); // Fecha y hora actuales
		Session currentSession = entityManager.unwrap(Session.class);

		// Actualizar los pases vencidos del usuario
		Query query = currentSession.createQuery(
				"UPDATE PaseUsuario p SET p.activo = false " +
						"WHERE p.cliente.idCliente = :o " +
						"AND (p.fechaVigencia < :currentDate OR p.disponibles <= 0)" +
						"AND p.activo = true"
		);
		query.setParameter("o", usuario);
		query.setParameter("currentDate", now);
		int updatedCount = query.executeUpdate();

		logger.info(updatedCount + " pases vencidos desactivados para el usuario: " + usuario);
	}

	public List<PaseUsuario> getByIdClienteGimnasio(int usuario) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<PaseUsuario> listaPaseUsuario = currentSession.createQuery("FROM PaseUsuario p where p.cliente.idCliente=:o and p.idProd=1746 and p.activo=true order by idVentaDetalle", PaseUsuario.class);
		listaPaseUsuario.setParameter("o",usuario);
		List<PaseUsuario> results = listaPaseUsuario.getResultList();
		
		return results;
	}
	
	public boolean cancelarPasesVencidos(int usuario) {
		Session currentSession = entityManager.unwrap(Session.class);
		//String hql = "update PaseUsuario p set p.activo=false where p.cliente.IdCliente in (SELECT c.IdCliente FROM Cliente c WHERE c.FechaFinAcceso<=current_date()) and p.concepto like '% Gym %'";
		//String hql = "update PaseUsuario p set p.activo = false where p.idVentaDetalle = 410750";
		String hql = "update PaseUsuario p set p.activo=false where p.cliente.idCliente in (SELECT c.idCliente FROM Cliente c WHERE c.FechaFinAcceso<=current_date()) and (lower(p.concepto) like '% gym %' or lower(p.concepto) like '% gimnasio %')";
		Query query = currentSession.createQuery(hql);
		query.executeUpdate();

		return true;
	}
	
	public boolean activarPases(int usuario) {
		Session currentSession = entityManager.unwrap(Session.class);
		  // your code
		  //String hql = "update PaseUsuario p set p.activo=true where p.cliente.IdCliente in (SELECT c.IdCliente FROM Cliente c WHERE c.FechaFinAcceso>=current_date()) and p.concepto like 'SP % Gym %'";
		String hql = "update PaseUsuario p set p.activo=true where p.cliente.idCliente in (SELECT c.idCliente FROM Cliente c WHERE c.FechaFinAcceso>=current_date()) and (lower(p.concepto) like '% gym %' or lower(p.concepto) like '% gimnasio %')";
		  Query<?> query = currentSession.createQuery(hql);
		  // your code end
		  query.executeUpdate();
		  return true;
	}

	public List<PaseUsuario> getPasesGimnasio(int usuario) {
		Session currentSession = entityManager.unwrap(Session.class);
		//Query<PaseUsuario> listaPaseUsuario = currentSession.createQuery("FROM PaseUsuario p where (p.cliente.IdCliente=:o and p.concepto like 'SP % Gym %') and p.activo=true order by idVentaDetalle", PaseUsuario.class);
		Query<PaseUsuario> listaPaseUsuario = currentSession.createQuery("FROM PaseUsuario p where (p.cliente.idCliente=:o and (lower(p.concepto) like '% gym %' or lower(p.concepto) like '% gimnasio %')) and p.activo=true order by idVentaDetalle", PaseUsuario.class);
		listaPaseUsuario.setParameter("o",usuario);
		List<PaseUsuario> results = listaPaseUsuario.getResultList();
		return results;
	}
	/*public List<PaseUsuario> getPasesAlberca(int usuario) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<PaseUsuario> listaPaseUsuario = currentSession.createQuery("FROM PaseUsuario p where (p.cliente.IdCliente=:o and (p.idProd>=1847 and p.idProd<=1849)) and p.activo=true order by idVentaDetalle desc", PaseUsuario.class);
		listaPaseUsuario.setParameter("o",usuario);
		List<PaseUsuario> results = listaPaseUsuario.getResultList();
		
		return results;
	}*/

	public boolean getPasesAlberca(int usuario) throws IOException, InterruptedException {
		// create a client
		var client = HttpClient.newHttpClient();
		// create a request
		var request = HttpRequest.newBuilder(
				URI.create("http://192.168.20.107:8000/ServiciosClubAlpha/api/sports/alberca/"))
				.header("accept", "application/json")
				//.headers("Content-Type", "text/plain;charset=UTF-8")
				.POST(HttpRequest.BodyPublishers.ofString("{\n" +
						"    \"usuario\":"+usuario+",\n" +
						"    \"terminal\":\"69\",\n" +
						"    \"super\":false,\n" +
						"    \"idVentaDetalle\":\"1101877\"\n" +
						"}"))
				.build();

		// use the client to send the request
		var response = client.send(request, BodyHandlers.ofString());
		// the response:
		//System.out.println("Response body pases alberca => " + response.body());
		//return response;
		return Boolean.parseBoolean(response.body());
	}
	
	public List<PaseUsuario> getPasesClasesNado(int usuario) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<PaseUsuario> listaPaseUsuario = currentSession.createQuery("FROM PaseUsuario p where (p.cliente.idCliente=:o and (p.idProd>=1834 and p.idProd<=1846)) and p.activo=true order by idVentaDetalle desc", PaseUsuario.class);
		listaPaseUsuario.setParameter("o",usuario);
		List<PaseUsuario> results = listaPaseUsuario.getResultList();
		
		return results;
	}
	
	public List<PaseUsuario> getByIdClienteQR(int usuario) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<PaseUsuario> listaPaseUsuario = currentSession.createQuery("FROM PaseUsuario p where p.cliente.idCliente=:o and (p.idProd=2503) and p.disponibles>0 and p.activo=true order by idVentaDetalle", PaseUsuario.class);
		//Query<PaseUsuario> listaPaseUsuario = currentSession.createQuery("FROM PaseUsuario p where p.cliente.IdCliente=:o and (p.idProd=1808 or p.idProd=1856) and p.disponibles>0 and p.activo=true order by idVentaDetalle", PaseUsuario.class);
		listaPaseUsuario.setParameter("o",usuario);
		List<PaseUsuario> results = listaPaseUsuario.getResultList();
		
		return results;
	}

	public List<PaseUsuario> getPasesTrote(int usuario) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<PaseUsuario> listaPaseUsuario = currentSession.createQuery("FROM PaseUsuario p where (p.cliente.idCliente=:o and (p.idProd=1746)) and p.activo=true order by idVentaDetalle desc", PaseUsuario.class);
		listaPaseUsuario.setParameter("o",usuario);
		List<PaseUsuario> results = listaPaseUsuario.getResultList();
		
		return results;
	}

	public PaseUsuario findByIdVentaDetalle(int idVentaDetalle) {
		return paseUsuarioRepository.findByIdVentaDetalle(idVentaDetalle);
	}

	public Optional<PaseUsuario> getPasesDisponibles(int clienteId, int idProd) {
		Cliente cliente = getTitularCliente(clienteId);
		List<PaseUsuario> pases = paseUsuarioRepository.findByClienteAndIdProdAndActivoTrueOrderByCreated(cliente, idProd);

		if (!pases.isEmpty()) {
			PaseUsuario primerPase = pases.get(0);
			if (primerPase.getDisponibles() > 0) {
				// Descontar uno de los disponibles
				primerPase.setDisponibles(primerPase.getDisponibles() - 1);
				primerPase.setConsumido(primerPase.getConsumido() + 1);
				primerPase.setUltimoUso(LocalDateTime.now());

				String concepto = primerPase.getConcepto().toLowerCase();
				if (concepto.contains("plus") && concepto.contains("alpha") && concepto.contains("cimera")) cliente.setUltimoUso(LocalDateTime.now());
				if (primerPase.getDisponibles() <= 0) primerPase.setActivo(false);
				// Guardar el pase actualizado
				paseUsuarioRepository.save(primerPase);
				return Optional.of(primerPase);
			}
		}
		return Optional.empty();
	}

	public Cliente getTitularCliente(int clienteId) {
		// Obtener el cliente por su ID
		Cliente cliente = clienteService.findById(clienteId);
		if (cliente == null) {
			throw new ClienteNoEncontradoException("Cliente no encontrado con ID: " + clienteId);
		}

		// Si el cliente es titular, devolverlo directamente
		if (cliente.getIdTitular() == clienteId) {
			return cliente;
		}

		// Si es dependiente, buscar el cliente titular
		Cliente titularCliente = clienteService.findById(cliente.getIdTitular());
		if (titularCliente == null) {
			throw new ClienteNoEncontradoException("Titular no encontrado con ID: " + cliente.getIdTitular());
		}

		return titularCliente;
	}

	public List<PaseUsuario> activatePasses(int idCliente, List<MovimientoDTO> movimientoDTO){
		Cliente titular = getTitularCliente(idCliente);
		List<PaseUsuario> pases = paseUsuarioRepository.findByClienteAndActivoFalseAndPagadoIsNullAndFechaPagoIsNull(titular);
		Map<Long, LocalDateTime> ordenesDeVenta = getIdVentaDetallePagos(movimientoDTO);

		for (PaseUsuario pase : pases) {
			if (ordenesDeVenta.containsKey((long) pase.getIdVentaDetalle())) {
				pase.setActivo(true);
				pase.setPagado(true);
				pase.setFechaPago(ordenesDeVenta.get((long) pase.getIdVentaDetalle()));
				pase.calcularVigenciaSiAplica();
				save(pase);
			}
		}
		return pases;
	}

	public Map<Long, LocalDateTime> getIdVentaDetallePagos(List<MovimientoDTO> movimientos) {
		Map<Long, LocalDateTime> idOrdenDeVentaDetalleMap = new HashMap<>();
		for (MovimientoDTO movimiento : movimientos) {
			if (!movimiento.getFolio().isEmpty()) {
				LocalDateTime fechaAplicacion = convertStringToLocalDateTime(movimiento.getFechaDeAplicacion());
				idOrdenDeVentaDetalleMap.put(movimiento.getIdordenDeVentaDetalle(), fechaAplicacion);
			}
		}
		return idOrdenDeVentaDetalleMap;
	}

	public LocalDateTime convertStringToLocalDateTime(String fechaStr) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Define el formato de fecha
		LocalDate fecha = LocalDate.parse(fechaStr, formatter); // Convertir a LocalDate
		return fecha.atTime(23, 59, 59);

	}

	public void deactivateExpiredPasses(int idCliente) {
		Cliente titular = getTitularCliente(idCliente);
		// Obtener todos los pases activos del cliente
		List<PaseUsuario> pasesActivos = paseUsuarioRepository.findByClienteAndActivoTrueAndPagadoIsNotNullAndFechaPagoIsNotNull(titular);
		// ID de productos que requieren vigencia de un mes
		Set<Integer> productosConVigencia = configuracionSancionService.getCodigoByConcepto("plus alpha");

		if (!pasesActivos.isEmpty()){
			for (PaseUsuario pase : pasesActivos) {
				if (productosConVigencia.contains(pase.getIdProd())) {
					LocalDateTime fechaPago = pase.getFechaPago();

					// Calcular si la fecha de pago más un mes ha pasado
					if (fechaPago != null && fechaPago.plus(1, ChronoUnit.MONTHS).isBefore(LocalDateTime.now())) {
						pase.setActivo(false);
						save(pase);
					}
				}
			}
		}
	}

//	public List<CAClase> getAquadomeClasses(String dia) {
//		return paseUsuarioRepository.getAquadomeClasses(dia);
//	}

	public List<CAClase> getAquadomeClasses(int idCliente, String dia) {
		Cliente cliente = clienteService.findById(idCliente);
		if (cliente == null) {
			throw new IllegalArgumentException("Cliente no encontrado: " + idCliente);
		}
		//logger.info("Cliente encontrado: " + idCliente);

		Set<Integer> productoAquadome = configuracionSancionService.getCodigoByConcepto("aquadome");
		if (productoAquadome.isEmpty()) {
			return Collections.emptyList();
		}
		//productoAquadome.forEach(codigo -> System.out.println("Producto Aquadome: " + codigo));

		List<PaseUsuario> pases = paseUsuarioRepository.findByClienteAndUltimoUsoIsNotNullAndFechaPagoMesActual(cliente, productoAquadome);
		//logger.info("Pases encontrados aquadome: " + pases.size());

		// Parsear la fecha del día recibido
		LocalDate fechaConsulta = LocalDate.parse(dia);
		//logger.info("Dia parseado a date: " + dia);

		// Verificar si hay pases disponibles y si el último uso es del mismo día
		boolean activo = pases.stream().anyMatch(pase -> pase.getActivo());
		boolean tienePasesDisponibles = pases.stream().anyMatch(pase -> pase.getDisponibles() >= 0);
		boolean ultimoUsoEsDelDia = pases.stream()
				.filter(pase -> pase.getUltimoUso() != null)
				.anyMatch(pase -> pase.getUltimoUso().toLocalDate().isEqual(fechaConsulta));

		if (ultimoUsoEsDelDia || activo) {
			//logger.info("Pase correcto devolviendo clases aquadome para: " + idCliente);
			return caClaseRepository.getAquadomeClasses(dia);
		}

		return Collections.emptyList();
	}

	public boolean getCimeraPlus(int idCliente) {
		Cliente cliente = clienteService.findById(idCliente);
		if (cliente == null) {
			return false;
		}

		Set<Integer> productoCimeraPlus = getCodigosByConceptos("plus", "cimera");
		if (productoCimeraPlus.isEmpty()) {
			return false;
		}
		//productoCimeraPlus.forEach(codigo -> System.out.println("Producto Cimera Plus: " + codigo));

		List<PaseUsuario> pases = paseUsuarioRepository.findByClienteAndUltimoUsoIsNotNullAndFechaPagoMesActual(cliente, productoCimeraPlus);
		//logger.info("Pases encontrados cimera: " + pases.size());

		// Parsear la fecha del día recibido
		LocalDate fechaConsulta = LocalDate.now();
		//logger.info("Fecha consulta: " + fechaConsulta);

		boolean activo = pases.stream().anyMatch(pase -> pase.getActivo());
		boolean ultimoUsoEsDelDia = pases.stream()
				.filter(pase -> pase.getUltimoUso() != null)
				.anyMatch(pase -> pase.getUltimoUso().toLocalDate().isEqual(fechaConsulta));

		return ultimoUsoEsDelDia || activo;
	}

	public Set<Integer> getCodigosByConceptos(String... conceptos) {
		// Comenzar a construir la consulta base
		StringBuilder queryBuilder = new StringBuilder("SELECT c.codigo FROM ConfiguracionSancion c WHERE ");

		// Agregar condiciones para cada concepto
		for (int i = 0; i < conceptos.length; i++) {
			queryBuilder.append("LOWER(c.concepto) LIKE LOWER(:concepto").append(i).append(")");
			if (i < conceptos.length - 1) {
				queryBuilder.append(" AND ");
			}
		}

		// Crear la consulta
		TypedQuery<Integer> query = entityManager.createQuery(queryBuilder.toString(), Integer.class);

		// Establecer los parámetros
		for (int i = 0; i < conceptos.length; i++) {
			query.setParameter("concepto" + i, "%" + conceptos[i].toLowerCase() + "%");
		}

		return new HashSet<>(query.getResultList());
	}

	public List<PaseQR> obtenerPasesQRPorIdVentaDetalle(Integer idCliente, Integer idVentaDetalle) {
		Cliente cliente = getTitularCliente(idCliente);
		List<PaseUsuario> pasesDisponibles = paseUsuarioRepository.findByClienteAndIdVentaDetalleAndActivoTrue(cliente, idVentaDetalle);

		// Usar un logger en lugar de System.out.println
		logger.info("Cliente: " + cliente.getIdCliente() + " Pases disponibles: "  + pasesDisponibles.size());

		return pasesDisponibles.stream()
				.map(paseUsuario -> crearPaseQR(cliente, paseUsuario))
				.collect(Collectors.toList());
	}

	private PaseQR crearPaseQR(Cliente cliente, PaseUsuario paseUsuario) {
		ClientePase clientePase = new ClientePase();
		clientePase.setNombre(cliente.getNombreCompleto());
		clientePase.setFoto(cliente.getURLFoto().getImagen());

		PaseQR paseQR = new PaseQR();
		paseQR.setCliente(clientePase);
		paseQR.setDisponibles(paseUsuario.getDisponibles());
		paseQR.setIdVentaDetalle(paseUsuario.getIdVentaDetalle());
		paseQR.setConcepto(paseUsuario.getConcepto());
		paseQR.setFechaVigencia(paseUsuario.getFechaVigencia());

		return paseQR;
	}

	public Boolean consumirPaseQR(Integer idCliente, Integer idVentaDetalle, String consumidoPor) {
		Cliente cliente = getTitularCliente(idCliente);
		List<PaseUsuario> pasesDisponibles = paseUsuarioRepository.findByClienteAndIdVentaDetalleAndActivoTrue(cliente, idVentaDetalle);

		if (!pasesDisponibles.isEmpty()) {
			PaseUsuario paseUsuario = pasesDisponibles.get(0);
			return procesarConsumoPase(paseUsuario, consumidoPor);
		}

		return false;
	}


	private Boolean procesarConsumoPase(PaseUsuario paseUsuario, String consumidoPor) {
		if (paseUsuario.getDisponibles() > 0) {
			paseUsuario.setDisponibles(paseUsuario.getDisponibles() - 1);
			paseUsuario.setConsumido(paseUsuario.getConsumido() + 1);

			if (paseUsuario.getDisponibles() == 0) {
				paseUsuario.setActivo(false);
			}

			//Guardamos el registro de pase consumido
			var paseConsumido = new PaseHealthStudioConsumido();
			paseConsumido.setPaseUsuario(paseUsuario);
			paseConsumido.setFechaConsumo(LocalDateTime.now().withNano(0));
			paseConsumido.setConsumidoPor(consumidoPor);
			pasesHealthStudioConsumidoRepository.save(paseConsumido);

			paseUsuarioRepository.save(paseUsuario);
			return true;
		}
		return false;
	}

}
