/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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

import com.speedment.common.injector.annotation.InjectorKey;
import com.speedment.common.injector.exception.NoDefaultConstructorException;
import com.speedment.common.injector.test_a.StringIdentityMapper;
import com.speedment.common.injector.test_a.TypeMapperComponent;
import com.speedment.common.injector.test_b.A;
import com.speedment.common.injector.test_b.B;
import com.speedment.common.injector.test_b.C;
import com.speedment.common.injector.test_c.ChildType;
import com.speedment.common.injector.test_c.ParentType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
                .canInject(StringIdentityMapper.class)
                .canInject(TypeMapperComponent.class)
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
                .canInject(A.class)
                .canInject(B.class)
                .canInject(C.class)
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
                .canInject(A.class)
                .canInject(B.class)
                .canInject(C.class)
                .canInject(ChildType.class)
                .build();
        } catch (final NoDefaultConstructorException | InstantiationException ex) {
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
                .canInject(Bar.class)
                .canInject(Baz.class)
                .build();
        } catch (final NoDefaultConstructorException | InstantiationException ex) {
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
    
    @InjectorKey(Foo.class)
    private interface Foo {}
    private final static class Bar implements Foo {}
    private final static class Baz implements Foo {}
}
