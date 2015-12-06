/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.internal.core.stream.parallelstrategy;

import java.util.Spliterator;
import java.util.function.Consumer;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author pemi
 */
public abstract class BaseSpliteratorTest {

    protected static final Consumer<Integer> DO_NOTHING = i -> {
    };

    protected Spliterator<Integer> instance;

    @Rule
    public TestName name = new TestName();

    protected void printTestName() {
        System.out.println(name.getMethodName());
    }

    @Test
    public void testCharacteristics() {
        printTestName();
        assertTrue(instance.hasCharacteristics(Spliterator.SIZED));
        assertTrue(instance.hasCharacteristics(Spliterator.SUBSIZED));
    }

    @Test(expected = IllegalStateException.class)
    public void testGetComparator() {
        printTestName();
        instance.getComparator();
    }

}
