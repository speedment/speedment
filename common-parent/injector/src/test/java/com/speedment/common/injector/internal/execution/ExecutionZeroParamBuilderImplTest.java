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