package com.tutorial.crud.Odoo.Spec.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.tutorial.crud.Odoo.Spec.dto.PaseQR;
import com.tutorial.crud.Odoo.Spec.dto.RespuestaDTO;
import com.tutorial.crud.Odoo.Spec.dto.RespuestasRequestDTO;
import com.tutorial.crud.Odoo.Spec.dto.SpecFacturaResponse;
import com.tutorial.crud.Odoo.Spec.entity.RespuestaFormulario;
import com.tutorial.crud.Odoo.Spec.service.SpecFacturaService;
import com.tutorial.crud.chatGPT.ChatGPT;
import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.exception.ClienteNoEncontradoException;
import com.tutorial.crud.exception.ResourceNotFoundException;
import com.tutorial.crud.service.AnswerChatGPTService;
import com.tutorial.crud.service.ClienteService;
import com.tutorial.crud.service.FormularioService;
import com.tutorial.crud.service.PaseUsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorial.crud.Odoo.Spec.dto.EvaluacionSpec.ReporteEvaluacion;
import com.tutorial.crud.Odoo.Spec.dto.EvaluacionSpec.SpecEvaluacionDto;
import com.tutorial.crud.Odoo.Spec.service.SpecEvaluacionService;

@RestController
@RequestMapping("/spec")
@CrossOrigin(origins = "*")
public class SpecController {

    private static final Logger logger = LoggerFactory.getLogger(SpecController.class);

    @Autowired
    private SpecEvaluacionService specEvaluacionService;

    @Autowired
    private SpecFacturaService specFacturaService;

    @Autowired
    private FormularioService formularioService;

    @Autowired
    private ChatGPT chatGPT;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private AnswerChatGPTService answerChatGPTService;

    @Autowired
    private PaseUsuarioService paseUsuarioService;


    /*=================================================== FORMULARIOS ========================================================*/

    @PostMapping("/formulario/responder")
    public ResponseEntity<List<RespuestaFormulario>> responderFormularioMultiple(@RequestBody RespuestasRequestDTO  formulario) throws ClienteNoEncontradoException {

        List<RespuestaFormulario> respuestasGuardadas = formularioService.responderFormularioMultiple(formulario);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/formulario/respuestas/{idCliente}")
    public ResponseEntity<List<RespuestaDTO>> obtenerRespuestasFormulario(
            @PathVariable Integer idCliente) {

        List<RespuestaDTO> respuestas = formularioService.obtenerRespuestasPorCliente(idCliente);

        return ResponseEntity.ok(respuestas);
    }

    /*=================================================== EVALUACIONES ========================================================*/

    //Endpoint para obtener datos de evaluacion
    @GetMapping("/evaluacion/{idCliente}")
    public ResponseEntity<?> obtenerEvaluacion(@PathVariable Integer idCliente) {

        try {
            var respuestas = specEvaluacionService.obtenerEvaluacion(idCliente);
            if (respuestas.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("msg", "No se encontraron evaluaciones"));
            }
            // get ultima evaluacion that state == done and id is the max
            Optional<SpecEvaluacionDto> ultimaEvaluacion = respuestas.stream().filter(e -> e.getState().equals("validado")).max((e1, e2) -> e1.getId_evaluacion() - e2.getId_evaluacion());
            if (ultimaEvaluacion.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("msg", "No se encontraron evaluaciones completadas"));
            }
            ReporteEvaluacion reporte = specEvaluacionService.obtenerReporte(ultimaEvaluacion.get());
            return ResponseEntity.ok(reporte);
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("msg", "Error al obtener evaluaciones"));
        }
    }

    @GetMapping("/usuario/{idCliente}")
    public ResponseEntity<?> getMethodName(@PathVariable Integer idCliente) {
        return specEvaluacionService.obtenerUsuarioSpec(idCliente);
    }

    /*=================================================== PLANES DE ALIMENTACIÓN MENSUALES ========================================================*/

    @GetMapping("/guia/getPrompt/{idCliente}")
    public ResponseEntity<?> getPromptByCliente(@PathVariable Integer idCliente) throws Exception{

        try {
            String prompt = chatGPT.getPrompt(idCliente);
            return ResponseEntity.ok(prompt);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("msg", "Error al obtener evaluaciones"));
        }
    }


    @GetMapping("guia/actualDelMes/{idCliente}")
    public ResponseEntity<String> obtenerGuiaAlimenticioMensual(@PathVariable Integer idCliente) throws ClienteNoEncontradoException, ResourceNotFoundException {

        String dieta = answerChatGPTService.getGuiaAlimenticiaMensual(idCliente);

        return ResponseEntity.ok(dieta);
    }

    @GetMapping("/guia/{clienteId}/dieta-valida")
    public ResponseEntity<Boolean> existeGuiaAlimenticioMensual(@PathVariable Integer clienteId) {

        Boolean dietaValida = answerChatGPTService.verificarGuiaGeneradaEsteMes(clienteId);

        return ResponseEntity.ok(dietaValida);
    }

    /*@PostMapping("/{idCliente}/guia")
    public ResponseEntity<?> verificarGuiaAlimenticiaMensual(@PathVariable Integer idCliente) throws ClienteNoEncontradoException {

        String guiaAlimenticia = answerChatGPTService.verificarGuiaAlimenticiaMensual(idCliente);

        if (guiaAlimenticia.contains("La guía alimenticia ya está generada")) {
            // Si la respuesta indica que ya existe una guía generada, devolvemos un 200 OK
            return ResponseEntity.ok(guiaAlimenticia);
        } else {
            // Si la respuesta indica que se generó la guía, devolvemos un 201 Created
            return ResponseEntity.status(HttpStatus.CREATED).body("Guía alimenticia generada con éxito.");
        }
    }*/

    @PostMapping("/{idCliente}/guia")
    public ResponseEntity<?> obtenerGuiaAlimenticia(@PathVariable Integer idCliente) {

        boolean guiaGenerada = answerChatGPTService.verificarGuiaGeneradaEsteMes(idCliente);

        if (guiaGenerada) {
            return ResponseEntity.ok("La guía alimenticia ya está generada.");
        }

        String guiaGeneradaMensaje = answerChatGPTService.generarGuiaAlimenticia(idCliente);

        return ResponseEntity.status(HttpStatus.CREATED).body(guiaGeneradaMensaje);
    }


    /*=================================================== PASES QRS MENSUALES ============================================================*/

    @GetMapping("/cargar-pases/{idCliente}")
    public ResponseEntity<?> cargarPases(@PathVariable String idCliente) {
        try {
            List<SpecFacturaResponse> facturas = specFacturaService.obtenerFacturas(idCliente);
            specFacturaService.procesarFacturas(facturas);
            return ResponseEntity.ok("Pases cargados correctamente para: " + idCliente);
        }catch (Exception e) {
            //System.out.println(e.getMessage());
            //e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("msg", "Error al cargar los pases"));
        }
    }

    @GetMapping("/{idCliente}/{idVentaDetalle}/paseQR")
    public ResponseEntity<?> obtenerPasesQR(@PathVariable Integer idCliente, @PathVariable Integer idVentaDetalle) {

        List<PaseQR> pases = paseUsuarioService.obtenerPasesQRPorIdVentaDetalle(idCliente, idVentaDetalle);

        return ResponseEntity.ok(pases);
    }

    @PostMapping("/{idCliente}/{idVentaDetalle}/{consumidoPor}/paseQR")
    public ResponseEntity<?> consumirPaseQR(@PathVariable Integer idCliente, @PathVariable Integer idVentaDetalle, @PathVariable String consumidoPor) {

        Boolean fueConsumido = paseUsuarioService.consumirPaseQR(idCliente, idVentaDetalle, consumidoPor);

        return ResponseEntity.ok(fueConsumido);
    }
}
