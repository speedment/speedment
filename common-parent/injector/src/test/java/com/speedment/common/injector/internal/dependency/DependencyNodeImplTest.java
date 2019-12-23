package com.speedment.common.injector.internal.dependency;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

final class DependencyNodeImplTest {

    @Test
    void getDependencies() {
        DependencyNodeImpl instance = new DependencyNodeImpl(Integer.class);
        assertTrue(instance.getDependencies().isEmpty());
    }
}