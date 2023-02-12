package ru.otus.reflection.service;

import ru.otus.reflection.ReflectionHelper;
import ru.otus.reflection.model.TestInstance;

public class TestRunner {

    /**
     * Запуск теста.
     *
     * @param test тест для запуска
     */
    public void run(TestInstance<?> test) {
        boolean isBeforeSuccess = true;

        try {
            ReflectionHelper.callMethod(test.getTestInstance(), test.getBefore().getName());
        } catch (RuntimeException e) {
            isBeforeSuccess = false;
            test.setSuccess(false);
        }

        if (isBeforeSuccess) {
            try {
                ReflectionHelper.callMethod(test.getTestInstance(), test.getTest().getName());
            } catch (RuntimeException e) {
                test.setSuccess(false);
            }
        }

        try {
            ReflectionHelper.callMethod(test.getTestInstance(), test.getAfter().getName());
        } catch (RuntimeException ignored) {
        }
    }
}
