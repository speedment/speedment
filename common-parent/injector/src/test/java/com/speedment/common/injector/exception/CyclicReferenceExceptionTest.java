package com.speedment.common.injector.exception;

import org.junit.jupiter.api.Test;

import static java.util.Collections.*;
import static org.junit.jupiter.api.Assertions.*;

final class CyclicReferenceExceptionTest {

    @Test
    void construct1() {
        testGetMessage(new CyclicReferenceException(singleton(Integer.class)));
    }

    @Test
    void construct2() {
        testGetMessage(new CyclicReferenceException(Integer.class));
    }

    @Test
    void construct3() {
        final CyclicReferenceException e0 = new CyclicReferenceException(Long.class);
        testGetMessage(new CyclicReferenceException(Integer.class, e0));
    }

    void testGetMessage(CyclicReferenceException ex) {
        assertTrue(ex.getMessage().contains("Cyclic"));
    }
}