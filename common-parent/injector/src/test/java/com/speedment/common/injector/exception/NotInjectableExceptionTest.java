package com.speedment.common.injector.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class NotInjectableExceptionTest {

    private static final String MSG = "Arne";

    @Test
    void create1() {
        final NotInjectableException ex = new NotInjectableException(Integer.class);
        assertTrue(ex.getMessage().contains(Integer.class.getSimpleName()));
        assertMsgReasonable(ex);
    }

    @Test
    void create2() {
        final NotInjectableException ex = new NotInjectableException(Integer.class, new RuntimeException());
        assertTrue(ex.getMessage().contains(Integer.class.getSimpleName()));
        assertMsgReasonable(ex);
    }

    void assertMsgReasonable(NotInjectableException ex) {
        assertTrue(ex.getMessage().contains("injected"));
    }

}