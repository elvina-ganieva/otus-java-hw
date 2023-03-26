package ru.otus.aop.proxy;


public class TestLogging implements TestLoggingInterface {

    @Log
    @Override
    public void calculation(int param) {

    }

    @Override
    public void calculation(String param) {

    }

    @Log
    @Override
    public void calculation(int param1, int param2) {

    }

    @Override
    public void calculation(int param1, int param2, String param3) {

    }
}
