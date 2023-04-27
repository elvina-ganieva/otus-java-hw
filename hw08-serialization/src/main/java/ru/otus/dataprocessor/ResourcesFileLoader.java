package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.otus.model.Measurement;

import javax.json.Json;
import java.lang.reflect.Type;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    private final String fileName;

    private final ObjectMapper mapper = new ObjectMapper();

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;

        var module = new SimpleModule();
        module.addDeserializer(Measurement.class, new MeasurementDeserializer());
        mapper.registerModule(module);
    }

    @Override
    public List<Measurement> load() {
        //todo читает файл, парсит и возвращает результат
        List<Measurement> list;

        try (var jsonReader = Json.createReader(ResourcesFileLoader.class.getClassLoader().getResourceAsStream(fileName))) {
            var jsonString = jsonReader.read().toString();

            list = deserializeStringWithJackson(jsonString);
//            list = deserializeStringWithGson(jsonString);
        }
        return list;
    }

    private List<Measurement> deserializeStringWithJackson(String jsonString) {
        var reader = mapper.readerForListOf(Measurement.class);

        try {
            return reader.readValue(jsonString);
        } catch (JsonProcessingException e) {
            throw new FileProcessException(e);
        }
    }

    private List<Measurement> deserializeStringWithGson(String jsonString) {
        var gson = new Gson();
        Type type = new TypeToken<List<Measurement>>() {}.getType();
        return gson.fromJson(jsonString, type);
    }
}

