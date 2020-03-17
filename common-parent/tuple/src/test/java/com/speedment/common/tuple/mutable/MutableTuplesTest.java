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
package com.speedment.common.tuple.mutable;

import com.speedment.common.tuple.MutableTuples;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Per Minborg
 */
final class MutableTuplesTest {

    @Test
    void degree0() {
        final MutableTuple0 t0 = MutableTuples.create0();
        test(t0);
    }

    @Test
    void constructor0() {
        final MutableTuple0 t0 = MutableTuples.constructor().get();
        test(t0);
    }

    @Test
    void degree1() {
        final MutableTuple1<Integer> t0 = MutableTuples.create1();
        test(t0);
    }

    @Test
    void constructor1() {
        test(MutableTuples.constructor(Integer.class).get());
    }

    @Test
    void constructor5() {
        final MutableTuple5<Integer, Integer, String, String, Long> t5 = MutableTuples.create5();
        t5.set0(0);
        t5.set1(1);
        t5.set2("Olle");
        t5.set3("Sven");
        t5.set4(4L);

        assertEquals((Integer) 0, t5.get0().get());
        assertEquals((Integer) 1, t5.get1().get());
        assertEquals("Olle", t5.get2().get());
        assertEquals("Sven", t5.get3().get());
        assertEquals((Long) 4L, t5.get4().get());

        final List<Object> actual = t5.stream().map(Optional::get).collect(toList());
        final List<Object> expected = Arrays.asList(0, 1, "Olle", "Sven", 4L);
        assertEquals(expected, actual);

        final List<Object> actualOf = t5.streamOf(String.class).collect(toList());
        final List<Object> expectedOf = Arrays.asList("Olle", "Sven");
        assertEquals(actualOf, expectedOf);

    }

    private void test(MutableTuple0 t0) {
        assertEquals(0, t0.degree());

        try {
            t0.get(0);
            fail();
        } catch (IndexOutOfBoundsException ignore) {
        }
    }

    private void test(MutableTuple1<Integer> t1) {
        assertEquals(1, t1.degree());

        final Optional<Integer> before = t1.get0();
        assertFalse(before.isPresent());

        t1.set0(1);
        final Optional<Integer> after = t1.get0();
        assertTrue(after.isPresent());
        assertEquals((Integer) 1, after.get());

        t1.set0(null);
        final Optional<Integer> nulled = t1.get0();
        assertFalse(nulled.isPresent());

        try {
            t1.get(1);
            fail();
        } catch (IndexOutOfBoundsException ignore) {
        }
    }

}
