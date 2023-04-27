package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ru.otus.model.Measurement;

import java.io.IOException;

public class MeasurementDeserializer extends JsonDeserializer<Measurement> {
    @Override
    public Measurement deserialize(JsonParser p, DeserializationContext ctxt) {
        JsonNode node;

        try {
            node = p.getCodec().readTree(p);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }

        String name = node.get("name").asText();
        double value = node.get("value").asDouble();
        return new Measurement(name, value);
    }
}
