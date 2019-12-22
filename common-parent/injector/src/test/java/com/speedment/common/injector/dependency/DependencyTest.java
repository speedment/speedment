package com.speedment.common.injector.dependency;

import com.speedment.common.injector.State;
import com.speedment.common.injector.internal.dependency.DependencyImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
final class DependencyTest {

    @Mock
    private DependencyNode dependencyNode;

    @Test
    void isSatisfied() {
        final Dependency dependency = new DependencyImpl(dependencyNode, State.STARTED);
        when(dependencyNode.is(State.STARTED)).thenReturn(false);
        assertFalse(dependency.isSatisfied());
    }

}