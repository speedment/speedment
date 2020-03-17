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
package com.speedment.common.function;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class OptionalBooleanTest {

    private static final OptionalBoolean OBT = OptionalBoolean.of(true);
    private static final OptionalBoolean OBF = OptionalBoolean.of(false);
    private static final OptionalBoolean OBE = OptionalBoolean.ofNullable(null);

    @Test
    void empty() {
        OptionalBoolean ob = OptionalBoolean.empty();
        assertSame(OptionalBoolean.EMPTY, ob);
    }

    @Test
    void of() {
        OptionalBoolean obT = OptionalBoolean.of(true);
        assertTrue(obT.getAsBoolean());
        OptionalBoolean obF = OptionalBoolean.of(false);
        assertFalse(obF.getAsBoolean());
    }

    @Test
    void ofNullable() {
        OptionalBoolean obT = OptionalBoolean.ofNullable(Boolean.TRUE);
        assertTrue(obT.getAsBoolean());
        OptionalBoolean obF = OptionalBoolean.ofNullable(Boolean.FALSE);
        assertFalse(obF.getAsBoolean());
        OptionalBoolean obN = OptionalBoolean.ofNullable(null);
        assertFalse(obN.isPresent());
        assertThrows(NoSuchElementException.class, obN::getAsBoolean);
    }

    @Test
    void filter() {
        assertTrue(OBT.filter(b -> true).getAsBoolean());
        assertFalse(OBT.filter(b -> false).isPresent());

        assertFalse(OBF.filter(b -> true).getAsBoolean());
        assertFalse(OBF.filter(b -> false).isPresent());

        assertFalse(OBE.filter(b -> true).isPresent());
        assertFalse(OBE.filter(b -> false).isPresent());
    }

    @Test
    void getAsBoolean() {
        assertTrue(OBT.filter(b -> true).getAsBoolean());
        assertFalse(OBT.filter(b -> false).isPresent());

        assertFalse(OBF.filter(b -> true).getAsBoolean());
        assertFalse(OBF.filter(b -> false).isPresent());

        assertFalse(OBE.filter(b -> true).isPresent());
        assertFalse(OBE.filter(b -> false).isPresent());
    }

    @Test
    void orElse() {
        assertTrue(OBT.orElse(true));
        assertTrue(OBT.orElse(false));

        assertFalse(OBF.orElse(true));
        assertFalse(OBF.orElse(false));

        assertTrue(OBE.orElse(true));
        assertFalse(OBE.orElse(false));
    }

    @Test
    void orElseThrow() {
        assertDoesNotThrow(() ->
            OBT.orElseThrow(RuntimeException::new)
        );
        assertDoesNotThrow(() ->
            OBF.orElseThrow(RuntimeException::new)
        );
        assertThrows(RuntimeException.class, () ->
           OBE.orElseThrow(RuntimeException::new)
        );
    }

    @Test
    void isPresent() {
        assertTrue(OBT.isPresent());
        assertTrue(OBF.isPresent());
        assertFalse(OBE.isPresent());
    }

    @Test
    void ifPresent() {
        {
            final AtomicBoolean atomicBoolean = new AtomicBoolean();
            OBT.ifPresent(b -> atomicBoolean.set(true));
            assertTrue(atomicBoolean.get());
        }
        {
            final AtomicBoolean atomicBoolean = new AtomicBoolean();
            OBF.ifPresent(b -> atomicBoolean.set(true));
            assertTrue(atomicBoolean.get());
        }
        {
            final AtomicBoolean atomicBoolean = new AtomicBoolean();
            OBE.ifPresent(b -> atomicBoolean.set(true));
            assertFalse(atomicBoolean.get());
        }
    }
}