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