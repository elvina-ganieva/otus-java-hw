package ru.otus.dataprocessor;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class FileSerializer implements Serializer {

    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        //todo формирует результирующий json и сохраняет его в файл

        var gson = new Gson();
        var jsonMap = gson.toJson(data);

        try {
            Files.write(Path.of(fileName), jsonMap.getBytes());
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
