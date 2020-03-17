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
package com.speedment.common.rest;

import static org.junit.jupiter.api.Assertions.*;

import com.speedment.common.rest.Option.Type;
import org.junit.jupiter.api.Test;

final class OptionTest {

    private final Option header = Header.header("Content-Type", "application/json");
    private final Option param = Param.param("key", "value");

    private final AbstractOption abstractHeader = (AbstractOption) header;
    private final AbstractOption abstractParam = (AbstractOption) param;

    @Test
    void getType() {
        assertEquals(Type.HEADER, header.getType());
        assertEquals(Type.PARAM, param.getType());
    }

    @Test
    void getKey() {
        assertEquals("Content-Type", header.getKey());
        assertEquals("key", param.getKey());
    }

    @Test
    void getValue() {
        assertEquals("application/json", header.getValue());
        assertEquals("value", param.getValue());
    }

    @Test
    void testHashCode() {
        assertNotEquals(0, abstractHeader.hashCode());
        assertNotEquals(0, abstractParam.hashCode());
    }

    @Test
    void testToString() {
        assertEquals("Content-Type=application/json", abstractHeader.toString());
        assertEquals("key=value", abstractParam.toString());
    }

    @Test
    void testEquals() {
        final AbstractOption copy = abstractHeader;

        assertTrue(abstractHeader.equals(copy));
        assertFalse(abstractHeader.equals(null));
        assertFalse(abstractHeader.equals(1));

        assertFalse(abstractHeader.equals(abstractParam));

        final Option sameKey = Header.header(header.getKey(), "text/html");
        assertFalse(abstractHeader.equals(sameKey));

        final Option sameValue = Header.header("Accepts", header.getValue());
        assertFalse(abstractHeader.equals(sameValue));

        final Option allDifferent = Header.header("Accepts", "text/html");
        assertFalse(abstractHeader.equals(allDifferent));

        final Option allSame = Header.header(header.getKey(), header.getValue());
        assertTrue(abstractHeader.equals(allSame));
    }
}
