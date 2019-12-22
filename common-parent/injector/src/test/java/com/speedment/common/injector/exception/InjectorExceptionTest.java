package com.speedment.common.injector.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class InjectorExceptionTest {

    private static final String MSG = "Arne";

    @Test
    void create1() {
        assertMsgIsResonable(new InjectorException(MSG));
    }

    @Test
    void create2() {
        assertMsgIsResonable(new InjectorException(MSG, new RuntimeException()));
    }

    @Test
    void createEx() {
        assertMsgIsResonable(new InjectorException(new RuntimeException(MSG)));
    }

    private void assertMsgIsResonable(InjectorException e) {
        assertTrue(e.getMessage().contains(MSG));
    }

}