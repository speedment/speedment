/*
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.field;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.speedment.runtime.field.TestEntity.NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 * @author pemi
 */
final class StringFieldTest extends BaseFieldTest {

    @Test
    void equalIgnoreCase() {
        final List<TestEntity> expected = collect(e -> e.getName() != null && e.getName().equalsIgnoreCase("abcdef"));
        final List<TestEntity> result = collect(NAME.equalIgnoreCase("abcdef"));

        assertEquals(expected, result);
        assertEquals(4, collect(NAME.isNull()).size());
        assertThrows(NullPointerException.class, () ->
            collect(NAME.equalIgnoreCase(null))
        );

    }

    @Test
    void notEqualIgnoreCase() {
        final List<TestEntity> expected = collect(e -> e.getName() != null && !e.getName().equalsIgnoreCase("abcdef"));
        final List<TestEntity> result = collect(NAME.notEqualIgnoreCase("abcdef"));

        assertEquals(expected, result);
        assertEquals(25, collect(NAME.isNotNull()).size());
        assertThrows(NullPointerException.class, () ->
            collect(NAME.notEqualIgnoreCase(null))
        );
    }

    @Test
    void startsWith() {

        //assertTrue(STARTS_WITH_PREDICATE.test("ab", "abc"));
        final List<TestEntity> expected = collect(e -> e.getName() != null && e.getName().startsWith("abc"));
        //final List<Entity> result = collect(e -> e.getName() != null && STARTS_WITH_PREDICATE.test(e.getName(), "abc"));
        final List<TestEntity> result = collect(NAME.startsWith("abc"));

        printList("startswith expected", expected);
        printList("startswith result", result);

        assertEquals(expected, result);
        assertThrows(NullPointerException.class, () ->
                collect(NAME.startsWith(null))
        );
    }

    @Test
    void endsWith() {
        
        final List<TestEntity> expected = collect(e -> e.getName() != null && e.getName().endsWith("f"));
        final List<TestEntity> result = collect(NAME.endsWith("f"));

        printList("endswith expected", expected);
        printList("endswith result", result);

        assertEquals(expected, result);
        assertThrows(NullPointerException.class, () ->
            collect(NAME.endsWith(null))
        );
    }

    @Test
    void contains()  {

        final List<TestEntity> expected = collect(e -> e.getName() != null && e.getName().contains("a"));
        final List<TestEntity> result = collect(NAME.contains("a"));

        printList("contains expected", expected);
        printList("contains result", result);

        assertEquals(expected, result);
        assertThrows(NullPointerException.class, () ->
                collect(NAME.contains(null))
        );
    }

    @Test
    void isEmpty() {
        final List<TestEntity> expected = collect(e -> e.getName() == null || e.getName().isEmpty());
        final List<TestEntity> result = collect(NAME.isEmpty());
        assertEquals(expected, result);
    }

    @Test
    void isNotEmpty()  {
        final List<TestEntity> expected = collect(e -> e.getName() != null && !e.getName().isEmpty());
        final List<TestEntity> result = collect(NAME.isNotEmpty());
        assertEquals(expected, result);
    }

}
