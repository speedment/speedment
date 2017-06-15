/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.core.internal.stream.parallel;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.util.Spliterator;
import java.util.function.Consumer;

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
        //System.out.println(name.getMethodName());
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
