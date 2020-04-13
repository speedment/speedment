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
package com.speedment.runtime.core.internal.component.sql.override;

import com.speedment.runtime.core.component.sql.override.reference.AllMatchTerminator;
import com.speedment.runtime.core.component.sql.override.reference.AnyMatchTerminator;
import com.speedment.runtime.core.component.sql.override.reference.CollectSupplierAccumulatorCombinerTerminator;
import com.speedment.runtime.core.component.sql.override.reference.CollectTerminator;
import com.speedment.runtime.core.component.sql.override.reference.CountTerminator;
import com.speedment.runtime.core.component.sql.override.reference.FindAnyTerminator;
import com.speedment.runtime.core.component.sql.override.reference.FindFirstTerminator;
import com.speedment.runtime.core.component.sql.override.reference.ForEachOrderedTerminator;
import com.speedment.runtime.core.component.sql.override.reference.ForEachTerminator;
import com.speedment.runtime.core.component.sql.override.reference.IteratorTerminator;
import com.speedment.runtime.core.component.sql.override.reference.MaxTerminator;
import com.speedment.runtime.core.component.sql.override.reference.MinTerminator;
import com.speedment.runtime.core.component.sql.override.reference.NoneMatchTerminator;
import com.speedment.runtime.core.component.sql.override.reference.ReduceIdentityCombinerTerminator;
import com.speedment.runtime.core.component.sql.override.reference.ReduceIdentityTerminator;
import com.speedment.runtime.core.component.sql.override.reference.ReduceTerminator;
import com.speedment.runtime.core.component.sql.override.reference.ReferenceTerminator;
import com.speedment.runtime.core.component.sql.override.reference.SpliteratorTerminator;
import com.speedment.runtime.core.component.sql.override.reference.ToArrayGeneratorTerminator;
import com.speedment.runtime.core.component.sql.override.reference.ToArrayTerminator;
import com.speedment.runtime.core.internal.component.sql.override.optimized.reference.OptimizedCountTerminator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

/**
 *
 * @author Per Minborg
 */
@Execution(ExecutionMode.CONCURRENT)
final class SqlStreamTerminatorComponentImplTest {

    private SqlStreamTerminatorComponentImpl instance;

    @BeforeEach
    void setUp() {
        instance = new SqlStreamTerminatorComponentImpl();
    }

    @Test
    void testGetters() {
        referenceTerminators()
            .filter(c -> !CountTerminator.class.equals(c)) // Count is optimized by default. Test separately
            .forEach(this::testGetter);
    }

    @Test
    void testGetCountTerminator() {
        assertEquals(
            OptimizedCountTerminator.create().getClass().getName(),
            instance.getCountTerminator().getClass().getName()
        );
    }

    @Test
    void testSetters() {
        referenceTerminators()
            .forEach(this::testSetter);
    }

    private void testGetter(Class<? extends ReferenceTerminator> clazz) {
        final String getterName = "get" + clazz.getSimpleName();
        try {
            final Method getter = SqlStreamTerminatorComponentImpl.class.getMethod(getterName);
            final Object instanceTerminator = getter.invoke(instance);
            final Method defaultTerminatorMethod = clazz.getMethod("defaultTerminator");
            final Object defaultTerminator = defaultTerminatorMethod.invoke(null);

            //System.out.println("Testing getter: " + clazz.getSimpleName() + ":" + instanceTerminator.getClass().getSimpleName());

            assertEquals(defaultTerminator, instanceTerminator, clazz.getSimpleName());

        } catch (NoSuchMethodException
            | SecurityException
            | IllegalAccessException
            | IllegalArgumentException
            | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private <T extends ReferenceTerminator> void testSetter(Class<T> clazz) {
        final String setterName = "set" + clazz.getSimpleName();
        final String getterName = "get" + clazz.getSimpleName();
        try {
            final T mockTerminator = mock(clazz);

            final Method setter = SqlStreamTerminatorComponentImpl.class.getMethod(setterName, clazz);
            setter.invoke(instance, mockTerminator);

            final Method getter = SqlStreamTerminatorComponentImpl.class.getMethod(getterName);
            final Object instanceTerminator = getter.invoke(instance);

            //System.out.println("Testing setter: " + clazz.getSimpleName() + ":" + instanceTerminator.getClass().getSimpleName());

            assertEquals(mockTerminator, instanceTerminator, clazz.getSimpleName());

        } catch (NoSuchMethodException
            | SecurityException
            | IllegalAccessException
            | IllegalArgumentException
            | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    Stream<Class<? extends ReferenceTerminator>> referenceTerminators() {
        return Stream.of(
            AllMatchTerminator.class,
            AnyMatchTerminator.class,
            CollectSupplierAccumulatorCombinerTerminator.class,
            CollectTerminator.class,
            CountTerminator.class,
            FindAnyTerminator.class,
            FindFirstTerminator.class,
            ForEachOrderedTerminator.class,
            ForEachTerminator.class,
            IteratorTerminator.class,
            MaxTerminator.class,
            MinTerminator.class,
            NoneMatchTerminator.class,
            ReduceTerminator.class,
            ReduceIdentityCombinerTerminator.class,
            ReduceIdentityTerminator.class,
            SpliteratorTerminator.class,
            ToArrayGeneratorTerminator.class,
            ToArrayTerminator.class
        );
    }

}
