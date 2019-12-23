package com.speedment.common.injector.internal.dependency;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class InjectorDependencyNodeTest {

    private InjectorDependencyNode instance;

    @BeforeEach
    void setup() {
        instance = new InjectorDependencyNode();
    }

    @Test
    void getRepresentedType() {
        assertEquals(Injector.class, instance.getRepresentedType());
    }

    @Test
    void getDependencies() {
        assertTrue(instance.getDependencies().isEmpty());
    }

    @Test
    void getExecutions() {
        assertTrue(instance.getExecutions().isEmpty());
    }

    @Test
    void getCurrentState() {
        assertEquals(State.STARTED, instance.getCurrentState());
        instance.setState(State.STOPPED);
        assertEquals(State.STOPPED, instance.getCurrentState());
    }

    @Test
    void setState() {

    }

    @Test
    void canBe() {
        assertFalse(instance.canBe(State.CREATED));
        assertFalse(instance.canBe(State.INITIALIZED));
        assertFalse(instance.canBe(State.RESOLVED));
        assertFalse(instance.canBe(State.STARTED));
        assertTrue(instance.canBe(State.STOPPED));
    }

    @Test
    void is() {
        assertTrue(instance.is(State.CREATED));
        assertTrue(instance.is(State.INITIALIZED));
        assertTrue(instance.is(State.RESOLVED));
        assertTrue(instance.is(State.STARTED));
        assertFalse(instance.is(State.STOPPED));
        instance.setState(State.STOPPED);
        assertTrue(instance.is(State.STARTED));
        assertTrue(instance.is(State.STOPPED));
    }
}