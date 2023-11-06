package com.tutorial.crud.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tutorial.crud.dto.BodyFormulario;
import com.tutorial.crud.dto.FormularioDTO;
import com.tutorial.crud.entity.AnswerChatGPT;
import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.repository.FoodCalorieRepository;
import com.tutorial.crud.service.AnswerChatGPTService;
import com.tutorial.crud.service.FormularioService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/formularios")
@CrossOrigin(origins = "*")
public class FormularioController {

    @Autowired
    FormularioService formularioService;
    @Autowired
    FoodCalorieRepository foodCalorieRepository;
    @Autowired
    AnswerChatGPTService answerChatGPTService;
    @PostMapping("")
    @ResponseBody
    public ResponseEntity<?> nuevoFormulario(@RequestBody BodyFormulario body){
        try {
            formularioService.save(body);
            return new ResponseEntity<>(new JSONObject("{'respuesta': 'Creado correctamente'}").toMap(), HttpStatus.OK);
        } catch (NullPointerException e){
            return new ResponseEntity<>(new JSONObject("{'respuesta':" + e.getMessage() + "}").toMap(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new JSONObject("{'respuesta': 'Error interno'}").toMap(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{folio}")
    @ResponseBody
    public ResponseEntity<?> getFormulario(@PathVariable int folio, @RequestBody BodyFormulario body){
        try {
            formularioService.updateFormulario(body, folio);
            return new ResponseEntity<>(new JSONObject("{'respuesta': 'Actualizado correctamente'}").toMap(), HttpStatus.OK);
        } catch (NullPointerException e){
            return new ResponseEntity<>(new JSONObject("{'respuesta':" + e.getMessage() + "}").toMap(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new JSONObject("{'respuesta': 'Error interno'}").toMap(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{folio}")
    @ResponseBody
    public ResponseEntity<?> getFormulario(@PathVariable int folio){
        try {
            FormularioDTO  formularioDTO = formularioService.findByFolio(folio);
            return new ResponseEntity<>(formularioDTO, HttpStatus.OK);
        } catch (NullPointerException e){
            return new ResponseEntity<>(new JSONObject("{'respuesta':" + e.getMessage() + "}").toMap(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new JSONObject("{'respuesta': 'Error interno'}").toMap(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{folio}")
    @ResponseBody
    public ResponseEntity<?> deleteFormulario(@PathVariable int folio){
        try {
            formularioService.deleteByFolio(folio);
            return new ResponseEntity<>(new JSONObject("{'respuesta': 'Eliminado correctamente'}").toMap(), HttpStatus.OK);
        } catch (NullPointerException e){
            return new ResponseEntity<>(new JSONObject("{'respuesta':" + e.getMessage() + "}").toMap(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new JSONObject("{'respuesta':" + e.getMessage() + "}").toMap(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*---------------------------------------- CLIENTE -------------------------------------------------*/
    @PostMapping("/asignarCliente")
    @ResponseBody
    public ResponseEntity<?> asignarFormulario(@RequestBody ObjectNode request){
        try {
            formularioService.asignarCliente(request);
            return new ResponseEntity<>(new JSONObject("{'respuesta': 'Asignado correctamente'}").toMap(), HttpStatus.OK);
        } catch (NullPointerException e){
            return new ResponseEntity<>(new JSONObject("{'respuesta':" + e.getMessage() + "}").toMap(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new JSONObject("{'respuesta':" + e.getMessage() + "}").toMap(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getByCliente/{id}")
    @ResponseBody
    public ResponseEntity<?> getFormularioByCliente(@PathVariable int id){
        try {
            FormularioDTO  formularioDTO = formularioService.findByCliente(id);
            return new ResponseEntity<>(formularioDTO, HttpStatus.OK);
        } catch (NullPointerException e){
            if (e.getMessage() == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/responder/{idCliente}")
    @ResponseBody
    public ResponseEntity<?> responderFormulario(@PathVariable Integer idCliente, @RequestBody FormularioDTO body){
        try {
            formularioService.responderForm(idCliente, body);
            return new ResponseEntity<>(new JSONObject("{'respuesta': 'Form completado correctamente'}").toMap(), HttpStatus.OK);
        } catch (NullPointerException e){
            return new ResponseEntity<>(new JSONObject("{'respuesta':" + e.getMessage() + "}").toMap(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new JSONObject("{'respuesta': 'Error interno'}").toMap(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "getCustomerPrompt/{customerID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCustomerPrompt(@PathVariable int customerID, @RequestParam int formFolio) {
        try {
            String customerPrompt = formularioService.getFormAnswersForPrompt(customerID, formFolio);
            JSONObject res = new JSONObject(customerPrompt);
            String content = res.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content").toString();
            //System.out.println("JSON OBject => " + content);
            return new ResponseEntity<>(content, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("{}", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("getFoodCalories")
    public ResponseEntity<?> getFoodCalories() {
        return new ResponseEntity<>(foodCalorieRepository.findAllByOrderById(),HttpStatus.OK);
    }

    @GetMapping(path = "currentDietByCustomer/{customerID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getcurrentDietByCustomer(@PathVariable int customerID) {
        try {
            LocalDate today = LocalDate.now();
            LocalDateTime startDate = LocalDateTime.of(today.minusDays(today.getDayOfMonth() - 1), LocalTime.MIN);
            LocalDateTime endDate = LocalDateTime.of(today.plusDays(today.lengthOfMonth() - today.getDayOfMonth()), LocalTime.MAX.withSecond(0).withNano(0));
            return new ResponseEntity<>(answerChatGPTService.getDietInCurrentMonth(customerID, startDate, endDate).get(0), HttpStatus.OK);
        } catch(Exception e) {
            //e.printStackTrace();
            switch(e.getMessage()) {
                case "Cliente no tiene pesajes":
                case "Index 0 out of bounds for length 0":
                    return new ResponseEntity<>("{\"message\": \"cliente no tiene pesajes\" }", HttpStatus.NOT_FOUND);//404
                case "Formulario vacio":
                    return new ResponseEntity<>("{\"message\": \"" + e.getMessage() + "\" }", HttpStatus.CONFLICT);//409
                case "No hay dietas en el mes":
                    return new ResponseEntity<>("{\"message\": \"" + e.getMessage() + "\" }", HttpStatus.NO_CONTENT);//204
                default:
                    return new ResponseEntity<>("{\"message\": \"" + e.getMessage() + "\" }", HttpStatus.INTERNAL_SERVER_ERROR);//500
            }
        }
    }
}
