package ru.otus.reflection;

import ru.otus.reflection.model.TestInstance;
import ru.otus.reflection.service.ClassAccessService;
import ru.otus.reflection.service.TestInstancesCreator;
import ru.otus.reflection.service.TestRunner;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnnotationTestStarter {

    private static final Logger LOGGER = Logger.getLogger(AnnotationTestStarter.class.getName());

    public static void main(String[] args) {
        if (args.length != 1)
            throw new RuntimeException("Необходимо передавать имя класса с тестами.");

        Class<?> clazz = new ClassAccessService().getClassByName(args[0]);

        List<TestInstance<?>> tests = new TestInstancesCreator().createTestsFromClassMethods(clazz);

        tests.forEach(test -> new TestRunner().run(test));

        long successCount = tests.stream().filter(TestInstance::getSuccess).count();
        String result = String.format("Статистика тестов: успешно - %d, упало - %d, всего - %d",
                successCount, tests.size() - successCount, tests.size());
        LOGGER.log(Level.INFO, result);
    }
}
