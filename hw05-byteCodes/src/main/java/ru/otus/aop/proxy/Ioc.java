package ru.otus.aop.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

        private final List<Method> methods = new ArrayList<>();

        CustomInvocationHandler(TestLoggingInterface loggingInterface) {
            this.loggingInterface = loggingInterface;
            this.methods.addAll(Arrays.stream(loggingInterface.getClass().getMethods()).toList());
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            var classMethod = methods.stream()
                    .filter(element ->
                            element.getName().equals(method.getName()) &&
                                    Arrays.equals(element.getParameterTypes(), method.getParameterTypes())
                    ).findFirst();
            if (classMethod.isPresent() && classMethod.get().isAnnotationPresent(Log.class)) {
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
