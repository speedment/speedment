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
package com.speedment.common.injector.internal.execution;

import com.speedment.common.injector.MissingArgumentStrategy;
import com.speedment.common.injector.State;
import com.speedment.common.injector.dependency.Dependency;
import com.speedment.common.injector.dependency.DependencyNode;
import com.speedment.common.injector.execution.Execution;
import com.speedment.common.injector.internal.dependency.DependencyImpl;
import com.speedment.common.injector.internal.dependency.DependencyNodeImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.*;

final class AbstractExecutionTest {

    private static final State STATE = State.RESOLVED;
    private static final State STATE_REQUIRED = State.CREATED;
    private static final MissingArgumentStrategy STRATEGY = MissingArgumentStrategy.SKIP_INVOCATION;

    private Execution<Foo> instance;
    private Dependency dependency;

    private static final class Foo {
        private int value;

        public Foo(int value) {
            this.value = value;
        }
    }

    @BeforeEach
    void setup() {
        final DependencyNode dependencyNode = new DependencyNodeImpl(Integer.class);
        dependency = new DependencyImpl(dependencyNode, STATE_REQUIRED);
        instance = new AbstractExecution<Foo>(Foo.class, STATE, singleton(dependency), STRATEGY) {
            @Override
            public boolean invoke(Foo component, ClassMapper classMapper) {
                return false;
            }
        };
    }

    @Test
    void getName() {
        assertTrue(instance.getName().contains(Integer.class.getSimpleName()));
    }

    @Test
    void getType() {
        assertEquals(Foo.class, instance.getType());
    }

    @Test
    void getState() {
        assertEquals(STATE, instance.getState());
    }

    @Test
    void getDependencies() {
        assertEquals(singleton(dependency), instance.getDependencies());
    }

    @Test
    void getMissingArgumentStrategy() {
        assertEquals(STRATEGY, instance.getMissingArgumentStrategy());
    }

    @Test
    void testToString() {
        final String toString = instance.toString();
        assertTrue(toString.toLowerCase().contains(STATE_REQUIRED.name().toLowerCase()));
        assertTrue(toString.contains(Integer.class.getSimpleName()));
    }
}