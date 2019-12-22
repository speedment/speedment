package com.speedment.common.injector.internal.execution;

import com.speedment.common.injector.State;
import com.speedment.common.injector.dependency.DependencyGraph;
import com.speedment.common.injector.dependency.DependencyNode;
import com.speedment.common.injector.execution.Execution;
import com.speedment.common.injector.execution.ExecutionBuilder;
import com.speedment.common.injector.execution.ExecutionThreeParamBuilder;
import com.speedment.common.injector.execution.ExecutionTwoParamBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
final class ExecutionThreeParamBuilderImplTest {

    @Mock
    private DependencyGraph dependencyGraph;
    @Mock
    private DependencyNode dependencyNode1;
    @Mock
    private DependencyNode dependencyNode2;
    @Mock
    private DependencyNode dependencyNode3;

    private ExecutionThreeParamBuilder<Integer, String, Long, Float> builder;

    @BeforeEach
    void setup() {
        builder = new ExecutionThreeParamBuilderImpl<>(Integer.class, State.RESOLVED, String.class, State.RESOLVED, Long.class, State.RESOLVED, Float.class, State.RESOLVED);
    }


    @Test
    void withExecute() {
        final AtomicInteger cnt = new AtomicInteger();
        final ExecutionBuilder<Integer> b = builder.withExecute((i, s, l, f) -> cnt.set(i + s.length() + l.intValue() + f.intValue()));
        assertNotNull(b);
    }

    @Test
    void build() {
        builder.withExecute((i, s, l, f) -> {});
        when(dependencyGraph.get(String.class)).thenReturn(dependencyNode1);
        when(dependencyGraph.get(Long.class)).thenReturn(dependencyNode2);
        when(dependencyGraph.get(Float.class)).thenReturn(dependencyNode3);
        final Execution<Integer> e = builder.build(dependencyGraph);
        assertNotNull(e);
    }
}