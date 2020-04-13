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
package com.speedment.common.injector.internal.dependency;

import com.speedment.common.injector.InjectorProxy;
import com.speedment.common.injector.MyInjectorProxy;
import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.Execute;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.common.injector.dependency.DependencyGraph;
import com.speedment.common.injector.dependency.DependencyNode;
import com.speedment.common.injector.exception.CyclicReferenceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

final class DependencyGraphImplTest {

    private InjectorProxy proxy;
    private DependencyGraphImpl instance;

    public static final class Foo {
        private boolean execute;
        private boolean executeBefore;

        @Execute
        public void execute() {
            execute = true;
        }

        @ExecuteBefore(State.STARTED)
        public void executeBefore() {
            executeBefore = true;
        }
    }

    public static final class Cyclic1 {
        @Execute
        void cyclic(@WithState(State.RESOLVED) Cyclic2 c) { }
    }

    public static final class Cyclic2 {
        @Execute
        void cyclic(@WithState(State.RESOLVED) Cyclic1 c) { }
    }


    @BeforeEach
    void setup() {
        proxy = new MyInjectorProxy();
        instance = new DependencyGraphImpl(c -> proxy);
    }

    @Test
    void get() {
        assertThrows(IllegalArgumentException.class, () -> instance.get(Foo.class));
        instance.getOrCreate(Foo.class);
        final DependencyNode dependencyNode = instance.get(Foo.class);
        assertEquals(Foo.class, dependencyNode.getRepresentedType());
    }

    @Test
    void getOrCreate() {
        final DependencyNode dependencyNode = instance.getOrCreate(Foo.class);
        assertEquals(Foo.class, dependencyNode.getRepresentedType());
    }

    @Test
    void getIfPresent() {
        assertFalse(instance.getIfPresent(Foo.class).isPresent());
        ;
        instance.getOrCreate(Foo.class);
        final Optional<DependencyNode> optionalNode = instance.getIfPresent(Foo.class);
        assertEquals(Foo.class, optionalNode.orElseThrow(NoSuchElementException::new).getRepresentedType());
    }

    @Test
    void inject() {
        instance.getOrCreate(Foo.class);
        final DependencyGraph dependencyGraph = instance.inject();
        final DependencyNode dependencyNode = dependencyGraph.get(Foo.class);
        assertEquals(2, dependencyNode.getExecutions().size());
    }

    @Test
    void nodes() {
        instance.nodes().forEach(System.out::println);
        // Injector is always present
        assertEquals(1, instance.nodes().count());
        instance.getOrCreate(Foo.class);
        assertEquals(2, instance.nodes().count());
    }

    @Test
    void methodName() throws NoSuchMethodException {
        final String name = "execute";
        final String methodName = DependencyGraphImpl.methodName(Foo.class.getDeclaredMethod(name));
        assertTrue(methodName.contains(Foo.class.getName()));
        assertTrue(methodName.contains(name));
    }


 /*   @Test
    void cyclic() {
        instance.getOrCreate(Cyclic1.class);
        instance.getOrCreate(Cyclic2.class);
        assertThrows(CyclicReferenceException.class, instance::inject);
    }*/
}