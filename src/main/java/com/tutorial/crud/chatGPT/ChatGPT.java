package com.tutorial.crud.chatGPT;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import com.tutorial.crud.CrudApplication;
import com.tutorial.crud.entity.AnswerChatGPT;
import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.repository.AnswerChatGPTRepository;
import com.tutorial.crud.service.AnswerChatGPTService;
import com.tutorial.crud.service.ClienteServiceImpl;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class ChatGPT {
    private final String URL_OPENAI = "https://api.openai.com/v1/chat/completions";
    private AnswerChatGPTService answerChatGPTService;
    private AnswerChatGPTRepository answerChatGPTRepository;
    public ChatGPT(AnswerChatGPTService answerChatGPTService, AnswerChatGPTRepository answerChatGPTRepository) {
        this.answerChatGPTService = answerChatGPTService;
        this.answerChatGPTRepository = answerChatGPTRepository;
    }

    @Value("${my.property.s}")
    String s;
    public String sendPrompt(String customerPrompt, Cliente customer) {
        /*String question = "{\n" +
                "  \"model\": \"gpt-3.5-turbo\",\n" +
                "  \"messages\": [\n" +
                "    {\n" +
                "      \"role\": \"system\",\n" +
                "      \"content\": \"Eres una maquina que sólo genera documentos json sin abreviar para dietas de una semana completa con los datos que se te dan, las dietas incluyen bebidas y están pensadas para gente que vive en el estado de Puebla en el país México. Todos los campos del json deben estar llenos y sin abreviar. El formato es:\\n{\\n  \\\"lunes\\\": {\\n    \\\"desayuno\\\": \\\"\\\",\\n    \\\"colación1\\\": \\\"\\\",\\n    \\\"comida\\\": \\\"\\\",\\n    \\\"colación2\\\": \\\"\\\",\\n    \\\"cena\\\": \\\"\\\"\\n  }\\n}\\n\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"role\": \"user\",\n" +
                "      \"content\": \"" + customerPrompt + "\"" +
                "    }\n" +
                "  ],\n" +
                "  \"temperature\": 1.45,\n" +
                "  \"max_tokens\": 3600,\n" +
                "  \"top_p\": 0.86,\n" +
                "  \"frequency_penalty\": 0.13,\n" +
                "  \"presence_penalty\": 1.59\n" +
                "}";*/
        //LocalDate today = LocalDate.now();
        //LocalDateTime startDate = LocalDateTime.of(today.minusDays(today.getDayOfMonth() - 1), LocalTime.MIN);
        //System.out.println("StartDate => " + startDate);
        //LocalDateTime endDate = LocalDateTime.of(today.plusDays(today.lengthOfMonth() - today.getDayOfMonth()), LocalTime.MAX.withSecond(0).withNano(0));
        //System.out.println("endDate => " + endDate);
        //List<AnswerChatGPT> answerChatGPTList = answerChatGPTService.getDietInCurrentMonth(customer, startDate, endDate);
        //System.out.println("List de dietas del mes: " + answerChatGPTList);
        LocalDate today = LocalDate.now();
        LocalDateTime startDate = LocalDateTime.of(today.minusDays(today.getDayOfMonth() - 1), LocalTime.MIN);
        LocalDateTime endDate = LocalDateTime.of(today.plusDays(today.lengthOfMonth() - today.getDayOfMonth()), LocalTime.MAX.withSecond(0).withNano(0));
        //List<AnswerChatGPT> answerChatGPTList = <answerChatGPTService.getDietInCurrentMonth(customer.getIdCliente(), startDate, endDate);
        List<AnswerChatGPT> answerChatGPTList = answerChatGPTRepository.findByCustomerAndCreatedAtBetweenOrderByCreatedAtDesc(customer, startDate, endDate);

        //String question = "{\n \"model\": \"gpt-4\",\n \"messages\": [\n {\n \"role\": \"system\",\n \"content\": \"Eres una maquina que sólo genera documentos json sin abreviar para dietas de una semana completa con los datos que se te dan, las dietas incluyen bebidas y están pensadas para gente que vive en el estado de Puebla en el país México. Todos los campos del json deben estar llenos y sin abreviar. El formato es:\\n{\\n  \\\"lunes\\\": {\\n    \\\"desayuno\\\": \\\"\\\",\\n    \\\"colación1\\\": \\\"\\\",\\n    \\\"comida\\\": \\\"\\\",\\n    \\\"colación2\\\": \\\"\\\",\\n    \\\"cena\\\": \\\"\\\"\\n  }\\n}\\n\"\n },\n {\n \"role\": \"user\",\n \"content\": \"" + customerPrompt + "\" }\n ],\n \"temperature\": 1.45,\n \"max_tokens\": 3600,\n \"top_p\": 0.86,\n \"frequency_penalty\": 0.13,\n \"presence_penalty\": 1.59\n }";
        //String question = "{\n \"model\": \"gpt-4\",\n \"messages\": [\n {\n \"role\": \"system\",\n \"content\": \"Eres una maquina que sólo genera documentos json sin abreviar para dietas con porciones en gramos de una semana completa con los datos que se te dan, las dietas incluyen bebidas y están pensadas para gente que vive en el estado de Puebla en el país México. Todos los campos del json deben estar llenos y sin abreviar. El formato es:\\n{\\n  \\\"lunes\\\": {\\n    \\\"desayuno\\\": \\\"\\\",\\n    \\\"colación1\\\": \\\"\\\",\\n    \\\"comida\\\": \\\"\\\",\\n    \\\"colación2\\\": \\\"\\\",\\n    \\\"cena\\\": \\\"\\\"\\n  }\\n}\\n\"\n },\n {\n \"role\": \"user\",\n \"content\": \"" + customerPrompt + "\" }\n ],\n \"temperature\": 1.78,\n \"max_tokens\": 4095,\n \"top_p\": 0.86,\n \"frequency_penalty\": 0.2,\n \"presence_penalty\": 1.01\n }";
        String question = "{\n \"model\": \"gpt-4\",\n \"messages\": [\n {\n \"role\": \"system\",\n \"content\": \"Eres una maquina que sólo genera documentos json sin abreviar para dietas con porciones en gramos de una semana completa con los datos que se te dan, las dietas incluyen bebidas y están pensadas para gente que vive en el estado de Puebla en el país México. Todos los campos del json deben estar llenos incluidas y sin abreviar. NUNCA te saltes comidas ni días. El formato es:\\n{\\n  \\\"lunes\\\": {\\n    \\\"desayuno\\\": \\\"\\\",\\n    \\\"colación1\\\": \\\"\\\",\\n    \\\"comida\\\": \\\"\\\",\\n    \\\"colación2\\\": \\\"\\\",\\n    \\\"cena\\\": \\\"\\\"\\n  }\\n}\\n\"\n },\n {\n \"role\": \"user\",\n \"content\": \"" + customerPrompt + "\" }\n ],\n \"temperature\": 1.78,\n \"max_tokens\": 4095,\n \"top_p\": 0.86,\n \"frequency_penalty\": 0.2,\n \"presence_penalty\": 1.01\n }";

        //System.out.println("Body a enviar => " + question);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL_OPENAI))
                .POST(HttpRequest.BodyPublishers.ofString(question))
                .header("Content-Type", "application/json")
                //.header("Authorization", "Bearer " + "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")
                .header("Authorization", "Bearer " + "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") // nuevo token para chat gpt-4
                .build();

        AnswerChatGPT answerChatGPT = new AnswerChatGPT();
        HttpResponse<String> response;
        try {
             response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String content = new JSONObject(response.body()).getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content").toString();
            //String breakfast = new JSONObject(content).getJSONObject("lunes").getString("desayuno");
            answerChatGPT.setCustomer(customer);
            answerChatGPT.setQuestion(question);
            answerChatGPT.setAnswer(response.body());
            answerChatGPT.setValidQuestion(true);

            //if (breakfast.equals("")) {
            if (!isValidDietJSON(content, customer)) {
                throw new RuntimeException("Respuesta Chat Vacia");
            }
            float cost = 0;
            if (today.getDayOfMonth() > 7 || !answerChatGPTList.isEmpty()) {
                int responseStatus = chargeDiet(customer);
                //System.out.println("Response de cargar orden de venta: " + response);
                if (responseStatus == 200) cost = 20;
            }
            answerChatGPT.setCost(cost);
        } catch (Exception e) {
            //System.out.println(e.getMessage());
            answerChatGPT.setValidQuestion(false);
            answerChatGPTService.save(answerChatGPT);
            return "";
        }
        answerChatGPTService.save(answerChatGPT);
        //System.out.println("Guarde la respuesta de chatGPT: " + new java.sql.Date(new Date().getTime()));
        return response.body();
    }

    public int chargeDiet(Cliente customer) {
        int holder = (customer.getIdTitular() != 0) ? customer.getIdTitular() : customer.getIdCliente();
        String body2 = "{\r\n"
                + "\"IDCliente\":" + holder + ",  \r\n"
                + "\"IDClub\":"+ customer.getClub().getIdClub()+ ",   \r\n"
                + "\"Cantidad\":1, \r\n"
                + "\"IDProductoServicio\":"+ 2585 +",  \r\n"
                + "\"Observaciones\":\"Dieta solicitada a ChatGPT\",\r\n"
                + "\"DescuentoPorciento\":0,  \r\n"
                + "\"FechaInicio\":\""+new java.sql.Date(new Date().getTime())+" 00:00:00\", \r\n" //2023-10-30
                + "\"FechaFin\":\""+new java.sql.Date(new Date().getTime())+" 00:00:00\",  \r\n"
                + "\"CobroProporcional\":0, \r\n"
                + "\"Token\":\"77D5BDD4-1FEE-4A47-86A0-1E7D19EE1C74\"  \r\n"
                + "}";

        int statusCode = 500;
        try {
            URL url = new URL("http://192.168.20.104:8000/ServiciosClubAlpha/api/OrdenDeVenta/Registra");
            String postData = body2;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            try (DataOutputStream dos = new DataOutputStream(conn.getOutputStream())) {
                dos.writeBytes(postData);
            }
            statusCode = conn.getResponseCode();

           /* if (statusCode == 200) {
               // toalla.setSancion("SI");
            }else {
                //toalla.setSancion("NO");
            }
            //toallaService.save(toalla);
            conn.disconnect();*/

        } catch (Exception e) {
            System.out.println("Error al cargar dieta en odoo cliente: " + holder + " => "+ e.getMessage());
        }
        return statusCode;
    }

    public boolean isValidDietJSON(String content, Cliente customer) {
        System.out.println("\nContent que recibo de chat gpt para empezar a validar => " + content);

        try {
            String dietasConAcento = content
                .replace("miercoles", "miércoles")
                .replace("sabado", "sábado")
                .replace("colacion1", "colación1")
                .replace("colacion2", "colación2");

            try (InputStream schemaAsStream = new FileInputStream(new File("/home/spapp/model/schema.json"))) {
                JsonSchema schema = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4).getSchema(schemaAsStream);
                ObjectMapper om = new ObjectMapper();
                JsonNode jsonNode = om.readTree(dietasConAcento);
                Set<ValidationMessage> errors = schema.validate(jsonNode);
                if (errors.isEmpty()) {
                    System.out.println("Dieta valida para usuario: " + customer.getIdCliente() + " => " + LocalDateTime.now().withNano(0));
                } else {
                    System.out.println("Errores de validación en dieta del usuario " + customer.getIdCliente() + " => " + LocalDateTime.now().withNano(0) + ":");
                    for (ValidationMessage error : errors) {
                        System.out.println(error.getMessage());
                    }

                }
                return errors.isEmpty();
            } catch (IOException e) {
                System.out.println("Errorr al cargar model/schema.json");
                //e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("Exception de Json Proccesing => " +  e.getMessage());
            //e.printStackTrace();
        }
        return false;
    }
}

