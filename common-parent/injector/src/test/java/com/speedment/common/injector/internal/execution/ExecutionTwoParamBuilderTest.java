package com.speedment.common.injector.internal.execution;

import com.speedment.common.injector.State;
import com.speedment.common.injector.execution.ExecutionOneParamBuilder;
import com.speedment.common.injector.execution.ExecutionTwoParamBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
final class ExecutionTwoParamBuilderTest {

    @Mock
    private ExecutionTwoParamBuilder<String, Integer, Integer> builder;
    private AtomicInteger cnt;

    @BeforeEach
    void setup() {
        cnt = new AtomicInteger();
    }

    @Test
    void withStateInitialized() {
        prepareMock(State.INITIALIZED);
        when(builder.withStateInitialized(Long.class)).thenCallRealMethod();
        builder.withStateInitialized(Long.class);
        assertEquals(1, cnt.get());
    }

    @Test
    void withStateResolved() {
        prepareMock(State.RESOLVED);
        when(builder.withStateResolved(Long.class)).thenCallRealMethod();
        builder.withStateResolved(Long.class);
        assertEquals(1, cnt.get());
    }

    @Test
    void withStateStarted() {
        prepareMock(State.STARTED);
        when(builder.withStateStarted(Long.class)).thenCallRealMethod();
        builder.withStateStarted(Long.class);
        assertEquals(1, cnt.get());
    }

    @Test
    void withStateStopped() {
        prepareMock(State.STOPPED);
        when(builder.withStateStopped(Long.class)).thenCallRealMethod();
        builder.withStateStopped(Long.class);
        assertEquals(1, cnt.get());
    }

    private void prepareMock(State state) {
        when(builder.withState(state, Long.class)).thenAnswer(invocationOnMock -> {
            cnt.incrementAndGet();
            return null;
        });
    }

}