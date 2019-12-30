package com.speedment.common.codegen;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class TransformTest {

    private static final Transform INSTANCE = (g, m) -> Optional.empty();

    @Test
    void is() {
        final Transform<String, String> t1 = new T1();
        final Transform<String, String> t2 = new T2();
        assertTrue(t1.is(T1.class));
        assertTrue(t2.is(T1.class));
        assertFalse(t1.is(T2.class));
    }

    private static class T1 implements Transform<String, String> {
        @Override
        public Optional<String> transform(Generator gen, String model) {
            return Optional.empty();
        }
    }

    private static class T2 extends T1 implements Transform<String, String> {}

}