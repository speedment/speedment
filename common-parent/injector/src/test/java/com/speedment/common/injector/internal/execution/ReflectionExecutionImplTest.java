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
import com.speedment.common.injector.MyInjectorProxy;
import com.speedment.common.injector.State;
import com.speedment.common.injector.dependency.Dependency;
import com.speedment.common.injector.dependency.DependencyNode;
import com.speedment.common.injector.execution.Execution;
import com.speedment.common.injector.internal.dependency.DependencyImpl;
import com.speedment.common.injector.internal.dependency.DependencyNodeImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.*;

final class ReflectionExecutionImplTest {

    private static final State STATE = State.RESOLVED;
    private static final State STATE_REQUIRED = State.CREATED;
    private static final MissingArgumentStrategy STRATEGY = MissingArgumentStrategy.SKIP_INVOCATION;

    private ReflectionExecutionImpl<Foo> instance;
    private Dependency dependency;
    private Method method;

    public static final class Foo {
        private int value;

        public Foo(int value) {
            this.value = value;
        }

        public void inc() {
            value++;
        }
    }

    @BeforeEach
    void setup() throws NoSuchMethodException {
        method = Foo.class.getMethod("inc");
        final DependencyNode dependencyNode = new DependencyNodeImpl(Integer.class);
        dependency = new DependencyImpl(dependencyNode, STATE_REQUIRED);
        instance = new ReflectionExecutionImpl<>(Foo.class, STATE, singleton(dependency), method, new MyInjectorProxy());
    }

    @Test
    void directMethodInvocation() throws InvocationTargetException, IllegalAccessException {
        final Foo foo = new Foo(0);
        method.invoke(foo);
        assertEquals(1, foo.value);
    }

    @Test
    void getName() {
        assertTrue(instance.getName().contains("Foo.inc()"));
    }

    @Test
    void getMethod() {
        assertSame(method, instance.getMethod());
    }

    @Test
    void invoke() throws InvocationTargetException, IllegalAccessException {
        final Foo foo = new Foo(0);
        final Execution.ClassMapper classMapper = new Execution.ClassMapper() {
            @Override
            public <T> T apply(Class<T> type) {
                return null;
            }
        };
        assertTrue(instance.invoke(foo, classMapper));
        assertEquals(1, foo.value);
    }

    @Test
    void testToString() {
        final String toString = instance.toString();
        System.out.println("toString = " + toString);
        assertTrue(toString.contains("Foo"));
        assertTrue(toString.contains("inc()"));
    }
}