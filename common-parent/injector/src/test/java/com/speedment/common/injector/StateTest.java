package com.speedment.common.injector;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

final class StateTest {

    @Test
    void next() {
        assertEquals(State.INITIALIZED, State.CREATED.next());
        assertEquals(State.RESOLVED, State.INITIALIZED.next());
        assertEquals(State.STARTED, State.RESOLVED.next());
        assertEquals(State.STOPPED, State.STARTED.next());
        assertThrows(NoSuchElementException.class, State.STOPPED::next);
    }

    @Test
    void previous() {
        assertThrows(NoSuchElementException.class, State.CREATED::previous);
        assertEquals(State.CREATED, State.INITIALIZED.previous());
        assertEquals(State.INITIALIZED, State.RESOLVED.previous());
        assertEquals(State.RESOLVED, State.STARTED.previous());
        assertEquals(State.STARTED, State.STOPPED.previous());
    }

    @Test
    void isBefore() {
        assertFalse(State.CREATED.isBefore(State.CREATED));
        assertTrue(State.CREATED.isBefore(State.INITIALIZED));
        assertTrue(State.CREATED.isBefore(State.RESOLVED));
        assertTrue(State.CREATED.isBefore(State.STARTED));
        assertTrue(State.CREATED.isBefore(State.STOPPED));

        assertFalse(State.INITIALIZED.isBefore(State.CREATED));
        assertFalse(State.INITIALIZED.isBefore(State.INITIALIZED));
        assertTrue(State.INITIALIZED.isBefore(State.RESOLVED));
        assertTrue(State.INITIALIZED.isBefore(State.STARTED));
        assertTrue(State.INITIALIZED.isBefore(State.STOPPED));

        assertFalse(State.RESOLVED.isBefore(State.CREATED));
        assertFalse(State.RESOLVED.isBefore(State.INITIALIZED));
        assertFalse(State.RESOLVED.isBefore(State.RESOLVED));
        assertTrue(State.RESOLVED.isBefore(State.STARTED));
        assertTrue(State.RESOLVED.isBefore(State.STOPPED));

        assertFalse(State.STARTED.isBefore(State.CREATED));
        assertFalse(State.STARTED.isBefore(State.INITIALIZED));
        assertFalse(State.STARTED.isBefore(State.RESOLVED));
        assertFalse(State.STARTED.isBefore(State.STARTED));
        assertTrue(State.STARTED.isBefore(State.STOPPED));

        assertFalse(State.STOPPED.isBefore(State.CREATED));
        assertFalse(State.STOPPED.isBefore(State.INITIALIZED));
        assertFalse(State.STOPPED.isBefore(State.RESOLVED));
        assertFalse(State.STOPPED.isBefore(State.STARTED));
        assertFalse(State.STOPPED.isBefore(State.STOPPED));



    }


}