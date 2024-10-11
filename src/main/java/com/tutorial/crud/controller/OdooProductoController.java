package com.tutorial.crud.controller;

import com.tutorial.crud.dto.AccesoTorniqueteDTO;
import com.tutorial.crud.dto.InvoiceRequest;
import com.tutorial.crud.dto.ListaTorniquetes;
import com.tutorial.crud.dto.ProductoDTO;
import com.tutorial.crud.entity.AccesoTorniquete;
import com.tutorial.crud.entity.PaseUsuario;
import com.tutorial.crud.exception.ResourceNotFoundException;
import com.tutorial.crud.service.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odoo")
@CrossOrigin("*")
public class OdooProductoController {

    private final PaseUsuarioService paseUsuarioService;
    private final OdooProductoService odooProductoService;
    private final OdooInvoiceService odooInvoiceService;
    private final OdooTorniqueteService odooTorniqueteService;
    private final AccesoTorniqueteService accesoTorniqueteService;

    public OdooProductoController(
            PaseUsuarioService paseUsuarioService,
            OdooProductoService odooProductoService,
            OdooInvoiceService odooInvoiceService,
            OdooTorniqueteService odooTorniqueteService,
            AccesoTorniqueteService accesoTorniqueteService) {
        this.paseUsuarioService = paseUsuarioService;
        this.odooProductoService = odooProductoService;
        this.odooInvoiceService = odooInvoiceService;
        this.odooTorniqueteService = odooTorniqueteService;
        this.accesoTorniqueteService = accesoTorniqueteService;
    }

    @GetMapping("/productos/{idCliente}")
    public List<ProductoDTO> obtenerProductos(@PathVariable int idCliente) throws ResourceNotFoundException {
        return odooProductoService.getProductos(idCliente);
    }

    @PostMapping("/invoice")
    public ResponseEntity<?> createInvoice(@RequestBody InvoiceRequest invoiceRequest) throws Exception{
        String response = odooInvoiceService.sendInvoiceToOdoo(invoiceRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @GetMapping("/pases/{clienteId}/{idProd}")
    public ResponseEntity<?> getPasesDisponibles(@PathVariable("clienteId") int clienteId, @PathVariable("idProd") int idProd) throws ResourceNotFoundException{

        Optional<PaseUsuario> pasesDisponible = paseUsuarioService.getPasesDisponibles(clienteId, idProd);

        return pasesDisponible.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontraron pases disponibles"));
    }

    @GetMapping("/torniquetes/{branchId}")
    public ListaTorniquetes obtenerTorniquetes(@PathVariable int branchId) throws ResourceNotFoundException {
        return odooTorniqueteService.getTorniquetes(branchId);
    }

    @GetMapping("/torniquete/abrir/{idTorniquete}")
    public ResponseEntity<?> abrirTorniquete(@PathVariable int idTorniquete) throws Exception {
        String res = odooTorniqueteService.abrirTorniquete(idTorniquete);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/torniquete/acceso")
    public ResponseEntity<?> registrarAccesoTorniquete(@RequestBody AccesoTorniquete accesoTorniquete) throws Exception {
        String response = accesoTorniqueteService.guardarAccesoTorniquete(accesoTorniquete);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
