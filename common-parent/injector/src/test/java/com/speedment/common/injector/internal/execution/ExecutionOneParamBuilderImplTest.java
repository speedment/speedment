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

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
final class ExecutionOneParamBuilderImplTest {

    @Mock
    private DependencyGraph dependencyGraph;
    @Mock
    private DependencyNode dependencyNode;

    private ExecutionOneParamBuilder<Integer, String> builder;

    @BeforeEach
    void setup() {
        builder = new ExecutionOneParamBuilderImpl<>(Integer.class, State.RESOLVED, String.class, State.RESOLVED);
    }

    @Test
    void withState() {
        final ExecutionTwoParamBuilder<Integer, String, Long> e1 = builder.withState(State.CREATED, Long.class);
        assertNotNull(e1);
    }

    @Test
    void withExecute() {
        final AtomicInteger cnt = new AtomicInteger();
        final ExecutionBuilder<Integer> b = builder.withExecute((i, s) -> cnt.set(i + s.length()));
        assertNotNull(b);
    }

    @Test
    void build() {
        builder.withExecute((i, s) -> {});
        when(dependencyGraph.get(String.class)).thenReturn(dependencyNode);
        final Execution<Integer> e = builder.build(dependencyGraph);
        assertNotNull(e);
    }
}