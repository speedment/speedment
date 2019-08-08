package com.speedment.common.injector;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Emil Forslund
 * @since  3.1.17
 */
class TestConstructorInvocation {

    static class ClassWithDefaultConstructor {
        private ClassWithDefaultConstructor() {}
    }

    static class ClassWithOneParameterConstructor {
        private final ClassWithDefaultConstructor other;

        private ClassWithOneParameterConstructor(ClassWithDefaultConstructor other) {
            this.other = other;
        }
    }

    static class ClassWithTwoParametersConstructor {
        private final ClassWithDefaultConstructor first;
        private final ClassWithOneParameterConstructor second;

        private ClassWithTwoParametersConstructor(
                ClassWithDefaultConstructor first,
                ClassWithOneParameterConstructor second) {
            this.first  = first;
            this.second = second;
        }
    }

    @Test
    void testPrivateDefaultConstructor() throws InstantiationException {
        final Injector injector = Injector.builder()
            .withComponent(ClassWithDefaultConstructor.class)
            .build();

        final ClassWithDefaultConstructor instance =
            injector.getOrThrow(ClassWithDefaultConstructor.class);

        assertNotNull(instance);
    }

    @Test
    void testConstructorWithOneParameter() throws InstantiationException {
        final Injector injector = Injector.builder()
            .withComponent(ClassWithOneParameterConstructor.class)
            .withComponent(ClassWithDefaultConstructor.class)
            .build();

        final ClassWithOneParameterConstructor instance =
            injector.getOrThrow(ClassWithOneParameterConstructor.class);

        assertNotNull(instance);
        assertNotNull(instance.other);
    }

    @Test
    void testConstructorWithTwoParameters() throws InstantiationException {
        final Injector injector = Injector.builder()
            .withComponent(ClassWithTwoParametersConstructor.class)
            .withComponent(ClassWithOneParameterConstructor.class)
            .withComponent(ClassWithDefaultConstructor.class)
            .build();

        final ClassWithTwoParametersConstructor instance =
            injector.getOrThrow(ClassWithTwoParametersConstructor.class);

        assertNotNull(instance);
        assertNotNull(instance.first);
        assertNotNull(instance.second);
    }
}
