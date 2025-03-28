package com.tutorial.crud.Odoo.Spec.service;

import com.tutorial.crud.Odoo.Spec.dto.PaginacionDTO;
import com.tutorial.crud.Odoo.Spec.dto.PaseHealthStudioDTO;
import com.tutorial.crud.Odoo.Spec.dto.PaseReporteDTO;
import com.tutorial.crud.Odoo.Spec.repository.PasesHealthStudioConsumidoRepository;
import com.tutorial.crud.controller.Servicios;
import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.entity.PaseUsuario;
import com.tutorial.crud.repository.PaseUsuarioRepository;
import com.tutorial.crud.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaseHealthStudioConsumidoService {
    @Autowired
    private PasesHealthStudioConsumidoRepository repository;

    @Autowired
    private PaseUsuarioRepository paseUsuarioRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private Servicios servicios;

    public PaginacionDTO getConsumosPaged(LocalDateTime startDate, LocalDateTime endDate, int page, int size) {
        long totalRecords = repository.countConsumosByFecha(startDate, endDate, "studio", "fisio", "hidro", "spa");
        Pageable pageable = PageRequest.of(page, size);
        List<PaseReporteDTO> registros = repository.findConsumosByFechaPaged(startDate, endDate, "studio", "fisio", "hidro", "spa", pageable);

        // Calcular el número total de páginas
        int totalPages = (int) Math.ceil((double) totalRecords / size);

        return new PaginacionDTO(registros, totalPages, totalRecords);
    }

    /*public List<PaseReporteDTO> obtenerConsumosPorFecha(LocalDateTime startDate, LocalDateTime endDate) throws InvalidDateTimeException {
        if (startDate == null || endDate == null)
            throw new InvalidDateTimeException("Las fechas de inicio y fin son obligatorias.");

        if (startDate.isAfter(endDate))
            throw new InvalidDateTimeException("La fecha de inicio no puede ser posterior a la fecha de fin.");

        return repository.findConsumosByFecha(startDate, endDate);
    }*/

    public PaseHealthStudioDTO obtenerPasesQR(Integer idCLiente) {
        String[] productosHealthStudio = {"%studio%", "%fisio%", "%hidro%", "%spa%"};
        Cliente cliente = clienteService.findById(idCLiente);
        try {
            servicios.getMovimientos(idCLiente);
        } catch (Exception e) {
            System.out.println("Fallo el consultar movimientos para usuario: " + idCLiente);
        }
        //List<PaseUsuario> paseUsuario = paseUsuarioRepository.findByClienteAndIdProdInAndActivoTrue(cliente, productosHealthStudio);
        //List<PaseUsuario> paseUsuario = paseUsuarioRepository.findByClienteAndConceptoContainingKeywordsNative(cliente.getIdCliente(), productosHealthStudio);
        List<PaseUsuario> paseUsuario = paseUsuarioRepository.findByClienteAndConceptoUsingDynamicKeywords(cliente.getIdCliente(), "%studio%", "%fisio%", "%hidro%", "%spa%");

        if (!paseUsuario.isEmpty()){
            PaseHealthStudioDTO pases = new PaseHealthStudioDTO();
            pases.setIdCliente(cliente.getIdCliente());
            pases.setNombreCompleto(cliente.getNombreCompleto());
            pases.setTelefono(cliente.getTelefono());
            pases.setSexo(cliente.getSexo());
            pases.setAcceso(cliente.getEstatusAcceso());
            pases.setClub(cliente.getClub().getNombre());
            pases.setPases(paseUsuario);

            return pases;

        }

        return null;
    }
}
