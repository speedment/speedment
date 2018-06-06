/**
 *
 * Copyright (c) 2006-2018, Speedment, Inc. All Rights Reserved.
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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.runtime.compute;

import com.speedment.runtime.compute.expression.ExpressionType;
import org.junit.Test;

import static com.speedment.runtime.compute.TestUtil.strings;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author Per Minborg
 */
public final class ToShortTest extends AbstractToTest<ToShort<String>> {

    public ToShortTest() {
        super(ExpressionType.SHORT);
    }

    @Override
    ToShort<String> create() {
        return s -> (short) s.length();
    }

    @Test
    public void testApplyAsInt() {
        strings().forEach(s -> {
            final long actual = mapper.apply(s);
            final long expected = instance.applyAsShort(s);
            assertEquals(expected, actual);
        });
    }

    @Test
    public void testMapToDouble() {
        strings().forEach(s -> {
            final double expected = mapper.apply(s).doubleValue() + 1.0;
            final ToDouble<String> toDouble = instance.mapToDouble(l -> l + 1);
            final double actual = toDouble.applyAsDouble(s);
            assertEquals(expected, actual, EPSILON);
        });
    }

    @Test
    public void testMap() {
        strings().forEach(s -> {
            final double expected = mapper.apply(s).doubleValue() + 1.0;
            final ToShort<String> to = instance.map(l -> (short) (l + 1));
            final double actual = to.applyAsShort(s);
            assertEquals(expected, actual, EPSILON);
        });
    }

    @Test
    public void testCompose() {
        strings().forEach(s -> {
            final ToShortNullable<String> composed = instance.compose(str -> str + "A");
            assertEquals((long) mapper.apply(s + "A"), composed.applyAsShort(s));
        });
    }

    @Test
    public void testOf() {
        strings().forEach(s -> {
            final ToShort<String> created = ToShort.of(str -> (short) str.length());
            assertEquals(s.length(), created.applyAsShort(s));
        });
    }

}
