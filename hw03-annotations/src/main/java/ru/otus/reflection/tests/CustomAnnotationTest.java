package ru.otus.reflection.tests;

import ru.otus.reflection.annotations.After;
import ru.otus.reflection.annotations.Before;
import ru.otus.reflection.annotations.Test;

public class CustomAnnotationTest {

    @Before
    public void setUp() {
        System.out.println("set up");
    }

    @After
    public void tearDown() {
        System.out.println("tear down");
    }

    @Test
    public void test1() {
        System.out.println("test1");
    }

    @Test
    public void test2() {
        System.out.println("test2");
        String s = null;
        System.out.println(s.length());
    }
}
