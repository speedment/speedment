/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.common.injector;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Emil Forslund
 * @since  3.1.17
 */
class TestConstructorInvocation {

    public static class ClassWithPrivateDefaultConstructor {
        private ClassWithPrivateDefaultConstructor() {}
    }

    public static class ClassWithDefaultConstructor {
        public ClassWithDefaultConstructor() {}
    }

    public static class ClassWithOneParameterConstructor {
        private final ClassWithDefaultConstructor other;

        public ClassWithOneParameterConstructor(ClassWithDefaultConstructor other) {
            this.other = other;
        }
    }

    public static class ClassWithTwoParametersConstructor {
        private final ClassWithDefaultConstructor first;
        private final ClassWithOneParameterConstructor second;

        public ClassWithTwoParametersConstructor(
                ClassWithDefaultConstructor first,
                ClassWithOneParameterConstructor second) {
            this.first  = first;
            this.second = second;
        }
    }

    @Test
    void testPrivateDefaultConstructor() {

        // Private constructors are not allowed since 3.2.0
        assertThrows(Exception.class, () -> {
            final Injector injector = Injector.builder()
                .withComponent(ClassWithPrivateDefaultConstructor.class)
                .build();
        });

    }

    @Test
    void testDefaultConstructor() throws InstantiationException {
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
