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
public final class ToIntTest extends AbstractToTest<ToInt<String>> {

    public ToIntTest() {
        super(ExpressionType.INT);
    }

    @Override
    ToInt<String> create() {
        return String::length;
    }

    @Test
    public void testApplyAsInt() {
        strings().forEach(s -> {
            final long actual = mapper.apply(s);
            final long expected = instance.applyAsInt(s);
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
            final ToInt<String> to = instance.map(l -> l + 1);
            final double actual = to.applyAsInt(s);
            assertEquals(expected, actual, EPSILON);
        });
    }

    @Test
    public void testCompose() {
        strings().forEach(s -> {
            final ToInt<String> composed = instance.compose(str -> str + "A");
            assertEquals((long) mapper.apply(s + "A"), composed.applyAsInt(s));
        });
    }

    @Test
    public void testOf() {
        strings().forEach(s -> {
            final ToInt<String> created = ToInt.of(String::length);
            assertEquals(s.length(), created.applyAsInt(s));
        });
    }

}
