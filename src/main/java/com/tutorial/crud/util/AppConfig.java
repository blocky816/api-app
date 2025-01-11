package com.tutorial.crud.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;

@Configuration
public class AppConfig {
    @Bean
    public HttpClient httpClient() {
        return HttpClient.newHttpClient();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        SimpleModule module = new SimpleModule();
        objectMapper.registerModule(new SimpleModule().addDeserializer(Object.class, new RespuestasDeserializer()));
        return objectMapper;
    }

    @Bean public RestTemplate restTemplate() { return new RestTemplate(); }
}
