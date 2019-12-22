package com.speedment.common.injector.execution;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class ExecutionTest {

    @Test
    void applyOrNullException() {
        final Execution.ClassMapper classMapper = new Execution.ClassMapper() {
            @Override
            public <T> T apply(Class<T> type) {
                throw new RuntimeException();
            }
        };
        assertNull(classMapper.applyOrNull(Integer.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    void applyOrNull() {
        final int expected = 1;
        final Execution.ClassMapper classMapper = new Execution.ClassMapper() {
            @Override
            public <T> T apply(Class<T> type) {
                return (T) (Integer) expected;
            }
        };
        assertEquals(expected , classMapper.applyOrNull(Integer.class));
    }

}