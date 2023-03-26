package ru.otus.aop.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.stream.Collectors;

class Ioc {

    private Ioc() {
    }

    static TestLoggingInterface createLoggingClass() {
        InvocationHandler handler = new CustomInvocationHandler(new TestLogging());
        return (TestLoggingInterface) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class}, handler);
    }

    static class CustomInvocationHandler implements InvocationHandler {
        private final TestLoggingInterface loggingInterface;

        private final Set<Method> methods = new HashSet<>();

        CustomInvocationHandler(TestLoggingInterface loggingInterface) {
            this.loggingInterface = loggingInterface;
            this.methods.addAll(Arrays.stream(loggingInterface.getClass().getMethods())
                    .filter(element -> element.isAnnotationPresent(Log.class))
                    .collect(Collectors.toSet()));
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            var classMethod = methods.stream()
                    .filter(element ->
                            element.getName().equals(method.getName()) &&
                                    Arrays.equals(element.getParameterTypes(), method.getParameterTypes())
                    ).findFirst();
            if (classMethod.isPresent()) {
                var params = Arrays.stream(args)
                        .map(Object::toString)
                        .collect(Collectors.joining(", "));
                System.out.printf("executed method: %s, param: %s%n", method.getName(), params);
            }
            return method.invoke(loggingInterface, args);
        }

        @Override
        public String toString() {
            return "CustomInvocationHandler{" +
                    "loggingClass=" + loggingInterface +
                    '}';
        }
    }
}
