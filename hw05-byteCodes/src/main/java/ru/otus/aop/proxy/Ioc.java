package ru.otus.aop.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
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
        private final TestLoggingInterface loggingClass;

        CustomInvocationHandler(TestLoggingInterface loggingClass) {
            this.loggingClass = loggingClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            var classMethod = loggingClass.getClass().getMethod(method.getName(), method.getParameterTypes());
            if (classMethod.isAnnotationPresent(Log.class)) {
                var params = Arrays.stream(args)
                        .map(Object::toString)
                        .collect(Collectors.joining(", "));
                System.out.printf("executed method: %s, param: %s%n", method.getName(), params);
            }
            return method.invoke(loggingClass, args);
        }

        @Override
        public String toString() {
            return "CustomInvocationHandler{" +
                    "loggingClass=" + loggingClass +
                    '}';
        }
    }
}
