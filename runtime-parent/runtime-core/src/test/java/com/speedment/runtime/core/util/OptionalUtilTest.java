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

package com.speedment.runtime.core.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.speedment.common.function.OptionalBoolean;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

final class OptionalUtilTest {

    @Test
    void unwrap() {
        assertEquals("string", OptionalUtil.unwrap("string"));
        assertEquals("string", OptionalUtil.unwrap((Object) Optional.of("string")));
        assertEquals("string", OptionalUtil.unwrap(Optional.of("string")));
        assertEquals(1, OptionalUtil.unwrap(OptionalInt.of(1)));
        assertEquals(1, OptionalUtil.unwrap(OptionalLong.of(1)));
        assertEquals(1, OptionalUtil.unwrap(OptionalDouble.of(1)));
        assertEquals(true, OptionalUtil.unwrap(OptionalBoolean.of(true)));

        assertNull(OptionalUtil.unwrap((Optional<Object>) null));
        assertNull(OptionalUtil.unwrap((OptionalInt) null));
        assertNull(OptionalUtil.unwrap((OptionalLong) null));
        assertNull(OptionalUtil.unwrap((OptionalDouble) null));
        assertNull(OptionalUtil.unwrap((OptionalBoolean) null));

        assertNull(OptionalUtil.unwrap(Optional.empty()));
        assertNull(OptionalUtil.unwrap(OptionalInt.empty()));
        assertNull(OptionalUtil.unwrap(OptionalLong.empty()));
        assertNull(OptionalUtil.unwrap(OptionalDouble.empty()));
        assertNull(OptionalUtil.unwrap(OptionalBoolean.empty()));
    }

    @Test
    void ofNullable() {
        assertTrue(OptionalUtil.ofNullable(1L).isPresent());
        assertTrue(OptionalUtil.ofNullable(1).isPresent());
        assertTrue(OptionalUtil.ofNullable(1d).isPresent());
        assertTrue(OptionalUtil.ofNullable(true).isPresent());

        assertFalse(OptionalUtil.ofNullable((Long) null).isPresent());
        assertFalse(OptionalUtil.ofNullable((Integer) null).isPresent());
        assertFalse(OptionalUtil.ofNullable((Double) null).isPresent());
        assertFalse(OptionalUtil.ofNullable((Boolean) null).isPresent());
    }

    @Test
    void parseLong() {
        assertTrue(OptionalUtil.parseLong("1").isPresent());
        assertFalse(OptionalUtil.parseLong(null).isPresent());
    }

    @Test
    void parseInt() {
        assertTrue(OptionalUtil.parseInt("1").isPresent());
        assertFalse(OptionalUtil.parseInt(null).isPresent());
    }

    @Test
    void parseDouble() {
        assertTrue(OptionalUtil.parseDouble("1").isPresent());
        assertFalse(OptionalUtil.parseDouble(null).isPresent());
    }

    @Test
    void parseBoolean() {
        assertTrue(OptionalUtil.parseBoolean("true").isPresent());
        assertFalse(OptionalUtil.parseBoolean(null).isPresent());
    }
}
