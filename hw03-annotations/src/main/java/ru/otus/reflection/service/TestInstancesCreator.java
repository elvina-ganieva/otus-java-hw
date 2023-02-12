package ru.otus.reflection.service;

import ru.otus.reflection.ReflectionHelper;
import ru.otus.reflection.annotations.After;
import ru.otus.reflection.annotations.Before;
import ru.otus.reflection.annotations.Test;
import ru.otus.reflection.model.AllMethods;
import ru.otus.reflection.model.TestInstance;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestInstancesCreator {

    /**
     * Создание экземлпяров тестов.
     *
     * @param clazz класс, из которого получаем методы
     * @return лист из экземпляров тестов
     */
    public List<TestInstance<?>> createTestsFromClassMethods(Class<?> clazz) {
        List<Method> methods = Arrays.stream(clazz.getDeclaredMethods()).toList();

        AllMethods allMethods = new AllMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(Test.class)) {
                allMethods.addTest(method);
            } else if (method.isAnnotationPresent(Before.class)) {
                allMethods.setBefore(method);
            } else if (method.isAnnotationPresent(After.class)) {
                allMethods.setAfter(method);
            }
        }

        List<TestInstance<?>> tests = new ArrayList<>();

        for (Method method : allMethods.getTests()) {
            tests.add(new TestInstance<>(
                            allMethods.getBefore(),
                            allMethods.getAfter(),
                            method,
                            ReflectionHelper.instantiate(clazz)
                    )
            );
        }
        return tests;
    }
}
