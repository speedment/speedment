package com.speedment.common.injector.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class MisusedAnnotationExceptionTest {

    private static final String MSG = "Arne";

    @Test
    void create() {
        assertTrue(new MisusedAnnotationException(MSG).getMessage().contains(MSG));
    }

}