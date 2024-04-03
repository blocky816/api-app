package com.tutorial.crud.service;

import com.tutorial.crud.entity.Encuesta;
import com.tutorial.crud.repository.EncuestaRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EncuestaService {
    @Autowired
    EncuestaRepository encuestaRepository;

    public JSONArray getSurveysByCliente(int idCliente, int idClub) {
        var client = HttpClient.newHttpClient();
        // create a request
        var request = HttpRequest.newBuilder(
                        URI.create("http://192.168.20.107:8000/ServiciosClubAlpha/api/Survey/Survey"))
                .header("accept", "application/json")
                //.headers("Content-Type", "text/plain;charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString("{\"IdCliente\": \"" + idCliente + "\"," + "\"branch_id\" : " + getClub(idClub) + "}"))
                .build();

        // use the client to send the request
        try {
            List<Encuesta> encuestas = encuestaRepository.findAllByIdClienteAndEncuestasIsNotNullAndPreguntasIsNullAndRespuestasIsNullOrderByFechaEncuestasAsc(idCliente);
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            var json = new JSONArray(response.body());
            //System.out.println("json que recibo de odoo esta vacio? " + json.isEmpty());
            //System.out.println("encuestas guardadas vacio? " + encuestas.isEmpty());
            if ((!encuestas.isEmpty() && json.length() == new JSONArray(encuestas.get(0).getEncuestas()).length()) || json.isEmpty()) {
                //System.out.println("Devuelvo encuesta guardada en bd porque la que consulte es lo mismo => " + encuestas.get(0).getIdEncuesta());
                return new JSONArray(encuestas.get(0).getEncuestas());
            }
            if (!json.isEmpty()) {
                Encuesta encuesta = new Encuesta();
                encuesta.setIdCliente(idCliente);
                encuesta.setIdClub(idClub);
                encuesta.setEncuestas(response.body());
                encuestaRepository.save(encuesta);
            }
            //System.out.println("Devuelvo justo lo que consulte odoo porque no existia");
            return json;
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Fallo el conusltar encuestas => " + e.getMessage());
            return new JSONArray();
        }
    }

    public JSONArray getSurveyQuestionsByCliente(int idCliente, int idSurvey) {
        var client = HttpClient.newHttpClient();
        // create a request
        var request = HttpRequest.newBuilder(
                        URI.create("http://192.168.20.107:8000/ServiciosClubAlpha/api/Survey/Asnwer"))
                .header("accept", "application/json")
                //.headers("Content-Type", "text/plain;charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString("{\"IdSurvey\" : " + idSurvey + "}"))
                .build();

        // use the client to send the request
        try {
            List<Encuesta> encuesta = encuestaRepository.findAllByIdClienteAndEncuestasIsNotNullAndPreguntasIsNullAndRespuestasIsNullOrderByFechaEncuestasAsc(idCliente);
            List<Encuesta> encuestasP = encuestaRepository.findAllByIdClienteAndIdSurveyAndEncuestasIsNotNullAndPreguntasIsNotNullAndRespuestasIsNullOrderByFechaPreguntasAsc(idCliente, idSurvey);
            if (!encuestasP.isEmpty()) {
                System.out.println("Devuelvo preguntas que ya tenia guardadas para encuesta => " + idSurvey);
                return new JSONArray(encuestasP.get(0).getPreguntas());
            }
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            var json = new JSONArray(response.body());
            if (!encuesta.isEmpty() && !json.isEmpty()) {
                encuesta.get(0).setPreguntas(response.body());
                encuesta.get(0).setFechaPreguntas(LocalDateTime.now());
                encuesta.get(0).setIdSurvey(idSurvey);
                System.out.println("Encuesta recuperada para mandar preguntas => " + encuesta.get(0).getIdEncuesta());
                encuestaRepository.save(encuesta.get(0));
            }
            return json;
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Fallo el conusltar preguntas => " + e.getMessage());
            return new JSONArray();
        }
    }

    public String sendRespuestasCliente(String request) {
        try {
            JSONObject jsonTransform = new JSONObject(request);
            //System.out.println("El json que llego fue : " + jsonTransform);
            var client = HttpClient.newHttpClient();
            // create a request
            var requestOdoo = HttpRequest.newBuilder(
                            URI.create("http://192.168.20.107:8000/ServiciosClubAlpha/api/Survey/Respuestas"))
                    .header("accept", "application/json")
                    //.headers("Content-Type", "text/plain;charset=UTF-8")
                    .POST(HttpRequest.BodyPublishers.ofString(request))
                    .build();
            int idCliente = Integer.parseInt(jsonTransform.getString("IdCliente"));
            int idSurvey = jsonTransform.getInt("IdSurvey");
            var response = client.send(requestOdoo, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            //System.out.println("Response de odoo => " + response.body());
            //System.out.println("Sttatus code => " + response.statusCode());
            //System.out.println("Idcliente => " + idCliente);
            //System.out.println("IdSurvey => " + idSurvey);
            List<Encuesta> encuestas = encuestaRepository.findAllByIdClienteAndIdSurveyAndEncuestasIsNotNullAndPreguntasIsNotNullAndRespuestasIsNullOrderByFechaPreguntasAsc(idCliente, idSurvey);
            if (!encuestas.isEmpty()) {
                //System.out.println("Encuesta get 0 para guardar respuestas => " + encuestas.get(0).getIdEncuesta());
                encuestas.get(0).setFechaRespuestas(LocalDateTime.now());
                encuestas.get(0).setRespuestas(request.replaceAll("\\s", ""));
                encuestaRepository.save(encuestas.get(0));
                if (statusCode >= 200 && statusCode <= 201) {
                    encuestas.get(0).setGuardadoOdoo(true);
                    encuestaRepository.save(encuestas.get(0));
                    return "{\"msg\": \"Resultados guardados correctamente\"}";
                }
            }
        } catch (Exception e) {
            System.out.println("Fallo el guardar respuestas => " + e.getMessage());
            //e.printStackTrace();
        }
        return "{\"msg\": \"Respuestas guardadas correctamente + de una ves\"}";
    }

    public int getClub(int idClub) {
        int idClubOdoo = 0;
        switch (idClub) {
            case 2: idClubOdoo = 6; break;
            case 3: idClubOdoo = 7; break;
            case 5: idClubOdoo = 8; break;
            case 4: idClubOdoo = 9; break;
        }
        return idClubOdoo;
    }
}
