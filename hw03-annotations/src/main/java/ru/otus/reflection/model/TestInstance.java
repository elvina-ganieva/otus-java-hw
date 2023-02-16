package ru.otus.reflection.model;

import java.lang.reflect.Method;

public class TestInstance<T> {
    private final Method before;
    private final Method after;
    private final Method test;
    private final T testInstance;
    private Boolean isSuccess = true;

    public TestInstance(Method before, Method after, Method test, T testInstance) {
        this.before = before;
        this.after = after;
        this.test = test;
        this.testInstance = testInstance;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public Method getBefore() {
        return before;
    }

    public Method getAfter() {
        return after;
    }

    public Method getTest() {
        return test;
    }

    public T getTestInstance() {
        return testInstance;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }
}
