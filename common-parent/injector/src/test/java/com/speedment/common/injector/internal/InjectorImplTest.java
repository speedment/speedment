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
package com.speedment.common.injector.internal;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.InjectorBuilder;
import com.speedment.common.injector.MissingArgumentStrategy;
import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.common.injector.exception.InjectorException;
import com.speedment.common.logger.Level;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.*;

final class InjectorImplTest {

    private InjectorImpl injector;

    @InjectKey(Foo.class) public interface Foo {};
    public static final class Bar implements Foo {};
    public static final class Baz implements Foo {
        @Inject public Bar bar;
        public boolean stopped;
        @ExecuteBefore(State.STOPPED) public void stop() {
            stopped = true;
        }
    };
    public static final class Unstoppable {
        @ExecuteBefore(State.STOPPED) void stop(Foo foo){}
    }
    public static final class Stoppable {
        @ExecuteBefore(value = State.STOPPED, missingArgument = MissingArgumentStrategy.SKIP_INVOCATION) void stop(Foo foo){}
    }

    @BeforeEach
    void setup() throws InstantiationException {
        injector = (InjectorImpl) Injector.builder()
                .withComponent(Bar.class)
                .withComponent(Baz.class)
                .build();
/*        final Injectable<Integer> integerInjectable0 = new Injectable<>(Integer.class, () -> 0);
        final Injectable<Integer> integerInjectable1 = new Injectable<>(Integer.class, () -> 1);
        final Injectable<String> stringInjectable = new Injectable<>(String.class, () -> "A");
        final Set<Injectable<?>> injectables = Stream.of(integerInjectable0, integerInjectable1, stringInjectable).collect(toCollection(LinkedHashSet::new));
        final List<Object> instances = injectables.stream().map(i -> i.supplier().get()).collect(toList());
        final Properties properties = new Properties();
        final ClassLoader classLoader = InjectorImplTest.class.getClassLoader();

        injector = new InjectorImpl(injectables, instances, properties, classLoader);*/
    }


    @Test
    void stream() {
        assertEquals(asList(Baz.class, Bar.class), injector.stream(Foo.class).map(Object::getClass).collect(toList()));
        assertEquals(singletonList(Bar.class), injector.stream(Bar.class).map(Object::getClass).collect(toList()));
        assertEquals(0, injector.stream(Integer.class).count());
    }

    @Test
    void getOrThrow() {
        assertThrows(IllegalArgumentException.class, () ->  injector.getOrThrow(Integer.class));
    }

    @Test
    void getAfterOrThrow() {
        final Foo expected = injector.getOrThrow(Bar.class);
        final Baz baz = injector.getOrThrow(Baz.class);
        final Foo actual = injector.getAfterOrThrow(Foo.class, baz);
        assertEquals(expected, actual);
        final Bar bar = injector.getOrThrow(Bar.class);
        assertThrows(IllegalArgumentException.class, () -> injector.getAfterOrThrow(Foo.class, bar));
    }

    @Test
    void get() {
       assertTrue(injector.get(Foo.class).isPresent());
       assertFalse(injector.get(Integer.class).isPresent());
    }

    @Test
    void getAfter() {
        final Foo expected = injector.getOrThrow(Bar.class);
        final Baz baz = injector.getOrThrow(Baz.class);
        final Foo actual = injector.getAfter(Foo.class, baz).orElseThrow(NoSuchElementException::new);
        assertEquals(expected, actual);
        final Bar bar = injector.getOrThrow(Bar.class);
        assertFalse(injector.getAfter(Foo.class, bar).isPresent());
    }

    @Test
    void injectables() {
        final Set<Class<?>> injectables = injector.injectables().collect(toSet());
        assertEquals(new HashSet<>(asList(Bar.class, Baz.class)), injectables);
    }

    @Test
    void inject() {
        final Baz newBaz = new Baz();
        assertNull(newBaz.bar);
        injector.inject(newBaz);
        assertNotNull(newBaz.bar);
    }

    @Test
    void injectIllegal() {
        final class Bez {
            @Inject private int a; // private field
        }
        final Bez bez = new Bez();
        assertThrows(IllegalArgumentException.class, () -> injector.inject(bez));
    }

    @Test
    void classLoader() {
        assertSame(Injector.class.getClassLoader(),injector.classLoader());
    }

    @Test
    void stop() {
        final Logger logger = LoggerManager.getLogger(InjectorImpl.class);
        final Level level = logger.getLevel();
        logger.setLevel(Level.DEBUG);
        try {
            final Baz baz = injector.getOrThrow(Baz.class);
            injector.stop();
            assertTrue(baz.stopped);
        } finally {
            logger.setLevel(level);
        }
    }

    @Test
    void stopFailed() throws InstantiationException {
        final Logger logger = LoggerManager.getLogger(InjectorImpl.class);
        final Level level = logger.getLevel();
        logger.setLevel(Level.DEBUG);
        final Injector newInjector = Injector.builder().withComponent(Unstoppable.class).build();
        try {
            assertThrows(InjectorException.class, newInjector::stop);
        } finally {
            logger.setLevel(level);
        }
    }

    @Test
    void stopSkipInvocation() throws InstantiationException {
        final Logger logger = LoggerManager.getLogger(InjectorImpl.class);
        final Level level = logger.getLevel();
        logger.setLevel(Level.DEBUG);
        final Injector newInjector = Injector.builder().withComponent(Stoppable.class).build();
        try {
            assertDoesNotThrow(newInjector::stop);
        } finally {
            logger.setLevel(level);
        }
    }

    @Test
    void newBuilder() {
        assertNotNull(injector.newBuilder());
        injector.newBuilder();
    }

    @Test
    @Disabled("https://github.com/speedment/speedment/issues/853")
    void newBuilderIsNew() {
        final InjectorBuilder builder1 = injector.newBuilder();
        final InjectorBuilder builder2 = injector.newBuilder();
        assertNotSame(builder1, builder2);
    }
}