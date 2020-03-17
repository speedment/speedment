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
package com.speedment.runtime.core.internal.stream.parallel;

import org.junit.jupiter.api.Test;

import java.util.Spliterator;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author pemi
 */
public abstract class BaseSpliteratorTest {

    protected static final Consumer<Integer> DO_NOTHING = i -> {
    };

    protected Spliterator<Integer> instance;

    protected void printTestName() {
        //System.out.println(name.getMethodName());
    }

    @Test
    void testCharacteristics() {
        assertTrue(instance.hasCharacteristics(Spliterator.SIZED));
        assertTrue(instance.hasCharacteristics(Spliterator.SUBSIZED));
    }

    @Test
    void testGetComparator() {
        assertThrows(IllegalStateException.class, () -> {
            instance.getComparator();
        });
    }

}
