/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.field;

import static com.speedment.internal.core.field.predicate.impl.string.BaseStringPredicate.ENDS_WITH_PREDICATE;
import static com.speedment.internal.core.field.predicate.impl.string.BaseStringPredicate.STARTS_WITH_PREDICATE;
import static com.speedment.internal.field.Entity.NAME;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author pemi
 */
public class StringFieldTest extends BaseFieldTest {

    @Test
    public void equalIgnoreCase() throws Exception {
        final List<Entity> expected = collect(e -> e.getName() != null && e.getName().equalsIgnoreCase("abcdef"));
        final List<Entity> result = collect(NAME.equalIgnoreCase("abcdef"));

        assertEquals(expected, result);
        assertEquals(4, collect(NAME.equalIgnoreCase(null)).size());
    }

    @Test
    public void notEqualIgnoreCase() throws Exception {
        final List<Entity> expected = collect(e -> e.getName() == null || !e.getName().equalsIgnoreCase("abcdef"));
        final List<Entity> result = collect(NAME.notEqualIgnoreCase("abcdef"));

        assertEquals(expected, result);
        assertEquals(25, collect(NAME.notEqualIgnoreCase(null)).size());
    }

    @Test
    public void startsWith() throws Exception {

        //assertTrue(STARTS_WITH_PREDICATE.test("ab", "abc"));
        final List<Entity> expected = collect(e -> e.getName() != null && e.getName().startsWith("abc"));
        //final List<Entity> result = collect(e -> e.getName() != null && STARTS_WITH_PREDICATE.test(e.getName(), "abc"));
        final List<Entity> result = collect(NAME.startsWith("abc"));

        printList("startswith expected", expected);
        printList("startswith result", result);

        assertEquals(expected, result);
        assertEquals(0, collect(NAME.startsWith(null)).size());
    }

    @Test
    public void endsWith() throws Exception {

        assertTrue(ENDS_WITH_PREDICATE.test("f", "abcdEf"));

        final List<Entity> expected = collect(e -> e.getName() != null && e.getName().endsWith("f"));
        final List<Entity> result = collect(NAME.endsWith("f"));

        printList("endswith expected", expected);
        printList("endswith result", result);

        assertEquals(expected, result);
        assertEquals(0, collect(NAME.endsWith(null)).size());
    }

    @Test
    public void contains() throws Exception {

        final List<Entity> expected = collect(e -> e.getName() != null && e.getName().contains("a"));
        final List<Entity> result = collect(NAME.contains("a"));

        printList("contains expected", expected);
        printList("contains result", result);

        assertEquals(expected, result);
        assertEquals(0, collect(NAME.contains(null)).size());
    }

    @Test
    public void isEmpty() throws Exception {
        final List<Entity> expected = collect(e -> e.getName() != null && e.getName().isEmpty());
        final List<Entity> result = collect(NAME.isEmpty());
        assertEquals(expected, result);
    }

    @Test
    public void isNotEmpty() throws Exception {
        final List<Entity> expected = collect(e -> e.getName() != null && !e.getName().isEmpty());
        final List<Entity> result = collect(NAME.isNotEmpty());
        assertEquals(expected, result);
    }

}
