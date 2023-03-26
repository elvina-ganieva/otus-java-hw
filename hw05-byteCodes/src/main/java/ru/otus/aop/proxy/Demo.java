package ru.otus.aop.proxy;

public class Demo {
    public static void main(String[] args) {
        TestLoggingInterface loggingClass = Ioc.createLoggingClass();
        loggingClass.calculation(1);
        loggingClass.calculation(2);
        loggingClass.calculation(3, 4);
        loggingClass.calculation(3, 4, "5");
        loggingClass.calculation("6");
    }
}
