/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.runtime.compute;

import static com.speedment.runtime.compute.TestUtil.strings;
import com.speedment.runtime.compute.expression.ExpressionType;
import java.math.BigDecimal;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Per Minborg
 */
public final class ToBigDecimalTest extends AbstractToTest<ToBigDecimal<String>> {

    public ToBigDecimalTest() {
        super(ExpressionType.BIG_DECIMAL);
    }

    @Override
    ToBigDecimal<String> create() {
        return s -> BigDecimal.valueOf(s.length());
    }

    @Test
    public void testApplyAsInt() {
        strings().forEach(s -> {
            final long actual = mapper.apply(s);
            final long expected = instance.apply(s).longValue();
            assertEquals(expected, actual);
        });
    }

    // NOT SUPPORTED
//    @Test
//    public void testMapToDouble() {
//        strings().forEach(s -> {
//            final double expected = mapper.apply(s).doubleValue() + 1.0;
//            final ToBigDecimal<String> toDouble = instance.mapToDouble(l -> l + 1);
//            final double actual = toDouble.applyAsDouble(s);
//            assertEquals(expected, actual, EPSILON);
//        });
//    }
    @Test
    public void testMap() {
        strings().forEach(s -> {
            final double expected = mapper.apply(s).doubleValue() + 1.0;
            final ToBigDecimal<String> to = instance.map(l -> l.add(BigDecimal.ONE));
            final double actual = to.apply(s).doubleValue();
            assertEquals(expected, actual, EPSILON);
        });
    }

    @Test
    public void testCompose() {
        strings().forEach(s -> {
            final ToBigDecimal<String> composed = instance.compose(str -> str + "A");
            assertEquals(BigDecimal.valueOf(mapper.apply(s + "A")), composed.apply(s));
        });
    }
    
    @Test
    public void testOf() {
        strings().forEach(s -> {
            final ToBigDecimal<String> created = ToBigDecimal.of(str -> BigDecimal.valueOf(str.length()));
            assertEquals(BigDecimal.valueOf(s.length()), created.apply(s));
        });
    }

}
