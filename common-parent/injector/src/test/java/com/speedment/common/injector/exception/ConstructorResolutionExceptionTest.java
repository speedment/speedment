package com.speedment.common.injector.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class ConstructorResolutionExceptionTest {

    @Test
    void construct() {
        assertDoesNotThrow(() -> new ConstructorResolutionException("A"));
    }

}