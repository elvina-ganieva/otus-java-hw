package ru.otus.reflection.model;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AllMethods {
    private Method before;
    private Method after;
    private final List<Method> tests = new ArrayList<>();

    public Method getBefore() {
        return before;
    }

    public void setBefore(Method before) {
        this.before = before;
    }

    public Method getAfter() {
        return after;
    }

    public void setAfter(Method after) {
        this.after = after;
    }

    public List<Method> getTests() {
        return tests;
    }

    public void addTest(Method test) {
        this.tests.add(test);
    }
}
