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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.common.tuple.old_tests;

import com.speedment.common.tuple.Tuple;
import com.speedment.common.tuple.TupleBuilder;
import com.speedment.common.tuple.Tuples;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 *
 * @author Per Minborg
 */
final class TupleBuilderTest {

    @Test
    void testBuilder() {
        final Tuple expected = Tuples.of("Arne", 1, 3L);
        final Tuple notExpected = Tuples.of("Arne", 1, 3L, "Tryggve");
        final Tuple result = TupleBuilder.builder().add("Arne").add(1).add(3L).build();
        assertEquals(expected, result);
        assertNotEquals(notExpected, result);
    }

}
