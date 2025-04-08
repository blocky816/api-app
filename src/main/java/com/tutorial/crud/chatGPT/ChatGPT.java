package com.tutorial.crud.chatGPT;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import com.tutorial.crud.Odoo.Spec.dto.RespuestaDTO;
import com.tutorial.crud.entity.AnswerChatGPT;
import com.tutorial.crud.entity.Cliente;
import com.tutorial.crud.exception.ChatGPTException;
import com.tutorial.crud.exception.ClienteNoEncontradoException;
import com.tutorial.crud.repository.AnswerChatGPTRepository;
import com.tutorial.crud.service.AnswerChatGPTService;
import com.tutorial.crud.service.ClienteService;
import com.tutorial.crud.service.FormularioService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.tutorial.crud.service.FormularioService.calcularAniosDate;

@Service
public class ChatGPT {

    private static final Logger logger = LoggerFactory.getLogger(ChatGPT.class);

    private static final String ATHLETE_PROGRAM = "estoy en un programa de atletas de alto rendimiento.";

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ClienteService clienteService;

    @Autowired
    FormularioService formularioService;

    @Autowired
    AnswerChatGPTRepository answerChatGPTRepository;

    @Autowired
    AnswerChatGPTService answerChatGPTService;

    @Value("${my.property.s}")
    String s;

    @Value("${chatgpt.api-url}")
    private String URL_OPENAI;

    @Value("${chatgpt.api-key}")
    private String API_KEY;

    @Value("${chatgpt.model}")
    private String MODEL;

    @Value("${chatgpt.temperature}")
    private double TEMPERATURE;

    @Value("${chatgpt.max-tokens}")
    private int MAX_TOKENS;

    @Value("${chatgpt.top-p}")
    private double TOP_P;


    public String sendPrompt(String customerPrompt, Cliente customer) {

        LocalDate today = LocalDate.now();
        LocalDateTime startDate = LocalDateTime.of(today.minusDays(today.getDayOfMonth() - 1), LocalTime.MIN);
        LocalDateTime endDate = LocalDateTime.of(today.plusDays(today.lengthOfMonth() - today.getDayOfMonth()), LocalTime.MAX.withSecond(0).withNano(0));
        List<AnswerChatGPT> answerChatGPTList = answerChatGPTRepository.findByCustomerAndCreatedAtBetweenOrderByCreatedAtDesc(customer, startDate, endDate);

        String question = "{\n \"model\": \"gpt-4\",\n \"messages\": [\n {\n \"role\": \"system\",\n \"content\": \"Eres una maquina que sólo genera documentos json sin abreviar para dietas con porciones en gramos de una semana completa con los datos que se te dan, las dietas incluyen bebidas y están pensadas para gente que vive en el estado de Puebla en el país México. Todos los campos del json deben estar llenos incluidas y sin abreviar. NUNCA te saltes comidas ni días. El formato es:\\n{\\n  \\\"lunes\\\": {\\n    \\\"desayuno\\\": \\\"\\\",\\n    \\\"colación1\\\": \\\"\\\",\\n    \\\"comida\\\": \\\"\\\",\\n    \\\"colación2\\\": \\\"\\\",\\n    \\\"cena\\\": \\\"\\\"\\n  }\\n}\\n\"\n },\n {\n \"role\": \"user\",\n \"content\": \"" + customerPrompt + "\" }\n ],\n \"temperature\": 1.78,\n \"max_tokens\": 4095,\n \"top_p\": 0.86,\n \"frequency_penalty\": 0.2,\n \"presence_penalty\": 1.01\n }";

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
            //System.out.println("REspuesta de CHATGTP => " + response.body());
            //return response.body();
            //System.out.println("REspuesta de CHATGTP.java => " + response.body());
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
                + "\"IDProductoServicio\":"+ 3296 +",  \r\n"
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


    /**********************************************************[GUIAS ALIMENTICIAS SPEC]*****************************************************************************/

    /*public String getPrompt(int idCliente) {
        Cliente cliente = obtenerCliente(idCliente);
        String gender = cliente.getSexo().toLowerCase();
        int age = calcularAniosDate(cliente.getFechaNacimiento());
        String customerPrompt = generarPromptConRespuestas(idCliente, gender, age);
        return construirJsonRequest(customerPrompt);
    }

    private Cliente obtenerCliente(int idCliente) {
        Cliente cliente = clienteService.findById(idCliente);
        if (cliente == null) {
            throw new ClienteNoEncontradoException("Cliente no encontrado");
        }
        return cliente;
    }

    public String generarPromptConRespuestas(int idCliente, String gender, int age) {
        List<RespuestaDTO> respuestas = formularioService.obtenerRespuestasPorCliente(idCliente);

        if (respuestas.isEmpty()) {
            //throw new ChatGPTException("El cliente " + idCliente + " no tiene información alimentaria.");
            return "";
        }

        String peso = respuestas.get(0).getPeso();
        String estatura = respuestas.get(0).getEstatura();

        StringBuilder respuestasConcatenadas = new StringBuilder();
        respuestas.forEach(respuesta -> {
            String pregunta = escapeJson(respuesta.getPregunta());
            String respuestaStr = escapeJson(respuesta.getRespuesta());
            if (!respuestaStr.isEmpty()) {
                respuestasConcatenadas.append("- Pregunta: ").append(pregunta)
                        .append(" Respuesta: ").append(respuestaStr);
            }
        });

        String initial = String.format("Soy %s de %d años de edad, mido %s, peso %s, y %s",
                gender, age, estatura, peso, ATHLETE_PROGRAM);

        if (respuestasConcatenadas.length() > 0) {
            respuestasConcatenadas.insert(0, " Recientemente conteste el siguiente cuestionario sobre salud: ");
        }

        return initial + respuestasConcatenadas.toString();
    }

    private String escapeJson(String input) {
        return input.replace("\"", "\\\"");
    }

    private String construirJsonRequest(String customerPrompt) {
        String temperatureFormatted = DECIMAL_FORMAT.format(TEMPERATURE);
        String topPFormatted = DECIMAL_FORMAT.format(TOP_P);
        int dias = obtenerDiasMesActual();

        return String.format("{\n" +
                "  \"model\": \"%s\",\n" +
                "  \"messages\": [\n" +
                "    {\n" +
                "      \"role\": \"system\",\n" +
                "      \"content\": \"Eres un nutriólogo que genera un documento JSON con las dietas completas para %d días, separados como dia1, dia2, " +
                "dia3...etc., pero NUNCA omitas ningún día de los que te indico. La dieta debe incluir las tres comidas principales del día y las dos colaciones, además de bebidas, con " +
                "cantidades exactas en gramos para cada comida. Todos los días deben estar completos, sin saltos de línea, puntos suspensivos ni omisiones.  No se deben " +
                "incluir comentarios o explicaciones. Solo devuelve el JSON. Ejemplo de formato: {\\\"día1\\\": {\\\"desayuno\\\": \\\"150 gramos de avena, 200 gramos de fresas, 300 mililitros de agua\\\", \\\"colación1\\\": " +
                "\\\"100 gramos de almendras, 250 mililitros de jugo de naranja\\\", \\\"comida\\\": \\\"200 gramos de pechuga de pollo, 150 gramos de arroz integral, 100 gramos " +
                "de verduras mixtas, 300 mililitros de agua\\\", \\\"colación2\\\": \\\"150 gramos de yogur natural sin lactosa, 50 gramos de nueces\\\", \\\"cena\\\": " +
                "\\\"150 gramos de pescado a la plancha, 100 gramos de brócoli al vapor, 300 mililitros de agua\\\"}, \\\"día2\\\": {\\\"desayuno\\\": \\\"...\\\", \\\"colación1\\\": " +
                "\\\"...\\\", \\\"comida\\\": \\\"...\\\", \\\"colación2\\\": \\\"...\\\", \\\"cena\\\": \\\"...\\\"}, ...}\" \n" +
                "    },\n" +
                "    {\n" +
                "      \"role\": \"user\",\n" +
                "      \"content\": \"%s\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"temperature\": %s,\n" +
                "  \"max_tokens\": %d,\n" +
                "  \"top_p\": %s,\n" +
                "  \"store\": false\n" +
                "}", MODEL, dias, customerPrompt, temperatureFormatted, MAX_TOKENS, topPFormatted);
    }

    private int obtenerDiasMesActual () {
        return LocalDate.now().lengthOfMonth();
    }

    public String generarGuiaSPEC(String prompt) throws RuntimeException {

        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL_OPENAI))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(prompt))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                logger.error("Error al obtener la guía alimenticia: {} - {}", response.statusCode(), response.body());
                throw new RuntimeException("Error en la respuesta de la API: " + response.statusCode() + " " + response.body());
            }
        } catch (Exception e) {
            logger.error("Error en la solicitud a la API de ChatGPT: {}", e.getMessage());
            throw new RuntimeException("Error al contactar con la API de ChatGPT.");
        }
    }


    public String isValidDiet(String guiaSpec) {
        try {
            String response = obtenerContenido(guiaSpec);

            String regex = "\\{.*\\}";
            Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
            Matcher matcher = pattern.matcher(response);
            if (matcher.find()) {
                // Extraer el JSON
                String json = matcher.group();
                String estructuraFinal = validarEstructura(json);
                if (!estructuraFinal.isEmpty())
                    return estructuraFinal;
            }

            return "";
        }catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "";
        }
    }

    public String obtenerContenido(String responseGPT) {
        // Realizar la llamada GET a la API de GPT
        JSONObject response = new JSONObject(responseGPT);

        // Extraer el contenido de la respuesta
        if (response != null && response.getJSONArray("choices") != null && !response.getJSONArray("choices").isEmpty()) {
            // Extraemos el contenido del mensaje
            String contentString = response.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");

            return contentString;
        }

        return "No se encontró contenido";
    }

    // Método principal para validar la estructura
    public String validarEstructura(String contentString) {
        try {
            // Convertir el contenido a un JsonNode para validar sin deserializar completamente
            JsonNode rootNode = objectMapper.readTree(contentString);

            // Verificar que sea un objeto
            if (!rootNode.isObject()) {
                return "";
            }

            // Normalizar las claves esperadas (sin acentos)
            String desayuno = quitarAcentos("desayuno");
            String colacion1 = quitarAcentos("colación1");
            String comida = quitarAcentos("comida");
            String colacion2 = quitarAcentos("colación2");
            String cena = quitarAcentos("cena");

            // Iterar sobre los días del mapa
            Iterator<Map.Entry<String, JsonNode>> fields = rootNode.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                JsonNode diaNode = field.getValue();

                // Iterar sobre las claves del día y normalizarlas para la comparación
                Iterator<String> fieldNames = diaNode.fieldNames();
                boolean tieneTodasLasComidas = true;

                while (fieldNames.hasNext()) {
                    String key = fieldNames.next();
                    // Normalizar las claves del JSON (sin acentos)
                    String keySinAcentos = quitarAcentos(key);

                    // Verificar si alguna comida falta
                    if (!keySinAcentos.equals(desayuno) && !keySinAcentos.equals(colacion1) &&
                            !keySinAcentos.equals(comida) && !keySinAcentos.equals(colacion2) &&
                            !keySinAcentos.equals(cena)) {
                        tieneTodasLasComidas = false;
                        break;
                    }
                }

                // Si falta alguna comida
                if (!tieneTodasLasComidas) {
                    return "";
                    //return "Faltan algunas comidas para el " + field.getKey();
                }
            }

            return contentString;
        } catch (Exception e) {
            e.printStackTrace();
            //return "Error al validar la estructura: " + e.getMessage();
            return "";
        }
    }

    // Método para normalizar la cadena y eliminar los acentos
    public static String quitarAcentos(String cadena) {
        String nfd = Normalizer.normalize(cadena, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfd).replaceAll("");
    }*/

    /*********************** CHATGPT SOLUCION 2 ************************************************************/

    public String getPrompt(int idCliente) {
        Cliente cliente = obtenerCliente(idCliente);
        String gender = cliente.getSexo().toLowerCase();
        int age = calcularAniosDate(cliente.getFechaNacimiento());
        return construirJsonRequest(generarPromptConRespuestas(idCliente, gender, age));
    }

    private Cliente obtenerCliente(int idCliente) {
        return Optional.ofNullable(clienteService.findById(idCliente))
                .orElseThrow(() -> new ClienteNoEncontradoException("Cliente no encontrado"));
    }

    public String generarPromptConRespuestas(int idCliente, String gender, int age) {
        List<RespuestaDTO> respuestas = formularioService.obtenerRespuestasPorCliente(idCliente);
        if (respuestas.isEmpty()) throw new ChatGPTException("El cliente " + idCliente + " no tiene información alimentaria.");

        StringBuilder respuestasConcatenadas = new StringBuilder();
        respuestas.forEach(respuesta -> {
            if (!respuesta.getRespuesta().isEmpty()) {
                respuestasConcatenadas.append(String.format("- Pregunta: %s Respuesta: %s",
                        escapeJson(respuesta.getPregunta()), escapeJson(respuesta.getRespuesta())));
            }
        });

        String initial = String.format("Soy %s de %d años de edad, mido %s, peso %s, y %s",
                gender, age, respuestas.get(0).getEstatura(), respuestas.get(0).getPeso(), ATHLETE_PROGRAM);

        if (respuestasConcatenadas.length() > 0) {
            respuestasConcatenadas.insert(0, " Recientemente conteste el siguiente cuestionario sobre salud: ");
        }

        return initial + respuestasConcatenadas.toString();
    }

    private String escapeJson(String input) {
        return input.replace("\"", "\\\"");
    }

    private String construirJsonRequest(String customerPrompt) {
        String temperatureFormatted = DECIMAL_FORMAT.format(TEMPERATURE);
        String topPFormatted = DECIMAL_FORMAT.format(TOP_P);
        int dias = obtenerDiasMesActual();

        return String.format("{\n" +
                "  \"model\": \"%s\",\n" +
                "  \"messages\": [\n" +
                "    {\n" +
                "      \"role\": \"system\",\n" +
                "      \"content\": \"Eres un nutriólogo que genera un documento JSON con las dietas completas para %d días, separados como dia1, dia2, " +
                "dia3...etc., IMPORTANTE: SIEMPRE GENERA LOS %d DÍAS Y NO TOMES EN CUENTA EL CUESTIONARIO SOBRE SALUD QUE SE TE ENVIA PARA LA GENERACIÓN DEL NUMERO DE DÍAS SOLICITADO. " +
                "Respeta estrictamente las restricciones y preferencias alimenticias indicadas por el usuario (por ejemplo: tipo de alimentación vegana, crudista, keto, etc.). Bajo ninguna circunstancia debes incluir " +
                "alimentos o preparaciones que violen estas restricciones. Toma en cuenta el cuestionario de salud y adapta las sugerencias de los planes alimenticios a las enfermedades, alergias, intolerancias, gustos y " +
                "el tipo de alimentación indicado por el usuario. También trata de ser variado en los alimentos que incluyes y utiliza ingredientes típicos y accesibles en la región indicada (Puebla, México). " +
                "La dieta debe incluir las tres comidas principales del día y las dos colaciones, además de bebidas, con " +
                "cantidades exactas en gramos para cada comida. Todos los días deben estar completos, sin saltos de línea, puntos suspensivos ni omisiones.  No se deben " +
                "incluir comentarios o explicaciones. Solo devuelve el JSON. Ejemplo de formato: {\\\"día1\\\": {\\\"desayuno\\\": \\\"150 gramos de avena, 200 gramos de fresas, 300 mililitros de agua\\\", \\\"colación1\\\": " +
                "\\\"100 gramos de almendras, 250 mililitros de jugo de naranja\\\", \\\"comida\\\": \\\"200 gramos de pechuga de pollo, 150 gramos de arroz integral, 100 gramos " +
                "de verduras mixtas, 300 mililitros de agua\\\", \\\"colación2\\\": \\\"150 gramos de yogur natural sin lactosa, 50 gramos de nueces\\\", \\\"cena\\\": " +
                "\\\"150 gramos de pescado a la plancha, 100 gramos de brócoli al vapor, 300 mililitros de agua\\\"}, \\\"día2\\\": {\\\"desayuno\\\": \\\"...\\\", \\\"colación1\\\": " +
                "\\\"...\\\", \\\"comida\\\": \\\"...\\\", \\\"colación2\\\": \\\"...\\\", \\\"cena\\\": \\\"...\\\"}, ...}\" \n" +
                "    },\n" +
                "    {\n" +
                "      \"role\": \"user\",\n" +
                "      \"content\": \"%s\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"temperature\": %s,\n" +
                "  \"max_tokens\": %d,\n" +
                "  \"top_p\": %s,\n" +
                "  \"store\": false\n" +
                "}", MODEL, dias, dias, customerPrompt, temperatureFormatted, MAX_TOKENS, topPFormatted);
    }

    private int obtenerDiasMesActual() {
        return LocalDate.now().lengthOfMonth();
    }

    public String generarGuiaSPEC(String prompt) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL_OPENAI))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(prompt))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                logger.error("Error al obtener la guía alimenticia: {} - {}", response.statusCode(), response.body());
                throw new RuntimeException("Error en la respuesta de la API: " + response.statusCode() + " " + response.body());
            }
        } catch (Exception e) {
            logger.error("Error en la solicitud a la API de ChatGPT: {}", e.getMessage());
            throw new RuntimeException("Error al contactar con la API de ChatGPT.");
        }
    }

    public String isValidDiet(String guiaSpec) {
        try {
            String response = obtenerContenido(guiaSpec);
            String cleanedResponse = removeJsonBlock(response);
            return validarEstructura(cleanedResponse);
        } catch (Exception e) {
            logger.error("Error al validar la dieta: {}", e.getMessage());
            return "";
        }
    }

    private String obtenerContenido(String responseGPT) {
        JSONObject response = new JSONObject(responseGPT);
        return Optional.ofNullable(response.getJSONArray("choices"))
                .filter(choices -> !choices.isEmpty())
                .map(choices -> choices.getJSONObject(0).getJSONObject("message").getString("content"))
                .orElse("No se encontró contenido");
    }

    private String removeJsonBlock(String response) {
        // Expresión regular para eliminar el bloque ```json ... ```
        String regex = "```json\\s*([\\s\\S]*?)```";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(response);

        if (matcher.find()) {
            // Devuelve solo el contenido del JSON sin el bloque ```json
            return matcher.group(1).trim();
        }
        return response; // Si no tiene el bloque, devolvemos la respuesta original
    }

    private String validarEstructura(String contentString) {
        try {
            JsonNode rootNode = objectMapper.readTree(contentString);
            if (!rootNode.isObject()) return "";

            Iterator<Map.Entry<String, JsonNode>> fields = rootNode.fields();
            while (fields.hasNext()) {
                JsonNode diaNode = fields.next().getValue();

                // Convertir el Iterator a un List
                List<String> fieldNamesList = new ArrayList<>();
                diaNode.fieldNames().forEachRemaining(fieldNamesList::add);

                // Verificar si contiene todas las comidas
                boolean tieneTodasLasComidas = fieldNamesList.stream()
                        .map(ChatGPT::quitarAcentos)
                        .collect(Collectors.toSet())
                        .containsAll(Arrays.asList("desayuno", "colacion1", "comida", "colacion2", "cena"));

                if (!tieneTodasLasComidas) return "";
            }

            return contentString;
        } catch (Exception e) {
            logger.error("Error al validar estructura de la dieta: {}", e.getMessage());
            return "";
        }
    }

    public static String quitarAcentos(String cadena) {
        return Normalizer.normalize(cadena, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

}

