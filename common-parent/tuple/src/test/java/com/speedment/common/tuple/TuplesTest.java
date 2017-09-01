package com.speedment.common.tuple;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * @author Emil Forslund
 * @since  1.0.5
 */
public class TuplesTest {

    /**
     * Attempts to recreate issue #500 (initializing a very large tuple caused
     * a NullPointerException since the constructor implementation of internal
     * abstract base class {@code BasicAbstractTuple} accessed the
     * {@link Tuple#degree()}-method before the inner array was initialized.)
     */
    @Test
    public void of() {
        final Random random = new Random();
        final Integer[] array = new Integer[100];
        for (int i = 0; i < 100; i++) {
            array[i] = random.nextInt();
        }

        final Tuple tuple = Tuples.of((Object[]) array);
        assertEquals(tuple.degree(), 100);

        for (int i = 0; i < 100; i++) {
            final Integer expected = array[i];
            final Integer actual = (Integer) tuple.get(i);
            assertEquals(expected, actual);
        }
    }
}