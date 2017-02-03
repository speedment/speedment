/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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

import static com.speedment.common.injector.State.INITIALIZED;
import static com.speedment.common.injector.State.RESOLVED;
import static com.speedment.common.injector.State.STARTED;
import static com.speedment.common.injector.State.STOPPED;
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
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public class InjectorTest {
    
    @Test
    public void testSimpleInjector() {
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
    public void testPotentialCyclicDependency() {
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
    public void testInheritance() {
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
    public void testKeyMultiples() {
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
        
        assertNotNull("Make sure Foo has an implementation", injector.get(Foo.class).orElse(null));
        assertNotNull("Make sure Bar had an implementation", injector.get(Bar.class).orElse(null));
        assertNotNull("Make sure Baz had an implementation", injector.get(Baz.class).orElse(null));
        
        assertEquals("Make sure the default implementation is Baz.", Baz.class, injector.get(Foo.class).get().getClass());
        assertEquals("Make sure the Bar can still be accessed",      Bar.class, injector.get(Bar.class).get().getClass());
    }
    
    @InjectKey(Foo.class)
    private interface Foo {}
    private final static class Bar implements Foo {}
    private final static class Baz implements Foo {}
    
    @Test
    public void testKeyWithoutOverwrite() {
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
        
        assertNotNull("Make sure Foo has an implementation", injector.get(Foo.class).orElse(null));
        assertNotNull("Make sure Bar had an implementation", injector.get(Bar.class).orElse(null));
        assertNotNull("Make sure Baz had an implementation", injector.get(Baz.class).orElse(null));
        assertNotNull("Make sure Baz had an implementation", injector.get(FooNoOverwrite.class).orElse(null));
        
        assertEquals("Make sure the default implementation is FooNoOverwrite.", FooNoOverwrite.class, injector.get(Foo.class).get().getClass());
        assertEquals("Make sure FooNoOverwrite can be accessed directly.", FooNoOverwrite.class, injector.get(FooNoOverwrite.class).get().getClass());
        assertEquals("Make sure the Bar can still be accessed", Bar.class, injector.get(Bar.class).get().getClass());
        assertEquals("Make sure the Baz can still be accessed", Baz.class, injector.get(Baz.class).get().getClass());
    }
    
    @InjectKey(value=Foo.class, overwrite=false)
    private final static class FooNoOverwrite implements Foo {}
    
    private final static class ClassWithConfig {
        
        private @Config(name="a", value="example") String defaultString;
        private @Config(name="b", value="-104726") int defaultInt;
        private @Config(name="c", value="0.43472") float defaultFloat;
        private @Config(name="d", value="false") boolean defaultBoolean;
        
        private @Config(name="e", value="example") String overridenString;
        private @Config(name="f", value="-104726") int overridenInt;
        private @Config(name="g", value="0.43472") float overridenFloat;
        private @Config(name="h", value="false") boolean overridenBoolean;
        
    }
    
    private ClassWithConfig configTest;
    
    @Before
    public void setupConfigTest() throws InstantiationException {
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
    public void testDefaultConfig() {
        assertEquals("Test default string param: ", "example", configTest.defaultString);
        assertEquals("Test default int param: ", -104726, configTest.defaultInt);
        assertEquals("Test default float param: ", 0.43472f, configTest.defaultFloat, 0.00000001f);
        assertEquals("Test default boolean param: ", false, configTest.defaultBoolean);
    }
    
    @Test
    public void testOverridenConfig() {
        assertEquals("Test overriden string param: ", "anotherExample", configTest.overridenString);
        assertEquals("Test overriden int param: ", 56629, configTest.overridenInt);
        assertEquals("Test overriden float param: ", -476.443f, configTest.overridenFloat, 0.00000001f);
        assertEquals("Test overriden boolean param: ", true, configTest.overridenBoolean);
    }
    
    private static abstract class AbstractComponent {
        
        protected final AtomicInteger counter;
        
        AbstractComponent() {
            counter = new AtomicInteger();
        }
        
        public int getCount() {
            return counter.get();
        }
        
        @ExecuteBefore(INITIALIZED)
        void parentInitialized() {
            counter.incrementAndGet();
        }
        
        @ExecuteBefore(RESOLVED)
        void parentResolved() {
            counter.incrementAndGet();
        }
        
        @ExecuteBefore(STARTED)
        void parentStarted() {
            counter.incrementAndGet();
        }
    }
    
    private static final class ImplementingComponent extends AbstractComponent {
        @ExecuteBefore(INITIALIZED)
        void childInitialized() {
            counter.incrementAndGet();
        }
        
        @ExecuteBefore(RESOLVED)
        void childResolved() {
            counter.incrementAndGet();
        }
        
        @ExecuteBefore(STARTED)
        void childStarted() {
            counter.incrementAndGet();
        }
    }
    
    @Test
    public void testParentChildExecutors() {
        try {
            final Injector injector = Injector.builder()
                .withComponent(ImplementingComponent.class)
                .build();
            
            final ImplementingComponent component =
                injector.getOrThrow(ImplementingComponent.class);
            
            assertEquals("Make sure all executors was executed: ", 6, component.getCount());
        } catch (final InstantiationException ex) {
            fail("InstantiationException!");
        }
    }
    
    private static final class ExecutableComponent {
        
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
    public void testInvokeExecutors() {
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
            
            assertTrue("Make sure 'a' was added before stop: ", c.getActionsInvoked().contains("a"));
            assertTrue("Make sure 'b' was added before stop: ", c.getActionsInvoked().contains("b"));
            assertTrue("Make sure 'c' was added before stop: ", c.getActionsInvoked().contains("c"));
            assertFalse("Make sure 'd' was NOT added before stop: ", c.getActionsInvoked().contains("d"));
            assertTrue("Make sure 'e' was added before stop: ", c.getActionsInvoked().contains("e"));
            assertTrue("Make sure 'f' was added before stop: ", c.getActionsInvoked().contains("f"));
            assertFalse("Make sure 'g' was NOT added before stop: ", c.getActionsInvoked().contains("g"));
            assertTrue("Make sure 'h' was added before stop: ", c.getActionsInvoked().contains("h"));
            assertTrue("Make sure 'q' was added before stop: ", c.getActionsInvoked().contains("q"));
            assertFalse("Make sure 'z' was NOT added before stop: ", c.getActionsInvoked().contains("z"));
            
            assertEquals("Make sure the total count is right before stop: ", 7, c.getCount());
            
            injector.stop();
            
            assertTrue("Make sure 'a' was added after stop: ", c.getActionsInvoked().contains("a"));
            assertTrue("Make sure 'b' was added after stop: ", c.getActionsInvoked().contains("b"));
            assertTrue("Make sure 'c' was added after stop: ", c.getActionsInvoked().contains("c"));
            assertTrue("Make sure 'd' was added after stop: ", c.getActionsInvoked().contains("d"));
            assertTrue("Make sure 'e' was added after stop: ", c.getActionsInvoked().contains("e"));
            assertTrue("Make sure 'f' was added after stop: ", c.getActionsInvoked().contains("f"));
            assertTrue("Make sure 'g' was added after stop: ", c.getActionsInvoked().contains("g"));
            assertTrue("Make sure 'h' was added after stop: ", c.getActionsInvoked().contains("h"));
            assertTrue("Make sure 'q' was added after stop: ", c.getActionsInvoked().contains("q"));
            assertTrue("Make sure 'z' was added after stop: ", c.getActionsInvoked().contains("z"));
            
            assertEquals("Make sure the total count is right after stop: ", 10, c.getCount());
            
        } catch (final InstantiationException ex) {
            fail("InstantiationException!");
        }
    }
}
