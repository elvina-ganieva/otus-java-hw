package ru.otus.jdbc.mapper;

import ru.otus.crm.model.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Constructor<T> constructor;

    private final String className;

    private final Field idField;

    private final List<Field> allFields;

    private final List<Field> fieldsWithoutId;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        try {
            this.constructor = clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(String.format("Конструктор класса %s не найден.", clazz.getName()), e);
        }

        this.className = clazz.getName()
                .substring(clazz.getName().lastIndexOf(".") + 1)
                .toLowerCase();

        this.idField = Arrays.stream(clazz.getDeclaredFields())
                .filter(el -> el.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Поле %s с аннотацией Id не найдено."));

        this.allFields = Arrays.stream(clazz.getDeclaredFields())
                .collect(Collectors.toList());

        this.fieldsWithoutId = Arrays.stream(clazz.getDeclaredFields())
                .filter(el -> !el.isAnnotationPresent(Id.class))
                .collect(Collectors.toList());
    }

    @Override
    public String getName() {
        return className;
    }

    @Override
    public Constructor<T> getConstructor() {
        return this.constructor;
    }

    @Override
    public Field getIdField() {
        return this.idField;
    }

    @Override
    public List<Field> getAllFields() {
        return allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fieldsWithoutId;
    }
}
