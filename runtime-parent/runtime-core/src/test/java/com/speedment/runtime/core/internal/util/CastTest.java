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

package com.speedment.runtime.core.internal.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

final class CastTest {

    @Test
    void cast() {
        assertThrows(NullPointerException.class, () -> Cast.cast("value", null));

        final Optional<String> nullOptional = Cast.cast(null, String.class);
        assertFalse(nullOptional.isPresent());

        final Optional<String> illegalCastOptional = Cast.cast(1, String.class);
        assertFalse(illegalCastOptional.isPresent());

        final Optional<Number> successfulOptional = Cast.cast(1L, Number.class);
        assertTrue(successfulOptional.isPresent());
        assertEquals(1, successfulOptional.get().intValue());
    }

    @Test
    void castOrFail() {
        assertThrows(NullPointerException.class, () -> Cast.castOrFail("value", null));
        assertThrows(NoSuchElementException.class, () -> Cast.castOrFail(null, String.class));
        assertThrows(NoSuchElementException.class, () -> Cast.castOrFail(1, String.class));
        assertEquals(1, Cast.castOrFail(1L, Number.class).intValue());
    }
}
