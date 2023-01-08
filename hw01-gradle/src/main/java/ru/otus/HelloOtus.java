package ru.otus;


import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.google.common.math.BigDecimalMath.roundToDouble;


/**
 * To start the application:
 * ./gradlew build
 * java -jar ./hw01-gradle/build/libs/gradleHelloWorld-0.1.jar
 * <p>
 * To unzip the jar:
 * unzip -l hw01-gradle.jar
 * unzip -l gradleHelloWorld-0.1.jar
 */
public class HelloOtus {
    public static void main(String... args) {
        BigDecimal bigDecimal = new BigDecimal("33.333");
        double result = roundToDouble(bigDecimal, RoundingMode.CEILING);
        System.out.println(result);
    }
}
