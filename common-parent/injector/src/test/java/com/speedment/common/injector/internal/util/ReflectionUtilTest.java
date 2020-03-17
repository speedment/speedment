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
package com.speedment.common.injector.internal.util;

import com.speedment.common.injector.MissingArgumentStrategy;
import com.speedment.common.injector.MyInjectorProxy;
import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.Execute;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.InjectKey;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.*;

final class ReflectionUtilTest {

    private static class Foo {
        public int foo;
        public int foo() {return foo;}
    }

    private static class Bar extends Foo {
        public int bar;
        public int bar() {return foo;}
    }

    @InjectKey(Baz.class)
    private final static class Baz extends Bar {
        public int baz;

        public Baz(Foo foo, int bar, int baz) {
            this.foo = foo.foo();
            this.bar = bar;
            this.baz = baz;
        }

        public int baz() {return baz;}
        @Execute(missingArgument = MissingArgumentStrategy.SKIP_INVOCATION)
        public int baz2() {return baz;}
        @ExecuteBefore(value =  State.STOPPED, missingArgument = MissingArgumentStrategy.SKIP_INVOCATION)
        public int baz3() {return baz;}
    }

    @Test
    void traverseFields() throws NoSuchFieldException {
        final List<Field> fields = ReflectionUtil.traverseFields(Bar.class)
                .filter(f -> !f.getName().contains("jacocoData")) // Jaccoco intrumentation fields
                .collect(toList());
        assertEquals(2, fields.size(), fields.toString());
    }

    @Test
    void traverseMethods() {
        final Set<String> methodNames = Stream.of("foo", "bar").collect(toSet());
        final List<Method> methods = ReflectionUtil.traverseMethods(Bar.class)
                .filter(m -> methodNames.contains(m.getName()))
                .collect(toList());
        assertEquals(2, methods.size(), methods.toString());
    }

    @Test
    void traverseAncestors() {
        final Set<Class<?>> expected = Stream.of(Object.class, Foo.class, Bar.class).collect(toSet());
        final Set<Class<?>> actual = ReflectionUtil.traverseAncestors(Bar.class)
                .collect(toSet());
        assertEquals(expected, actual);
    }

    @Test
    void missingArgumentStrategy() throws NoSuchMethodException {
        final Method method = Baz.class.getMethod("baz");
        final MissingArgumentStrategy strategy = ReflectionUtil.missingArgumentStrategy(method);
        assertEquals(MissingArgumentStrategy.THROW_EXCEPTION, strategy);
    }

    @Test
    void missingArgumentStrategyExecute() throws NoSuchMethodException {
        final Method method = Baz.class.getMethod("baz2");
        final MissingArgumentStrategy strategy = ReflectionUtil.missingArgumentStrategy(method);
        assertEquals(MissingArgumentStrategy.SKIP_INVOCATION, strategy);
    }

    @Test
    void missingArgumentStrategyExecuteBefore() throws NoSuchMethodException {
        final Method method = Baz.class.getMethod("baz3");
        final MissingArgumentStrategy strategy = ReflectionUtil.missingArgumentStrategy(method);
        assertEquals(MissingArgumentStrategy.SKIP_INVOCATION, strategy);
    }

    @Test
    void tryToCreate() throws InstantiationException {
        final Optional<Integer> actual = ReflectionUtil.tryToCreate(Integer.class, new Properties(), emptyList(), emptySet(), new MyInjectorProxy());
        assertFalse(actual.isPresent());
    }

    @Test
    void errorMsg() {
        final String actual = ReflectionUtil.errorMsg(Baz.class, Arrays.asList(String.class, Long.class));
        assertTrue(actual.contains(Baz.class.getName()));
        assertTrue(actual.contains(Foo.class.getSimpleName()));
        assertTrue(actual.contains("Missing"));
    }

    @Test
    void parseChar() {
        assertEquals('A', ReflectionUtil.parse(Character.class, "A").orElseThrow(NoSuchElementException::new));
    }

    @Test
    void parseCharLarge() {
        assertThrows(IllegalArgumentException.class, () -> ReflectionUtil.parse(Character.class, "AB").orElseThrow(NoSuchElementException::new));
    }

}