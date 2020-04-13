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
package com.speedment.common.codegen.provider;

import com.speedment.common.codegen.Transform;
import com.speedment.common.codegen.internal.DefaultDependencyManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnJre;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

final class StandardTransformFactoryTest {

    private static final String NAME = "A";
    private StandardTransformFactory instance;

    @BeforeEach
    void setup() {
        instance = new StandardTransformFactory(NAME);
    }

    @Test
    void getName() {
        assertEquals(NAME, instance.getName());
    }

    @Test
    void install() {
        final Transform<Integer, String> transform = (g, i) -> Optional.of(Integer.toString(i));
        instance.install(Integer.class, String.class, () -> transform);
        final StandardGenerator generator = new StandardGenerator(new DefaultDependencyManager(), instance);
        final Optional<String> result = generator.on(1);
        assertEquals("1", result.orElseThrow(NoSuchElementException::new));
    }

    @Test
    void allFrom() {
        final Transform<Integer, String> transform = (g, i) -> Optional.of(Integer.toString(i));
        instance.install(Integer.class, String.class, () -> transform);

        final Set<Map.Entry<Class<?>, Transform<Integer, ?>>> entries = instance.allFrom(Integer.class);
        Map.Entry<Class<?>, Transform<Integer, ?>> transformEntry = entries.iterator().next();
        assertEquals(String.class, transformEntry.getKey());
        assertSame(transform, transformEntry.getValue());
    }
}