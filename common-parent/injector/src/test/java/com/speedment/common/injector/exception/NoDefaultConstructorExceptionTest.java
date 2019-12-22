package com.speedment.common.injector.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class NoDefaultConstructorExceptionTest {

    private static final String MSG = "Arne";

    @Test
    void create1() {
        assertTrue(new NoDefaultConstructorException(MSG).getMessage().contains(MSG));
    }

    @Test
    void create2() {
        assertTrue(new NoDefaultConstructorException(new RuntimeException(MSG)).getMessage().contains(MSG));
    }

    @Test
    void createEx() {
        assertTrue(new NoDefaultConstructorException(MSG, new RuntimeException()).getMessage().contains(MSG));
    }

}