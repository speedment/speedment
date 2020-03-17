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
import com.speedment.common.injector.execution.Execution;
import com.speedment.common.injector.execution.ExecutionBuilder;
import com.speedment.common.injector.execution.ExecutionOneParamBuilder;
import com.speedment.common.injector.execution.ExecutionZeroParamBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
final class ExecutionZeroParamBuilderImplTest {

    @Mock
    private DependencyGraph dependencyGraph;
    private ExecutionZeroParamBuilder<Integer> builder;

    @BeforeEach
    void setup() {
        builder = new ExecutionZeroParamBuilderImpl<>(Integer.class, State.RESOLVED);
    }

    @Test
    void withState() {
        final ExecutionOneParamBuilder<Integer, String> e1 = builder.withState(State.CREATED, String.class);
        assertNotNull(e1);
    }

    @Test
    void withExecute() {
        final AtomicInteger cnt = new AtomicInteger();
        final ExecutionBuilder<Integer> b = builder.withExecute(cnt::set);
        assertNotNull(b);
    }

    @Test
    void build() {
        builder.withExecute(i -> {});
        final Execution<Integer> e = builder.build(dependencyGraph);
        assertNotNull(e);
    }
}