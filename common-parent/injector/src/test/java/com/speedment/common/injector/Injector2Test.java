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

import com.speedment.common.injector.annotation.Config;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.common.injector.exception.NoDefaultConstructorException;
import com.speedment.common.injector.test_a.StringIdentityMapper;
import com.speedment.common.injector.test_a.TypeMapperComponent;
import com.speedment.common.injector.test_b.A;
import com.speedment.common.injector.test_b.B;
import com.speedment.common.injector.test_b.C;
import com.speedment.common.injector.test_c.ChildType;
import com.speedment.common.injector.test_c.ParentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static com.speedment.common.injector.MissingArgumentStrategy.SKIP_INVOCATION;
import static com.speedment.common.injector.State.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
final class Injector2Test {

    @Test
    void testRepeatedInjection() {
        try {
            final Injector injector = Injector.builder()
                .withComponent(Bar.class)
                .withComponent(Baz.class)
                .withComponent(Bar.class) // This is the last one injected and should "win"
                .build();

            final Foo foo = injector.getOrThrow(Foo.class);

            assertEquals(Bar.class, foo.getClass());

        } catch (InstantiationException ie) {
            fail(ie);
        }
    }

    @Test
    void testSimpleInjector() {
        final Injector injector;
        
        try {
            injector = Injector.builder()
                .withComponent(StringIdentityMapper.class)
                .withComponent(TypeMapperComponent.class)
                .build();
        } catch (final InstantiationException ex) {
            throw new RuntimeException(
                "Failed to instantiate class.", ex
            );
        }
        
        final StringIdentityMapper mapper = injector.getOrThrow(StringIdentityMapper.class);
        final TypeMapperComponent mappers = injector.getOrThrow(TypeMapperComponent.class);
        
        assertNotNull(mapper);
        assertNotNull(mappers);
        
        assertEquals(mapper, mappers.toDatabaseTypeMappers().get(String.class));
        assertEquals(mapper, mappers.toJavaTypeMappers().get(String.class));
    }

    @Test
    void testInjectorWithSupplier() throws InstantiationException {
        Injector injector = Injector.builder()
                .withComponent(String.class, () -> "foo")
                .withComponent(Integer.class, () -> 42)
                .build();

        assertEquals("foo", injector.getOrThrow(String.class));
        assertEquals(42, injector.getOrThrow(Integer.class).intValue());
    }


    @Test
    void testPotentialCyclicDependency() {
        final Injector injector;
        
        try {
            injector = Injector.builder()
                .withComponent(A.class)
                .withComponent(B.class)
                .withComponent(C.class)
                .build();
        } catch (final InstantiationException ex) {
            throw new RuntimeException(
                "Failed to instantiate class.", ex
            );
        }
        
        assertNotNull(injector.getOrThrow(A.class).b);
        assertNotNull(injector.getOrThrow(A.class).c);
        assertNotNull(injector.getOrThrow(B.class).a);
        assertNotNull(injector.getOrThrow(B.class).c);
        assertNotNull(injector.getOrThrow(C.class).a);
        assertNotNull(injector.getOrThrow(C.class).b);
    }
    
    @Test
    void testInheritance() {
        final Injector injector;
        
        try {
            injector = Injector.builder()
                .withComponent(A.class)
                .withComponent(B.class)
                .withComponent(C.class)
                .withComponent(ChildType.class)
                .build();
        } catch (final NoDefaultConstructorException 
                     | InstantiationException ex) {
            
            throw new RuntimeException(
                "Failed to instantiate class.", ex
            );
        }
        
        assertNotNull(injector.getOrThrow(ParentType.class).a);
        assertNotNull(injector.getOrThrow(ChildType.class).b);
    }
    
    @Test
    void testKeyMultiples() {
        final Injector injector;
        
        try {
            injector = Injector.builder()
                .withComponent(Bar.class)
                .withComponent(Baz.class)
                .build();
        } catch (final NoDefaultConstructorException 
                     | InstantiationException ex) {
            
            throw new RuntimeException(
                "Failed to instantiate class.", ex
            );
        }
        
        assertNotNull(injector.get(Foo.class).orElse(null), "Make sure Foo has an implementation");
        assertNotNull(injector.get(Bar.class).orElse(null), "Make sure Bar had an implementation");
        assertNotNull(injector.get(Baz.class).orElse(null), "Make sure Baz had an implementation");
        
        assertEquals(Baz.class, injector.get(Foo.class).get().getClass(), "Make sure the default implementation is Baz.");
        assertEquals(Bar.class, injector.get(Bar.class).get().getClass(), "Make sure the Bar can still be accessed");
    }
    
    @InjectKey(Foo.class)
    public interface Foo {}
    public final static class Bar implements Foo {}
    public final static class Baz implements Foo {}
    
    @Test
    void testKeyWithoutOverwrite() {
        final Injector injector;
        
        try {
            injector = Injector.builder()
                .withComponent(Bar.class)
                .withComponent(Baz.class)
                .withComponent(FooNoOverwrite.class)
                .build();
        } catch (final NoDefaultConstructorException 
                     | InstantiationException ex) {
            
            throw new RuntimeException(
                "Failed to instantiate class.", ex
            );
        }
        
        assertNotNull(injector.get(Foo.class).orElse(null), "Make sure Foo has an implementation");
        assertNotNull(injector.get(Bar.class).orElse(null), "Make sure Bar had an implementation");
        assertNotNull(injector.get(Baz.class).orElse(null), "Make sure Baz had an implementation");
        assertNotNull(injector.get(FooNoOverwrite.class).orElse(null), "Make sure Baz had an implementation");
        
        assertEquals(FooNoOverwrite.class, injector.get(Foo.class).get().getClass(), "Make sure the default implementation is FooNoOverwrite.");
        assertEquals(FooNoOverwrite.class, injector.get(FooNoOverwrite.class).get().getClass(), "Make sure FooNoOverwrite can be accessed directly.");
        assertEquals(Bar.class, injector.get(Bar.class).get().getClass(), "Make sure the Bar can still be accessed");
        assertEquals(Baz.class, injector.get(Baz.class).get().getClass(), "Make sure the Baz can still be accessed");
    }
    
    @InjectKey(value=Foo.class, overwrite=false)
    public final static class FooNoOverwrite implements Foo {}
    
    public final static class ClassWithConfig {
        
        public @Config(name="a", value="example") String defaultString;
        public @Config(name="b", value="-104726") int defaultInt;
        public @Config(name="c", value="0.43472") float defaultFloat;
        public @Config(name="d", value="false") boolean defaultBoolean;

        public @Config(name="e", value="example") String overridenString;
        public @Config(name="f", value="-104726") int overridenInt;
        public @Config(name="g", value="0.43472") float overridenFloat;
        public @Config(name="h", value="false") boolean overridenBoolean;
        
    }
    
    private ClassWithConfig configTest;
    
    @BeforeEach
    void setupConfigTest() throws InstantiationException {
        configTest = new ClassWithConfig();
        
        final Injector injector = Injector.builder()
            .withParam("e", "anotherExample")
            .withParam("f", "56629")
            .withParam("g", "-476.443")
            .withParam("h", "true")
            .build();
        
        injector.inject(configTest);
    }
    
    @Test
    void testDefaultConfig() {
        assertEquals("example", configTest.defaultString, "Test default string param: ");
        assertEquals(-104726, configTest.defaultInt, "Test default int param: ");
        assertEquals(0.43472f, configTest.defaultFloat, 0.00000001f, "Test default float param: ");
        assertEquals(false, configTest.defaultBoolean, "Test default boolean param: ");
    }
    
    @Test
    void testOverridenConfig() {
        assertEquals("anotherExample", configTest.overridenString, "Test overriden string param: ");
        assertEquals(56629, configTest.overridenInt, "Test overriden int param: ");
        assertEquals(-476.443f, configTest.overridenFloat, 0.00000001f, "Test overriden float param: ");
        assertEquals(true, configTest.overridenBoolean, "Test overriden boolean param: ");
    }
    
    public static abstract class AbstractComponent {
        
        protected final AtomicInteger counter;
        
        AbstractComponent() {
            counter = new AtomicInteger();
        }
        
        public int getCount() {
            return counter.get();
        }
        
        @ExecuteBefore(INITIALIZED)
        public void parentInitialized() {
            counter.incrementAndGet();
        }
        
        @ExecuteBefore(RESOLVED)
        public void parentResolved() {
            counter.incrementAndGet();
        }
        
        @ExecuteBefore(STARTED)
        public void parentStarted() {
            counter.incrementAndGet();
        }
    }
    
    public static final class ImplementingComponent extends AbstractComponent {
        @ExecuteBefore(INITIALIZED)
        public void childInitialized() {
            counter.incrementAndGet();
        }
        
        @ExecuteBefore(RESOLVED)
        public void childResolved() {
            counter.incrementAndGet();
        }
        
        @ExecuteBefore(STARTED)
        public void childStarted() {
            counter.incrementAndGet();
        }
    }
    
    @Test
    void testParentChildExecutors() {
        try {
            final Injector injector = Injector.builder()
                .withComponent(ImplementingComponent.class)
                .build();
            
            final ImplementingComponent component =
                injector.getOrThrow(ImplementingComponent.class);
            
            assertEquals( 6, component.getCount(), "Make sure all executors was executed: ");
        } catch (final InstantiationException ex) {
            fail("InstantiationException!");
        }
    }
    
    public static final class ExecutableComponent {
        
        private final Set<String> actionsInvoked = new HashSet<>();
        private int count;
        
        public void set(String action) {
            actionsInvoked.add(action);
            count++;
        }
        
        @ExecuteBefore(INITIALIZED)
        public void onInit() {
            actionsInvoked.add("q");
            count++;
        }
        
        @ExecuteBefore(STOPPED)
        public void onStop() {
            actionsInvoked.add("z");
            count++;
        }
        
        public Set<String> getActionsInvoked() {
            return actionsInvoked;
        }
        
        public int getCount() {
            return count;
        }
    }
    
    @Test
    void testInvokeExecutors() {
        try {
            final Injector injector = Injector.builder()
                .withComponent(ExecutableComponent.class)
                .beforeInitialized(ExecutableComponent.class, ec -> ec.set("a"))
                .beforeInitialized(ExecutableComponent.class, ec -> ec.set("b"))
                .beforeResolved(ExecutableComponent.class, ec -> ec.set("c"))
                .beforeStopped(ExecutableComponent.class, ec -> ec.set("d"))
                .beforeStarted(ExecutableComponent.class, ec -> ec.set("e"))
                .beforeStarted(ExecutableComponent.class, ec -> ec.set("f"))
                .beforeStopped(ExecutableComponent.class, ec -> ec.set("g"))
                .beforeInitialized(ExecutableComponent.class, ec -> ec.set("h"))
                .build();
            
            final ExecutableComponent c =
                injector.getOrThrow(ExecutableComponent.class);
            
            assertTrue(c.getActionsInvoked().contains("a"), "Make sure 'a' was added before stop: ");
            assertTrue(c.getActionsInvoked().contains("b"), "Make sure 'b' was added before stop: ");
            assertTrue(c.getActionsInvoked().contains("c"), "Make sure 'c' was added before stop: ");
            assertFalse(c.getActionsInvoked().contains("d"), "Make sure 'd' was NOT added before stop: ");
            assertTrue(c.getActionsInvoked().contains("e"), "Make sure 'e' was added before stop: ");
            assertTrue(c.getActionsInvoked().contains("f"), "Make sure 'f' was added before stop: ");
            assertFalse(c.getActionsInvoked().contains("g"), "Make sure 'g' was NOT added before stop: ");
            assertTrue(c.getActionsInvoked().contains("h"), "Make sure 'h' was added before stop: ");
            assertTrue(c.getActionsInvoked().contains("q"), "Make sure 'q' was added before stop: ");
            assertFalse(c.getActionsInvoked().contains("z"), "Make sure 'z' was NOT added before stop: ");
            
            assertEquals(7, c.getCount(), "Make sure the total count is right before stop: ");
            
            injector.stop();
            
            assertTrue(c.getActionsInvoked().contains("a"), "Make sure 'a' was added after stop: ");
            assertTrue(c.getActionsInvoked().contains("b"), "Make sure 'b' was added after stop: ");
            assertTrue(c.getActionsInvoked().contains("c"), "Make sure 'c' was added after stop: ");
            assertTrue(c.getActionsInvoked().contains("d"), "Make sure 'd' was added after stop: ");
            assertTrue(c.getActionsInvoked().contains("e"), "Make sure 'e' was added after stop: ");
            assertTrue(c.getActionsInvoked().contains("f"), "Make sure 'f' was added after stop: ");
            assertTrue(c.getActionsInvoked().contains("g"), "Make sure 'g' was added after stop: ");
            assertTrue(c.getActionsInvoked().contains("h"), "Make sure 'h' was added after stop: ");
            assertTrue(c.getActionsInvoked().contains("q"), "Make sure 'q' was added after stop: ");
            assertTrue(c.getActionsInvoked().contains("z"), "Make sure 'z' was added after stop: ");
            
            assertEquals(10, c.getCount(), "Make sure the total count is right after stop: ");
            
        } catch (final InstantiationException ex) {
            fail("InstantiationException!");
        }
    }

    @Test
    void testOptional() throws InstantiationException {
        final Injector injector = Injector.builder()
            .withComponent(ComponentWithOptionals.class)
            .build();

        final ComponentWithOptionals c = injector.getOrThrow(ComponentWithOptionals.class);
        assertFalse(c.initCalled);
        assertFalse(c.init2Called);

    }

    @Test
    void testOptionalPresent() throws InstantiationException {
        final Injector injector = Injector.builder()
            .withComponent(ComponentWithOptionals.class)
            .withComponent(Bar.class)
            .withComponent(Baz.class)
            .build();

        final ComponentWithOptionals c = injector.getOrThrow(ComponentWithOptionals.class);
        assertTrue(c.initCalled);
        assertTrue(c.init2Called);
    }

    public static final class ComponentWithOptionals {

        private boolean initCalled;
        private boolean init2Called;

        @ExecuteBefore(value = INITIALIZED, missingArgument = SKIP_INVOCATION)
        public void init(Bar bar) {
            initCalled = true;
        }

        @ExecuteBefore(value = INITIALIZED, missingArgument = SKIP_INVOCATION)
        public void init2(Bar bar, Baz baz) {
            init2Called = true;
        }
    }

    @Test
    void testConfigDefault() throws InstantiationException {
        final Injector injector = Injector.builder()
            .withComponent(ComponentWithConfigInConstructor.class)
            .build();

        assertEquals(1000L, injector.getOrThrow(ComponentWithConfigInConstructor.class).value());

    }

    @Test
    void testConfigCustom() throws InstantiationException {
        final long val = 999;
        final Injector injector = Injector.builder()
            .withComponent(ComponentWithConfigInConstructor.class)
            .withParam("value", Long.toString(val))
            .build();

        assertEquals(val, injector.getOrThrow(ComponentWithConfigInConstructor.class).value());

    }


    public static final class ComponentWithConfigInConstructor {

        private final long value;

        public ComponentWithConfigInConstructor(@Config(name = "value", value = "1000") long value) {
            this.value = value;
        }

        public long value() {
            return value;
        }
    }


}
