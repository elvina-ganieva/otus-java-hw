package ru.otus.reflection.service;

import java.io.File;

public class ClassAccessService {

    /**
     * Получение класса по его имени.
     *
     * @param classSimpleName имя класса
     * @return экземпляр класса
     */
    public Class<?> getClassByName(String classSimpleName) {
        String dotClass = ".class";
        String javaClassPath = System.getProperty("java.class.path").split(";")[0];
        File file = searchFile(new File(javaClassPath), classSimpleName + dotClass);
        if (file == null)
            throw new RuntimeException("Переданный класс не найден.");

        String filePath = file.getPath();
        String testClassName = filePath
                .substring(javaClassPath.length() + 1, filePath.length() - dotClass.length())
                .replace('\\', '.');

        Class<?> clazz;
        try {
            clazz = Class.forName(testClassName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Переданный класс не найден.");
        }
        return clazz;
    }

    private File searchFile(File file, String className) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                File found = searchFile(f, className);
                if (found != null)
                    return found;
            }
        } else {
            if (file.getName().equals(className)) {
                return file;
            }
        }
        return null;
    }
}
