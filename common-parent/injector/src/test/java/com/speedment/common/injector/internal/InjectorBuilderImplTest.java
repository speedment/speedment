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

import com.speedment.common.injector.*;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.common.injector.dependency.DependencyGraph;
import com.speedment.common.injector.dependency.DependencyNode;
import com.speedment.common.injector.exception.ConstructorResolutionException;
import com.speedment.common.injector.exception.InjectorException;
import com.speedment.common.injector.exception.MisusedAnnotationException;
import com.speedment.common.injector.execution.Execution;
import com.speedment.common.injector.execution.ExecutionBuilder;
import com.speedment.common.logger.Level;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

final class InjectorBuilderImplTest {

    private static final Logger LOGGER = InjectorBuilder.logger();

    public static final class Foo {}
    public static final class Bar {
        private int val;
    }
    public static final class Baz {
        private Foo foo;

        public Baz(Foo foo) {
            this.foo = foo;
        }
    }
    public static final class Bez {
        @Inject public Foo foo;
    }

    public static final class Buz {
        @ExecuteBefore(State.INITIALIZED) void a(@WithState(State.STOPPED) Foo foo){}
    }

    public static final class PrivateFieldHolder {
        @Inject private int a;
    }

    public static final class BarConsumerThrows {
        @ExecuteBefore(State.INITIALIZED) void accept(Bar bar){};
    }

    public static final class BarConsumerIgnored {
        @ExecuteBefore(value = State.INITIALIZED, missingArgument = MissingArgumentStrategy.SKIP_INVOCATION) void accept(Bar bar){};
    }

    public static final class TestBundle implements InjectBundle {
        @Override
        public Stream<Class<?>> injectables() {
            return Stream.of(Foo.class, Bar.class);
        }
    }

    private static final class PrivateClass{}
    private static final class ClassWithPrivateConstructor{ private ClassWithPrivateConstructor() {} }

    private InjectorBuilder instance;

    @BeforeEach
    void setup() {
        instance = new InjectorBuilderImpl();
    }

    @Test
    void construct() throws InstantiationException {
        final InjectorBuilder builder = new InjectorBuilderImpl();
        final Injector injector = builder.build();
        assertEquals(0, injector.injectables().count());
    }

    @Test
    void construct2() throws InstantiationException {
        final InjectorBuilder builder = new InjectorBuilderImpl(InjectorBuilderImpl.class.getClassLoader());
        final Injector injector = builder.build();
        assertEquals(0, injector.injectables().count());
        assertEquals(InjectorBuilderImpl.class.getClassLoader(), injector.classLoader());
    }

    @Test
    void construct3() throws InstantiationException {
        final InjectorBuilder builder = new InjectorBuilderImpl(singleton(Foo.class));
        final Injector injector = builder.build();
        assertEquals(1, injector.injectables().count());
    }

    @Test
    void withComponent() throws InstantiationException {
        final Injector injector = instance.withComponent(Foo.class).build();
        assertEquals(singletonList(Foo.class), injector.injectables().collect(toList()));
    }

    @Test
    void testWithComponent() throws InstantiationException {
        final Injector injector = instance.withComponent(Foo.class, Foo::new).build();
        assertEquals(singletonList(Foo.class), injector.injectables().collect(toList()));
    }

    @Test
    void withBundle() throws InstantiationException {
        final Injector injector = instance.withBundle(TestBundle.class).build();
        assertEquals(asList(Foo.class, Bar.class), injector.injectables().collect(toList()));
    }

    @Test
    void withInjectorProxy() throws InstantiationException {
        final class MyTestInjectorProxy implements InjectorProxy {
            @Override public boolean isApplicable(Class<?> clazz) { return true; }
            @Override public void set(Field field, Object instance, Object value) { throw new UnsupportedOperationException(); }
            @Override public Object invoke(Method method, Object obj, Object... args) { throw new UnsupportedOperationException(); }

            @Override
            public <T> T newInstance(Constructor<T> constructor, Object... args) throws IllegalAccessException, InvocationTargetException, InstantiationException {
                final T result = constructor.newInstance(args);
                if (result instanceof Bar) {
                    ((Bar)result).val = 1;
                }
                return result;
            }
        }
        final Injector injector = instance.withInjectorProxy(new MyTestInjectorProxy()).withComponent(Bar.class).build();
        assertEquals(1, injector.getOrThrow(Bar.class).val);
    }


    @Test
    void withInjectorProxyConstructor() throws InstantiationException {
        final class MyTestInjectorProxy implements InjectorProxy {
            @Override public boolean isApplicable(Class<?> clazz) { return true; }
            @Override public void set(Field field, Object instance, Object value) { throw new UnsupportedOperationException(); }
            @Override public Object invoke(Method method, Object obj, Object... args) { throw new UnsupportedOperationException(); }

            @Override
            public <T> T newInstance(Constructor<T> constructor, Object... args) throws IllegalAccessException, InvocationTargetException, InstantiationException {
                final T result = constructor.newInstance(args);
                if (result instanceof Bar) {
                    ((Bar)result).val = 1;
                }
                return result;
            }
        }
        final Injector injector = instance.withComponent(MyTestInjectorProxy.class, MyTestInjectorProxy::new).withComponent(Bar.class).build();
        assertEquals(1, injector.getOrThrow(Bar.class).val);
    }


    @Test
    void withConfigFileLocation() {
        assertDoesNotThrow(() -> instance.withConfigFileLocation(Paths.get(".")));
    }

    @Test
    void withLogging() {
        withLogging(() -> instance.withBundle(TestBundle.class).build());
    }

    @Test
    void withCannotInstantiate() {
        assertThrows(ConstructorResolutionException.class, () -> instance.withComponent(Baz.class).build());
    }

    @Test
    void withMissingAutoInject() {
        assertThrows(IllegalArgumentException.class, () -> instance.withComponent(Bez.class).build());
    }

    @Test
    void withIllegalAutoInject() {
        assertThrows(IllegalArgumentException.class, () -> instance.withComponent(PrivateFieldHolder.class).build());
    }

    @Test
    void withMissingComponent() {
        assertThrows(IllegalArgumentException.class, () -> instance.withComponent(Bez.class).build());
    }

    @Test
    void withMisusedAnnotation() {
        assertThrows(MisusedAnnotationException.class, () -> instance.withComponent(Foo.class).withComponent(Buz.class).build());
    }

    @Test
    void injectInjectorProxy() throws InstantiationException {
        final Injector injector = instance.withComponent(MyInjectorProxy.class).build();
        assertEquals(MyInjectorProxy.class, injector.getOrThrow(MyInjectorProxy.class).getClass());
    }

    @Test
    void withUnableToCall() {
        final Level level = LOGGER.getLevel();
        LOGGER.setLevel(Level.DEBUG);
        try {
            assertThrows(IllegalArgumentException.class, () -> instance.withComponent(BarConsumerThrows.class).build());
        } finally {
            LOGGER.setLevel(level);
        }
    }

    @Test
    void withUnableButIgnore() {
        final Level level = LOGGER.getLevel();
        LOGGER.setLevel(Level.DEBUG);
        try {
            assertDoesNotThrow(() -> instance.withComponent(BarConsumerIgnored.class).build());
        } finally {
            LOGGER.setLevel(level);
        }
    }

    @Test
    void withClassThatIsPrivate() throws InstantiationException {
        assertThrows(InjectorException.class, () ->instance.withComponent(PrivateClass.class).build());
    }

    @Test
    void withClassWithPrivateConstructor() throws InstantiationException {
        assertThrows(InjectorException.class, () ->instance.withComponent(ClassWithPrivateConstructor.class).build());
    }

    @Test
    void throwInjectorException() {
        try {
            final RuntimeException runtimeException = new RuntimeException("AnyReason");
            final InjectorProxy injectorProxy = new MyInjectorProxy();
            final DependencyGraph dependencyGraph = DependencyGraph.create(Stream.of(Bar.class, BarConsumerThrows.class), c -> injectorProxy);
            dependencyGraph.getOrCreate(Bar.class);
            final DependencyNode n = dependencyGraph.getOrCreate(BarConsumerThrows.class);
            final Execution ex = n.getExecutions().iterator().next();
            InjectorBuilderImpl.throwInjectorException(dependencyGraph, ex, runtimeException);
        } catch (InjectorException e) {
            final String msg = e.getMessage();
            assertTrue(msg.contains("AnyReason"));
        }
    }

    private interface ThrowingRunnable  {
        void run() throws InstantiationException;
    }

    void withLogging(ThrowingRunnable runnable) {
        final Level level = LOGGER.getLevel();
        LOGGER.setLevel(Level.DEBUG);
        try {
            runnable.run();
        } catch (InstantiationException ie) {
            fail(ie);
        } finally {
            LOGGER.setLevel(level);
        }
    }

}