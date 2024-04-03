package com.tutorial.crud.controller;

import com.tutorial.crud.service.EncuestaService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/encuesta")
public class EncuestaController {
    @Autowired
    EncuestaService encuestaService;
    @RequestMapping(value = "/getByCliente/{idCliente}/{idClub}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllEncuestasByCliente(@PathVariable int idCliente, @PathVariable int idClub) {
        JSONArray response = encuestaService.getSurveysByCliente(idCliente,idClub);
        //System.out.println("controller => " + response);
        return new ResponseEntity<>(response.toList(), HttpStatus.OK);
    }

    @RequestMapping(value = "/getQuestionsByCliente/{idCliente}/{idSurvey}", method = RequestMethod.GET)
    public ResponseEntity<?> getQuestionsByCliente(@PathVariable int idCliente, @PathVariable int idSurvey) {
        JSONArray response = encuestaService.getSurveyQuestionsByCliente(idCliente,idSurvey);
        //System.out.println("controller => " + response);
        return new ResponseEntity<>(response.toList(), HttpStatus.OK);
    }

    @RequestMapping(value = "/sendRespuestas", method = RequestMethod.POST)
    public ResponseEntity<?> sendRespuestasCliente(@RequestBody String request) {
        String response = encuestaService.sendRespuestasCliente(request);
        //System.out.println("controller => " + response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
