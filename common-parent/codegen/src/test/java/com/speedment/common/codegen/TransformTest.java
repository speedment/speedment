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
package com.speedment.common.codegen;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class TransformTest {

    @Test
    void is() {
        final Transform<String, String> t1 = new T1();
        final Transform<String, String> t2 = new T2();
        assertTrue(t1.is(T1.class));
        assertTrue(t2.is(T1.class));
        assertFalse(t1.is(T2.class));
    }

    private static class T1 implements Transform<String, String> {
        @Override
        public Optional<String> transform(Generator gen, String model) {
            return Optional.empty();
        }
    }

    private static class T2 extends T1 implements Transform<String, String> {}

}