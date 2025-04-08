package com.tutorial.crud.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class RespuestasDeserializer extends JsonDeserializer<Object> {
    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        /*String value = p.getText().trim();
        if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            return Boolean.parseBoolean(value); // Convertir a Boolean
        } else {
            return value; // Devolver como String si no es un valor booleano
        }*/
        try {
            // Intentar deserializar como un mapa o lista si aplica
            if (p.getCurrentToken().isStructStart()) {
                return ctxt.readTree(p);
            }
            // Manejar valores booleanos y cadenas
            String value = p.getText().trim();
            if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
                return Boolean.parseBoolean(value);
            }
            return value;
        } catch (Exception e) {
            throw new IOException("Error al deserializar el valor: " + p.getText(), e);
        }
    }
}
