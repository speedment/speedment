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

import com.speedment.common.injector.State;
import com.speedment.common.injector.dependency.DependencyGraph;
import com.speedment.common.injector.dependency.DependencyNode;
import com.speedment.common.injector.execution.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
final class ExecutionTwoParamBuilderImplTest {

    @Mock
    private DependencyGraph dependencyGraph;
    @Mock
    private DependencyNode dependencyNode1;
    @Mock
    private DependencyNode dependencyNode2;

    private ExecutionTwoParamBuilder<Integer, String, Long> builder;

    @BeforeEach
    void setup() {
        builder = new ExecutionTwoParamBuilderImpl<>(Integer.class, State.RESOLVED, String.class, State.RESOLVED, Long.class, State.RESOLVED);
    }

    @Test
    void withState() {
        final ExecutionThreeParamBuilder<Integer, String, Long, Float> e1 = builder.withState(State.CREATED, Float.class);
        assertNotNull(e1);
    }

    @Test
    void withExecute() {
        final AtomicInteger cnt = new AtomicInteger();
        final ExecutionBuilder<Integer> b = builder.withExecute((i, s, l) -> cnt.set(i + s.length() + l.intValue()));
        assertNotNull(b);
    }

    @Test
    void build() throws InvocationTargetException, IllegalAccessException {
        builder.withExecute((i, s, l) -> {});
        when(dependencyGraph.get(String.class)).thenReturn(dependencyNode1);
        when(dependencyGraph.get(Long.class)).thenReturn(dependencyNode2);
        final Execution<Integer> e = builder.build(dependencyGraph);
        assertNotNull(e);
        final Execution.ClassMapper classMapper = new Execution.ClassMapper() {
            @Override
            @SuppressWarnings("unchecked")
            public <T> T apply(Class<T> type) {
                switch (type.getSimpleName()) {
                    case "Sting":
                        return (T)"Arne";
                    case "Long":
                        return (T) (Long) 2L;

                }
                return null;
            }
        };
        assertTrue(e.invoke(1, classMapper));
    }
}